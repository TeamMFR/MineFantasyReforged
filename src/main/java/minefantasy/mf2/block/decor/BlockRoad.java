package minefantasy.mf2.block.decor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityRoad;
import minefantasy.mf2.item.tool.advanced.ItemMattock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRoad extends BlockContainer {
    public BlockRoad(String name, float f) {
        super(Material.ground);
        this.setStepSound(Block.soundTypeGravel);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f / 16F, 1.0F);
        this.setLightOpacity(0);
        GameRegistry.registerBlock(this, name);
        setBlockName(name);
        setHardness(0.5F);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        if (this == BlockListMF.lowroad)
            return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + 1, y + 0.5, z + 1);

        return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + 1, y + 1, z + 1);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return meta == 1 ? Blocks.sand.getIcon(side, 0) : Blocks.dirt.getIcon(side, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityRoad) {
            if (((TileEntityRoad) tile).surface[0] > 0) {
                Block surface = ((TileEntityRoad) tile).getBaseBlock();
                int surface_m = ((TileEntityRoad) tile).surface[1];
                return surface.getIcon(side, surface_m);
            }
        }
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
     * not to render the shared face of two adjacent blocks and also whether the
     * player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False
     * (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        this.updateTick(world, x, y, z, new Random());
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.getBlock(x, y - 1, z) == Blocks.grass) {
            world.setBlock(x, y - 1, z, Blocks.dirt, 0, 2);
        }
        super.updateTick(world, x, y, z, random);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1,
                                    float f2) {
        TileEntityRoad tile = getTile(world, x, y, z);
        if (tile == null)
            return false;

        boolean isLocked = tile.isLocked;
        ItemStack itemstack = player.getHeldItem();
        if (itemstack != null) {
            if (!player.canPlayerEdit(x, y, z, i, itemstack)) {
                return false;
            }
            if (!isLocked) {
                Block block = Block.getBlockFromItem(itemstack.getItem());
                if (itemstack.getItem() instanceof ItemBlock && block != null) {
                    if (upgradeRoad(world, x, y, z, 4, itemstack, block)) {
                        if (!player.capabilities.isCreativeMode && !world.isRemote) {
                            itemstack.stackSize--;
                            if (itemstack.stackSize <= 0) {
                                player.setCurrentItemOrArmor(0, null);
                            }
                        }
                        return true;
                    }
                }

                if (itemstack.getItem() instanceof ItemSpade) {
                    if (this == BlockListMF.road) {
                        if (!world.isRemote) {
                            int m = world.getBlockMetadata(x, y, z);
                            world.setBlock(x, y, z, BlockListMF.lowroad, m, 2);
                        }
                        return true;
                    }
                }
            }
            if (!world.isRemote && itemstack.getItem() instanceof ItemMattock) {
                toggleLocks(world, x, y, z, 4);
            }
        }
        return !tile.isLocked;
    }

    private TileEntityRoad getTile(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityRoad) {
            return (TileEntityRoad) tile;
        }
        return null;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        Block drop = meta == 1 ? Blocks.sand : Blocks.dirt;
        return Item.getItemFromBlock(drop);
    }

    /**
     * Resets the Texture
     *
     * @param ID the block right clicked with
     * @return
     */
    private boolean upgradeRoad(World world, int x, int y, int z, int r, ItemStack held, Block block) {
        if (!block.isNormalCube()) {
            return false;
        }
        if (held == null) {
            return false;
        }
        boolean flag = false;

        for (int x2 = -r; x2 <= r; x2++) {
            for (int y2 = -r; y2 <= r; y2++) {
                for (int z2 = -r; z2 <= r; z2++) {
                    Block id = world.getBlock(x + x2, y + y2, z + z2);
                    int m = world.getBlockMetadata(x + x2, y + y2, z + z2);
                    if ((id == BlockListMF.road || id == BlockListMF.lowroad)) {
                        if (getDistance(x + x2, y + y2, z + z2, x, y, z) < r) {
                            {
                                TileEntity tile = world.getTileEntity(x + x2, y + y2, z + z2);
                                if (tile != null && tile instanceof TileEntityRoad
                                        && !((TileEntityRoad) tile).isLocked) {
                                    flag = true;
                                    ((TileEntityRoad) tile).setSurface(block, held.getItemDamage());
                                }
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    private boolean toggleLocks(World world, int x, int y, int z, int r) {
        boolean flag = false;
        TileEntityRoad tile = getTile(world, x, y, z);
        if (tile == null) {
            return false;
        }
        flag = !tile.isLocked;
        tile.isLocked = flag;
        tile.sendPacketToClients();

        for (int x2 = -r; x2 <= r; x2++) {
            for (int y2 = -r; y2 <= r; y2++) {
                for (int z2 = -r; z2 <= r; z2++) {
                    if (getDistance(x + x2, y + y2, z + z2, x, y, z) < r) {
                        TileEntityRoad tile2 = getTile(world, x + x2, y + y2, z + z2);
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
        return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityRoad();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
    }
}
