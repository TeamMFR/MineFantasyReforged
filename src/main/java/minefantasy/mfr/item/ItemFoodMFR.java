package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.client.ClientItemsMFR;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.StaminaBar;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.List;

public class ItemFoodMFR extends ItemFood implements IClientRegister {
	public static float SAT_MODIFIER = 1.0F;
	public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
	private static final String eatDelayNBT = "MF_EatenFoodDelay";
	private static final String leftOverNbt = "MF_Food_leftover";
	public int itemRarity;
	protected int hungerLevel;
	protected float saturationLevel;
	private float staminaRestore = 0F;
	private boolean hasEffect = false;
	private float staminaBuff = 0F;
	private int staminaSeconds = 0;
	private boolean staminaInMinutes = false;
	private boolean staminaInHours = false;
	private float staminaRegenBuff = 0F;
	private int staminaRegenSeconds = 0;
	private boolean staminaRegenInMinutes = false;
	private int useTime = 32;

	public ItemFoodMFR(String name, int hunger, float saturation, boolean isMeat) {
		super(hunger, saturation, isMeat);

		hungerLevel = hunger;
		saturationLevel = saturation;
		setRegistryName(name);
		setUnlocalizedName(name);

		setCreativeTab(MineFantasyTabs.tabFood);

		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	public ItemFoodMFR(String name, int hunger, float saturation, boolean isMeat, int rarity) {
		this(name, hunger, saturation, isMeat);
		itemRarity = rarity;
	}

	public static void setLeftOver(ItemStack food, ItemStack leftover) {
		if (!food.hasTagCompound()) {
			food.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound savedItem = new NBTTagCompound();
		leftover.writeToNBT(savedItem);
		food.getTagCompound().setTag(leftOverNbt, savedItem);
	}

	public static void onTick(EntityPlayer player) {
		int time = getEatDelay(player);
		if (time > 0) {
			time--;
			setEatDelay(player, time);
		}
	}

	private static void setEatDelay(EntityPlayer player, int time) {
		time += getEatDelay(player);// add to existing

		player.getEntityData().setInteger(eatDelayNBT, time);
	}

	private static int getEatDelay(EntityPlayer player) {
		if (player.getEntityData().hasKey(eatDelayNBT)) {
			return player.getEntityData().getInteger(eatDelayNBT);
		}
		return 0;
	}

	protected void onMFFoodEaten(ItemStack food, World world, EntityPlayer consumer) {
		if (StaminaBar.isSystemActive) {
			if (staminaRestore > 0) {
				StaminaBar.modifyStaminaValue(consumer, staminaRestore);
			}
			if (staminaSeconds > 0) {
				StaminaBar.buffStamina(consumer, staminaBuff, staminaSeconds);
			}
			if (staminaRegenSeconds > 0) {
				StaminaBar.buffStaminaRegen(consumer, staminaRegenBuff, staminaRegenSeconds);
			}
		}

		if (this == MineFantasyItems.BERRIES_JUICY) {
			PotionEffect poison = consumer.getActivePotionEffect(MobEffects.POISON);
			if (poison != null) {
				poison.addCurativeItem(food);
				consumer.curePotionEffects(food);
			}
		}
	}

	public ItemFoodMFR setRarity(int i) {
		itemRarity = i;
		return this;
	}

	public ItemFoodMFR setStaminaRestore(float f) {
		staminaRestore = f;
		hasEffect = true;
		return this;
	}

	/**
	 * Grade Sugar, Carbs and Fats from 0-1 and tier does the rest 0 means
	 * non-existent
	 *
	 * @param sugar restore stamina and add regen
	 * @param carbs increase max stamina for 1 hr
	 * @param fats  saturation
	 */
	public ItemFoodMFR setFoodStats(int tier, float sugar, float carbs, float fats) {
		return setFoodStats(sugar * tier, carbs * tier, fats * tier);
	}

	/**
	 * New system for setting food vales: Values should be based on tier value (t1,
	 * t2, t3,...,tx) 0 means non-existent
	 *
	 * @param sugar restore stamina and add regen
	 * @param carbs increase max stamina for 1 hr
	 * @param fats  saturation
	 */
	public ItemFoodMFR setFoodStats(float sugar, float carbs, float fats) {
		if (sugar > 0) {
			setStaminaRestore((int) (sugar * 50));
			setStaminaRegenModifier((int) sugar, sugar / 30);
		}
		if (carbs > 0) {
			setStaminaModifier(50 * carbs, 1F);
		}
		if (fats > 0) {
			setSaturation(10 * fats);
		}
		return this;
	}

	public ItemFoodMFR setSaturation(float amount) {
		hasEffect = true;
		this.saturationLevel = amount * SAT_MODIFIER;
		return this;
	}

	public ItemFoodMFR setStaminaModifier(float buff, float hours) {
		int secondsLasting = (int) (hours * 3600F);
		staminaBuff = buff;
		staminaSeconds = secondsLasting;
		staminaInMinutes = secondsLasting > 60;
		staminaInHours = secondsLasting > 3600;
		hasEffect = true;
		return this;
	}

	public ItemFoodMFR setStaminaRegenModifier(float buff, float minutesLasting) {
		int secondsLasting = (int) (minutesLasting * 60F);
		staminaRegenBuff = buff;
		staminaRegenSeconds = secondsLasting;
		staminaRegenInMinutes = secondsLasting > 60;
		hasEffect = true;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack food, World world, List<String> list, ITooltipFlag flag) {
		super.addInformation(food, world, list, flag);
		list.add(I18n.format("food.stat.hunger.name", hungerLevel));

		if (hasEffect && ClientItemsMFR.showSpecials(food, world, list, flag)) {
			list.add("");
			list.add(TextFormatting.WHITE + I18n.format("food.stat.list.name"));
			if (saturationLevel > 0) {
				list.add(I18n.format("food.stat.saturation.name",
						decimal_format.format(saturationLevel)));
			}
			if (staminaRestore > 0) {
				list.add(I18n.format("food.stat.staminaPlus.name", (int) staminaRestore));
			}
			if (staminaBuff > 0) {
				if (staminaInHours) {
					list.add(I18n.format("food.stat.staminabuffHours.name",
							decimal_format.format(staminaBuff), decimal_format.format(staminaSeconds / 3600F)));
				} else if (staminaInMinutes) {
					list.add(I18n.format("food.stat.staminabuffMinutes.name",
							decimal_format.format(staminaBuff), decimal_format.format(staminaSeconds / 60F)));
				} else {
					list.add(I18n.format("food.stat.staminabuffSeconds.name",
							decimal_format.format(staminaBuff), decimal_format.format(staminaSeconds)));
				}
			}
			if (staminaRegenBuff > 0) {
				if (staminaRegenInMinutes) {
					list.add(I18n.format("food.stat.staminabuffRegenMinutes.name",
							decimal_format.format(staminaRegenBuff), decimal_format.format(staminaRegenSeconds / 60F)));
				} else {
					list.add(I18n.format("food.stat.staminabuffRegenSeconds.name",
							decimal_format.format(staminaRegenBuff), decimal_format.format(staminaRegenSeconds)));
				}
			}
		}
		if (this == MineFantasyItems.BERRIES_JUICY) {
			list.add(I18n.format("food.stat.cure.poison"));
		}
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
		if (lvl >= MineFantasyItems.RARITY.length) {
			lvl = MineFantasyItems.RARITY.length - 1;
		}
		return MineFantasyItems.RARITY[lvl];
	}

	public ItemFoodMFR setReturnItem(Item item) {
		return this;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack food, World world, EntityLivingBase consumer) {
		if (consumer instanceof EntityPlayer) {
			setEatDelay((EntityPlayer) consumer, 10);
			((EntityPlayer) consumer).getFoodStats().addStats(this, food);
			world.playSound((EntityPlayer) consumer, consumer.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.AMBIENT, 1.0F, 1.0F);
			this.onMFFoodEaten(food, world, (EntityPlayer) consumer);

			if (this.isDamageable() && consumer instanceof EntityPlayerMP) {
				if (food.attemptDamageItem(1, consumer.getRNG(), (EntityPlayerMP) consumer)) {
					food.shrink(1);
					if (food.getCount() < 0) {
						food.setCount(0);
					}
				}
			} else {
				food.shrink(1);
			}
		}

		return food;
	}

	//
	//    @Override
	//    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
	//        if (!isInCreativeTab(tab)) {
	//            rNeturn;
	////        }
	////        //ItemStack food = items.get(0);
	////        // TODO: this should be in its own childclass
	////        //        if (this.getUnlocalizedame().contains("stew")) {
	//        //            setLeftOver(food, new ItemStack(Items.BOWL));
	//        //        }
	//        //        items.add(food);
	//    }

	public ItemFoodMFR setEatTime(int i) {
		useTime = i;
		return this;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return useTime;
	}

	public Object onItemRightClick(ItemStack food, World world, EntityPlayer user, EnumHand hand) {
		if (getEatDelay(user) > 0) {
			return food;
		}
		return super.onItemRightClick(world, user, hand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}
}
