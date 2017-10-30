package minefantasy.mf2.item.gadget;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.crafting.ISpecialSalvage;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemCrudeBomb extends ItemBomb implements ISpecialSalvage {
    private IIcon basicIcon;

    public ItemCrudeBomb(String name) {
        super(name);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return 20;
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "item.bomb_crude";
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(this));
    }

    // TODO Icons
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(byte type) {
        return basicIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        basicIcon = reg.registerIcon("minefantasy2:Other/bomb_crude");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack item) {
        return basicIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer) {
        return getIconIndex(item);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return ToolListMF.poor;
    }

    @Override
    public Object[] getSalvage(ItemStack item) {
        return new Object[]{Items.paper, ComponentListMF.thread, ComponentListMF.blackpowder};
    }

    @Override
    public byte getItemFuse(byte value) {
        return 0;
    }

    @Override
    public byte getItemFilling(byte value) {
        return 0;
    }

    @Override
    public byte getItemCasing(byte value) {
        return -1;
    }

    @Override
    public byte getItemPowder(byte value) {
        return 0;
    }
}