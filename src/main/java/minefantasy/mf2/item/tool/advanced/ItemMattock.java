package minefantasy.mf2.item.tool.advanced;

import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityRoad;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.tool.ToolMaterialMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Anonymous Productions
 */
public class ItemMattock extends ItemPickaxe implements IToolMaterial
{
    public ItemMattock(String name, ToolMaterial material, int rarity)
    {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabOldTools);
        
        setTextureName("minefantasy2:Tool/Advanced/"+name);
		GameRegistry.registerItem(this, name, MineFantasyII.MODID);
		this.setHarvestLevel("pickaxe", Math.max(0, material.getHarvestLevel()-2));
		this.setUnlocalizedName(name);
    }
    private int itemRarity;
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
    @Override
	public ToolMaterial getMaterial()
	{
		return toolMaterial;
	}
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		return ToolHelper.modifyDigOnQuality(stack, super.getDigSpeed(stack, block, meta));
	}
	@Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) 
    {
        super.addInformation(item, user, list, extra);
    }
	@Override
    public boolean canHarvestBlock(Block block, ItemStack stack)
    {
    	return super.canHarvestBlock(block, stack) || Items.iron_shovel.canHarvestBlock(block, stack);
    }
	@Override
    public boolean onItemUse(ItemStack mattock, EntityPlayer player, World world, int x, int y, int z, int facing, float pitch, float yaw, float pan)
    {
		if (!player.canPlayerEdit(x, y, z, facing, mattock))
        {
            return false;
        }
        else
        {
            Block var11 = world.getBlock(x, y, z);
            int var11m = world.getBlockMetadata(x, y, z);
            Block var12 = world.getBlock(x, y + 1, z);

            if (facing == 0 || var12 != Blocks.air)
            {
                return false;
            }
            else if(var11 == Blocks.grass || var11 == Blocks.dirt || var11 == Blocks.sand)
            {
                Block var13 = BlockListMF.road;
                int m = 0;
                
                if(var11 == Blocks.sand)
                {
                	m = 1;
                }
                if(var11 == Blocks.grass)
                {
                	var11 = Blocks.dirt;
                }
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "dig."+var11.stepSound.soundName, (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);

                if (world.isRemote)
                {
                    return true;
                }
                else
                {
                    world.setBlock(x, y, z, var13, m, 2);
                    
                    TileEntityRoad road = new TileEntityRoad();
                    road.setWorldObj(world);
                    world.setTileEntity(x, y, z, road);
                    road.setSurface(var11, var11m);
                    mattock.damageItem(1, player);
                    return true;
                }
            }
            else
            {
            	return false;
            }
        }
    }
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
	}
}
