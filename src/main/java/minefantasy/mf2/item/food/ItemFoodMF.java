package minefantasy.mf2.item.food;

import java.text.DecimalFormat;
import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.hunger.HungerSystemMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.mechanics.TierHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemFoodMF extends ItemFood
{
	private static final String	eatDelayNBT = "MF_EatenFoodDelay";
	private float staminaRestore = 0F;
	private Item leftOver;
	private float mfSaturation = 0F;
	protected int hungerLevel;
	protected float saturationLevel;
	
	public ItemFoodMF(String name, int hunger, float saturation, boolean isMeat)
	{
		super(hunger, 0F, isMeat);
		
		hungerLevel = hunger;
		saturationLevel = saturation;
		setCreativeTab(CreativeTabMF.tabFood);
		GameRegistry.registerItem(this, "MF2_food_"+name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
		this.setTextureName("minefantasy2:food/"+name);
	}
	
	public ItemFoodMF(String name, int hunger, float saturation, float saturation2, boolean isMeat, int rarity)
	{
		this(name, hunger, saturation, isMeat);
		this.mfSaturation = saturation2 * FoodListMF.satModifier;
		itemRarity = rarity;
	}
	
	@Override
	protected void onFoodEaten(ItemStack food, World world, EntityPlayer consumer)
    {
		super.onFoodEaten(food, world, consumer);
		
		if(StaminaBar.isSystemActive)
		{
			if(staminaRestore > 0)
	        {
	        	StaminaBar.modifyStaminaValue(consumer, staminaRestore);
	        }
			if(staminaSeconds > 0)
			{
				StaminaBar.buffStamina(consumer, staminaBuff, staminaSeconds);
			}
			if(staminaRegenSeconds > 0)
			{
				StaminaBar.buffStaminaRegen(consumer, staminaRegenBuff, staminaRegenSeconds);
			}
		}
		
		if(mfSaturation > 0 && consumer.getFoodStats().getFoodLevel() >= 20)//Only apply saturation at full food level
		{
			HungerSystemMF.applySaturation(consumer, mfSaturation);
		}
		
		if(this == FoodListMF.berriesJuicy)
		{
			PotionEffect poison = consumer.getActivePotionEffect(Potion.poison);
			if(poison != null)
			{
				poison.addCurativeItem(food);
				consumer.curePotionEffects(food);
			}
		}
    }
	public ItemFoodMF setRarity(int i)
	{
		itemRarity = i;
		return this;
	}
	public ItemFoodMF setStaminaRestore(float f)
	{
		staminaRestore = f;
		hasEffect = true;
		return this;
	}
	public ItemFoodMF setStaminaModifier(float buff, float hours)
	{
		int secondsLasting = (int) (hours*3600F);
		staminaBuff = buff;
		staminaSeconds = secondsLasting;
		staminaInMinutes = secondsLasting > 60;
		staminaInHours = secondsLasting > 3600;
		hasEffect = true;
		return this;
	}
	public ItemFoodMF setStaminaRegenModifier(float buff, float minutesLasting)
	{
		int secondsLasting = (int) (minutesLasting*60F);
		staminaRegenBuff = buff;
		staminaRegenSeconds = secondsLasting;
		staminaRegenInMinutes = secondsLasting > 60;
		hasEffect = true;
		return this;
	}
	public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
	@Override
    public void addInformation(ItemStack weapon, EntityPlayer user, List list, boolean extra) 
    {
        super.addInformation(weapon, user, list, extra);
        if(MineFantasyII.isDebug())
        {
        	int pts = TierHelper.getPointsWorthForFood(hungerLevel, mfSaturation, staminaBuff, staminaSeconds, staminaRegenBuff, staminaRegenSeconds);
        	list.add("Points: " + pts);
        	list.add("Recommended Tier: " + (pts <= 10 ? 0 : (int)Math.ceil(pts/50F)));
        	int lvl =(Math.max(0, (Math.round(pts/5F))-10));
        	list.add("Level: " + lvl);
        }
        list.add(StatCollector.translateToLocalFormatted("food.stat.hunger.name", hungerLevel));
        if(mfSaturation > 0)
    	{
			list.add(StatCollector.translateToLocalFormatted("food.stat.saturation.name", decimal_format.format(mfSaturation)));
    	}
        
        if(hasEffect)
        {
        	list.add("");
        	list.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("food.stat.list.name"));
        	if(staminaRestore > 0)
        	{
        		list.add(StatCollector.translateToLocalFormatted("food.stat.staminaPlus.name", (int)staminaRestore));
        	}
        	if(staminaBuff > 0)
        	{
        		if(staminaInHours)
        		{
        			list.add(StatCollector.translateToLocalFormatted("food.stat.staminabuffHours.name", decimal_format.format(staminaBuff), decimal_format.format(staminaSeconds/3600F)));
        		}
        		else if(staminaInMinutes)
        		{
        			list.add(StatCollector.translateToLocalFormatted("food.stat.staminabuffMinutes.name", decimal_format.format(staminaBuff), decimal_format.format(staminaSeconds/60F)));
        		}
        		else
        		{
        			list.add(StatCollector.translateToLocalFormatted("food.stat.staminabuffSeconds.name", decimal_format.format(staminaBuff), decimal_format.format(staminaSeconds)));
        		}
        	}
        	if(staminaRegenBuff > 0)
        	{
        		if(staminaRegenInMinutes)
        		{
        			list.add(StatCollector.translateToLocalFormatted("food.stat.staminabuffRegenMinutes.name", decimal_format.format(staminaRegenBuff), decimal_format.format(staminaRegenSeconds/60F)));
        		}
        		else
        		{
        			list.add(StatCollector.translateToLocalFormatted("food.stat.staminabuffRegenSeconds.name", decimal_format.format(staminaRegenBuff), decimal_format.format(staminaRegenSeconds)));
        		}
        	}
        }
        if(this == FoodListMF.berriesJuicy)
        {
        	list.add(StatCollector.translateToLocal("food.stat.cure.poison"));
        }
    }
	
	public int itemRarity;
    @Override
	public EnumRarity getRarity(ItemStack item)
	{
		int lvl = itemRarity+1;
		
		if(item.isItemEnchanted())
		{
			if(lvl == 0)
			{
				lvl++;
			}
			lvl ++;
		}
		if(lvl >= ToolListMF.rarity.length)
		{
			lvl = ToolListMF.rarity.length-1;
		}
		return ToolListMF.rarity[lvl];
	}
	
	private boolean hasEffect = false;
	private float staminaBuff = 0F;
	private int staminaSeconds = 0;
	private boolean staminaInMinutes = false;
	private boolean staminaInHours = false;
	
	private float staminaRegenBuff = 0F;
	private int staminaRegenSeconds = 0;
	private boolean staminaRegenInMinutes = false;
	
	private static final String leftOverNbt = "MF_Food_leftover";

	public ItemFoodMF setReturnItem(Item item)
	{
		leftOver = item;
		return this;
	}
	@Override
	public ItemStack onEaten(ItemStack food, World world, EntityPlayer consumer)
    {
		ItemStack left = getLeftOver(food);
        if(left != null)
        {
        	if(food.stackSize == 1)
        	{
        		consumer.getFoodStats().func_151686_a(this, food);
                world.playSoundAtEntity(consumer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                this.onFoodEaten(food, world, consumer);
        		return left;
        	}
    		if(!consumer.inventory.addItemStackToInventory(left) && !consumer.worldObj.isRemote)
    		{
    			consumer.entityDropItem(left, 1.0F);
    		}
        }
        setEatDelay(consumer, 10);
        return super.onEaten(food, world, consumer);
    }
	
	public static void setLeftOver(ItemStack food, ItemStack leftover)
	{
		if(!food.hasTagCompound())
		{
			food.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound savedItem = new NBTTagCompound();
		leftover.writeToNBT(savedItem);
		food.getTagCompound().setTag(leftOverNbt, savedItem);
	}

	protected ItemStack getLeftOver(ItemStack food)
	{
		if(food.hasTagCompound() && food.getTagCompound().hasKey(leftOverNbt))
		{
			return ItemStack.loadItemStackFromNBT(food.getTagCompound().getCompoundTag(leftOverNbt));
		}
		if(leftOver != null)
		{
			return new ItemStack(leftOver);
		}
		return getContainerItem(food);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		ItemStack food = new ItemStack(item);
		if(this.getUnlocalizedName().contains("stew"))
		{
			setLeftOver(food, new ItemStack(Items.bowl));
		}
		list.add(food);
	}
	private int useTime = 32;
	
	public ItemFoodMF setEatTime(int i)
	{
		useTime = i;
		return this;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack item)
    {
        return useTime;
    }

	public static void onTick(EntityPlayer player)
	{
		int time = getEatDelay(player);
		if(time > 0)
		{
			time --;
			setEatDelay(player, time);
		}
	}

	private static void setEatDelay(EntityPlayer player, int time)
	{
		player.getEntityData().setInteger(eatDelayNBT , time);
	}
	private static int getEatDelay(EntityPlayer player)
	{
		if(player.getEntityData().hasKey(eatDelayNBT))
		{
			return player.getEntityData().getInteger(eatDelayNBT);
		}
		return 0;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack food, World world, EntityPlayer user)
    {
		if(getEatDelay(user) > 0)
		{
			return food;
		}
        return super.onItemRightClick(food, world, user);
    }
}
