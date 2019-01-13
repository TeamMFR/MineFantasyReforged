package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.integration.minetweaker.helpers.TweakedShapedAnvilRecipe;
import minefantasy.mf2.integration.minetweaker.helpers.TweakedShapelessAnvilRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Anvil")
public class Anvil {

    @ZenMethod
    public static void addShapedRecipe(@NotNull IItemStack output, String skill, String research, boolean hot,
                                       String tool, int hammer, int anvil, int time, IIngredient[][] ingreds) {
        MineTweakerAPI.apply(new AnvilAction(output, RPGElements.getSkillByName(skill), research, hot,
                tool, hammer, anvil, time, ingreds));
    }

    @ZenMethod
    public static void addShapelessRecipe(@NotNull IItemStack output, String skill, String research, boolean hot,
                                          String tool, int hammer, int anvil, int time, IIngredient[] ingreds) {
        MineTweakerAPI.apply(new AnvilAction(output, RPGElements.getSkillByName(skill), research, hot,
                tool, hammer, anvil, time, ingreds));
    }

    public static class AnvilAction implements IUndoableAction {

        IItemStack output;
        Skill s;
        String research, tool;
        boolean hot;
        int hammer, anvil, time;
        IIngredient[][] ingreds;
        IIngredient[] ingreds2;
        boolean shaped;
        IAnvilRecipe recipe;

        public AnvilAction(IItemStack out, Skill s, String research, boolean hot, String tool, int hammer,
                           int anvil, int time, IIngredient[][] ingreds) {
            this.output = out;
            this.s = s;
            this.research = research;
            this.tool = tool;
            this.hot = hot;
            this.hammer = hammer;
            this.anvil = anvil;
            this.time = time;
            this.ingreds = ingreds;
            this.shaped = true;
            recipe = new TweakedShapedAnvilRecipe(ingreds, out, tool, time, hammer, anvil, hot, research, s);
        }

        public AnvilAction(IItemStack out, Skill s, String research, boolean hot, String tool, int hammer,
                           int anvil, int time, IIngredient[] ingreds) {
            this.output = out;
            this.s = s;
            this.research = research;
            this.tool = tool;
            this.hot = hot;
            this.hammer = hammer;
            this.anvil = anvil;
            this.time = time;
            this.ingreds2 = ingreds;
            this.shaped = false;
            recipe = new TweakedShapelessAnvilRecipe(ingreds2, out, tool, time, hammer, anvil, hot, research, s);
        }

        @Override
        public void apply() {
            CraftingManagerAnvil.getInstance().recipes.add(recipe);
        }

        @Override
        public String describe() {
            return "Adding a " + (hot ? "hot" : "") + " Anvil Recipe resulting in " + MineTweakerMC.getItemStack(output);
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
            return "Undoing Anvil Recipe";
        }

        @Override
        public void undo() {
            CraftingManagerAnvil.getInstance().recipes.remove(recipe);
        }
    }
}