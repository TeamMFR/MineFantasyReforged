package minefantasy.mfr.block.crafting;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.tool.ILighter;
import minefantasy.mfr.block.tile.TileEntityFirepit;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockFirepit extends BlockContainer {

    public static int firepit_RI = 112;
    private Random rand = new Random();
    AxisAlignedBB BlockBB = new AxisAlignedBB(0.2F, 0F, 0.2F, 0.8F, 0.5F, 0.8F);

    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos){
        return BlockBB;
    }

    public BlockFirepit() {
        super(Material.WOOD);
        String name = "firepit";

        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        this.setLightOpacity(0);
        setHardness(2F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public int quantityDropped(Random rand) {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityFirepit firepit = (TileEntityFirepit) world.getTileEntity(pos);

        if (firepit != null) {
            ItemStack held = player.getHeldItem(hand);
            boolean burning = firepit.isBurning();

            if (held != null) {
                if (firepit.addFuel(held) && !player.capabilities.isCreativeMode) {
                    if (!world.isRemote) {
                        if (held.getCount() == 1) {
                            if (held.getItem().getContainerItem(held) != null) {
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, held.getItem().getContainerItem(held));
                            } else {
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                            }
                        } else {
                            held.shrink(1);
                            if (held.getItem().getContainerItem(held) != null) {
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
                            if (contain != null) {
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, contain);
                            } else
                                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                        } else if (contain != null) {
                            firepit.dropItem(player, contain);
                        }
                    }
                    return true;
                } else if (firepit.fuel > 0) {
                    if (held.getItem() instanceof ILighter) {
                        world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 1.0F, rand.nextFloat() * 0.4F + 0.8F );
                        world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, 0F, 0F, 0F);

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

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFirepit();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return firepit_RI;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, Random random, EntityPlayer player) {
        TileEntityFirepit firepit = (TileEntityFirepit) world.getTileEntity(pos);
        if (firepit != null && firepit.isBurning()) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            world.playSound(player, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.AMBIENT, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F );
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

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityFirepit tile = (TileEntityFirepit) world.getTileEntity(pos);
        if (tile != null) {
            int charcoal = tile.getCharcoalDrop();

            if (charcoal > 0) {
                float xDrop = this.rand.nextFloat() * 0.8F + 0.1F;
                float yDrop = this.rand.nextFloat() * 0.8F + 0.1F;
                float zDrop = this.rand.nextFloat() * 0.8F + 0.1F;

                for (int c = 0; c < charcoal; c++) {
                    EntityItem drop = new EntityItem(world, pos.getX() + xDrop, pos.getY() + yDrop, pos.getZ() + zDrop,
                            new ItemStack(Items.COAL, 1, 1));

                    float jumpFactor = 0.05F;
                    drop.motionX = (float) this.rand.nextGaussian() * jumpFactor;
                    drop.motionY = (float) this.rand.nextGaussian() * jumpFactor + 0.2F;
                    drop.motionZ = (float) this.rand.nextGaussian() * jumpFactor;
                    world.spawnEntity(drop);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }
}
