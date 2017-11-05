package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.integration.minetweaker.TweakedShapedAnvilRecipe;
import minefantasy.mf2.integration.minetweaker.TweakedShapelessAnvilRecipe;
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
                                       double exp, String tool, int hammer, int anvil, int time, IIngredient[][] ingreds) {
        MineTweakerAPI.apply(new AnvilAction(output, RPGElements.getSkillByName(skill), research, hot, (float) exp,
                tool, hammer, anvil, time, ingreds));
    }

    @ZenMethod
    public static void addShapelessRecipe(@NotNull IItemStack output, String skill, String research, boolean hot,
                                          double exp, String tool, int hammer, int anvil, int time, IIngredient[] ingreds) {
        MineTweakerAPI.apply(new AnvilAction(output, RPGElements.getSkillByName(skill), research, hot, (float) exp,
                tool, hammer, anvil, time, ingreds));
    }

    public static class AnvilAction implements IUndoableAction {

        IItemStack out;
        Skill s;
        String research, tool;
        boolean hot;
        float exp;
        int hammer, anvil, time;
        IIngredient[][] ingreds;
        IIngredient[] ingreds2;
        boolean shaped;
        IAnvilRecipe r;

        // private static final char[] chars =
        // {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        public AnvilAction(IItemStack out, Skill s, String research, boolean hot, float exp, String tool, int hammer,
                           int anvil, int time, IIngredient[][] ingreds) {
            this.out = out;
            this.s = s;
            this.research = research;
            this.tool = tool;
            this.hot = hot;
            this.exp = exp;
            this.hammer = hammer;
            this.anvil = anvil;
            this.time = time;
            this.ingreds = ingreds;
            this.shaped = true;
            r = new TweakedShapedAnvilRecipe(ingreds, out, tool, time, hammer, anvil, exp, hot, research, s);
        }

        public AnvilAction(IItemStack out, Skill s, String research, boolean hot, float exp, String tool, int hammer,
                           int anvil, int time, IIngredient[] ingreds) {
            this.out = out;
            this.s = s;
            this.research = research;
            this.tool = tool;
            this.hot = hot;
            this.exp = exp;
            this.hammer = hammer;
            this.anvil = anvil;
            this.time = time;
            this.ingreds2 = ingreds;
            this.shaped = false;
            r = new TweakedShapelessAnvilRecipe(ingreds2, out, tool, time, hammer, anvil, exp, hot, research, s);
        }

        @Override
        public void apply() {
            CraftingManagerAnvil.getInstance().recipes.add(r);
        }

        @Override
        public String describe() {
            return "Adding a " + (hot ? "hot" : "") + " Anvil Recipe resulting in " + MineTweakerMC.getItemStack(out);
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
            CraftingManagerAnvil.getInstance().recipes.remove(r);
        }
    }
}