package minefantasy.mf2.block.decor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import minefantasy.mf2.block.tileentity.decor.TileEntityWoodDecor;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

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
        float width = (storageType == 0 ? 8F : storageType == 1 ? 14F : 16F) / 16F;
        float height = (storageType == 0 ? 4F : storageType == 1 ? 8F : 9F) / 16F;

        float border = (1F - width) / 2;
        this.setBlockBounds(border, 0F, border, 1 - border, height, 1 - border);

        this.name = name;
        GameRegistry.registerBlock(this, ItemBlockAmmoBox.class, name);
        setBlockName(name);
        this.setHardness(0.5F);
        this.setResistance(2F);
        this.setCreativeTab(CreativeTabMF.tabUtil);
    }

    public static ItemStack getHeld(ItemStack item, boolean removeTag) {
        if (item.hasTagCompound() && item.getTagCompound().hasKey(NBT_Ammo)) {
            ItemStack i = ItemStack.loadItemStackFromNBT(item.getTagCompound().getCompoundTag(NBT_Ammo));
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
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.planks.getIcon(side, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {

    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return ammo_RI;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item) {
        int direction = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, direction, 2);

        TileEntityAmmoBox tile = getTile(world, x, y, z);
        if (tile != null) {
            if (item.hasTagCompound() && item.getTagCompound().hasKey(NBT_Ammo)
                    && item.getTagCompound().hasKey(NBT_Stock)) {
                tile.ammo = ItemStack.loadItemStackFromNBT(item.getTagCompound().getCompoundTag(NBT_Ammo));
                tile.stock = item.getTagCompound().getInteger(NBT_Stock);
            }
        }
        super.onBlockPlacedBy(world, x, y, z, user, item);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAmmoBox();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        ItemStack held = user.getHeldItem();
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityAmmoBox) {
            return ((TileEntityAmmoBox) tile).interact(user, held);
        }
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
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

                MFLogUtil.logDebug("Added Drop: " + tile.ammo.getDisplayName() + " x" + tile.stock);
                item.getTagCompound().setTag(NBT_Ammo, nbt);
                item.getTagCompound().setInteger(NBT_Stock, tile.stock);
            }
        }
        return item;
    }

    private TileEntityAmmoBox getTile(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityAmmoBox) {
            return (TileEntityAmmoBox) tile;
        }
        return null;
    }
}
