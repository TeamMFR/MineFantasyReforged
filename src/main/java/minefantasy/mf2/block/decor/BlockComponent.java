package minefantasy.mf2.block.decor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockComponent extends BlockContainer {
    public static int component_RI = 118;
    private Random rand = new Random();

    public BlockComponent() {
        super(Material.circuits);
        GameRegistry.registerBlock(this, ItemBlockAmmoBox.class, "MF_ComponentStorage");
        setBlockName("");
        this.setHardness(1F);
        this.setResistance(1F);
        this.setBlockBounds(1 / 16F, 0F, 1 / 16F, 15 / 16F, 12 / 16F, 15 / 16F);
    }

    private static TileEntityComponent getTile(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityComponent) {
            return (TileEntityComponent) tile;
        }
        return null;
    }

    public static int placeComponent(EntityPlayer user, ItemStack item, World world, int x, int y, int z, String type,
                                     String tex, int dir) {
        if (world.isAirBlock(x, y, z) && canBuildOn(world, x, y - 1, z)) {
            world.setBlock(x, y, z, BlockListMF.components, dir, 2);

            int max = getStorageSize(type);
            int size = user.isSneaking() ? Math.min(item.stackSize, max) : 1;

            TileEntityComponent tile = new TileEntityComponent();
            ItemStack newitem = item.copy();
            newitem.stackSize = 1;
            tile.setItem(newitem, type, tex, max, size);
            world.setTileEntity(x, y, z, tile);
            return size;
        }
        return 0;
    }

    public static boolean canBuildOn(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityComponent) {
            return ((TileEntityComponent) tile).isFull();
        }
        return world.isSideSolid(x, y, z, ForgeDirection.UP);
    }

    public static int useComponent(ItemStack item, String type, String tex, World world, EntityPlayer user,
                                   MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;

            if (movingobjectposition.sideHit == 0) {
                --j;
            }

            if (movingobjectposition.sideHit == 1) {
                ++j;
            }

            if (movingobjectposition.sideHit == 2) {
                --k;
            }

            if (movingobjectposition.sideHit == 3) {
                ++k;
            }

            if (movingobjectposition.sideHit == 4) {
                --i;
            }

            if (movingobjectposition.sideHit == 5) {
                ++i;
            }

            if (user.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {
                int l = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
                return placeComponent(user, item, user.worldObj, i, j, k, type, tex, l);
            }
        }
        return 0;
    }

    public static int getStorageSize(String id) {
        if (id == null)
            return 0;

        if (id.equalsIgnoreCase("bar"))
            return 64;
        if (id.equalsIgnoreCase("plank"))
            return 64;
        if (id.equalsIgnoreCase("pot"))
            return 64;
        if (id.equalsIgnoreCase("jug"))
            return 32;
        if (id.equalsIgnoreCase("sheet"))
            return 16;
        if (id.equalsIgnoreCase("bigplate"))
            return 8;

        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.iron_block.getIcon(side, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {

    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return component_RI;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item) {
        int direction = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityComponent();
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer user) {
        useBlock(world, x, y, z, user, true);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        useBlock(world, x, y, z, user, false);
        return true;
    }

    private void useBlock(World world, int x, int y, int z, EntityPlayer user, boolean leftClick) {
        ItemStack held = user.getHeldItem();
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityComponent) {
            ((TileEntityComponent) tile).interact(user, held, leftClick);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityComponent) {
            ((TileEntityComponent) tile).checkStack();
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityComponent) {
            if (((TileEntityComponent) tile).item != null) {
                ItemStack item = ((TileEntityComponent) tile).item.copy();
                item.stackSize = 1;
                return item;
            }
        }
        return null;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        return null;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityComponent) {
            TileEntityComponent component = (TileEntityComponent) tile;

            if (component.item != null) {
                while (component.stackSize > 0) {
                    ItemStack drop = component.item.copy();
                    int count = Math.min(component.stackSize, drop.getMaxStackSize());
                    drop.stackSize = count;
                    component.stackSize -= count;
                    dropItem(world, x, y, z, drop);
                }
            }
        }
    }

    private void dropItem(World world, int x, int y, int z, ItemStack itemstack) {
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
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                world.spawnEntityInWorld(entityitem);
            }
        }
    }

    private AxisAlignedBB getBoundingBox(World world, int x, int y, int z) {
        float height = 1.0F;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityComponent) {
            height = ((TileEntityComponent) tile).getBlockHeight();
        }
        return AxisAlignedBB.getBoundingBox(x + 0.0625D, y + 0D, z + 0.0625D, x + 0.9375D, y + height, z + 0.9375D);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return getBoundingBox(world, x, y, z);
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return getBoundingBox(world, x, y, z);
    }
}
