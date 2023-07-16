package minefantasy.mfr.util;

import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.CustomArmourEntry;
import minefantasy.mfr.api.armour.CustomDamageRatioEntry;
import minefantasy.mfr.api.armour.IArmourMFR;
import minefantasy.mfr.api.armour.IArmourPenetrationMob;
import minefantasy.mfr.api.armour.IArmouredEntity;
import minefantasy.mfr.api.armour.ISpecialArmourMFR;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.config.ConfigArmour;
import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import minefantasy.mfr.data.PlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class is used to calculate armour values like classes and weight
 */
public class ArmourCalculator {
	/**
	 * This is the scale for helmet, chest, legs and boots. Use this with 'slot' to
	 * scale things like weight and DT distribution
	 */
	public static final float[] sizes = new float[] {0.2F, 0.3F, 0.3F, 0.2F};
	/**
	 * For the percentage based armour, how much is ratio 2(50%) viewed as
	 */
	public static final float armourRatingScale = 100;
	/**
	 * What affects stamina regen, and what affects stamina cost
	 */
	public static final float[] encumberanceArray = new float[] {10F, 40F};
	/**
	 * When your movement starts and peaks at slowing down
	 */
	public static final float slowAmount = 10F;
	public static final float moveSpeedThresholdMin = 20F; //ToDo make a config option, potentially?
	public static final float getMoveSpeedThresholdMax = 40F;
	public static final IStoredVariable<Float> WORN_WEIGHT_KEY = IStoredVariable.StoredVariable.ofFloat("wornWeight", Persistence.DIMENSION_CHANGE);
	public static final IStoredVariable<Float> WORN_WEIGHT_NS_KEY = IStoredVariable.StoredVariable.ofFloat("wornWeightNS", Persistence.DIMENSION_CHANGE);

	static {
		PlayerData.registerStoredVariables(WORN_WEIGHT_KEY, WORN_WEIGHT_NS_KEY);
	}

	/**
	 * Gets the default using the items ArmourClass
	 */
	private static float getDefaultSuitWeight(ItemStack armour) {
		return CustomArmourEntry.getEntryVars(armour)[0];
	}

	private static float getDefaultBulk(ItemStack armour) {
		return CustomArmourEntry.getEntryVars(armour)[1];
	}

	public static ArmourDesign getDefaultAD(ItemStack armour) {
		return ArmourDesign.SOLID;
	}

	/**
	 * Converts a hit:damage ratio into a decimal percentage
	 */
	public static float convertToPercent(float ratio) {
		return (1F - (1F / ratio));
	}

	/**
	 * Estimates the distribution of stats over a piece
	 */
	private static float estimateScale(ItemStack item, EntityEquipmentSlot slot) {
		if (item.isEmpty()) {
			return 0;
		}

		if (item.getItem() instanceof ItemArmor) {
			ItemArmor armour = (ItemArmor) item.getItem();
			ArmorMaterial material = armour.getArmorMaterial();

			// gets the total added for all 4 pieces
			int totalProtection = material.getDamageReductionAmount(EntityEquipmentSlot.FEET) + material.getDamageReductionAmount(EntityEquipmentSlot.LEGS)
					+ material.getDamageReductionAmount(EntityEquipmentSlot.CHEST) + material.getDamageReductionAmount(EntityEquipmentSlot.HEAD);

			if (totalProtection > 0) {
				float singlePointWorth = 1F / totalProtection;
				return material.getDamageReductionAmount(slot) * singlePointWorth;
			}
		}
		return sizes[slot.getIndex()];
	}

	public static float getSpeedModForWeight(EntityPlayer user) {
		float mod = 0.0F;
		float min = moveSpeedThresholdMin;
		float max = getMoveSpeedThresholdMax - min;
		float mass = getTotalWeightOfWorn(user, true) - min;
		if (mass > 0 && max > 0) {
			mod -= (mass / max) * slowAmount * ConfigArmour.slowRate;
		}
		return mod;
	}

	/**
	 * Gets the total worn weight of a player: this updates every 20 ticks
	 */
	public static float getTotalWeightOfWorn(EntityPlayer player, boolean considerSpeed) {
		PlayerData data = PlayerData.get(player);
		IStoredVariable<Float> variable = considerSpeed ? WORN_WEIGHT_NS_KEY : WORN_WEIGHT_KEY;
		if (data.getVariable(variable) == null) {
			return setTotalWeightOfWorn(player, considerSpeed);
		} else {
			return data.getVariable(variable);
		}
	}

	public static void updateWeights(EntityPlayer player) {
		setTotalWeightOfWorn(player, true);
		setTotalWeightOfWorn(player, false);
	}

	/**
	 * @param considerSpeed if true: some pieces will be ignored, ment for speed calculations
	 */
	public static float setTotalWeightOfWorn(EntityPlayer player, boolean considerSpeed) {
		PlayerData data = PlayerData.get(player);
		IStoredVariable<Float> variable = considerSpeed ? WORN_WEIGHT_NS_KEY : WORN_WEIGHT_KEY;
		float weight = 0.0F;
		Iterable<ItemStack> armour = player.getArmorInventoryList();
		for (ItemStack stack : armour) {
			if (!considerSpeed || shouldArmourAlterSpeed(stack)) {
				weight += getPieceWeight(stack, EntityLiving.getSlotForItemStack(stack));
			}
		}
		data.setVariable(variable, weight);
		return weight;
	}

	public static float getPieceWeight(ItemStack item, EntityEquipmentSlot slot) {
		if (item.isEmpty() || item.getItem() == Items.AIR) {
			return 0.0F;
		}
		if (item.getItem() instanceof IArmourMFR) {
			return ((IArmourMFR) item.getItem()).getPieceWeight(item, slot);
		}
		return getDefaultSuitWeight(item);
	}

	private static boolean shouldArmourAlterSpeed(ItemStack armour) {
		if (armour.isEmpty()) {
			return false;
		}

		return armour.getItem() instanceof IArmourMFR || CustomArmourEntry.doesPieceSlowDown(armour);
	}

	public static float convertKgToIbs(float kg) {
		return kg * 2.5F;
	}

	public static float convertIbsToKg(float pounds) {
		return pounds / 2.5F;
	}

	/**
	 * Modifes a value for resistence
	 *
	 * @param src               the source (breaks down to ratio)
	 * @param value             the variable to modify
	 * @param cuttingProtection the cutting resistance
	 * @param bluntProtection   the blunt resistance
	 * @param pierceProtection  the piercing resistance
	 * @return damage adjusted for Armor Class, in a float
	 */
	public static float adjustArmorClassForDamage(DamageSource src, float value, float cuttingProtection, float bluntProtection, float pierceProtection) {
		float[] ratio = getRatioForSource(src);
		if (ratio == null) {
			return value;// Null means undefined
		}

		return modifyArmorClassForType(value, ratio[0], ratio[1], ratio[2], cuttingProtection, bluntProtection, pierceProtection, getArmourPenetration(src));
	}

	public static float modifyArmorClassForType(int type, float value, float cuttingProt, float bluntProt, float pierceProt) {
		float[] f = new float[] {0F, 0F, 0F};
		f[type] = 1.0F;
		return modifyArmorClassForType(value, f[0], f[1], f[2], cuttingProt, bluntProt, pierceProt, 0F);
	}

	public static float modifyArmorClassForType(float value, float cutting, float blunt, float pierce, float cuttingProt, float bluntProt, float pierceProt, float specialAP) {
		if (ConfigArmour.advancedDamageTypes) {
			// Averages the ratio between cutting and blunt, while modifying it by the
			// armour traits
			value *= (((cutting * cuttingProt) + (blunt * bluntProt) + (pierce * pierceProt))
					/ (cutting + blunt + pierce));
		}
		float ACModifier = 1.0F + specialAP;
		value *= ACModifier;
		return Math.max(value, 0F);
	}

	public static float getArmourPenetration(DamageSource source) {
		if (source != null && source.getTrueSource() != null) {
			Entity user = source.getTrueSource();// The attacker
			Entity damager = source.getImmediateSource();// The thing causing damage(like arrows)

			if (user == damager && user instanceof EntityLivingBase && !((EntityLivingBase) user).getHeldItemMainhand().isEmpty()) {
				if (((EntityLivingBase) user).getHeldItemMainhand().getItem() instanceof IDamageType) {
					return ((IDamageType) ((EntityLivingBase) user).getHeldItemMainhand().getItem()).getPenetrationLevel(((EntityLivingBase) user).getHeldItemMainhand());
				}
			}
			if (user != damager) {
				if (damager instanceof IDamageType) {
					return ((IDamageType) damager).getPenetrationLevel(damager);
				}
			}
		}
		return 0F;
	}

	public static float[] getRatioForSource(DamageSource source) {
		if (source.isMagicDamage() || source.isFireDamage() || source == DamageSource.STARVE) {
			return null;
		}

		if (source.isExplosion() || source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) {
			return new float[] {0, 1, 0};// blunt
		}
		if (source == DamageSource.CACTUS) {
			return new float[] {0, 0, 1};// pierce
		}
		if (source != null && source.getImmediateSource() != null && source.getTrueSource() != null) {
			Entity user = source.getTrueSource();// The attacker
			Entity damager = source.getImmediateSource();// The thing causing damage(like arrows)

			if (user == damager && user instanceof EntityLivingBase) {
				return getRatioForMelee((EntityLivingBase) user, ((EntityLivingBase) user).getHeldItemMainhand());
			}
			return getRatioForIndirect(damager);
		}

		return new float[] {0, 1, 0};
	}

	private static float[] getRatioForIndirect(Entity damager) {
		if (damager == null) {
			return new float[] {1, 1, 1};// No damage type.
		}
		if (damager instanceof IDamageType) {
			return ((IDamageType) damager).getDamageRatio(damager);
		}
		if (damager instanceof EntityArrow) {
			return new float[] {0, 0, 1};
		}

		if (ConfigArmour.useConfigIndirectDmg) {
			return CustomDamageRatioEntry.getEntityTraits(ForgeRegistries.ENTITIES.getKey(EntityRegistry.getEntry(damager.getClass())));
		}
		return new float[] {1, 1, 1};
	}

	public static float[] getRatioForMelee(EntityLivingBase user, ItemStack weapon) {
		if (weapon.isEmpty()) {
			return getMobDefault(user);
		}
		return getRatioForWeapon(user, weapon);
	}

	public static float[] getRatioForWeapon(ItemStack weapon) {
		return getRatioForWeapon(null, weapon);
	}

	public static float[] getRatioForWeapon(EntityLivingBase user, ItemStack weapon) {
		Item item = weapon.getItem();

		if (item instanceof IDamageType) {
			return user != null ? ((IDamageType) item).getDamageRatio(weapon, user) : ((IDamageType) item).getDamageRatio(weapon);
		}
		if (CustomDamageRatioEntry.getTraits(weapon) != null) {
			return CustomDamageRatioEntry.getTraits(weapon);
		}
		if (item instanceof ItemSword) {
			return new float[] {1, 0, 0};// sword is cutting by default
		}
		if (item instanceof ItemAxe) {
			return new float[] {3, 1, 0};// Axe Ratio is about 3:1 (25%blunt)
		}
		if (item instanceof ItemPickaxe) {
			return new float[] {0, 0, 1};// Picks are pierce
		}
		if (item instanceof ItemHoe || item instanceof ItemSpade) {
			return new float[] {0, 1, 0};// Tools are blunt
		}

		return CustomDamageRatioEntry.getTraits(weapon);
	}

	private static float[] getMobDefault(EntityLivingBase user) {
		if (user instanceof EntitySpider) {
			return new float[] {20F, 10F, 80F};// 10% blunt, 20% slash, 80% pierce
		}
		if (user instanceof IArmourPenetrationMob) {
			return ((IArmourPenetrationMob) user).getHitTraits();
		}
		return new float[] {0, 1, 0};
	}

	public static String getEntityRegisterName(Entity entity) {
		if (entity == null) {
			return "genetic";
		}
		String s = EntityList.getEntityString(entity);

		if (s == null) {
			s = "generic";
		}

		return s;
	}

	public static float getTotalBulk(EntityLivingBase user) {
		if (PowerArmour.isWearingCogwork(user)) {
			return 5F;
		}
		return getEquipmentBulk(user);
	}

	/**
	 * Defined by Light/Medium/Heavy armour average 0 for light, 1 for full medium,
	 * 2 for full heavy
	 */
	public static float getEquipmentBulk(EntityLivingBase user) {
		float bulk = 0.0F;
		Iterable<ItemStack> armour = user.getArmorInventoryList();
		for (ItemStack stack : armour) {
			String s = getArmourClass(stack);
			if (s != null) {
				if (s.equalsIgnoreCase("Medium")) {
					bulk += 1;
				}
				if (s.equalsIgnoreCase("Heavy")) {
					bulk += 2;
				}
			}
		}
		return bulk / 4;
	}

	public static String getArmourClass(ItemStack armour) {
		if (armour.isEmpty()) {
			return null;
		}
		if (armour.getItem() instanceof IArmourMFR) {
			return ((IArmourMFR) armour.getItem()).getSuitWeightType(armour);
		}
		return CustomArmourEntry.getArmourClass(armour);
	}

	// THRESHOLD

	/**
	 * CLIENT WHOLE ENTITY: Gets the total DT of an entity by damage type: Used by
	 * the Screen renderer
	 */
	@SideOnly(Side.CLIENT)
	public static float getDTDisplay(EntityLivingBase user, int id) {
		float armourDT = 0;

		Iterable<ItemStack> armour = user.getArmorInventoryList();
		for (ItemStack stack : armour) {
			if (!stack.isEmpty() && stack.getItem() instanceof ISpecialArmourMFR) {
				float threshold = getArmourValueMod(stack, ((ISpecialArmourMFR) stack.getItem()).getDamageTypeDisplay(stack, id));
				armourDT += threshold;
			}
		}
		return armourDT;
	}

	/**
	 * CLIENT SINGLE PIECE: Gets a stat for a single piece: Used by Tooltips
	 */
	@SideOnly(Side.CLIENT)
	public static float getDTForDisplayPiece(ItemStack armour, int id) {
		if (!armour.isEmpty() && armour.getItem() instanceof ISpecialArmourMFR) {
			return getArmourValueMod(armour, ((ISpecialArmourMFR) armour.getItem()).getDamageTypeDisplay(armour, id));
		}
		return 0F;
	}

	/**
	 * CLIENT SINGLE PIECE: Gets a stat for a single piece: Used by Tooltips
	 */
	@SideOnly(Side.CLIENT)
	public static float getDamageReductionForDisplayPiece(ItemStack armour, int id) {
		if (armour.isEmpty()) {
			return 0F;
		}
		if (armour.getItem() instanceof ISpecialArmourMFR) {
			return getArmourValueMod(armour, ((ISpecialArmourMFR) armour.getItem()).getDamageRatingDisplay(armour, id));
		}
		return 0F;
	}

	@SideOnly(Side.CLIENT)

    /*
      CLIENT WHOLE ENTITY: Gets the total DT of an entity by damage type: Used by
      the Screen renderer
     */

	public static float getDRDisplay(EntityLivingBase user, int id) {
		float armourDT = 0;

		Iterable<ItemStack> armour = user.getArmorInventoryList();
		for (ItemStack stack : armour) {
			if (!stack.isEmpty() && stack.getItem() instanceof ISpecialArmourMFR) {
				float threshold = getArmourValueMod(stack, ((ISpecialArmourMFR) stack.getItem()).getDamageRatingDisplay(stack, id));
				armourDT += threshold;
			}
		}
		return armourDT;
	}

	/**
	 * MECHANIC WHOLE ENTITY: Gets the threshold of a whole entity
	 */
	public static float getACThreshold(EntityLivingBase user, DamageSource src) {
		float naturalAC = getACForMob(user);

		if (user instanceof IArmouredEntity) {
			naturalAC = ((IArmouredEntity) user).getThreshold(src);
		}

		float armourDT = 0;
		Iterable<ItemStack> armour = user.getArmorInventoryList();
		for (ItemStack stack : armour) {
			if (!stack.isEmpty() && stack.getItem() instanceof ISpecialArmourMFR) {
				float threshold = getArmourValueMod(stack, ((ISpecialArmourMFR) stack.getItem()).getDamageTypeValue(user, stack, src));
				armourDT += threshold;
			}
		}
		return naturalAC + armourDT;
	}

	/**
	 * MECHANIC DT MODIFIER: Gets the DT Modifier for durability loss
	 */
	private static float modifyDTOnDura(ItemStack armour) {
		float percentQuality = getPercentQuality(armour);

		float reduction = 0.8F;

		if (percentQuality < reduction) {
			float mod = Math.max(0.1F, percentQuality / reduction);

			return mod;
		}
		return 1.0F;
	}

	/**
	 * MECHANIC DAMAGE FINDER: Gets the percent durability of an item
	 */
	private static float getPercentQuality(ItemStack armour) {
		return 1F - ((float) armour.getItemDamage() / (float) armour.getMaxDamage());
	}

	/**
	 * MECHANIC DT CALCULATOR: Gets the natural DT of an entity
	 */
	private static float getACForMob(EntityLivingBase user) {
		if (user.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
			return user.getMaxHealth() / 10F;// 1.6AC for spiders
		}
		return 0F;
	}

	/**
	 * MECHANIC DT CALCULATOR: Gets the modifiers based on the itemstack (such as
	 * damage and NBT) DT OR DR
	 */
	public static float getArmourValueMod(ItemStack armour, float DT) {
		float initDT = DT;
		if (armour.hasTagCompound() && armour.getTagCompound().hasKey("MF_Inferior")) {
			DT *= (armour.getTagCompound().getBoolean("MF_Inferior") ? 0.8F : 1.2F);
		}
		DT *= modifyDTOnDura(armour);
		return DT;
	}

	/**
	 * MECHANIC DT CALCULATOR: How much is armour damaged by a hit
	 */
	public static int getDamageToDura(EntityLivingBase user, DamageSource source, ItemStack armour, float dam) {
		if (source.isUnblockable()) {
			return 0;
		}
		if (source.getImmediateSource() != null && source.getImmediateSource() == source.getTrueSource()) {
			dam *= getMobArmourDamage(source.getImmediateSource());
		}
		if (getPercentQuality(armour) > 0.1F) {
			dam = Math.max(1.0F, dam / 4F);
		}
		return (int) dam;
	}

	/**
	 * MECHANIC DT CALCULATOR: How much armour damage the entity does
	 */
	private static float getMobArmourDamage(Entity src) {
		if (src instanceof EntitySpider) {
			return 3.0F;
		}
		return 0.5F;
	}

	public static void damageArmour(EntityLivingBase target, int damageAmount) {
		if (!target.world.isRemote) {
			for (ItemStack stack : target.getArmorInventoryList()) {
				if (!stack.isEmpty()) {
					stack.damageItem(damageAmount, target);
					if (stack.isEmpty()) // item broke
						target.world.playSound(null, target.getPosition(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
			}
		}
	}

	/**
	 * Determines how much armour reduces parrying ability
	 */
	public static float getParryModifier(EntityLivingBase user) {
		float bulk = getTotalBulk(user);

		return 1.0F / ((bulk * 0.5F) + 1);// 50% in heavy
	}

	public static int modifyParryCooldown(EntityLivingBase user, int ticks) {
		float bulk = getTotalBulk(user);

		return (int) Math.max(5, ticks + (bulk * 5));// plate adds 5t
	}
}
