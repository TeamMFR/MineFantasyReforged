package minefantasy.mf2.api.material;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomMaterial {
    public static final String NBTBase = "MF_CustomMaterials";
    public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
    public static final DecimalFormat decimal_format_grams = new DecimalFormat("#");
    /**
     * Min and Max workable temps
     */
    public static final int[] flameResistArray = new int[]{100, 300};
    public static HashMap<String, CustomMaterial> materialList = new HashMap<String, CustomMaterial>();
    public static HashMap<String, ArrayList<CustomMaterial>> typeList = new HashMap<String, ArrayList<CustomMaterial>>();
    public final String name, type;
    public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
    /**
     * The material colour
     */
    public int[] colourRGB = new int[]{255, 255, 255};
    /**
     * Base threshold for armour rating
     */
    public float hardness;
    /**
     * The Modifier for durability (1pt per 250 uses)
     */
    public float durability;
    /**
     * used for bow power.. >1 weakens blunt prot, <1 weakens piercing prot
     */
    public float flexibility;
    /**
     * The Efficiency modifier (Like ToolMaterial) Also does damage
     */
    public float sharpness;
    /**
     * The modifier to resist elements like fire and corrosion)
     */
    public float resistance;
    /**
     * The weight Kg/U (Kilogram per unit)
     */
    public float density;
    public int tier;
    public int rarityID = 0;
    public int crafterTier = 0;
    public int crafterAnvilTier = 0;
    public float craftTimeModifier = 1.0F;
    public float meltingPoint;
    private float[] armourProtection = new float[]{1.0F, 1.0F, 1.0F};
    private boolean unbreakable = false;

    public CustomMaterial(String name, String type, int tier, float hardness, float durability, float flexibility,
                          float resistance, float sharpness, float density) {
        this.name = name;
        this.type = type;
        this.tier = tier;
        this.hardness = hardness;
        this.durability = durability;
        this.flexibility = flexibility;
        this.sharpness = sharpness;
        this.density = density;
        this.resistance = resistance;
        this.craftTimeModifier = 2F + (sharpness * 2F);
    }

    /**
     * Gets material name
     */
    public String getName() {
        return name.toLowerCase();
    }

    /**
     * Gets material type
     */
    public String getType() {
        return type.toLowerCase();
    }

    /**
     * Gets a material by name
     */
    public static CustomMaterial getMaterial(String name) {
        if (name == null)
            return null;

        return materialList.get(name.toLowerCase());
    }

    /**
     * Adds a new material
     *
     * @param name        The name of the material (and registration)
     * @param type        What it is (Metal, Wood, etc)
     * @param tier        The tier of the material
     * @param hardness    How hard the material is to break
     * @param flexibility How well the material can bend and retract
     * @param sharpness   How well it can be sharpened
     * @param resistance  How well it can handle destructive elements like fire and
     *                    corrosion, and increased heating temperature
     * @param density     how dense the element is, increasing mass per unit. (KG/Units)
     */
    public static CustomMaterial getOrAddMaterial(String name, String type, int tier, float hardness, float durability,
                                                  float flexibility, float sharpness, float resistance, float density, int red, int green, int blue) {
        if (getMaterial(name) != null) {
            return getMaterial(name);
        }
        return new CustomMaterial(name, type, tier, hardness, durability, flexibility, sharpness, resistance, density)
                .setColour(red, green, blue).register();
    }

    public static void addMaterial(ItemStack item, String slot, String material) {
        if (material == null || material.isEmpty()) {
            return;
        }
        NBTTagCompound nbt = getNBT(item, true);
        nbt.setString(slot, material);
    }

    public static CustomMaterial getMaterialFor(ItemStack item, String slot) {
        NBTTagCompound nbt = getNBT(item, false);
        if (nbt != null) {
            if (nbt.hasKey(slot)) {
                return getMaterial(nbt.getString(slot));
            }
        }
        return null;
    }

    public static NBTTagCompound getNBT(ItemStack item, boolean createNew) {
        if (item != null && item.hasTagCompound() && item.getTagCompound().hasKey(NBTBase)) {
            return (NBTTagCompound) item.getTagCompound().getTag(NBTBase);
        }
        if (createNew) {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTTagCompound nbt2 = new NBTTagCompound();
            item.setTagCompound(nbt);
            nbt.setTag(NBTBase, nbt2);
            return nbt2;
        }
        return null;
    }

    public static ArrayList<CustomMaterial> getList(String type) {
        if (typeList.get(type) == null) {
            typeList.put(type, new ArrayList<>());
        }
        return typeList.get(type);
    }

    public static void addOreDict(String material, String name) {
        if (getMaterial(name) != null) {
            getMaterial(name).addOreDict(material);
        }
    }

    @SideOnly(Side.CLIENT)
    public static String getWeightString(float mass) {
        DecimalFormat df = decimal_format;
        String s = "attribute.weightKg.name";
        if (mass > 0 && mass < 1.0F) {
            s = "attribute.weightg.name";
            df = decimal_format_grams;
            mass = (int) (mass * 1000F);
        } else if (mass > 1000) {
            s = "attribute.weightt.name";
            mass = (int) (mass / 1000F);
        }
        return StatCollector.translateToLocalFormatted(s, decimal_format.format(mass));
    }

    public CustomMaterial setCrafterTiers(int tier) {
        this.crafterTier = tier;
        this.crafterAnvilTier = Math.min(tier, 4);
        return this;
    }

    public CustomMaterial modifyCraftTime(float time) {
        this.craftTimeModifier *= time;
        return this;
    }

    public CustomMaterial setArmourStats(float cutting, float blunt, float piercing) {
        armourProtection = new float[]{cutting, blunt, piercing};
        return this;
    }

    public CustomMaterial setRarity(int id) {
        rarityID = id;
        return this;
    }

    public CustomMaterial setColour(int red, int green, int blue) {
        colourRGB = new int[]{red, green, blue};
        return this;
    }

    public CustomMaterial register() {
        materialList.put(this.getName(), this);
        getList(type).add(this);
        return this;
    }

    public int getColourInt() {
        return (colourRGB[0] << 16) + (colourRGB[1] << 8) + colourRGB[2];
    }

    public void addOreDict(String name) {
        for (ItemStack item : OreDictionary.getOres(name)) {
            items.add(item);
        }
    }

    public boolean isItemApplicable(ItemStack item) {
        return this.items.contains(item);
    }

    @SideOnly(Side.CLIENT)
    public String getMaterialString() {
        return StatCollector.translateToLocalFormatted("materialtype." + this.type + ".name", this.crafterTier);
    }

    public float getArmourProtection(int id) {
        return armourProtection[id];
    }

    public ItemStack getItem() {
        return null;
    }

    public CustomMaterial setMeltingPoint(float heat) {
        meltingPoint = heat;
        return this;
    }

    public float getFireResistance() {
        if (meltingPoint > flameResistArray[0]) {
            float max = flameResistArray[1] - flameResistArray[0];
            float heat = meltingPoint - flameResistArray[0];

            int res = (int) (heat / max * 100F);
            return Math.min(100, res);
        }
        return 0F;
    }

    // -----------------------------------BOW
    // FUNCTIONS----------------------------------------\\

    public int[] getHeatableStats() {
        int workableTemp = (int) meltingPoint;
        int unstableTemp = (int) (workableTemp * 1.5F);
        int maxTemp = (int) (workableTemp * 2F);
        return new int[]{workableTemp, unstableTemp, maxTemp};
    }

    // Returns if the material is a hard wood
    // false is a non-unique value, this should not be used when youre not sure if
    // the material is a wood
    public boolean isHardwood() {
        if (this.getName().contains("wood")) {
            if (this.getName().contains("yew")) {// Yew is a softwood but has a higher hardness value
                return false;
            }
            return this.hardness > 3.3;
        }
        return false;
    }

    // Returns a rating for how well a would would serve as a bow.
    // Answers are in irrelevant physical units, so this is scaled in a later
    // function which is meant to be used.
    // Bow rating is proportional to power of the bow
    // With regular woods, this value is from 6.4 to 11.5
    // A rating BELOW 8.0 means that the wood is not suitable for a bow and this
    // funtion will return zero
    private float getBowRating() {
        if (this.getName().contains("wood")) {
            float mid = (0.83F * (this.durability / this.flexibility));
            float result = (10F * this.flexibility * mid * mid) / this.density;
            return result > 8.0F ? result : 0;
        }
        return 0;
    }

    public boolean isHeatable() {
        return type.equalsIgnoreCase("metal");
    }

    public CustomMaterial setUnbreakable() {
        unbreakable = true;
        return this;
    }

    public boolean isUnbrekable() {
        return unbreakable;
    }
}
