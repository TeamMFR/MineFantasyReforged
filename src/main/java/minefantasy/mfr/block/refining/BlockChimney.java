package minefantasy.mfr.block.refining;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.tile.TileEntityChimney;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class BlockChimney extends BlockContainer {
    public static EnumBlockRenderType pipe_RI;
    public int size;
    /**
     * Weather it can absorb smoke indirectly (not directly above a source)
     */
    public boolean isIndirect;
    private Random rand = new Random();
    private boolean isWide;
    private String chimneyType;
    private boolean isPipe;
    private AxisAlignedBB wideBB;

    public BlockChimney(String type, boolean wide, boolean indirect, int size) {
        super(Material.ROCK);
        this.isIndirect = indirect;
        isWide = wide;
        this.chimneyType = type;
        this.size = size;
        if (wide) {
            this.setLightOpacity(255);
        } else {
          wideBB = new AxisAlignedBB(1F / 4F, 0, 1F / 4F, 3F / 4F, 1F, 3F / 4F);
        }

        setRegistryName("MF_Chimney_" + type + (isWide ? "_Wide" : "_Thin"));
        setUnlocalizedName(("chimney." + type + (isWide ? ".wide" : "")));
        this.setSoundType(SoundType.METAL);
        this.setHardness(5F);
        this.setResistance(10F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return wideBB;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityChimney();
    }

    private TileEntityChimney getTile(IBlockAccess world, BlockPos pos) {
        return (TileEntityChimney) world.getTileEntity(pos);
    }

    public boolean isWideChimney() {
        return isWide;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return isWide;
    }

    /*
     *
     * @Override public boolean onBlockActivated(World world, int x, int y, int z,
     * EntityPlayer user, int side, float xOffset, float yOffset, float zOffset) {
     * int m = world.getBlockMetadata(x, y, z); TileEntityChimney chimney =
     * getTile(world, x, y, z);
     *
     * if(chimney != null) { ItemStack held = user.getHeldItem(); if(!world.isRemote
     * && held != null && held.getItem() instanceof ItemBlock) { ItemBlock item =
     * (ItemBlock)held.getItem(); if(item.field_150939_a.isNormalCube()) {
     * chimney.maskBlock = item.field_150939_a; chimney.maskMeta =
     * item.getMetadata(held.getItemDamage()); chimney.sync(); world.setBlock(x, y,
     * z, BlockListMF.chimney_wide, m, 2); return true; } } } return false; }
     *
     * @Override public void getSubBlocks(Item item, CreativeTabs tab, List list) {
     * if(!isWide) { super.getSubBlocks(item, tab, list); } }
     */

    public Block setPipe() {
        isPipe = true;
        isWide = false;
        return this;
    }

    public boolean isPipe() {
        return isPipe;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return isPipe() ? pipe_RI : super.getRenderType(state);
    }

}
