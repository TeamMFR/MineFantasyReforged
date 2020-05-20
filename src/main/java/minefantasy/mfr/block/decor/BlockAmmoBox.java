package minefantasy.mfr.block.decor;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.tile.decor.TileEntityAmmoBox;
import minefantasy.mfr.block.tile.decor.TileEntityWoodDecor;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class BlockAmmoBox extends BlockWoodDecor {
    public static final String NBT_Ammo = "Ammo", NBT_Stock = "Stock";
    public static int ammo_RI = 116;
    /**
     * Food-Ammo-All
     */
    public final byte storageType;
    public String name;
    private Random rand = new Random();

    public BlockAmmoBox(String name, byte storageType) {
        this(name, name, storageType);
    }

    /**
     * @param storageType (Food, Ammo, All)
     */
    public BlockAmmoBox(String name, String texName, byte storageType) {
        super(texName);
        this.storageType = storageType;
        this.name = name;

        setRegistryName(name);
        setUnlocalizedName(name);
        this.setHardness(0.5F);
        this.setResistance(2F);
        this.setCreativeTab(CreativeTabMFR.tabUtil);
    }

    @Override
    public AxisAlignedBB getBoundingBox (IBlockState state, IBlockAccess source, BlockPos pos){
        float width = (storageType == 0 ? 8F : storageType == 1 ? 14F : 16F) / 16F;
        float height = (storageType == 0 ? 4F : storageType == 1 ? 8F : 9F) / 16F;
        float border = (1F - width) / 2;
        return new AxisAlignedBB(border, 0F, border, 1 - border, height, 1 - border);
    }

    public static ItemStack getHeld(ItemStack item, boolean removeTag) {
        if (item.hasTagCompound() && item.getTagCompound().hasKey(NBT_Ammo)) {
            ItemStack i = new ItemStack(item.getTagCompound().getCompoundTag(NBT_Ammo));
            if (removeTag) {
                item.getTagCompound().removeTag(NBT_Ammo);
            }
            return i;
        }
        return null;
    }

    public static int getStock(ItemStack item, boolean removeTag) {
        if (item.hasTagCompound() && item.getTagCompound().hasKey(NBT_Stock)) {
            int i = item.getTagCompound().getInteger(NBT_Stock);
            if (removeTag) {
                item.getTagCompound().removeTag(NBT_Stock);
            }
            return i;
        }
        return 0;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return ammo_RI;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {

        TileEntityAmmoBox tile = getTile(world, pos);
        if (tile != null) {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_Ammo)
                    && stack.getTagCompound().hasKey(NBT_Stock)) {
                tile.ammo = new ItemStack(stack.getTagCompound().getCompoundTag(NBT_Ammo));
                tile.stock = stack.getTagCompound().getInteger(NBT_Stock);

            }
        }
        super.onBlockPlacedBy(world, pos, state, user, stack);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAmmoBox();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = user.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityAmmoBox) {
            return ((TileEntityAmmoBox) tile).interact(user, held);
        }
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }

    @Override
    protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
        return modifyAmmo((TileEntityAmmoBox) tile, super.modifyDrop(tile, item));
    }

    private ItemStack modifyAmmo(TileEntityAmmoBox tile, ItemStack item) {
        if (tile != null && item != null) {
            if (tile.ammo != null) {
                NBTTagCompound nbt = new NBTTagCompound();
                if (!item.hasTagCompound()) {
                    item.setTagCompound(new NBTTagCompound());
                }
                tile.ammo.writeToNBT(nbt);

                MFRLogUtil.logDebug("Added Drop: " + tile.ammo.getDisplayName() + " x" + tile.stock);
                item.getTagCompound().setTag(NBT_Ammo, nbt);
                item.getTagCompound().setInteger(NBT_Stock, tile.stock);
            }
        }
        return item;
    }

    private TileEntityAmmoBox getTile(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityAmmoBox) {
            return (TileEntityAmmoBox) tile;
        }
        return null;
    }
}
