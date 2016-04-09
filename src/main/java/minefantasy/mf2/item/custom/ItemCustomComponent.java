package minefantasy.mf2.item.custom;

import java.util.HashMap;
import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomComponent extends Item 
{
	@SideOnly(Side.CLIENT)
	public IIcon baseTex;
	private String name;
	private float mass;
	
	public ItemCustomComponent(String name, float mass)
	{
		this.name = name;
		this.setCreativeTab(CreativeTabMF.tabMaterials);
		GameRegistry.registerItem(this, "custom_"+name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
		this.mass=mass;
	}
	
	 @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
	 
    }
	 
	public float getWeightInKg(ItemStack tool)
    {
    	CustomMaterial base = getBase(tool);
    	if(base != null)
    	{
    		return base.density * mass;
    	}
    	return mass;
    }
 
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack tool, EntityPlayer user, List list, boolean fullInfo)
    {
    	super.addInformation(tool, user, list, fullInfo);
    	CustomToolHelper.addComponentString(tool, list, getBase(tool));
    		
    }
	 
	@Override
    public String getItemStackDisplayName(ItemStack tool)
    {
    	CustomMaterial head = getBase(tool);
    	String matString = "??";
    	if(head != null)
    	{
    		matString = StatCollector.translateToLocal("material."+head.name.toLowerCase() + ".name");
    	}
    	return StatCollector.translateToLocalFormatted("item.commodity_"+ name +".name", matString);
    }
	
	public CustomMaterial getBase(ItemStack component)
	{
		return CustomToolHelper.getCustomMetalMaterial(component);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata)
    {
        return 1;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack item, int layer)
    {
		CustomMaterial base = getBase(item);
    	if(base != null)
    	{
    		return base.getColourInt();
    	}
    	return super.getColorFromItemStack(item, layer);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer)
    {
		return baseTex;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
    	return true;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        baseTex = reg.registerIcon("minefantasy2:custom/component/"+name);
    }
    
    public ItemStack createComm(String base) 
	{
    	return createComm(base, 1);
	}
	public ItemStack createComm(String base, int stack) 
	{
		ItemStack item = new ItemStack(this, stack);
		CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, base);
		return item;
	}
}
