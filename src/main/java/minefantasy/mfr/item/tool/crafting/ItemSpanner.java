package minefantasy.mfr.item.tool.crafting;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.api.tool.IToolMFR;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Anonymous Productions
 */

@InterfaceList({
        @Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "BuildCraft|Core"),
        @Interface(iface = "cofh.api.item.IToolHammer", modid = "CoFHAPI|item")
})

public class ItemSpanner extends ItemTool implements IToolMaterial, IToolMFR, IDamageType {
    protected int itemRarity;
    private ToolMaterial material;
    private int tier;
    private float baseDamage;
    private String name;
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;


    private final Set<Class<? extends Block>> shiftRotations = new HashSet<Class<? extends Block>>();
    private final Set<Class<? extends Block>> blacklistedRotations = new HashSet<Class<? extends Block>>();

    public ItemSpanner(String name, int rarity, int tier) {
        super(2.0F, 1.0F, ToolMaterial.IRON, Sets.newHashSet(new Block[]{}));
        this.material = ToolMaterial.IRON;
        this.name = name;
        itemRarity = rarity;
        setCreativeTab(CreativeTabMFR.tabOldTools);
        this.setMaxDamage(material.getMaxUses() * 2);
        this.tier = tier;
        setRegistryName(name);
        this.setUnlocalizedName(name);

        shiftRotations.add(BlockLever.class);
        shiftRotations.add(BlockButton.class);
        shiftRotations.add(BlockChest.class);
        blacklistedRotations.add(BlockBed.class);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Block block = world.getBlockState(pos).getBlock();

        if (block == null || isClass(blacklistedRotations, block.getClass())) {
            return EnumActionResult.FAIL;
        }

        if (player.isSneaking() != isClass(shiftRotations, block.getClass())) {
            return EnumActionResult.FAIL;
        }

        if (block instanceof BlockChest && Utils.getOtherDoubleChest(world.getTileEntity(pos)) != null) {
            return EnumActionResult.FAIL;
        }

        if (block.rotateBlock(world, pos, side)) {
            player.swingArm(hand);
            if (!world.isRemote){
                return EnumActionResult.PASS;
            }
        }
        return EnumActionResult.FAIL;
    }

    private boolean isClass(Set<Class<? extends Block>> set, Class<? extends Block> cls) {
        for (Class<? extends Block> shift : set) {
            if (shift.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, net.minecraft.world.IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public String getToolType(ItemStack item) {
        return "spanner";
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        return new float[]{0, 1, 0};
    }

    private void addSet(List list, Item[] items) {
        for (Item item : items) {
            list.add(new ItemStack(item));
        }
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0F;
    }

    public ItemSpanner setCustom(String s) {
        canRepair = false;
        isCustom = true;
        return this;
    }

    public ItemSpanner setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    public ItemSpanner setEfficiencyMod(float efficiencyMod) {
        this.efficiencyMod = efficiencyMod;
        return this;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack item) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getMeleeDamage(item), 0));

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
    public float getEfficiency(ItemStack item) {
        return CustomToolHelper.getEfficiency(item, material.getEfficiency(), efficiencyMod);
    }

    @Override
    public int getTier(ItemStack item) {
        return CustomToolHelper.getCrafterTier(item, tier);
    }


    public float getDigSpeed(ItemStack stack, Block block, World world, BlockPos pos, EntityPlayer player) {
        if (!ForgeHooks.isToolEffective(world, pos, stack)) {
            return this.getDestroySpeed(stack, block);
        }
        float digSpeed = player.getDigSpeed(block.getDefaultState(), pos);
        return CustomToolHelper.getEfficiency(stack, digSpeed, efficiencyMod);
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
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                if (MineFantasyReborn.isDebug() || customMat.getItem() != null) {
                    items.add(this.construct(customMat.name, "OakWood"));
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
}