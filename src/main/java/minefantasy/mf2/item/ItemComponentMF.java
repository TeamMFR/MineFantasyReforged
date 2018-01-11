package minefantasy.mf2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.crafting.ITieredComponent;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.decor.BlockComponent;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemComponentMF extends Item implements ITieredComponent {
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
    private boolean isCustom = false;
    private float unitCount = 1;
    private IIcon componentTex = null;
    private String materialType = null;

    public ItemComponentMF(int rarity) {
        itemRarity = rarity;
    }

    public ItemComponentMF(String name) {
        this(name, 0);
    }

    public ItemComponentMF(String name, int rarity) {
        itemRarity = rarity;
        this.name = name;
        setTextureName("minefantasy2:component/" + name);
        this.setCreativeTab(CreativeTabMF.tabMaterialsMF);
        GameRegistry.registerItem(this, "MF_Com_" + name, MineFantasyII.MODID);

        this.setUnlocalizedName(name);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (this.getCreativeTab() != CreativeTabMF.tabMaterialsMF) {
            super.getSubItems(item, tab, list);
            return;
        }

        if (this != ComponentListMF.plank) {
            return;
        }

        for (Item ingot : ComponentListMF.ingots) {
            if (ingot == ComponentListMF.ingots[3]) {
                add(list, Items.iron_ingot);
            }
            if (ingot == ComponentListMF.ingots[7]) {
                add(list, Items.gold_ingot);
            }
            add(list, ingot);
        }

        if (isCustom) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                list.add(this.construct(customMat.name));
                list.add(((ItemComponentMF) ComponentListMF.plank_pane).construct(customMat.name));
            }
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                list.add(ComponentListMF.bar(customMat.name));
            }
        }

        add(list, ComponentListMF.nail);
        add(list, ComponentListMF.rivet);
        add(list, ComponentListMF.thread);
        add(list, ComponentListMF.clay_pot_uncooked);
        add(list, ComponentListMF.clay_pot);
        add(list, ComponentListMF.ingot_mould_uncooked);
        add(list, ComponentListMF.ingot_mould);

        add(list, ComponentListMF.leather_strip);
        add(list, ComponentListMF.rawhideSmall);
        add(list, ComponentListMF.rawhideMedium);
        add(list, ComponentListMF.rawhideLarge);
        add(list, ComponentListMF.hideSmall);
        add(list, ComponentListMF.hideMedium);
        add(list, ComponentListMF.hideLarge);

        add(list, ComponentListMF.oreCopper);
        add(list, ComponentListMF.oreTin);
        add(list, ComponentListMF.oreIron);
        add(list, ComponentListMF.oreSilver);
        add(list, ComponentListMF.oreGold);
        add(list, ComponentListMF.oreTungsten);

        add(list, ComponentListMF.flux);
        add(list, ComponentListMF.flux_strong);
        add(list, ComponentListMF.flux_pot);
        add(list, ComponentListMF.coal_flux);
        add(list, ComponentListMF.coke);
        add(list, ComponentListMF.diamond_shards);
        add(list, ComponentListMF.fletching);
        add(list, ComponentListMF.plant_oil);

        add(list, ComponentListMF.coalDust);
        add(list, ComponentListMF.iron_prep);
        add(list, ComponentListMF.obsidian_rock);
        add(list, ComponentListMF.sulfur);
        add(list, ComponentListMF.nitre);
        add(list, ComponentListMF.blackpowder);
        add(list, ComponentListMF.blackpowder_advanced);
        add(list, ComponentListMF.bomb_fuse);
        add(list, ComponentListMF.bomb_fuse_long);
        add(list, ComponentListMF.shrapnel);
        add(list, ComponentListMF.magma_cream_refined);
        add(list, ComponentListMF.bomb_casing_uncooked);
        add(list, ComponentListMF.bomb_casing);
        add(list, ComponentListMF.mine_casing_uncooked);
        add(list, ComponentListMF.mine_casing);
        add(list, ComponentListMF.bomb_casing_iron);
        add(list, ComponentListMF.mine_casing_iron);
        add(list, ComponentListMF.bomb_casing_obsidian);
        add(list, ComponentListMF.mine_casing_obsidian);
        add(list, ComponentListMF.bomb_casing_crystal);
        add(list, ComponentListMF.mine_casing_crystal);
        add(list, ComponentListMF.bomb_casing_arrow);
        add(list, ComponentListMF.bomb_casing_bolt);

        add(list, ComponentListMF.clay_brick);
        add(list, ComponentListMF.kaolinite);
        add(list, ComponentListMF.kaolinite_dust);
        add(list, ComponentListMF.fireclay);
        add(list, ComponentListMF.fireclay_brick);
        add(list, ComponentListMF.strong_brick);
        add(list, ComponentListMF.dragon_heart);
        add(list, ComponentListMF.ornate_items);

        add(list, ComponentListMF.talisman_lesser);
        add(list, ComponentListMF.talisman_greater);

        add(list, ComponentListMF.bolt);
        add(list, ComponentListMF.iron_frame);
        add(list, ComponentListMF.iron_strut);
        add(list, ComponentListMF.steel_tube);
        add(list, ComponentListMF.bronze_gears);
        add(list, ComponentListMF.tungsten_gears);
        add(list, ComponentListMF.cogwork_shaft);
        add(list, ComponentListMF.ingotCompositeAlloy);

        add(list, ComponentListMF.crossbow_handle_wood);
        add(list, ComponentListMF.crossbow_stock_wood);
        add(list, ComponentListMF.crossbow_stock_iron);

        add(list, ComponentListMF.cross_arms_basic);
        add(list, ComponentListMF.cross_arms_light);
        add(list, ComponentListMF.cross_arms_heavy);
        add(list, ComponentListMF.cross_arms_advanced);

        add(list, ComponentListMF.cross_ammo);
        add(list, ComponentListMF.cross_scope);
        add(list, ComponentListMF.cross_bayonet);

        add(list, ComponentListMF.copper_coin);
        add(list, ComponentListMF.silver_coin);
        add(list, ComponentListMF.gold_coin);
    }

    private void add(List list, Item item) {
        list.add(new ItemStack(item));
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {

        super.addInformation(item, user, list, extra);
        if (isCustom) {
            CustomToolHelper.addComponentString(item, list,
                    CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main), this.unitCount);
        }
    }

    public ItemComponentMF setCustom(float units, String type) {
        canRepair = false;
        this.unitCount = units;
        setTextureName("minefantasy2:custom/component/" + name);
        isCustom = true;
        this.setHasSubtypes(true);
        this.materialType = type;
        return this;
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        if (isCustom) {
            componentTex = reg.registerIcon(this.getIconString());
        }
        super.registerIcons(reg);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return isCustom;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (isCustom) {
            return componentTex;
        }
        return super.getIcon(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    //// Probably not the cleanest way to do this, but I decided to because for this
    //// item,
    /// the color will always be only one layer from only one material

    public int getColorFromItemStack(ItemStack item, int layer) {
        // return CustomToolHelper.getColourFromItemStack(item, layer,
        // super.getColorFromItemStack(item, layer));
        if (isCustom) {
            CustomMaterial mat = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
            if (mat != null)
                return mat.getColourInt();
        }
        return super.getColorFromItemStack(item, layer);
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

    public ItemComponentMF setStoragePlacement(String type, String tex) {
        this.blocktex = tex;
        this.storageType = type;
        return this;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote && storageType != null) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, user, false);

            if (movingobjectposition == null) {
                return item;
            } else {
                int placed = BlockComponent.useComponent(item, storageType, blocktex, world, user,
                        movingobjectposition);
                if (placed > 0) {
                    item.stackSize -= placed;
                    return item;
                }
            }
        }
        return super.onItemRightClick(item, world, user);
    }
}
