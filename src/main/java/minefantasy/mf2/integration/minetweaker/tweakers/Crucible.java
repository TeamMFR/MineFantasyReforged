package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.refine.Alloy;
import minefantasy.mf2.api.refine.AlloyRecipes;
import minefantasy.mf2.integration.minetweaker.helpers.TweakedAlloyRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.minefantasy.Crucible")
public class Crucible {

    @ZenMethod
    public static void addAlloy(IItemStack out, @Optional int level, int dupe, IIngredient[] ingred) {
        MineTweakerAPI.apply(new AddAlloyAction(out, level, ingred, dupe));
    }

    private static class AddAlloyAction extends OneWayAction {

        private Alloy alloy;
        private IItemStack out;
        private int level;
        private List<IIngredient> ingreds;
        private int dupe;

        public AddAlloyAction(IItemStack out, int level, IIngredient[] ingreds, int dupe) {
            this.out = out;
            this.level = level;
            this.ingreds = new ArrayList<IIngredient>();
            this.dupe = dupe;
            for (IIngredient i : ingreds) {
                this.ingreds.add(i);
            }
            alloy = new TweakedAlloyRecipe(out, level, this.ingreds);
        }

        @Override
        public void apply() {
            AlloyRecipes.addAlloy(alloy);
        }

        @Override
        public String describe() {
            return "Adding Custom Alloy";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

}
