package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class BasicBlockMF extends Block {
    private Object drop;

    public BasicBlockMF(String name, Material material) {
        this(name, material, null);
    }

    public BasicBlockMF(String name, Material material, Object drop) {
        super(material);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        if (material == Material.ROCK) {
            this.setHarvestLevel("pickaxe", 0);
        }
        this.drop = drop;
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public BasicBlockMF setBlockSoundType(SoundType soundType) {
        setSoundType(soundType);
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube(IBlockState state) {
        return this.blockMaterial != Material.GLASS;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (drop != null) {
            if (drop instanceof Item) {
                return (Item) drop;
            }
            if (drop instanceof Block) {
                return Item.getItemFromBlock((Block) drop);
            }
        }
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }
}
