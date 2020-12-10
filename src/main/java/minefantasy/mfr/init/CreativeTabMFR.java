package minefantasy.mfr.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        super(item);
        type = t;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        switch (type) {
            case 1:
                return CustomToolListMFR.STANDARD_AXE.construct("steel", "OakWood");
            case 2:
                return (CustomToolListMFR.STANDARD_SWORD).construct("steel", "OakWood");
            case 3:
                return CustomArmourListMFR.STANDARD_PLATE_HELMET.construct("steel");
            case 4:
                return CustomToolListMFR.STANDARD_BOW.construct("steel", "OakWood");
            case 5:
                return new ItemStack(MineFantasyBlocks.ANVIL_IRON);
            case 6:
                return ToolListMFR.BOMB_CUSTOM.createBomb("ceramic", "basic", "basic", "black_powder", 1);
            case 7:
                return ComponentListMFR.TIMBER.construct("OakWood");
            case 8:
                return CustomToolListMFR.STANDARD_HANDPICK.construct("steel", "OakWood");
            case 9:
                return CustomToolListMFR.STANDARD_HAMMER.construct("steel", "OakWood");
            case 10:
                return new ItemStack(MineFantasyBlocks.COPPER_ORE);
            case 11:
                return new ItemStack(FoodListMFR.SWEETROLL);
            case 12:
                return new ItemStack(ComponentListMFR.DRAGON_HEART);
            case 13:
                return new ItemStack(ComponentListMFR.ORNATE_ITEMS);
            case 14:
                return new ItemStack(ToolListMFR.STONE_PICK);
        }
        return new ItemStack(ComponentListMFR.PIG_IRON_INGOT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(CustomToolListMFR.STANDARD_AXE);
    }
}
