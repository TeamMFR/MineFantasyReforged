package minefantasy.mfr.block.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.IHeatSource;
import minefantasy.mfr.api.crafting.IHeatUser;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.helpers.Functions;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.FoodListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            if (i == Items.STICK)
                return 600;// 30Sec
            if (i == ComponentListMFR.PLANK || i == ComponentListMFR.PLANK_CUT) {
                return (int) (200 * CustomToolHelper.getBurnModifier(input));
            }
            if (i == ComponentListMFR.PLANK_PANE) {
                return (int) (600 * CustomToolHelper.getBurnModifier(input));
            }

            return 0;
        }
    }

    @Override
    public void markDirty() {
        if (world.isRemote) {
            if (isBurning()) {
                fuel--;
            }
            return;
        }

        ++ticksExisted;
        if (isLit()) {
            if (isWet()) {
                extinguish(Blocks.WATER, 0);
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

        return world.isRainingAt(pos.add(0,1,0));
    }

    public int getCharcoalDrop() {
        return (int) (Math.floor(charcoal));
    }

    public boolean isBurning() {
        return isLit() && fuel > 0;
    }

    public boolean isLit() {
        return Block.getStateId(world.getBlockState(pos)) == this.getBlockMetadata();
    }

    public void setLit(boolean lit) {
        if (world != null) {
            IBlockState litState = world.getBlockState(pos);
            world.setBlockState(pos, litState, 1);
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
        return world.getBlockState(pos.add(x,y,z)).getMaterial() == Material.FIRE;
    }

    private boolean isWater(int x, int y, int z) {
        return world.getBlockState(pos.add(x,y,z)).getMaterial() == Material.WATER;
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
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("fuel", fuel);
        nbt.setInteger("ticksExisted", ticksExisted);
        nbt.setFloat("charcoal", charcoal);
        return nbt;
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
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("fuel", fuel);
        return new SPacketUpdateTileEntity(pos, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        fuel = packet.getNbtCompound().getInteger("fuel");
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

        return "150C " + I18n.format("forge.fuel.name") + " " + mins + ":" + s + seconds;
    }

    public void extinguish(Block block, int meta) {
        world.playSound(pos.getY() + 0.5F, pos.getY() + 0.25F, pos.getZ() + 0.5F, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F, true);
        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

        setLit(false);
    }

    public boolean tryCook(EntityPlayer player, ItemStack held) {
        if (held.getItem() instanceof ItemFood) {
            player.swingArm(EnumHand.MAIN_HAND);
            if (world.isRemote) {
                return false;
            }
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(held);
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
                ItemStack creation = success ? result.copy() : new ItemStack(FoodListMFR.BURNT_FOOD);
                dropItem(player, creation);
                SkillList.provisioning.addXP(player, success ? 2 : 1);
                return true;
            }
        }
        return false;
    }

    public void dropItem(EntityPlayer player, ItemStack item) {
        EntityItem drop = new EntityItem(world, player.posX, player.posY, player.posZ, item);
        drop.setPickupDelay(10);
        drop.motionX = drop.motionY = drop.motionZ = 0;
        world.spawnEntity(drop);
    }

    public boolean hasBlockAbove() {
        if (world == null)
            return false;

        TileEntity tile = world.getTileEntity(pos.add(0,1,0));
        if (tile != null && tile instanceof IHeatUser) {
            return ((IHeatUser) tile).canAccept(this);
        }

        return false;
    }
}
