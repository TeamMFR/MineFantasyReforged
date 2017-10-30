package minefantasy.mf2.item.tool.crafting;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.api.weapon.IRackItem;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemSaw extends ItemAxe implements IToolMaterial, IDamageType, IToolMF, IRackItem {
    protected int itemRarity;
    private float hitDamage;
    private float baseDamage;
    private String name;
    private int tier;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    /**
     */
    public ItemSaw(String name, ToolMaterial material, int rarity, int tier) {
        super(material);
        this.tier = tier;
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabOldTools);
        this.hitDamage = (2.0F + material.getDamageVsEntity()) / 2F;
        setTextureName("minefantasy2:Tool/Crafting/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        this.name = name;
        this.setHarvestLevel("axe", material.getHarvestLevel());
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Tool modifier", this.hitDamage, 0));
        return multimap;
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        return new float[]{1, 0, 0};
    }

    @Override
    public String getToolType(ItemStack item) {
        return "saw";
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0.5F;
    }

    public ItemSaw setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemSaw setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    @Override
    public float getEfficiency(ItemStack item) {
        return CustomToolHelper.getEfficiency(item, toolMaterial.getEfficiencyOnProperMaterial(), efficiencyMod);
    }

    @Override
    public int getTier(ItemStack item) {
        return CustomToolHelper.getCrafterTier(item, tier);
    }

    public ItemSaw setEfficiencyMod(float efficiencyMod) {
        this.efficiencyMod = efficiencyMod;
        return this;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack item) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Weapon modifier", getMeleeDamage(item), 0));

        return map;
    }

    /**
     * Gets a stack-sensitive value for the melee dmg
     */
    protected float getMeleeDamage(ItemStack item) {
        return baseDamage + CustomToolHelper.getMeleeDamage(item, toolMaterial.getDamageVsEntity());
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        if (isCustom) {
            haftTex = reg.registerIcon(this.getIconString() + "_haft");
            detailTex = reg.registerIcon(this.getIconString() + "_detail");

        }
        super.registerIcons(reg);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return isCustom;
    }

    // Returns the number of render passes this item has.
    @Override
    public int getRenderPasses(int metadata) {
        return 3;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (isCustom && pass == 1 && haftTex != null) {
            return haftTex;
        }
        if (isCustom && pass == 2 && detailTex != null) {
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

    public ItemStack construct(String main, String haft) {
        return CustomToolHelper.construct(this, main, haft);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (!ForgeHooks.isToolEffective(stack, block, meta)) {
            return this.func_150893_a(stack, block);
        }
        return CustomToolHelper.getEfficiency(stack, super.getDigSpeed(stack, block, meta), efficiencyMod);
    }

    public float func_150893_a(ItemStack stack, Block block) {
        float base = super.func_150893_a(stack, block);
        return base <= 1.0F ? base
                : CustomToolHelper.getEfficiency(stack, this.efficiencyOnProperMaterial, efficiencyMod);
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
                    list.add(this.construct(customMat.name, "OakWood"));
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
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }
    // ====================================================== CUSTOM END
    // ==============================================================\\

    @Override
    public float getScale(ItemStack itemstack) {
        return 1.0F;
    }

    @Override
    public float getOffsetX(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getOffsetY(ItemStack itemstack) {
        return 9F / 16F;
    }

    @Override
    public float getOffsetZ(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getRotationOffset(ItemStack itemstack) {
        return 90F;
    }

    @Override
    public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
        return true;
    }

    @Override
    public boolean isSpecialRender(ItemStack item) {
        return false;
    }
}
