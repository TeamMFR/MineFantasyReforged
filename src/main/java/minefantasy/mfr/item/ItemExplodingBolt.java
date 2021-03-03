package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.List;

public class ItemExplodingBolt extends ItemArrowMFR implements ISpecialSalvage {

	public ItemExplodingBolt() {
		super("exploding_bolt", 1, MineFantasyMaterials.IRON.getToolMaterial(), ArrowType.EXPLOSIVEBOLT);
		setCreativeTab(MineFantasyTabs.tabGadget);
		setAmmoType("bolt");
		setMaxStackSize(20);
	}

	public static ItemStack createBombArrow(String powder, String filling) {
		ItemStack arrow = new ItemStack(MineFantasyItems.EXPLODING_BOLT);

		ItemBomb.setFilling(arrow, filling);
		ItemBomb.setPowder(arrow, powder);

		return arrow;
	}

	@Override
	public EntityArrowMFR getFiredArrow(EntityArrowMFR instance, ItemStack arrow) {
		instance = super.getFiredArrow(instance, arrow);
		instance.canBePickedUp = 0;
		return instance.setBombStats(ItemBomb.getPowder(arrow), ItemBomb.getFilling(arrow));
	}

	@Override
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flag) {
		super.addInformation(item, world, list, flag);

		EnumFillingType fill = EnumFillingType.getType(ItemBomb.getFilling(item));
		EnumPowderType powder = EnumPowderType.getType(ItemBomb.getPowder(item));

		int damage = (int) (fill.damage * powder.damageModifier * 0.5F);
		float range = fill.range * powder.rangeModifier * 0.5F;

		list.add(I18n.format(MineFantasyItems.BOMB_CUSTOM.getUnlocalizedName(item) + ".name"));
		list.add(I18n.format("bomb.powder." + powder.name + ".name"));
		list.add("");
		list.add(I18n.format("bomb.damage.name") + ": " + damage);
		list.add(I18n.format("bomb.range.metric.name", range));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		items.add(createBombArrow("black_powder", "basic"));
		items.add(createBombArrow("black_powder", "shrapnel"));
		items.add(createBombArrow("black_powder", "fire"));
		items.add(createBombArrow("advanced_black_powder", "basic"));
		items.add(createBombArrow("advanced_black_powder", "shrapnel"));
		items.add(createBombArrow("advanced_black_powder", "fire"));
	}

	@Override
	public Object[] getSalvage(ItemStack item) {
		return new Object[] {MineFantasyItems.BOMB_CASING_BOLT,
				ItemBombComponent.getBombComponent("powder", ItemBomb.getPowder(item)),
				ItemBombComponent.getBombComponent("filling", ItemBomb.getFilling(item)),};
	}
}
