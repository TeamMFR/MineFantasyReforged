package minefantasy.mfr.material;

import minefantasy.mfr.api.armour.ArmourMaterialMFR;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

import java.util.HashMap;

/**
 * This is used to create both tool and armour materials. Variables needed:
 * Durability, protection, sharpness, enchantment, weight, harvestLvl
 */
public class BaseMaterialMFR {
    /**
     * This scales armour so a sword hitting full mail of equal tier has this as a
     * result Used so armour can scale up with weapon damage
     */
    private static final float armourVsSwordBalance = 2.0F;
    /**
     * The base damage for swords used with players. (Swords are 4+dam, adding 1 dam
     * for player base hit dam)
     */
    private static final float SWORD_DAMAGE = 5F;
    public static HashMap<String, BaseMaterialMFR> materialMap = new HashMap<String, BaseMaterialMFR>();

    // Cloth and leather
    public static BaseMaterialMFR LINEN;
    public static BaseMaterialMFR WOOL;
    public static BaseMaterialMFR LEATHER_APRON;
    public static BaseMaterialMFR HIDE;
    public static BaseMaterialMFR ROUGH;
    public static BaseMaterialMFR REINFORCED;
    public static BaseMaterialMFR PADDING;
    public static BaseMaterialMFR STUDDED;
    public static BaseMaterialMFR SCALED;
    public static BaseMaterialMFR DRAGONSCALE;

    // Misc Mats
    public static BaseMaterialMFR STONE;
    public static BaseMaterialMFR TIN;
    public static BaseMaterialMFR PIG_IRON;
    public static BaseMaterialMFR SILVER;
    public static BaseMaterialMFR GOLD;
    public static BaseMaterialMFR ORNATE;
    public static BaseMaterialMFR WEAK_BLACK_STEEL;
    public static BaseMaterialMFR WEAK_RED_STEEL;
    public static BaseMaterialMFR WEAK_BLUE_STEEL;
    public static BaseMaterialMFR TUNGSTEN;

    // Tiers
    public static BaseMaterialMFR COPPER;
    public static BaseMaterialMFR BRONZE;
    public static BaseMaterialMFR IRON;
    public static BaseMaterialMFR STEEL;
    public static BaseMaterialMFR ENCRUSTED;
    public static BaseMaterialMFR OBSIDIAN;
    public static BaseMaterialMFR BLACK_STEEL;
    public static BaseMaterialMFR BLUE_STEEL;
    public static BaseMaterialMFR RED_STEEL;
    public static BaseMaterialMFR DRAGONFORGE;
    public static BaseMaterialMFR ADAMANTIUM;
    public static BaseMaterialMFR MITHRIL;
    public static BaseMaterialMFR IGNOTUMITE;
    public static BaseMaterialMFR MITHIUM;
    public static BaseMaterialMFR ENDERFORGE;

    // Engineer
    public static BaseMaterialMFR COGWORKS;
    public static BaseMaterialMFR COMPOSITE_ALLOY;

    private static float ACrounding = 10F; // round to nearest 10
    /*
     * WOOD(0, 59, 2.0F, 0.0F, 15), STONE(1, 131, 4.0F, 1.0F, 5), IRON(2, 250, 6.0F,
     * 2.0F, 14), EMERALD(3, 1561, 8.0F, 3.0F, 10), GOLD(0, 32, 12.0F, 0.0F, 22);
     *
     * To get variables: X = sword damage, Y = Armour Ratio(+1 of value), Z = Damage
     *
     * X = Z * Y Y = X / Z Z = X / Y
     */
    // Rounding off to nearest 0.5 makes about an 0.03 difference, but the AR is
    // cleaner
    // Hardness isn't added, it calculats armour itself to match sharpness
    // name dura, AC enchant weight
    public String name;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CLASS
    // START~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /**
     * This variable determines max uses
     */
    public int durability;
    /**
     * This variable determines the protection : Hardness and sharpness are normally
     * relevant to each other (so armour vs weapon equal tier has the same
     * after-calculation damage)
     */
    public float hardness;
    /**
     * This variable determines damage and dig speed : Hardness and sharpness are
     * normally relevant to each other (so armour vs weapon equal tier has the same
     * after-calculation damage)
     */
    public float sharpness;
    /**
     * This variable determines enchantement
     */
    public int enchantment;
    public float weight;
    /**
     * The required level to craft
     */
    public int requiredLevel;
    /**
     * The tier the material is on
     */
    public int tier;
    public int harvestLevel;
    // FORGING
    public int hammerTier;
    public int anvilTier;
    public float craftTimeModifier = 2.0F;

    // TIERING
    public int workableTemp = 100;
    public int unstableTemp = 150;
    // RESISTANCES
    public float fireResistance;
    public float arcaneResistance;
    public boolean isMythic = false;
    public int rarity;
    // SPECIALS
    private ArmourMaterialMFR armourConversion;
    private ToolMaterial toolConversion;

    public BaseMaterialMFR(String name, int tier, int durability, int harvestLevel, float hardness, float sharpness, int enchantment, float weight, int lvl) {
        this.requiredLevel = lvl;
        this.name = name;
        this.tier = tier;
        this.durability = durability;
        this.hardness = hardness;
        this.sharpness = sharpness;
        this.enchantment = enchantment;
        this.weight = weight;
        this.harvestLevel = harvestLevel;
    }

    public static void init() {
        // LEATHER AND CLOTH
        LINEN = addArmourSpecificMaterial("linen", 0, 10, 0.1F, 0, 1.00F, 0);
        WOOL = addArmourSpecificMaterial("wool", 1, 15, 0.1F, 0, 1.00F, 5);

        LEATHER_APRON = addArmourSpecificMaterial("leather_apron", 0, 10, 1.5F, 0, 0.50F, 0);
        HIDE = addArmourSpecificMaterial("hide", 0, 100, 1.5F, 0, 1.00F, 0);
        ROUGH = addArmourSpecificMaterial("rough_leather", 0, 150, 1.5F, 1, 1.00F, 0);
        REINFORCED = addArmourSpecificMaterial("strong_leather", 1, 250, 2.0F, 1, 1.00F, 5);
        PADDING = addArmourSpecificMaterial("padded", 1, 200, 2.0F, 0, 1.00F, 5);
        STUDDED = addArmourSpecificMaterial("stud_leather", 1, 500, 2.5F, 5, 1.20F, 15);
        SCALED = addArmourSpecificMaterial("scale_leather", 2, 1000, 2.8F, 8, 1.50F, 25);
        DRAGONSCALE = addArmourSpecificMaterial("dragonscale", 3, 3000, 5F, 20, 1.20F, 85).setRarity(2);

        // name Tier dura, harvest sharpness enchant weight
        // MISC
        WEAK_BLACK_STEEL = addMaterial("weak_black_steel", -1, 250, 4, 2.0F, 0, 1.00F, 40).setForgeStats(4, 4, 4.0F, 150, 500);
        WEAK_RED_STEEL = addMaterial("weak_red_steel", -1, 400, 5, 3.0F, 0, 1.10F, 65).setForgeStats(4, 4, 4.0F, 200, 500);
        WEAK_BLUE_STEEL = addMaterial("weak_blue_steel", -1, 300, 5, 2.5F, 0, 0.90F, 65).setForgeStats(4, 4, 4.0F, 175, 500);
        STONE = addMaterial("stone", 0, 50, 0, 0.1F, 0.0F, 0, 2.00F, 0).setForgeStats(0, 0, 0.75F, 0, 0);
        TIN = addMaterial("tin", 0, 100, 0, 0.2F, 5, 0.80F, 0).setForgeStats(0, 0, 0, 85, 100);
        PIG_IRON = addMaterial("pig_iron", 0, 250, 0, 1.5F, 3, 1.00F, 0).setForgeStats(2, 2, 2.0F, 100, 400);
        SILVER = addMaterial("silver", -1, 150, 0, 0.0F, 10, 0.70F, 0).setForgeStats(1, 1, 3F, 90, 120);
        GOLD = addMaterial("gold", -1, 150, 0, 0.0F, 25, 1.50F, 0).setForgeStats(1, 1, 3F, 90, 120);
        // goldPure = addMaterial("PureGold", -1, 50 , 0, 0.0F, 50, 2.00F, 0).setRarity(1);
        ORNATE = addMaterial("ornate", -1, 300, 0, 0.0F, 30, 1.00F, 30).setRarity(1).setForgeStats(1, 1, 4F, 120, 150);
        TUNGSTEN = addMaterial("tungsten", 2, 600, 3, 4F, 5, 1.50F, 0).setRarity(1).setForgeStats(3, 3, 5.0F, 150, 300);

        // TIERS
        // Basic / Common Materials (0-2) Levels 0-50
        COPPER = addMaterial("copper", 0, 200, 1, 1.0F, 5, 1.00F, 0).setForgeStats(0, 0, 1.0F, 95, 250); // lvl 0-4
        BRONZE = addMaterial("bronze", 1, 300, 2, 1.5F, 5, 1.00F, 5).setForgeStats(1, 1, 2.5F, 100, 250); // lvl 5-14
        IRON = addMaterial("iron", 2, 500, 2, 2.0F, 5, 1.00F, 15).setForgeStats(2, 2, 2.0F, 90, 250); // lvl 15-24
        STEEL = addMaterial("steel", 3, 750, 2, 2.5F, 10, 1.00F, 25).setForgeStats(3, 3, 2.5F, 120, 250); // lvl 25-39
        ENCRUSTED = addMaterial("encrusted", 3, 2000, 3, 3.5F, 25, 1.00F, 40).setForgeStats(3, 3, 5.0F, 130, 240); // lvl 40-49
        OBSIDIAN = addMaterial("obsidian", 3, 2000, 3, 3.5F, 25, 1.00F, 40).setForgeStats(3, 3, 5.0F, 130, 240); // lvl 40-49

        // Advanced Materials (3 - 4) Levels 50-75
        BLACK_STEEL = addMaterial("black_steel", 4, 1500, 4, 4.0F, 12, 1.00F, 50).setForgeStats(4, 4, 4.0F, 150, 350);// lvl 50
        DRAGONFORGE = addMaterial("dragonforge", 4, 1500, 4, 5.0F, 14, 1.00F, 60).setForgeStats(4, 4, 8.0F, 250, 350).setRarity(1).setResistances(100F, 0F);// lvl 60
        RED_STEEL = addMaterial("red_steel", 5, 2000, 5, 6.0F, 1, 1.15F, 75).setForgeStats(5, 5, 6.5F, 200, 350).setResistances(20F, 0F);// lvl 75
        BLUE_STEEL = addMaterial("blue_steel", 5, 1800, 5, 5.0F, 20, 0.75F, 75).setForgeStats(5, 5, 4.5F, 175, 325).setResistances(0F, 20F);// lvl 75

        // Mythic Materials (5) Levels 75-100
        ADAMANTIUM = addMaterial("adamantium", 6, 3000, 6, 8.0F, 10, 1.25F, 90).setForgeStats(6, 5, 9.0F, 300, 400).setRarity(1).setResistances(35F, 0F);// lvl 90
        MITHRIL = addMaterial("mithril", 6, 2500, 6, 7.0F, 30, 0.50F, 90).setForgeStats(6, 5, 6.0F, 280, 400).setRarity(1).setResistances(0F, 35F);// lvl 90

        // Masterwork Materials (6) Level 100
        IGNOTUMITE = addMaterial("ignotumite", 7, 1000, 7, 14.0F, 20, 2.00F, 100).setForgeStats(7, 5, 15.0F, 350, 400).setRarity(2).setResistances(50F, 0F);// High damage, heavy, fire resist lvl 100
        MITHIUM = addMaterial("mithium", 7, 1000, 7, 10.0F, 40, 0.25F, 100).setForgeStats(7, 5, 15.0F, 330, 400).setRarity(2).setResistances(0F, 50F);// Low damage, light, magic resist lvl 100
        ENDERFORGE = addMaterial("enderforge", 7, 1000, 7, 12.0F, 20, 1.00F, 100).setForgeStats(7, 5, 15.0F, 400, 450).setRarity(2).setResistances(25F, 25F);// Middle lvl 100

        // Engineer Materials
        // steel = addMaterial("Steel", 3, 750, 2, 2.5F, 10, 1.00F, 25).setForgeStats(3,
        // 3, 2.5F, 120, 250); //lvl 25-39
        COGWORKS = addArmourSpecificMaterial("cogworks", 4, 500, 1.0F, 10, 1.00F, 85).setForgeStats(3, 3, 2.5F, 120, 250);
        COMPOSITE_ALLOY = addArmourSpecificMaterial("composite_alloy", 4, 1800, 4.0F, 10, 2.00F, 85).setForgeStats(3, 3, 2.5F, 120, 250).setResistances(95F, 85F);

    }

    /**
     * This method auto-calculates the Armour Rating to scale the damage
     */
    public static BaseMaterialMFR addArmourSpecificMaterial(String name, int tier, int durability, float AC, int enchantment, float weight, int lvl) {
        return addMaterial(name, tier, durability, -1, AC, -1, enchantment, weight, lvl);
    }

    public static BaseMaterialMFR addMaterial(String name, int tier, int durability, int harvestLevel, float sharpness, int enchantment, float weight, int lvl) {
        float AC;
        AC = ((sharpness + SWORD_DAMAGE) / armourVsSwordBalance) - 1.0F;
        MFRLogUtil.logDebug("Added Ratio Armour Material " + name + " AR = " + AC);
        float initAc = AC;

        AC = Math.round(AC * (100F / ACrounding));
        AC = AC / (100F / ACrounding);

        if (initAc != AC) {
            MFRLogUtil.logDebug("Auto-Calculated ArmourRating for tier: " + name + ", modified to " + AC);
        }
        return addMaterial(name, tier, durability, harvestLevel, AC, sharpness, enchantment, weight, lvl);
    }

    public static BaseMaterialMFR addMaterial(String name, int tier, int durability, int harvestLevel, float hardness, float sharpness, int enchantment, float weight, int lvl) {
        return register(new BaseMaterialMFR(name, tier, durability, harvestLevel, hardness, sharpness, enchantment, weight, lvl));
    }

    public static BaseMaterialMFR register(BaseMaterialMFR material) {
        materialMap.put(material.name.toLowerCase(), material);
        return material;
    }

    private static ArmourMaterialMFR getMFRArmourMaterial(String name) {
        BaseMaterialMFR material = materialMap.get(name);
        return material != null ? getMFRArmourMaterial(material) : null;
    }

    public static ArmourMaterialMFR getMFRArmourMaterial(BaseMaterialMFR material) {
        return material.convertToMFArmour();
    }

    public static ToolMaterial getToolMaterial(String name) {
        BaseMaterialMFR material = materialMap.get(name);
        return material != null ? getToolMaterial(material) : null;
    }

    public static ToolMaterial getToolMaterial(BaseMaterialMFR material) {
        return material.convertToTool();
    }

    public static BaseMaterialMFR getMaterial(String name) {
        return materialMap.get(name.toLowerCase());
    }

    protected BaseMaterialMFR setForgeStats(int hammer, int anvil, float timer, int workable, int unstable) {
        hammerTier = hammer;
        anvilTier = anvil;
        craftTimeModifier = (timer * 2F) + 2.0F;
        workableTemp = workable;
        unstableTemp = unstable;
        return this;
    }

    protected BaseMaterialMFR setResistances(float fire, float arcane) {
        fireResistance = fire;
        arcaneResistance = arcane;
        return this;
    }

    protected BaseMaterialMFR setRarity(int value) {
        rarity = value;
        return this;
    }

    protected BaseMaterialMFR setMythic() {
        isMythic = true;
        return this;
    }

    private ArmourMaterialMFR convertToMFArmour() {
        return new ArmourMaterialMFR("MF" + name, durability, hardness, enchantment, weight)
                .setFireResistance(fireResistance).setMagicResistance(arcaneResistance).setMythic(isMythic);
    }

    private ToolMaterial convertToTool() {
        return EnumHelper.addToolMaterial("MF" + name, harvestLevel, durability, 2.0F + (sharpness * 2F), sharpness, enchantment);
    }

    public ArmourMaterialMFR getArmourConversion() {
        if (armourConversion == null) {
            armourConversion = getMFRArmourMaterial(this);
        }
        return armourConversion;
    }

    public ToolMaterial getToolConversion() {
        if (toolConversion == null) {
            toolConversion = getToolMaterial(this);
        }
        return toolConversion;
    }
}
