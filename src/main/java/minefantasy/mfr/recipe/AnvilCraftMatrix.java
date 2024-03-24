package minefantasy.mfr.recipe;

import minefantasy.mfr.tile.TileEntityAnvil;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class AnvilCraftMatrix extends InventoryCrafting {
	private final TileEntityAnvil anvil;

	public AnvilCraftMatrix(TileEntityAnvil anvil, Container instance, int width, int height) {
		super(instance, width, height);
		this.anvil = anvil;
	}

	public void modifyTier(int hammerTier, int anvilTier, int craftTime) {
		anvil.setRequiredToolTier(hammerTier);
		anvil.setRequiredAnvilTier(anvilTier);
		anvil.setProgressMax(craftTime);
	}

	public void modifyResearch(String name) {
		anvil.setRequiredResearch(name);
	}
}
