package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.archery.IAmmo;
import minefantasy.mfr.api.archery.IArrowMFR;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.material.BaseMaterial;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.mechanics.MFArrowDispenser;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemArrowMFR extends Item implements IArrowMFR, IAmmo, IClientRegister {
	public static final DecimalFormat decimal_format = new DecimalFormat("#.##");
	public static final MFArrowDispenser dispenser = new MFArrowDispenser();
	protected float damage;
	protected String arrowName;
	protected ArrowType design;
	protected int itemRarity;
	private ToolMaterial arrowMat;
	private String ammoType = "arrow";
	// ===================================================== CUSTOM START
	// =============================================================\\
	private boolean isCustom = false;

	public ItemArrowMFR(String name, ArrowType type, int stackSize) {
		this(name, 0, ToolMaterial.WOOD, type);
		setMaxStackSize(stackSize);
	}

	public ItemArrowMFR(String name, ArrowType type) {
		this(name, 0, ToolMaterial.WOOD, type);
	}

	public ItemArrowMFR(String name) {
		this(name, 0, ToolMaterial.WOOD);
	}

	public ItemArrowMFR(String name, int rarity, ToolMaterial material) {
		this(name, rarity, material, ArrowType.NORMAL);
	}

	public ItemArrowMFR(String name, int rarity, ToolMaterial material, ArrowType type) {
		name = convertName(name);
		material = convertMaterial(material);

		super.setUnlocalizedName((type == ArrowType.EXPLOSIVE || type == ArrowType.EXPLOSIVEBOLT) ? name : type == ArrowType.BOLT ? (name + "_bolt") : (name + "_arrow"));
		name = getName(name, type);
		design = type;
		arrowName = name;
		arrowMat = material;
		damage = (3 + material.getAttackDamage()) * type.damageModifier;
		if (type == ArrowType.EXPLOSIVE || type == ArrowType.EXPLOSIVEBOLT) {
			damage = 1;
		}
		itemRarity = rarity;
		setRegistryName(name);
		setUnlocalizedName(name);

		setCreativeTab(MineFantasyTabs.tabOldTools);
		AmmoMechanics.addArrow(new ItemStack(this));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, dispenser);

		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	private ToolMaterial convertMaterial(ToolMaterial material) {
		if (material == BaseMaterial.getMaterial("ornate").getToolMaterial()) {
			return BaseMaterial.getMaterial("silver").getToolMaterial();
		}
		return material;
	}

	// TODO: remove/fix this hack
	private String convertName(String name) {
		if (name.equalsIgnoreCase("ornate")) {
			return "silver";
		}
		return name;
	}

	private String getName(String mat, ArrowType type) {
		if (type == ArrowType.EXPLOSIVE) {
			return "exploding_arrow";
		}
		if (type == ArrowType.EXPLOSIVEBOLT) {
			return "exploding_bolt";
		}
		if (type.name.equalsIgnoreCase("normal")) {
			return mat + "_arrow";
		}
		if (type.name.equalsIgnoreCase("bolt")) {
			return mat + "_bolt";
		}
		return mat + "_arrow_" + type.name.toLowerCase();
	}

	public EntityArrowMFR getFiredArrow(EntityArrowMFR instance, ItemStack arrow) {
		instance.modifyVelocity(design.velocity);
		return instance.setArrow(arrow).setArrowTex(arrowName);
	}

	@Override
	public void onHitEntity(Entity arrowInstance, Entity shooter, Entity hit, float damage) {
		if (arrowMat == BaseMaterial.getMaterial("dragonforge").getToolMaterial()) {
			hit.setFire((int) (damage * (arrowInstance.isBurning() ? 2.0F : 1.0F)));
		}
	}

	public ItemArrowMFR setAmmoType(String type) {
		ammoType = type;
		return this;
	}

	@Override
	public String getAmmoType(ItemStack ammo) {
		return ammoType;
	}

	public ItemArrowMFR setCustom() {
		canRepair = false;
		isCustom = true;
		return this;
	}

	@Override
	public float getDamageModifier(ItemStack arrow) {
		return (4F + CustomToolHelper.getMeleeDamage(arrow, damage)) * design.damageModifier;
	}

	@Override
	public float getGravityModifier(ItemStack arrow) {
		float weight = 1.0F * design.weightModifier;
		return CustomToolHelper.getWeightModifier(arrow, weight);
	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		return CustomToolHelper.getRarity(item, itemRarity);
	}

	public ItemStack construct(String main) {
		return CustomToolHelper.construct(this, main, null);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		if (isCustom) {
			ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
			for (CustomMaterial customMat : metal) {
				if (MineFantasyReforged.isDebug() || !customMat.getItemStack().isEmpty()) {
					items.add(this.construct(customMat.name));
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flag) {
		if (isCustom) {
			CustomToolHelper.addInformation(item, list);
		}
		super.addInformation(item, world, list, flag);
		list.add(TextFormatting.BLUE + I18n.format("attribute.arrowPower.name") + ": "
				+ decimal_format.format(getDamageModifier(item)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack item) {
		String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
		return CustomToolHelper.getLocalisedName(item, unlocalName);
	}

	@Override
	public float getBreakChance(Entity entityArrow, ItemStack arrow) {
		int maxUses = CustomToolHelper.getMaxDamage(arrow, arrowMat.getMaxUses());
		return 1F / (maxUses / 150);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}

	// ====================================================== CUSTOM END
	// ==============================================================\\
}
