package minefantasy.mfr.item;

import minefantasy.mfr.api.tool.ILighter;
import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemLighter extends ItemBaseMFR implements ILighter {
	private float chance;

	public ItemLighter(String name, float chance, int uses) {
		super(name);
		setMaxDamage(uses);
		this.chance = chance;
		this.setMaxStackSize(1);
		this.setCreativeTab(MineFantasyTabs.tabGadget);
	}

	// 0 for N/A -1 for fail, 1 for succeed
	public static int tryUse(ItemStack held, EntityPlayer user) {
		if (held.isEmpty()) {
			return 0;
		}
		if (held.getItem() instanceof ILighter) {
			ILighter lighter = (ILighter) held.getItem();
			if (lighter.canLight()) {
				return (user.getRNG().nextFloat() < lighter.getChance()) ? 1 : -1;
			}
		}
		if (held.getItem() instanceof ItemFlintAndSteel) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canLight() {
		return true;
	}

	@Override
	public double getChance() {
		return chance;
	}

	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);

		pos = pos.offset(facing);

		if (!player.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		} else {
			if (!world.isRemote) {
				boolean success = player.getRNG().nextFloat() < chance;
				if (world.isAirBlock(pos)) {
					world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.AMBIENT, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
					if (success) {
						world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
					}
				}
				if (success) {
					stack.damageItem(1, player);
				}
			}
			return EnumActionResult.SUCCESS;
		}
	}
}
