package minefantasy.mf2.block.refining;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.block.tileentity.TileEntityQuern;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockQuern extends BlockContainer {
    public static int quern_RI = 111;
    public IIcon bottomTex, sideTex, topTex;
    private Random rand = new Random();
    private String type;

    public BlockQuern(String type) {
        super(Material.rock);
        this.type = type;
        GameRegistry.registerBlock(this, "MF_Grind_" + type);
        setBlockName(type);
        this.setStepSound(Block.soundTypeStone);
        this.setHardness(5F);
        this.setResistance(5F);
        this.setCreativeTab(CreativeTabMF.tabUtil);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityQuern();
    }

    private TileEntityQuern getTile(IBlockAccess world, int x, int y, int z) {
        return (TileEntityQuern) world.getTileEntity(x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityQuern tile = getTile(world, x, y, z);

        if (tile != null) {
            for (int i1 = 0; i1 < tile.getSizeInventory(); ++i1) {
                ItemStack itemstack = tile.getStackInSlot(i1);

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
                            entityitem.getEntityItem()
                                    .setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                        entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        TileEntityQuern tile = getTile(world, x, y, z);
        if (tile != null) {
            if (side == 1) {
                tile.onUse(user);
            } else {
                user.openGui(MineFantasyII.instance, 0, world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer user) {
        TileEntityQuern tile = getTile(world, x, y, z);
        if (tile != null) {
            tile.onUse(user);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.topTex = reg.registerIcon("minefantasy2:processor/" + type + "_top");
        this.sideTex = reg.registerIcon("minefantasy2:processor/" + type + "_side");
        this.bottomTex = reg.registerIcon("minefantasy2:processor/" + type + "_base");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 0 ? bottomTex : side == 1 ? topTex : sideTex;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return quern_RI;
    }
}
