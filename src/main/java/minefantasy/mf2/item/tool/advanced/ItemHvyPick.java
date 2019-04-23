package minefantasy.mf2.item.tool.advanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.config.ConfigTools;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.util.BukkitUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemHvyPick extends ItemPickaxe implements IToolMaterial {
    protected int itemRarity;
    private String name;
    private float baseDamage = 2F;
    private Random rand = new Random();
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    public ItemHvyPick(String name, ToolMaterial material, int rarity) {
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
        if (!world.isRemote && ForgeHooks.isToolEffective(item, block, world.getBlockMetadata(x, y, z))
                && ItemLumberAxe.canAcceptCost(user)) {
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int y1 = -1; y1 <= 1; y1++) {
                    for (int z1 = -1; z1 <= 1; z1++) {
                        ForgeDirection FD = getFDFor(user);
                        int blockX = x + x1 + FD.offsetX;
                        int blockY = y + y1 + FD.offsetY;
                        int blockZ = z + z1 + FD.offsetZ;

                        if (!(x1 + FD.offsetX == 0 && y1 + FD.offsetY == 0 && z1 + FD.offsetZ == 0)) {
                            Block newblock = world.getBlock(blockX, blockY, blockZ);
                            int m = world.getBlockMetadata(blockX, blockY, blockZ);

                            if (newblock != null && user instanceof EntityPlayer
                                    && ForgeHooks.canHarvestBlock(newblock, (EntityPlayer) user, m)
                                    /*&& ForgeHooks.isToolEffective(item, newblock, m)*/) {
                                if ((MineFantasyII.isBukkitServer()
                                        && BukkitUtils.cantBreakBlock((EntityPlayer) user, blockX, blockY, blockZ))) {
                                    continue;
                                }

                                if (rand.nextFloat() * 100F < (100F - ConfigTools.hvyDropChance)) {
                                    newblock.dropBlockAsItem(world, blockX, blockY, blockZ, m,
                                            EnchantmentHelper.getFortuneModifier(user));
                                }
                                world.setBlockToAir(blockX, blockY, blockZ);
                                item.damageItem(1, user);
                                ItemLumberAxe.tirePlayer(user, 1F);
                            }
                        }
                    }
                }
            }
        }
        return super.onBlockDestroyed(item, world, block, x, y, z, user);
    }

    private ForgeDirection getFDFor(EntityLivingBase user) {
        return ForgeDirection.UNKNOWN;// TODO: FD
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    public ItemHvyPick setCustom(String s) {
        setCreativeTab(CreativeTabMF.tabOldTools);
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemHvyPick setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemHvyPick setEfficiencyMod(float efficiencyMod) {
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
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack)) * 2;
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
        return CustomToolHelper.getEfficiency(stack, super.getDigSpeed(stack, block, meta), efficiencyMod / 10);
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
