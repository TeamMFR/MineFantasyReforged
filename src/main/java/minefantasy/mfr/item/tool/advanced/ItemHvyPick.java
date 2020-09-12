package minefantasy.mfr.item.tool.advanced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.config.ConfigTools;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemHvyPick extends ItemPickaxe implements IToolMaterial, IClientRegister {
    protected int itemRarity;
    private float baseDamage = 2F;
    private Random rand = new Random();
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;

    public ItemHvyPick(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMFR.tabOldTools);
        setRegistryName(name);
        setUnlocalizedName(name);

        setMaxDamage(material.getMaxUses());

        MineFantasyReborn.PROXY.addClientRegister(this);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack item, World world, IBlockState state, BlockPos pos, EntityLivingBase user) {
        if (!world.isRemote && ForgeHooks.isToolEffective(world, pos, item)
                && ItemLumberAxeMFR.canAcceptCost(user)) {
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int y1 = -1; y1 <= 1; y1++) {
                    for (int z1 = -1; z1 <= 1; z1++) {
                        EnumFacing EF = getEFFor(user, pos);
                        BlockPos blockPos = new BlockPos(pos.add( EF.getFrontOffsetX()+x1, EF.getFrontOffsetY()+y1, EF.getFrontOffsetZ()+z1 ));

                        if (!(x1 + EF.getFrontOffsetX() == 0 && y1 + EF.getFrontOffsetY() == 0 && z1 + EF.getFrontOffsetZ() == 0)) {
                            IBlockState newblock = world.getBlockState(blockPos);

                            if (newblock != null && user instanceof EntityPlayer
                                    && ForgeHooks.canHarvestBlock(newblock.getBlock(), (EntityPlayer) user, world, blockPos)
                                    /*&& ForgeHooks.isToolEffective(item, newblock, m)*/) {

                                if (rand.nextFloat() * 100F < (100F - ConfigTools.hvyDropChance)) {
                                    newblock.getBlock().dropBlockAsItem(world, pos, newblock, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), item));
                                }
                                world.setBlockToAir(blockPos);
                                item.damageItem(1, user);
                                ItemLumberAxeMFR.tirePlayer(user, 1F);
                            }
                        }
                    }
                }
            }
        }
        return super.onBlockDestroyed(item, world, state, pos, user);
    }

    private EnumFacing getEFFor(EntityLivingBase user, BlockPos pos) {
        return EnumFacing.getDirectionFromEntityLiving(pos, user);// TODO: FD
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    public ItemHvyPick setCustom(String s) {
        setCreativeTab(CreativeTabMFR.tabOldTools);
        canRepair = false;
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
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        if (slot != EntityEquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot, stack);
        }

        Multimap<String, AttributeModifier> map = HashMultimap.create();
        map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getMeleeDamage(stack), 0));

        return map;
    }

    /**
     * Gets a stack-sensitive value for the melee dmg
     */
    protected float getMeleeDamage(ItemStack item) {
        return baseDamage + CustomToolHelper.getMeleeDamage(item, toolMaterial.getAttackDamage());
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, 1.0F);
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

    public float getDigSpeed(ItemStack stack, Block block, World world, BlockPos pos, EntityPlayer player) {
        if (!ForgeHooks.isToolEffective(world, pos, stack)) {
            return this.getDestroySpeed(stack, block);
        }
        float digSpeed = player.getDigSpeed(block.getDefaultState(), pos);
        return CustomToolHelper.getEfficiency(stack, digSpeed, efficiencyMod / 10);
    }

    public float getDestroySpeed(ItemStack stack, Block block) {
        return block.getMaterial(block.getDefaultState()) != Material.IRON && block.getMaterial(block.getDefaultState()) != Material.ANVIL
                && block.getMaterial(block.getDefaultState()) != Material.ROCK ? super.getDestroySpeed(stack, block.getDefaultState())
                : CustomToolHelper.getEfficiency(stack, this.efficiency, efficiencyMod / 2);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        return CustomToolHelper.getHarvestLevel(stack, super.getHarvestLevel(stack, toolClass, player, blockState));
    }
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        if (isCustom) {
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            for (CustomMaterial customMat : metal) {
                if (MineFantasyReborn.isDebug() || customMat.getItemStack().isEmpty()) {
                    items.add(this.construct(customMat.name, "oak_wood"));
                }
            }
        } else {
            super.getSubItems(tab, items);
        }
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        if (isCustom) {
            CustomToolHelper.addInformation(item, list);
        }
        super.addInformation(item, world, list, flag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }

    @Override
    public void registerClient() {
        ModelLoaderHelper.registerItem(this);
    }
    // ====================================================== CUSTOM END
    // ==============================================================\\
}
