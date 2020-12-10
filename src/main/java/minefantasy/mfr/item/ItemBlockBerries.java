package minefantasy.mfr.item;

import net.minecraft.block.Block;

public class ItemBlockBerries extends ItemBlockBase {
    public ItemBlockBerries(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int id) {
        return id;
    }
}
