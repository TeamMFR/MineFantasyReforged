package minefantasy.mf2.block.decor;

import minefantasy.mf2.block.tileentity.TileEntityTrough;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTrough extends BlockContainer 
{
	public static int trough_RI = 107;
	public int capacity = 16;
	public String name;
	public BlockTrough(String name, Material material, int capacity) 
	{
		super(material);
		this.capacity = capacity;
		this.setBlockBounds(0F, 0F, 0F, 1.0F, (7F/16F), 1.0F);
		
		this.name=name;
		GameRegistry.registerBlock(this, "MF_Trough_"+name);
		setBlockName("trough"+name);
		this.setHardness(1F);
		this.setResistance(0.5F);
        this.setCreativeTab(CreativeTabMF.tabUtil);
	}
	
	@Override
    public boolean isOpaqueCube() 
    {
        return false;
    }
    @Override
    public boolean renderAsNormalBlock() 
    {
        return false;
    }
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	return Blocks.planks.getIcon(side, 0);
    }
    @Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		
	}
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return trough_RI;
    }
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item)
    {
        int direction = MathHelper.floor_double((double)(user.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }
	@Override
	public TileEntity createNewTileEntity(World world, int meta) 
	{
		return new TileEntityTrough();
	}
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset, float yOffset, float zOffset)
    {
		ItemStack held = user.getHeldItem();
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityTrough)
		{
			return ((TileEntityTrough)tile).interact(user, held);
		}
		return false;
    }
}
