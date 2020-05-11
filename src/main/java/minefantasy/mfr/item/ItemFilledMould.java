package minefantasy.mfr.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemFilledMould extends ItemComponentMFR {

    private static final String itemNBT = "MF_HeldItem";

    public ItemFilledMould() {
        super("ingot_mould_filled");
        // setMaxStackSize(1);
        this.setUnlocalizedName("ingot_mould");
    }

    public static ItemStack createMould(ItemStack fill) {
        ItemStack mould = new ItemStack(ComponentListMFR.INGOT_MOULD_FILLED);
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
            return new ItemStack(nbt.getCompoundTag(itemNBT));
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        ItemStack held = getHeldItem(item);
        if (held != null) {
            list.add(held.getDisplayName());
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

        if (rayTraceResult == null) {
            return ActionResult.newResult(EnumActionResult.PASS, item);
        } else {
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

                if (!world.canMineBlockBody(player, rayTraceResult.getBlockPos())) {
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }

                if (!player.canPlayerEdit(rayTraceResult.getBlockPos(), rayTraceResult.sideHit, item)) {
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }

                float water = TongsHelper.getWaterSource(world, rayTraceResult.getBlockPos());
                ItemStack drop = getHeldItem(item);

                if (drop != null && water >= 0) {
                    player.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1F, 1F);
                    player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 2F, 0.5F);

                    for (int a = 0; a < 5; a++) {
                        world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, rayTraceResult.getBlockPos().getX() + 0.5F, rayTraceResult.getBlockPos().getY() + 1, rayTraceResult.getBlockPos().getZ() + 0.5F, 0, 0.065F, 0);
                    }

                    if (!world.isRemote) {
                        ItemStack mould = new ItemStack(ComponentListMFR.INGOT_MOULD);
                        if (!world.getBlockState(rayTraceResult.getBlockPos().add(0,1,0)).getMaterial().isSolid()) {
                            dropItem(world, rayTraceResult.getBlockPos().add(0,1,0), drop);
                            dropItem(world, rayTraceResult.getBlockPos().add(0,1,0), mould);
                        } else {
                            dropItem(world, player.posX, player.posY, player.posZ, drop, false);
                            dropItem(world, player.posX, player.posY, player.posZ, mould, false);
                        }
                    }

                    item.shrink(1);
                }
            }

            return ActionResult.newResult(EnumActionResult.FAIL, item);
        }
    }

    private void dropItem(World world, BlockPos pos, ItemStack drop) {
        dropItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop, true);
    }

    private void dropItem(World world, double i, double j, double k, ItemStack drop, boolean delay) {
        EntityItem entity = new EntityItem(world, i, j, k, drop);
        if (delay)
            entity.setPickupDelay(20);
        entity.motionX = entity.motionY = entity.motionZ = 0F;
        world.spawnEntity(entity);
    }

}
