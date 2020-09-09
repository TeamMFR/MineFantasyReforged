package minefantasy.mfr.block.crafting;

import minefantasy.mfr.block.basic.BlockTileEntity;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.tile.TileEntityCarpenterMFR;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCarpenter extends BlockTileEntity<TileEntityCarpenterMFR> {

    public BlockCarpenter() {
        super(Material.WOOD);

        setRegistryName("carpenter_bench");
        setUnlocalizedName("carpenter_bench");
        this.setSoundType(SoundType.WOOD);
        this.setHardness(5F);
        this.setResistance(2F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCarpenterMFR();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
        world.setBlockState(pos, state, 2);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityCarpenterMFR tile = (TileEntityCarpenterMFR) getTile(world, pos);
        if (tile != null && (world.isAirBlock(pos.add(0,1,0)) || !world.isSideSolid(pos.add(0,1,0), EnumFacing.DOWN))) {
            if (facing != EnumFacing.UP || !tile.tryCraft(player) && !world.isRemote) {
                tile.openGUI(world, player);
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
        TileEntityCarpenterMFR tile = (TileEntityCarpenterMFR) getTile(world, pos);
        if (tile != null) {
            tile.tryCraft(user);
        }
    }
}