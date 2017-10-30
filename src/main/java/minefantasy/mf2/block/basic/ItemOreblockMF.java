package minefantasy.mf2.block.basic;

import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemOreblockMF extends ItemBlock {
    private BlockOreMF ore;

    public ItemOreblockMF(Block block) {
        super(block);
        ore = (BlockOreMF) block;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        int lvl = ore.rarity + 1;

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
}
