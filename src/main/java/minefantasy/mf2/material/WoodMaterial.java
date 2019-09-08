package minefantasy.mf2.material;

import minefantasy.mf2.api.material.CustomMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class WoodMaterial extends CustomMaterial {
    public WoodMaterial(String name, int tier, float hardness, float durability, float flexibility, float sharpness,
                        float resistance, float density) {
        super(name, "wood", tier, hardness, durability, flexibility, resistance, sharpness, density);
    }

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
    public static CustomMaterial getOrAddWood(String name, int tier, float hardness, float durability,
                                              float flexibility, float resistance, float density, int red, int green, int blue) {
        if (getMaterial(name) != null) {
            return CustomMaterial.getMaterial(name);
        }
        return new WoodMaterial(name, tier, hardness, durability, flexibility, 0F, resistance, density)
                .setColour(red, green, blue).register();
    }

    public static void init() {
        new CustomMaterial("magic", "Magic", -1, 1F, 0F, 1F, 0F, 0F, 0F).setColour(157, 157, 255).register();

        MetalMaterial.init();
        LeatherMaterial.init();
        // Hardness-The Impact it can take. Used for armour rating, in this case
        // nothing.
        // Durability- how many uses it has, 1 pt per 250 uses (1/5th this adds to
        // durability to tools if used as a haft).
        // Flexibility- how well the wood can bend and reshape, affects bow damage. This
        // is the direct damage modifier of the bow
        // Resistance- Score of how well the wood withstands rot and weathering.
        // Density- how much weight (Kg) does the timber plank weigh.

        // Name T Hds Dua Flx Rst Wgt R G B
        getOrAddWood("ScrapWood", 0, 0.10F, 0.50F, 0.50F, 10F, 0.5F, 100, 95, 80);

        getOrAddWood("OakWood", 1, 0.70F, 1.00F, 1.30F, 40F, 0.8F, 149, 119, 70).setCrafterTiers(1);
        getOrAddWood("SpruceWood", 1, 0.20F, 0.90F, 1.00F, 20F, 0.4F, 102, 79, 47).setCrafterTiers(1);
        getOrAddWood("BirchWood", 1, 0.50F, 0.90F, 1.30F, 10F, 0.7F, 200, 183, 122).setCrafterTiers(1);
        getOrAddWood("JungleWood", 1, 0.40F, 1.00F, 1.60F, 50F, 0.6F, 159, 113, 74).setCrafterTiers(1);
        getOrAddWood("AcaciaWood", 1, 0.50F, 1.20F, 1.00F, 20F, 0.6F, 173, 93, 50).setCrafterTiers(1);
        getOrAddWood("DarkOakWood", 1, 1.20F, 1.50F, 1.30F, 50F, 1.0F, 62, 41, 18).setCrafterTiers(1);

        getOrAddWood("RefinedWood", 2, 0.80F, 2.00F, 1.30F, 50F, 0.8F, 95, 40, 24).setCrafterTiers(2).setRarity(1);
        getOrAddWood("YewWood", 2, 0.70F, 2.00F, 2.50F, 40F, 0.7F, 195, 138, 54).setCrafterTiers(2).setRarity(1);
        getOrAddWood("IronbarkWood", 2, 0.90F, 3.50F, 1.10F, 50F, 0.9F, 202, 92, 29).setCrafterTiers(2).setRarity(1);

        getOrAddWood("EbonyWood", 3, 1.30F, 4.00F, 1.60F, 80F, 1.0F, 50, 46, 40).setCrafterTiers(3).setRarity(2);

        getOrAddWood("SilverwoodWood", 2, 1.00F, 3.50F, 1.50F, 75F, 0.8F, 224, 220, 208).setCrafterTiers(2);
        getOrAddWood("GreatwoodWood", 2, 1.20F, 1.50F, 1.30F, 50F, 1.5F, 37, 25, 23).setCrafterTiers(2);
        /*
         * //OTHERS. playing around with, mainly forestry getOrAddWood("PineWood", 0, 1,
         * 1, 1, 1, 1, 1, 189, 147, 63); getOrAddWood("CherryWood", 0, 1, 1, 1, 1, 1, 1,
         * 162, 116, 47); getOrAddWood("PapayaWood", 0, 1, 1, 1, 1, 1, 1, 214, 194, 91);
         * getOrAddWood("CitrusWood", 0, 1, 1, 1, 1, 1, 1, 142, 153, 27);
         * getOrAddWood("PoplarWood", 0, 1, 1, 1, 1, 1, 1, 198, 198, 107);
         * getOrAddWood("MapleWood", 0, 1, 1, 1, 1, 1, 1, 158, 99, 39);
         * getOrAddWood("MahoeWood", 0, 1, 1, 1, 1, 1, 1, 111, 138, 158);
         * getOrAddWood("GreenheartWood", 0, 1, 1, 1, 1, 1, 1, 65, 103, 77);
         * getOrAddWood("PalmWood", 0, 1, 1, 1, 1, 1, 1, 195, 114, 56);
         * getOrAddWood("LimeWood", 0, 1, 1, 1, 1, 1, 1, 190, 147, 96);
         * getOrAddWood("BalsaWood", 0, 1, 1, 1, 1, 1, 1, 159, 152, 143);
         * getOrAddWood("WalnutWood", 0, 1, 1, 1, 1, 1, 1, 85, 69, 55);
         * getOrAddWood("KapokWood", 0, 1, 1, 1, 1, 1, 1, 99, 95, 45);
         * getOrAddWood("ChestnutWood", 0, 1, 1, 1, 1, 1, 1, 156, 147, 74);
         * getOrAddWood("BaobabWood", 0, 1, 1, 1, 1, 1, 1, 116, 126, 84);
         * getOrAddWood("LarchWood", 0, 1, 1, 1, 1, 1, 1, 200, 143, 122);
         * getOrAddWood("WillowWood", 0, 1, 1, 1, 1, 1, 1, 149, 152, 74);
         * getOrAddWood("TeakWood", 0, 1, 1, 1, 1, 1, 1, 111, 107, 91);
         * getOrAddWood("SequoiaWood", 0, 1, 1, 1, 1, 1, 1, 123, 81, 75);
         * getOrAddWood("MahoganyWood", 0, 1, 1, 1, 1, 1, 1, 95, 56, 49);
         * getOrAddWood("WengeWood", 0, 1, 1, 1, 1, 1, 1, 76, 72, 62);
         * getOrAddWood("RedwoodWood", 0, 1, 1, 1, 1, 1, 1, 148, 104, 55);
         * getOrAddWood("FirWood", 0, 1, 1, 1, 1, 1, 1, 114, 111, 63);
         * getOrAddWood("PurpleHeartWood", 0, 1, 1, 1, 1, 1, 1, 55, 25, 49);
         */

    }

    @Override
    public String getMaterialString() {
        return StatCollector.translateToLocalFormatted("materialtype." + this.type + ".name", this.tier);
    }

    @Override
    public ItemStack getItem() {
        ArrayList<ItemStack> list = OreDictionary.getOres("planks" + name);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
