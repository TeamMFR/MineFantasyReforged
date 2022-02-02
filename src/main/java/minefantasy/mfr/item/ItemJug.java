package minefantasy.mfr.item;

import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.StaminaBar;
import minefantasy.mfr.tile.TileEntityComponent;
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
			return super.onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
		} else {
			if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

				if (!world.canMineBlockBody(player, pos)) {
					return super.onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
				}

				if (!player.canPlayerEdit(pos, rayTraceResult.sideHit, stack)) {
					return super.onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
				}

				if (isWaterSource(world, pos.up())) {
					gather(stack, world, player, hand);
					return EnumActionResult.SUCCESS;
				}
			}
			return super.onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
		}
	}

	private void gather(ItemStack item, World world, EntityPlayer player, EnumHand hand) {
		player.swingArm(hand);
		if (!world.isRemote) {
			world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
			item.shrink(1);
			EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(MineFantasyItems.JUG_WATER));
			world.spawnEntity(resultItem);
		}
	}

	private boolean isWaterSource(World world, BlockPos pos) {
		return TongsHelper.getWaterSource(world, pos) >= 0;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (storageType == null) {
			return EnumActionResult.FAIL;
		}
		if (world.getTileEntity(pos) != null && !(world.getTileEntity(pos) instanceof TileEntityComponent)){
			return EnumActionResult.FAIL;
		}

		if (type.equalsIgnoreCase("uncooked")) {
			return super.onItemUseFirst(player, world, pos, facing, hitX, hitY, hitZ, hand);
		}
		if (type.equalsIgnoreCase("empty")) {
			return rightClickEmpty(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}

		EnumFacing facingForPlacement = EnumFacing.getDirectionFromEntityLiving(pos, player);

		if (facingForPlacement != EnumFacing.UP && world.getBlockState(pos).getBlock() instanceof BlockComponent){
			return EnumActionResult.FAIL;
		}

		ItemStack stack = player.getHeldItem(hand);

		if (player.canPlayerEdit(pos.offset(facingForPlacement), facing, stack)) {
			int size = BlockComponent.placeComponent(player, stack, world, pos.offset(facingForPlacement), facing, hitX, hitY, hitZ, player, hand, storageType, blockTexture);
			if (!player.capabilities.isCreativeMode){
				stack.shrink(size);
				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.SUCCESS;
	}
}
