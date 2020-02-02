package minefantasy.mfr.block.decor;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.tile.decor.TileEntityWoodDecor;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockWoodDecor extends BlockContainer {
    private final String texture;

    public BlockWoodDecor(String texture) {
        super(Material.WOOD);
        this.texture = texture;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
        TileEntityWoodDecor tile = getTile(world, pos);
        if (tile != null) {
            CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(stack);
            if (material != null) {
                tile.setMaterial(material);
            }
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityWoodDecor tile = getTile(world, pos);

        ItemStack itemstack = new ItemStack(getItemDropped(state, world.rand, 0), 1, damageDropped(state));

        itemstack = modifyDrop(tile, itemstack);

        if (tile != null) {
            if (itemstack != null) {
                float f = world.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, itemstack);

                float f3 = 0.05F;
                entityitem.motionX = (float) world.rand.nextGaussian() * f3;
                entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
                world.spawnEntity(entityitem);
            }
        }

        super.breakBlock(world, pos, state);
    }

    protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
        if (tile != null && item != null) {
            CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, tile.getMaterialName());
        }
        return item;
    }

    private TileEntityWoodDecor getTile(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityWoodDecor) {
            return (TileEntityWoodDecor) tile;
        }
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }

    public String getFullTexName() {
        return this.texture;
    }

    public ItemStack construct(String name) {
        return construct(name, 1);
    }

    public ItemStack construct(String name, int stacksize) {
        ItemStack item = new ItemStack(this, stacksize);
        CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, name.toLowerCase());

        return item;
    }
}
