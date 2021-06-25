package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ConstructionBlockMF extends BasicBlockMF {

	public static final String[] m_names = new String[] {"", "_cobblestone", "_brick", "_pavement"};

	public ConstructionBlockMF(String name, Material material, String... types) {
		super(name, material);

		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setHardness(1.5F);
		setResistance(10F);
		if (material == Material.ROCK) {
			setHarvestLevel("pickaxe", 0);
		}
	}

	@Override
	public Block setHardness(float level) {

		return super.setHardness(level);
	}

	@Override
	public Block setResistance(float level) {

		return super.setResistance(level);
	}

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

	public static class StairsConstBlock extends BlockStairs implements IClientRegister{

		public StairsConstBlock(String name, Block baseBlock, IBlockState state) {
			super(state);

			setRegistryName(name);
			setUnlocalizedName(name);
			this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
			this.setLightOpacity(0);// They seem to render shadows funny
			MineFantasyReforged.PROXY.addClientRegister(this);
		}

		public StairsConstBlock(String unlocalizedName, Block baseBlock) {
			this(unlocalizedName, baseBlock, baseBlock.getDefaultState());
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void registerClient() {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
		}
	}
}
