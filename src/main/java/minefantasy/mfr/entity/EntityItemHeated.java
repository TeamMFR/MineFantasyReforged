package minefantasy.mfr.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemHeated extends EntityItem {

	public EntityItemHeated(World world) {
		super(world);
	}

	public EntityItemHeated(World world, EntityItem parent) {
		super(world, parent.posX, parent.posY, parent.posZ, parent.getItem());
		this.mimicSpeed(parent);
		setPickupDelay(parent.pickupDelay);
		isImmuneToFire = true;
	}

	public EntityItemHeated(World world, double posX, double posY, double posZ, ItemStack stack) {
		super(world, posX, posY, posZ, stack);
		isImmuneToFire = true;
	}

	public void mimicSpeed(EntityItem parent) {
		this.motionX = parent.motionX;
		this.motionY = parent.motionY;
		this.motionZ = parent.motionZ;
	}

	@Override
	protected void dealFireDamage(int amount) { }

	@Override
	public boolean canRenderOnFire() {
		return false;
	}
}
