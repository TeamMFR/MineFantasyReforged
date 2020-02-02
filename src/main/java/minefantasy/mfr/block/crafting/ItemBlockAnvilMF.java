package minefantasy.mfr.block.crafting;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockAnvilMF extends ItemBlock {
    private BlockAnvilMF anvil;

    public ItemBlockAnvilMF(Block block) {
        super(block);
        this.anvil = (BlockAnvilMF) block;
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        list.add(I18n.translateToLocal("attribute.mfcrafttier.name") + ": " + anvil.getTier());
        super.addInformation(item, world, list, flag);
    }
}
