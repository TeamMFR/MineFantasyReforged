package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.decor.BlockComponent;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemComponentMFR extends ItemBaseMFR implements ITieredComponent {
    protected String name;
    protected int itemRarity;
    // STORAGE
    protected String blocktex;
    protected String storageType;

    /*
     * private int itemRarity;
     *
     * @Override public EnumRarity getRarity(ItemStack item) { int lvl =
     * itemRarity+1;
     *
     * if(item.isItemEnchanted()) { if(lvl == 0) { lvl++; } lvl ++; } if(lvl >=
     * ToolListMF.rarity.length) { lvl = ToolListMF.rarity.length-1; } return
     * ToolListMF.rarity[lvl]; }
     */
    // ===================================================== CUSTOM START
    // =============================================================\\
    private float unitCount = 1;
    private boolean isCustom = false;
    private String materialType = null;

    public ItemComponentMFR(String name) {
        this(name, 0);
    }

    public ItemComponentMFR(String name, int rarity) {
        super(name);
        itemRarity = rarity;
        this.name = name;
        this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        if (this.getCreativeTab() != CreativeTabMFR.tabMaterialsMFR) {
            super.getSubItems(tab, items);
            return;
        }

        if (this != ComponentListMFR.TIMBER) {
            return;
        }

        for (Item ingot : ComponentListMFR.INGOTS) {
            if (ingot == ComponentListMFR.INGOTS[3]) {
                add(items, Items.IRON_INGOT);
            }
            if (ingot == ComponentListMFR.INGOTS[6]) {
                add(items, Items.GOLD_INGOT);
            }
            add(items, ingot);
        }

        if (isCustom) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            for (CustomMaterial customMat : wood) {
                items.add(this.construct(customMat.name));
                items.add(((ItemComponentMFR) ComponentListMFR.TIMBER_PANE).construct(customMat.name));
            }
            for (CustomMaterial customMat : metal) {
                items.add(ComponentListMFR.bar(customMat.name));
            }
        }


        add(items, ComponentListMFR.NAIL);
        add(items, ComponentListMFR.RIVET);
        add(items, ComponentListMFR.THREAD);
        add(items, ComponentListMFR.CLAY_POT_UNCOOKED);
        add(items, ComponentListMFR.CLAY_POT);
        add(items, ComponentListMFR.INGOT_MOULD_UNCOOKED);
        add(items, ComponentListMFR.INGOT_MOULD);

        add(items, ComponentListMFR.LEATHER_STRIP);
        add(items, ComponentListMFR.RAWHIDE_SMALL);
        add(items, ComponentListMFR.RAWHIDE_MEDIUM);
        add(items, ComponentListMFR.RAWHIDE_LARGE);
        add(items, ComponentListMFR.HIDE_SMALL);
        add(items, ComponentListMFR.HIDE_MEDIUM);
        add(items, ComponentListMFR.HIDE_LARGE);

        add(items, ComponentListMFR.ORE_COPPER);
        add(items, ComponentListMFR.ORE_TIN);
        add(items, ComponentListMFR.ORE_IRON);
        add(items, ComponentListMFR.ORE_SILVER);
        add(items, ComponentListMFR.ORE_GOLD);
        add(items, ComponentListMFR.ORE_TUNGSTEN);

        add(items, ComponentListMFR.FLUX);
        add(items, ComponentListMFR.FLUX_STRONG);
        add(items, ComponentListMFR.FLUX_POT);
        add(items, ComponentListMFR.COAL_FLUX);
        add(items, ComponentListMFR.COKE);
        add(items, ComponentListMFR.DIAMOND_SHARDS);
        add(items, ComponentListMFR.FLETCHING);
        add(items, ComponentListMFR.PLANT_OIL);

        add(items, ComponentListMFR.COAL_DUST);
        add(items, ComponentListMFR.IRON_PREP);
        add(items, ComponentListMFR.OBSIDIAN_ROCK);
        add(items, ComponentListMFR.SULFUR);
        add(items, ComponentListMFR.NITRE);
        add(items, ComponentListMFR.BLACKPOWDER);
        add(items, ComponentListMFR.BLACKPOWDER_ADVANCED);
        add(items, ComponentListMFR.BOMB_FUSE);
        add(items, ComponentListMFR.BOMB_FUSE_LONG);
        add(items, ComponentListMFR.SHRAPNEL);
        add(items, ComponentListMFR.MAGMA_CREAM_REFINED);
        add(items, ComponentListMFR.BOMB_CASING_UNCOOKED);
        add(items, ComponentListMFR.BOMB_CASING);
        add(items, ComponentListMFR.MINE_CASING_UNCOOKED);
        add(items, ComponentListMFR.MINE_CASING);
        add(items, ComponentListMFR.BOMB_CASING_IRON);
        add(items, ComponentListMFR.MINE_CASING_IRON);
        add(items, ComponentListMFR.BOMB_CASING_OBSIDIAN);
        add(items, ComponentListMFR.MINE_CASING_OBSIDIAN);
        add(items, ComponentListMFR.BOMB_CASING_CRYSTAL);
        add(items, ComponentListMFR.MINE_CASING_CRYSTAL);
        add(items, ComponentListMFR.BOMB_CASING_ARROW);
        add(items, ComponentListMFR.BOMB_CASING_BOLT);

        add(items, ComponentListMFR.CLAY_BRICK);
        add(items, ComponentListMFR.KAOLINITE);
        add(items, ComponentListMFR.KAOLINITE_DUST);
        add(items, ComponentListMFR.FIRECLAY);
        add(items, ComponentListMFR.FIRECLAY_BRICK);
        add(items, ComponentListMFR.STRONG_BRICK);
        add(items, ComponentListMFR.DRAGON_HEART);
        add(items, ComponentListMFR.ORNATE_ITEMS);

        add(items, ComponentListMFR.TALISMAN_LESSER);
        add(items, ComponentListMFR.TALISMAN_GREATER);

        add(items, ComponentListMFR.BOLT);
        add(items, ComponentListMFR.IRON_FRAME);
        add(items, ComponentListMFR.IRON_STRUT);
        add(items, ComponentListMFR.STEEL_TUBE);
        add(items, ComponentListMFR.BRONZE_GEARS);
        add(items, ComponentListMFR.TUNGSTEN_GEARS);
        add(items, ComponentListMFR.COGWORK_SHAFT);
        add(items, ComponentListMFR.COMPOSITE_ALLOY_INGOT);

        add(items, ComponentListMFR.CROSSBOW_HANDLE_WOOD);
        add(items, ComponentListMFR.CROSSBOW_STOCK_WOOD);
        add(items, ComponentListMFR.CROSSBOW_STOCK_IRON);

        add(items, ComponentListMFR.CROSSBOW_ARMS_BASIC);
        add(items, ComponentListMFR.CROSSBOW_ARMS_LIGHT);
        add(items, ComponentListMFR.CROSSBOW_ARMS_HEAVY);
        add(items, ComponentListMFR.CROSSBOW_ARMS_ADVANCED);

        add(items, ComponentListMFR.CROSSBOW_AMMO);
        add(items, ComponentListMFR.CROSSBOW_SCOPE);
        add(items, ComponentListMFR.CROSSBOW_BAYONET);

        add(items, ComponentListMFR.COPPER_COIN);
        add(items, ComponentListMFR.SILVER_COIN);
        add(items, ComponentListMFR.GOLD_COIN);
    }

    private void add(List<ItemStack> list, Item item) {
        list.add(new ItemStack(item));
    }

    @Override
    public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flag) {

        super.addInformation(item, world, list, flag);
        if (isCustom) {
            CustomToolHelper.addComponentString(item, list, CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main), this.unitCount);
        }
    }

    public ItemComponentMFR setCustom(float units, String type) {
        canRepair = false;
        this.unitCount = units;
        isCustom = true;
        this.materialType = type;
        return this;
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, 1.0F);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }

    public ItemStack construct(String main) {
        return construct(main, 1);
    }

    public ItemStack construct(String main, int stacksize) {
        return CustomToolHelper.constructSingleColoredLayer(this, main, stacksize);
    }
    // ====================================================== CUSTOM END
    // ==============================================================\\

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        if (isCustom) {
            return CustomToolHelper.getLocalisedName(item, "item.commodity_" + name + ".name");
        }
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }

    @Override
    public String getMaterialType(ItemStack item) {
        return isCustom ? materialType : null;
    }

    public ItemComponentMFR setStoragePlacement(String type, String tex) {
        this.blocktex = tex;
        this.storageType = type;
        return this;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (!world.isRemote && storageType != null) {
            RayTraceResult rayTraceResult = this.rayTrace(world, user, false);

            if (rayTraceResult == null) {
                return ActionResult.newResult(EnumActionResult.PASS, item);
            } else {
                int placed = BlockComponent.useComponent(item, storageType, blocktex, world, user, rayTraceResult);
                if (placed > 0) {
                    item.shrink(placed);
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
            }
        }
        return super.onItemRightClick(world, user, hand);
    }
}
