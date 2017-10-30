package minefantasy.mf2.block.decor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tool.IStorageBlock;
import minefantasy.mf2.block.tileentity.decor.TileEntityTrough;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ItemBlockTrough extends ItemBlock implements IStorageBlock {
    private Random rand = new Random();

    public ItemBlockTrough(Block base) {
        super(base);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean info) {
        if (item.hasTagCompound() && item.getTagCompound().hasKey(BlockTrough.NBT_fill)) {
            int stock = item.getTagCompound().getInteger(BlockTrough.NBT_fill);
            if (stock > 0) {
                list.add(StatCollector.translateToLocalFormatted("attribute.fill", stock));
            }
        }
        CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
        if (material != null) {
            list.add(StatCollector.translateToLocalFormatted("attribute.fill.capacity.name",
                    TileEntityTrough.getCapacity(material.tier) * TileEntityTrough.capacityScale));
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (MineFantasyII.isDebug()) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                list.add(this.construct(customMat.name));
            }
        } else {
            list.add(this.construct("RefinedWood"));
        }
    }

    private ItemStack construct(String name) {
        return CustomToolHelper.constructSingleColoredLayer(this, name, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        return CustomToolHelper.getLocalisedName(item, this.getUnlocalizedNameInefficiently(item) + ".name");
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float f,
                             float f1, float f2) {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            return super.onItemUse(item, player, world, x, y, z, side, f, f1, f2);
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k)) {
                    return super.onItemUse(item, player, world, x, y, z, side, f, f1, f2);
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {
                    return super.onItemUse(item, player, world, x, y, z, side, f, f1, f2);
                }

                if (isWaterSource(world, i, j, k)) {
                    gather(player);
                    world.playSoundAtEntity(player, "random.splash", 0.125F + rand.nextFloat() / 4F,
                            0.5F + rand.nextFloat());
                    return false;
                }
            }
            return super.onItemUse(item, player, world, x, y, z, side, f, f1, f2);
        }
    }

    private void gather(EntityPlayer player) {
        ItemStack item = player.getHeldItem();
        if (item != null) {
            int tier = 0;
            CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
            if (material != null) {
                tier = material.tier;
            }
            NBTTagCompound nbt = getNBT(item);
            nbt.setInteger(BlockTrough.NBT_fill, TileEntityTrough.getCapacity(tier) * TileEntityTrough.capacityScale);
        }
        player.swingItem();

    }

    private NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    private boolean isWaterSource(World world, int i, int j, int k) {
        if (world.getBlock(i, j, k).getMaterial() == Material.water) {
            return true;
        }
        return false;
    }
}
