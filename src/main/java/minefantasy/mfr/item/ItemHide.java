package minefantasy.mfr.item;

import minefantasy.mfr.api.heating.IQuenchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class ItemHide extends ItemComponentMFR {
    private final Item result;
    private float hardness;
    private Random rand = new Random();

    public ItemHide(String name, Item result, float hardness) {
        super(name, -1);
        this.result = result;
        this.hardness = hardness;
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

                if (!player.canPlayerEdit( rayTraceResult.getBlockPos(), rayTraceResult.sideHit, item)) {
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }

                if (isWaterSource(world,  rayTraceResult.getBlockPos())) {
                    tryClean(item, world, player, rayTraceResult.getBlockPos());
                }
            }

            return ActionResult.newResult(EnumActionResult.FAIL, item);
        }
    }

    private void tryClean(ItemStack item, World world, EntityPlayer player, BlockPos pos) {
        player.swingArm(player.swingingHand);
        if (!world.isRemote) {
            world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
            if (rand.nextFloat() * 2 * hardness < 1.0F) {
                item.shrink(1);
                EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(result));
                world.spawnEntity(resultItem);
            }
        }
    }

    private boolean isWaterSource(World world, BlockPos pos) {
        if (world.getBlockState(pos).getMaterial() == Material.WATER) {
            return true;
        }
        if (isCauldron(world,pos)) {
            return true;
        }
        if (isTrough(world, pos)) {
            return true;
        }
        return false;
    }

    public boolean isTrough(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        return tile != null && tile instanceof IQuenchBlock;
    }

    public boolean isCauldron(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.CAULDRON;
    }
}
