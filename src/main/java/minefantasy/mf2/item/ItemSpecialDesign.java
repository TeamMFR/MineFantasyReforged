package minefantasy.mf2.item;

import minefantasy.mf2.api.crafting.exotic.ISpecialCraftItem;
import net.minecraft.item.ItemStack;

public class ItemSpecialDesign extends ItemComponentMF implements ISpecialCraftItem {
    private String design;

    public ItemSpecialDesign(String name, int rarity, String design) {
        super(name, rarity);
        this.design = design;
    }

    @Override
    public String getDesign(ItemStack item) {
        return this.design;
    }

}
