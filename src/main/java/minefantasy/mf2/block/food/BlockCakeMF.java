package minefantasy.mf2.block.food;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.item.food.ItemFoodMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCakeMF extends Block {
    protected int maxSlices = 8;
    protected float height = 0.5F;
    protected float width = 14F / 16F;
    @SideOnly(Side.CLIENT)
    private IIcon topTex;
    @SideOnly(Side.CLIENT)
    private IIcon bottomTex;
    @SideOnly(Side.CLIENT)
    private IIcon insideTex;
    private Item cakeSlice;

    public BlockCakeMF(String name, Item slice) {
        super(Material.cake);
        GameRegistry.registerBlock(this, ItemBlockCake.class, name);
        setBlockName(name);
        setBlockTextureName("minefantasy2:food/" + name);
        cakeSlice = slice;
        this.setTickRandomly(true);
        setCreativeTab(CreativeTabMF.tabFood);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int slices = world.getBlockMetadata(x, y, z);
        float border = (1F - width) / 2;
        float size = (slices / (float) maxSlices) * (1.0F - (border * 2));
        this.setBlockBounds(border + size, 0.0F, border, 1.0F - border, height, 1.0F - border);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    @Override
    public void setBlockBoundsForItemRender() {
        float f = (1F - width) / 2;
        float f1 = height;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box
     * can change after the pool has been cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        int slices = world.getBlockMetadata(x, y, z);
        float border = (1F - width) / 2;
        float size = border + (slices / (float) maxSlices) * (1.0F - (border * 2));
        return AxisAlignedBB.getBoundingBox(x + size, y, z + border, x + 1 - border, y + height - border,
                z + 1 - border);
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        int slices = world.getBlockMetadata(x, y, z);
        float border = (1F - width) / 2;
        float size = border + (slices / (float) maxSlices) * (1.0F - (border * 2));
        return AxisAlignedBB.getBoundingBox(x + size, y, z + border, x + 1 - border, y + height, z + 1 - border);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.topTex
                : (side == 0 ? this.bottomTex : (meta > 0 && side == 4 ? this.insideTex : this.blockIcon));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(this.getTextureName() + "_side");
        this.insideTex = reg.registerIcon(this.getTextureName() + "_inner");
        this.topTex = reg.registerIcon(this.getTextureName() + "_top");
        this.bottomTex = reg.registerIcon(this.getTextureName() + "_bottom");
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
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xoffset,
                                    float yoffset, float zoffset) {
        if (ToolHelper.getCrafterTool(user.getHeldItem()).equalsIgnoreCase("knife")) {
            this.cutSlice(world, x, y, z, user);
            return true;
        }
        return false;
    }

    /**
     * Called when a player hits the block. Args: world, x, y, z, player
     */
    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer user) {
        if (ToolHelper.getCrafterTool(user.getHeldItem()).equalsIgnoreCase("knife")) {
            this.cutSlice(world, x, y, z, user);
        }
    }

    private void cutSlice(World world, int x, int y, int z, EntityPlayer user) {
        if (cakeSlice != null) {
            ItemStack slice = new ItemStack(cakeSlice);
            if (!user.inventory.addItemStackToInventory(slice)) {
                user.entityDropItem(slice, 1.0F);
            }
        }
        int l = world.getBlockMetadata(x, y, z) + 1;

        if (l >= maxSlices) {
            world.setBlockToAir(x, y, z);
        } else {
            world.setBlockMetadataWithNotify(x, y, z, l, 2);
        }
        if (user.getHeldItem() != null) {
            user.getHeldItem().damageItem(1, user);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates.
     * Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return !super.canPlaceBlockAt(world, x, y, z) ? false : this.canBlockStay(world, x, y, z);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor
     * Block
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!this.canBlockStay(world, x, y, z)) {
            ItemStack item = new ItemStack(this, 1, damageDropped(world.getBlockMetadata(x, y, z)));
            EntityItem drop = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, item);
            world.spawnEntityInWorld(drop);
            world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Can this block stay at this position. Similar to canPlaceBlockAt except gets
     * checked often with plants.
     */
    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z).getMaterial().isSolid();
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
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