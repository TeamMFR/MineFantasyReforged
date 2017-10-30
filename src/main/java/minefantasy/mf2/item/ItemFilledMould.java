package minefantasy.mf2.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.heating.TongsHelper;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class ItemFilledMould extends ItemComponentMF {

    private static final String itemNBT = "MF_HeldItem";

    public ItemFilledMould() {
        super("ingot_mould_filled");
        // setMaxStackSize(1);
        this.setUnlocalizedName("ingot_mould");
    }

    public static ItemStack createMould(ItemStack fill) {
        ItemStack mould = new ItemStack(ComponentListMF.ingot_mould_filled);
        NBTTagCompound nbt = getOrCreateNBT(mould);
        NBTTagCompound save = new NBTTagCompound();
        fill.writeToNBT(save);
        nbt.setTag(itemNBT, save);
        return mould;
    }

    public static NBTTagCompound getOrCreateNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    public ItemStack getHeldItem(ItemStack item) {
        NBTTagCompound nbt = getOrCreateNBT(item);
        if (nbt.hasKey(itemNBT)) {
            return ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(itemNBT));
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        ItemStack held = getHeldItem(item);
        if (held != null) {
            list.add(held.getDisplayName());
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            return item;
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k)) {
                    return item;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {
                    return item;
                }

                float water = TongsHelper.getWaterSource(world, i, j, k);
                ItemStack drop = getHeldItem(item);

                if (drop != null && water >= 0) {
                    player.playSound("random.splash", 1F, 1F);
                    player.playSound("random.fizz", 2F, 0.5F);

                    for (int a = 0; a < 5; a++) {
                        world.spawnParticle("largesmoke", i + 0.5F, j + 1, k + 0.5F, 0, 0.065F, 0);
                    }

                    if (!world.isRemote) {
                        ItemStack mould = new ItemStack(ComponentListMF.ingot_mould);
                        if (!world.getBlock(i, j + 1, k).getMaterial().isSolid()) {
                            dropItem(world, i, j + 1, k, drop);
                            dropItem(world, i, j + 1, k, mould);
                        } else {
                            dropItem(world, player.posX, player.posY, player.posZ, drop, false);
                            dropItem(world, player.posX, player.posY, player.posZ, mould, false);
                        }
                    }

                    --item.stackSize;
                }
            }

            return item;
        }
    }

    private void dropItem(World world, int i, int j, int k, ItemStack drop) {
        dropItem(world, i + 0.5, j + 0.5, k + 0.5, drop, true);
    }

    private void dropItem(World world, double i, double j, double k, ItemStack drop, boolean delay) {
        EntityItem entity = new EntityItem(world, i, j, k, drop);
        if (delay)
            entity.delayBeforeCanPickup = 20;
        entity.motionX = entity.motionY = entity.motionZ = 0F;
        world.spawnEntityInWorld(entity);
    }

}
