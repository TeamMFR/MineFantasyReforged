package minefantasy.mfr.block.decor;

import minefantasy.mfr.tile.TileEntityRoad;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.item.tool.advanced.ItemMattock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRoad extends BlockContainer {
    public BlockRoad(String name, float f) {
        super(Material.GROUND);
        this.setSoundType(SoundType.GROUND);
        this.setLightOpacity(0);

        setRegistryName(name);
        setUnlocalizedName(name);
        setHardness(0.5F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (this == BlockListMFR.LOW_ROAD)
            return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);

        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
     * not to render the shared face of two adjacent blocks and also whether the
     * player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState blockState) {
        this.updateTick(world, pos, blockState, new Random());
        super.onBlockAdded(world, pos, blockState);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.getBlockState(pos.add(0,-1,0)).getBlock() == Blocks.GRASS) {
            world.setBlockState(pos.add(0,-1,0),  Blocks.GRASS.getDefaultState(), 2);
        }
        super.updateTick(world, pos, state, rand);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityRoad tile = getTile(world, pos);
        if (tile == null)
            return false;

        boolean isLocked = tile.isLocked;
        ItemStack itemstack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (itemstack != null) {
            if (!player.canPlayerEdit(pos, facing, itemstack)) {
                return false;
            }
            if (!isLocked) {
                Block block = Block.getBlockFromItem(itemstack.getItem());
                if (itemstack.getItem() instanceof ItemBlock && block != null) {
                    if (upgradeRoad(world, pos, 4, itemstack, block)) {
                        if (!player.capabilities.isCreativeMode && !world.isRemote) {
                            itemstack.shrink(1);
                            if (itemstack.getCount() <= 0) {
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                            }
                        }
                        return true;
                    }
                }

                if (itemstack.getItem() instanceof ItemSpade) {
                    if (this == BlockListMFR.ROAD) {
                        if (!world.isRemote) {
                            world.setBlockState(pos, (BlockListMFR.LOW_ROAD).getDefaultState());
                        }
                        return true;
                    }
                }
            }
            if (!world.isRemote && itemstack.getItem() instanceof ItemMattock) {
                toggleLocks(world,pos, 4);
            }
        }
        return !tile.isLocked;
    }

    private TileEntityRoad getTile(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityRoad) {
            return (TileEntityRoad) tile;
        }
        return null;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Block block = state.getBlock();
        Block drop = block.getDefaultState() == block.getBlockState() ? Blocks.SAND : Blocks.DIRT;
        return Item.getItemFromBlock(drop);
    }

/*
     * Resets the Texture
     *
     * @param ID the block right clicked with
     * @return
     */

    private boolean upgradeRoad(World world, BlockPos pos, int r, ItemStack held, Block block) {
        if (!block.isNormalCube(block.getDefaultState())) {
            return false;
        }
        Block heldBlock = Block.getBlockById(held.getItemDamage());
        if (held == null) {
            return false;
        }
        boolean flag = false;

        for (int x2 = -r; x2 <= r; x2++) {
            for (int y2 = -r; y2 <= r; y2++) {
                for (int z2 = -r; z2 <= r; z2++) {
                    Block id = world.getBlockState(pos.add(x2, y2, z2)).getBlock();
                    if ((id == BlockListMFR.ROAD || id == BlockListMFR.LOW_ROAD)) {
                        if (getDistance(pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2, pos.getX(), pos.getY(), pos.getZ()) < r) {
                            {
                                TileEntity tile = world.getTileEntity(pos.add(x2, y2, z2));
                                if (tile != null && tile instanceof TileEntityRoad
                                        && !((TileEntityRoad) tile).isLocked) {
                                    flag = true;
                                    ((TileEntityRoad) tile).setSurface(block, heldBlock);
                                }
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    private boolean toggleLocks(World world, BlockPos pos, int r) {
        boolean flag = false;
        TileEntityRoad tile = getTile(world, pos);
        if (tile == null) {
            return false;
        }
        flag = !tile.isLocked;
        tile.isLocked = flag;
        tile.sendPacketToClients();

        for (int x2 = -r; x2 <= r; x2++) {
            for (int y2 = -r; y2 <= r; y2++) {
                for (int z2 = -r; z2 <= r; z2++) {
                    if (getDistance(pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2, pos.getX(), pos.getY(), pos.getZ()) < r) {
                        TileEntityRoad tile2 = getTile(world, pos.add(x2, y2, z2));
                        if (tile2 != null) {
                            tile2.isLocked = flag;
                            tile2.sendPacketToClients();
                        }
                    }
                }
            }
        }
        return true;
    }

    public double getDistance(double x, double y, double z, int posX, int posY, int posZ) {
        double var7 = posX - x;
        double var9 = posY - y;
        double var11 = posZ - z;
        return MathHelper.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityRoad();
    }
}
