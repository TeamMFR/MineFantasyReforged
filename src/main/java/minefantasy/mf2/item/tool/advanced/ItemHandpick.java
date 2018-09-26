package minefantasy.mf2.item.tool.advanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.mining.RandomOre;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.config.ConfigTools;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemHandpick extends ItemPickaxe implements IToolMaterial {
    protected int itemRarity;
    private String name;
    private float baseDamage = 1F;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    public ItemHandpick(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabOldTools);
        this.name = name;
        setTextureName("minefantasy2:Tool/Advanced/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        setMaxDamage(material.getMaxUses());
    }

    @Override
    public boolean onBlockDestroyed(ItemStack item, World world, Block block, int x, int y, int z,
                                    EntityLivingBase user) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            int harvestlvl = this.getMaterial().getHarvestLevel();
            int fortune = EnchantmentHelper.getFortuneModifier(user);
            boolean silk = EnchantmentHelper.getSilkTouchModifier(user);

            //double drop logic
            ArrayList<ItemStack> drops = block.getDrops(world, x, y, z, meta, ConfigTools.handpickFortune ? fortune : 0);

            if (!silk && drops != null && !drops.isEmpty()) {
                Iterator<ItemStack> list = drops.iterator();
                while (list.hasNext()) {
                    ItemStack drop = list.next();
                    if (isOre(block, meta) && !drop.isItemEqual(new ItemStack(block, 1, meta))
                            && !(drop.getItem() instanceof ItemBlock) && world.rand.nextFloat() < getDoubleDropChance()) {
                        dropItem(world, x, y, z, drop.copy());
                    }
                }
            }

            //special drop logic
            ArrayList<ItemStack> specialdrops = RandomOre.getDroppedItems(user, block, meta, harvestlvl, fortune, silk,
                    y);

            if (specialdrops != null && !specialdrops.isEmpty()) {
                Iterator list = specialdrops.iterator();

                while (list.hasNext()) {
                    ItemStack newdrop = (ItemStack) list.next();

                    if (newdrop != null) {
                        if (newdrop.stackSize < 1)
                            newdrop.stackSize = 1;

                        dropItem(world, x, y, z, newdrop);
                    }
                }
            }
        }
        return super.onBlockDestroyed(item, world, block, x, y, z, user);
    }

    private boolean isOre(Block block, int meta) {
        if (!block.isToolEffective("pickaxe", meta)) {
            return false;
        }
        for (int i : OreDictionary.getOreIDs(new ItemStack(block, 1, meta))) {
            String orename = OreDictionary.getOreName(i);
            if (orename != null && orename.startsWith("ore")) {
                return true;
            }
        }
        return false;
    }

    private void dropItem(World world, int x, int y, int z, ItemStack drop) {
        if (world.isRemote)
            return;

        EntityItem dropItem = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, drop);
        dropItem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(dropItem);
    }

    private float getDoubleDropChance() {
        return ConfigTools.handpickBonus / 100F;
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    public ItemHandpick setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemHandpick setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemHandpick setEfficiencyMod(float efficiencyMod) {
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
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack)) / 2;
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
        return CustomToolHelper.getEfficiency(stack, super.getDigSpeed(stack, block, meta), efficiencyMod / 2);
    }

    public float func_150893_a(ItemStack stack, Block block) {
        return block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil
                && block.getMaterial() != Material.rock ? super.func_150893_a(stack, block)
                : CustomToolHelper.getEfficiency(stack, this.efficiencyOnProperMaterial, efficiencyMod / 2);
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
