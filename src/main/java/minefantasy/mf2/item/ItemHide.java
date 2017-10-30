package minefantasy.mf2.item;

import minefantasy.mf2.api.heating.IQuenchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

public class ItemHide extends ItemComponentMF {
    private final Item result;
    private float hardness;
    private Random rand = new Random();

    public ItemHide(String name, Item result, float hardness) {
        super(name, -1);
        this.result = result;
        this.hardness = hardness;
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

                if (isWaterSource(world, i, j, k)) {
                    tryClean(item, world, player);
                }
            }

            return item;
        }
    }

    private void tryClean(ItemStack item, World world, EntityPlayer player) {
        player.swingItem();
        if (!world.isRemote) {
            world.playSoundAtEntity(player, "random.splash", 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
            if (rand.nextFloat() * 2 * hardness < 1.0F) {
                item.stackSize--;
                EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ,
                        new ItemStack(result));
                world.spawnEntityInWorld(resultItem);
            }
        }
    }

    private boolean isWaterSource(World world, int i, int j, int k) {
        if (world.getBlock(i, j, k).getMaterial() == Material.water) {
            return true;
        }
        if (isCauldron(world, i, j, k)) {
            return true;
        }
        if (isTrough(world, i, j, k)) {
            return true;
        }
        return false;
    }

    public boolean isTrough(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile != null && tile instanceof IQuenchBlock;
    }

    public boolean isCauldron(World world, int x, int y, int z) {
        return world.getBlock(x, y, z) == Blocks.cauldron && world.getBlockMetadata(x, y, z) > 0;
    }
}
