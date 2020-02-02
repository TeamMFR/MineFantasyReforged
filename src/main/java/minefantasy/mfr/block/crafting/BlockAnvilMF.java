package minefantasy.mfr.block.crafting;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.block.tile.TileEntityAnvilMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.item.tool.crafting.ItemTongs;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockAnvilMF extends BlockContainer {
    public static EnumBlockRenderType anvil_RI;

    @SideOnly(Side.CLIENT)
    public int anvilRenderSide;
    public BaseMaterialMFR material;
    private int tier;
    private Random rand = new Random();
    float height = 1.0F / 16F * 13F;
    AxisAlignedBB BlockBB = new AxisAlignedBB(0F, 0F, 0F, 1F, height, 1F);


    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos){
        return BlockBB;
    }

    public BlockAnvilMF(BaseMaterialMFR material) {
        super(Material.ANVIL);
        this.material = material;
        String name = "anvil" + material.name;
        this.tier = material.tier;
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        this.setSoundType(SoundType.METAL);
        this.setHardness(material.hardness + 1 / 2F);
        this.setResistance(material.hardness + 1);
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
        world.setBlockState(pos, state);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = user.getHeldItem(hand);
        TileEntityAnvilMFR tile = getTile(world, pos);
        if (tile != null) {
            if (facing == EnumFacing.NORTH && held != null && held.getItem() instanceof ItemTongs
                    && onUsedTongs(world, user, held, tile)) {
                return true;
            }
            if (facing == EnumFacing.NORTH || !tile.tryCraft(user, true) && !world.isRemote) {
                user.openGui(MineFantasyReborn.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    private boolean onUsedTongs(World world, EntityPlayer user, ItemStack held, TileEntityAnvilMFR tile) {
        ItemStack result = tile.getStackInSlot(tile.getSizeInventory() - 1);
        ItemStack grabbed = TongsHelper.getHeldItem(held);

        // GRAB
        if (grabbed == null) {
            if (result != null && result.getItem() == ComponentListMFR.hotItem) {
                if (TongsHelper.trySetHeldItem(held, result)) {
                    tile.setInventorySlotContents(tile.getSizeInventory() - 1, null);
                    return true;
                }
            }
        } else {
            for (int s = 0; s < (tile.getSizeInventory() - 1); s++) {
                ItemStack slot = tile.getStackInSlot(s);
                if (slot == null) {
                    tile.setInventorySlotContents(s, grabbed);
                    TongsHelper.clearHeldItem(held, user);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
        {
            TileEntityAnvilMFR tile = getTile(world, pos);
            if (tile != null) {
                if (user.isSneaking()) {
                    tile.upset(user);
                } else {
                    tile.tryCraft(user, false);
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAnvilMFR(tier, material.name);
    }

    private TileEntityAnvilMFR getTile(World world, BlockPos pos) {
        return (TileEntityAnvilMFR) world.getTileEntity(pos);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityAnvilMFR tile = getTile(world, pos);

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
                            entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
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

        super.breakBlock(world,pos, state);
    }

    public int getTier() {
        return tier;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return anvil_RI;
    }
}