package minefantasy.mf2.block.decor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tool.IStorageBlock;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemBlockAmmoBox extends ItemBlock implements IStorageBlock {
    public ItemBlockAmmoBox(Block base) {
        super(base);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean info) {
        if (item.hasTagCompound() && item.getTagCompound().hasKey(BlockAmmoBox.NBT_Ammo)
                && item.getTagCompound().hasKey(BlockAmmoBox.NBT_Stock)) {
            ItemStack ammo = ItemStack
                    .loadItemStackFromNBT(item.getTagCompound().getCompoundTag(BlockAmmoBox.NBT_Ammo));
            int stock = item.getTagCompound().getInteger(BlockAmmoBox.NBT_Stock);
            if (ammo != null) {
                list.add(ammo.getDisplayName() + " x" + stock);
            }
        }
        CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
        if (material != null) {
            list.add(StatCollector.translateToLocalFormatted("attribute.box.capacity.name",
                    TileEntityAmmoBox.getCapacity(material.tier)));
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
}
