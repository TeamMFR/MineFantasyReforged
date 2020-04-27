package minefantasy.mfr.block.refining;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.tile.TileEntityCrucible;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.item.ItemFilledMould;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCrucible extends BlockContainer {
    private static boolean keepInventory;
    public final boolean isActive;
    public int tier;
    public String type;
    public boolean isAuto;
    private Random rand = new Random();

    public BlockCrucible(String tex, int tier, boolean isActive) {
        super(Material.ROCK);
        this.tier = tier;
        this.type = tex;
        this.isActive = isActive;
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName( "MF_Crucible" + tex + (isActive ? "Active" : ""));
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + "crucible." + tex);
        this.setSoundType(SoundType.STONE);
        this.setHardness(8F);
        this.setResistance(8F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    private static TileEntityCrucible getTile(IBlockAccess world, BlockPos pos) {
        return (TileEntityCrucible) world.getTileEntity(pos);
    }

    public static void updateFurnaceBlockState(boolean state, World world, BlockPos pos) {
        TileEntityCrucible tileentity = getTile(world, pos);
        keepInventory = true;
        Block block = world.getBlockState(pos).getBlock();

        if (block != null && block instanceof BlockCrucible) {
            int blocktier = ((BlockCrucible) block).tier;
            boolean auto = ((BlockCrucible) block).isAuto;
            if (state) {
                world.setBlockState(pos, (getActiveBlock(blocktier, auto)).getDefaultState());
            } else {
                world.setBlockState(pos, (getInactiveBlock(blocktier, auto)).getDefaultState());
            }
        }

        keepInventory = false;
        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

    private static Block getActiveBlock(int tier, boolean auto) {
        if (tier == 1) {
            return auto ? BlockListMFR.CRUCIBLE_AUTO_ACTIVE : BlockListMFR.CRUCIBLE_ADV_ACTIVE;
        }
        if (tier == 2) {
            return BlockListMFR.CRUCIBLE_MYTHIC_ACTIVE;
        }
        if (tier == 3) {
            return BlockListMFR.CRUCIBLE_MASTER_ACTIVE;
        }
        return BlockListMFR.CRUCIBLE_ACTIVE;
    }

    private static Block getInactiveBlock(int tier, boolean auto) {
        if (tier == 1) {
            return auto ? BlockListMFR.CRUCIBLE_AUTO : BlockListMFR.CRUCIBLE_ADV;
        }
        if (tier == 2) {
            return BlockListMFR.CRUCIBLE_MYTHIC;
        }
        if (tier == 3) {
            return BlockListMFR.CRUCIBLE_MASTER;
        }
        return BlockListMFR.CRUCIBLE;
    }

    public BlockCrucible setAuto() {
        isAuto = true;
        return this;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> item) {
        if (!isActive) {
            super.getSubBlocks(tab, item);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCrucible();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (keepInventory)
            return;

        TileEntityCrucible tile = getTile(world, pos);

        if (tile != null) {
            int size = (!isAuto && ConfigHardcore.HCCreduceIngots) ? tile.getSizeInventory() - 1
                    : tile.getSizeInventory();
            for (int i1 = 0; i1 < size; ++i1) {
                ItemStack itemstack = tile.getStackInSlot(i1);

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
                            entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                        entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                        world.spawnEntity(entityitem);
                    }
                }
            }
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityCrucible tile = getTile(world, pos);
        if (tile != null) {
            ItemStack held = user.getHeldItem(hand);
            if (held != null && held.getItem() == ComponentListMFR.artefacts && held.getItemDamage() == 3) {
                if (tier == 2 && isActive) {
                    held.shrink(1);
                    if (held.getCount() <= 0) {
                        user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                    }
                    if (!world.isRemote) {
                        world.spawnEntity(new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, true));
                        world.setBlockState(pos, (BlockListMFR.CRUCIBLE_MASTER_ACTIVE).getDefaultState(), 2);
                    }
                }
                return true;
            }
            if (tier >= 2) {
                BlockCrucible.updateFurnaceBlockState(tile.isCoated(), world, pos);
            }
            ItemStack out = tile.getStackInSlot(tile.getSizeInventory() - 1);
            if (held != null && held.getItem() == ComponentListMFR.ingot_mould && out != null
                    && !(out.getItem() instanceof ItemBlock)) {
                ItemStack result = out.copy();
                result.setCount(1);
                tile.decrStackSize(tile.getSizeInventory() - 1, 1);

                ItemStack mould = ItemFilledMould.createMould(result);
                if (held.getCount() == 1) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, mould);
                } else {
                   held.shrink(1);
                    if (!world.isRemote) {
                        EntityItem drop = new EntityItem(world, user.posX, user.posY, user.posZ, mould);
                        drop.setPickupDelay(0);
                        world.spawnEntity(drop);
                    }
                }
                return true;
            }
            if (!world.isRemote) {
                user.openGui(MineFantasyReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(getInactiveBlock(tier, isAuto));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(getInactiveBlock(tier, isAuto));
    }
}
