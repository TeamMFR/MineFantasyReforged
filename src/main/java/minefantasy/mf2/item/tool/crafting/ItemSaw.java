package minefantasy.mf2.item.tool.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.config.ConfigTools;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.tool.ToolMaterialMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Anonymous Productions
 */
public class ItemSaw extends ItemAxe implements IToolMaterial, IDamageType, IToolMF
{
	private Random rand = new Random();
	private float hitDamage;
	private float baseDamage;
	private String name;
	private int tier;
	/**
	 */
    public ItemSaw(String name, ToolMaterial material, int rarity, int tier)
    {
        super(material);
        this.tier=tier;
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabOldTools);
        this.hitDamage = (2.0F + material.getDamageVsEntity())/2F;
        setTextureName("minefantasy2:Tool/Crafting/"+name);
		GameRegistry.registerItem(this, name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
		this.name = name;
		this.setHarvestLevel("axe", material.getHarvestLevel());
    }
    
    @Override
	public ToolMaterial getMaterial()
	{
		return toolMaterial;
	}

    @Override
	public boolean onBlockDestroyed(ItemStack item, World world, Block block, int x, int y, int z, EntityLivingBase user)
	{
		if(user instanceof EntityPlayer && canAcceptCost(user))
		{
			breakChain(world, x, y, z, item, block, user, 32, block, world.getBlockMetadata(x, y, z));
		}
		return super.onBlockDestroyed(item, world, block, x, y, z, user);
	}
	
	private void breakChain(World world, int x, int y, int z, ItemStack item, Block block, EntityLivingBase user, int maxLogs, Block orient, int orientM) 
	{
		if(maxLogs > 0 && isLog(world, x, y, z, orient, orientM))
		{
			Block newblock = world.getBlock(x, y, z);
			breakSurrounding(item, world, newblock, x, y, z, user);
			newblock.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), EnchantmentHelper.getFortuneModifier(user));
			world.setBlockToAir(x, y, z);
			item.damageItem(1, user);
			
			maxLogs --;
			for(int x1 = -1; x1 <= 1; x1 ++)
			{
				for(int y1 = -1; y1 <= 1; y1 ++)
				{
					for(int z1 = -1; z1 <= 1; z1 ++)
					{
						breakChain(world, x+x1, y+y1, z+z1, item, newblock, user, maxLogs, orient, orientM);
					}
				}
			}
			if(user instanceof EntityPlayer)
			{
				tirePlayer(user, 2.0F);
			}
		}
	}

	public static boolean canAcceptCost(EntityLivingBase user)
	{
		return canAcceptCost(user, 0.1F);
	}
	public static boolean canAcceptCost(EntityLivingBase user, float cost)
	{
		if(user instanceof EntityPlayer && StaminaBar.isSystemActive)
		{
			return StaminaBar.isPercentStamAvailable(user, cost, true);
		}
		return true;
	}
	
	public static void tirePlayer(EntityLivingBase user, float points)
	{
		if(user instanceof EntityPlayer && StaminaBar.isSystemActive)
		{
			StaminaBar.modifyStaminaValue(user, -StaminaBar.getBaseDecayModifier(user, true, true)*points);
			StaminaBar.ModifyIdleTime(user, 5F*points);
		}
	}

	private boolean isLog(World world, int x, int y, int z, Block orient, int orientM) 
	{
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(block != null)
		{
			return block == orient && block.getMaterial() == Material.wood;
		}
		return false;
	}

	public void breakSurrounding(ItemStack item, World world, Block block, int x, int y, int z, EntityLivingBase user)
	{
		if(!world.isRemote && ForgeHooks.isToolEffective(item, block, world.getBlockMetadata(x, y, z)))
		{
			for(int x1 = -2; x1 <= 2; x1 ++)
			{
				for(int y1 = -2; y1 <= 2; y1 ++)
				{
					for(int z1 = -2; z1 <= 2; z1 ++)
					{
						ForgeDirection FD = getFDFor(user);
						int blockX = x+x1 + FD.offsetX;
						int blockY = y+y1 + FD.offsetY;
						int blockZ = z+z1 + FD.offsetZ;
						
						if(!(x1+FD.offsetX == 0 && y1+FD.offsetY == 0 &&  z1+FD.offsetZ == 0))
						{
							Block newblock = world.getBlock(blockX, blockY, blockZ);
							int m = world.getBlockMetadata(blockX, blockY, blockZ);
							
							if(item.getItemDamage() < item.getMaxDamage() && newblock != null && user instanceof EntityPlayer && newblock.getMaterial() == Material.leaves)
							{
								if(rand.nextFloat()*100F < (100F - ConfigTools.hvyDropChance))
								{
									newblock.dropBlockAsItem(world, blockX, blockY, blockZ, m, EnchantmentHelper.getFortuneModifier(user));
								}
								world.setBlockToAir(blockX, blockY, blockZ);
								item.damageItem(1, user);
							}
						}
					}	
				}	
			}
		}
	}

	private ForgeDirection getFDFor(EntityLivingBase user) 
	{
		return ForgeDirection.UNKNOWN;//TODO: FD
	}
	
	@Override
	public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.hitDamage, 0));
        return multimap;
    }
	
	@Override
	public float[] getDamageRatio(Object... implement) 
	{
		return new float[]{1,0, 0};
	}

	@Override
	public String getToolType(ItemStack item)
	{
		return "saw";
	}
	@Override
	public float getPenetrationLevel(Object implement)
	{
		return 0.5F;
	}
	
	//===================================================== CUSTOM START =============================================================\\
	private boolean isCustom = false;
	public ItemSaw setCustom(String s)
	{
		canRepair = false;
		setTextureName("minefantasy2:custom/tool/"+s+"/"+name);
		isCustom = true;
		return this;
	}
	public ItemSaw setBaseDamage(float baseDamage)
    {
    	this.baseDamage = baseDamage;
    	return this;
    }
	
	@Override
	public float getEfficiency(ItemStack item) 
	{
		return CustomToolHelper.getEfficiency(item, toolMaterial.getEfficiencyOnProperMaterial(), efficiencyMod);
	}
	@Override
	public int getTier(ItemStack item) 
	{
		return CustomToolHelper.getCrafterTier(item, tier);
	}
	
    private float efficiencyMod = 1.0F;
    public ItemSaw setEfficiencyMod(float efficiencyMod)
    {
    	this.efficiencyMod = efficiencyMod;
    	return this;
    }
    
	@Override
	public Multimap getAttributeModifiers(ItemStack item)
	{
		Multimap map = HashMultimap.create();
		map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", getMeleeDamage(item), 0));
	
	    return map;
	}
	/**
	 * Gets a stack-sensitive value for the melee dmg
	 */
    protected float getMeleeDamage(ItemStack item) 
    {
    	return baseDamage + CustomToolHelper.getMeleeDamage(item, toolMaterial.getDamageVsEntity());
	}
    protected float getWeightModifier(ItemStack stack)
	{
    	return CustomToolHelper.getWeightModifier(stack, 1.0F);
	}
    private IIcon detailTex = null;
	private IIcon haftTex = null;
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
    	if(isCustom)
    	{
    		haftTex = reg.registerIcon(this.getIconString()+"_haft");
    		detailTex = reg.registerIcon(this.getIconString()+"_detail");
    		
    	}
    	super.registerIcons(reg);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return isCustom;
    }
  //Returns the number of render passes this item has.
    @Override
    public int getRenderPasses(int metadata)
    {
        return 3;
    }
    
    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
    	if(isCustom && pass == 1 && haftTex != null)
    	{
    		return haftTex; 
    	}
    	if(isCustom && pass == 2 && detailTex != null)
    	{
    		return detailTex;
    	}
        return super.getIcon(stack, pass);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer)
    {
    	return CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));
    }
    @Override
	public int getMaxDamage(ItemStack stack)
	{
		return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
	}

    public ItemStack construct(String main, String haft)
	{
		return CustomToolHelper.construct(this, main, haft);
	}
	protected int itemRarity;
    @Override
	public EnumRarity getRarity(ItemStack item)
	{
    	return CustomToolHelper.getRarity(item, itemRarity);
	}
    @Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
    	if (!ForgeHooks.isToolEffective(stack, block, meta))
        {
    		return this.func_150893_a(stack, block);
        }
		return CustomToolHelper.getEfficiency(stack, super.getDigSpeed(stack, block, meta), efficiencyMod);
	}
    public float func_150893_a(ItemStack stack, Block block)
    {
    	float base = super.func_150893_a(stack, block);
        return base <= 1.0F ? base : CustomToolHelper.getEfficiency(stack, this.efficiencyOnProperMaterial, efficiencyMod);
    }
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
    	return CustomToolHelper.getHarvestLevel(stack, super.getHarvestLevel(stack, toolClass));
    }
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	if(isCustom)
    	{
    		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
    		Iterator iteratorMetal = metal.iterator();
    		while(iteratorMetal.hasNext())
        	{
    			CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
    			if(MineFantasyII.isDebug() || customMat.getItem() != null)
    			{
    				list.add(this.construct(customMat.name,"OakWood"));
    			}
        	}
    	}
    	else
    	{
    		super.getSubItems(item, tab, list);
    	}
    }
    
    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) 
    {
    	if(isCustom)
        {
        	CustomToolHelper.addInformation(item, list);
        }
        super.addInformation(item, user, list, extra);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item)
    {
    	String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
    	return CustomToolHelper.getLocalisedName(item, unlocalName);
    }
    //====================================================== CUSTOM END ==============================================================\\
}
