package minefantasy.mf2.api.crafting.anvil;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

/**
 * @author AnonymousProductions
 */
public class CraftingManagerAnvil {
    /**
     * The static instance of this class
     */
    private static final CraftingManagerAnvil instance = new CraftingManagerAnvil();

    /**
     * A list of all the recipes added
     */
    public List recipes = new ArrayList();

    private CraftingManagerAnvil() {
        Collections.sort(this.recipes, new RecipeSorterAnvil(this));
        System.out.println("MineFantasy: Anvil recipes initiating");
    }

    /**
     * Returns the static instance of this class
     */
    public static CraftingManagerAnvil getInstance() {
        return instance;
    }

    public IAnvilRecipe addRecipe(ItemStack result, Skill skill, String research, boolean hot, String tool,
                                  int hammer, int anvil, int time, Object... input) {
        return addRecipe(result, skill, research, hot, tool, hammer, anvil, time, (byte) 0, input);
    }

    public IAnvilRecipe addToolRecipe(ItemStack result, Skill skill, String research, boolean hot,
                                      String tool, int hammer, int anvil, int time, Object... input) {
        return addRecipe(result, skill, research, hot, tool, hammer, anvil, time, (byte) 1, input);
    }

    /**
     * Adds a recipe. See spreadsheet on first page for details.
     */
    private IAnvilRecipe addRecipe(ItemStack result, Skill skill, String research, boolean hot, String tool,
                                   int hammer, int anvil, int time, byte recipeType, Object... input) {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var9;

        if (input[var4] instanceof String[]) {
            String[] var7 = ((String[]) input[var4++]);
            String[] var8 = var7;
            var9 = var7.length;

            for (int var10 = 0; var10 < var9; ++var10) {
                String var11 = var8[var10];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        } else {
            while (input[var4] instanceof String) {
                String var13 = (String) input[var4++];
                ++var6;
                var5 = var13.length();
                var3 = var3 + var13;
            }
        }

        HashMap var14;

        for (var14 = new HashMap(); var4 < input.length; var4 += 2) {
            Character var16 = (Character) input[var4];
            ItemStack var17 = null;

            if (input[var4 + 1] instanceof Item) {
                var17 = new ItemStack((Item) input[var4 + 1], 1, 32767);
            } else if (input[var4 + 1] instanceof Block) {
                var17 = new ItemStack((Block) input[var4 + 1], 1, 32767);
            } else if (input[var4 + 1] instanceof ItemStack) {
                var17 = (ItemStack) input[var4 + 1];
            }

            var14.put(var16, var17);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (var9 = 0; var9 < var5 * var6; ++var9) {
            char var18 = var3.charAt(var9);

            if (var14.containsKey(Character.valueOf(var18))) {
                var15[var9] = ((ItemStack) var14.get(Character.valueOf(var18))).copy();
            } else {
                var15[var9] = null;
            }
        }

        IAnvilRecipe recipe;
        if (recipeType == (byte) 1) {
            recipe = new CustomToolRecipe(var5, var6, var15, result, tool, time, hammer, anvil, hot, research,
                    skill);
        } else {
            recipe = new ShapedAnvilRecipes(var5, var6, var15, result, tool, time, hammer, anvil, hot, research,
                    skill);
        }
        this.recipes.add(recipe);
        return recipe;
    }

    public IAnvilRecipe addShapelessRecipe(ItemStack output, Skill skill, String research, boolean hot,
                                           String tool, int hammer, int anvil, int time, Object... input) {
        ArrayList var3 = new ArrayList();
        Object[] var4 = input;
        int var5 = input.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack) {
                var3.add(((ItemStack) var7).copy());
            } else if (var7 instanceof Item) {
                var3.add(new ItemStack((Item) var7));
            } else {
                if (!(var7 instanceof Block)) {
                    throw new RuntimeException("MineFantasy: Invalid shapeless anvil recipe!");
                }

                var3.add(new ItemStack((Block) var7));
            }
        }

        IAnvilRecipe recipe = new ShapelessAnvilRecipes(output, tool, hammer, anvil, time, var3, hot,
                research, skill);
        this.recipes.add(recipe);
        return recipe;
    }

    public ItemStack findMatchingRecipe(AnvilCraftMatrix matrix) {
        int var2 = 0;
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < matrix.getSizeInventory(); ++var5) {
            ItemStack var6 = matrix.getStackInSlot(var5);

            if (var6 != null) {
                if (var2 == 0) {
                    var3 = var6;
                }

                if (var2 == 1) {
                    var4 = var6;
                }

                ++var2;
            }
        }

        if (var2 == 2 && canRepair(var3, var4)) {
            Item var10 = var3.getItem();
            int var12 = var10.getMaxDamage() - var3.getItemDamageForDisplay();
            int var7 = var10.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var12 + var7 + var10.getMaxDamage() * 10 / 100;
            int var9 = var10.getMaxDamage() - (var8) * 2;

            if (var9 < 0) {
                var9 = 0;
            }

            return new ItemStack(var3.getItem(), 1, var9);
        } else {
            Iterator var11 = this.recipes.iterator();
            IAnvilRecipe var13;

            do {
                if (!var11.hasNext()) {
                    return null;
                }

                var13 = (IAnvilRecipe) var11.next();
            } while (!var13.matches(matrix));

            return var13.getCraftingResult(matrix);
        }
    }

    private boolean canRepair(ItemStack item1, ItemStack item2) {
        if (item1.getItem() == item2.getItem() && item1.stackSize == 1 && item2.stackSize == 1
                && item1.getItem().isRepairable()) {
            return true;
        }
        if (item1.getItem() == item2.getItem() && item1.stackSize == 1 && item2.stackSize == 1 && item1.isItemDamaged()
                && CustomToolHelper.areToolsSame(item1, item2)) {
            return true;
        }
        return false;
    }

    public ItemStack findMatchingRecipe(IAnvil anvil, AnvilCraftMatrix matrix) {
        int time = 200;
        int anvi = 1;
        boolean hot = false;
        int hammer = 0;
        int var2 = 0;
        String toolType = "hammer";
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < matrix.getSizeInventory(); ++var5) {
            ItemStack var6 = matrix.getStackInSlot(var5);

            if (var6 != null) {
                if (var2 == 0) {
                    var3 = var6;
                }

                if (var2 == 1) {
                    var4 = var6;
                }

                ++var2;
            }
        }

        if (var2 == 2 && var3.getItem() == var4.getItem() && var3.stackSize == 1 && var4.stackSize == 1
                && var3.getItem().isRepairable()) {
            Item var10 = var3.getItem();
            int var12 = var10.getMaxDamage() - var3.getItemDamageForDisplay();
            int var7 = var10.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var12 + var7 + var10.getMaxDamage() * 10 / 100;
            int var9 = var10.getMaxDamage() - var8;

            if (var9 < 0) {
                var9 = 0;
            }

            return new ItemStack(var3.getItem(), 1, var9);
        } else {
            Iterator var11 = this.recipes.iterator();
            IAnvilRecipe var13 = null;

            while (var11.hasNext()) {
                IAnvilRecipe rec = (IAnvilRecipe) var11.next();

                if (rec.matches(matrix)) {
                    var13 = rec;
                }
            }

            if (var13 != null) {
                time = var13.getCraftTime();
                hammer = var13.getRecipeHammer();
                anvi = var13.getAnvil();
                hot = var13.outputHot();
                toolType = var13.getToolType();

                if (!var13.useCustomTiers()) {
                    anvil.setForgeTime(time);
                    anvil.setHammerUsed(hammer);
                    anvil.setRequiredAnvil(anvi);
                }
                anvil.setHotOutput(hot);
                anvil.setToolType(toolType);
                if (!var13.getResearch().equalsIgnoreCase("tier")) {
                    anvil.setResearch(var13.getResearch());
                }
                anvil.setSkill(var13.getSkill());

                return var13.getCraftingResult(matrix);
            }
            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList() {
        return this.recipes;
    }
}
