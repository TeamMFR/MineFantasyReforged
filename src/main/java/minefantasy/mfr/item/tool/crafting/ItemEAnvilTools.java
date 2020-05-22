package minefantasy.mfr.item.tool.crafting;

import minefantasy.mfr.item.ItemBaseMFR;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 */
public class ItemEAnvilTools extends ItemBaseMFR {
    public ItemEAnvilTools(String name, int uses) {
        super(name);
        setCreativeTab(CreativeTabMFR.tabCraftTool);

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
