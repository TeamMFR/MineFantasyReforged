package minefantasy.mfr.block.food;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.item.food.ItemFoodMF;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCakeMF extends Block {
    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 8);
    protected static final AxisAlignedBB[] CAKE_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.8D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.925D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D),
            new AxisAlignedBB(0.25D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D)};
    protected float height = 0.5F;
    protected float width = 14F / 16F;
    private Item cakeSlice;

    public BlockCakeMF(String name, Item slice) {
        super(Material.CAKE);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        cakeSlice = slice;
        this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, Integer.valueOf(0)));
        this.setTickRandomly(true);
        setCreativeTab(CreativeTabMFR.tabFood);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CAKE_AABB[((Integer)state.getValue(BITES)).intValue()];
    }

    public boolean isFullCube(IBlockState state) {
        return false;
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

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand,
            EnumFacing side, float hitX, float hitY, float hitZ) {
        if (ToolHelper.getCrafterTool(user.getHeldItem(EnumHand.MAIN_HAND)).equalsIgnoreCase("knife")) {
            this.cutSlice(world, state, pos, user);
            return true;
        }
        return false;
    }

    /**
     * Called when a player hits the block. Args: world, x, y, z, player
     */
    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
        if (ToolHelper.getCrafterTool(user.getHeldItem(EnumHand.MAIN_HAND)).equalsIgnoreCase("knife")) {
            IBlockState state = world.getBlockState(pos);
            this.cutSlice(world, state, pos, user);
        }
    }

    private void cutSlice(World world, IBlockState state, BlockPos pos, EntityPlayer user) {
        if (cakeSlice != null) {
            ItemStack slice = new ItemStack(cakeSlice);
            if (!user.inventory.addItemStackToInventory(slice)) {
                user.entityDropItem(slice, 1.0F);
            }
        }
        int i = ((Integer)state.getValue(BITES)).intValue();

        if (i >= 8) {
            world.setBlockToAir(pos);
        } else {
            world.setBlockState(pos, state.withProperty(BITES, Integer.valueOf(i + 1)), 3);
        }
        if (user.getHeldItem(EnumHand.MAIN_HAND) != null) {
            user.getHeldItem(EnumHand.MAIN_HAND).damageItem(1, user);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates.
     * Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return !super.canPlaceBlockAt(world, pos) ? false : this.canBlockStay(world, pos);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor
     * Block
     */
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(world, pos)) {
            ItemStack item = new ItemStack(this, 1, damageDropped(world.getBlockState(pos)));
            EntityItem drop = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, item);
            world.spawnEntity(drop);
            world.setBlockToAir(pos);
        }
    }

    /**
     * Can this block stay at this position. Similar to canPlaceBlockAt except gets
     * checked often with plants.
     */

    public boolean canBlockStay(World world, BlockPos pos) {
        return world.getBlockState(pos.add(0,-1,0)).getMaterial().isSolid();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    public int getRarity() {
        if (cakeSlice instanceof ItemFoodMF) {
            return ((ItemFoodMF) cakeSlice).itemRarity;
        }
        return 0;
    }

    public Block setCheese() {
        width = 0.5F;
        height = 5F / 16F;
        return this;
    }
}