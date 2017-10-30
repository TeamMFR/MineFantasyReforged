package minefantasy.mf2.block.crafting;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockRepairKit extends ItemBlock {
    private BlockRepairKit kit;

    public ItemBlockRepairKit(Block base) {
        super(base);
        this.setMaxStackSize(8);
        this.kit = (BlockRepairKit) base;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        if (kit.isOrnate) {
            list.add(StatCollector.translateToLocal("attribute.kit.repairRate_normal.name") + ": "
                    + kit.repairLevel * 100F + "%");
            list.add(StatCollector.translateToLocal("attribute.kit.repairRate_enchant.name") + ": "
                    + kit.repairLevelEnchant * 100F + "%");
        } else {
            list.add(StatCollector.translateToLocal("attribute.kit.repairRate.name") + ": " + kit.repairLevel * 100F
                    + "%");
        }
        list.add(StatCollector.translateToLocal("attribute.kit.repairChance.name") + ": " + kit.successRate * 100F
                + "%");
        list.add(
                StatCollector.translateToLocal("attribute.kit.breakChance.name") + ": " + kit.breakChance * 100F + "%");
        super.addInformation(item, user, list, extra);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        if (kit.isOrnate) {
            return EnumRarity.rare;
        }
        if (kit.repairLevel >= 1.0F) {
            return EnumRarity.uncommon;
        }
        return super.getRarity(item);
    }
}
