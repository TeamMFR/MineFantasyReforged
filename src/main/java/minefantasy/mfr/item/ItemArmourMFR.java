package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.IElementalResistance;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.material.BaseMaterial;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemArmourMFR extends ItemArmourBaseMFR implements IElementalResistance {
	@SideOnly(Side.CLIENT)
	private static Object fullplate;
	protected BaseMaterial baseMaterial;
	private final int itemRarity;

	public ItemArmourMFR(String name, BaseMaterial material, ArmourDesign armourDesign, EntityEquipmentSlot slot, String tex, int rarity) {
		super(name, material.getArmourConversion(), armourDesign, slot, tex);
		baseMaterial = material;
		setRegistryName(name);
		setUnlocalizedName(name);

		setCreativeTab(MineFantasyTabs.tabArmour);

		itemRarity = rarity;
	}

	public ItemArmourMFR(String name, BaseMaterial material, ArmourDesign armourDesign, EntityEquipmentSlot slot, String tex, int rarity, float customBulk) {
		this(name, material, armourDesign, slot, tex, rarity);
		this.suitBulk = customBulk;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		if (source.isMagicDamage() && this.getMagicResistance(stack, source) > 100F) {
			return;
		}
		if ((source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE || source == DamageSource.HOT_FLOOR) && this.getFireResistance(stack, source) > 100F) {
			return;
		}
		if (LeatherArmourListMFR.isUnbreakable(baseMaterial)) {
			return;
		}
		initArmourDamage(entity, stack, damage);
	}

	@Override
	public float getMagicResistance(ItemStack item, DamageSource source) {
		CustomMaterial custom = getCustomMaterial(item);
		if (custom != CustomMaterial.NONE) {
			return custom.resistance;
		}
		return material.magicResistanceModifier;
	}

	@Override
	public float getFireResistance(ItemStack item, DamageSource source) {
		CustomMaterial custom = getCustomMaterial(item);
		if (custom != CustomMaterial.NONE) {
			MFRLogUtil.logDebug("Fire Resist: " + custom.getFireResistance());
			return custom.getFireResistance() * design.getRating();
		}
		return material.fireResistanceModifier;
	}

	@Override
	public float getArrowDeflection(ItemStack item, DamageSource source) {
		return (design == ArmourDesign.MAIL || design == ArmourDesign.PLATE) ? 0.5F : 0.0F;
	}

	@Override
	public float getBaseResistance(ItemStack item, DamageSource source) {
		if (baseMaterial == BaseMaterial.getMaterial("ender") && source.getImmediateSource() != null
				&& source.getImmediateSource() instanceof EntityEnderPearl) {
			return 100F;
		}
		return 0;
	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		int lvl = itemRarity + 1;

		if (item.isItemEnchanted()) {
			if (lvl == 0) {
				lvl++;
			}
			lvl++;
		}
		if (design == ArmourDesign.PLATE) {
			lvl++;
		}
		if (lvl >= MineFantasyItems.RARITY.length) {
			lvl = MineFantasyItems.RARITY.length - 1;
		}
		return MineFantasyItems.RARITY[lvl];
	}

	@Override
	protected boolean isUnbreakable() {
		return baseMaterial == BaseMaterial.getMaterial("ender");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		if (this != LeatherArmourListMFR.LEATHER[0]) {
			return;
		}
		items.add(new ItemStack(LeatherArmourListMFR.LEATHER_APRON));
		addSet(items, LeatherArmourListMFR.LEATHER);
	}

	private void addSet(List list, Item[] items) {
		for (Item item : items) {
			list.add(new ItemStack(item));
		}
	}

	public boolean canColour() {
		return design == ArmourDesign.PADDING || design == ArmourDesign.LEATHER || design == ArmourDesign.CLOTH
				|| isMetal();
	}

	public boolean isMetal() {
		return design == ArmourDesign.MAIL || design == ArmourDesign.PLATE;
	}

	/**
	 * Return whether the specified armor ItemStack has a color.
	 */
	@Override
	public boolean hasColor(ItemStack item) {
		return canColour() && (item.hasTagCompound() && (item.getTagCompound().hasKey("display", 10) && item.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
	}

	/**
	 * The Colour of the armour when not dyed
	 */
	public int getBaseColour(ItemStack item) {
		if (!this.isCustom()) {
			return 10511680;
		}
		return -1;
	}

	/**
	 * Return the color for the specified armor ItemStack.
	 */
	@Override
	public int getColor(ItemStack item) {
		int metal = getBaseColour(item);
		if (!canColour()) {
			return metal;
		} else {
			NBTTagCompound nbttagcompound = item.getTagCompound();

			if (nbttagcompound == null) {
				return metal;
			} else {
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
				return nbttagcompound1 == null ? metal : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : metal);
			}
		}
	}

	/**
	 * Remove the color from the specified armor ItemStack.
	 */
	@Override
	public void removeColor(ItemStack item) {
		if (canColour()) {
			NBTTagCompound nbttagcompound = item.getTagCompound();

			if (nbttagcompound != null) {
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

				if (nbttagcompound1.hasKey("color")) {
					nbttagcompound1.removeTag("color");
				}
			}
		}
	}

	@Override
	public void setColor(ItemStack item, int color) {
		if (!canColour()) {
			return;
		} else {
			NBTTagCompound nbttagcompound = item.getTagCompound();

			if (nbttagcompound == null) {
				nbttagcompound = new NBTTagCompound();
				item.setTagCompound(nbttagcompound);
			}

			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (!nbttagcompound.hasKey("display", 10)) {
				nbttagcompound.setTag("display", nbttagcompound1);
			}

			nbttagcompound1.setInteger("color", color);
		}
	}

	@Override
	public float getMagicArmorClass(float armorClass, DamageSource source, double damage, EntityLivingBase player) {
		if (damage > 1 && material.isMythic) {
			return armorClass;
		}
		return 0F;
	}

	@Override
	public String getSuitWeightType(ItemStack item) {
		if (design == ArmourDesign.MAIL) {
			return "medium";
		}
		if (design == ArmourDesign.PLATE) {
			return "heavy";
		}
		return super.getSuitWeightType(item);
	}

	public ItemStack construct(String plate) {
		ItemStack item = new ItemStack(this);
		CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, plate.toLowerCase());
		return item;
	}

	/**
	 * A bit of the new system, gets custom materials for armour Only used on
	 * cogwork armour though
	 */
	public CustomMaterial getCustomMaterial(ItemStack item) {
		return CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
	}

	@Override
	public float getDamageRatingValue(EntityLivingBase user, ItemStack armour, DamageSource src) {
		float damageRating = getProtectionRatio(armour) * scalePiece();

		if (ArmourCalculator.advancedDamageTypes && !user.world.isRemote) {
			damageRating = ArmourCalculator.adjustArmorClassForDamage(src, damageRating, getProtectiveTrait(armour, 0),
					getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
		}
		MFRLogUtil.logDebug(">>>>damageRating<<<< = " + damageRating);
		return damageRating;
	}

	@Override
	protected float getProtectionRatio(ItemStack item) {
		CustomMaterial main = getCustomMaterial(item);
		if (main != CustomMaterial.NONE) {
			return main.hardness * design.getRating();
		}
		return super.getProtectionRatio(item);
	}

	/**
	 * Gets the modifier for a certain damage type (Cutting, Blunt, Piercing)
	 */
	@Override
	public float getProtectiveTrait(ItemStack item, int dtype) {
		float value = super.getProtectiveTrait(item, dtype);
		float cutting = 1.0F;
		float piercing = 1.0F;
		float blunt = 1.0F;

		CustomMaterial material = getCustomMaterial(item);
		if (material != CustomMaterial.NONE) {
			cutting = material.getArmourProtection(0);
			blunt = material.getArmourProtection(1);
			piercing = material.getArmourProtection(2);
		}

		if (dtype == 0)// Cutting
		{
			value *= cutting;
		}
		if (dtype == 2)// Piercing
		{
			value *= piercing;
		}
		if (dtype == 1)// Blunt
		{
			value *= blunt;
		}
		return value;
	}

	public float getResistanceModifier(ItemStack item, String hazard) {
		CustomMaterial custom = getCustomMaterial(item);
		if (custom != CustomMaterial.NONE) {
			return custom.resistance;
		}
		return super.getResistanceModifier(item, hazard);
	}

	public boolean isCustom() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, World world, List list, ITooltipFlag full) {
		CustomToolHelper.addInformation(item, list);
		float mass = getPieceWeight(item, EntityLiving.getSlotForItemStack(item));

		list.add(CustomMaterial.getWeightString(mass));
		super.addInformation(item, world, list, full);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (entity instanceof EntityPlayer
				&& armorType != EntityEquipmentSlot.LEGS
				&& armorType != EntityEquipmentSlot.FEET
				&& design == ArmourDesign.FIELDPLATE
				&& ConfigClient.customModel) {
			return getArmourTextureName(stack, entity, slot, type) + "_s.png";
		}
		return getArmourTextureName(stack, entity, slot, type) + ".png";
	}

	public String getArmourTextureName(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		String tex = MineFantasyReforged.MOD_ID + ":textures/models/armour/" + design.getName().toLowerCase() + "/" + texture;
		if (type == null && canColour())// bottom layer
		{
			return tex + "_cloth";
		}
		return tex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
		if (!(entityLiving instanceof EntityPlayer) || armorType == EntityEquipmentSlot.LEGS || armorType == EntityEquipmentSlot.FEET || !ConfigClient.customModel) {
			return super.getArmorModel(entityLiving, itemStack, armorSlot, model);
		}

		if (fullplate == null) {
			fullplate = new minefantasy.mfr.client.model.armour.ModelFullplate(1.0F);
		}
		model = this.design == ArmourDesign.FIELDPLATE ? (net.minecraft.client.model.ModelBiped) fullplate : null;
		if (model == null) {
			return super.getArmorModel(entityLiving, itemStack, armorSlot, model);
		}
		model.bipedHead.showModel = (this.armorType == EntityEquipmentSlot.HEAD);
		model.bipedHeadwear.showModel = (this.armorType == EntityEquipmentSlot.HEAD);

		model.bipedBody.showModel = (this.armorType == EntityEquipmentSlot.CHEST);
		model.bipedLeftArm.showModel = (this.armorType == EntityEquipmentSlot.CHEST);
		model.bipedRightArm.showModel = (this.armorType == EntityEquipmentSlot.CHEST);
		return model;
	}
}
