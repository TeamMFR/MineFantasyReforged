package minefantasy.mf2.item.tool.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 */
public class ItemEAnvilTools extends Item {
    public ItemEAnvilTools(String name, int uses) {
        setCreativeTab(CreativeTabMF.tabCraftTool);

        setTextureName("minefantasy2:Tool/Crafting/Engineer/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        this.setMaxDamage(uses);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack getContainerItem(ItemStack item) {
        item.setItemDamage(item.getItemDamage() + 1);
        return item.getItemDamage() >= item.getMaxDamage() ? null : item;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
    }
}
