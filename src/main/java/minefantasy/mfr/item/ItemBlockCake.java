package minefantasy.mfr.item;

import minefantasy.mfr.block.BlockCakeMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockCake extends ItemBlockBase {
    private BlockCakeMFR cake;

    public ItemBlockCake(Block block) {
        super(block);
        cake = (BlockCakeMFR) block;
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
        if (lvl >= ToolListMFR.RARITY.length) {
            lvl = ToolListMFR.RARITY.length - 1;
        }
        return ToolListMFR.RARITY[lvl];
    }
}
