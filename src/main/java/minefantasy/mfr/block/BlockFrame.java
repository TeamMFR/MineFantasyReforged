package minefantasy.mfr.block;

import minefantasy.mfr.api.helpers.PowerArmour;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFrame extends BasicBlockMF {
    public static final float offset = 0.28125F;
    public boolean isCogworkHolder = false;

    public BlockFrame(String name) {
        this(name, null);
    }

    public BlockFrame(String name, Object drop) {
        super(name, Material.IRON, drop);
        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.setHardness(1.0F);
        this.setResistance(3.0F);
    }

    public BlockFrame setCogworkHolder() {
        isCogworkHolder = true;
        this.setCreativeTab(null);
        return this;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }


    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0F, 0F, 0F, 1F, 1F - offset, 1F);
    }


    public void setBlockBoundsBasedOnState(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (isCogworkHolder) {
            this.getBoundingBox(state, source, pos);
        }
    }

    public boolean canInteract(IBlockAccess world, BlockPos pos) {
        return canInteract(world, pos, false);
    }

    public boolean canInteract(IBlockAccess world, BlockPos pos, boolean floor) {
        if (floor && world.getBlockState(pos).isSideSolid(world, pos, EnumFacing.UP)) {
            return true;
        }
        return world.getBlockState(pos) instanceof BlockFrame;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (ToolHelper.getCrafterTool(player.getHeldItem(hand)).equalsIgnoreCase("spanner")) {
            return tryBuild(player, world, pos);
        }
        return false;
    }

    private boolean tryBuild(EntityPlayer player, World world, BlockPos pos) {
        if (PowerArmour.isBasicStationFrame(world, pos) && (player.capabilities.isCreativeMode || player.inventory.hasItemStack(new ItemStack(ComponentListMFR.COGWORK_PULLEY)))) {
            if (!player.capabilities.isCreativeMode) {
                player.inventory.removeStackFromSlot(1);
            }
            if (!world.isRemote) {
                world.setBlockState(pos, (IBlockState) MineFantasyBlocks.COGWORK_HOLDER,2);
            }
            return true;
        }
        return false;
    }
}
