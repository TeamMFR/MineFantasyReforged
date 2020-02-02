package minefantasy.mfr.block.tile;

import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.heating.ForgeItemHandler;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.refine.BigFurnaceRecipes;
import minefantasy.mfr.api.refine.IBellowsUseable;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.block.refining.BlockBigFurnace;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.packet.BigFurnacePacket;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityBigFurnace extends TileEntity implements IBellowsUseable, IInventory, ISidedInventory, ITickable {

    // UNIVERSAL
    public int fuel;
    public int maxFuel;
    public ItemStack[] inv;
    public int itemMeta;
    public boolean built = false;
    // ANIMATE
    public int numUsers;
    public int doorAngle;
    // HEATER
    public float heat;
    public float maxHeat;
    public int justShared;
    // FURNACE
    public int progress;
    /**
     * CLIENT VAR This is used to determine if the block is able to emit smoke
     */
    public boolean isBurningClient;
    private Random rand = new Random();
    private int aboveType;
    private int ticksExisted;
    private int ticksSinceSync;
    private boolean wasBurning;

    public TileEntityBigFurnace() {
        super();
        inv = new ItemStack[8];
        fuel = maxFuel = progress = 0;
    }

    public TileEntityBigFurnace(int meta) {
        this();
        itemMeta = meta;
    }

    public TileEntityBigFurnace setBlockType(Block block) {
        blockType = block;
        return this;
    }

    @Override
    public void onUsedWithBellows(float powerLevel) {
        if (isHeater()) {
            if (justShared > 0) {
                return;
            }
            justShared = 5;

            if (fuel > 0) {
                int max = (int) (maxHeat * 1.5F);
                if (heat < max) {
                    heat += 50 * powerLevel;
                }

                for (int a = 0; a < 10; a++) {
                    world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (rand.nextDouble() * 0.8D) + 0.1D, pos.getY() + 0.4D,
                            pos.getZ() + (rand.nextDouble() * 0.8D) + 0.1D, 0, 0.01, 0);
                }
            }
            pumpBellows(-1, 0, powerLevel * 0.9F);
            pumpBellows(0, -1, powerLevel * 0.9F);
            pumpBellows(0, 1, powerLevel * 0.9F);
            pumpBellows(1, 0, powerLevel * 0.9F);
        }
    }

    private void pumpBellows(int x, int z, float pump) {
        int share = 2;
        TileEntity tile = world.getTileEntity(pos.add(x,0, z));
        if (tile == null)
            return;

        if (tile instanceof TileEntityBigFurnace) {
            TileEntityBigFurnace furn = (TileEntityBigFurnace) tile;
            if (furn.isHeater())
                furn.onUsedWithBellows(pump);
        }
    }

    @Override
    public void update() {

        if (!world.isRemote) {
            if (numUsers > 0 && doorAngle < 20) {
                doorAngle++;
            }

            if (numUsers <= 0 && doorAngle > 0) {
                doorAngle--;
            }
            if (doorAngle < 0)
                doorAngle = 0;
            if (doorAngle > 20)
                doorAngle = 20;
        }

        ++ticksExisted;
        if (justShared > 0)
            justShared--;
        if (ticksExisted % 10 == 0) {
            built = structureExists();
        }
        if (isHeater()) {
            updateHeater();
        } else {
            updateFurnace();
        }
        // UNIVERSAL
        if (isBurning() != wasBurning || ticksExisted == 20) {
            world.notifyLightSet(pos);
        }
        if (!world.isRemote) {
            sendPacketToClients();
        }
        wasBurning = isBurning();
    }

    private void updateFurnace() {
        if (isBurning()) {
            if (world.isRemote && rand.nextInt(10) == 0) {
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (rand.nextDouble() * 0.8D) + 0.1D, pos.getY() + 0.4D,
                        pos.getZ() + (rand.nextDouble() * 0.8D) + 0.1D, 0, 0.01, 0);
            }
        }
        if (world.isRemote) {
            return;
        }
        if (isBurning()) {
            puffSmoke(new Random(), world, pos);
        }
        TileEntityBigFurnace heater = getHeater();

        if (heater != null) {
            heat = heater.heat;
        } else {
            heat -= 4;
        }
        boolean canSmelt = false;
        boolean smelted = false;

        if (getSpecialResult() != null) {
            if (!canFitSpecialResult()) {
                canSmelt = false;
            } else {
                canSmelt = true;

                if (progress >= getMaxTime()) {
                    smeltSpecial();
                    smelted = true;
                }
            }
        } else
            for (int a = 0; a < 4; a++) {
                if (canSmelt(inv[a], inv[a + 4])) {
                    canSmelt = true;

                    if (progress >= getMaxTime()) {
                        smeltItem(a, a + 4);
                        smelted = true;
                    }
                }
            }

        if (canSmelt) {
            progress += heat;
        }
        if (!canSmelt || smelted) {
            progress = 0;
        }
    }

    private boolean canFitSpecialResult() {
        ItemStack spec = getSpecialResult();

        if (spec != null) {
            int spaceNeeded = spec.getCount();
            int spaceLeft = 0;

            for (int a = 4; a < 8; a++) {
                ItemStack item = inv[a];
                if (inv[a] == null) {
                    spaceLeft += 64;
                } else {
                    if (CustomToolHelper.areEqual(inv[a], spec)) {
                        if (inv[a].getCount() < inv[a].getMaxStackSize()) {
                            spaceLeft += inv[a].getMaxStackSize() - inv[a].getCount();
                        }
                    }
                }
            }
            return spec.getCount() <= spaceLeft;
        }
        return false;
    }

    private void smeltSpecial() {
        ItemStack res = getSpecialResult().copy();

        for (int output = 4; output < 8; output++) {
            if (res.getCount() <= 0)
                break;

            if (inv[output] == null) {
                setInventorySlotContents(output, res);
                break;
            } else {
                if (CustomToolHelper.areEqual(inv[output], res)) {
                    int spaceLeft = inv[output].getMaxStackSize() - inv[output].getCount();

                    if (res.getCount() <= spaceLeft) {
                        inv[output].grow(res.getCount());
                        break;
                    } else {
                        inv[output].grow(spaceLeft);
                        res.shrink(spaceLeft);
                    }
                }
            }
        }
        for (int input = 0; input < 4; input++)
            decrStackSize(input, 1);
    }

    public void puffSmoke(Random rand, World world, BlockPos pos) {
        if (rand.nextInt(5) != 0) {
            return;
        }
        EnumFacing dir = getBack();

        SmokeMechanics.emitSmokeIndirect(world, pos.add(dir.getFrontOffsetX(),(isHeater() ? 2 : 1), dir.getFrontOffsetZ()), 1);
    }

    private int getMaxTime() {
        return (int) 1.0E+5;
    }

    private void smeltItem(int input, int output) {
        ItemStack res = getResult(inv[input]).copy();

        if (inv[output] == null) {
            setInventorySlotContents(output, res);
        } else {
            if (CustomToolHelper.areEqual(inv[output], res)) {
                inv[output].grow( res.getCount());
            }
        }

        decrStackSize(input, 1);
    }

    private boolean canSmelt(ItemStack in, ItemStack out) {
        if (isHeater())
            return false;

        if (!built)
            return false;

        ItemStack res = getResult(in);
        if (res == null) {
            return false;
        }
        if (out == null) {
            return true;
        }
        if (CustomToolHelper.areEqual(out, res)) {
            int max = res.getMaxStackSize();
            if ((out.getCount() + res.getCount()) > max) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    private TileEntityBigFurnace getHeater() {
        TileEntity tile = world.getTileEntity(pos.add(0,-1,0));
        if (tile != null && tile instanceof TileEntityBigFurnace) {
            if (((TileEntityBigFurnace) tile).isHeater())
                return (TileEntityBigFurnace) tile;
        }
        return null;
    }

    private TileEntityBigFurnace getFurnace() {
        TileEntity tile = world.getTileEntity(pos.add(0,1,0));
        if (tile != null && tile instanceof TileEntityBigFurnace) {
            if (!((TileEntityBigFurnace) tile).isHeater())
                return (TileEntityBigFurnace) tile;
        }
        return null;
    }

    private void updateHeater() {
        if (world.isRemote)
            return;

        TileEntityBigFurnace furn = getFurnace();
        if (furn != null) {
            aboveType = furn.getType();
        }
        if (!built) {
            heat = maxHeat = fuel = maxFuel = 0;
            return;
        }
        if (heat < maxHeat) {
            heat++;
        }
        if (heat > maxHeat) {
            heat--;
        }
        if (fuel > 0) {
            fuel--;
        } else {
            if (inv[0] != null && isItemFuel(inv[0])) {
                fuel = maxFuel = getItemBurnTime(inv[0]);
                maxHeat = getItemHeat(inv[0]);
                ItemStack cont = inv[0].getItem().getContainerItem(inv[0]);

                if (cont != null) {
                    inv[0] = cont;
                } else {
                    decrStackSize(0, 1);
                }
            }
            if (fuel <= 0) {
                if (heat > 0)
                    heat--;
                maxHeat = 0;
            }
        }

    }

    private float getItemHeat(ItemStack itemStack) {
        return ForgeItemHandler.getForgeHeat(itemStack);
    }

    public ItemStack getResult(ItemStack item) {
        if (item == null)
            return null;

        // SPECIAL SMELTING
        BigFurnaceRecipes recipe = BigFurnaceRecipes.getResult(item);
        if (recipe != null && recipe.tier <= this.getTier()) {
            return recipe.result;
        }

        ItemStack res = FurnaceRecipes.instance().getSmeltingResult(item);// If no special: try vanilla
        if (res != null) {
            if (res.getItem() instanceof ItemFood || item.getItem() instanceof ItemFood) {
                return new ItemStack(FoodListMFR.burnt_food, 1, 1);
            }
            return res;
        }

        return null;
    }

    public ItemStack getSpecialResult() {
        return null;
        /*
         * ItemStack[] input = new ItemStack[4]; for(int a = 0; a < 4; a ++) { input[a]
         * = inv[a]; } Alloy alloy = SpecialFurnaceRecipes.getResult(input); if(alloy !=
         * null) { if(alloy.getLevel() <= getSmeltLevel()) { return
         * SpecialFurnaceRecipes.getResult(input).getRecipeOutput(); } } return null;
         */
    }

    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public ItemStack getStackInSlot(int i) {
        return inv[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (inv[i] != null) {
            if (inv[i].getCount() <= j) {
                ItemStack itemstack = inv[i];
                inv[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = inv[i].splitStack(j);
            if (inv[i].getCount() == 0) {
                inv[i] = null;
            }
            return itemstack1;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int height) {
        if (this.maxFuel == 0) {
            this.maxFuel = 200;
        }

        return this.fuel * height / this.maxFuel;
    }

    @SideOnly(Side.CLIENT)
    public int getHeatScaled(int height) {
        if (heat <= 0)
            return 0;
        int size = (int) (height / TileEntityForge.maxTemperature * this.heat);

        return Math.min(size, height);
    }

    @SideOnly(Side.CLIENT)
    public int getItemHeatScaled(int height) {
        if (maxHeat <= 0)
            return 0;
        int size = (int) (height / TileEntityForge.maxTemperature * this.maxHeat);

        return Math.min(size, height);
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inv[i] = itemstack;
        if (itemstack != null && itemstack.getCount() > getInventoryStackLimit()) {
            itemstack.setCount(getInventoryStackLimit());
        }
    }

    public String getInvName() {
        int t = getType();
        String tier = "";

        if (isHeater()) {
            return I18n.translateToLocal("tile.furnace.name") + " "
                    + I18n.translateToLocal("block.furnace.heater");
        }

        return tier + " " + I18n.translateToLocal("tile.furnace.name");
    }

    public int getSmeltLevel() {
        if (isHeater()) {
            return -1;
        }
        return getType() - 1;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.inv.length) {
                this.inv[slotNum] = new ItemStack(savedSlot);
            }
        }

        justShared = nbt.getInteger("Shared");
        built = nbt.getBoolean("Built");

        fuel = nbt.getInteger("fuel");
        maxFuel = nbt.getInteger("MaxFuel");

        heat = nbt.getFloat("heat");
        maxHeat = nbt.getFloat("maxHeat");

        progress = nbt.getInteger("progress");
        aboveType = nbt.getInteger("Level");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("Shared", justShared);
        nbt.setInteger("Level", aboveType);
        nbt.setBoolean("Built", built);

        nbt.setInteger("fuel", fuel);
        nbt.setInteger("maxFuel", maxFuel);

        nbt.setFloat("heat", heat);
        nbt.setFloat("maxHeat", maxHeat);

        nbt.setInteger("progress", progress);

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

    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    public boolean isBurning() {
        if (!world.isRemote) {
            return isBurningClient;
        }
        if (isHeater()) {
            return heat > 0;
        }
        return progress > 0 && heat > 0;
    }

    public boolean isHeater() {
        Block block = world != null ? getBlockType() : blockType;

        if (block != null && block instanceof BlockBigFurnace) {
            return ((BlockBigFurnace) block).isHeater;
        }
        return false;
    }

    public int getTier() {
        Block block = world != null ? getBlockType() : blockType;

        if (block != null && block instanceof BlockBigFurnace) {
            return ((BlockBigFurnace) block).tier;
        }
        return 0;
    }

    public int getItemBurnTime(ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        return (int) Math.ceil(ForgeItemHandler.getForgeFuel(itemstack) / 8);
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        if (world.getTileEntity(pos) != this) {
            return false;
        }
        return entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
    }

    public void openChest() {
        if (numUsers == 0) {
            this.world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundsMFR.FURNACE_OPEN, SoundCategory.NEUTRAL, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F, true);
        }
        ++numUsers;
    }

    public void closeChest() {
        --numUsers;
        if (numUsers == 0 && doorAngle >= 15) {
            this.world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundsMFR.FURNACE_CLOSE, SoundCategory.NEUTRAL, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F, true);
        }
    }

    private void sendPacketToClients() {
        if (!world.isRemote) {
            NetworkUtils.sendToWatchers(new BigFurnacePacket(this).generatePacket(), (WorldServer) world, this.pos);
			/*
			List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
			for (int i = 0; i < players.size(); i++) {
				EntityPlayer player = players.get(i);
				((WorldServer) worldObj).getEntityTracker().func_151248_b(player,
						new BigFurnacePacket(this).generatePacket());
			}
			*/
        }
    }

    /*
     * @Override public void recievePacket(ByteArrayDataInput data) { fuel =
     * data.readInt(); progress = data.readInt(); direction = data.readInt(); heat =
     * data.readInt(); int burn = data.readInt(); isBurningClient = burn == 1;
     * justShared = data.readInt(); doorAngle = data.readInt(); }
     */

    /**
     * Heater:0||Stone :1;
     */
    public int getType() {
        return isHeater() ? 0 : 1;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return canInsertItem(slot, item, EnumFacing.UP);
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

    public boolean isItemFuel(ItemStack item) {
        return this.getItemBurnTime(item) > 0;
    }

    public int getCookProgressScaled(int i) {
        return (progress * i) / getMaxTime();
    }

    public boolean isBlockValidForSide(BlockPos pos) {
        if (world == null) {
            return false;
        }

        Block block = world.getBlockState(pos).getBlock();

        if (block == null) {
            return false;
        }

        if (block == world.getBlockState(pos)) {
            return true;
        }
        return block == BlockListMFR.FIREBRICKS;
    }

    /**
     * Checks a valid block for sides. It must be the required block.
     * <p>
     * HEATER: Requires any stone block for bronze, stone above hardness 2.0(like
     * slate)for iron, and stone above hardness above 5.0(like granite) for steel
     * <p>
     * FURNACES require metal blocks of their material
     */
    public boolean isBlockValidForTop(BlockPos pos) {
        if (world == null) {
            return false;
        }
        Block block = world.getBlockState(pos).getBlock();

        if (block == null) {
            return false;
        }
        return block == BlockListMFR.FIREBRICKS;
    }

    public boolean isBlockValidForSide(EnumFacing side) {
        if (world == null) {
            return false;
        }

        return isBlockValidForSide(pos.add(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ() ));
    }

    public boolean isSolid(EnumFacing side) {
        if (world == null) {
            return false;
        }

        Material mat = world.getBlockState(pos.add(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ())).getMaterial();

        return mat.isSolid();
    }

    /**
     * Determines if the furnace is built properly HEATER must have sides blocked by
     * stone FURNACES must have walls built to specifications
     */
    private boolean structureExists() {
        if (world == null) {
            return false;
        }

        if (isSolid(getFront())) {
            return false;
        }

        if (!isHeater() && !isBlockValidForTop(pos.add(0,1,0))) {
            return false;
        }

        if (!isBlockValidForSide(getLeft())) {
            return false;
        }
        if (!isBlockValidForSide(getRight())) {
            return false;
        }
        if (!isBlockValidForSide(getBack())) {
            return false;
        }

        return true;
    }

    /**
     * Gets the direction the face is at(Opposite to the placer)
     */
    public EnumFacing getFront() {
        EnumFacing direction = EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ());
        if (direction == EnumFacing.SOUTH)// SOUTH PLACE
        {
            return EnumFacing.NORTH;
        }
        if (direction == EnumFacing.WEST)// WEST PLACE
        {
            return EnumFacing.EAST;
        }
        if (direction == EnumFacing.NORTH)// NORTH PLACE
        {
            return EnumFacing.SOUTH;
        }
        if (direction == EnumFacing.EAST)// EAST PLACE
        {
            return EnumFacing.WEST;
        }
        return null;
    }

    /**
     * Gets the direction the back is facing (Same dir as placer)
     */
    public EnumFacing getBack() {
        EnumFacing direction = EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ());
        if (direction == EnumFacing.SOUTH)// SOUTH PLACE
        {
            return EnumFacing.SOUTH;
        }
        if (direction == EnumFacing.WEST)// WEST PLACE
        {
            return EnumFacing.WEST;
        }
        if (direction == EnumFacing.NORTH)// NORTH PLACE
        {
            return EnumFacing.NORTH;
        }
        if (direction == EnumFacing.EAST)// EAST PLACE
        {
            return EnumFacing.EAST;
        }
        return null;
    }

    /**
     * Gets the direction the left is facing
     */
    public EnumFacing getLeft() {
        EnumFacing direction = EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ());
        if (direction == EnumFacing.SOUTH)// SOUTH PLACE
        {
            return EnumFacing.WEST;
        }
        if (direction == EnumFacing.WEST)// WEST PLACE
        {
            return EnumFacing.NORTH;
        }
        if (direction == EnumFacing.NORTH)// NORTH PLACE
        {
            return EnumFacing.EAST;
        }
        if (direction == EnumFacing.EAST)// EAST PLACE
        {
            return EnumFacing.SOUTH;
        }
        return null;
    }

    /**
     * Gets the direction the right is facing
     */
    public EnumFacing getRight() {
        EnumFacing direction = EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ());
        if (direction == EnumFacing.SOUTH)// SOUTH PLACE
        {
            return EnumFacing.EAST;
        }
        if (direction == EnumFacing.WEST)// WEST PLACE
        {
            return EnumFacing.SOUTH;
        }
        if (direction == EnumFacing.NORTH)// NORTH PLACE
        {
            return EnumFacing.WEST;
        }
        if (direction == EnumFacing.EAST)// EAST PLACE
        {
            return EnumFacing.NORTH;
        }
        return null;
    }

    public String getTexture() {
        if (isHeater()) {
            return "furnace_heater";
        }
        return "furnace_rock";
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (isHeater()) {
            return new int[]{0};
        }
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, EnumFacing direction) {
        if (isHeater()) {
            return slot == 0 && isItemFuel(item);
        }
        return slot < 4 && getResult(item) != null;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, EnumFacing direction) {
        if (!isHeater()) {
            return slot >= 4;
        }
        return item.getItem() == Items.BUCKET;
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
