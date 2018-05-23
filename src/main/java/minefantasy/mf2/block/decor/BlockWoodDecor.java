package minefantasy.mf2.block.decor;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.tileentity.decor.TileEntityWoodDecor;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockWoodDecor extends BlockContainer {
    private final String texture;

    public BlockWoodDecor(String texture) {
        super(Material.wood);
        this.texture = texture;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item) {
        TileEntityWoodDecor tile = getTile(world, x, y, z);
        if (tile != null) {
            CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
            if (material != null) {
                tile.setMaterial(material);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityWoodDecor tile = getTile(world, x, y, z);

        ItemStack itemstack = new ItemStack(getItemDropped(meta, world.rand, 0), 1, damageDropped(meta));

        itemstack = modifyDrop(tile, itemstack);

        if (tile != null) {
            if (itemstack != null) {
                float f = world.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, itemstack);

                float f3 = 0.05F;
                entityitem.motionX = (float) world.rand.nextGaussian() * f3;
                entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
                world.spawnEntityInWorld(entityitem);
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
        if (tile != null && item != null) {
            CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, tile.getMaterialName());
        }
        return item;
    }

    private TileEntityWoodDecor getTile(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityWoodDecor) {
            return (TileEntityWoodDecor) tile;
        }
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
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
