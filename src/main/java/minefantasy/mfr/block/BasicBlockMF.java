package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BasicBlockMF extends Block implements IClientRegister {
	private final Object drop;

	public BasicBlockMF(String name, Material material) {
		this(name, material, null);
	}

	public BasicBlockMF(String name, Material material, Object drop) {
		super(material);

		setRegistryName(name);
		setUnlocalizedName(name);
		if (material == Material.ROCK) {
			this.setHarvestLevel("pickaxe", 0);
		}
		this.drop = drop;
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		MineFantasyReforged.PROXY.addClientRegister(this);
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		if (this.blockMaterial == Material.GLASS) {
			return BlockRenderLayer.CUTOUT;
		}
		return BlockRenderLayer.SOLID;
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}
