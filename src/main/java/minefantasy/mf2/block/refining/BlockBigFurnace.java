package minefantasy.mf2.block.refining;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityBigFurnace;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBigFurnace extends BlockContainer {
    public static int furn_RI = 114;
    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block
     * removal, is used internally when the furnace block changes from idle to
     * active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;
    public final boolean isHeater;
    public final int tier;
    /**
     * Is the random generator used by furnace to drop the inventory contents in
     * random directions.
     */
    private Random rand = new Random();

    public BlockBigFurnace(String name, boolean isHeater, int tier) {
        super(Material.rock);
        this.isHeater = isHeater;
        this.tier = tier;
        this.setCreativeTab(CreativeTabMF.tabUtil);
        GameRegistry.registerBlock(this, name);
        setBlockName(name);
        this.setStepSound(Block.soundTypeStone);
        this.setHardness(3.5F);
        this.setResistance(2F);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return BlockListMF.firebricks.getIcon(side, meta);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityBigFurnace) {
            if (((TileEntityBigFurnace) tile).isBurning()) {
                return 10;
            }
        }
        return super.getLightValue(world, x, y, z);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return furn_RI;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1,
                                    float f2) {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityBigFurnace tile = (TileEntityBigFurnace) world.getTileEntity(x, y, z);

            ItemStack item = player.getHeldItem();

            if (tile != null) {
                player.openGui(MineFantasyII.instance, 0, world, x, y, z);
            }

            return true;
        }
    }

    /**
     * /** Returns the TileEntity used by this block.
     */
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBigFurnace();
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int dir = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    }

    /**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World world, int x, int y, int z, Block i1, int i2) {
        if (!keepFurnaceInventory) {
            TileEntityBigFurnace tile = (TileEntityBigFurnace) world.getTileEntity(x, y, z);

            if (tile != null) {
                for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6) {
                    ItemStack var7 = tile.getStackInSlot(var6);

                    if (var7 != null) {
                        float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (var7.stackSize > 0) {
                            int var11 = this.rand.nextInt(21) + 10;

                            if (var11 > var7.stackSize) {
                                var11 = var7.stackSize;
                            }

                            var7.stackSize -= var11;
                            EntityItem var12 = new EntityItem(world, x + var8, y + var9, z + var10,
                                    new ItemStack(var7.getItem(), var11, var7.getItemDamage()));

                            if (var7.hasTagCompound()) {
                                var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
                            }

                            float var13 = 0.05F;
                            var12.motionX = (float) this.rand.nextGaussian() * var13;
                            var12.motionY = (float) this.rand.nextGaussian() * var13 + 0.2F;
                            var12.motionZ = (float) this.rand.nextGaussian() * var13;
                            world.spawnEntityInWorld(var12);
                        }
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, i1, i2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
    }
}
