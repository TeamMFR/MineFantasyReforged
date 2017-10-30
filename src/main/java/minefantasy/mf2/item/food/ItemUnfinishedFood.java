package minefantasy.mf2.item.food;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 */
public class ItemUnfinishedFood extends Item {
    private int itemRarity;

    public ItemUnfinishedFood(String name) {
        this(name, 0);
    }

    public ItemUnfinishedFood(String name, int rarity) {
        super();
        setMaxStackSize(1);
        itemRarity = rarity;
        setTextureName("minefantasy2:food/unfinished/" + name);
        this.setCreativeTab(CreativeTabMF.tabFood);
        GameRegistry.registerItem(this, "MF_UFood" + name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        int lvl = itemRarity + 1;

        if (item.isItemEnchanted()) {
            if (lvl == 0) {
                lvl++;
            }
            lvl++;
        }
        if (lvl >= ToolListMF.rarity.length) {
            lvl = ToolListMF.rarity.length - 1;
        }
        return ToolListMF.rarity[lvl];
    }

    public void onCrafted(EntityPlayer user, ItemStack item) {
    }
}
