package minefantasy.mfr.material;

import com.google.common.base.CaseFormat;
import minefantasy.mfr.api.material.CustomMaterial;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class WoodMaterial extends CustomMaterial {
    public WoodMaterial(String name, int tier, float hardness, float durability, float flexibility, float sharpness, float resistance, float density) {
        super(name, "wood", tier, hardness, durability, flexibility, resistance, sharpness, density);
    }

    // TODO: remove statics after we add a data-driven material registry
    public static final String SCRAP_WOOD = "scrap_wood";
    public static final String OAK_WOOD = "oak_wood";
    public static final String SPRUCE_WOOD = "spruce_wood";
    public static final String BIRCH_WOOD = "birch_wood";
    public static final String JUNGLE_WOOD = "jungle_wood";
    public static final String ACACIA_WOOD = "acacia_wood";
    public static final String DARK_OAK_WOOD = "dark_oak_wood";
    public static final String REFINED_WOOD = "refined_wood";
    public static final String YEW_WOOD = "yew_wood";
    public static final String IRONBARK_WOOD = "ironbark_wood";
    public static final String EBONY_WOOD = "ebony_wood";
    public static final String SILVERWOOD_WOOD = "silverwood_wood";
    public static final String GREATWOOD_WOOD = "greatwood_wood";

    /**
     * @param name        The name of the wood
     * @param tier        Tier of the material
     * @param hardness    The impact it can take (used for reinforcment in BG shields),
     *                    usually an armour rating based value
     * @param durability  how many uses / 250
     * @param flexibility The ability to flex and reshape, used in bow power
     * @param resistance  Resistance to rot
     * @param density     How much a unit (timber plank) weighs in Kg
     */
    public static CustomMaterial getOrAddWood(String name, int tier, float hardness, float durability, float flexibility, float resistance, float density, int red, int green, int blue) {
        if (getMaterial(name) != null) {
            return CustomMaterial.getMaterial(name);
        }
        return new WoodMaterial(name, tier, hardness, durability, flexibility, 0F, resistance, density)
                .setColour(red, green, blue).register();
    }

    public static void init() {
        new CustomMaterial("magic", "magic", -1, 1F, 0F, 1F, 0F, 0F, 0F).setColour(157, 157, 255).register();

        // Hardness-The Impact it can take. Used for armour rating, in this case
        // nothing.
        // Durability- how many uses it has, 1 pt per 250 uses (1/5th this adds to
        // durability to tools if used as a haft).
        // Flexibility- how well the wood can bend and reshape, affects bow damage. This
        // is the direct damage modifier of the bow
        // Resistance- Score of how well the wood withstands rot and weathering.
        // Density- how much weight (Kg) does the timber plank weigh.

        // Name T Hds Dua Flx Rst Wgt R G B
        getOrAddWood(SCRAP_WOOD, 0, 0.10F, 0.50F, 0.50F, 10F, 0.5F, 100, 95, 80);

        getOrAddWood(OAK_WOOD, 1, 0.70F, 1.00F, 1.30F, 40F, 0.8F, 149, 119, 70).setCrafterTiers(1);
        getOrAddWood(SPRUCE_WOOD, 1, 0.20F, 0.90F, 1.00F, 20F, 0.4F, 102, 79, 47).setCrafterTiers(1);
        getOrAddWood(BIRCH_WOOD, 1, 0.50F, 0.90F, 1.30F, 10F, 0.7F, 200, 183, 122).setCrafterTiers(1);
        getOrAddWood(JUNGLE_WOOD, 1, 0.40F, 1.00F, 1.20F, 50F, 0.6F, 159, 113, 74).setCrafterTiers(1);
        getOrAddWood(ACACIA_WOOD, 1, 0.50F, 1.20F, 1.00F, 20F, 0.6F, 173, 93, 50).setCrafterTiers(1);
        getOrAddWood(DARK_OAK_WOOD, 1, 1.20F, 1.50F, 1.30F, 50F, 1.0F, 62, 41, 18).setCrafterTiers(1);

        getOrAddWood(REFINED_WOOD, 2, 0.80F, 2.00F, 1.30F, 50F, 0.8F, 95, 40, 24).setCrafterTiers(2).setRarity(1);
        getOrAddWood(YEW_WOOD, 2, 0.70F, 2.00F, 2.00F, 40F, 0.7F, 195, 138, 54).setCrafterTiers(2).setRarity(1);
        getOrAddWood(IRONBARK_WOOD, 2, 0.90F, 3.50F, 1.10F, 50F, 0.9F, 202, 92, 29).setCrafterTiers(2).setRarity(1);

        getOrAddWood(EBONY_WOOD, 3, 1.30F, 4.00F, 1.60F, 80F, 1.0F, 50, 46, 40).setCrafterTiers(3).setRarity(2);

        getOrAddWood(SILVERWOOD_WOOD, 2, 1.00F, 3.50F, 2.20F, 75F, 0.8F, 224, 220, 208).setCrafterTiers(2);
        getOrAddWood(GREATWOOD_WOOD, 2, 1.20F, 1.50F, 1.00F, 50F, 1.5F, 37, 25, 23).setCrafterTiers(2);
    }

    @Override
    public String getMaterialString() {
        return I18n.format("materialtype." + this.type + ".name", this.tier);
    }

    @Override
    public ItemStack getItemStack() {
        String camelCaseName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
        NonNullList<ItemStack> list = OreDictionary.getOres("planks" + camelCaseName);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return ItemStack.EMPTY;
    }

}
