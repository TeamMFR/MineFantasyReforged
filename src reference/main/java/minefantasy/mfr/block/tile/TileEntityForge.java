package minefantasy.mfr.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.IHeatSource;
import minefantasy.mfr.api.crafting.IHeatUser;
import minefantasy.mfr.api.heating.ForgeFuel;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.helpers.Functions;
import minefantasy.mfr.api.refine.IBellowsUseable;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.block.refining.BlockForge;
import minefantasy.mfr.item.heatable.ItemHeated;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.network.ForgePacket;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityForge extends TileEntity implements IInventory, IBasicMetre, IHeatSource, IBellowsUseable, ITickable {
    public static final float maxTemperature = 5000;
    public float fuel;
    public float maxFuel = 6000;// 5m
    public float temperature, fuelTemperature;
    public float dragonHeartPower = 0F;
    public String texTypeForRender = "stone";
    public int workableState = 0;
    public boolean isLit;
    public int exactTemperature;
    int justShared;
    private ItemStack[] inv = new ItemStack[1];
    private Random rand = new Random();
    private int ticksExisted;
    private boolean isBurning = false;

    public TileEntityForge(Block block, String type) {
        texTypeForRender = type;
        blockType = block;
    }

    public TileEntityForge() {
    }

    @Override
    public void update() {
        ++ticksExisted;

        if (world.isRemote)
            return;

        if (justShared > 0)
            --justShared;

        if (ticksExisted % 20 == 0) {
            ItemStack item = getStackInSlot(0);
            if (item != null && !world.isRemote) {
                modifyItem(item, 0);
            }
            syncData();
        }
        if (ticksExisted % 10 == 0) {
            shareTemp();
        }

        if (!isLit() && !world.isRemote) {
            if (temperature > 0 && ticksExisted % 5 == 0) {
                temperature = 0;
            }
            return;
        }
        tickFuel();
        if (fuel <= 0) {
            this.extinguish();
            return;
        }
        isBurning = isBurning();// Check if it's burning
        float maxTemp = isLit() ? (fuelTemperature + getUnderTemperature()) : 0;

        if (temperature < maxTemp) {
            float amount = 2.0F;
            temperature += amount;
            if (temperature > maxTemp) {
                temperature = maxTemp;
            }
        } else if (temperature > maxTemp && rand.nextInt(20) == 0) {
            temperature -= 10;
        }

        if (isBurning && temperature > 250 && rand.nextInt(20) == 0 && !isOutside()) {
            int val = this.getTier() == 1 ? 3 : 1;
            if (this.hasBlockAbove()) {
                SmokeMechanics.emitSmokeIndirect(world, pos.add(0,1,0), val);
            } else {
                SmokeMechanics.emitSmokeIndirect(world, pos, val);
            }
        }
        maxFuel = getTier() == 1 ? 12000 : 6000;
    }

    private boolean isOutside() {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!world.canBlockSeeSky(pos.add(x,1,y))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void shareTemp() {
        isLit = isLit();
        shareTo(-1, 0);
        shareTo(1, 0);
        shareTo(0, -1);
        shareTo(0, 1);
    }

    private void shareTo(int x, int z) {
        if (fuel <= 0)
            return;

        int share = 2;
        TileEntity tile = world.getTileEntity(pos.add(x,0,z));
        if (tile == null)
            return;

        if (tile instanceof TileEntityForge) {
            TileEntityForge forge = (TileEntityForge) tile;

            if (isLit && !forge.isLit && forge.fuel > 0) {
                forge.fireUpForge();
            }
            if (!forge.isBurning() && temperature > 1) {
                forge.temperature = 1;
            }
            if (forge.temperature < (temperature - share)) {
                forge.temperature += share;
                temperature -= share;
            }
            share = 1200;
            if (forge.fuel < (fuel - share)) {
                forge.fuel += share;
                fuel -= share;
            }
        }
    }

    private void tickFuel() {
        if (fuel > 0) {
            fuel -= 0.2F;
        }

        if (fuel < 0)
            fuel = 0;
    }

    private boolean isBurning() {
        return fuel > 0 && temperature > 0;
    }

    @SideOnly(Side.CLIENT)
    private boolean shouldRenderFlame() {
        return fuel > 0 && temperature > 1;
    }

    private void modifyItem(ItemStack item, int slot) {
        if (item.getItem() == ComponentListMFR.hotItem) {
            int temp = ItemHeated.getTemp(item);
            if (temp > temperature) {
                int i = (int) (temperature / 5F);
                temp -= i;
            } else {
                int increase = (int) (temperature / (20F * getStackModifier(item.getCount())));
                if (temp >= (temperature - increase)) {
                    temp = (int) temperature;
                } else {
                    temp += increase;
                }
            }
            if (temp <= 0) {
                // this.setInventorySlotContents(slot, ItemHeated.getItem(item));
            } else {
                ItemHeated.setTemp(item, Math.max(0, temp));
            }
        } else if (temperature > 0) {
            this.setInventorySlotContents(slot, ItemHeated.createHotItem(item));

        }
    }

    private float getStackModifier(int stackSize) {
        float mod = 1F + stackSize / 16F;// 1x to 5x
        return mod;
    }

    public int getTier() {
        Block block = world.getBlockState(pos).getBlock();
        if (block != null && block instanceof BlockForge) {
            return ((BlockForge) block).tier;
        }
        return 0;
    }

    public float getUnderTemperature() {
        IBlockState under = world.getBlockState(pos.add(0,-1,0));

        if (under.getMaterial() == Material.FIRE) {
            return 50F;
        }
        if (under.getMaterial() == Material.LAVA) {
            return 100F;
        }
        return 0F;
    }
    // INVENTORY

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setFloat("temperature", temperature);
        nbt.setFloat("fuelTemperature", fuelTemperature);
        nbt.setFloat("dragonHeartPower", dragonHeartPower);
        nbt.setFloat("fuel", fuel);
        nbt.setFloat("maxFuel", maxFuel);

        NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < this.inv.length; ++i) {
            if (this.inv[i] != null) {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte) i);
                this.inv[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        temperature = nbt.getFloat("temperature");
        fuelTemperature = nbt.getFloat("fuelTemperature");
        dragonHeartPower = nbt.getFloat("dragonHeartPower");
        fuel = nbt.getFloat("fuel");
        maxFuel = nbt.getFloat("maxFuel");

        NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.inv.length) {
                this.inv[slotNum] = new ItemStack(savedSlot);
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.inv[slot] != null) {
            ItemStack itemstack;

            if (this.inv[slot].getCount() <= num) {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inv[slot].splitStack(num);

                if (this.inv[slot].getCount() == 0) {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return inv[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        inv[slot] = item;
    }

    public void onInventoryChanged() {
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer user) {
        return user.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) < 8D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    public BlockForge getActiveBlock() {
        if (world == null)
            return null;

        Block block = world.getBlockState(pos).getBlock();

        if (block != null && block instanceof BlockForge) {
            return (BlockForge) block;
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public String getTextureName() {
        BlockForge forge = getActiveBlock();
        if (forge == null)
            return "forge_" + texTypeForRender;

        return "forge_" + forge.type + (shouldRenderFlame() ? "_active" : "");
    }

    public boolean hasFuel() {
        return world != null && fuel > 0;
    }

    public boolean isLit() {
        BlockForge forge = getActiveBlock();
        return forge != null && forge.isActive;
    }

    /**
     * Puts the fire out
     */
    public void extinguish() {
        if (getTier() == 1) {
            return;
        }
        BlockForge.updateFurnaceBlockState(false, world, pos);
    }

    /**
     * Fires the forge up
     */
    public void fireUpForge() {
        if (getTier() == 1) {
            return;
        }
        BlockForge.updateFurnaceBlockState(true, world, pos);
    }

    public float getBellowsEffect() {
        return getTier() == 1 ? 2.0F : 1.5F;
    }

    private float getBellowsMax() {
        return Math.min(fuelTemperature * getBellowsEffect(), getMaxTemp());
    }

    public int getMaxTemp() {
        return (int) maxTemperature;
    }

    public boolean addFuel(ForgeFuel stats, boolean hand, int tier) {
        maxFuel = tier == 1 ? 12000 : 6000;

        boolean hasUsed = false;

        if (stats.baseHeat > this.fuelTemperature) // uses if hotter
        {
            hasUsed = true;
        }
        int room_left = (int) (maxFuel - fuel);
        if (hand && room_left > 0) {
            hasUsed = true;
            fuel = Math.min(fuel + stats.duration, maxFuel);// Fill as much as can fit
        } else if (!hand && (fuel == 0 || room_left >= stats.duration))// For hoppers: only fill when there's full room
        {
            hasUsed = true;
            fuel = Math.min(fuel + stats.duration, maxFuel);// Fill as much as can fit
        }
        if (stats.doesLight && !isLit()) {
            fireUpForge();
            hasUsed = true;
        }
        if (hasUsed) {
            fuelTemperature = stats.baseHeat;
        }
        return hasUsed;
    }

    @Override
    public int getMetreScale(int size) {
        if (shouldShowMetre()) {
            return (int) (size / maxFuel * fuel);
        }
        return 0;
    }

    public int[] getTempsScaled(int size) {
        int[] temps = new int[2];
        if (shouldShowMetre()) {
            temps[0] = (int) (size / this.maxTemperature * this.temperature);
            temps[1] = (int) (size / this.maxTemperature * this.fuelTemperature);
        }
        if (temps[0] > size)
            temps[0] = size;
        if (temps[1] > size)
            temps[1] = size;
        return temps;
    }

    @Override
    public boolean shouldShowMetre() {
        return true;
    }

    @Override
    public String getLocalisedName() {
        return (int) this.temperature + "C " + I18n.translateToLocal(
                workableState >= 2 ? "state.unstable" : workableState == 1 ? "state.workable" : "forge.fuel.name");
    }

    public boolean[] showSides() {
        if (world == null) {
            return new boolean[]{true, true, true, true};
        }
        boolean front = !isForge(0, 0, 1);
        boolean left = !isForge(-1, 0, 0);
        boolean right = !isForge(1, 0, 0);
        boolean back = !isForge(0, 0, -1);

        return new boolean[]{front, left, right, back};
    }

    private boolean isForge(int x, int y, int z) {
        return world.getBlockState(pos.add(x,y,z)) instanceof BlockForge;
    }

    private void syncData() {

        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new ForgePacket(this).generatePacket(), (WorldServer) world, this.pos);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player, new ForgePacket(this).generatePacket());
		}
		*/
    }

    public void onUsedWithBellows(float powerLevel) {
        if (!isBurning())
            return;
        if (justShared > 0) {
            return;
        }
        if (fuel <= 0) {
            return;
        }
        float max = getBellowsMax();
        justShared = 5;
        if (temperature < max) {
            temperature = Math.min(max, temperature + (500F * powerLevel));
        }

        for (int a = 0; a < 10; a++) {
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (rand.nextDouble() * 0.8D) + 0.1D, pos.getY() + 0.4D,
                    pos.getZ() + (rand.nextDouble() * 0.8D) + 0.1D, 0, 0.01, 0);
        }

        pumpBellows(-1, 0, powerLevel * 0.9F);
        pumpBellows(0, -1, powerLevel * 0.9F);
        pumpBellows(0, 1, powerLevel * 0.9F);
        pumpBellows(1, 0, powerLevel * 0.9F);

    }

    private void pumpBellows(int x, int z, float pump) {
        if (fuel <= 0)
            return;

        // int share = 2;
        TileEntity tile = world.getTileEntity(pos.add(x,0,z));
        if (tile == null)
            return;

        if (tile instanceof TileEntityForge) {
            TileEntityForge forge = (TileEntityForge) tile;
            forge.onUsedWithBellows(pump);
        }
    }

    public float getBlockTemperature() {
        if (this.isLit) {
            return temperature;
        }
        return 0;
    }

    /*
     * private void averageAllItems() { /* int temp = 0; int items = 0;
     * for(ItemStack item: inv) { if(item != null && item.getItem() instanceof
     * IHotItem) { ++items; temp += ItemHeated.getTemp(item); } } int average =
     * (int)((float)temp / items); for(ItemStack item: inv) { if(item != null &&
     * item.getItem() instanceof IHotItem) { ItemHeated.setTemp(item, average); } }
     * }
     */

    public boolean hasBlockAbove() {
        if (world == null)
            return false;

        TileEntity tile = world.getTileEntity(pos.add(0,1,0));
        if (tile != null && tile instanceof IHeatUser) {
            return ((IHeatUser) tile).canAccept(this);
        }

        return tile != null && tile instanceof TileEntityFurnace;
    }

    public boolean tryAddHeatable(ItemStack held) {
        ItemStack contents = inv[0];
        if (contents == null) {
            ItemStack placed = held.copy();
            placed.setCount(1);
            setInventorySlotContents(0, placed);
            return true;
        } else if (contents.getItem() instanceof ItemHeated) {
            ItemStack hot = Heatable.getItem(contents);
            if (hot != null && hot.isItemEqual(held) && ItemStack.areItemStackTagsEqual(held, hot)
                    && contents.getCount() < contents.getMaxStackSize()) {
                contents.grow(1);
                return true;
            }
        }
        return false;
    }

    public int getWorkableState() {
        if (world == null || world.isRemote) {
            return workableState;
        }
        if (this.inv[0] != null) {
            return Heatable.getHeatableStage(inv[0]);
        }
        return 0;
    }

    @Override
    public boolean canPlaceAbove() {
        return true;
    }

    @Override
    public int getHeat() {
        if (world.isRemote) {
            return exactTemperature;
        }
        int mx = (int) (temperature * 1.2F);
        int mn = (int) (temperature * 0.8F);
        return Functions.getIntervalWave1_i(ticksExisted, 400, mx, mn);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
