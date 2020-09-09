package minefantasy.mfr.block.decor;

import minefantasy.mfr.tile.TileEntityComponent;
import minefantasy.mfr.init.BlockListMFR;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockComponent extends BlockContainer {
    public static int component_RI = 118;
    private Random rand = new Random();

    public BlockComponent() {
        super(Material.CIRCUITS);

        setRegistryName("");
        setUnlocalizedName("MF_ComponentStorage");
        this.setHardness(1F);
        this.setResistance(1F);
        new AxisAlignedBB(1 / 16F, 0F, 1 / 16F, 15 / 16F, 12 / 16F, 15 / 16F);
    }

    private static TileEntityComponent getTile(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityComponent) {
            return (TileEntityComponent) tile;
        }
        return null;
    }

    public static int placeComponent(EntityPlayer user, ItemStack item, World world, BlockPos pos, String type, String tex, int dir) {
        if (world.isAirBlock(pos) && canBuildOn(world, pos.add(0,-1,0))) {
            world.setBlockState(pos, (IBlockState) BlockListMFR.COMPONENTS, 2);

            int max = getStorageSize(type);
            int size = user.isSneaking() ? Math.min(item.getCount(), max) : 1;

            TileEntityComponent tile = new TileEntityComponent();
            ItemStack newitem = item.copy();
            newitem.setCount(1);
            tile.setItem(newitem, type, tex, max, size);
            world.setTileEntity(pos, tile);
            return size;
        }
        return 0;
    }

    public static boolean canBuildOn(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityComponent) {
            return ((TileEntityComponent) tile).isFull();
        }
        return world.isSideSolid(pos, EnumFacing.UP);
    }

    public static int useComponent(ItemStack item, String type, String tex, World world, EntityPlayer user, RayTraceResult movingobjectposition) {
        if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK) {
           BlockPos hit = movingobjectposition.getBlockPos();

            if (movingobjectposition.sideHit == EnumFacing.UP) {
                hit.add(1,0,0);
            }

            if (movingobjectposition.sideHit == EnumFacing.DOWN) {
                hit.add(0,1,0);
            }

            if (movingobjectposition.sideHit == EnumFacing.NORTH) {
                hit.add(0,0,1);
            }

            if (movingobjectposition.sideHit == EnumFacing.SOUTH) {
                hit.add(0,0,1);
            }

            if (movingobjectposition.sideHit == EnumFacing.EAST) {
                hit.add(0,1,0);
            }

            if (movingobjectposition.sideHit == EnumFacing.WEST) {
                hit.add(1,0,0);
            }

            if (user.canPlayerEdit(hit, movingobjectposition.sideHit, item)) {
                int l = MathHelper.floor(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
                return placeComponent(user, item, user.world, hit, type, tex, l);
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
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }


    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return component_RI;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
        world.setBlockState(pos, state, 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityComponent();
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
        useBlock(world, pos, user, true);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        useBlock(world, pos, user, false);
        return true;
    }

    private void useBlock(World world, BlockPos pos, EntityPlayer user, boolean leftClick) {
        ItemStack held = user.getHeldItem(EnumHand.MAIN_HAND);
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityComponent) {
            ((TileEntityComponent) tile).interact(user, held, leftClick);
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityComponent) {
            ((TileEntityComponent) tile).checkStack();
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityComponent) {
            if (((TileEntityComponent) tile).item != null) {
                ItemStack item = ((TileEntityComponent) tile).item.copy();
                item.setCount(1);
                return item;
            }
        }
        return null;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityComponent) {
            TileEntityComponent component = (TileEntityComponent) tile;

            if (component.item != null) {
                while (component.stackSize > 0) {
                    ItemStack drop = component.item.copy();
                    int count = Math.min(component.stackSize, drop.getMaxStackSize());
                    drop.setCount(count);
                    component.stackSize -= count;
                    dropItem(world, pos, drop);
                }
            }
        }
    }

    private void dropItem(World world, BlockPos pos, ItemStack itemstack) {
        if (itemstack != null) {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.getCount() > 0) {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.getCount()) {
                    j1 = itemstack.getCount();
                }

                itemstack.shrink(j1);
                EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2,
                        new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                if (itemstack.hasTagCompound()) {
                    entityitem.getItem().setTagCompound( itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                world.spawnEntity(entityitem);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        float height = 1.0F;
        TileEntity tile = source.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityComponent) {
            height = ((TileEntityComponent) tile).getBlockHeight();
        }
        return new AxisAlignedBB(pos.getX() + 0.0625D, pos.getY() + 0D, pos.getZ() + 0.0625D,
                pos.getX() + 0.9375D, pos.getY() + height, pos.getZ() + 0.9375D);
    }
}
