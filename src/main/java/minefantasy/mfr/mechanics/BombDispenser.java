package minefantasy.mfr.mechanics;

import minefantasy.mfr.entity.EntityBomb;
import minefantasy.mfr.entity.EntityMine;
import minefantasy.mfr.item.gadget.ItemBomb;
import minefantasy.mfr.item.gadget.ItemMine;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BombDispenser implements IBehaviorDispenseItem {

    @Override
    public ItemStack dispense(IBlockSource dispenser, ItemStack item) {
        IPosition direction = BlockDispenser.getDispensePosition(dispenser);
        // BlockDispenser
        if (item.isEmpty())
            return ItemStack.EMPTY;

        if (item.getItem() instanceof ItemBomb) {
            ItemBomb itembomb = (ItemBomb) item.getItem();
            World world = dispenser.getWorld();

            double posX = dispenser.getX() + (direction.getX() / 2F);
            double posY = dispenser.getY() + (direction.getY() / 2F);
            double posZ = dispenser.getZ() + (direction.getY() / 2F);

            double xVelocity = +direction.getX();
            double yVelocity = +direction.getY();
            double zVelocity = +direction.getZ();
            float velocityModifier = 1.5F;

            if (!world.isRemote) {
                EntityBomb bomb = new EntityBomb(world).setType(ItemBomb.getFilling(item), ItemBomb.getCasing(item),
                        ItemBomb.getFuse(item), ItemBomb.getPowder(item));
                bomb.setPosition(posX, posY, posZ);
                bomb.setThrowableHeading(xVelocity, yVelocity, zVelocity, 1.0F, velocityModifier);
                world.spawnEntity(bomb);
                if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
                    bomb.getEntityData().setBoolean("stickyBomb", true);
                }
            }
            item.splitStack(1);
        }

        if (item.getItem() instanceof ItemMine) {
            ItemMine itembomb = (ItemMine) item.getItem();
            World world = dispenser.getWorld();

            double posX = dispenser.getX() + (direction.getX() / 2F);
            double posY = dispenser.getY() + (direction.getY() / 2F);
            double posZ = dispenser.getZ() + (direction.getZ() / 2F);

            double xVelocity = +direction.getX();
            double yVelocity = +direction.getY();
            double zVelocity = +direction.getZ();
            float velocityModifier = 0.5F;

            if (!world.isRemote) {
                EntityMine bomb = new EntityMine(world).setType(ItemBomb.getFilling(item), ItemBomb.getCasing(item), ItemBomb.getFuse(item), ItemBomb.getPowder(item));
                bomb.setPosition(posX, posY, posZ);
                bomb.setThrowableHeading(xVelocity, yVelocity, zVelocity, 1.0F, velocityModifier);
                world.spawnEntity(bomb);
                if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
                    bomb.getEntityData().setBoolean("stickyBomb", true);
                }
            }
            item.splitStack(1);
        }

        return item;
    }

}
