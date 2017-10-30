package minefantasy.mf2.integration.nei;

import minefantasy.mf2.api.crafting.ITieredComponent;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import net.minecraft.item.ItemStack;

public class NEIHelper {

    public static ItemStack fillMaterials(IAnvilRecipe recipe, ItemStack componentStack, ItemStack outputStack) {
        if (componentStack.getItem() instanceof ITieredComponent) {
            String componentType = ((ITieredComponent) componentStack.getItem()).getMaterialType(componentStack);
            if (componentType != null) {
                if (componentType.equalsIgnoreCase("metal")) {
                    CustomMaterial resultPrimaryMaterial = CustomToolHelper.getCustomPrimaryMaterial(outputStack);
                    if (resultPrimaryMaterial != null) {
                        CustomMaterial.addMaterial(componentStack, CustomToolHelper.slot_main, resultPrimaryMaterial.name);
                    }
                }
                if (componentType.equalsIgnoreCase("wood")) {
                    CustomMaterial resultSecondaryMaterial = CustomToolHelper.getCustomSecondaryMaterial(outputStack);
                    if (resultSecondaryMaterial != null) {
                        CustomMaterial.addMaterial(componentStack, CustomToolHelper.slot_main, resultSecondaryMaterial.name);
                    }
                }
            }

            /*if (recipe.outputHot() && componentStack.getItem() instanceof IHotItem) {
                ItemHeated.setTemp(componentStack, 1400);
            }*/
        }

        return componentStack;
    }
}
