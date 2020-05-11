package minefantasy.mfr.itemblock;

import minefantasy.mfr.block.basic.BlockMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemMetaBlock extends ItemBlockBase {
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
