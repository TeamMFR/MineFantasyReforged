package minefantasy.mfr;

import com.google.common.base.CaseFormat;
import minefantasy.mfr.api.armour.ISpecialArmourMFR;
import minefantasy.mfr.api.farming.FarmingHelper;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.api.tool.ISmithTongs;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.client.ClientItemsMFR;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.config.ConfigSpecials;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.constants.WeaponClass;
import minefantasy.mfr.container.ContainerAnvil;
import minefantasy.mfr.container.ContainerForge;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.EntityItemUnbreakable;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.event.LevelUpEvent;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.integration.CustomStone;
import minefantasy.mfr.item.ItemArmourBaseMFR;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.mechanics.CombatMechanics;
import minefantasy.mfr.mechanics.PlayerTickHandler;
import minefantasy.mfr.mechanics.RPGElements;
import minefantasy.mfr.mechanics.StaminaBar;
import minefantasy.mfr.mechanics.StaminaMechanics;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.LevelUpPacket;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.ArrowEffectsMF;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.TacticalManager;
import minefantasy.mfr.util.ToolHelper;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

import static minefantasy.mfr.constants.Constants.CRAFTED_BY_NAME_TAG;

/**
 * General-purpose event handler for things, as a replacement for {EventManagerMFR}
 *
 * @since MFR
 */

@Mod.EventBusSubscriber
public final class MFREventHandler {
	private static final XSTRandom random = new XSTRandom();

	private MFREventHandler() {} // No instances!

	/**
	 * Shows a tooltip list of the different damage reduction attributes of an armor item (like blunt, cutting, pierce). Also displays the difference between
	 * the currently worn armor's stats and the hovered item.
	 *
	 * @param armour The armor ItemStack currently being checked
	 * @param user   The user hovering over the ItemStack
	 * @param list   The tooltip list of the ItemStack
	 * @param extra  If advanced tooltips are enabled (=true), show some extra info
	 */
	public static void addArmorDamageReductionTooltip(ItemStack armour, EntityPlayer user, List<String> list, boolean extra) {
		list.add("");
		String armourClass = ArmourCalculator.getArmourClass(armour);  // Light/Medium/Heavy
		if (armourClass != null) {
			list.add(I18n.format("attribute.armour." + armourClass));
		}
		if (armour.getItem() instanceof ISpecialArmourMFR) {
			if (ArmourCalculator.advancedDamageTypes) {
				list.add(TextFormatting.BLUE + I18n.format("attribute.armour.protection"));
				addSingleDamageReductionTooltip(armour, user, 0, list, true);
				addSingleDamageReductionTooltip(armour, user, 2, list, true);
				addSingleDamageReductionTooltip(armour, user, 1, list, true);
			} else {
				addSingleDamageReductionTooltip(armour, user, 0, list, false);
			}
		}
	}

	/**
	 * Shows adds a damage reduction attribute label to the ItemStack of the specified damage reduction type
	 *
	 * @param armour   The armor ItemStack currently being checked
	 * @param user     The user hovering over the ItemStack
	 * @param id       The id of the armor rating type
	 * @param list     The tooltip list of the ItemStack
	 * @param advanced If advanced tooltips are enabled or not
	 */
	public static void addSingleDamageReductionTooltip(ItemStack armour, EntityPlayer user, int id, List<String> list, boolean advanced) {
		EntityEquipmentSlot slot = ((ItemArmor) armour.getItem()).armorType;
		String attach = "";

		int rating = (int) (ArmourCalculator.getDamageReductionForDisplayPiece(armour, id) * 100F);
		int equipped = (int) (ArmourCalculator.getDamageReductionForDisplayPiece(user.getItemStackFromSlot(slot), id) * 100F);

		if (rating > 0 || equipped > 0) {
			if (equipped > 0 && rating != equipped) {
				float d = rating - equipped;

				attach += d > 0 ? TextFormatting.DARK_GREEN : TextFormatting.RED;

				String d2 = ItemWeaponMFR.decimal_format.format(d);
				attach += " (" + (d > 0 ? "+" : "") + d2 + ")";
			}
			if (advanced) {
				list.add(TextFormatting.BLUE + I18n.format("attribute.armour.rating." + id) + " "
						+ rating + attach);
			} else {
				list.add(TextFormatting.BLUE + I18n.format("attribute.armour.protection") + ": "
						+ rating + attach);
			}
		}
	}

	/**
	 * Adds a tooltip to the specified ItemStack about the crafter (if it has info about the crafter in nbt)
	 *
	 * @param tool the ItemStack
	 * @param list the tooltip of the ItemStack
	 */
	private static void showCrafterTooltip(ItemStack tool, List<String> list) {
		Tool toolStack = ToolHelper.getToolTypeFromStack(tool);
		int tier = ToolHelper.getCrafterTier(tool);
		float efficiency = ToolHelper.getCrafterEfficiency(tool);

		list.add(I18n.format("attribute.mfcrafttool.name") + ": " + toolStack.getDisplayName());
		list.add(I18n.format("attribute.mfcrafttier.name") + ": " + tier);
		list.add(I18n.format("attribute.mfcrafteff.name") + ": " + efficiency);
	}

	// ================================================ Event Handlers ================================================

	/**
	 * Attaches the dynamic tooltips to itemStacks. Called when an item is hovered with the cursor
	 */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onItemTooltip(ItemTooltipEvent event) {
		//  Excludes this from the initial search tree population for tooltips (during game initialization).
		if (event.getEntity() == null) {
			return;
		}

		if (!event.getItemStack().isEmpty()) {
			boolean saidArtefact = false;
			int[] ids = OreDictionary.getOreIDs(event.getItemStack());
			boolean hasInfo = false;
			if (ids != null && event.getEntityPlayer() != null) {
				for (int id : ids) {
					String s = OreDictionary.getOreName(id);
					if (s != null) {
						if (!hasInfo && s.startsWith("ingot")) {
							String s2 = s.substring(5, s.length());
							CustomMaterial material = CustomMaterial.getMaterial(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s2));
							if (material != CustomMaterial.NONE){
								hasInfo = true;
							}
							else {
								if (!s.contains("Brick")){
									ArrayList<CustomMaterial> metalMaterials = CustomMaterial.getList("metal");
									for (CustomMaterial metal : metalMaterials){
										if (metal instanceof MetalMaterial) {
											if (((MetalMaterial) metal).oreDictList.equals(s)){
												material = metal;
												if (material != CustomMaterial.NONE){
													break;
												}
											}
										}
									}
								}
							}

							CustomToolHelper.addComponentString(event.getItemStack(), event.getToolTip(), material);
						}
						if (s.startsWith("Artefact-")) {
							if (!saidArtefact) {
								String knowledge = s.substring(9).toLowerCase();

								if (!ResearchLogic.hasInfoUnlocked(event.getEntityPlayer(), knowledge) && !ResearchLogic.alreadyUsedArtefact(event.getEntityPlayer(), ResearchLogic.getResearch(knowledge), event.getItemStack())) {
									saidArtefact = true;
									event.getToolTip().add(TextFormatting.AQUA + I18n.format("info.hasKnowledge"));
								}
							}
						} else if (ConfigClient.displayOreDict) {
							event.getToolTip().add("oreDict: " + s);
						}
					}
				}
			}

			if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("MF_Inferior")) {
				if (event.getItemStack().getTagCompound().getBoolean("MF_Inferior")) {
					event.getToolTip().add(TextFormatting.RED + I18n.format("attribute.inferior.name"));
				}
				if (!event.getItemStack().getTagCompound().getBoolean("MF_Inferior")) {
					event.getToolTip().add(TextFormatting.GREEN + I18n.format("attribute.superior.name"));
				}
			}
			if (event.getEntityPlayer() != null && event.getToolTip() != null && event.getFlags() != null) {
				if (event.getItemStack().getItem() instanceof ItemArmor
						&& (!(event.getItemStack().getItem() instanceof ItemArmourBaseMFR)
						|| ClientItemsMFR.showSpecials(event.getItemStack(), event.getEntityPlayer().world, event.getToolTip(), event.getFlags()))) {
					addArmorDamageReductionTooltip(event.getItemStack(), event.getEntityPlayer(), event.getToolTip(), event.getFlags().isAdvanced());
				}
			}
			if (ArmourCalculator.advancedDamageTypes && ArmourCalculator.getRatioForWeapon(event.getItemStack()) != null) {
				displayWeaponTraits(ArmourCalculator.getRatioForWeapon(event.getItemStack()), event.getToolTip());
			}
			if (ToolHelper.shouldShowTooltip(event.getItemStack())) {
				showCrafterTooltip(event.getItemStack(), event.getToolTip());
			}
			if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey(CRAFTED_BY_NAME_TAG)) {
				String name = event.getItemStack().getTagCompound().getString(CRAFTED_BY_NAME_TAG);
				boolean special = MineFantasyReforged.isNameModder(name);// Mod creators have highlights

				event.getToolTip().add((special ? TextFormatting.GREEN : "")
						+ I18n.format("attribute.mfcraftedbyname.name")
						+ ": " + name
						+ TextFormatting.GRAY);
			}
			WeaponClass WC = WeaponClass.findClassForAny(event.getItemStack());
			if (WC != null && RPGElements.isSystemActive && WC.parentSkill != Skill.NONE) {
				event.getToolTip().add(I18n.format("weaponclass." + WC.name.toLowerCase()));
				float skillMod = RPGElements.getWeaponModifier(event.getEntityPlayer(), WC.parentSkill) * 100F;
				if (skillMod > 100)
					event.getToolTip().add(I18n.format("rpg.skillmod") + ItemWeaponMFR.decimal_format.format(skillMod - 100) + "%");
			}
		}
	}

	@SubscribeEvent
	public static void specialInteractForComponentBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getEntityPlayer().isSneaking() && event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockComponent) {
			event.setUseBlock(Event.Result.ALLOW);
		}
	}

	@SubscribeEvent
	public static void tryDropItems(LivingDropsEvent event) {
		EntityLivingBase dropper = event.getEntityLiving();

		if (dropper instanceof EntityChicken) {
			int dropCount = 1 + random.nextInt(event.getLootingLevel() + 4);

			for (int a = 0; a < dropCount; a++) {
				dropper.entityDropItem(new ItemStack(Items.FEATHER), 0.0F);
			}
		}
		if (dropper.getEntityData().hasKey(Constants.DROP_LOOT_TAG)) {
			int id = dropper.getEntityData().getInteger(Constants.DROP_LOOT_TAG);
			Item drop = id == 0 ? MineFantasyItems.LOOT_SACK_COMMON : id == 1 ? MineFantasyItems.LOOT_SACK_VALUABLE : MineFantasyItems.LOOT_SACK_EXQUISITE;
			dropper.entityDropItem(new ItemStack(drop), 0.0F);
		}
		if (dropper instanceof EntityAgeable && dropper.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
			if (random.nextFloat() * (1 + event.getLootingLevel()) < 0.05F) {
				dropper.entityDropItem(new ItemStack(MineFantasyItems.GUTS), 0.0F);
			}
		}
		if (dropper instanceof IAnimals && !(dropper instanceof IMob)) {
			if (ConfigHardcore.hunterKnife && !dropper.getEntityData().hasKey(Constants.HUNTER_KILL_TAG)) {
				event.setCanceled(true);
				return;
			}
			if (ConfigHardcore.lessHunt) {
				alterDrops(dropper, event);
			}
		}
		if (getRegisterName(dropper).contains("Horse")) {
			int dropCount = random.nextInt(3 + event.getLootingLevel());
			if (ConfigHardcore.lessHunt) {
				dropCount = 1 + random.nextInt(event.getLootingLevel() + 1);
			}

			Item meat = dropper.isBurning() ? MineFantasyItems.HORSE_COOKED : MineFantasyItems.HORSE_RAW;
			for (int a = 0; a < dropCount; a++) {
				dropper.entityDropItem(new ItemStack(meat), 0.0F);
			}
		}
		if (getRegisterName(dropper).contains("Wolf")) {
			int dropCount = random.nextInt(3 + event.getLootingLevel());
			if (ConfigHardcore.lessHunt) {
				dropCount = 1 + random.nextInt(event.getLootingLevel() + 1);
			}

			Item meat = dropper.isBurning() ? MineFantasyItems.WOLF_COOKED : MineFantasyItems.WOLF_RAW;
			for (int a = 0; a < dropCount; a++) {
				dropper.entityDropItem(new ItemStack(meat), 0.0F);
			}
		}
		dropLeather(event.getEntityLiving(), event);

		if (dropper instanceof EntitySkeleton) {
			EntitySkeleton skeleton = (EntitySkeleton) dropper;

			if ((skeleton.getHeldItemMainhand().isEmpty() || !(skeleton.getHeldItemMainhand().getItem() instanceof ItemBow))
					&& event.getDrops() != null && !event.getDrops().isEmpty()) {

				for (EntityItem entItem : event.getDrops()) {
					ItemStack drop = entItem.getItem();

					if (drop.getItem() == Items.ARROW) {
						entItem.setDead();
					}
				}
			}
		}
	}

	public static String getRegisterName(Entity entity) {
		String s = EntityList.getEntityString(entity);

		if (s == null) {
			s = "generic";
		}

		return s;
	}

	private static void dropLeather(EntityLivingBase mob, LivingDropsEvent event) {
		boolean dropHide = shouldAnimalDropHide(mob);
		Item hide = getHideFor(mob);

		if (event.getDrops() != null && !event.getDrops().isEmpty()) {

			for (EntityItem entItem : event.getDrops()) {
				ItemStack drop = entItem.getItem();

				if (drop.getItem() == Items.LEATHER) {
					entItem.setDead();
					dropHide = true;
				}
			}
		}
		if (dropHide && hide != null && !(ConfigHardcore.hunterKnife && !mob.getEntityData().hasKey(Constants.HUNTER_KILL_TAG))) {
			mob.entityDropItem(new ItemStack(hide), 0.0F);
		}
	}

	private static Item getHideFor(EntityLivingBase mob) {
		Item[] hide = new Item[] {MineFantasyItems.RAWHIDE_SMALL, MineFantasyItems.RAWHIDE_MEDIUM, MineFantasyItems.RAWHIDE_LARGE};
		int size = getHideSizeFor(mob);
		if (mob.isChild()) {
			size--;
		}

		if (size <= 0) {
			return null;
		}
		if (size > hide.length) {
			size = hide.length;
		}

		return hide[size - 1];
	}

	private static int getHideSizeFor(EntityLivingBase mob) {
		String mobName = mob.getClass().getName();
		if (mobName.endsWith("EntityCow") || mobName.endsWith("EntityHorse")) {
			return 3;
		}
		if (mobName.endsWith("EntitySheep")) {
			return 2;
		}
		if (mobName.endsWith("EntityPig")) {
			return 1;
		}

		int size = (int) (mob.width + mob.height + 1);
		if (size <= 1) {
			return 0;
		}
		if (size == 2) {
			return 1;
		} else if (size <= 4) {
			return 2;
		}
		return 3;
	}

	private static boolean shouldAnimalDropHide(EntityLivingBase mob) {
		return mob instanceof EntityWolf || mob instanceof EntityCow || mob instanceof EntityPig || mob instanceof EntitySheep || mob instanceof EntityHorse;
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityDragon && event.getSource() != null
				&& event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
			PlayerTickHandler.addDragonKill((EntityPlayer) event.getSource().getTrueSource());
		}
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource() != null
				&& event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityDragon) {
			PlayerTickHandler.addDragonEnemyPts((EntityPlayer) event.getEntityLiving(), -1);
		}
		Entity dropper = event.getEntity();

		boolean useArrows = true;
		try {
			Class.forName("minefantasy.mf2.api.helpers.ArrowEffectsMF");
		}
		catch (Exception e) {
			useArrows = false;
		}
		if (dropper != null && useArrows && ConfigSpecials.stickArrows && !dropper.world.isRemote) {
			ArrayList<ItemStack> stuckArrows = (ArrayList<ItemStack>) ArrowEffectsMF.getStuckArrows(dropper);
			if (!stuckArrows.isEmpty()) {

				for (ItemStack arrow : stuckArrows) {
					if (!arrow.isEmpty()) {
						dropper.entityDropItem(arrow, 0.0F);
					}
				}
			}
		}

	}

	@SubscribeEvent
	public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if (event.getEntity().isDead) {
			return;
		}
		if (event.getEntity() instanceof EntityItem && !(event.getEntity() instanceof EntityItemUnbreakable)) {
			EntityItem entityItem = (EntityItem) event.getEntity();
			if (!entityItem.getItem().isEmpty()) {
				//noinspection ConstantConditions
				if (entityItem.getItem().hasTagCompound() && entityItem.getItem().getTagCompound().hasKey(Constants.UNBREAKABLE_TAG)) {
					EntityItem newEntity = new EntityItemUnbreakable(event.getWorld(), entityItem);
					event.getWorld().spawnEntity(newEntity);
					entityItem.setDead();
					event.setCanceled(true);
				}
				if (entityItem.getItem().getItem() == MineFantasyItems.DRAGON_HEART) {
					MFRLogUtil.logDebug("Found dragon heart");
					EntityItem newEntity = new EntityItemUnbreakable(event.getWorld(), entityItem);
					event.getWorld().spawnEntity(newEntity);
					entityItem.setDead();
					event.setCanceled(true);
				}
			}
		}
	}


	public static void alterDrops(EntityLivingBase dropper, LivingDropsEvent event) {
		ArrayList<ItemStack> meats = new ArrayList<ItemStack>();

		if (event.getDrops() != null && !event.getDrops().isEmpty()) {

			for (EntityItem entItem : event.getDrops()) {
				ItemStack drop = entItem.getItem();
				boolean dropItem = true;

				if (drop.getItem() instanceof ItemFood) {
					entItem.setDead();

					if (!meats.isEmpty()) {
						for (ItemStack compare : meats) {
							if (drop.isItemEqual(compare)) {
								dropItem = false;
								break;
							}
						}
					}
					if (dropItem) {
						drop.setCount(1);
						if (event.getLootingLevel() > 0) {
							drop.setCount(dropper.getRNG().nextInt(event.getLootingLevel() + 1));
						}
						meats.add(drop.copy());
					}
				}
			}

			for (ItemStack meat : meats) {
				dropper.entityDropItem(meat, 0.0F);
			}
		}
	}


	@SubscribeEvent
	public static void useHoe(UseHoeEvent event) {
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		if (block != Blocks.FARMLAND
				&& FarmingHelper.didHoeFail(event.getCurrent(), event.getWorld(), block == Blocks.GRASS)) {
			EntityPlayer player = event.getEntityPlayer();
			player.swingArm(player.isHandActive() ? player.getActiveHand() : (player.getHeldItemMainhand() == event.getCurrent() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND));
			event.getWorld().playSound(player, event.getPos(), SoundEvents.ITEM_HOE_TILL, SoundCategory.AMBIENT, 12, 1F);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void breakBlock(BlockEvent.BreakEvent event) {
		// Block block = event.block;
		Block base = event.getWorld().getBlockState(event.getPos().down()).getBlock();
		// int meta = event.blockMetadata;

		if (base != null && base == Blocks.FARMLAND && FarmingHelper.didHarvestRuinBlock(event.getWorld(), false)) {
			event.getWorld().setBlockState(event.getPos().add(0, -1, 0), Blocks.DIRT.getDefaultState());
		}

		EntityPlayer player = event.getPlayer();
		if (player != null && !player.capabilities.isCreativeMode && !(player instanceof FakePlayer)) {
			playerMineBlock(event);
		}
	}

	public static void playerMineBlock(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		ItemStack held = player.getHeldItemMainhand();
		IBlockState broken = event.getState();
		if (broken != null){
			if (ConfigHardcore.HCCallowRocks) {
				if (held.isEmpty() && CustomStone.isStone(broken.getBlock())) {
					entityDropItem(event.getWorld(), event.getPos(),
							new ItemStack(MineFantasyItems.SHARP_ROCK, random.nextInt(3) + 1));
				}
				if (!held.isEmpty() && held.getItem() == MineFantasyItems.SHARP_ROCK && broken.getBlock() instanceof BlockLeaves) {
					if (random.nextInt(5) == 0) {
						entityDropItem(event.getWorld(), event.getPos(), new ItemStack(Items.STICK, random.nextInt(3) + 1));
					}
					if (random.nextInt(3) == 0) {
						entityDropItem(event.getWorld(), event.getPos(), new ItemStack(MineFantasyItems.VINE, random.nextInt(3) + 1));
					}
				}
			}

			if (StaminaBar.isSystemActive && ConfigStamina.affectMining && StaminaBar.doesAffectEntity(player) && !isBlockPlant(broken.getBlock()) && !(broken == (Blocks.SNOW_LAYER).getDefaultState())) {
				float points = 2.0F * ConfigStamina.miningSpeed;
				ItemWeaponMFR.applyFatigue(player, points, 20F);

				if (points > 0 && !StaminaBar.isAnyStamina(player, false)) {
					player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 1));
				}
			}
		}
	}

	public static boolean isBlockPlant(Block block){
		return block instanceof IPlantable && !(block instanceof BlockGrass);
	}

	public static EntityItem entityDropItem(World world, BlockPos pos, ItemStack item) {
		if (item.getCount() != 0 && !item.isEmpty()) {
			EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, item);
			entityitem.setPickupDelay(10);
			world.spawnEntity(entityitem);
			return entityitem;
		}
		return null;
	}


	private static void displayWeaponTraits(float[] ratio, List<String> list) {
		int cutting = (int) (ratio[0] / (ratio[0] + ratio[1] + ratio[2]) * 100F);
		int piercing = (int) (ratio[2] / (ratio[0] + ratio[1] + ratio[2]) * 100F);
		int blunt = (int) (ratio[1] / (ratio[0] + ratio[1] + ratio[2]) * 100F);

		addDamageType(list, cutting, "cutting");
		addDamageType(list, piercing, "piercing");
		addDamageType(list, blunt, "blunt");
	}

	private static void addDamageType(List<String> list, int value, String name) {
		if (value > 0) {
			String s = I18n.format("attribute.weapon." + name);
			if (value < 100) {
				s += " " + value + "%";
			}
			list.add(s);
		}
	}

	@SubscribeEvent
	public static void updateEntity(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityCogwork) {
			return;
		}
		EntityLivingBase entity = event.getEntityLiving();

		int injury = getInjuredTime(entity);

		if (ConfigHardcore.critLimp && entity.ticksExisted - entity.getLastAttackedEntityTime() > 200 && (entity instanceof EntityLiving || !(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode))) {
			float lowHp = entity.getMaxHealth() / 5F;

			if (entity.getHealth() <= lowHp || injury > 0) {
				if (entity.getRNG().nextInt(10) == 0 && entity.onGround && !entity.isRiding()) {
					entity.motionX = 0F;
					entity.motionZ = 0F;
				}
				float x = (float) (entity.posX + (random.nextFloat() - 0.5F) / 4F);
				float y = (float) (entity.posY + entity.getEyeHeight() + (random.nextFloat() - 0.5F) / 4F);
				float z = (float) (entity.posZ + (random.nextFloat() - 0.5F) / 4F);
				entity.world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0F, 0F, 0F);
			}
			if (!entity.world.isRemote && entity.getHealth() <= (lowHp / 2) && entity.getRNG().nextInt(200) == 0) {
				entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 50));
			}
		}
		if (injury > 0 && !entity.world.isRemote) {
			injury--;
			entity.getEntityData().setInteger(Constants.INJURED_TAG, injury);
		}
		if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(entity) && event.getEntityLiving() instanceof EntityPlayer) {
			StaminaMechanics.tickEntity((EntityPlayer) event.getEntityLiving());
		}

	}

	public static int getInjuredTime(Entity entity) {
		if (entity.getEntityData().hasKey(Constants.INJURED_TAG)) {
			return entity.getEntityData().getInteger(Constants.INJURED_TAG);
		}
		return 0;
	}

	@SubscribeEvent
	public static void startUseItem(LivingEntityUseItemEvent.Start event) {
		EntityLivingBase player = event.getEntityLiving();
		if (!event.getItem().isEmpty() && event.getItem().getItemUseAction() == EnumAction.valueOf("mfr_block")) {
			if ((StaminaBar.isSystemActive && TacticalManager.shouldStaminaBlock && !StaminaBar.isAnyStamina(player, false)) || !CombatMechanics.isParryAvailable(player)) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void itemEvent(EntityItemPickupEvent event) {
		EntityPlayer player = event.getEntityPlayer();

		EntityItem drop = event.getItem();
		ItemStack item = drop.getItem();
		ItemStack held = player.getHeldItemMainhand();

		if (!held.isEmpty() && held.getItem() instanceof ISmithTongs) {
			if (!TongsHelper.hasHeldItem(held)) {
				if (isHotItem(item)) {
					if (TongsHelper.trySetHeldItem(held, item)) {
						drop.setDead();

						if (event.isCancelable()) {
							event.setCanceled(true);
						}
						return;
					}
				}
			}
		}
		{
			if (ConfigHardcore.HCChotBurn && !item.isEmpty() && isHotItem(item)) {
				if (event.isCancelable()) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onContainerClosed(PlayerContainerEvent.Close event){
		EntityPlayer player = event.getEntityPlayer();
		Container container = event.getContainer();
		if (!(container instanceof ContainerAnvil || container instanceof ContainerForge || container instanceof ContainerPlayer)){
			for (Slot slot : container.inventorySlots){
				int slotIndex = slot.slotNumber;
				if (slotIndex < (container.inventorySlots.size() - player.inventory.mainInventory.size())){
					ItemStack stack = slot.getStack().copy();
					if (stack.getItem() instanceof IHotItem){

						player.dropItem(stack, false);
						container.putStackInSlot(slotIndex, ItemStack.EMPTY);
						container.detectAndSendChanges();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void wakeUp(PlayerWakeUpEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player.isPlayerFullyAsleep()){
			PlayerTickHandler.wakeUp(event.getEntityPlayer());
		}
	}

	private static boolean isHotItem(ItemStack item) {
		return !item.isEmpty() && (item.getItem() instanceof IHotItem);
	}

	@SubscribeEvent
	public static void levelup(LevelUpEvent event) {
		EntityPlayer player = event.thePlayer;
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.sendToPlayer((EntityPlayerMP) player, new LevelUpPacket(player, event.theSkill, event.theLevel));
			PlayerData.get(player).sync();
		}
	}
}
