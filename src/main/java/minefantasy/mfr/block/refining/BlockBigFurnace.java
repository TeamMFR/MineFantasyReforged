package minefantasy.mfr.block.refining;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.tile.TileEntityBigFurnace;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBigFurnace extends BlockContainer {
    public static EnumBlockRenderType furn_RI;
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
        super(Material.ROCK);

        setRegistryName(name);
        setUnlocalizedName(name);
        this.isHeater = isHeater;
        this.tier = tier;
        this.setCreativeTab(CreativeTabMFR.tabUtil);
        this.setSoundType(SoundType.STONE);
        this.setHardness(3.5F);
        this.setResistance(2F);
    }


    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityBigFurnace) {
            if (((TileEntityBigFurnace) tile).isBurning()) {
                return 10;
            }
        }
        return super.getLightValue(state, world, pos);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return furn_RI;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityBigFurnace tile = (TileEntityBigFurnace) world.getTileEntity(pos);

            ItemStack item = player.getHeldItem(hand);

            if (tile != null) {
                player.openGui(MineFantasyReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state);
    }

    /**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepFurnaceInventory) {
            TileEntityBigFurnace tile = (TileEntityBigFurnace) world.getTileEntity(pos);

            if (tile != null) {
                for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6) {
                    ItemStack var7 = tile.getStackInSlot(var6);

                    if (var7 != null) {
                        float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (var7.getCount() > 0) {
                            int var11 = this.rand.nextInt(21) + 10;

                            if (var11 > var7.getCount()) {
                                var11 = var7.getCount();
                            }

                            var7.shrink(var11);
                            EntityItem var12 = new EntityItem(world, pos.getX() + var8, pos.getY() + var9, pos.getZ() + var10,
                                    new ItemStack(var7.getItem(), var11, var7.getItemDamage()));

                            if (var7.hasTagCompound()) {
                                var12.getItem().setTagCompound( var7.getTagCompound().copy());
                            }

                            float var13 = 0.05F;
                            var12.motionX = (float) this.rand.nextGaussian() * var13;
                            var12.motionY = (float) this.rand.nextGaussian() * var13 + 0.2F;
                            var12.motionZ = (float) this.rand.nextGaussian() * var13;
                            world.spawnEntity(var12);
                        }
                    }
                }
            }
        }

        super.breakBlock(world, pos, state);
    }
}
