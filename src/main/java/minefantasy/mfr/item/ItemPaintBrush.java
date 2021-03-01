package minefantasy.mfr.item;

import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.recipe.refine.PaintOilRecipe;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
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

		// setTextureName("minefantasy2:Tool/Crafting/"+name);
		// this.setUnlocalizedName(name);
		// this.setMaxDamage(uses);
		// setMaxStackSize(1);
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
			if (!user.isSwingInProgress && user.inventory.hasItemStack(new ItemStack(ComponentListMFR.PLANT_OIL))) {
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

			user.inventory.removeStackFromSlot(EntityEquipmentSlot.MAINHAND.getIndex());
			ItemStack jug = new ItemStack(FoodListMFR.JUG_EMPTY);

			if (!user.inventory.addItemStackToInventory(jug) && !world.isRemote) {
				user.entityDropItem(jug, 0F);
			}
			if (world.isRemote)
				return true;

			Skill.CONSTRUCTION.addXP(user, 1);
			item.damageItem(1, user);
			world.setBlockState(pos, newBlock.getDefaultState());
		}
		return false;
	}

	@Override
	public float getScale(ItemStack itemstack) {
		return 1.0F;
	}

	@Override
	public float getOffsetX(ItemStack itemstack) {
		return 0;
	}

	@Override
	public float getOffsetY(ItemStack itemstack) {
		return 9F / 16F;
	}

	@Override
	public float getOffsetZ(ItemStack itemstack) {
		return 1F / 8F;
	}

	@Override
	public float getRotationOffset(ItemStack itemstack) {
		return 90;
	}

	@Override
	public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
		return true;
	}

}
