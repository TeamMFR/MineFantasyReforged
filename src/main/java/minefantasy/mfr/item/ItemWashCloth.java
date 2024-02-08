package minefantasy.mfr.item;

import com.google.common.collect.Sets;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.tool.IToolMFR;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.BlockUtils;
import minefantasy.mfr.util.ModelLoaderHelper;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemWashCloth extends ItemTool implements IToolMFR, IClientRegister {
	private final int tier;
	private int maxUses;

	public ItemWashCloth(String name, int tier) {
		super(1.0F, 1.0F, ToolMaterial.WOOD, Sets.newHashSet(new Block[] {}));
		this.tier = tier;
		setCreativeTab(MineFantasyTabs.tabCraftTool);

		setRegistryName(name);
		setTranslationKey(name);

		this.setMaxStackSize(1);

		MineFantasyReforged.PROXY.addClientRegister(this);

		this.addPropertyOverride(new ResourceLocation("uses"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return getDamage(stack);
			}
		});
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		RayTraceResult rayTraceResult = this.rayTrace(world, player, true);
		if (BlockUtils.isWaterSource(world, rayTraceResult.getBlockPos())) {
			if (!world.isRemote) {
				int rand = itemRand.nextInt(10);
				if (rand > 5) {
					ItemStack stack = player.getHeldItem(hand);
					int plus = stack.getItemDamage() - (maxUses / 2);
					if (plus < 0) {
						plus = 0;
					}
					stack.setItemDamage(plus);
				}
			}
			world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + itemRand.nextFloat() / 4F, 0.5F + itemRand.nextFloat());
		}

		return ToolHelper.performBlockTransformation(player, world, pos, hand, facing);
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		if (damage <= maxUses) {
			super.setDamage(stack, damage);
		}
	}

	public int getMaxUses() {
		return maxUses;
	}

	public ItemWashCloth setMaxUses(int maxUses) {
		this.maxUses = maxUses;
		this.setMaxDamage(maxUses);
		return this;
	}

	public boolean isWet(ItemStack stack) {
		return getDamage(stack) > (maxUses / 2);
	}

	@Override
	public float getEfficiency(ItemStack item) {
		if (isWet(item)) {
			return tier * 10;
		}
		return tier * 5;
	}

	@Override
	public int getTier(ItemStack item) {
		return tier;
	}

	@Override
	public Tool getToolType(ItemStack stack) {
		return Tool.WASH;
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

		items.add(new ItemStack(this,1,0));
		items.add(new ItemStack(this,1,3));
	}

	@Override
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}
}
