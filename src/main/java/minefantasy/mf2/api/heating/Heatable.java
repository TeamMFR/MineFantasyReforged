package minefantasy.mf2.api.heating;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public class Heatable {
    public static final int forgeMaximumMetalHeat = 5000;
    public static final String NBT_Item = "MFHeatable_ItemSave";
    // public static final String NBT_ItemID = "MFHeatable_ItemID";
    // public static final String NBT_SubID = "MFHeatable_SubID";
    public static final String NBT_ShouldDisplay = "MFHeatable_DisplayTemperature";
    public static final String NBT_CurrentTemp = "MFHeatable_Temperature";
    public static final String NBT_WorkableTemp = "MFHeatable_WorkTemp";
    public static final String NBT_UnstableTemp = "MFHeatable_UnstableTemp";
    public static boolean requiresHeating = true;
    public static HashMap<String, Heatable> registerList = new HashMap<String, Heatable>();
    /**
     * Hardcore Crafting: Should quencing in inproper sources damage items
     */
    public static boolean HCCquenchRuin = true;
    /**
     * The min heat the ingot must be to forge with mesured in celcius
     */
    private final int minTemperature;
    /**
     * The heat when it becomes unstable mesured in celcius
     */
    private final int unstableTemperature;
    /**
     * The max heat until the ingot is destroyed mesured in celcius
     */
    private final int maxTemperature;
    /**
     * The item that's used
     */
    protected ItemStack object;

    public Heatable(ItemStack item, int min, int unstable, int max) {
        this.object = item;
        this.minTemperature = min;
        this.unstableTemperature = unstable;
        this.maxTemperature = max;
    }

    public static void addItem(ItemStack item, int min, int unstable, int max) {
        registerList.put(getRegistrationForItem(item), new Heatable(item, min, unstable, max));
    }

    public static boolean canHeatItem(ItemStack item) {
        return loadStats(item) != null;
    }

    public static Heatable loadStats(ItemStack item) {
        if (item == null)
            return null;

        if (registerList.isEmpty())
            return null;

        Heatable stats = findRegister(item);
        if (stats != null) {
            if (stats.object.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                if (stats.object.getItem() == item.getItem()) {
                    return stats;
                }
            } else if (stats.object.isItemEqual(item)) {
                return stats;
            }
        }

        return null;
    }

    /**
     * 0 = nothing, 1 = soft, 2 = unstable
     */
    public static byte getHeatableStage(ItemStack item) {
        if (item == null || !(item.getItem() instanceof IHotItem)) {
            return 0;
        }
        if (item != null && item.hasTagCompound()) {
            int temp = getTemp(item);
            int work = item.getTagCompound().getInteger(NBT_WorkableTemp);
            int unstable = item.getTagCompound().getInteger(NBT_UnstableTemp);
            if (temp > unstable)
                return (byte) 2;
            if (temp > work)
                return (byte) 1;
        }
        return (byte) 0;
    }

    public static int getWorkTemp(ItemStack item) {
        if (item == null || !(item.getItem() instanceof IHotItem)) {
            return 0;
        }
        NBTTagCompound tag = getNBT(item);

        if (tag.hasKey(NBT_WorkableTemp))
            return tag.getInteger(NBT_WorkableTemp);

        return 0;
    }

    public static int getUnstableTemp(ItemStack item) {
        if (item == null || !(item.getItem() instanceof IHotItem)) {
            return 0;
        }
        NBTTagCompound tag = getNBT(item);

        if (tag.hasKey(NBT_UnstableTemp))
            return tag.getInteger(NBT_UnstableTemp);

        return 0;
    }

    public static int getTemp(ItemStack item) {
        if (item == null || !(item.getItem() instanceof IHotItem)) {
            return 0;
        }
        NBTTagCompound tag = getNBT(item);

        if (tag.hasKey(NBT_CurrentTemp))
            return tag.getInteger(NBT_CurrentTemp);

        return 0;
    }

    /**
     * Gets a hot item
     *
     * @param item   the hot item
     * @param hazard the amount the source is hazardous (damaging the item): usually a
     *               percent dura loss
     * @return what item is heated
     */
    public static ItemStack getQuenchedItem(ItemStack item, float hazard) {
        ItemStack cold = Heatable.getItem(item);

        if (HCCquenchRuin && cold.isItemStackDamageable() && hazard > 0) {
            cold.setItemDamage((int) (cold.getMaxDamage() * hazard / 100F));
        }

        return cold;
    }

    public static ItemStack getItem(ItemStack item) {
        if (item == null || !(item.getItem() instanceof IHotItem)) {
            return null;
        }
        NBTTagCompound tag = getNBT(item);

        if (tag.hasKey(NBT_Item)) {
            return ItemStack.loadItemStackFromNBT(tag.getCompoundTag(NBT_Item));
        }

        return null;
    }

    private static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        return item.getTagCompound();
    }

    public static boolean isWorkable(ItemStack inputItem) {
        if (inputItem == null || !(inputItem.getItem() instanceof IHotItem)) {
            return true;
        }
        if (inputItem != null && inputItem.getItem() instanceof IHotItem) {
            return getHeatableStage(inputItem) == 1;
        }
        return true;
    }

    private static Heatable findRegister(ItemStack item) {
        Heatable specific = registerList.get(item.getItem().getUnlocalizedName() + "_" + item.getItemDamage());// Try
        // Specific
        // first
        if (specific != null) {
            return specific;
        }
        return registerList.get(item.getUnlocalizedName() + "_any");// Try Any;
    }

    public static String getRegistrationForItem(ItemStack item) {
        String s;
        if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            s = item.getItem().getUnlocalizedName() + "_any";
        } else {
            s = item.getItem().getUnlocalizedName() + "_" + item.getItemDamage();
        }
        return s;
    }

    public int getWorkableStat(ItemStack item) {
        if (this.minTemperature == -1) {
            CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
            if (material != null)
                return material.getHeatableStats()[0];
        }
        return this.minTemperature;
    }

    public int getUnstableStat(ItemStack item) {
        if (this.unstableTemperature == -1) {
            CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
            if (material != null)
                return material.getHeatableStats()[1];
        }
        return this.unstableTemperature;
    }
}
