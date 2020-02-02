package minefantasy.mfr.init;

import minefantasy.mfr.item.weapon.ItemSwordMF;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class CreativeTabMFR extends CreativeTabs {
    public static CreativeTabs tabTool = new CreativeTabMFR(CreativeTabs.getNextID(), "forgedtool", 1);
    public static CreativeTabs tabWeapon = new CreativeTabMFR(CreativeTabs.getNextID(), "forgedweapon", 2);
    public static CreativeTabs tabArmour = new CreativeTabMFR(CreativeTabs.getNextID(), "forgedarmour", 3);
    public static CreativeTabs tabArcher = new CreativeTabMFR(CreativeTabs.getNextID(), "MFBows", 4);
    public static CreativeTabs tabUtil = new CreativeTabMFR(CreativeTabs.getNextID(), "MFUtil", 5);
    public static CreativeTabs tabGadget = new CreativeTabMFR(CreativeTabs.getNextID(), "MFGadgets", 6);
    public static CreativeTabs tabMaterialsMFR = new CreativeTabMFR(CreativeTabs.getNextID(), "MFMaterials", 7);
    public static CreativeTabs tabToolAdvanced = new CreativeTabMFR(CreativeTabs.getNextID(), "MFadvancedtool", 8);
    public static CreativeTabs tabCraftTool = new CreativeTabMFR(CreativeTabs.getNextID(), "MFcrafttool", 9);
    public static CreativeTabs tabOres = new CreativeTabMFR(CreativeTabs.getNextID(), "MFore", 10);
    public static CreativeTabs tabFood = new CreativeTabMFR(CreativeTabs.getNextID(), "MFfood", 11);
    public static CreativeTabs tabDragonforged = new CreativeTabMFR(CreativeTabs.getNextID(), "MFdragonforged", 12);
    public static CreativeTabs tabOrnate = new CreativeTabMFR(CreativeTabs.getNextID(), "MFornate", 13);
    public static CreativeTabs tabOldTools = new CreativeTabMFR(CreativeTabs.getNextID(), "MFOld", 14);

    private int type;

    CreativeTabMFR(int id, String item, int t) {
        super(id, item);
        type = t;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        switch (type) {
            case 1:
                return CustomToolListMFR.standard_axe.construct("Steel", "OakWood");
            case 2:
                return ( CustomToolListMFR.standard_sword).construct("Steel", "OakWood");
            case 3:
                return CustomArmourListMFR.standard_plate_helmet.construct("Steel");
            case 4:
                return CustomToolListMFR.standard_bow.construct("Steel", "OakWood");
            case 5:
                return new ItemStack(BlockListMFR.ANVIL[1]);
            case 6:
                return new ItemStack(ToolListMFR.bomb_custom);
            case 7:
                return ComponentListMFR.plank.construct("OakWood");
            case 8:
                return CustomToolListMFR.standard_handpick.construct("Steel", "OakWood");
            case 9:
                return CustomToolListMFR.standard_hammer.construct("Steel", "OakWood");
            case 10:
                return new ItemStack(BlockListMFR.ORE_COPPER);
            case 11:
                return new ItemStack(FoodListMFR.sweetroll);
            case 12:
                return new ItemStack(ComponentListMFR.dragon_heart);
            case 13:
                return new ItemStack(ComponentListMFR.ornate_items);
            case 14:
                return new ItemStack(ToolListMFR.pickStone);
        }
        return new ItemStack(ComponentListMFR.ingots[3]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(CustomToolListMFR.standard_axe);
    }
}
