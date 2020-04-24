package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ConstructionBlockMF extends Block {

    public static final String[] m_names = new String[]{"", "_cobblestone", "_brick", "_pavement"};
    public Block[] stairblocks = new Block[4];

    public ConstructionBlockMF(String unlocName) {
        this(unlocName, new String[]{"", "_cobblestone", "_brick", "_pavement"});
    }

    public ConstructionBlockMF(String unlocName, String... types) {
        this(unlocName, Material.ROCK, types);
    }

    public ConstructionBlockMF(String unlocName, Material material, String... types) {
        super(material);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(unlocName);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + unlocName);

        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        for (int i = 0; i < m_names.length; i++) {
            GameRegistry.findRegistry(Block.class).register(stairblocks[i] = new StairsConstBlock(unlocName + m_names[i] + "_stair", this).setHardness(1.5F).setResistance(10F));
        }

        setHardness(1.5F);
        setResistance(10F);
        if (material == Material.ROCK) {
            setHarvestLevel("pickaxe", 0);
        }

//        addConstructRecipes();

    }

    @Override
    public Block setHardness(float level) {
        if (stairblocks != null) {
            for (Block stairblock : stairblocks)
                stairblock.setHardness(level);
        }
        return super.setHardness(level);
    }

    @Override
    public Block setResistance(float level) {
        if (stairblocks != null) {
            for (Block stairblock : stairblocks)
                stairblock.setResistance(level);
        }
        return super.setResistance(level);
    }

    // public Item getItemDropped(int meta, Random rand, int i) { return null; };

//    public void addConstructRecipes() {
//        GameRegistry.addSmelting(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0), 0);
//        GameRegistry.addRecipe(new ItemStack(this, 4, 3), new Object[]{"XX", "XX", 'X', new ItemStack(this, 1, 1)});
//        GameRegistry.addRecipe(new ItemStack(this, 4, 2),
//                new Object[]{"X X", "   ", "X X", 'X', new ItemStack(this, 1, 1)});
//        GameRegistry.addRecipe(new ItemStack(this, 1, 1), new Object[]{"X", 'X', new ItemStack(this, 1, 2)});
//        GameRegistry.addRecipe(new ItemStack(this, 1, 1), new Object[]{"X", 'X', new ItemStack(this, 1, 3)});
//        for (int i = 0; i < 4; i++)
//            GameRegistry.addRecipe(new ItemStack(stairblocks[i], 4),
//                    new Object[]{"  X", " XX", "XXX", 'X', new ItemStack(this, 1, i)});
//    }

    @Override
    public int damageDropped(IBlockState state) {
        if (state == state.getBlock())
            return 1;
        return 0;
    }

    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + m_names[itemstack.getItemDamage()];
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {

        for (int i = 0; i < 4; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    public static class StairsConstBlock extends BlockStairs {
        private final Block base;

        public StairsConstBlock(String unlocalizedName, Block baseBlock, IBlockState state) {
            super(state);
            GameRegistry.findRegistry(Block.class).register(this);
            setRegistryName(unlocalizedName);
            setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + unlocalizedName);
            this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
            this.setLightOpacity(0);// They seem to render shadows funny
            this.base = baseBlock;
        }

        public StairsConstBlock(String unlocalizedName, Block baseBlock) {
            this(unlocalizedName, baseBlock, baseBlock.getDefaultState());
        }

//        public void addRecipe() {
//            GameRegistry.addRecipe(new ItemStack(this, 4), new Object[]{"B  ", "BB ", "BBB", 'B', this.base});
//        }

        public Block register(String name) {
            GameRegistry.findRegistry(Block.class).register(this);
            setRegistryName(name);
            setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
            return this;
        }
    }
}
