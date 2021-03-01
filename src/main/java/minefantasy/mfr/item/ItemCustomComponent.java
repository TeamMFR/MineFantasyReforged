package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemCustomComponent extends ItemBaseMFR implements ITieredComponent {
    private final String materialType;
    private String name;
    private float mass;
    private boolean canDamage = false;
    private String blocktex;
    private String storageType;

    public ItemCustomComponent(String name, String type) {
        this(name, -1F, type);
    }

    public ItemCustomComponent(String name, float mass, String type) {
        super(name);
        this.name = name;

        this.setCreativeTab(CreativeTabs.MATERIALS);
        this.mass = mass;
        this.materialType = type;
    }

    public ItemCustomComponent setCanDamage() {
        this.canDamage = true;
        this.setHasSubtypes(false);
        return this;
    }

    @Override
    public boolean isDamageable() {
        return canDamage;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        if (tab != MineFantasyTabs.tabMaterials) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("metal");
            for (CustomMaterial customMat : wood) {
                items.add(this.createComm(customMat.name));
            }
        }
    }

    public float getWeightInKg(ItemStack tool) {
        CustomMaterial base = getBase(tool);
        if (base != null) {
            return base.density * mass;
        }
        return mass;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack tool, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(tool, world, list, flag);
        if (!canDamage) {
            CustomToolHelper.addComponentString(tool, list, getBase(tool), mass);
        }
        if (this == ComponentListMFR.COGWORK_ARMOUR) {
            int AR = EntityCogwork.getArmourRating(getBase(tool));
            list.add(I18n.format("attribute.armour.protection") + " " + AR);
            if (mass > 0)
                list.add(CustomMaterial.getWeightString(getWeightInKg(tool)));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack tool) {
        return CustomToolHelper.getLocalisedName(tool, "item.commodity_" + name + ".name");
    }

    public CustomMaterial getBase(ItemStack component) {
        return CustomToolHelper.getCustomPrimaryMaterial(component);
    }

    public ItemStack createComm(String base) {
        return createComm(base, 1);
    }

    public ItemStack createComm(String base, int stack) {
        return createComm(base, stack, 0);
    }

    public ItemStack createComm(String base, int stack, float damage) {
        ItemStack item = new ItemStack(this, stack);
        CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, base);
        int maxdam = this.getMaxDamage(item);

        item.setItemDamage((int) (maxdam * damage));
        return item;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (canDamage) {
            return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
        }
        return super.getMaxDamage(stack);
    }

    public ItemStack createComm(String base, int stack, int damage) {
        ItemStack item = new ItemStack(this, stack, damage);
        CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, base);
        return item;
    }

    @Override
    public String getMaterialType(ItemStack item) {
        return materialType;
    }

    public ItemCustomComponent setStoragePlacement(String type, String tex) {
        this.blocktex = tex;
        this.storageType = type;
        return this;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (storageType == null) {
            return EnumActionResult.FAIL;
        }

        ItemStack stack = player.getHeldItem(hand);
        Block componentBlock = MineFantasyBlocks.COMPONENTS;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!block.isReplaceable(world, pos)) {
            pos = pos.offset(facing);
        }

        if (!world.mayPlace(componentBlock, pos, false, facing, player)) {
            return EnumActionResult.FAIL;
        }

        world.setBlockState(pos, MineFantasyBlocks.COMPONENTS.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, 0, player, hand), 2);

        int size = BlockComponent.placeComponent(player, stack, world, pos, storageType, blocktex);

        stack.shrink(size);

        return EnumActionResult.SUCCESS;
    }
}