package minefantasy.mf2.block.basic;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ConstructionBlockMF extends Block {

    public static final String[] m_names = new String[]{"", "_cobblestone", "_brick", "_pavement"};
    public IIcon[] m_icons = new IIcon[4];
    public Block[] stairblocks = new Block[4];

    public ConstructionBlockMF(String unlocName) {
        this(unlocName, new String[]{"", "_cobblestone", "_brick", "_pavement"});
    }

    public ConstructionBlockMF(String unlocName, String... types) {
        this(unlocName, Material.rock, types);
    }

    public ConstructionBlockMF(String unlocName, Material material, String... types) {
        super(material);

        this.setBlockName(unlocName);

        this.setCreativeTab(CreativeTabs.tabBlock);
        GameRegistry.registerBlock(this, ItemConstBlock.class, unlocName);
        for (int i = 0; i < m_names.length; i++) {
            GameRegistry.registerBlock(stairblocks[i] = new StairsConstBlock(unlocName + m_names[i] + "_stair", this, i)
                    .setHardness(1.5F).setResistance(10F), unlocName + m_names[i] + "_stair");
        }

        setHardness(1.5F);
        setResistance(10F);
        if (material == Material.rock) {
            setHarvestLevel("pickaxe", 0);
        }

        addConstructRecipes();

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

    public void addConstructRecipes() {
        GameRegistry.addSmelting(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0), 0);

        GameRegistry.addRecipe(new ItemStack(this, 4, 3), new Object[]{"XX", "XX", 'X', new ItemStack(this, 1, 1)});
        GameRegistry.addRecipe(new ItemStack(this, 4, 2),
                new Object[]{"X X", "   ", "X X", 'X', new ItemStack(this, 1, 1)});
        GameRegistry.addRecipe(new ItemStack(this, 1, 1), new Object[]{"X", 'X', new ItemStack(this, 1, 2)});
        GameRegistry.addRecipe(new ItemStack(this, 1, 1), new Object[]{"X", 'X', new ItemStack(this, 1, 3)});
        for (int i = 0; i < 4; i++)
            GameRegistry.addRecipe(new ItemStack(stairblocks[i], 4),
                    new Object[]{"  X", " XX", "XXX", 'X', new ItemStack(this, 1, i)});
    }

    @Override
    public int damageDropped(int meta) {
        if (meta == 0)
            return 1;

        return meta;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        m_icons[0] = register.registerIcon(MineFantasyII.MODID + ":" + "basic/" + getUnlocalizedName().substring(5));
        m_icons[1] = register.registerIcon(
                MineFantasyII.MODID + ":" + "basic/" + getUnlocalizedName().substring(5) + "_cobblestone");
        m_icons[2] = register
                .registerIcon(MineFantasyII.MODID + ":" + "basic/" + getUnlocalizedName().substring(5) + "_brick");
        m_icons[3] = register
                .registerIcon(MineFantasyII.MODID + ":" + "basic/" + getUnlocalizedName().substring(5) + "_pavement");
    }

    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + m_names[itemstack.getItemDamage()];
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0; i < 4; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (meta) {
            case 0:
                return m_icons[0];

            case 1:
                return m_icons[1];

            case 2:
                return m_icons[2];

            case 3:
                return m_icons[3];

            default:
                return blockIcon;
        }
    }

    public static class StairsConstBlock extends BlockStairs {
        private final Block base;

        public StairsConstBlock(String unlocalizedName, Block baseBlock, int metaOfBaseBlock) {
            super(baseBlock, metaOfBaseBlock);
            this.setBlockName(unlocalizedName);
            this.setCreativeTab(CreativeTabs.tabBlock);
            this.setLightOpacity(0);// They seem to render shadows funny
            this.base = baseBlock;
        }

        public StairsConstBlock(String unlocalizedName, Block baseBlock) {
            this(unlocalizedName, baseBlock, 0);
        }

        public void addRecipe() {
            GameRegistry.addRecipe(new ItemStack(this, 4), new Object[]{"B  ", "BB ", "BBB", 'B', this.base});
        }

        public Block register(String name) {
            GameRegistry.registerBlock(this, name);
            return this;
        }
    }

    public static class ItemConstBlock extends ItemBlockWithMetadata {

        public ItemConstBlock(Block block) {
            super(block, block);
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
