package minefantasy.mf2.block.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.heating.TongsHelper;
import minefantasy.mf2.block.tileentity.TileEntityAnvilMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.tool.crafting.ItemTongs;
import minefantasy.mf2.material.BaseMaterialMF;
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

public class BlockAnvilMF extends BlockContainer {
    public static int anvil_RI = 100;

    @SideOnly(Side.CLIENT)
    public int anvilRenderSide;
    public BaseMaterialMF material;
    private int tier;
    private Random rand = new Random();

    public BlockAnvilMF(BaseMaterialMF material) {
        super(Material.anvil);
        String name = material.name;
        this.material = material;
        float height = 1.0F / 16F * 13F;
        setBlockBounds(0F, 0F, 0F, 1F, height, 1F);

        this.setBlockTextureName("minefantasy2:metal/" + name.toLowerCase() + "_block");
        name = "anvil" + name;
        this.tier = material.tier;
        GameRegistry.registerBlock(this, ItemBlockAnvilMF.class, name);
        setBlockName(name);
        this.setStepSound(Block.soundTypeMetal);
        this.setHardness(material.hardness + 1 / 2F);
        this.setResistance(material.hardness + 1);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMF.tabUtil);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item) {
        int dir = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        ItemStack held = user.getHeldItem();
        TileEntityAnvilMF tile = getTile(world, x, y, z);
        if (tile != null) {
            if (side == 1 && held != null && held.getItem() instanceof ItemTongs
                    && onUsedTongs(world, user, held, tile)) {
                return true;
            }
            if (side != 1 || !tile.tryCraft(user, true) && !world.isRemote) {
                user.openGui(MineFantasyII.instance, 0, world, x, y, z);
            }
        }
        return true;
    }

    private boolean onUsedTongs(World world, EntityPlayer user, ItemStack held, TileEntityAnvilMF tile) {
        ItemStack result = tile.getStackInSlot(tile.getSizeInventory() - 1);
        ItemStack grabbed = TongsHelper.getHeldItem(held);

        // GRAB
        if (grabbed == null) {
            if (result != null && result.getItem() == ComponentListMF.hotItem) {
                if (TongsHelper.trySetHeldItem(held, result)) {
                    tile.setInventorySlotContents(tile.getSizeInventory() - 1, null);
                    return true;
                }
            }
        } else {
            for (int s = 0; s < (tile.getSizeInventory() - 1); s++) {
                ItemStack slot = tile.getStackInSlot(s);
                if (slot == null) {
                    tile.setInventorySlotContents(s, grabbed);
                    TongsHelper.clearHeldItem(held, user);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer user) {
        {
            TileEntityAnvilMF tile = getTile(world, x, y, z);
            if (tile != null) {
                if (user.isSneaking()) {
                    tile.upset(user);
                } else {
                    tile.tryCraft(user, false);
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAnvilMF(tier, material.name);
    }

    private TileEntityAnvilMF getTile(World world, int x, int y, int z) {
        return (TileEntityAnvilMF) world.getTileEntity(x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityAnvilMF tile = getTile(world, x, y, z);

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

    public int getTier() {
        return tier;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (tier == 0) {
            return side == 1 ? Blocks.stone.getIcon(side, meta) : Blocks.cobblestone.getIcon(side, meta);
        }
        return super.getIcon(side, meta);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        if (tier != 0)
            super.registerBlockIcons(reg);
    }

    @Override
    public int getRenderType() {
        return anvil_RI;
    }
}