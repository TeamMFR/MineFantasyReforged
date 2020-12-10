package minefantasy.mfr.block;

import minefantasy.mfr.api.helpers.PowerArmour;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCogwork extends BlockDirectional {
    private boolean isMain;

    public BlockCogwork(String name, boolean helmet) {
        super(Material.CIRCUITS);
        this.isMain = helmet;

        setRegistryName(name);
        setUnlocalizedName(name);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.setHardness(1F);
        this.setResistance(5F);
        this.setLightOpacity(0);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && ToolHelper.getCrafterTool(player.getHeldItem(hand)).equalsIgnoreCase("spanner")) {
            return tryBuild(player, world, pos);
        }
        return false;
    }

    public boolean tryBuild(EntityPlayer builder, World world, BlockPos pos) {
        if (isMain && PowerArmour.isStationBlock(world, pos.add(0,2,0))
                && world.getBlockState(pos.add(0,-1,0)).getBlock() == MineFantasyBlocks.BLOCKCOGWORK_LEGS
                && world.getBlockState(pos.add(0,1,0)).getBlock() == MineFantasyBlocks.BLOCKCOGWORK_HELM) {
            if (!world.isRemote) {
                world.setBlockState(pos, getDefaultState());
                world.setBlockState(pos.add(0,-1,0), getDefaultState());
                world.setBlockState(pos.add(0,1,0), getDefaultState());

                EntityCogwork suit = new EntityCogwork(world);
                int angle = getAngleFor(builder);
                suit.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() - 0.95D, pos.getZ() + 0.5D, angle, 0.0F);
                suit.rotationYaw = suit.rotationYawHead = suit.prevRotationYaw = suit.prevRotationYawHead = angle;
                world.spawnEntity(suit);
            }

        }
        return false;
    }

    private int getAngleFor(EntityPlayer user) {
        int l = MathHelper.floor(user.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        return l * 90;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
        world.setBlockState(pos, state, 2);
    }
}