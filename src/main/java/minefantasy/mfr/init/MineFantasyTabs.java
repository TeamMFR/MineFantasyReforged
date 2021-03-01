package minefantasy.mfr.init;

import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.material.WoodMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class MineFantasyTabs extends CreativeTabs {
    public static CreativeTabs tabTool = new MineFantasyTabs(CreativeTabs.getNextID(), "forgedtool", 1);
    public static CreativeTabs tabWeapon = new MineFantasyTabs(CreativeTabs.getNextID(), "forgedweapon", 2);
    public static CreativeTabs tabArmour = new MineFantasyTabs(CreativeTabs.getNextID(), "forgedarmour", 3);
    public static CreativeTabs tabArchery = new MineFantasyTabs(CreativeTabs.getNextID(), "MFBows", 4);
    public static CreativeTabs tabUtil = new MineFantasyTabs(CreativeTabs.getNextID(), "MFUtil", 5);
    public static CreativeTabs tabGadget = new MineFantasyTabs(CreativeTabs.getNextID(), "MFGadgets", 6);
    public static CreativeTabs tabMaterials = new MineFantasyTabs(CreativeTabs.getNextID(), "MFMaterials", 7);
    public static CreativeTabs tabToolAdvanced = new MineFantasyTabs(CreativeTabs.getNextID(), "MFadvancedtool", 8);
    public static CreativeTabs tabCraftTool = new MineFantasyTabs(CreativeTabs.getNextID(), "MFcrafttool", 9);
    public static CreativeTabs tabOres = new MineFantasyTabs(CreativeTabs.getNextID(), "MFore", 10);
    public static CreativeTabs tabFood = new MineFantasyTabs(CreativeTabs.getNextID(), "MFfood", 11);
    public static CreativeTabs tabDragonforged = new MineFantasyTabs(CreativeTabs.getNextID(), "MFdragonforged", 12);
    public static CreativeTabs tabOrnate = new MineFantasyTabs(CreativeTabs.getNextID(), "MFornate", 13);
    public static CreativeTabs tabOldTools = new MineFantasyTabs(CreativeTabs.getNextID(), "MFOld", 14);

    private int type;

    MineFantasyTabs(int id, String item, int t) {
        super(item);
        type = t;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        switch (type) {
            case 1:
                return CustomToolListMFR.STANDARD_AXE.construct(MetalMaterial.STEEL, WoodMaterial.OAK_WOOD);
            case 2:
                return (CustomToolListMFR.STANDARD_SWORD).construct(MetalMaterial.STEEL, WoodMaterial.OAK_WOOD);
            case 3:
                return CustomArmourListMFR.STANDARD_PLATE_HELMET.construct(MetalMaterial.STEEL);
            case 4:
                return CustomToolListMFR.STANDARD_BOW.construct(MetalMaterial.STEEL, WoodMaterial.OAK_WOOD);
            case 5:
                return new ItemStack(MineFantasyBlocks.ANVIL_IRON);
            case 6:
                return ToolListMFR.BOMB_CUSTOM.createBomb("ceramic", "basic", "basic", "black_powder", 1);
            case 7:
                return ComponentListMFR.TIMBER.construct(WoodMaterial.OAK_WOOD);
            case 8:
                return CustomToolListMFR.STANDARD_HANDPICK.construct(MetalMaterial.STEEL, WoodMaterial.OAK_WOOD);
            case 9:
                return CustomToolListMFR.STANDARD_HAMMER.construct(MetalMaterial.STEEL, WoodMaterial.OAK_WOOD);
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
