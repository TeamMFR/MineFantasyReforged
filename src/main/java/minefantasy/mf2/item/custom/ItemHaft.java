package minefantasy.mf2.item.custom;

import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.GuiHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.gadget.EnumCasingType;
import minefantasy.mf2.item.gadget.EnumExplosiveType;
import minefantasy.mf2.item.gadget.EnumFuseType;
import minefantasy.mf2.item.gadget.EnumPowderType;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHaft extends Item 
{
	@SideOnly(Side.CLIENT)
	public IIcon baseTex, gripTex, braceTex;
	private String name;
	
	public ItemHaft(String name)
	{
		this.name=name;
		this.setCreativeTab(CreativeTabMF.tabMaterials);
		GameRegistry.registerItem(this, name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
	}
	
	public CustomMaterial getBase(ItemStack haft)
	{
		return CustomMaterial.getMaterialFor(haft, "base");
	}
	public CustomMaterial getGrip(ItemStack haft)
	{
		return CustomMaterial.getMaterialFor(haft, "grip");
	}
	public CustomMaterial getBrace(ItemStack haft)
	{
		return CustomMaterial.getMaterialFor(haft, "brace");
	}
	 @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
	 
    }
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo)
    {
    	super.addInformation(item, user, list, fullInfo);
    	
    	CustomMaterial base = getBase(item);
    	CustomMaterial grip = getGrip(item);
    	CustomMaterial brace = getBrace(item);
    	
    	float mass = 0F;
    	if(base != null)mass += base.density;
    	if(grip != null)mass += base.density;
    	if(brace != null)mass += brace.density;
    	
    	list.add(CustomMaterial.getWeightString(mass));
    	
    	if(base != null)
    	{
    		list.add(StatCollector.translateToLocalFormatted("component.shaft.name", StatCollector.translateToLocal("material."+base.name.toLowerCase() + ".name")));
    	}
    	if(grip != null)
    	{
    		list.add(StatCollector.translateToLocalFormatted("component.grip.name", StatCollector.translateToLocal("material."+grip.name.toLowerCase() + ".name")));
    	}
    	if(brace != null)
    	{
    		list.add(StatCollector.translateToLocalFormatted("component.brace.name", StatCollector.translateToLocal("material."+brace.name.toLowerCase() + ".name")));
    	}
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata)
    {
        return 3;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack item, int layer)
    {
		CustomMaterial base = getBase(item);
    	CustomMaterial grip = getGrip(item);
    	CustomMaterial brace = getBrace(item);
    	
    	int topLayer = brace != null ? brace.getColourInt() : grip != null ? grip.getColourInt() : base != null ? base.getColourInt() : super.getColorFromItemStack(item, layer);
    	
    	if(layer == 0 && base != null)
    	{
    		return base.getColourInt();
    	}
    	if(layer == 1 && grip != null)
    	{
    		return grip.getColourInt();
    	}
    	if(layer == 2 && brace != null)
    	{
    		return brace.getColourInt();
    	}
    	return topLayer;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer)
    {
    	CustomMaterial grip = getGrip(item);
    	CustomMaterial brace = getBrace(item);
    	IIcon topLayer = brace != null ? braceTex : grip != null ? gripTex : baseTex;
    	
    	if(layer == 0)
    	{
    		return baseTex;
    	}
    	if(layer == 1 && grip != null)
    	{
    		return gripTex;
    	}
    	if(layer == 2 && brace != null)
    	{
    		return braceTex;
    	}
    	return topLayer;
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
        baseTex = reg.registerIcon("minefantasy2:custom/haft/"+name+"_base");
        gripTex = reg.registerIcon("minefantasy2:custom/haft/"+name+"_grip");
        braceTex = reg.registerIcon("minefantasy2:custom/haft/"+name+"_brace");
    }
    
	public ItemStack createHaft(String base, String grip, String brace) 
	{
		ItemStack item = new ItemStack(this);
		CustomMaterial.addMaterial(item, "base", base);
		CustomMaterial.addMaterial(item, "grip", grip);
		CustomMaterial.addMaterial(item, "brace", brace);
		return item;
	}
}
