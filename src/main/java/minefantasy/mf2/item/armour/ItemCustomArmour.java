package minefantasy.mf2.item.armour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomArmour extends ItemArmourMF
{
	private String craftDesign;
	public ItemCustomArmour(String craftDesign, String name, ArmourDesign AD, int slot, String tex, int rarity)
	{
		super(craftDesign + "_"+name, BaseMaterialMF.iron, AD, slot, craftDesign + "_"+tex, rarity);
		this.setTextureName("minefantasy2:custom/apparel/" + craftDesign + "/"+craftDesign+"_"+name);
		this.craftDesign = craftDesign;
		canRepair = false;
	}
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		super.damageArmor(entity, stack, source, damage, slot);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item)
    {
    	String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
    	return CustomToolHelper.getLocalisedName(item, unlocalName);
    }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		String tex = "minefantasy2:textures/models/armour/custom/" + craftDesign + "/"+texture;
		if(type == null)//bottom layer
		{
			return tex +".png";//COLOUR LAYER
		}
		return tex +"_detail.png";//STATIC LAYER
	}
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean full)
	{
		CustomToolHelper.addInformation(item, list);
		float mass = getPieceWeight(item, armorType);
    	
    	list.add(CustomMaterial.getWeightString(mass));
    	/*
    	CustomMaterial base = CustomToolHelper.getCustomMetalMaterial(item);
    	if(base != null)
    	{
    		list.add(StatCollector.translateToLocalFormatted("materialtype.fireresist.name", base.getFireResistance()));
    	}
    	*/
		super.addInformation(item, user, list, full);
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
     * Return the colour of the material it is made of.
     */
	@Override
    public int getBaseColour(ItemStack item)
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
	public static void addSuit(List list, String material) 
	{
		list.add(CustomArmourListMF.standard_chain_helmet.construct(material));
		list.add(CustomArmourListMF.standard_chain_chest.construct(material));
		list.add(CustomArmourListMF.standard_chain_legs.construct(material));
		list.add(CustomArmourListMF.standard_chain_boots.construct(material));
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		if(this != CustomArmourListMF.standard_chain_boots)return;
		
		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
		Iterator iteratorMetal = metal.iterator();
		while(iteratorMetal.hasNext())
    	{
			CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
			
			if(MineFantasyII.isDebug() || customMat.getItem() != null)
			{
				list.add(CustomArmourListMF.standard_scale_helmet.construct(customMat.name));
				list.add(CustomArmourListMF.standard_scale_chest.construct(customMat.name));
				list.add(CustomArmourListMF.standard_scale_legs.construct(customMat.name));
				list.add(CustomArmourListMF.standard_scale_boots.construct(customMat.name));
				
				list.add(CustomArmourListMF.standard_chain_helmet.construct(customMat.name));
				list.add(CustomArmourListMF.standard_chain_chest.construct(customMat.name));
				list.add(CustomArmourListMF.standard_chain_legs.construct(customMat.name));
				list.add(CustomArmourListMF.standard_chain_boots.construct(customMat.name));
				
				list.add(CustomArmourListMF.standard_splint_helmet.construct(customMat.name));
				list.add(CustomArmourListMF.standard_splint_chest.construct(customMat.name));
				list.add(CustomArmourListMF.standard_splint_legs.construct(customMat.name));
				list.add(CustomArmourListMF.standard_splint_boots.construct(customMat.name));
				
				list.add(CustomArmourListMF.standard_plate_helmet.construct(customMat.name));
				list.add(CustomArmourListMF.standard_plate_chest.construct(customMat.name));
				list.add(CustomArmourListMF.standard_plate_legs.construct(customMat.name));
				list.add(CustomArmourListMF.standard_plate_boots.construct(customMat.name));
			}
    	}
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
			baseWeight *= material.density;
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
	
	public boolean isCustom() {
		return true;
	}
}
