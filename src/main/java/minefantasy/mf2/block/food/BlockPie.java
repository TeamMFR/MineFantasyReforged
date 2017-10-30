package minefantasy.mf2.block.food;

import net.minecraft.item.Item;

public class BlockPie extends BlockCakeMF {

    public BlockPie(String name, Item slice) {
        super(name, slice);
        this.height = 1F / 16F * 6F;
    }

}
