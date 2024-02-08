package minefantasy.mfr.item;

import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.item.ItemStack;

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
