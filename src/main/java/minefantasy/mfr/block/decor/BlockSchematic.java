package minefantasy.mfr.block.decor;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSchematic extends Block {

    public BlockSchematic(String name) {
        super(Material.CLOTH);

        setRegistryName(name);
        setUnlocalizedName(name);
    }

    public static boolean useSchematic(ItemStack item, World world, EntityPlayer user, RayTraceResult movingobjectposition) {
        if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos hit = movingobjectposition.getBlockPos();

            if (movingobjectposition.sideHit == EnumFacing.UP) {
                hit.add(0,1,0);
            }

            if (movingobjectposition.sideHit == EnumFacing.DOWN) {
                hit.add(0,1,0);
            }

            if (movingobjectposition.sideHit == EnumFacing.NORTH) {
                hit.add(0,0,1);
            }

            if (movingobjectposition.sideHit == EnumFacing.SOUTH) {
                hit.add(0,0,1);
            }

            if (movingobjectposition.sideHit == EnumFacing.EAST) {
                hit.add(1,0,0);
            }

            if (movingobjectposition.sideHit == EnumFacing.WEST) {
                hit.add(1,0,0);
            }

            if (user.canPlayerEdit(hit, movingobjectposition.sideHit, item)) {
                return placeSchematic(item.getItemDamage(), user, item, user.world, hit);
            }
        }
        return false;
    }

    public static boolean placeSchematic(int meta, EntityPlayer user, ItemStack item, World world, BlockPos pos) {
        if (world.isAirBlock(pos) && canBuildOn(world, pos.add(0,-1,0))) {
            world.setBlockState(pos, (BlockListMFR.SCHEMATIC_GENERAL).getDefaultState());
            return true;
        }
        return false;
    }

    public static boolean canBuildOn(World world, BlockPos pos) {
        return world.isSideSolid(pos, EnumFacing.UP);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ComponentListMFR.ARTEFACTS;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 4;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }
}
