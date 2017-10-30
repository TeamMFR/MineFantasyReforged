package minefantasy.mf2.block.food;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.item.food.FoodListMF;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

public class BlockBerryBush extends BlockBush implements IShearable {
    @SideOnly(Side.CLIENT)
    protected int field_150127_b;
    int[] field_150128_a;
    private Random rand = new Random();
    private IIcon[] berriedBush = new IIcon[2];
    private IIcon[] emptyBush = new IIcon[2];

    public BlockBerryBush(String name) {
        super(Material.leaves);
        GameRegistry.registerBlock(this, ItemBlockBerries.class, name);
        setBlockName(name);

        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.3F);
        float offset = 1 / 16F;
        this.setBlockBounds(offset, 0F, offset, 1.0F - offset, 1.0F - (offset * 2), 1.0F - offset);
        this.setLightOpacity(1);
        this.setStepSound(soundTypeGrass);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta >= 1 && rand.nextInt(120) == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        berriedBush[0] = reg.registerIcon("minefantasy2:food/berrybushfull");
        berriedBush[1] = reg.registerIcon("minefantasy2:food/berrybushfull_opaque");
        emptyBush[0] = reg.registerIcon("minefantasy2:food/berrybush");
        emptyBush[1] = reg.registerIcon("minefantasy2:food/berrybush_opaque");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        int flag = Minecraft.getMinecraft().gameSettings.fancyGraphics ? 0 : 1;
        if (meta == 0) {
            return berriedBush[flag];
        }
        return emptyBush[flag];
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
        return ret;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0) {
            if (world.isRemote)
                return true;

            world.setBlockMetadataWithNotify(x, y, z, 1, 2);

            ItemStack itemstack = rand.nextInt(10) == 0 ? new ItemStack(FoodListMF.berriesJuicy)
                    : new ItemStack(FoodListMF.berries, 1);
            if (itemstack != null) {
                float f = this.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0) {
                    int j1 = this.rand.nextInt(21) + 10;

                    if (j1 > itemstack.stackSize) {
                        j1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j1;
                    EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2,
                            new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound()) {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                    entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<ItemStack>() {
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(int i) {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBlockColor() {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; ++k1) {
            for (int l1 = -1; l1 <= 1; ++l1) {
                int i2 = world.getBiomeGenForCoords(x + l1, z + k1).getBiomeFoliageColor(x + l1, y, z + k1);
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }

        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }
}