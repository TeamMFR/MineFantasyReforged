package minefantasy.mf2.api.mining;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class RandomDigs {
    public static ArrayList<RandomDigs> drops = new ArrayList<RandomDigs>();

    private final ItemStack loot;
    private final float chanceToDrop;
    private final Block block;
    private final int blockSub;
    private final int harvestLvl;
    private final int minHeight;
    private final int maxHeight;
    private final boolean doesSilktouchDisable;

    public RandomDigs(ItemStack drop, float chance, Block base, int meta, int harvestLevel, int min, int max,
                      boolean silkDisable) {
        doesSilktouchDisable = silkDisable;
        minHeight = min;
        maxHeight = max;
        loot = drop;
        chanceToDrop = chance;
        harvestLvl = harvestLevel;

        block = base;
        blockSub = meta;
    }

    /**
     * Adds an ore to drop
     *
     * @param drop   the item dropped
     * @param chance the chance as a percentage to drop
     * @param block  the block it drops from (ID, Block, or stack)
     */
    public static void addOre(ItemStack drop, float chance, Block block, int meta, int harvestLevel, int min, int max,
                              boolean silkDisable) {
        drops.add(new RandomDigs(drop, chance / 100F, block, meta, harvestLevel, min, max, silkDisable));
    }

    public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max,
                              boolean silkDisable) {
        drops.add(new RandomDigs(drop, chance / 100F, block, OreDictionary.WILDCARD_VALUE, harvestLevel, min, max,
                silkDisable));
    }

    public static ArrayList<ItemStack> getDroppedItems(Block base, int meta, int harvest, int fortune,
                                                       boolean silktouch, int y) {
        ArrayList<ItemStack> loot = new ArrayList<ItemStack>();

        if (!drops.isEmpty()) {
            Iterator list = drops.iterator();

            while (list.hasNext()) {
                RandomDigs ore = (RandomDigs) list.next();
                if (matchesOre(ore, base, meta, harvest, fortune / 2F + 1F, silktouch, y)) {
                    loot.add(ore.loot);
                }
            }
        }

        return loot;
    }

    private static boolean matchesOre(RandomDigs ore, Block base, int meta, int harvest, float multiplier,
                                      boolean silktouch, int y) {
        Random random = new Random();

        if (ore.doesSilktouchDisable && silktouch) {
            return false;
        }

        if (!(ore.minHeight == -1 && ore.maxHeight == -1)) {
            if (y < ore.minHeight || y > ore.maxHeight) {
                return false;
            }
        }
        if (ore.block != base) {
            return false;
        }
        if (ore.blockSub != meta && ore.blockSub != OreDictionary.WILDCARD_VALUE) {
            return false;
        }
        if (ore.harvestLvl > harvest) {
            return false;
        }
        return random.nextFloat() < (ore.chanceToDrop * multiplier);
    }
}
