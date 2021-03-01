package minefantasy.mfr.block.refining;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.tile.blastfurnace.TileEntityBlastFC;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.mechanics.knowledge.KnowledgeListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class BlockBFC extends BlockContainer {
    private Random rand = new Random();

    public BlockBFC() {
        super(Material.ANVIL);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName("MF_BlastChamber");
        setUnlocalizedName(MineFantasyReborn.MODID + "." + "blastfurnchamber");
        this.setSoundType(SoundType.METAL);
        this.setHardness(8F);
        this.setResistance(10F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBlastFC();
    }

    private TileEntityBlastFC getTile(IBlockAccess world, BlockPos pos) {
        return (TileEntityBlastFC) world.getTileEntity(pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntityBlastFC tile = getTile(world, pos);
        if (tile != null)
            tile.updateBuild();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityBlastFC tile = getTile(world, pos);

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
        TileEntityBlastFC tile = getTile(world, pos);
        if (tile != null) {
            if (!world.isRemote) {
                user.openGui(MineFantasyReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

}
