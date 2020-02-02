package minefantasy.mfr.block.crafting;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.block.tile.TileEntityBombPress;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class BlockBombPress extends BlockContainer {
    public static EnumBlockRenderType bpress_RI;
    private Random rand = new Random();

    public BlockBombPress() {
        super(Material.IRON);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName("bombPress");
        setUnlocalizedName(MineFantasyReborn.MODID + "." + "MF_BombPress");
        this.setSoundType(SoundType.METAL);
        this.setHardness(5F);
        this.setResistance(2F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!ResearchLogic.hasInfoUnlocked(user, KnowledgeListMFR.bombs)) {
            if (world.isRemote)
                user.sendMessage(new TextComponentString(I18n.translateToLocal("knowledge.unknownUse")));
            return false;
        }
        TileEntityBombPress tile = getTile(world, pos);
        if (tile != null) {
            tile.use(user);
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBombPress();
    }

    private TileEntityBombPress getTile(World world, BlockPos pos) {
        return (TileEntityBombPress) world.getTileEntity(pos);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return bpress_RI;
    }
}