package minefantasy.mfr.mechanics;

import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.item.ItemArrowMFR;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MFArrowDispenser extends BehaviorDefaultDispenseItem {
    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack dispenseStack(IBlockSource dispenser, ItemStack item) {
        World world = dispenser.getWorld();
        IPosition direction = BlockDispenser.getDispensePosition(dispenser);
        double posX = dispenser.getX() + (direction.getX());
        double posY = dispenser.getY() + (direction.getY());
        double posZ = dispenser.getZ() + (direction.getZ());

        if (item.getItem() instanceof ItemArrowMFR) {
            ItemArrowMFR arrowitem = (ItemArrowMFR) item.getItem();
            EntityArrowMFR arrow = arrowitem.getFiredArrow(new EntityArrowMFR(world, posX, posY, posZ), item);
            arrow.shoot(direction.getX(), direction.getY() + 0.1F, direction.getZ(), this.getPower(), this.getSpread());
            world.spawnEntity(arrow);
            arrow.canBePickedUp = 1;
            item.splitStack(1);
        }
        return item;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void playDispenseSound(IBlockSource source) {
        source.getWorld().playEvent(1002, source.getBlockPos(), 0);
    }

    protected float getSpread() {
        return 6.0F;
    }

    protected float getPower() {
        return 1.1F;
    }
}