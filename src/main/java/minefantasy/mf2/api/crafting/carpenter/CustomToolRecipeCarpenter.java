package minefantasy.mf2.api.crafting.carpenter;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author AnonymousProductions
 */
public class CustomToolRecipeCarpenter extends ShapedCarpenterRecipes {
    public CustomToolRecipeCarpenter(int wdth, int heit, ItemStack[] inputs, ItemStack output, String toolType,
                                     int time, int toolTier, int blockTier, float exp, boolean hot, String sound, String research, Skill skill) {
        super(wdth, heit, inputs, output, toolType, time, toolTier, blockTier, exp, hot, sound, research, skill);
    }

    private boolean checkMatch(CarpenterCraftMatrix matrix, int x, int y, boolean b) {
        String wood = null;
        String metal = null;
        for (int var5 = 0; var5 < ShapelessCarpenterRecipes.globalWidth; ++var5) {
            for (int var6 = 0; var6 < ShapelessCarpenterRecipes.globalHeight; ++var6) {
                int var7 = var5 - x;
                int var8 = var6 - y;
                ItemStack recipeItem = null;

                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
                    if (b) {
                        recipeItem = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
                    } else {
                        recipeItem = this.recipeItems[var7 + var8 * this.recipeWidth];
                    }
                }

                ItemStack inputItem = matrix.getStackInRowAndColumn(var5, var6);

                if (inputItem != null || recipeItem != null) {
                    String component_wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
                    String component_metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");

                    if (component_metal != null)// CHECK CUSTOM METAL
                    {
                        if (metal == null) {
                            metal = component_metal;
                        } else {
                            if (!metal.equalsIgnoreCase(component_metal)) {
                                return false;
                            }
                        }
                    }

                    if (component_wood != null)// CHECK CUSTOM WOOD
                    {
                        if (wood == null) {
                            wood = component_wood;
                        } else {
                            if (!wood.equalsIgnoreCase(component_wood)) {
                                return false;
                            }
                        }
                    }

                    if (inputItem == null && recipeItem != null || inputItem != null && recipeItem == null) {
                        return false;
                    }

                    if (inputItem == null) {
                        return false;
                    }

                    if (recipeItem.getItem() != inputItem.getItem()) {
                        return false;
                    }
                    if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
                        return false;
                    }
                    if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE
                            && recipeItem.getItemDamage() != inputItem.getItemDamage()) {
                        return false;
                    }
                }
            }
        }
        if (!modifyTiers(matrix, metal)) {
            modifyTiers(matrix, wood);
        }

        return true;
    }

    private boolean modifyTiers(CarpenterCraftMatrix matrix, String tier) {
        CustomMaterial material = CustomMaterial.getMaterial(tier);
        if (material != null) {
            int newTier = recipeHammer < 0 ? material.crafterTier : recipeHammer;
            matrix.modifyTier(newTier, (int) (recipeTime * material.craftTimeModifier));
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(CarpenterCraftMatrix matrix) {
        ItemStack result = super.getCraftingResult(matrix);

        String wood = null;
        String metal = null;
        for (int i = 0; i < matrix.getSizeInventory(); i++) {
            ItemStack item = matrix.getStackInSlot(i);
            String component_wood = CustomToolHelper.getComponentMaterial(item, "wood");
            String component_metal = CustomToolHelper.getComponentMaterial(item, "metal");
            if (wood == null && component_wood != null) {
                wood = component_wood;
            }
            if (metal == null && component_metal != null) {
                metal = component_metal;
            }
        }
        if (metal != null) {
            CustomMaterial.addMaterial(result, CustomToolHelper.slot_main, metal);
        }
        if (wood != null) {
            CustomMaterial.addMaterial(result, metal == null ? CustomToolHelper.slot_main : CustomToolHelper.slot_haft,
                    wood);
        }
        return result;
    }
}
