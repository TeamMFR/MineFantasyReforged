package minefantasy.mfr.recipe;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class AnvilCraftMatrix extends InventoryCrafting {
    private IAnvil anvil;

    public AnvilCraftMatrix(IAnvil anvil, Container instance, int width, int height) {
        super(instance, width, height);
        this.anvil = anvil;
    }

    public void modifyTier(int hammerTier, int anvilTier, int craftTime) {
        anvil.setRequiredHammerTier(hammerTier);
        anvil.setRequiredAnvilTier(anvilTier);
        anvil.setProgressMax(craftTime);
    }

    public void modifyResearch(String name) {
        anvil.setRequiredResearch(name);
    }

    public int getTier() {
        return anvil.getRequiredHammerTier();
    }

}
