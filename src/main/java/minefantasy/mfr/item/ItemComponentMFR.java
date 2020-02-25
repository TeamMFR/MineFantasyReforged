package minefantasy.mfr.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.decor.BlockComponent;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemComponentMFR extends Item implements ITieredComponent {
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

    public ItemComponentMFR(int rarity) {
        itemRarity = rarity;
    }

    public ItemComponentMFR(String name) {
        this(name, 0);
    }

    public ItemComponentMFR(String name, int rarity) {
        itemRarity = rarity;
        this.name = name;
        this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.getCreativeTab() != CreativeTabMFR.tabMaterialsMFR) {
            super.getSubItems(tab, items);
            return;
        }

        if (this != ComponentListMFR.plank) {
            return;
        }

        for (Item ingot : ComponentListMFR.ingots) {
            if (ingot == ComponentListMFR.ingots[3]) {
                add(items, Items.IRON_INGOT);
            }
            if (ingot == ComponentListMFR.ingots[7]) {
                add(items, Items.GOLD_INGOT);
            }
            add(items, ingot);
        }

        if (isCustom) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                items.add(this.construct(customMat.name));
                items.add(((ItemComponentMFR) ComponentListMFR.plank_pane).construct(customMat.name));
            }
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                items.add(ComponentListMFR.bar(customMat.name));
            }
        }

        add(items, ComponentListMFR.nail);
        add(items, ComponentListMFR.rivet);
        add(items, ComponentListMFR.thread);
        add(items, ComponentListMFR.clay_pot_uncooked);
        add(items, ComponentListMFR.clay_pot);
        add(items, ComponentListMFR.ingot_mould_uncooked);
        add(items, ComponentListMFR.ingot_mould);

        add(items, ComponentListMFR.leather_strip);
        add(items, ComponentListMFR.rawhideSmall);
        add(items, ComponentListMFR.rawhideMedium);
        add(items, ComponentListMFR.rawhideLarge);
        add(items, ComponentListMFR.hideSmall);
        add(items, ComponentListMFR.hideMedium);
        add(items, ComponentListMFR.hideLarge);

        add(items, ComponentListMFR.oreCopper);
        add(items, ComponentListMFR.oreTin);
        add(items, ComponentListMFR.oreIron);
        add(items, ComponentListMFR.oreSilver);
        add(items, ComponentListMFR.oreGold);
        add(items, ComponentListMFR.oreTungsten);

        add(items, ComponentListMFR.flux);
        add(items, ComponentListMFR.flux_strong);
        add(items, ComponentListMFR.flux_pot);
        add(items, ComponentListMFR.coal_flux);
        add(items, ComponentListMFR.coke);
        add(items, ComponentListMFR.diamond_shards);
        add(items, ComponentListMFR.fletching);
        add(items, ComponentListMFR.plant_oil);

        add(items, ComponentListMFR.coalDust);
        add(items, ComponentListMFR.iron_prep);
        add(items, ComponentListMFR.obsidian_rock);
        add(items, ComponentListMFR.sulfur);
        add(items, ComponentListMFR.nitre);
        add(items, ComponentListMFR.blackpowder);
        add(items, ComponentListMFR.blackpowder_advanced);
        add(items, ComponentListMFR.bomb_fuse);
        add(items, ComponentListMFR.bomb_fuse_long);
        add(items, ComponentListMFR.shrapnel);
        add(items, ComponentListMFR.magma_cream_refined);
        add(items, ComponentListMFR.bomb_casing_uncooked);
        add(items, ComponentListMFR.bomb_casing);
        add(items, ComponentListMFR.mine_casing_uncooked);
        add(items, ComponentListMFR.mine_casing);
        add(items, ComponentListMFR.bomb_casing_iron);
        add(items, ComponentListMFR.mine_casing_iron);
        add(items, ComponentListMFR.bomb_casing_obsidian);
        add(items, ComponentListMFR.mine_casing_obsidian);
        add(items, ComponentListMFR.bomb_casing_crystal);
        add(items, ComponentListMFR.mine_casing_crystal);
        add(items, ComponentListMFR.bomb_casing_arrow);
        add(items, ComponentListMFR.bomb_casing_bolt);

        add(items, ComponentListMFR.clay_brick);
        add(items, ComponentListMFR.kaolinite);
        add(items, ComponentListMFR.kaolinite_dust);
        add(items, ComponentListMFR.fireclay);
        add(items, ComponentListMFR.fireclay_brick);
        add(items, ComponentListMFR.strong_brick);
        add(items, ComponentListMFR.dragon_heart);
        add(items, ComponentListMFR.ornate_items);

        add(items, ComponentListMFR.talisman_lesser);
        add(items, ComponentListMFR.talisman_greater);

        add(items, ComponentListMFR.bolt);
        add(items, ComponentListMFR.iron_frame);
        add(items, ComponentListMFR.iron_strut);
        add(items, ComponentListMFR.steel_tube);
        add(items, ComponentListMFR.bronze_gears);
        add(items, ComponentListMFR.tungsten_gears);
        add(items, ComponentListMFR.cogwork_shaft);
        add(items, ComponentListMFR.ingotCompositeAlloy);

        add(items, ComponentListMFR.crossbow_handle_wood);
        add(items, ComponentListMFR.crossbow_stock_wood);
        add(items, ComponentListMFR.crossbow_stock_iron);

        add(items, ComponentListMFR.cross_arms_basic);
        add(items, ComponentListMFR.cross_arms_light);
        add(items, ComponentListMFR.cross_arms_heavy);
        add(items, ComponentListMFR.cross_arms_advanced);

        add(items, ComponentListMFR.cross_ammo);
        add(items, ComponentListMFR.cross_scope);
        add(items, ComponentListMFR.cross_bayonet);

        add(items, ComponentListMFR.copper_coin);
        add(items, ComponentListMFR.silver_coin);
        add(items, ComponentListMFR.gold_coin);
    }

    private void add(List list, Item item) {
        list.add(new ItemStack(item));
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {

        super.addInformation(item, world, list, flag);
        if (isCustom) {
            CustomToolHelper.addComponentString(item, list,
                    CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main), this.unitCount);
        }
    }

    public ItemComponentMFR setCustom(float units, String type) {
        canRepair = false;
        this.unitCount = units;
        isCustom = true;
        this.setHasSubtypes(true);
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
                int placed = BlockComponent.useComponent(item, storageType, blocktex, world, user,
                        rayTraceResult);
                if (placed > 0) {
                    item.shrink(placed);
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
            }
        }
        return super.onItemRightClick(world, user, hand);
    }
}
