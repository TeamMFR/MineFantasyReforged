package minefantasy.mf2.block.decor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.network.packet.RackCommand;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.*;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class BlockRack extends BlockWoodDecor {
    public static int rack_RI = 115;

    public BlockRack(String name) {
        super(name);

        setHardness(1.0F);
        setResistance(1.0F);
        GameRegistry.registerBlock(this, ItemBlockToolRack.class, name);
        setBlockName(name);
        this.setCreativeTab(CreativeTabMF.tabUtil);
    }

    /**
     * Convert standard South, West, North, East to rack's (North, South, East,
     * West)
     */
    public static int getDirection(int dir) {
        int[] directions = new int[]{3, 4, 2, 5};
        return directions[Math.min(3, dir)];
    }

    public static boolean interact(int slot, World world, TileEntityRack tile, EntityPlayer player) {
        if (player.isSneaking()) {
            player.openGui(MineFantasyII.instance, 0, world, tile.xCoord, tile.yCoord, tile.zCoord);
            return false;
        }

        ItemStack held = player.getHeldItem();
        if (held == null) {
            ItemStack hung = tile.getStackInSlot(slot);
            if (hung != null) {
                if (!world.isRemote) {
                    player.setCurrentItemOrArmor(0, hung);
                    tile.setInventorySlotContents(slot, null);
                    tile.syncItems();
                }
                player.swingItem();
                return true;
            }
        } else {
            ItemStack hung = tile.getStackInSlot(slot);

            if (hung == null && tile.canHang(player.getHeldItem(), slot)) {
                if (!world.isRemote) {
                    tile.setInventorySlotContents(slot, player.getHeldItem().copy());
                    player.setCurrentItemOrArmor(0, null);
                    tile.syncItems();
                }
                player.swingItem();
                return true;
            } else if (held != null && hung != null) {
                if (hung.isItemEqual(held)) {
                    int space = hung.getMaxStackSize() - hung.stackSize;

                    if (held.stackSize > space) {
                        if (!world.isRemote) {
                            held.stackSize -= space;
                            hung.stackSize += space;
                        }
                        player.swingItem();
                        return true;
                    } else {
                        if (!world.isRemote) {
                            hung.stackSize += held.stackSize;
                            player.setCurrentItemOrArmor(0, null);
                        }
                        player.swingItem();
                        return true;
                    }
                }
            }
        }
        player.openGui(MineFantasyII.instance, 0, world, tile.xCoord, tile.yCoord, tile.zCoord);
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box
     * can change after the pool has been cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
                                                         int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_,
                                           int p_149719_4_) {
        this.modifyBoundingbox(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_,
                                                        int p_149633_4_) {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    public void modifyBoundingbox(int meta) {
        float f = 0.25F;

        if (meta == 2) {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }

        if (meta == 3) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }

        if (meta == 4) {
            this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (meta == 5) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
     * not to render the shared face of two adjacent blocks and also whether the
     * player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False
     * (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z,
     * side, hitX, hitY, hitZ, block metadata
     */

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return rack_RI;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates.
     * Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return p_149742_1_.isSideSolid(p_149742_2_ - 1, p_149742_3_, p_149742_4_, EAST)
                || p_149742_1_.isSideSolid(p_149742_2_ + 1, p_149742_3_, p_149742_4_, WEST)
                || p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_, p_149742_4_ - 1, SOUTH)
                || p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_, p_149742_4_ + 1, NORTH);
    }

    @Override
    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_,
                             float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        int j1 = p_149660_9_;

        if ((p_149660_9_ == 0 || p_149660_5_ == 2)
                && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_, p_149660_4_ + 1, NORTH)) {
            j1 = 2;
        }

        if ((j1 == 0 || p_149660_5_ == 3)
                && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_, p_149660_4_ - 1, SOUTH)) {
            j1 = 3;
        }

        if ((j1 == 0 || p_149660_5_ == 4) && p_149660_1_.isSideSolid(p_149660_2_ + 1, p_149660_3_, p_149660_4_, WEST)) {
            j1 = 4;
        }

        if ((j1 == 0 || p_149660_5_ == 5) && p_149660_1_.isSideSolid(p_149660_2_ - 1, p_149660_3_, p_149660_4_, EAST)) {
            j1 = 5;
        }

        return j1;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor
     * Block
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityRack) {
            ((TileEntityRack) tile).updateInventory();
        }
        int l = world.getBlockMetadata(x, y, z);
        boolean flag = false;

        if (l == 2 && world.isSideSolid(x, y, z + 1, NORTH)) {
            flag = true;
        }

        if (l == 3 && world.isSideSolid(x, y, z - 1, SOUTH)) {
            flag = true;
        }

        if (l == 4 && world.isSideSolid(x + 1, y, z, WEST)) {
            flag = true;
        }

        if (l == 5 && world.isSideSolid(x - 1, y, z, EAST)) {
            flag = true;
        }

        if (!flag) {
            this.dropBlockAsItem(world, x, y, z, l, 0);
            world.setBlockToAir(x, y, z);
        }

        super.onNeighborBlockChange(world, x, y, z, block);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.planks.getIcon(side, 0);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int m) {
        return new TileEntityRack();
    }

    /**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityRack tile = (TileEntityRack) world.getTileEntity(x, y, z);

        if (tile != null) {
            for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6) {
                ItemStack var7 = tile.getStackInSlot(var6);

                if (var7 != null) {
                    float var8 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float var9 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float var10 = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (var7.stackSize > 0) {
                        int var11 = world.rand.nextInt(21) + 10;

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
                        var12.motionX = (float) world.rand.nextGaussian() * var13;
                        var12.motionY = (float) world.rand.nextGaussian() * var13 + 0.2F;
                        var12.motionZ = (float) world.rand.nextGaussian() * var13;
                        world.spawnEntityInWorld(var12);
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int i, float f, float f1,
                                    float f2) {
        TileEntityRack tile = (TileEntityRack) world.getTileEntity(x, y, z);
        if (world.isRemote) {
            int slot = tile.getSlotFor(f, f2);
            if (slot >= 0 && slot < 4) {
                ((EntityClientPlayerMP) user).sendQueue
                        .addToSendQueue(new RackCommand(slot, user, tile).generatePacket());
            }
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
    }
}
