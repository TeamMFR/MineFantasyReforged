package minefantasy.mf2.integration.minetweaker.helpers;

import minefantasy.mf2.api.refine.Alloy;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TweakedAlloyRecipe extends Alloy {

    public TweakedAlloyRecipe(IItemStack output, int requiredLevel, List<?> items) {
        super(MineTweakerMC.getItemStack(output), requiredLevel, items);
    }

    @Override
    public boolean matches(ItemStack[] inv) {
        ArrayList check = new ArrayList(recipeItems);
        ArrayList check2 = new ArrayList(recipeItems);
        for (ItemStack stack : inv) {
            if (stack != null) {
                boolean matches = false;
                Iterator it = check.iterator();
                while (it.hasNext()) {
                    IIngredient ingred = (IIngredient) it.next();
                    for (IItemStack i : ingred.getItems()) {
                        ItemStack is = MineTweakerMC.getItemStack(i);
                        if (stack.isItemEqual(is) && (is.getItemDamage() == OreDictionary.WILDCARD_VALUE
                                || is.getItemDamage() == stack.getItemDamage())) {
                            matches = true;
                            check2.remove(ingred);
                            break;
                        }
                        if (areBothCarbon(is, stack)) {
                            matches = true;
                            check2.remove(ingred);
                            break;
                        }
                    }
                    if (matches)
                        break;
                }
                if (!matches)
                    return false;
            }
        }
        boolean empty = true;
        for (Object o : check2) {
            if (o != null)
                empty = false;
        }
        return empty;
    }

}
