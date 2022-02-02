package minefantasy.mfr.item;

import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.recipe.refine.PaintOilRecipe;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Anonymous Productions
 */
public class ItemPaintBrush extends ItemBasicCraftTool implements IRackItem {
	public ItemPaintBrush(String name, int uses) {
		super(name, Tool.BRUSH, 0, uses);
		setCreativeTab(MineFantasyTabs.tabCraftTool);
		this.setFull3D();
	}

	@Override
	public ItemStack getContainerItem(ItemStack item) {
		item.setItemDamage(item.getItemDamage() + 1);
		return item.getItemDamage() >= item.getMaxDamage() ? ItemStack.EMPTY : item;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer user, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack item = user.getHeldItem(hand);
		if (user.canPlayerEdit(pos, facing, item) && ResearchLogic.hasInfoUnlocked(user, "paint_brush")) {
			if (!user.isSwingInProgress && user.inventory.hasItemStack(new ItemStack(MineFantasyItems.JUG_PLANT_OIL))) {
				Block block = world.getBlockState(pos).getBlock();
				if (onUsedWithBlock(world, pos, block, item, user)) {
					return EnumActionResult.PASS;
				}
			}
		}
		return EnumActionResult.FAIL;
	}

	private boolean onUsedWithBlock(World world, BlockPos pos, Block block, ItemStack item, EntityPlayer user) {
		Block newBlock = null;
		ItemStack output = PaintOilRecipe.getPaintResult(new ItemStack(block, 1));
		if (!output.isEmpty() && output.getItem() instanceof ItemBlock) {
			newBlock = Block.getBlockFromItem(output.getItem());
		}
		if (newBlock != null) {
			if (!user.capabilities.isCreativeMode) {
				user.inventory.decrStackSize(user.inventory.getSlotFor(new ItemStack(MineFantasyItems.JUG_PLANT_OIL)), 1);
				ItemStack jug = new ItemStack(MineFantasyItems.JUG_EMPTY);

				if (!user.inventory.addItemStackToInventory(jug) && !world.isRemote) {
					user.entityDropItem(jug, 0F);
				}

				Skill.CONSTRUCTION.addXP(user, 1);
				item.damageItem(1, user);
			}
			world.destroyBlock(pos, false);
			world.setBlockState(pos, newBlock.getDefaultState());
			return true;
		}
		return false;
	}

	@Override
	public float getScale(ItemStack itemstack) {
		return 1.0F;
	}

	@Override
	public float getOffsetX(ItemStack itemstack) {return 1.18F;}

	@Override
	public float getOffsetY(ItemStack itemstack) {
		return -0.65F;
	}

	@Override
	public float getOffsetZ(ItemStack itemstack) {
		return 1.10F;
	}

	@Override
	public float getRotationOffset(ItemStack itemstack) {
		return -90F;
	}

	@Override
	public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
		return true;
	}

	@Override
	public boolean flip(ItemStack itemStack) {
		return false;
	}

}
