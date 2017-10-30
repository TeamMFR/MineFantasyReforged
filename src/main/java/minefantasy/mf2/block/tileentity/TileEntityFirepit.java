package minefantasy.mf2.block.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.crafting.IBasicMetre;
import minefantasy.mf2.api.crafting.IHeatSource;
import minefantasy.mf2.api.crafting.IHeatUser;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.helpers.Functions;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import java.util.Random;

public class TileEntityFirepit extends TileEntity implements IBasicMetre, IHeatSource {
    private final int maxFuel = 12000; // 10 minutes
    public int fuel = 0;
    private float charcoal = 0;
    private int ticksExisted;
    private Random rand = new Random();

    /**
     * Gets the burn time
     * <p>
     * Wood tools and plank item are 1 minute sticks and saplings are 30seconds
     */
    public static int getItemBurnTime(ItemStack input) {
        if (input == null) {
            return 0;
        } else {
            Item i = input.getItem();
            if (i == Items.stick)
                return 600;// 30Sec
            if (i == ComponentListMF.plank || i == ComponentListMF.plank_cut) {
                return (int) (200 * CustomToolHelper.getBurnModifier(input));
            }
            if (i == ComponentListMF.plank_pane) {
                return (int) (600 * CustomToolHelper.getBurnModifier(input));
            }

            return 0;
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            if (isBurning()) {
                fuel--;
            }
            return;
        }

        ++ticksExisted;
        if (isLit()) {
            if (isWet()) {
                extinguish(Blocks.water, 0);
                return;
            }

            if (fuel > 0) {
                fuel--;
                charcoal += (0.25F / 20F / 60F);
            }

            if (fuel <= 0) {
                setLit(false);
            }
        } else if (fuel > 0 && ticksExisted % 10 == 0) {
            tryLight();
        }
    }

    private boolean isWet() {
        if (isWater(-1, 0, 0) || isWater(1, 0, 0) || isWater(0, 0, -1) || isWater(0, 0, 1) || isWater(0, 1, 0)) {
            return true;
        }

        return worldObj.canLightningStrikeAt(xCoord, yCoord + 1, zCoord);
    }

    public int getCharcoalDrop() {
        return (int) (Math.floor(charcoal));
    }

    public boolean isBurning() {
        return isLit() && fuel > 0;
    }

    public boolean isLit() {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1;
    }

    public void setLit(boolean lit) {
        if (worldObj != null) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lit ? 1 : 0, 2);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        ticksExisted = 0;
    }

    private void tryLight() {
        if (isFire(-1, 0, 0) || isFire(1, 0, 0) || isFire(0, 0, -1) || isFire(0, 0, 1) || isFire(0, -1, 0)
                || isFire(0, 1, 0)) {
            setLit(true);
        }
    }

    private boolean isFire(int x, int y, int z) {
        return worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z).getMaterial() == Material.fire;
    }

    private boolean isWater(int x, int y, int z) {
        return worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z).getMaterial() == Material.water;
    }

    public boolean addFuel(ItemStack input) {
        int amount = getItemBurnTime(input);
        if (amount > 0 && fuel < maxFuel) {
            fuel += amount;
            if (fuel > maxFuel) {
                fuel = maxFuel;
            }
            return true;
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("fuel", fuel);
        nbt.setInteger("ticksExisted", ticksExisted);
        nbt.setFloat("charcoal", charcoal);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        setLit(nbt.getBoolean("isLit"));
        fuel = nbt.getInteger("fuel");
        ticksExisted = nbt.getInteger("ticksExisted");
        charcoal = nbt.getFloat("charcoal");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("fuel", fuel);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        fuel = packet.func_148857_g().getInteger("fuel");
    }

    @Override
    public boolean canPlaceAbove() {
        return true;
    }

    @Override
    public int getHeat() {
        if (!isBurning()) {
            return 0;
        }
        return Functions.getIntervalWave1_i(ticksExisted, 30, 100, 200);// 100*-300*
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldShowMetre() {
        return fuel > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getMetreScale(int width) {
        return this.fuel * width / (maxFuel);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getLocalisedName() {
        int seconds = (int) Math.floor(fuel / 20);
        int mins = (int) Math.floor(seconds / 60);
        seconds -= mins * 60;

        String s = "";
        if (seconds < 10) {
            s += "0";
        }

        return "150C " + StatCollector.translateToLocal("forge.fuel.name") + " " + mins + ":" + s + seconds;
    }

    public void extinguish(Block block, int meta) {
        worldObj.playSoundEffect(xCoord + 0.5F, yCoord + 0.25F, zCoord + 0.5F, "random.fizz", 0.4F,
                2.0F + this.rand.nextFloat() * 0.4F);
        worldObj.spawnParticle("largesmoke", xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 0.0D, 0.0D, 0.0D);
        worldObj.spawnParticle("tilecrack_" + block + "_" + meta, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 0.0D,
                0.0D, 0.0D);

        setLit(false);
    }

    public boolean tryCook(EntityPlayer player, ItemStack held) {
        if (held.getItem() instanceof ItemFood) {
            player.swingItem();
            if (worldObj.isRemote) {
                return false;
            }
            ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(held);
            if (result != null) {
                if (rand.nextInt(10) != 0) {
                    return false;
                }
                float chance = 75F;
                if (RPGElements.isSystemActive) {
                    int skill = RPGElements.getLevel(player, SkillList.provisioning);
                    chance += (int) ((float) skill / 4);
                }
                boolean success = (rand.nextFloat() * 100) < chance;
                ItemStack creation = success ? result.copy() : new ItemStack(FoodListMF.burnt_food);
                dropItem(player, creation);
                SkillList.provisioning.addXP(player, success ? 2 : 1);
                return true;
            }
        }
        return false;
    }

    public void dropItem(EntityPlayer player, ItemStack item) {
        EntityItem drop = new EntityItem(worldObj, player.posX, player.posY, player.posZ, item);
        drop.delayBeforeCanPickup = 0;
        drop.motionX = drop.motionY = drop.motionZ = 0;
        worldObj.spawnEntityInWorld(drop);
    }

    public boolean hasBlockAbove() {
        if (worldObj == null)
            return false;

        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        if (tile != null && tile instanceof IHeatUser) {
            return ((IHeatUser) tile).canAccept(this);
        }

        return false;
    }
}
