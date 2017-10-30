package minefantasy.mf2.block.crafting;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockSalvage extends ItemBlock {
    private BlockSalvage kit;

    public ItemBlockSalvage(Block base) {
        super(base);
        this.setMaxStackSize(8);
        this.kit = (BlockSalvage) base;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        list.add(StatCollector.translateToLocal("attribute.kit.salvage.name") + ": " + kit.dropLevel);
        super.addInformation(item, user, list, extra);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return super.getRarity(item);
    }
}
