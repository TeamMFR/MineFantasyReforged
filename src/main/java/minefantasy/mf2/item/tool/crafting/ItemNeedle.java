package minefantasy.mf2.item.tool.crafting;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemNeedle extends ItemTool implements IToolMaterial, IToolMF {
    protected int itemRarity;
    private ToolMaterial material;
    private int tier;
    private float baseDamage;
    private String name;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;

    public ItemNeedle(String name, ToolMaterial material, int rarity, int tier) {
        super(0F, material, Sets.newHashSet(new Block[]{}));
        this.material = material;
        this.name = name;
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabOldTools);
        this.tier = tier;
        setTextureName("minefantasy2:Tool/Crafting/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Weapon modifier", 0.5D, 0));

        return map;
    }

    public ItemNeedle setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemNeedle setEfficiencyMod(float efficiencyMod) {
        this.efficiencyMod = efficiencyMod;
        return this;
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        if (isCustom) {
            detailTex = reg.registerIcon(this.getIconString() + "_detail");
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
        if (isCustom && pass > 0 && detailTex != null) {
            return detailTex;
        }
        return super.getIcon(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        return CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
    }

    public ItemStack construct(String main) {
        return CustomToolHelper.construct(this, main, null);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        return CustomToolHelper.getHarvestLevel(stack, super.getHarvestLevel(stack, toolClass));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (isCustom) {
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                if (MineFantasyII.isDebug() || customMat.getItem() != null) {
                    list.add(this.construct(customMat.name));
                }
            }
        } else {
            super.getSubItems(item, tab, list);
        }
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        if (isCustom) {
            CustomToolHelper.addInformation(item, list);
        }
        super.addInformation(item, user, list, extra);
    }

    @Override
    public float getEfficiency(ItemStack item) {
        return CustomToolHelper.getEfficiency(item, toolMaterial.getEfficiencyOnProperMaterial(), efficiencyMod);
    }

    @Override
    public int getTier(ItemStack item) {
        return CustomToolHelper.getCrafterTier(item, tier);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }

    // ====================================================== CUSTOM END
    // ==============================================================\\
    @Override
    public String getToolType(ItemStack item) {
        return "needle";
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }
}
