package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.material.CustomMaterial;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.CustomMaterial")
public class CustomMaterialHandler {

    @ZenMethod
    public static CustomMaterial addCustomMaterial(String type, String name, int tier, float hardness, float durability,
                                                   float flexibility, float sharpness, float resistance, float density) {
        RegisterCustomMaterial reg = new RegisterCustomMaterial(type.toLowerCase(), name, tier, hardness, durability, flexibility, sharpness, resistance, density);
        MineTweakerAPI.apply(reg);
        return reg.material;
    }

    @ZenMethod
    public static CustomMaterial getMaterial(String materialName) {
        return CustomMaterial.getMaterial(materialName);
    }

    private static class RegisterCustomMaterial implements IUndoableAction {

        private final String type, name;
        private final int tier;
        private final float hardness, durability, flexibility, sharpness, resistance, density;
        public CustomMaterial material;

        public RegisterCustomMaterial(String type, String name, int tier, float hardness, float durability,
                                      float flexibility, float sharpness, float resistance, float density) {
            this.type = type;
            this.name = name;
            this.tier = tier;
            this.hardness = hardness;
            this.durability = durability;
            this.flexibility = flexibility;
            this.sharpness = sharpness;
            this.resistance = resistance;
            this.density = density;
        }

        @Override
        public void apply() {
            material = CustomMaterial.getMaterial(name) != null ? CustomMaterial.getMaterial(name) : new CustomMaterial(name, type, tier, hardness, durability, flexibility, resistance, sharpness, density).register();
        }

        @Override
        public boolean canUndo() {
            return false;
        }

        @Override
        public void undo() {

        }

        @Override
        public String describe() {
            return "Registers custom material for MF crafting system";
        }

        @Override
        public String describeUndo() {
            return "Impossible to remove registered CustomMaterials!";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}