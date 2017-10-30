package minefantasy.mf2.material;

import minefantasy.mf2.api.material.CustomMaterial;

public class LeatherMaterial extends CustomMaterial {
    public LeatherMaterial(String name, int tier, float hardness, float durability, float flexibility, float sharpness,
                           float resistance, float density) {
        super(name, "leather", tier, hardness, durability, flexibility, sharpness, resistance, density);
    }

    public static CustomMaterial getOrAddWood(String name, int tier, float hardness, float durability,
                                              float flexibility, float sharpness, float resistance, float density, int red, int green, int blue) {
        if (getMaterial(name) != null) {
            return CustomMaterial.getMaterial(name);
        }
        return new LeatherMaterial(name, tier, hardness, durability, flexibility, sharpness, resistance, density)
                .setColour(red, green, blue).register();
    }

    public static void init() {
        getOrAddWood("Leather", 0, 1.0F, 0.4F, 1.2F, 0F, 0.5F, 0.1F, 198, 92, 53);
        getOrAddWood("HardLeather", 1, 1.5F, 0.8F, 1.0F, 0F, 0.5F, 0.2F, 154, 72, 41);
        getOrAddWood("MinotaurSkin", 2, 2.0F, 1.5F, 0.8F, 0F, 1.0F, 0.5F, 118, 69, 48);
        getOrAddWood("DragonSkin", 3, 3.0F, 2.0F, 1.0F, 0F, 1.2F, 0.75F, 56, 43, 66);
    }
}
