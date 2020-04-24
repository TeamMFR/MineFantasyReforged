package minefantasy.mfr.block.food;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.FoodListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class BlockBerryBush extends BlockBush implements IShearable {
    @SideOnly(Side.CLIENT)
    protected int field_150127_b;
    int[] field_150128_a;
    private Random rand = new Random();


    public BlockBerryBush(String name) {
        super(Material.LEAVES);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHardness(0.3F);
        float offset = 1 / 16F;
        this.setLightOpacity(1);
        this.setSoundType(SoundType.GROUND);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        float offset = 1 / 16F;
        return new AxisAlignedBB(offset, 0F, offset, 1.0F - offset, 1.0F - (offset * 2), 1.0F - offset);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(world.getBlockState(pos).getBlock(), 1));
        return ret;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
            ItemStack itemstack = rand.nextInt(10) == 0 ? new ItemStack(FoodListMFR.berriesJuicy) : new ItemStack(FoodListMFR.berries, 1);
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
            return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return new ArrayList<ItemStack>() {
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int i) {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    @SideOnly(Side.CLIENT)

    public int getBlockColor() {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, BlockPos pos) {
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; ++k1) {
            for (int l1 = -1; l1 <= 1; ++l1) {
                int i2 = world.getBiome(pos.add(l1,0, k1)).getFoliageColorAtPos(pos.add(l1, 0, k1));
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }

        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }
}