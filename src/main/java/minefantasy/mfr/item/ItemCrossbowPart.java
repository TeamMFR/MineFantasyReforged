package minefantasy.mfr.item;

import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.api.crafting.engineer.ICrossbowPart;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemCrossbowPart extends ItemBaseMFR implements ICrossbowPart {
	private final int tier;
	private final String type;
	private final String partname;
	private int capacity = 0, durability = 50;
	private float power, spread, recoil, speed, bash, zoom;
	private boolean isHandle = false;

	public ItemCrossbowPart(String name, String type) {
		this(name, type, ICrossbowPart.components.size(), name);
	}

	public ItemCrossbowPart(String name, String type, int tier, String model) {
		super(name);
		this.setFull3D();
		this.type = type;
		this.tier = tier;
		this.partname = model;
		MineFantasyReforgedAPI.registerCrossbowPart(this);
		this.setCreativeTab(MineFantasyTabs.tabMaterials);
	}

	public static ICrossbowPart getPart(String type, int id) {
		return components.get(type + id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag fullInfo) {
		list.add(TextFormatting.GOLD + I18n.format("crossbow.component.name"));
		list.add(TextFormatting.ITALIC + I18n.format("crossbow.component." + type));
		list.add(TextFormatting.DARK_GRAY + I18n.format(getUnlocalizedName() + ".desc"));
	}

	@Override
	public String getComponentType() {
		return type;
	}

	@Override
	public int getID() {
		return tier;
	}

	@Override
	public String getUnlocalisedName() {
		if (this == MineFantasyItems.CROSSBOW_ARMS_BASIC) {
			return null;
		}
		return "crosspart." + type + "." + partname;
	}

	public ItemCrossbowPart addPower(float power) {
		this.power = power;
		return this;
	}

	public ItemCrossbowPart setScope(float zoom) {
		this.zoom = zoom;
		return this;
	}

	public ItemCrossbowPart addSpread(float spread) {
		this.spread = spread;
		return this;
	}

	public ItemCrossbowPart addRecoil(float recoil) {
		this.recoil = recoil;
		return this;
	}

	public ItemCrossbowPart addSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	public ItemCrossbowPart addBash(float bash) {
		this.bash = bash;
		return this;
	}

	public ItemCrossbowPart addCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public ItemCrossbowPart addDurability(int durability) {
		this.durability = durability;
		return this;
	}

	public ItemCrossbowPart setHandCrossbow(boolean flag) {
		this.isHandle = flag;
		return this;
	}

	@Override
	public float getModifier(String type) {
		if (type.equalsIgnoreCase("power"))
			return power;
		if (type.equalsIgnoreCase("spread"))
			return spread;
		if (type.equalsIgnoreCase("recoil"))
			return recoil;
		if (type.equalsIgnoreCase("speed"))
			return speed;
		if (type.equalsIgnoreCase("capacity"))
			return capacity;
		if (type.equalsIgnoreCase("bash"))
			return bash;
		if (type.equalsIgnoreCase("zoom"))
			return zoom;
		if (type.equalsIgnoreCase("durability"))
			return durability;
		return 0F;
	}

	@Override
	public boolean makesSmallWeapon() {
		return isHandle;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			if (this == MineFantasyItems.CROSSBOW_STRING_LOADED || this == MineFantasyItems.CROSSBOW_STRING_UNLOADED){
				return;
			}
			items.add(new ItemStack(this));
		}
	}
}
