package minefantasy.mfr.registry.recipe;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class KitchenBenchCraftMatrix extends InventoryCrafting {
	private IKitchenBench crafter;

	public KitchenBenchCraftMatrix(IKitchenBench crafter, Container instance, int xSize, int ySize) {
		super(instance, xSize, ySize);
		this.crafter = crafter;
	}
}
