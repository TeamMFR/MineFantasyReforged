package minefantasy.mf2.api.helpers;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import minefantasy.mf2.api.crafting.ITieredComponent;
import minefantasy.mf2.api.crafting.exotic.ISpecialDesign;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.heatable.ItemHeated;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class CustomToolHelper {
    public static final String slot_main = "main_metal";
    public static final String slot_haft = "haft_wood";
    public static EnumRarity poor = EnumHelper.addRarity("Poor", EnumChatFormatting.DARK_GRAY, "poor");
    public static EnumRarity[] rarity = new EnumRarity[]{poor, EnumRarity.common, EnumRarity.uncommon,
            EnumRarity.rare, EnumRarity.epic};

    /**
     * A bit of the new system, gets custom materials for the head
     */
    public static CustomMaterial getCustomPrimaryMaterial(ItemStack item) {
        if (item == null)
            return null;

        CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
        if (material != null) {
            return material;
        }
        return null;
    }

    public static CustomMaterial getCustomSecondaryMaterial(ItemStack item) {
        if (item == null)
            return null;

        CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_haft);
        if (material != null) {
            return material;
        }
        return null;
    }

    public static ItemStack construct(Item base, String main) {
        return construct(base, main, "OakWood");
    }

    public static ItemStack construct(Item base, String main, String haft) {
        ItemStack item = new ItemStack(base);
        CustomMaterial.addMaterial(item, slot_main, main.toLowerCase());
        if (haft != null) {
            CustomMaterial.addMaterial(item, slot_haft, haft.toLowerCase());
        }
        return item;
    }

    public static ItemStack constructSingleColoredLayer(Item base, String main) {
        return constructSingleColoredLayer(base, main, 1);
    }

    public static ItemStack constructSingleColoredLayer(Item base, String main, int stacksize) {
        ItemStack item = new ItemStack(base, stacksize);
        CustomMaterial.addMaterial(item, slot_main, main.toLowerCase());
        return item;
    }

    /**
     * Gets the rarity for a custom item
     *
     * @param itemRarity is the default id
     */
    public static EnumRarity getRarity(ItemStack item, int itemRarity) {
        int lvl = itemRarity + 1;
        CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
        if (material != null) {
            lvl = material.rarityID + 1;
        }

        if (item.isItemEnchanted()) {
            if (lvl == 0) {
                lvl++;
            }
            lvl++;
        }
        if (lvl >= rarity.length) {
            lvl = rarity.length - 1;
        }
        return rarity[lvl];
    }

    /**
     * Gets the max durability
     *
     * @param dura is the default dura
     */
    public static int getMaxDamage(ItemStack stack, int dura) {
        CustomMaterial head = getCustomPrimaryMaterial(stack);
        CustomMaterial haft = getCustomSecondaryMaterial(stack);
        if (head != null) {
            dura = (int) (head.durability * 100);
        }
        if (haft != null) {
            dura += (int) (haft.durability * 100);// Hafts add 50% to the durability
        }
        return ToolHelper.setDuraOnQuality(stack, dura);
    }

    /**
     * Gets the colour for a layer
     *
     * @param base is default colour 0 is base 1 is haft 2 is detail
     */
    public static int getColourFromItemStack(ItemStack item, int layer, int base) {
        if (layer == 0) {
            CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
            if (material != null) {
                return material.getColourInt();
            }
        }
        if (layer == 1) {
            CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_haft);
            if (material != null) {
                return material.getColourInt();
            }
        }
        return base;
    }

    public static float getWeightModifier(ItemStack item, float base) {
        CustomMaterial metal = getCustomPrimaryMaterial(item);
        CustomMaterial wood = getCustomSecondaryMaterial(item);

        if (metal != null) {
            base = (metal.density / 2.5F) * base;
        }
        if (wood != null) {
            base += (wood.density / 2.5F);
        }
        return base;
    }

    /**
     * Gets the material modifier if it exists
     *
     * @param defaultModifier default if no material exists
     */
    public static float getMeleeDamage(ItemStack item, float defaultModifier) {
        CustomMaterial custom = getCustomPrimaryMaterial(item);
        if (custom != null) {
            return custom.sharpness;
        }
        return defaultModifier;
    }

    public static float getBowDamage(ItemStack item, float defaultModifier) {
        CustomMaterial base = getCustomSecondaryMaterial(item);
        CustomMaterial joints = getCustomPrimaryMaterial(item);

        if (base != null) {
            defaultModifier = base.flexibility;
        }
        if (joints != null) {
            defaultModifier *= joints.flexibility;
        }
        return defaultModifier;
    }

    /**
     * The total damage of a bow and arrow
     */
    public static float getBaseDamages(ItemStack item, float defaultModifier) {
        CustomMaterial custom = getCustomPrimaryMaterial(item);
        if (custom != null) {
            return getBaseDamage(custom.sharpness * custom.flexibility);
        }
        return getBaseDamage(defaultModifier);
    }

    /**
     * The damage a bow and arrow should do (same as a sword)
     */
    public static float getBaseDamage(float modifier) {
        return 4F + modifier;
    }

    public static float getEfficiencyForHds(ItemStack item, float value, float mod) {
        CustomMaterial custom = getCustomPrimaryMaterial(item);
        if (custom != null) {
            value = 2.0F + (custom.hardness * 4F);// Efficiency starts at 2 and each point of sharpness adds 2
        }
        return ToolHelper.modifyDigOnQuality(item, value) * mod;
    }

    public static float getEfficiency(ItemStack item, float value, float mod) {
        CustomMaterial custom = getCustomPrimaryMaterial(item);
        if (custom != null) {
            value = 2.0F + (custom.sharpness * 2F);// Efficiency starts at 2 and each point of sharpness adds 2
        }
        return ToolHelper.modifyDigOnQuality(item, value) * mod;
    }

    public static int getCrafterTier(ItemStack item, int value) {
        CustomMaterial custom = getCustomPrimaryMaterial(item);
        if (custom != null) {
            return custom.crafterTier;
        }
        return value;
    }

    public static int getHarvestLevel(ItemStack item, int value) {
        if (value <= 0) {
            return value;// If its not effective
        }

        CustomMaterial custom = getCustomPrimaryMaterial(item);
        if (custom != null) {
            if (custom.tier == 0)
                return 1;
            if (custom.tier <= 2)
                return 2;
            return Math.max(custom.tier, 2);
        }
        return value;
    }

    @SideOnly(Side.CLIENT)
    public static void addInformation(ItemStack item, List list) {
        CustomMaterial haft = getCustomSecondaryMaterial(item);

        if (materialOnTooltip()) {
            CustomMaterial main = getCustomPrimaryMaterial(item);
            if (main != null) {
                String matName = StatCollector.translateToLocal(
                        StatCollector.translateToLocal("material." + main.getName() + ".name"));
                list.add(EnumChatFormatting.GOLD + matName);
            }
        }

        if (haft != null) {
            String matName = StatCollector.translateToLocalFormatted("item.mod_haft.name",
                    StatCollector.translateToLocal("material." + haft.getName() + ".name"));
            list.add(EnumChatFormatting.GOLD + matName);
        }

    }

    /**
     * Gets if the language puts tiers in the tooltip, leaving the name blank
     *
     * @return
     */
    public static boolean materialOnTooltip() {
        String cfg = StatCollector.translateToLocal("languagecfg.tooltiptier");
        return cfg.equalsIgnoreCase("true");
    }

    @SideOnly(Side.CLIENT)
    public static void addBowInformation(ItemStack item, List list) {

        CustomMaterial metals = getCustomPrimaryMaterial(item);
        if (metals != null) {
            String matName = StatCollector.translateToLocalFormatted("item.mod_joint.name",
                    StatCollector.translateToLocal("material." + metals.getName() + ".name"));
            list.add(EnumChatFormatting.GOLD + matName);
        }

    }

    public static String getWoodenLocalisedName(ItemStack item, String unlocalName) {
        if (materialOnTooltip()) {
            StatCollector.translateToLocal(unlocalName);
        }

        CustomMaterial base = getCustomSecondaryMaterial(item);
        String name = "any";
        if (base != null) {
            name = base.getName();
        }
        return StatCollector.translateToLocalFormatted(unlocalName,
                StatCollector.translateToLocal("material." + name + ".name"));
    }

    public static String getLocalisedName(ItemStack item, String unlocalName) {
        if (materialOnTooltip()) {
            StatCollector.translateToLocal(unlocalName);
        }

        CustomMaterial base = getCustomPrimaryMaterial(item);
        String name = "any";
        if (base != null) {
            name = base.getName();
        }
        return StatCollector.translateToLocalFormatted(unlocalName,
                StatCollector.translateToLocal("material." + name + ".name"));
    }

    public static boolean areEqual(ItemStack recipeItem, ItemStack inputItem) {
        if (recipeItem == null) {
            return inputItem == null;
        }
        if (inputItem == null)
            return false;

        return recipeItem.isItemEqual(inputItem) && doesMainMatchForRecipe(recipeItem, inputItem)
                && doesHaftMatchForRecipe(recipeItem, inputItem);
    }

    /**
     * Checks if two items' materials match
     */
    public static boolean doesMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
        return doesMainMatchForRecipe(recipeItem, inputItem) && doesHaftMatchForRecipe(recipeItem, inputItem);
    }

    public static boolean doesMainMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
        CustomMaterial recipeMat = CustomToolHelper.getCustomPrimaryMaterial(recipeItem);
        CustomMaterial inputMat = CustomToolHelper.getCustomPrimaryMaterial(inputItem);

        if (recipeMat == null) {
            return true;
        }

        if (inputMat == null && recipeMat != null) {
            return false;
        }
        if (recipeMat != inputMat) {
            return false;
        }
        return true;
    }

    public static boolean doesHaftMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
        CustomMaterial recipeMat = CustomToolHelper.getCustomSecondaryMaterial(recipeItem);
        CustomMaterial inputMat = CustomToolHelper.getCustomSecondaryMaterial(inputItem);

        if (recipeMat == null) {
            return true;
        }

        if (inputMat == null && recipeMat != null) {
            return false;
        }
        if (recipeMat != inputMat) {
            return false;
        }
        return true;
    }

    public static void addComponentString(ItemStack tool, List list, CustomMaterial base) {
        addComponentString(tool, list, base, 1);
    }

    public static void addComponentString(ItemStack tool, List list, CustomMaterial base, float units) {
        if (base != null) {
            float mass = base.density * units;
            list.add(EnumChatFormatting.GOLD + base.getMaterialString());
            if (mass > 0)
                list.add(CustomMaterial.getWeightString(mass));

            if (base.isHeatable()) {
                int maxTemp = base.getHeatableStats()[0];
                int beyondMax = base.getHeatableStats()[1];
                list.add(StatCollector.translateToLocalFormatted("materialtype.workable.name", maxTemp, beyondMax));
            }
        }
    }

    public static float getBurnModifier(ItemStack fuel) {
        CustomMaterial mat = CustomMaterial.getMaterialFor(fuel, slot_main);
        if (mat != null && mat.type.equalsIgnoreCase("wood")) {
            return (2 * mat.density) + 0.5F;
        }
        return 1.0F;
    }

    public static String getReferenceName(ItemStack item) {
        String dam = "any";
        int d = item.getItemDamage();
        if (d != OreDictionary.WILDCARD_VALUE) {
            dam = "" + d;
        }

        return getReferenceName(item, dam);
    }

    public static String getReferenceName(ItemStack item, String dam) {
        return getReferenceName(item, dam, true);
    }

    public static String getReferenceName(ItemStack item, String dam, boolean tiered) {
        String reference = getSimpleReferenceName(item.getItem(), dam);

        if (tiered) {
            CustomMaterial base = getCustomPrimaryMaterial(item);
            CustomMaterial haft = getCustomSecondaryMaterial(item);

            if (base != null) {
                reference += "_" + base.getName();
            }
            if (haft != null) {
                reference += "_" + haft.getName();
            }
        }

        return reference;
    }

    public static String getSimpleReferenceName(Item item, String dam) {
        String reference = Item.itemRegistry.getNameForObject(item);
        if (reference == null) {
            return "";
        }
        return reference.toLowerCase() + "_@" + dam;
    }

    public static String getSimpleReferenceName(Item item) {
        return getSimpleReferenceName(item, "any");
    }

    public static boolean areToolsSame(ItemStack item1, ItemStack item2) {
        CustomMaterial main1 = getCustomPrimaryMaterial(item1);
        CustomMaterial main2 = getCustomSecondaryMaterial(item2);
        CustomMaterial haft1 = getCustomPrimaryMaterial(item1);
        CustomMaterial haft2 = getCustomSecondaryMaterial(item2);
        if ((main1 == null && main2 != null) || (main2 == null && main1 != null))
            return false;
        if ((haft1 == null && haft2 != null) || (haft2 == null && haft1 != null))
            return false;

        if (main1 != null && main2 != null && main1 != main2)
            return false;
        if (haft1 != null && haft2 != null && haft1 != haft2)
            return false;

        return true;
    }

    public static boolean isMythic(ItemStack result) {
        CustomMaterial main1 = getCustomPrimaryMaterial(result);
        CustomMaterial haft1 = getCustomPrimaryMaterial(result);
        if (main1 != null && main1.isUnbrekable()) {
            return true;
        }
        if (haft1 != null && haft1.isUnbrekable()) {
            return true;
        }
        return false;
    }

    public static void writeToPacket(ByteBuf packet, ItemStack stack) {
        packet.writeInt(stack != null ? Item.getIdFromItem(stack.getItem()) : 0);
        packet.writeInt(stack != null ? stack.stackSize : 0);
        packet.writeInt(stack != null ? stack.getItemDamage() : 0);
        packet.writeInt((stack != null && stack.isItemEnchanted()) ? 1 : 0);

        CustomMaterial main1 = getCustomPrimaryMaterial(stack);
        CustomMaterial haft1 = getCustomSecondaryMaterial(stack);

        ByteBufUtils.writeUTF8String(packet, main1 != null ? main1.getName() : "null");
        ByteBufUtils.writeUTF8String(packet, haft1 != null ? haft1.getName() : "null");
    }

    public static ItemStack readFromPacket(ByteBuf packet) {
        int id = packet.readInt();
        int ss = packet.readInt();
        int md = packet.readInt();
        boolean ec = packet.readInt() == 1;
        String main1 = ByteBufUtils.readUTF8String(packet);
        String haft1 = ByteBufUtils.readUTF8String(packet);
        Item item = Item.getItemById(id);

        if (item != null && ss > 0) {
            ItemStack stack = new ItemStack(item, ss, md);
            if (!main1.equalsIgnoreCase("null")) {
                CustomMaterial.addMaterial(stack, slot_main, main1);
            }
            if (!haft1.equalsIgnoreCase("null")) {
                CustomMaterial.addMaterial(stack, slot_haft, haft1);
            }
            return stack;
        }

        return null;
    }

    public static String getComponentMaterial(ItemStack item, String type) {
        if (item == null || type == null)
            return null;

        if (item.getItem() instanceof ItemHeated) {
            return getComponentMaterial(ItemHeated.getItem(item), type);
        }

        CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
        if (material != null) {
            return material.type.equalsIgnoreCase(type) ? material.name : null;
        }
        return null;
    }

    public static boolean hasAnyMaterial(ItemStack item) {
        return getCustomPrimaryMaterial(item) != null || getCustomSecondaryMaterial(item) != null;
    }

    public static ItemStack tryDeconstruct(ItemStack newitem, ItemStack mainItem) {
        String type = null;
        if (newitem != null && newitem.getItem() instanceof ITieredComponent) {
            type = ((ITieredComponent) newitem.getItem()).getMaterialType(newitem);
        }

        if (type != null) {
            CustomMaterial primary = CustomToolHelper.getCustomPrimaryMaterial(mainItem);
            CustomMaterial secondary = CustomToolHelper.getCustomSecondaryMaterial(mainItem);

            if (primary != null && primary.type.equalsIgnoreCase(type)) {
                CustomMaterial.addMaterial(newitem, slot_main, primary.name);
            } else {
                if (secondary != null && secondary.type.equalsIgnoreCase(type)) {
                    CustomMaterial.addMaterial(newitem, slot_main, secondary.name);
                }
            }
        }
        return newitem;
    }

    public static String getCustomStyle(ItemStack weapon) {
        if (weapon != null && weapon.getItem() instanceof ISpecialDesign) {
            return ((ISpecialDesign) weapon.getItem()).getDesign(weapon);
        }
        return null;
    }

}
