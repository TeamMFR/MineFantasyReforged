package minefantasy.mfr.item;

import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.StaminaBar;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class ItemJug extends ItemComponentMFR {
	private String type;
	private Random rand = new Random();

	public ItemJug(String type) {
		super("jug_" + type, 0);
		setCreativeTab(MineFantasyTabs.tabFood);
		this.type = type;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack item, World world, EntityLivingBase entityLiving) {
		EntityPlayer user = (EntityPlayer) entityLiving;
		if (!world.isRemote) {
			if (type.equalsIgnoreCase("milk")) {
				curePotionEffects(user);
			}
			if (StaminaBar.isSystemActive) {
				StaminaBar.modifyStaminaValue(user, 10);
			}
		}
		ItemStack container = getContainerItem(item);
		if (item.getCount() > 1) {
			if (!user.capabilities.isCreativeMode) {
				item.shrink(1);
			}
			if (!user.inventory.addItemStackToInventory(container)) {
				user.entityDropItem(container, 0F);
			}
			return item;
		}

		return container;
	}

	private void curePotionEffects(EntityPlayer user) {
		user.clearActivePotions();
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return 24;
	}

	/**
	 * returns the action that specifies what animation to play when the items is
	 * being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack item) {
		return EnumAction.DRINK;
	}

	public EnumActionResult rightClickEmpty(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		RayTraceResult rayTraceResult = this.rayTrace(world, player, true);
		ItemStack stack = player.getHeldItem(hand);

		if (rayTraceResult == null) {
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		} else {
			if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

				if (!world.canMineBlockBody(player, pos)) {
					return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
				}

				if (!player.canPlayerEdit(pos, rayTraceResult.sideHit, stack)) {
					return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
				}

				if (isWaterSource(world, pos)) {
					gather(stack, world, player);
					return EnumActionResult.SUCCESS;
				}
			}
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
	}

	private void gather(ItemStack item, World world, EntityPlayer player) {
		player.swingArm(player.swingingHand);
		if (!world.isRemote) {
			world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
			item.shrink(1);
			EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ,
					new ItemStack(FoodListMFR.JUG_WATER));
			world.spawnEntity(resultItem);
		}
	}

	private boolean isWaterSource(World world, BlockPos pos) {
		return TongsHelper.getWaterSource(world, pos) >= 0;
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

		if (type.equalsIgnoreCase("uncooked")) {
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
		if (type.equalsIgnoreCase("empty")) {
			rightClickEmpty(player, world, pos, hand, facing, hitX, hitY, hitZ);
			return rightClickEmpty(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}

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
