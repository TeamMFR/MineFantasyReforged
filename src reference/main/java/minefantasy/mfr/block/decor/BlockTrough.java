package minefantasy.mfr.block.decor;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.tile.decor.TileEntityTrough;
import minefantasy.mfr.tile.decor.TileEntityWoodDecor;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrough extends BlockWoodDecor {
    public static final String NBT_fill = "Fill_Level";
    public static int trough_RI = 107;

    public BlockTrough(String name) {
        super(name);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        this.setHardness(1F);
        this.setResistance(0.5F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0F, 0F, 0F, 1.0F, (7F / 16F), 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return trough_RI;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack item) {

        TileEntityTrough tile = getTile(world, pos);
        if (tile != null) {
            if (item.hasTagCompound() && item.getTagCompound().hasKey(NBT_fill)) {
                tile.fill = item.getTagCompound().getInteger(NBT_fill);
            }
        }
        super.onBlockPlacedBy(world, pos, state, user, item);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTrough();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = user.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityTrough) {
            if (((TileEntityTrough) tile).interact(user, held)) {
                world.playSound(user, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + user.getRNG().nextFloat() / 4F, 0.5F + user.getRNG().nextFloat());
                ((TileEntityTrough) tile).syncData();
                return true;
            }
        }
        return false;
    }

    private TileEntityTrough getTile(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityTrough) {
            return (TileEntityTrough) tile;
        }
        return null;
    }

    @Override
    protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
        return modifyFill((TileEntityTrough) tile, super.modifyDrop(tile, item));
    }

    private ItemStack modifyFill(TileEntityTrough tile, ItemStack item) {
        if (tile != null && item != null) {
            item.getTagCompound().setInteger(NBT_fill, tile.fill);
        }
        return item;
    }
}
