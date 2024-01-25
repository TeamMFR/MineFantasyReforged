package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ISalvageDrop;
import minefantasy.mfr.api.crafting.engineer.IBombComponent;
import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;

public class ItemBombComponent extends ItemBaseMFR implements IBombComponent, ISalvageDrop {
	private static final HashMap<String, Item> components = new HashMap<String, Item>();

	private final byte tier;
	private final String type;
	private final String componentName;

	public ItemBombComponent(String name, IRarity rarity, String type, String componentName, int tier) {
		super(name, rarity);
		this.type = type;
		this.tier = (byte) tier;
		this.componentName = componentName;
		components.put(type + tier, this);
		setCreativeTab(MineFantasyTabs.tabMaterials);
	}

	public static ItemStack getBombComponent(String name, String tier) {
		return new ItemStack(components.get(name + tier));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List tooltip, ITooltipFlag flag) {
		tooltip.add(TextFormatting.GOLD + I18n.format("bomb.component.name"));
		tooltip.add(TextFormatting.ITALIC + I18n.format("bomb.component." + type));
	}

	@Override
	public String getComponentType() {
		return type;
	}

	public String getComponentName(){
		return componentName;
	}

	@Override
	public byte getTier() {
		return tier;
	}

	@Override
	public boolean canSalvage(EntityPlayer user, ItemStack item) {
		if (getContainerItem() != null) {
			return false;
		}
		return true;
	}
}
