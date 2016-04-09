package minefantasy.mf2.item.armour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.CogworkArmour;
import minefantasy.mf2.api.armour.ICogworkArmour;
import minefantasy.mf2.api.armour.IGasProtector;
import minefantasy.mf2.api.crafting.ISpecialSalvage;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.material.MetalMaterial;
import minefantasy.mf2.util.MFLogUtil;

public class ItemCogworkArmour extends ItemArmourMF implements ICogworkArmour, IGasProtector, ISpecialSalvage
{
	private boolean isFrame = false;
	public ItemCogworkArmour(String name, ArmourDesign AD, BaseMaterialMF material, int slot, String tex, int rarity)
	{
		super(name, material, AD, slot, tex, rarity);
		this.setTextureName("minefantasy2:apparel/cogwork/"+name);
		setCreativeTab(CreativeTabMF.tabGadget);
	}
	private float fuelCost = 1;
	public ItemCogworkArmour setFuelCost(float cost)
	{
		this.fuelCost = cost;
		return this;
	}
	public ItemCogworkArmour setAsFrame()
	{
		this.isFrame = true;
		return this;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		float resist = 100F;
		float modifier = 1.0F;
		
		CustomMaterial custom = getCustomMaterial(stack);
		if(custom != null)
		{
			resist = custom.resistance;
			if(resist > 0)
			{
				modifier = 1F / (resist/10F);
			}
		}
		
		if(source.isFireDamage())
		{
			damage *= 5F * modifier;
		}
		if(source.isExplosion())
		{
			damage *= 10F;
		}
		if(ArmourListMF.isUnbreakable(baseMaterial, entity))
		{
			return;
		}
		initArmourDamage(entity, stack, damage);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		String img = texture;
		if(type == null || isFrame)//bottom layer
		{
			return "minefantasy2:textures/models/armour/cogwork/"+texture +".png";//COLOUR LAYER
		}
		return "minefantasy2:textures/models/armour/cogwork/"+texture +"_detail.png";//STATIC LAYER
	}
	@Override
	public float getBaseResistance(ItemStack item, DamageSource source)
	{
		if(source != null && source == DamageSource.fall)
		{
			return 75F;
		}
		return 0;
	}
	@Override
	public String getSuitWeigthType(ItemStack item)
	{
		return "heavy";
	}
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user)
    {
		if(user.isSneaking() && needsPower(item) && this.armorType == 1)
		{
			if(CogworkArmour.getFuelValue(item) < CogworkArmour.getMaxFuel(item))
			{
				int value = CogworkArmour.coalFuel;
				if(user.inventory.consumeInventoryItem(ComponentListMF.coke))
				{
					CogworkArmour.addFuel(item, value);
				}
			}
			return item;
		}
		return super.onItemRightClick(item, world, user);
    }


	@Override
	public boolean needsPower(ItemStack item) 
	{
		return armorType == 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean full)
	{
		if(needsPower(item) && this.armorType == 1)
		{
			float value = ((float)CogworkArmour.getFuelValue(item) / (float)CogworkArmour.getMaxFuel(item)) * 100F;
			list.add(StatCollector.translateToLocalFormatted("vehicle.fuel.name", (int)value));
		}
		float mass = getPieceWeight(item, armorType);
    	
    	CustomMaterial base = getCustomMaterial(item);
    	if(base != null)
    	{
    		String matName = StatCollector.translateToLocalFormatted("item.mod_plating.name", StatCollector.translateToLocal("material."+base.name.toLowerCase() + ".name"));
    		list.add(EnumChatFormatting.GOLD + matName);
    		list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("materialtype.resist.name", base.resistance));
    	}
    	list.add(CustomMaterial.getWeightString(mass));
    	
		super.addInformation(item, user, list, full);
	}

	@Override
	public float getArrowDeflection(ItemStack item, DamageSource source)
	{
		return (baseMaterial == BaseMaterialMF.cogworks) ? 0.0F : 0.75F;
	}

	@Override
	public float getGasProtection(ItemStack item) 
	{
		return 100F;
	}


	@Override
	public float getPowerCost(ItemStack item)
	{
		return fuelCost;
	}
	@Override
	public boolean hasColor(ItemStack item)
    {
        return true;
    }
	@Override
	public boolean canColour()
    {
        return true;
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
	@Override
    public int getColor(ItemStack item)
    {
		CustomMaterial material =  getCustomMaterial(item);
		if(material == null)
		{
			return (255 << 16) + (255 << 8) + 255;
		}
        return material.getColourInt();
    }
	
	@Override
	public CustomMaterial getCustomMaterial(ItemStack item)
	{
		CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
		if(material != null)
		{
			return material;
		}
		return null;
	}
	 @Override
    public void func_82813_b(ItemStack item, int colour)
    {
        return;
    }

	 /**
	  * Adds a suit ONLY IF the material ingot exists
	  */
	public static void tryAddSuits(List list, String plating)
	{
		ArrayList<ItemStack> mats = OreDictionary.getOres("ingot"+plating);
		if(MineFantasyII.isDebug() || (mats != null && !mats.isEmpty()))
		{
			addSuit(list, plating);
		}
	}
	public static void addSuit(List list, String plating) 
	{
		list.add(ArmourListMF.cogwork_armour_helmet.construct(plating));
		list.add(ArmourListMF.cogwork_armour_chest.construct(plating));
		list.add(ArmourListMF.cogwork_armour_legs.construct(plating));
		list.add(ArmourListMF.cogwork_armour_boots.construct(plating));
		
		//list.add(ArmourListMF.cogwork_dwarf_armour_helmet.construct(plating));
		//list.add(ArmourListMF.cogwork_dwarf_armour_chest.construct(plating));
		//list.add(ArmourListMF.cogwork_dwarf_armour_legs.construct(plating));
		//list.add(ArmourListMF.cogwork_dwarf_armour_boots.construct(plating));
	}
	
	private IIcon plateIcon, detailIcon;
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
		this.plateIcon = reg.registerIcon(this.getIconString());
		this.detailIcon = reg.registerIcon(this.getIconString()+"_detail");
    }
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int dam, int layer)
    {
        return layer == 1 ?  detailIcon : plateIcon;
    }
	@SideOnly(Side.CLIENT)
	@Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	
	@Override
	public float getPieceWeight(ItemStack item, int slot) 
	{
		float baseWeight = armourWeight * ArmourCalculator.sizes[slot];
		CustomMaterial material = this.getCustomMaterial(item);
		if(material != null)
		{
			baseWeight = material.density*8F + (ArmourListMF.cogwork_frame_helmet.armourWeight * ArmourCalculator.sizes[slot]);
		}
		return baseWeight;
	}
	
	public int getMaxDamage(ItemStack stack)
    {
		CustomMaterial material = this.getCustomMaterial(stack);
		if(material != null)
		{
			return (int)((material.durability*250) * (design.getDurability()/2F));
		}
        return getMaxDamage();
    }
	@Override
	public Object[] getSalvage(ItemStack item) 
	{
		if(!isFrame)
		{
			ItemStack ingot = null;
			CustomMaterial material = this.getCustomMaterial(item);
			if(material != null)
			{
				ArrayList<ItemStack> ores = OreDictionary.getOres("ingot"+material.name);
				if(ores != null && ores.size()>0)
				{
					ingot = ores.get(0).copy();
					ingot.stackSize = 8;
				}
			}
			if(ingot == null)
			{
				return new Object[]
				{
					this.getContainerItem(item),
					new ItemStack(ComponentListMF.rivet, 3),
				};
			}
			else
			{
				return new Object[]
				{
					this.getContainerItem(item),
					new ItemStack(ComponentListMF.rivet, 3),
					ingot
				};
			}
		}
		return null;
	}
	@Override
	public float getProtectiveTrait(ItemStack item, int dtype)
	{
		if(isFrame && dtype == 2)
		{
			return 0.1F;
		}
		return super.getProtectiveTrait(item, dtype);
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		if(this != ArmourListMF.cogwork_armour_boots)
		{
			return;
		}
		list.add(new ItemStack(ArmourListMF.cogwork_frame_helmet));
		list.add(new ItemStack(ArmourListMF.cogwork_frame_chest));
		list.add(new ItemStack(ArmourListMF.cogwork_frame_legs));
		list.add(new ItemStack(ArmourListMF.cogwork_frame_boots));
		
		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
		Iterator iteratorMetal = metal.iterator();
		while(iteratorMetal.hasNext())
    	{
			CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
			ItemCogworkArmour.tryAddSuits(list, customMat.name);
    	}
    }
}
