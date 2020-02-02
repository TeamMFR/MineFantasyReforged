package minefantasy.mfr.block.basic;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMetaBlock extends ItemBlock {
    private BlockMeta instance;

    public ItemMetaBlock(Block block) {
        super(block);
        this.instance = (BlockMeta) block;
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return instance.getUnlocalisedName(item.getItemDamage());
    }

    @Override
    public int getMetadata(int subid) {
        return subid;
    }
}
