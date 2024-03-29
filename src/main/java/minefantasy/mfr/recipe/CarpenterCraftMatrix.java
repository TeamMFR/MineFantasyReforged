package minefantasy.mfr.recipe;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class CarpenterCraftMatrix extends InventoryCrafting {
	private ICarpenter crafter;

	public CarpenterCraftMatrix(ICarpenter crafter, Container instance, int xSize, int ySize) {
		super(instance, xSize, ySize);
		this.crafter = crafter;
	}

	public void modifyTier(int hammerTier, int craftTime) {
		crafter.setRequiredToolTier(hammerTier);
		crafter.setProgressMax(craftTime);
	}

}
