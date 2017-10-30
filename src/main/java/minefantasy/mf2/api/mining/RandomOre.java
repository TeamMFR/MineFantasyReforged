package minefantasy.mf2.api.mining;

import minefantasy.mf2.api.knowledge.ResearchLogic;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class RandomOre {
    public static ArrayList<RandomOre> drops = new ArrayList<RandomOre>();

    private final ItemStack loot;
    private final float chanceToDrop;
    private final Block block;
    private final int blockSub;
    private final int harvestLvl;
    private final int minHeight;
    private final int maxHeight;
    private final boolean doesSilktouchDisable;
    private final String research;

    public RandomOre(ItemStack drop, float chance, Block base, int meta, int harvestLevel, int min, int max,
                     boolean silkDisable) {
        this(drop, chance, base, meta, harvestLevel, min, max, silkDisable, null);
    }

    public RandomOre(ItemStack drop, float chance, Block base, int meta, int harvestLevel, int min, int max,
                     boolean silkDisable, String research) {
        doesSilktouchDisable = silkDisable;
        minHeight = min;
        maxHeight = max;
        loot = drop;
        chanceToDrop = chance;
        harvestLvl = harvestLevel;

        block = base;
        blockSub = meta;
        this.research = research;
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
        drops.add(new RandomOre(drop, chance / 100F, block, meta, harvestLevel, min, max, silkDisable));
    }

    public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max,
                              boolean silkDisable) {
        drops.add(new RandomOre(drop, chance / 100F, block, OreDictionary.WILDCARD_VALUE, harvestLevel, min, max,
                silkDisable));
    }

    public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max,
                              boolean silkDisable, String research) {
        drops.add(new RandomOre(drop, chance / 100F, block, OreDictionary.WILDCARD_VALUE, harvestLevel, min, max,
                silkDisable, research));
    }

    public static ArrayList<ItemStack> getDroppedItems(EntityLivingBase user, Block base, int meta, int harvest,
                                                       int fortune, boolean silktouch, int y) {
        ArrayList<ItemStack> loot = new ArrayList<ItemStack>();

        if (!drops.isEmpty()) {
            Iterator list = drops.iterator();

            while (list.hasNext()) {
                RandomOre ore = (RandomOre) list.next();
                if (matchesOre(user, ore, base, meta, harvest, fortune / 2F + 1F, silktouch, y)) {
                    loot.add(ore.loot);
                }
            }
        }

        return loot;
    }

    private static boolean matchesOre(EntityLivingBase user, RandomOre ore, Block base, int meta, int harvest,
                                      float multiplier, boolean silktouch, int y) {
        Random random = new Random();

        if (ore.doesSilktouchDisable && silktouch) {
            return false;
        }
        if (user instanceof EntityPlayer && ore.research != null) {
            if (!ResearchLogic.hasInfoUnlocked((EntityPlayer) user, ore.research)) {
                return false;
            }
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
