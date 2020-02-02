package minefantasy.mfr.block.basic;

import minefantasy.mfr.init.ToolListMFR;
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
        if (lvl >= ToolListMFR.rarity.length) {
            lvl = ToolListMFR.rarity.length - 1;
        }
        return ToolListMFR.rarity[lvl];
    }
}
