package minefantasy.mfr.block.food;

import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCake extends ItemBlock {
    private BlockCakeMF cake;

    public ItemBlockCake(Block block) {
        super(block);
        cake = (BlockCakeMF) block;
        setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        int lvl = cake.getRarity() + 1;
        if (lvl >= ToolListMFR.rarity.length) {
            lvl = ToolListMFR.rarity.length - 1;
        }
        return ToolListMFR.rarity[lvl];
    }
}
