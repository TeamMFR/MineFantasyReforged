package minefantasy.mf2.block.decor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.mechanics.PlayerTickHandlerMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Iterator;
import java.util.Random;

public class BlockBedMF extends BlockDirectional {
    public static final int[][] field_149981_a = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    @SideOnly(Side.CLIENT)
    private IIcon[] endTex;
    @SideOnly(Side.CLIENT)
    private IIcon[] sideTex;
    @SideOnly(Side.CLIENT)
    private IIcon[] topTex;
    @SideOnly(Side.CLIENT)
    private IIcon baseTex;

    private float height = 0.25F;

    public BlockBedMF(String name) {
        super(Material.cloth);
        this.setBlockName(name);
        GameRegistry.registerBlock(this, name + "_block");
        setBlockTextureName("minefantasy2:furnishings/" + name);
        this.resetBounds();
    }

    /**
     * Returns whether or not this bed block is the head of the bed.
     */
    public static boolean isBlockHeadOfBed(int meta) {
        return (meta & 8) != 0;
    }

    public static boolean func_149976_c(int meta) {
        return (meta & 4) != 0;
    }

    public static void setState(World world, int x, int y, int z, boolean flag) {
        int l = world.getBlockMetadata(x, y, z);

        if (flag) {
            l |= 4;
        } else {
            l &= -5;
        }

        world.setBlockMetadataWithNotify(x, y, z, l, 4);
    }

    public static ChunkCoordinates findBedside(World world, int x, int y, int z, int counter) {
        int i1 = world.getBlockMetadata(x, y, z);
        int j1 = BlockDirectional.getDirection(i1);

        for (int k1 = 0; k1 <= 1; ++k1) {
            int l1 = x - field_149981_a[j1][0] * k1 - 1;
            int i2 = z - field_149981_a[j1][1] * k1 - 1;
            int j2 = l1 + 2;
            int k2 = i2 + 2;

            for (int l2 = l1; l2 <= j2; ++l2) {
                for (int i3 = i2; i3 <= k2; ++i3) {
                    if (World.doesBlockHaveSolidTopSurface(world, l2, y - 1, i3)
                            && !world.getBlock(l2, y, i3).getMaterial().isOpaque()
                            && !world.getBlock(l2, y + 1, i3).getMaterial().isOpaque()) {
                        if (counter <= 0) {
                            return new ChunkCoordinates(l2, y, i3);
                        }

                        --counter;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int meta, float fx, float fy,
                                    float fz) {
        if (world.isRemote) {
            return true;
        } else {
            int i1 = world.getBlockMetadata(x, y, z);

            if (!isBlockHeadOfBed(i1)) {
                int j1 = getDirection(i1);
                x += field_149981_a[j1][0];
                z += field_149981_a[j1][1];

                if (world.getBlock(x, y, z) != this) {
                    return true;
                }

                i1 = world.getBlockMetadata(x, y, z);
            }

            if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell) {
                if (func_149976_c(i1)) {
                    EntityPlayer entityplayer1 = null;
                    Iterator iterator = world.playerEntities.iterator();

                    while (iterator.hasNext()) {
                        EntityPlayer entityplayer2 = (EntityPlayer) iterator.next();

                        if (entityplayer2.isPlayerSleeping()) {
                            ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

                            if (chunkcoordinates.posX == x && chunkcoordinates.posY == y
                                    && chunkcoordinates.posZ == z) {
                                entityplayer1 = entityplayer2;
                            }
                        }
                    }

                    if (entityplayer1 != null) {
                        user.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                        return true;
                    }

                    setState(world, x, y, z, false);
                }

                EntityPlayer.EnumStatus enumstatus = user.sleepInBedAt(x, y, z);

                if (enumstatus == EntityPlayer.EnumStatus.OK) {
                    setState(world, x, y, z, true);
                    PlayerTickHandlerMF.readyToResetBedPosition(user);
                    return true;
                } else {
                    if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
                        user.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
                    } else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
                        user.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
                    }

                    return true;
                }
            } else {
                double d2 = x + 0.5D;
                double d0 = y + 0.5D;
                double d1 = z + 0.5D;
                world.setBlockToAir(x, y, z);
                int k1 = getDirection(i1);
                x += field_149981_a[k1][0];
                z += field_149981_a[k1][1];

                if (world.getBlock(x, y, z) == this) {
                    world.setBlockToAir(x, y, z);
                    d2 = (d2 + x + 0.5D) / 2.0D;
                    d0 = (d0 + y + 0.5D) / 2.0D;
                    d1 = (d1 + z + 0.5D) / 2.0D;
                }

                world.newExplosion((Entity) null, x + 0.5F, y + 0.5F, z + 0.5F, 5.0F, true, true);
                return true;
            }
        }
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 0) {
            return baseTex;
        } else {
            int k = getDirection(meta);
            int l = Direction.bedDirection[k][side];
            int i1 = isBlockHeadOfBed(meta) ? 1 : 0;
            return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? this.topTex[i1] : this.sideTex[i1])
                    : this.endTex[i1];
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.topTex = new IIcon[]{register.registerIcon(this.getTextureName() + "_feet_top"),
                register.registerIcon(this.getTextureName() + "_head_top")};
        this.endTex = new IIcon[]{register.registerIcon(this.getTextureName() + "_feet_end"),
                register.registerIcon(this.getTextureName() + "_head_end")};
        this.sideTex = new IIcon[]{register.registerIcon(this.getTextureName() + "_feet_side"),
                register.registerIcon(this.getTextureName() + "_head_side")};
        this.baseTex = register.registerIcon(this.getTextureName() + "_base");
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 14;
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
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
     * not to render the shared face of two adjacent blocks and also whether the
     * player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.resetBounds();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor
     * Block
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour) {
        int l = world.getBlockMetadata(x, y, z);
        int i1 = getDirection(l);

        if (isBlockHeadOfBed(l)) {
            if (world.getBlock(x - field_149981_a[i1][0], y, z - field_149981_a[i1][1]) != this) {
                world.setBlockToAir(x, y, z);
            }
        } else if (world.getBlock(x + field_149981_a[i1][0], y, z + field_149981_a[i1][1]) != this) {
            world.setBlockToAir(x, y, z);

            if (!world.isRemote) {
                this.dropBlockAsItem(world, x, y, z, l, 0);
            }
        }
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        /**
         * Returns whether or not this bed block is the head of the bed.
         */
        return isBlockHeadOfBed(meta) ? Item.getItemById(0) : ToolListMF.bedroll;
    }

    private void resetBounds() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, height, 1.0F);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float f, int i) {
        if (!isBlockHeadOfBed(meta)) {
            super.dropBlockAsItemWithChance(world, x, y, z, meta, f, 0);
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but
     * can move over, 2 = total immobility and stop pistons
     */
    @Override
    public int getMobilityFlag() {
        return 1;
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, int x, int y, int z) {
        return ToolListMF.bedroll;
    }

    /**
     * Called when the block is attempted to be harvested
     */
    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer user) {
        if (user.capabilities.isCreativeMode && isBlockHeadOfBed(meta)) {
            int i1 = getDirection(meta);
            x -= field_149981_a[i1][0];
            z -= field_149981_a[i1][1];

            if (world.getBlock(x, y, z) == this) {
                world.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player) {
        return true;
    }

}