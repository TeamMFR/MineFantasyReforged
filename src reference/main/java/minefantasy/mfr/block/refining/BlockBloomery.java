package minefantasy.mfr.block.refining;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.tile.TileEntityBloomery;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.item.tool.ItemLighterMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBloomery extends BlockContainer {
    public static int bloomery_RI = 109;
    private Random rand = new Random();

    public BlockBloomery() {
        super(Material.ROCK);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName("MF_Bloomery");
        setUnlocalizedName(MineFantasyReborn.MODID + "." + "bloomery");
        this.setSoundType(SoundType.STONE);
        this.setHardness(8F);
        this.setResistance(10F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBloomery();
    }

    private TileEntityBloomery getTile(IBlockAccess world, BlockPos pos) {
        return (TileEntityBloomery) world.getTileEntity(pos);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityBloomery tile = getTile(world, pos);

        if (tile != null) {
            for (int i1 = 0; i1 < (tile.getSizeInventory() - 1); ++i1)// breaking does not retrieve the result
            {
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
        TileEntityBloomery tile = getTile(world, pos);
        if (tile != null) {
            ItemStack held = user.getHeldItem(hand);

            // Hammer
            if (!user.isSwingInProgress && tile.tryHammer(user)) {
                return true;
            }
            // Light
            int l = ItemLighterMF.tryUse(held, user);
            if (!tile.isActive && !tile.hasBloom()) {
                if (held != null && l != 0) {
                    user.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
                    if (world.isRemote)
                        return true;
                    if (l == 1 && tile.light(user)) {
                        held.damageItem(1, user);
                    }
                    return true;
                }
            }
            // GUI
            if (!world.isRemote && !tile.isActive && !tile.hasBloom()) {
                user.openGui(MineFantasyReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
        TileEntityBloomery tile = getTile(world, pos);
        if (tile != null) {
            if (user.swingProgress <= 0.1F) {
                tile.tryHammer(user);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return bloomery_RI;
    }

}
