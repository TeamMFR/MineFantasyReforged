package minefantasy.mfr.util;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtils {

	public static void dropItemsInWorld(World world, IItemHandler handler, BlockPos pos) {
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			dropItemInWorld(world, handler.getStackInSlot(slot), pos);
		}
	}

	/*
	 * drops the input itemstack into the world at the input position
	 */
	public static void dropItemInWorld(World world, ItemStack item, BlockPos pos) {
		dropItemInWorld(world, item, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void dropItemInWorld(World world, ItemStack item, double x, double y, double z) {
		if (world.isRemote) {
			return;
		}
		InventoryHelper.spawnItemStack(world, x, y, z, item);
	}
}
