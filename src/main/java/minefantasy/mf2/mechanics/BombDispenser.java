package minefantasy.mf2.mechanics;

import minefantasy.mf2.entity.EntityBomb;
import minefantasy.mf2.entity.EntityMine;
import minefantasy.mf2.item.gadget.ItemBomb;
import minefantasy.mf2.item.gadget.ItemMine;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BombDispenser implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource dispenser, ItemStack item) {
        EnumFacing direction = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
        // BlockDispenser
        if (item == null)
            return null;

        if (item.getItem() instanceof ItemBomb) {
            ItemBomb itembomb = (ItemBomb) item.getItem();
            World world = dispenser.getWorld();

            double posX = dispenser.getX() + (direction.getFrontOffsetX() / 2F);
            double posY = dispenser.getY() + (direction.getFrontOffsetY() / 2F);
            double posZ = dispenser.getZ() + (direction.getFrontOffsetZ() / 2F);

            double xVelocity = +direction.getFrontOffsetX();
            double yVelocity = +direction.getFrontOffsetY();
            double zVelocity = +direction.getFrontOffsetZ();
            float velocityModifier = 1.5F;

            if (!world.isRemote) {
                EntityBomb bomb = new EntityBomb(world).setType(ItemBomb.getFilling(item), ItemBomb.getCasing(item),
                        ItemBomb.getFuse(item), ItemBomb.getPowder(item));
                bomb.setPosition(posX, posY, posZ);
                bomb.setThrowableHeading(xVelocity, yVelocity, zVelocity, 1.0F, velocityModifier);
                world.spawnEntityInWorld(bomb);
                if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
                    bomb.getEntityData().setBoolean("stickyBomb", true);
                }
            }
            item.splitStack(1);
        }

        if (item.getItem() instanceof ItemMine) {
            ItemMine itembomb = (ItemMine) item.getItem();
            World world = dispenser.getWorld();

            double posX = dispenser.getX() + (direction.getFrontOffsetX() / 2F);
            double posY = dispenser.getY() + (direction.getFrontOffsetY() / 2F);
            double posZ = dispenser.getZ() + (direction.getFrontOffsetZ() / 2F);

            double xVelocity = +direction.getFrontOffsetX();
            double yVelocity = +direction.getFrontOffsetY();
            double zVelocity = +direction.getFrontOffsetZ();
            float velocityModifier = 0.5F;

            if (!world.isRemote) {
                EntityMine bomb = new EntityMine(world).setType(ItemBomb.getFilling(item), ItemBomb.getCasing(item),
                        ItemBomb.getFuse(item), ItemBomb.getPowder(item));
                bomb.setPosition(posX, posY, posZ);
                bomb.setThrowableHeading(xVelocity, yVelocity, zVelocity, 1.0F, velocityModifier);
                world.spawnEntityInWorld(bomb);
                if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
                    bomb.getEntityData().setBoolean("stickyBomb", true);
                }
            }
            item.splitStack(1);
        }

        return item;
    }

}
