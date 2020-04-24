package minefantasy.mfr.item.food;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.ToolListMFR;
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
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
        this.setCreativeTab(CreativeTabMFR.tabFood);
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
        if (lvl >= ToolListMFR.rarity.length) {
            lvl = ToolListMFR.rarity.length - 1;
        }
        return ToolListMFR.rarity[lvl];
    }

    public void onCrafted(EntityPlayer user, ItemStack item) {
    }
}
