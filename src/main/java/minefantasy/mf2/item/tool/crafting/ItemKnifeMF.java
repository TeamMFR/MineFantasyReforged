package minefantasy.mf2.item.tool.crafting;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tool.IHuntingItem;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.api.weapon.WeaponClass;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import minefantasy.mf2.util.XSTRandom;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemKnifeMF extends ItemWeaponMF implements IToolMF, IHuntingItem {
    protected int itemRarity;
    private int tier;
    private float baseDamage;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    /**
     * Knives are weapons used for hunting, and tools used for processing
     */
    public ItemKnifeMF(String name, ToolMaterial material, int rarity, float weight, int tier) {
        super(material, name, rarity, weight);
        this.tier = tier;
        setTextureName("minefantasy2:Tool/Crafting/" + name);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            Block block = player.worldObj.getBlock(x, y, z);
            if (block instanceof IShearable) {
                IShearable target = (IShearable) block;
                if (target.isShearable(itemstack, player.worldObj, x, y, z)) {
                    XSTRandom rand = new XSTRandom();
                    if(rand.nextInt(10) < 3) { //knife has reduced drop and can't be used to shear entity's
                        ArrayList<ItemStack> drops = target.onSheared(itemstack, player.worldObj, x, y, z,
                                EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
                        for (ItemStack stack : drops) {
                            float f = 0.7F;
                            double d = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                            double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                            double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                            EntityItem entityitem = new EntityItem(player.worldObj, (double) x + d, (double) y + d1, (double) z + d2, stack);
                            entityitem.delayBeforeCanPickup = 10;
                            player.worldObj.spawnEntityInWorld(entityitem);
                        }

                        itemstack.damageItem(1, player);
                        player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        return item;
    }

    @Override
    public boolean canRetrieveDrops(ItemStack item) {
        return true;
    }

    @Override
    public String getToolType(ItemStack item) {
        return "knife";
    }

    @Override
    public boolean canBlock() {
        return false;
    }

    /**
     * Determines if the weapon can parry
     */
    @Override
    public boolean canWeaponParry() {
        return false;
    }

    @Override
    protected float getStaminaMod() {
        return 0.5F;
    }

    @Override
    public WeaponClass getWeaponClass() {
        return null;
    }

    public ItemKnifeMF setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemKnifeMF setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    @Override
    public float getEfficiency(ItemStack item) {
        return CustomToolHelper.getEfficiency(item, material.getEfficiencyOnProperMaterial(), efficiencyMod);
    }

    @Override
    public int getTier(ItemStack item) {
        return CustomToolHelper.getCrafterTier(item, tier);
    }

    public ItemKnifeMF setEfficiencyMod(float efficiencyMod) {
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
        return baseDamage + CustomToolHelper.getMeleeDamage(item, material.getDamageVsEntity());
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
                : CustomToolHelper.getEfficiency(stack, material.getEfficiencyOnProperMaterial(), efficiencyMod);
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
}
