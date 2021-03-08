package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemComponentMFR extends ItemBaseMFR implements ITieredComponent {
	protected String name;
	protected int itemRarity;
	// STORAGE
	protected String blockTexture;
	protected String storageType;

	/*
	 * private int itemRarity;
	 *
	 * @Override public EnumRarity getRarity(ItemStack item) { int lvl =
	 * itemRarity+1;
	 *
	 * if(item.isItemEnchanted()) { if(lvl == 0) { lvl++; } lvl ++; } if(lvl >=
	 * ToolListMF.rarity.length) { lvl = ToolListMF.rarity.length-1; } return
	 * ToolListMF.rarity[lvl]; }
	 */
	// ===================================================== CUSTOM START
	// =============================================================\\
	private float unitCount = 1;
	private boolean isCustom = false;
	public String materialType = CustomMaterial.NONE.name;

	public ItemComponentMFR(String name) {
		this(name, 0);
	}

	public ItemComponentMFR(String name, int rarity) {
		super(name);
		itemRarity = rarity;
		this.name = name;
		this.setCreativeTab(MineFantasyTabs.tabMaterials);
	}

	private void add(List<ItemStack> list, Item item) {
		list.add(new ItemStack(item));
	}

	@Override
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flag) {

		super.addInformation(item, world, list, flag);
		if (isCustom) {
			CustomToolHelper.addComponentString(item, list, CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main), this.unitCount);
		}
	}

	public ItemComponentMFR setCustom(float units, String type) {
		canRepair = false;
		this.unitCount = units;
		isCustom = true;
		this.materialType = type;
		return this;
	}

	public ItemComponentMFR setStoragePlacement(String type, String texture) {
		this.blockTexture = texture;
		this.storageType = type;
		return this;
	}

	protected float getWeightModifier(ItemStack stack) {
		return CustomToolHelper.getWeightModifier(stack, 1.0F);
	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		return CustomToolHelper.getRarity(item, itemRarity);
	}

	public ItemStack construct(String main) {
		return construct(main, 1);
	}

	public ItemStack construct(String main, int stackSize) {
		return CustomToolHelper.constructSingleColoredLayer(this, main, stackSize);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack item) {
		if (isCustom) {
			return CustomToolHelper.getLocalisedName(item, "item.commodity_" + name + ".name");
		}
		String unlocalizedName = this.getUnlocalizedNameInefficiently(item) + ".name";
		return CustomToolHelper.getLocalisedName(item, unlocalizedName);
	}

	@Override
	public String getMaterialType(ItemStack item) {
		return materialType;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (storageType == null) {
			return EnumActionResult.FAIL;
		}

		ItemStack stack = player.getHeldItem(hand);
		Block componentBlock = MineFantasyBlocks.COMPONENTS;

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (!block.isReplaceable(world, pos)) {
			pos = pos.offset(facing);
		}

		if (!world.mayPlace(componentBlock, pos, false, facing, player)) {
			return EnumActionResult.FAIL;
		}

		world.setBlockState(pos, MineFantasyBlocks.COMPONENTS.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, 0, player, hand), 2);

		int size = BlockComponent.placeComponent(player, stack, world, pos, storageType, blockTexture);

		stack.shrink(size);

		return EnumActionResult.SUCCESS;
	}
}
