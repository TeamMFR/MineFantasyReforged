package minefantasy.mf2.item.gadget;

import java.util.Iterator;
import java.util.Random;

import minefantasy.mf2.api.heating.TongsHelper;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ItemJug extends ItemComponentMF 
{
	private String type;
	public ItemJug(String type)
	{
		super("jug_"+type, 0);
		setCreativeTab(CreativeTabMF.tabFood);
		this.type = type;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
		if(type.equalsIgnoreCase("uncooked"))
		{
			return item;
		}
		if(type.equalsIgnoreCase("empty"))
		{
			return rightClickEmpty(item, world, player);
		}
		player.setItemInUse(item, getMaxItemUseDuration(item));
		return item;
    }
	
	@Override
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer user)
    {
        if (!user.capabilities.isCreativeMode)
        {
            --item.stackSize;
        }

        if (!world.isRemote)
        {
        	if(type.equalsIgnoreCase("milk"))
        	{
        		curePotionEffects(user);
        	}
        	if(StaminaBar.isSystemActive)
        	{
        		StaminaBar.modifyStaminaValue(user, 10);
        	}
        }

        return item.stackSize <= 0 ? getContainerItem(item) : item;
    }

    private void curePotionEffects(EntityPlayer user) 
    {
    	user.clearActivePotions();
	}

	/**
     * How long it takes to use or consume an item
     */
	@Override
    public int getMaxItemUseDuration(ItemStack item)
    {
        return 24;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
	@Override
    public EnumAction getItemUseAction(ItemStack item)
    {
        return EnumAction.drink;
    }
	
	private ItemStack rightClickEmpty(ItemStack item, World world, EntityPlayer player) 
	{
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null)
        {
            return item;
        }
        else
        {
        	if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k))
                {
                    return item;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item))
                {
                    return item;
                }

                if (isWaterSource(world, i, j, k))
                {
                	gather(item, world, player);
                }
            }
            return item;
        }
	}

	private  Random rand = new Random();
    private void gather(ItemStack item, World world, EntityPlayer player)
    {
        player.swingItem();
        if (!world.isRemote) 
        {
            world.playSoundAtEntity(player, "random.splash", 0.125F + rand.nextFloat()/4F, 0.5F + rand.nextFloat());
            item.stackSize--;
            EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(FoodListMF.jug_water));
            world.spawnEntityInWorld(resultItem);
        }
	}
    
    private boolean isWaterSource(World world, int i, int j, int k)
    {
		return TongsHelper.getWaterSource(world, i, j, k) >= 0;
	}
}
