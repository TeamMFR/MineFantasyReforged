package minefantasy.mf2.block.decor;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.tileentity.TileEntityQuern;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockAmmoBox extends BlockContainer 
{
	public static int ammo_RI = 116;
	public int capacity = 128;
	public String name;
	public BlockAmmoBox(String name, Material material, int capacity) 
	{
		super(material);
		this.capacity = capacity;
		float border = 1F/8F;
		this.setBlockBounds(border, border, 0F, 1-border, 0.5F, 1-border);
		
		this.name=name;
		GameRegistry.registerBlock(this, "MF_AmmoBox_"+name);
		setBlockName("ammo_box_"+name);
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
    	return ammo_RI;
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
		return new TileEntityAmmoBox();
	}
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset, float yOffset, float zOffset)
    {
		ItemStack held = user.getHeldItem();
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityAmmoBox)
		{
			return ((TileEntityAmmoBox)tile).interact(user, held);
		}
		return false;
    }
	private Random rand = new Random();
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		TileEntityAmmoBox tile = getTile(world, x, y, z);

        if (tile != null && tile.ammo != null)
        {
            ItemStack itemstack = tile.ammo;

            if (itemstack != null)
            {
                float f = this.rand .nextFloat() * 0.8F + 0.1F;
                float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                while (tile.stock > 0)
                {
                    int j1 = this.rand.nextInt(10) + 8;

                    if (j1 > tile.stock)
                    {
                        j1 = tile.stock;
                    }

                    tile.stock -= j1;
                    EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float)this.rand.nextGaussian() * f3;
                    entityitem.motionY = (float)this.rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)this.rand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

	private TileEntityAmmoBox getTile(World world, int x, int y, int z) 
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityAmmoBox)
		{
			return (TileEntityAmmoBox)tile;
		}
		return null;
	}
}
