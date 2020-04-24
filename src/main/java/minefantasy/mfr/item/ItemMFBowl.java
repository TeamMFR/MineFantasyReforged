package minefantasy.mfr.item;

import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;

import java.util.Random;

public class ItemMFBowl extends ItemComponentMFR {
    private Random rand = new Random();

    public ItemMFBowl(String name) {
        super(name, 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

        if (rayTraceResult == null) {
            return super.onItemRightClick(world, player, hand);
        } else {
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

                if (!world.canMineBlockBody(player, rayTraceResult.getBlockPos())) {
                    return super.onItemRightClick(world, player, hand);
                }

                if (!player.canPlayerEdit(rayTraceResult.getBlockPos(), rayTraceResult.sideHit, item)) {
                    return super.onItemRightClick(world, player, hand);
                }

                if (isWaterSource(world, rayTraceResult.getBlockPos())) {
                    gather(item, world, player);
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    private void gather(ItemStack item, World world, EntityPlayer player) {
        player.swingArm(player.getActiveHand());
        if (!world.isRemote) {
            world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT,0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
            item.shrink(1);
            EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ,
                    new ItemStack(FoodListMFR.bowl_water_salt));
            world.spawnEntity(resultItem);
        }
    }

    private boolean isWaterSource(World world, BlockPos pos) {
        if (world.getBlockState(pos).getMaterial() != Material.WATER) {
            return false;
        }

        Biome biome = world.getBiome(pos);
        if (biome == Biome.getBiome(0) || biome == Biome.getBiome(24) || biome == Biome.getBiome(16)) {
            return true;
        }
        MFRLogUtil.logDebug("Biome = " + biome.toString());
        if (world.getBlockState(pos.add(0,-1,0)) == Blocks.SAND) {
            return true;
        }
        return false;
    }
}
