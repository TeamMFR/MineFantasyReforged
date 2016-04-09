package minefantasy.mf2.item.tool;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.tool.ILighter;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemLighterMF extends Item implements ILighter
{
	private float chance;
	public ItemLighterMF(String name, float chance, int uses)
	{
		setTextureName("minefantasy2:Tool/"+name);
		GameRegistry.registerItem(this, name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
		setMaxDamage(uses);
		this.chance = chance;
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabMF.tabGadget);
	}
	@Override
	public boolean canLight()
	{
		return true;
	}
	@Override
	public double getChance() 
	{
		return chance;
	}
	//0 for N/A -1 for fail, 1 for succeed
	public static int tryUse(ItemStack held, EntityPlayer user) 
	{
		if(held == null)return 0;
		
		if(held.getItem() instanceof ItemFlintAndSteel)
		{
			return 1;
		}
		if(held.getItem() instanceof ILighter)
		{
			ILighter lighter = (ILighter)held.getItem();
			return (lighter.canLight() && user.getRNG().nextFloat() < lighter.getChance()) ? 1 : -1;
		}
		return 0;
	}
	
	public boolean onItemUse(ItemStack item, EntityPlayer user, World world, int x, int y, int z, int face, float xo, float yo, float zo)
    {
        if (face == 0)
        {
            --y;
        }

        if (face == 1)
        {
            ++y;
        }

        if (face == 2)
        {
            --z;
        }

        if (face == 3)
        {
            ++z;
        }

        if (face == 4)
        {
            --x;
        }

        if (face == 5)
        {
            ++x;
        }

        if (!user.canPlayerEdit(x, y, z, face, item))
        {
            return false;
        }
        else
        {
        	boolean success = user.getRNG().nextFloat() < chance;
            if (world.isAirBlock(x, y, z))
            {
                world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                if(success)
                {
                	world.setBlock(x, y, z, Blocks.fire);
                }
            }
            if(success)
            {
            	item.damageItem(1, user);
            }
            return true;
        }
    }
}
