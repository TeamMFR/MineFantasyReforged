package minefantasy.mfr.block.crafting;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.tile.TileEntityRoast;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class BlockRoast extends BlockContainer {
    public static EnumBlockRenderType roast_RI;
    public String tex;
    public Random rand = new Random();
    private boolean isOven;
    private int tier;

    public BlockRoast(int tier, String tex, boolean isOven) {
        super(Material.ROCK);
        this.isOven = isOven;
        this.tex = tex;
        this.tier = tier;
        String name = "food_" + (isOven ? "oven" : "roast") + "_" + tex;
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        this.setHardness(1.5F);
        this.setResistance(1F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
        if (isOven) {
            new AxisAlignedBB(3F / 16F, 0F, 3F / 16F, 13F / 16F, 1F / 1.6F, 13F / 16F);
        } else {
            new AxisAlignedBB(3F / 16F, 0F, 3F / 16F, 13F / 16F, 0.125F, 13F / 16F);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityRoast();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
        world.setBlockState(pos, state, 2);
    }

    public TileEntityRoast getTile(World world, BlockPos pos) {
        return (TileEntityRoast) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityRoast tile = getTile(world, pos);
        if (tile != null) {
            return tile.interact(user);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityRoast tile = getTile(world, pos);

        if (tile != null) {
            ItemStack itemstack = tile.items[0];

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
                        entityitem.getItem().setTagCompound( itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                    entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                    world.spawnEntity(entityitem);
                }
            }
        }

        super.breakBlock(world, pos, state);
    }


    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isOven() {
        return isOven;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return isOven ? roast_RI : super.getRenderType(state);
    }
}
