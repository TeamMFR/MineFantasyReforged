package minefantasy.mfr.block.refining;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFH;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBFH extends BlockContainer {
    private static boolean keepInventory;
    public boolean isActive;
    private Random rand = new Random();

    public BlockBFH(boolean isActive) {
        super(Material.ANVIL);
        this.isActive = isActive;

        setRegistryName(isActive ? "MF_BlastHeaterActive" : "MF_BlastHeater");
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + "blastfurnheater");
        this.setSoundType(SoundType.METAL);
        this.setHardness(10F);
        this.setResistance(10F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    public static void updateFurnaceBlockState(boolean state, World world, BlockPos pos) {
        TileEntity tileentity = world.getTileEntity(pos);
        keepInventory = true;

        if (state) {
            world.setBlockState(pos, (BlockListMFR.BLAST_HEATER_ACTIVE).getDefaultState());
        } else {
            world.setBlockState(pos, (BlockListMFR.BLAST_HEATER).getDefaultState());
        }

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
        if (isActive && rand.nextInt(20) == 0) {
            world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.AMBIENT,1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isActive) {
            super.getSubBlocks(tab, items);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBlastFH();
    }

    private TileEntityBlastFH getTile(IBlockAccess world, BlockPos pos) {
        return (TileEntityBlastFH) world.getTileEntity(pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntityBlastFH tile = getTile(world, pos);
        if (tile != null)
            tile.updateBuild();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (keepInventory)
            return;

        TileEntityBlastFH tile = getTile(world, pos);

        if (tile != null) {
            for (int i1 = 0; i1 < tile.getSizeInventory(); ++i1) {
                ItemStack itemstack = tile.getStackInSlot(i1);

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
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!ResearchLogic.hasInfoUnlocked(user, KnowledgeListMFR.blastfurn)) {
            if (world.isRemote)
                user.sendMessage(new TextComponentString(I18n.translateToLocal("knowledge.unknownUse")));
            return false;
        }
        TileEntityBlastFH tile = getTile(world, pos);
        if (tile != null) {
            if (!world.isRemote) {
                user.openGui(MineFantasyReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(BlockListMFR.BLAST_HEATER));
    }

}
