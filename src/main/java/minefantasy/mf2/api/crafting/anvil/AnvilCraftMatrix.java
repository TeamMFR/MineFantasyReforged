package minefantasy.mf2.api.crafting.anvil;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class AnvilCraftMatrix extends InventoryCrafting {
    private IAnvil anvil;

    public AnvilCraftMatrix(IAnvil anvil, Container instance, int xSize, int ySize) {
        super(instance, xSize, ySize);
        this.anvil = anvil;
    }

    public void modifyTier(int hammerTier, int anvilTier, int craftTime) {
        anvil.setHammerUsed(hammerTier);
        anvil.setRequiredAnvil(anvilTier);
        anvil.setForgeTime(craftTime);
    }

    public void modifyResearch(String name) {
        anvil.setResearch(name);
    }

    public int getTier() {
        return anvil.getRecipeHammer();
    }

    public int getAnvilTier() {
        return anvil.getRecipeAnvil();
    }

}
