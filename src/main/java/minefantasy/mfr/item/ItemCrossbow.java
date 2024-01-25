package minefantasy.mfr.item;

import com.google.common.collect.Lists;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.archery.IArrowHandler;
import minefantasy.mfr.api.archery.IDisplayMFRAmmo;
import minefantasy.mfr.api.archery.IFirearm;
import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.api.crafting.engineer.ICrossbowPart;
import minefantasy.mfr.api.tool.IScope;
import minefantasy.mfr.api.weapon.IDamageModifier;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.client.render.item.RenderCrossbow;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.mechanics.CombatMechanics;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.util.ModelLoaderHelper;
import minefantasy.mfr.util.PowerArmour;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemCrossbow extends ItemBaseMFR implements IFirearm, IDisplayMFRAmmo, IDamageModifier, IDamageType, IScope, ISpecialSalvage {
	private static final String partNBT = "MineFantasy_GunPiece_";
	public static String useTypeNBT = "MF_ActionInUse";
	private final String[] fullParts = new String[]{"mod", "muzzle", "mechanism", "stock"};

	public ItemCrossbow() {
		super("crossbow_custom");
		this.setCreativeTab(MineFantasyTabs.tabGadget);
		this.setFull3D();
		this.setMaxDamage(150);
		this.setMaxStackSize(1);
	}

	public static void setUseAction(ItemStack item, String action) {
		AmmoMechanics.getNBT(item).setString(useTypeNBT, action);
	}

	public static String getUseAction(ItemStack item) {
		String action = AmmoMechanics.getNBT(item).getString(useTypeNBT);

		return action != null ? action : "null";
	}

	public static void setPart(String part, ItemStack item, int id) {
		NBTTagCompound nbt = getNBT(item);
		nbt.setInteger(partNBT + part, id);
	}

	public static int getPart(String part, ItemStack item) {
		NBTTagCompound nbt = getNBT(item);
		if (nbt.hasKey(partNBT + part)) {
			return nbt.getInteger(partNBT + part);
		}
		return -1;
	}

	public static NBTTagCompound getNBT(ItemStack item) {
		if (!item.hasTagCompound())
			item.setTagCompound(new NBTTagCompound());
		return item.getTagCompound();
	}

	@Override
	public EnumAction getItemUseAction(ItemStack item) {
		String action = getUseAction(item);
		if (action.equalsIgnoreCase("reload")) {
			return EnumAction.valueOf("mfr_block");
		}
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (AmmoMechanics.isDepleted(stack)) {
			reloadFirearm(player);
			return ActionResult.newResult(EnumActionResult.FAIL, stack);
		}
		ItemStack loaded = AmmoMechanics.getArrowOnBow(stack);
		if (loaded.isEmpty() || player.isSwingInProgress)// RELOAD
		{
			startUse(player, stack, "reload");
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}  // FIRE

		startUse(player, stack, "fire");
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack item, World world, EntityLivingBase user) {
		boolean infinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, item) > 0;
		user.swingArm(EnumHand.MAIN_HAND);
		ItemStack loaded = AmmoMechanics.getArrowOnBow(item);
		ItemStack storage = AmmoMechanics.getAmmo(item);
		String action = getUseAction(item);
		boolean shouldConsume = true;

		if (action.equalsIgnoreCase("reload")) {
			if (storage.isEmpty() && infinity) {
				shouldConsume = false;
				storage = MineFantasyItems.STANDARD_BOLT.construct("Magic");
			}
			if (!storage.isEmpty())// RELOAD
			{
				boolean success = false;
				if (loaded.isEmpty()) {
					ItemStack ammo = storage.copy();
					ammo.setCount(1);
					if (shouldConsume && user instanceof EntityPlayer){
						AmmoMechanics.consumeAmmo((EntityPlayer) user, item);
					}
					AmmoMechanics.putAmmoOnFirearm(item, ammo);
					success = true;
				} else if (loaded.isItemEqual(storage) && loaded.getCount() < getAmmoCapacity(item)) {
					if (shouldConsume && user instanceof EntityPlayer){
						AmmoMechanics.consumeAmmo((EntityPlayer) user, item);
					}
					loaded.grow(1);
					AmmoMechanics.putAmmoOnFirearm(item, loaded);
					success = true;
				}
				if (success) {
					user.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0F, 1.0F);
				}
			}
		}
		return super.onItemUseFinish(item, world, user);
	}

	@Override
	public void onUsingTick(ItemStack item, EntityLivingBase player, int time) {
		ItemStack loaded = AmmoMechanics.getArrowOnBow(item);
		int max = getMaxItemUseDuration(item);

		if (time == (max - 5) && getUseAction(item).equalsIgnoreCase("reload") && (loaded.isEmpty() || loaded.getCount() < getAmmoCapacity(item))) {
			player.playSound(MineFantasySounds.CROSSBOW_LOAD, 1.0F, 1 / (getFullValue(item, "speed") / 4F));
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack crossbow, World world, EntityLivingBase user, int timeLeft) {
		if (!(user instanceof EntityPlayer)) {
			return;
		}

		final EntityPlayer player = (EntityPlayer) user;

		String action = getUseAction(crossbow);
		ItemStack boltOnBow = AmmoMechanics.getArrowOnBow(crossbow);

		if (!(boltOnBow.getItem() instanceof ItemArrowMFR)) {
			return;
		}

		final boolean isInfinite = player.capabilities.isCreativeMode || boltOnBow.getItem() instanceof ItemArrow && ((ItemArrow) boltOnBow.getItem()).isInfinite(boltOnBow, crossbow, player);
		float power = this.getFullValue(crossbow, "power");

		if (!world.isRemote && action.equalsIgnoreCase("fire")) {
			IArrowHandler arrowHandler = AmmoMechanics.handlers.get(Constants.MFR_BOLT_HANDLER);
			ItemArrowMFR itemArrow = (ItemArrowMFR) boltOnBow.getItem();

			EntityArrow entityArrow = itemArrow.createArrow(world, boltOnBow, player);

			entityArrow = arrowHandler.onFireArrow(entityArrow, boltOnBow, crossbow, power, player);

			final int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, crossbow);
			if (powerLevel > 0 && entityArrow instanceof EntityArrowMFR) {
				((EntityArrowMFR) entityArrow).setPower(1 + (0.25F * powerLevel));
			}

			final int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, crossbow);
			if (punchLevel > 0) {
				entityArrow.setKnockbackStrength(punchLevel);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, crossbow) > 0) {
				entityArrow.setFire(100);
			}

			AmmoMechanics.damageContainer(crossbow, player, 1);
			world.playSound(null, player.posX, player.posY, player.posZ, MineFantasySounds.BOW_FIRE, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + Math.min(power, 1) * 0.5F);

			if (isInfinite) {
				entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
			}

			world.spawnEntity(entityArrow);

			if (!isInfinite && !boltOnBow.isEmpty()) {
				boltOnBow.shrink(1);
				AmmoMechanics.putAmmoOnFirearm(crossbow, (boltOnBow.getCount() > 0 ? boltOnBow : ItemStack.EMPTY));
			}

			recoilUser((EntityPlayer) user, getFullValue(crossbow, "recoil"));
		}

		stopUse(crossbow);
	}

	public void startUse(EntityPlayer user, ItemStack item, String action) {
		setUseAction(item, action);
		if (user != null)
			user.setActiveHand(EnumHand.MAIN_HAND);
	}

	public void stopUse(ItemStack item) {
		startUse(null, item, "null");
	}

	private void recoilUser(EntityPlayer user, float value) {
		if (PowerArmour.isPowered(user)) {
			return;
		}
		float str = CombatMechanics.getStrengthEnhancement(user) + 1;
		value /= str;

		float angle = value;
		user.rotationPitch -= itemRand.nextFloat() * angle;
		user.rotationYawHead += itemRand.nextFloat() * angle - 0.5F;
	}

	@Override
	public void reloadFirearm(EntityPlayer player) {
		player.openGui(MineFantasyReforged.MOD_ID, NetworkHandler.GUI_RELOAD, player.world, 1, 0, 0);
	}

	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
		if (ItemStack.areItemsEqualIgnoreDurability(oldStack, newStack)) {
			return true;
		}
		return super.canContinueUsing(oldStack, newStack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		// This method does some VERY strange things! Despite its name, it also seems to affect the updating of NBT...

		if(!oldStack.isEmpty() || !newStack.isEmpty()){
			// We only care about the situation where we specifically want the animation NOT to play.
			if(oldStack.getItem() == newStack.getItem() && !slotChanged)
				return false;
		}

		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		String action = getUseAction(item);
		if (action.equalsIgnoreCase("reload")) {
			return (int) (getFullValue(item, "speed") * 20F);
		}
		return 72000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag fullInfo) {
		super.addInformation(item, world, list, fullInfo);

		list.add(I18n.translateToLocalFormatted("attribute.crossbow.power.name", getFullValue(item, "power")));
		list.add(I18n.translateToLocalFormatted("attribute.crossbow.speed.name", getFullValue(item, "speed")));
		list.add(I18n.translateToLocalFormatted("attribute.crossbow.recoil.name", getFullValue(item, "recoil")));
		list.add(I18n.translateToLocalFormatted("attribute.crossbow.spread.name", getFullValue(item, "spread")));
		list.add(I18n.translateToLocalFormatted("attribute.crossbow.capacity.name", getAmmoCapacity(item)));
		list.add(I18n.translateToLocalFormatted("attribute.crossbow.bash.name", getMeleeDmg(item)));
	}

	@Override
	public String getItemStackDisplayName(ItemStack item) {
		String base = getNameModifier(item, "stock");
		String arms = getNameModifier(item, "mechanism");
		String mod = getNameModifier(item, "mod");

		String fullName = "Crossbow";

		if (base != null)
			fullName = base;
		if (arms != null)
			fullName = arms + " " + fullName;
		if (mod != null)
			fullName = mod + " " + fullName;

		return fullName;
	}

	/**
	 * Constructs a crossbow with a list of parts
	 */
	public ItemStack constructCrossbow(ICrossbowPart... crossbowParts) {
		ItemStack crossbow = new ItemStack(this);

		for (ICrossbowPart part : crossbowParts) {
			if (part != null) {
				setPart(crossbow, part);
			}
		}
		return crossbow;
	}

	/**
	 * Adds a part to a crossbow
	 */
	public ItemStack setPart(ItemStack item, ICrossbowPart part) {
		setPart(part.getComponentType(), item, part.getID());
		return item;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		items.add(constructCrossbow((ICrossbowPart) MineFantasyItems.CROSSBOW_HANDLE_WOOD, (ICrossbowPart) MineFantasyItems.CROSSBOW_ARMS_BASIC));
		items.add(constructCrossbow((ICrossbowPart) MineFantasyItems.CROSSBOW_STOCK_WOOD, (ICrossbowPart) MineFantasyItems.CROSSBOW_ARMS_LIGHT));
		items.add(constructCrossbow((ICrossbowPart) MineFantasyItems.CROSSBOW_STOCK_WOOD, (ICrossbowPart) MineFantasyItems.CROSSBOW_ARMS_BASIC, (ICrossbowPart) MineFantasyItems.CROSSBOW_AMMO));
		items.add(constructCrossbow((ICrossbowPart) MineFantasyItems.CROSSBOW_STOCK_WOOD, (ICrossbowPart) MineFantasyItems.CROSSBOW_ARMS_HEAVY, (ICrossbowPart) MineFantasyItems.CROSSBOW_BAYONET));
		items.add(constructCrossbow((ICrossbowPart) MineFantasyItems.CROSSBOW_STOCK_IRON, (ICrossbowPart) MineFantasyItems.CROSSBOW_ARMS_ADVANCED, (ICrossbowPart) MineFantasyItems.CROSSBOW_SCOPE));
	}

	public String getNameModifier(ItemStack item, String partname) {
		ICrossbowPart part = ItemCrossbowPart.getPart(partname, getPart(partname, item));
		if (part != null) {
			String name = part.getUnlocalisedName();
			if (name != null) {
				return I18n.translateToLocal(name);
			}
		}
		return null;
	}

	/**
	 * Get the modifier for a part (such as power, speed, recoil, capacity and
	 * spread)
	 */
	public float getModifierForPart(ItemStack item, String partName, String variable) {
		ICrossbowPart part = ItemCrossbowPart.getPart(partName, getPart(partName, item));
		if (part != null) {
			return part.getModifier(variable);
		}
		return 0F;
	}

	/**
	 * Checks all "fullParts" for value modifiers
	 */
	public float getFullValue(ItemStack item, String variable) {
		float min = variable.equalsIgnoreCase("speed") ? 0.5F : 0F;
		float value = 0F;

		for (String part : fullParts) {
			value += getModifierForPart(item, part, variable);
		}

		return Math.max(min, value);
	}

	@Override
	public int getAmmoCapacity(ItemStack item) {
		return 1 + (int) getModifierForPart(item, "mod", "capacity");// only mod affects capacity
	}

	public float getMeleeDmg(ItemStack item) {
		return 1 + getModifierForPart(item, "muzzle", "bash");// only muzzle affects capacity
	}

	@Override
	public boolean canAcceptAmmo(ItemStack weapon, String ammo) {
		return ammo.equalsIgnoreCase("bolt");
	}

	@Override
	public float[] getDamageRatio(Object... implement) {
		if (implement.length > 0 && implement[0] instanceof ItemStack) {
			if (this.getMeleeDmg((ItemStack) implement[0]) > 1.0F)// Bayonet is used
			{
				return new float[]{0F, 0F, 1F};
			}
		}

		return new float[]{0F, 1F, 0F};
	}

	@Override
	public float getPenetrationLevel(Object implement) {
		return 0;
	}

	@Override
	public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam,
			boolean properHit) {
		return initialDam + this.getMeleeDmg(item) - 1;
	}

	@Override
	public float getZoom(ItemStack item) {
		return getUseAction(item).equalsIgnoreCase("fire") ? getModifierForPart(item, "mod", "zoom") : 0F;
	}

	@Override
	public List<ItemStack> getSalvage(ItemStack item) {
		return Lists.newArrayList(
				new ItemStack(getItem(item, "stock")),
				new ItemStack(getItem(item, "mechanism")),
				new ItemStack(getItem(item, "muzzle")),
				new ItemStack(getItem(item, "mod"))
		);
	}

	public Item getItem(ItemStack item, String type) {
		return (Item) ItemCrossbowPart.getPart(type, getPart(type, item));
	}

	@Override
	public int getMaxDamage(ItemStack item) {
		return super.getMaxDamage(item) + (int) getFullValue(item, "durability");
	}

	public boolean isHandCrossbow(ItemStack item) {
		ICrossbowPart part = ItemCrossbowPart.getPart("stock", getPart("stock", item));
		if (part != null) {
			return part.makesSmallWeapon();
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "normal");
		ModelLoaderHelper.registerWrappedItemModel(this, new RenderCrossbow(() -> modelLocation), modelLocation);
	}
}