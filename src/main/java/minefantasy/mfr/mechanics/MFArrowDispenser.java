package minefantasy.mfr.mechanics;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public class MFArrowDispenser extends BehaviorDefaultDispenseItem {
	/**
	 * Dispense the specified stack, play the dispense sound and spawn particles.
	 */
	public ItemStack dispenseStack(IBlockSource dispenser, ItemStack item) {
		//TODO fix shooting MFR arrows from dispensers
//		World world = dispenser.getWorld();
//		IPosition direction = BlockDispenser.getDispensePosition(dispenser);
//		double posX = dispenser.getX() + (direction.getX());
//		double posY = dispenser.getY() + (direction.getY());
//		double posZ = dispenser.getZ() + (direction.getZ());
//
//		if (item.getItem() instanceof ItemArrowMFR) {
//			ItemArrowMFR arrowitem = (ItemArrowMFR) item.getItem();
//			EntityArrowMFR arrow = arrowitem.createArrow(new EntityArrowMFR(world, posX, posY, posZ), item);
//			arrow.shoot(direction.getX(), direction.getY() + 0.1F, direction.getZ(), this.getPower(), this.getSpread());
//			world.spawnEntity(arrow);
//			arrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
//			item.splitStack(1);
//		}
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