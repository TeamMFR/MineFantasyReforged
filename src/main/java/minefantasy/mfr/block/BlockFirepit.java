package minefantasy.mfr.block;

import minefantasy.mfr.api.tool.ILighter;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.tile.TileEntityFirepit;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockFirepit extends BlockTileEntity<TileEntityFirepit> {
    private static final PropertyBool BURNING = PropertyBool.create("burning");
    private static final PropertyBool PLANKS = PropertyBool.create("planks");
    private static final PropertyBool UNDER = PropertyBool.create("under");

    private Random rand = new Random();
    AxisAlignedBB AABB = new AxisAlignedBB(0.2F, 0F, 0.2F, 0.8F, 0.5F, 0.8F);

    public BlockFirepit() {
        super(Material.WOOD);
        String name = "firepit";

        setRegistryName(name);
        setUnlocalizedName(name);
        this.setLightOpacity(0);
        setHardness(2F);
        this.setCreativeTab(MineFantasyTabs.tabUtil);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING, PLANKS, UNDER);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityFirepit();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityFirepit tile = (TileEntityFirepit) getTile(world, pos);
        return state.withProperty(BURNING, tile.isBurning()).withProperty(PLANKS, tile.fuel > 0).withProperty(UNDER, tile.hasBlockAbove());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    public static void setActiveState(boolean burning, boolean planks, boolean under, World world, BlockPos pos){
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BURNING, burning).withProperty(PLANKS, planks).withProperty(UNDER, under));
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        return getDefaultState().withProperty(BURNING, false).withProperty(PLANKS, false).withProperty(UNDER, false);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityFirepit tile = (TileEntityFirepit) getTile(world, pos);
        if (tile != null){
            setActiveState(tile.isBurning(),tile.fuel > 0, tile.hasBlockAbove(), world, pos);
        }
    }

    @Override
    public int quantityDropped(Random rand) {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityFirepit firepit = (TileEntityFirepit) world.getTileEntity(pos);

        if (firepit != null) {
            ItemStack held = player.getHeldItemMainhand();
            boolean burning = firepit.isBurning();

            if (!held.isEmpty()) {
                if (firepit.addFuel(held) && !player.capabilities.isCreativeMode) {
                    if (!world.isRemote) {
                        if (held.getCount() == 1) {
                            if (!held.getItem().getContainerItem(held).isEmpty()) {
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, held.getItem().getContainerItem(held));
                            } else {
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                            }
                        } else {
                            held.shrink(1);
                            if (!held.getItem().getContainerItem(held).isEmpty()) {
                                if (!player.inventory.addItemStackToInventory(held.getItem().getContainerItem(held))) {
                                    player.entityDropItem(held.getItem().getContainerItem(held), 0F);
                                }
                            }
                        }
                    }
                    return true;
                }

                if (burning) {
                    if (firepit.tryCook(player, held) && !player.capabilities.isCreativeMode) {
                        ItemStack contain = held.getItem().getContainerItem(held);
                        held.shrink(1);
                        if (held.getCount() <= 0) {
                            if (!contain.isEmpty()) {
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, contain);
                            } else
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                        } else if (!contain.isEmpty()) {
                            firepit.dropItem(player, contain);
                        }
                    }
                    return true;
                } else if (firepit.fuel > 0) {
                    if (held.getItem() instanceof ILighter) {
                        world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 1.0F, rand.nextFloat() * 0.4F + 0.8F );
                        world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, 0F, 0.1F, 0F);

                        ILighter lighter = (ILighter) held.getItem();
                        if (lighter.canLight()) {
                            if (rand.nextDouble() < lighter.getChance()) {
                                if (!world.isRemote) {
                                    firepit.setLit(true);
                                    held.damageItem(1, player);
                                }
                            }
                            return true;
                        }
                    }
                    if (held.getItem() instanceof ItemFlintAndSteel) {
                        world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 1.0F, rand.nextFloat() * 0.4F + 0.8F );
                        world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, 0F, 0F, 0F);

                        if (!world.isRemote) {
                            firepit.setLit(true);
                            held.damageItem(1, player);
                        }
                        return true;
                    }
                }
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    public boolean getBurningValue(IBlockState state){
        return state.getValue(BURNING);
    }


    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos){
        return AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (!state.getValue(BURNING)) {
            return;
        }

        if (rand.nextInt(10) == 0) {
            world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }

        for (int i = 0; i < 3; ++i) {
            double x = pos.getX() + 0.25D + rand.nextDouble() * 0.5D;
            double y = pos.getY() + rand.nextDouble() * 0.5D;
            double z = pos.getZ() + 0.25D + rand.nextDouble() * 0.5D;
            world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (world.isRemote) {
            return;
        }
        TileEntityFirepit tile = (TileEntityFirepit) world.getTileEntity(pos);
        if (entity == null || tile == null || tile.hasBlockAbove()) {
            return;
        }
        if (entity instanceof EntityItem) {
            entity.motionX = entity.motionY = entity.motionZ = 0;
            entity.posX = pos.getX() + 0.5D;
            entity.posY = pos.getY() + 0.75D;
            entity.posZ = pos.getZ() + 0.5D;
        } else {
            if (tile.isBurning()) {
                entity.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
                entity.setFire(2);
            }
        }
    }
}
