package minefantasy.mf2.block.basic;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class BlockOreMF extends Block {
    public int rarity;
    private int xp;
    private Item drop;
    private int dropMin;
    private int dropMax;
    private Random rand = new Random();

    public BlockOreMF(String name, int harvestLevel) {
        this(name, harvestLevel, 0);
    }

    public BlockOreMF(String name, int harvestLevel, int rarity) {
        this(name, harvestLevel, rarity, null, 1, 1, 0);
    }

    public BlockOreMF(String name, int harvestLevel, int rarity, Item drop, int min, int max, int xp) {
        this(name, harvestLevel, rarity, drop, min, max, xp, Material.rock);
    }

    public BlockOreMF(String name, int harvestLevel, int rarity, Item drop, int min, int max, int xp,
                      Material material) {
        super(material);
        this.xp = xp;
        this.drop = drop;
        this.rarity = rarity;
        this.dropMin = min;
        this.dropMax = max;

        this.setStepSound(Block.soundTypePiston);
        GameRegistry.registerBlock(this, ItemOreblockMF.class, name);
        setBlockName(name);
        setBlockTextureName("minefantasy2:ores/" + name);

        if (material == Material.rock) {
            this.setHarvestLevel("pickaxe", harvestLevel);
        }
        this.setCreativeTab(CreativeTabMF.tabOres);
        OreDictionary.registerOre(name, this);
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int i) {
        return drop != null ? drop : Item.getItemFromBlock(this);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random rand) {
        return MathHelper.getRandomIntegerInRange(rand, dropMin, dropMax);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i'
     * (inclusive).
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, rand, fortune)) {
            int j = rand.nextInt(fortune + 2) - 1;

            if (j < 0) {
                j = 0;
            }

            return this.quantityDropped(rand) * (j + 1);
        } else {
            return this.quantityDropped(rand);
        }
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int m, float f, int i) {
        super.dropBlockAsItemWithChance(world, x, y, z, m, f, i);
    }

    @Override
    public int getExpDrop(IBlockAccess world, int meta, int fortune) {
        if (xp > 0 && this.getItemDropped(meta, rand, fortune) != Item.getItemFromBlock(this)) {
            return MathHelper.getRandomIntegerInRange(rand, xp, xp * 2);
        }
        return 0;
    }
}
