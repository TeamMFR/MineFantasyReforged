package minefantasy.mfr.material;

public class LeatherMaterial extends CustomMaterial {
    public LeatherMaterial(String name, int tier, float hardness, float durability, float flexibility, float sharpness, float resistance, float density) {
        super(name, "leather", tier, hardness, durability, flexibility, sharpness, resistance, density);
    }

    public static void getOrAddWood(String name, int tier, float hardness, float durability, float flexibility, float sharpness, float resistance, float density, int red, int green, int blue) {
        if (getMaterial(name) != null) {
            CustomMaterial.getMaterial(name);
            return;
        }
        new LeatherMaterial(name, tier, hardness, durability, flexibility, sharpness, resistance, density).setColour(red, green, blue).register();
    }

    public static void init() {
        getOrAddWood("leather", 0, 1.0F, 0.4F, 1.2F, 0F, 0.5F, 0.1F, 198, 92, 53);
        getOrAddWood("hard_leather", 1, 1.5F, 0.8F, 1.0F, 0F, 0.5F, 0.2F, 154, 72, 41);
        getOrAddWood("minotaur_skin", 2, 2.0F, 1.5F, 0.8F, 0F, 1.0F, 0.5F, 118, 69, 48);
        getOrAddWood("dragon_skin", 3, 3.0F, 2.0F, 1.0F, 0F, 1.2F, 0.75F, 56, 43, 66);
    }
}
