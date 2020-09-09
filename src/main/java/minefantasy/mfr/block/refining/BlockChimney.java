package minefantasy.mfr.block.refining;

import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.tile.TileEntityChimney;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockChimney extends Block {
    public int size;
    /**
     * Weather it can absorb smoke indirectly (not directly above a source)
     */
    public boolean isIndirect;
    private boolean isWide;
    private boolean isPipe;

    public BlockChimney(String type, boolean wide, boolean indirect, int size) {
        super(Material.ROCK);
        this.isIndirect = indirect;
        isWide = wide;
        this.size = size;
        if (wide) {
            this.setLightOpacity(255);
        }

        setRegistryName("chimney_" + type + (isWide ? "_wide" : "_thin"));
        setUnlocalizedName(("chimney." + type + (isWide ? ".wide" : "")));
        this.setSoundType(SoundType.METAL);
        this.setHardness(5F);
        this.setResistance(10F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityChimney();
    }

    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (isWide){
            return new AxisAlignedBB(0F, 0F, 0F, 1F, 1F, 1F);
        }
        else{
            return new AxisAlignedBB(1F / 4F, 0, 1F / 4F, 3F / 4F, 1F, 3F / 4F);
        }
    }


    public boolean isWideChimney() {
        return isWide;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return isWide;
    }

    public Block setPipe() {
        isPipe = true;
        isWide = false;
        return this;
    }

    public boolean isPipe() {
        return isPipe;
    }

}
