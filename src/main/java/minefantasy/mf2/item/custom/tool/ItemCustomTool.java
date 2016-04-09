package minefantasy.mf2.item.custom.tool;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public abstract class ItemCustomTool extends ItemTool
{
	@SideOnly(Side.CLIENT)
	public IIcon baseTex, gripTex, braceTex, headTex;
    public ItemCustomTool(String name, Set set)
    {
    	super(0F, ToolMaterial.IRON, set);
    	this.setCreativeTab(CreativeTabMF.tabTool);
		GameRegistry.registerItem(this, name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
    }

    @Override
    public float func_150893_a(ItemStack tool, Block block)
    {
        return ForgeHooks.isToolEffective(tool, block, 0) ? getEfficiency(tool) : 1.0F;
    }

    public float getEfficiency(ItemStack tool)
    {
    	float efficiency = 2.0F;
    	
    	CustomMaterial base = getBase(tool);
    	if(base != null)
    	{
    		efficiency += (base.sharpness*2F);
    	}
    	return efficiency;
    }
    public float getHitDamage(ItemStack tool)
    {
    	CustomMaterial base = getBase(tool);
    	if(base != null)
    	{
    		return base.sharpness;
    	}
    	return 1.0F;
    }
    public int getMaxUses(ItemStack tool)
    {
    	float efficiency = 1.0F;
    	
    	CustomMaterial base = getBase(tool);
    	CustomMaterial shaft = getHaftBase(tool);
    	CustomMaterial grip = getHaftGrip(tool);
    	CustomMaterial brace = getHaftBrace(tool);
    	if(base != null)
    	{
    		efficiency *= base.hardness;
    	}
    	if(shaft != null)
    	{
    		efficiency += (efficiency *= shaft.hardness * 0.25F);
    	}
    	if(grip != null)
    	{
    		efficiency += (efficiency *= grip.hardness * 0.1F);
    	}
    	if(brace != null)
    	{
    		efficiency += (efficiency *= brace.hardness * 0.25F);
    	}
    	return (int)(250*efficiency);
    }
    public int getHarvestLevel(ItemStack tool)
    {
    	CustomMaterial base = getBase(tool);
    	if(base != null && base.tier > 3)
    	{
    		return base.tier-1;
    	}
    	return 2;
    }
    public float getWeightInKg(ItemStack tool)
    {
    	float mass = 0F;
    	
    	CustomMaterial base = getBase(tool);
    	CustomMaterial haft = getHaftBase(tool);
    	CustomMaterial grip = getHaftGrip(tool);
    	CustomMaterial brace = getHaftBrace(tool);
    	if(base != null)
    	{
    		mass += base.density;
    	}
    	if(haft != null)
    	{
    		mass += haft.density;
    	}
    	if(grip != null)
    	{
    		mass += grip.density;
    	}
    	if(brace != null)
    	{
    		mass += brace.density;
    	}
    	return mass;
    }

    public CustomMaterial getBase(ItemStack head)
	{
		return CustomMaterial.getMaterialFor(head, "head");
	}
    
    public CustomMaterial getHaftBase(ItemStack haft)
	{
		return CustomMaterial.getMaterialFor(haft, "base");
	}
	public CustomMaterial getHaftGrip(ItemStack haft)
	{
		return CustomMaterial.getMaterialFor(haft, "grip");
	}
	public CustomMaterial getHaftBrace(ItemStack haft)
	{
		return CustomMaterial.getMaterialFor(haft, "brace");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack tool, EntityPlayer user, List list, boolean fullInfo)
    {
    	super.addInformation(tool, user, list, fullInfo);
    	
    	float efficiency = getEfficiency(tool);
    	int uses = getMaxUses(tool);
    	float mass = getWeightInKg(tool);
    	float damage = getHitDamage(tool);
    	
    	list.add("Efficiency: "+efficiency);
    	list.add("Damage: "+ damage);
    	list.add("Uses: "+uses);
    	list.add(CustomMaterial.getWeightString(mass));
    }
    
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    @Override
    public boolean hitEntity(ItemStack tool, EntityLivingBase target, EntityLivingBase user)
    {
        tool.damageItem(2, user);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack tool, World world, Block block, int x, int y, int z, EntityLivingBase user)
    {
        if ((double)block.getBlockHardness(world, x, y, z) != 0.0D)
        {
            tool.damageItem(1, user);
        }

        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public Item.ToolMaterial func_150913_i()
    {
        return this.toolMaterial;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack item, ItemStack item2)
    {
        return false;//this.toolMaterial.func_150995_f() == item2.getItem() ? true : super.getIsRepairable(item, item2);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", 0D, 0));
        return multimap;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata)
    {
        return 4;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack item, int layer)
    {
		CustomMaterial head = getBase(item);
		CustomMaterial base = getHaftBase(item);
    	CustomMaterial grip = getHaftGrip(item);
    	CustomMaterial brace = getHaftBrace(item);
    	
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
    	return head.getColourInt();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer)
    {
    	CustomMaterial head = getBase(item);
		CustomMaterial base = getHaftBase(item);
    	CustomMaterial grip = getHaftGrip(item);
    	CustomMaterial brace = getHaftBrace(item);
    	
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
    	return headTex;
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
        baseTex = reg.registerIcon("minefantasy2:custom/haft/haft_base");
        gripTex = reg.registerIcon("minefantasy2:custom/haft/haft_grip");
        braceTex = reg.registerIcon("minefantasy2:custom/haft/haft_brace");
        headTex = reg.registerIcon("minefantasy2:custom/commodity/"+getName() + "_head");
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack tool)
    {
    	CustomMaterial head = getBase(tool);
    	return StatCollector.translateToLocalFormatted("item.tool_"+ getName() +".name", StatCollector.translateToLocal("material."+head.name.toLowerCase() + ".name"));
    }
    
	public abstract String  getName();

	 @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		 ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
    	Iterator iteratorMetal = metal.iterator();
    	while(iteratorMetal.hasNext())
    	{
    		CustomMaterial material = (CustomMaterial) iteratorMetal.next();
    		list.add(this.createTool(material.name, "OakWood", "Leather", null));
    	}
    	
    	list.add(this.createTool("Iron", "OakWood", null, null));
    	list.add(this.createTool("Iron", "OakWood", "Leather", "Bronze"));
    	list.add(this.createTool("Steel", "EbonyWood", "DragonSkin", "Iron"));
    }
	 
	public ItemStack createTool(String head, String base, String grip, String brace) 
	{
		ItemStack item = new ItemStack(this);
		CustomMaterial.addMaterial(item, "head", head);
		CustomMaterial.addMaterial(item, "base", base);
		CustomMaterial.addMaterial(item, "grip", grip);
		CustomMaterial.addMaterial(item, "brace", brace);
		
		item.getAttributeModifiers().clear();
		item.getAttributeModifiers().put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Material modifier", getHitDamage(item), 0));
		return item;
	}

    /*===================================== FORGE START =================================*/
    private String toolClass;
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
    	return getHarvestLevel(stack);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return toolClass != null ? ImmutableSet.of(toolClass) : super.getToolClasses(stack);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
        if (ForgeHooks.isToolEffective(stack, block, meta))
        {
            return getEfficiency(stack);
        }
        return func_150893_a(stack, block);
    }
    /*===================================== FORGE END =================================*/
}