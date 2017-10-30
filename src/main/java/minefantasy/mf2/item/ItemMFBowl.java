package minefantasy.mf2.item;

import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class ItemMFBowl extends ItemComponentMF {
    private Random rand = new Random();

    public ItemMFBowl(String name) {
        super(name, 0);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            return super.onItemRightClick(item, world, player);
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k)) {
                    return super.onItemRightClick(item, world, player);
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {
                    return super.onItemRightClick(item, world, player);
                }

                if (isWaterSource(world, i, j, k)) {
                    gather(item, world, player);
                    return item;
                }
            }
        }
        return super.onItemRightClick(item, world, player);
    }

    private void gather(ItemStack item, World world, EntityPlayer player) {
        player.swingItem();
        if (!world.isRemote) {
            world.playSoundAtEntity(player, "random.splash", 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
            item.stackSize--;
            EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ,
                    new ItemStack(FoodListMF.bowl_water_salt));
            world.spawnEntityInWorld(resultItem);
        }
    }

    private boolean isWaterSource(World world, int i, int j, int k) {
        if (world.getBlock(i, j, k).getMaterial() != Material.water) {
            return false;
        }

        BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (biome == BiomeGenBase.ocean || biome == BiomeGenBase.deepOcean || biome == BiomeGenBase.beach) {
            return true;
        }
        MFLogUtil.logDebug("Biome = " + biome.biomeName);
        if (world.getBlock(i, j - 1, k) == Blocks.sand) {
            return true;
        }
        return false;
    }
}
