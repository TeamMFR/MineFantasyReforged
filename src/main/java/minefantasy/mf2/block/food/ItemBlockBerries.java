package minefantasy.mf2.block.food;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockBerries extends ItemBlock {
    public ItemBlockBerries(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int id) {
        return id;
    }
}
