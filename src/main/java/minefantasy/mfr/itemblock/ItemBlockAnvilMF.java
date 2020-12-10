package minefantasy.mfr.itemblock;

import minefantasy.mfr.block.BlockAnvilMF;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockAnvilMF extends ItemBlockBase {
    private BlockAnvilMF anvil;

    public ItemBlockAnvilMF(Block block) {
        super(block);
        this.anvil = (BlockAnvilMF) block;
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        list.add(I18n.format("attribute.mfcrafttier.name") + ": " + anvil.getTier());
        super.addInformation(item, world, list, flag);
    }
}
