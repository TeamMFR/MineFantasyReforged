package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.carpenter.CraftingManagerCarpenter;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.integration.minetweaker.helpers.TweakedShapedCBRecipes;
import minefantasy.mf2.integration.minetweaker.helpers.TweakedShapelessCBRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.CarpenterBench")
public class CarpentersBench {

    @ZenMethod
    public static void addShapedRecipe(@NotNull IItemStack output, String skill, String research, String sound,
                                       double exp, String tool, int hammer, int anvil, int time, IIngredient[][] ingreds) {
        MineTweakerAPI.apply(new CarpentersAction(output, RPGElements.getSkillByName(skill), research, sound,
                (float) exp, tool, hammer, anvil, time, ingreds));
    }

    @ZenMethod
    public static void addShapelessRecipe(@NotNull IItemStack output, String skill, String research, String sound,
                                          double exp, String tool, int hammer, int anvil, int time, IIngredient[] ingreds) {
        MineTweakerAPI.apply(new CarpentersAction(output, RPGElements.getSkillByName(skill), research, sound,
                (float) exp, tool, hammer, anvil, time, ingreds));
    }

    public static class CarpentersAction implements IUndoableAction {

        IItemStack out;
        Skill s;
        String research, tool, sound;
        float exp;
        int hammer, anvil, time;
        IIngredient[][] ingreds;
        IIngredient[] ingreds2;
        boolean shaped;
        ICarpenterRecipe r;

        // private static final char[] chars =
        // {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','recipe','s','t','u','v','w','x','y','z'};

        public CarpentersAction(IItemStack out, Skill s, String research, String sound, float exp, String tool,
                                int hammer, int anvil, int time, IIngredient[][] ingreds) {
            this.out = out;
            this.s = s;
            this.research = research;
            this.tool = tool;
            this.sound = sound;
            this.exp = exp;
            this.hammer = hammer;
            this.anvil = anvil;
            this.time = time;
            this.ingreds = ingreds;
            this.shaped = true;
            r = new TweakedShapedCBRecipes(ingreds, out, tool, time, hammer, anvil, exp, sound, research, s);
        }

        public CarpentersAction(IItemStack out, Skill s, String research, String sound, float exp, String tool,
                                int hammer, int anvil, int time, IIngredient[] ingreds) {
            this.out = out;
            this.s = s;
            this.research = research;
            this.tool = tool;
            this.sound = sound;
            this.exp = exp;
            this.hammer = hammer;
            this.anvil = anvil;
            this.time = time;
            this.ingreds2 = ingreds;
            this.shaped = false;
            r = new TweakedShapelessCBRecipes(ingreds2, out, tool, time, hammer, anvil, exp, sound, research, s);
        }

        @Override
        public void apply() {
            CraftingManagerCarpenter.getInstance().recipes.add(r);
        }

        @Override
        public String describe() {
            return "Adding Carpenters Bench Recipe...";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describeUndo() {
            return "Undoing Carpenters Recipe";
        }

        @Override
        public void undo() {
            CraftingManagerCarpenter.getInstance().recipes.remove(r);
        }

    }

}
