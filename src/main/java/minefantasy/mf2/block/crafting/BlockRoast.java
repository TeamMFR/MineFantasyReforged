package minefantasy.mf2.block.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.tileentity.TileEntityRoast;
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

import java.util.Random;

public class BlockRoast extends BlockContainer {
    public static int roast_RI = 113;
    public String tex;
    public Random rand = new Random();
    private boolean isOven;
    private int tier;

    public BlockRoast(int tier, String tex, boolean isOven) {
        super(Material.rock);
        this.isOven = isOven;
        this.tex = tex;
        this.tier = tier;
        String name = "food_" + (isOven ? "oven" : "roast") + "_" + tex;
        this.setBlockName(name);
        GameRegistry.registerBlock(this, name);
        this.setHardness(1.5F);
        this.setResistance(1F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMF.tabUtil);
        if (isOven) {
            setBlockBounds(3F / 16F, 0F, 3F / 16F, 13F / 16F, 1F / 1.6F, 13F / 16F);
        } else {
            setBlockBounds(3F / 16F, 0F, 3F / 16F, 13F / 16F, 0.125F, 13F / 16F);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityRoast();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item) {
        int dir = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    }

    public TileEntityRoast getTile(World world, int x, int y, int z) {
        return (TileEntityRoast) world.getTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        TileEntityRoast tile = getTile(world, x, y, z);
        if (tile != null) {
            return tile.interact(user);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityRoast tile = getTile(world, x, y, z);

        if (tile != null) {
            ItemStack itemstack = tile.items[0];

            if (itemstack != null) {
                float f = this.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0) {
                    int j1 = this.rand.nextInt(21) + 10;

                    if (j1 > itemstack.stackSize) {
                        j1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j1;
                    EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2,
                            new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound()) {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                    entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.anvil.getIcon(side, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {

    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isOven() {
        return isOven;
    }

    @Override
    public int getRenderType() {
        return isOven ? roast_RI : super.getRenderType();
    }
}
