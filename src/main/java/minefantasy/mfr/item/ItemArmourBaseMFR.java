package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.IArmourMFR;
import minefantasy.mfr.api.armour.IArmourRating;
import minefantasy.mfr.api.armour.ISpecialArmourMFR;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.material.ArmorMaterialMFR;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.ModelLoaderHelper;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.List;

public class ItemArmourBaseMFR extends ItemArmor implements ISpecialArmor, IArmourMFR, IArmourRating, ISpecialArmourMFR, IClientRegister {
public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
	public static ArmorMaterial baseMaterial = EnumHelper.addArmorMaterial("MF Armour Base", "MFR_armour_base_texture", 0, new int[] {2, 6, 5, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
	public float baseArmorRating;
	public int enchantment;
	public String texture;
	public float armourWeight;
	public ArmorMaterialMFR material;
	public ArmourDesign design;
	public float damageType;
	protected float suitBulk;
	private final EntityEquipmentSlot piece;

	public ItemArmourBaseMFR(String name, ArmorMaterialMFR material, ArmourDesign armourDesign, EntityEquipmentSlot slot, String tex) {
		super(baseMaterial, 0, slot);
		this.material = material;
		baseArmorRating = material.baseArmorRating;
		armourWeight = armourDesign.getWeight() * material.armourWeight;
		enchantment = material.enchantability;
		this.piece = slot;
		design = armourDesign;
		suitBulk = design.getBulk();
		texture = tex;
		float baseDurability = material.durability * design.getDurability() / 2F;
		float durability = baseDurability / 2F + (baseDurability / 2F * ArmourCalculator.sizes[slot.getIndex()] / ArmourCalculator.sizes[1]);
		this.setMaxDamage((int) durability);
		this.setUnlocalizedName(name);
		setDamageType(getBaseArmorClass() * scalePiece());
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	public ItemArmourBaseMFR setDamageType(float damageType) {
		this.damageType = damageType;
		return this;
	}

	/*
	 * Piece | Slot 0 | 3 1 | 2 2 | 1 3 | 0
	 */
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		initArmourDamage(entity, stack, damage);
	}

	public void initArmourDamage(EntityLivingBase user, ItemStack armour, float damage) {
		armour.damageItem((int) (damage / 5F) + 1, user);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armour, int slot) {
		float max = armour.getMaxDamage();
		float dam = max - armour.getItemDamage();
		float total = 0;
		total += (ArmourCalculator.getDamageReductionForDisplayPiece(armour, 0) * 100F);
		total += (ArmourCalculator.getDamageReductionForDisplayPiece(armour, 1) * 100F);
		total += (ArmourCalculator.getDamageReductionForDisplayPiece(armour, 2) * 100F);
		return Math.round((total / 50) / max * dam);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase entity, ItemStack armour, DamageSource source, double damage, int slot) {
		entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0D);
		float armorClass = getProtectionRatio(armour);
		armorClass = ArmourCalculator.getArmourValueMod(armour, armorClass);

		if (ArmourCalculator.advancedDamageTypes && !entity.world.isRemote) {
			armorClass = ArmourCalculator.adjustArmorClassForDamage(source, armorClass, getProtectiveTrait(armour, 0), getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
		}

		armorClass *= getArmorClassModifier(entity, armour, source, damage);

		if (source == DamageSource.FALL && design == ArmourDesign.PLATE) {
			armorClass = 1F / material.armourWeight;
		} else if (source.isMagicDamage()) {
			armorClass = getMagicArmorClass(armorClass, source, damage, entity);
		} else if (source == DamageSource.ON_FIRE) {
			armorClass *= getArmorClassForBurn();
		} else if (source.isUnblockable()) {
			armorClass *= getUnblockableResistance(armour, source);
		}
		armorClass *= getSpecialModifier(armour, source);

		if (entity.getEntityData().hasKey("MF_ZombieArmour")) {
			armorClass -= 1.5F;
			if (armorClass < 0)
				armorClass = 0;
		}

		armorClass++;// because 1.0AC = no armour so it adds on top of this

		double totalPercent = ArmourCalculator.convertToPercent(armorClass);
		double maxPercent = 0.99D;// max percentage is 99% damage to avoid immunity

		if (totalPercent > maxPercent) {
			totalPercent = maxPercent;
		}
		double percent = totalPercent * scalePiece();
		if (percent < 0) {
			percent = 0;
		}

		return new ArmorProperties(0, percent, Integer.MAX_VALUE);
	}

	protected float getSpecialModifier(ItemStack armour, DamageSource source) {
		return 1.0F;
	}

	private float getArmorClassForBurn() {
		return armourWeight >= ArmourCalculator.encumberanceArray[1] ? 0.1F
				: armourWeight >= ArmourCalculator.encumberanceArray[0] ? 0.05F : 0F;
	}

	public float getUnblockableResistance(ItemStack item, DamageSource source) {
		return 0F;
	}

	public float getMagicArmorClass(float AC, DamageSource source, double damage, EntityLivingBase player) {
		return 0F;
	}

	protected float getArmorClassModifier(EntityLivingBase player, ItemStack armour, DamageSource source, double damage) {
		return 1.0F;
	}

	public float scalePiece() {
		return ArmourCalculator.sizes[piece.getIndex()];
	}

	protected float getProtectionRatio(ItemStack item) {
		return getBaseArmorClass();
	}

	protected float getBaseArmorClass() {
		return material.baseArmorRating * design.getRating();
	}

	private void addModifier(List list, ItemStack item, float ratio, String name) {
		if (getProtectionRatio(item) != ratio) {
			if (getProtectionRatio(item) > ratio) {
				float percent = (ratio / getProtectionRatio(item)) - 1F;
				list.add(TextFormatting.RED + I18n.format(
						"attribute.modifier.take." + 1, decimal_format.format(-percent * 100),
						I18n.format("attribute.armour." + name)));
			} else {
				float percent = (ratio / getProtectionRatio(item)) - 1F;
				list.add(TextFormatting.DARK_GREEN + I18n.format(
						"attribute.modifier.plus." + 1, decimal_format.format(percent * 100),
						I18n.format("attribute.armour." + name)));
			}
		}
	}

	public ArmorMaterialMFR getMaterial() {
		return this.material;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		if (isUnbreakable()) {
			setUnbreakable(stack);
		}
		return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
	}

	protected boolean isUnbreakable() {
		return false;
	}

	public static void setUnbreakable(ItemStack tool) {
		if (!tool.hasTagCompound()) {
			tool.setTagCompound(new NBTTagCompound());
		}
		tool.getTagCompound().setBoolean(Constants.UNBREAKABLE_TAG, true);
	}

	@Override
	public float scalePiece(ItemStack item) {
		return scalePiece();
	}

	@Override
	public int getItemEnchantability() {
		return material.enchantability;
	}

	@Override
	public String getSuitWeightType(ItemStack item) {
		return design.getGroup();
	}

	@Override
	public float getDamageTypeValue(EntityLivingBase user, ItemStack armour, DamageSource src) {
		float newDamageType = damageType;

		if (ArmourCalculator.advancedDamageTypes && !user.world.isRemote) {
			newDamageType = ArmourCalculator.adjustArmorClassForDamage(src, newDamageType, getProtectiveTrait(armour, 0),
					getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
		}
		return newDamageType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getDamageTypeDisplay(ItemStack armour, int damageType) {
		return design.getProtectiveTraits()[damageType] * this.damageType;
	}

	@Override
	public float getDamageRatingValue(EntityLivingBase user, ItemStack armour, DamageSource src) {
		float damageRating = getProtectionRatio(armour) * scalePiece();

		if (ArmourCalculator.advancedDamageTypes && !user.world.isRemote) {
			damageRating = ArmourCalculator.adjustArmorClassForDamage(src, damageRating, getProtectiveTrait(armour, 0),
					getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
		}
		return damageRating;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getDamageRatingDisplay(ItemStack armour, int damageType) {
		float damageRating = getProtectionRatio(armour) * scalePiece();

		if (ArmourCalculator.advancedDamageTypes) {
			damageRating = ArmourCalculator.modifyArmorClassForType(damageType, damageRating, getProtectiveTrait(armour, 0),
					getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
		}
		return damageRating;
	}

	public float getProtectiveTrait(ItemStack item, int dtype) {
		return design.getProtectiveTraits()[dtype];
	}

	/**
	 * Gets a modifier, improving resistances against the elements
	 *
	 * @param hazard is the type of damage, like "fire"
	 */
	public float getResistanceModifier(ItemStack item, String hazard) {
		return 1.0F;
	}

	@Override
	public float getPieceWeight(ItemStack item, EntityEquipmentSlot slot) {
		return armourWeight * ArmourCalculator.sizes[slot.getIndex()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}
}
