package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.itemblock.ItemBlockBase;
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


    public ConstructionBlockMF(String unlocName) {
        this(unlocName, new String[]{"", "_cobblestone", "_brick", "_pavement"});
    }

    public ConstructionBlockMF(String unlocName, String... types) {
        this(unlocName, Material.ROCK, types);
    }

    public ConstructionBlockMF(String unlocName, Material material, String... types) {
        super(material);

        setRegistryName(unlocName);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + unlocName);

        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(1.5F);
        setResistance(10F);
        if (material == Material.ROCK) {
            setHarvestLevel("pickaxe", 0);
        }

//        addConstructRecipes();

    }

    @Override
    public Block setHardness(float level) {

        return super.setHardness(level);
    }

    @Override
    public Block setResistance(float level) {

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

    }

    public static class ItemConstBlock extends ItemBlockBase {

        public ItemConstBlock(Block block) {
            super(block);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            switch (stack.getItemDamage()) {
                case 1:
                    return this.getUnlocalizedName() + "_cobblestone";
                case 2:
                    return this.getUnlocalizedName() + "_brick";
                case 3:
                    return this.getUnlocalizedName() + "_pavement";
                default:
                    return this.getUnlocalizedName();
            }
        }

        @Override
        public int getMetadata(int d) {
            return d;
        }
    }
}
