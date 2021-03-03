package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemComponentMFR extends ItemBaseMFR implements ITieredComponent {
	protected String name;
	protected int itemRarity;
	// STORAGE
	protected String blocktex;
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
	private String materialType = null;

	public ItemComponentMFR(String name) {
		this(name, 0);
	}

	public ItemComponentMFR(String name, int rarity) {
		super(name);
		itemRarity = rarity;
		this.name = name;
		this.setCreativeTab(MineFantasyTabs.tabMaterials);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		if (this.getCreativeTab() != MineFantasyTabs.tabMaterials) {
			super.getSubItems(tab, items);
			return;
		}

		if (this != MineFantasyItems.TIMBER) {
			return;
		}

		for (Item ingot : MineFantasyItems.INGOTS) {
			if (ingot == MineFantasyItems.INGOTS[3]) {
				add(items, Items.IRON_INGOT);
			}
			if (ingot == MineFantasyItems.INGOTS[6]) {
				add(items, Items.GOLD_INGOT);
			}
			add(items, ingot);
		}

		if (isCustom) {
			ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
			ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
			for (CustomMaterial customMat : wood) {
				items.add(this.construct(customMat.name));
				items.add(((ItemComponentMFR) MineFantasyItems.TIMBER_PANE).construct(customMat.name));
			}
			for (CustomMaterial customMat : metal) {
				items.add(MineFantasyItems.bar(customMat.name));
			}
		}

		add(items, MineFantasyItems.NAIL);
		add(items, MineFantasyItems.RIVET);
		add(items, MineFantasyItems.THREAD);
		add(items, MineFantasyItems.CLAY_POT_UNCOOKED);
		add(items, MineFantasyItems.CLAY_POT);
		add(items, MineFantasyItems.INGOT_MOULD_UNCOOKED);
		add(items, MineFantasyItems.INGOT_MOULD);

		add(items, MineFantasyItems.LEATHER_STRIP);
		add(items, MineFantasyItems.RAWHIDE_SMALL);
		add(items, MineFantasyItems.RAWHIDE_MEDIUM);
		add(items, MineFantasyItems.RAWHIDE_LARGE);
		add(items, MineFantasyItems.HIDE_SMALL);
		add(items, MineFantasyItems.HIDE_MEDIUM);
		add(items, MineFantasyItems.HIDE_LARGE);

		add(items, MineFantasyItems.ORE_COPPER);
		add(items, MineFantasyItems.ORE_TIN);
		add(items, MineFantasyItems.ORE_IRON);
		add(items, MineFantasyItems.ORE_SILVER);
		add(items, MineFantasyItems.ORE_GOLD);
		add(items, MineFantasyItems.ORE_TUNGSTEN);

		add(items, MineFantasyItems.FLUX);
		add(items, MineFantasyItems.FLUX_STRONG);
		add(items, MineFantasyItems.FLUX_POT);
		add(items, MineFantasyItems.COAL_FLUX);
		add(items, MineFantasyItems.COKE);
		add(items, MineFantasyItems.DIAMOND_SHARDS);
		add(items, MineFantasyItems.FLETCHING);
		add(items, MineFantasyItems.PLANT_OIL);

		add(items, MineFantasyItems.COAL_DUST);
		add(items, MineFantasyItems.IRON_PREP);
		add(items, MineFantasyItems.OBSIDIAN_ROCK);
		add(items, MineFantasyItems.SULFUR);
		add(items, MineFantasyItems.NITRE);
		add(items, MineFantasyItems.BLACKPOWDER);
		add(items, MineFantasyItems.BLACKPOWDER_ADVANCED);
		add(items, MineFantasyItems.BOMB_FUSE);
		add(items, MineFantasyItems.BOMB_FUSE_LONG);
		add(items, MineFantasyItems.SHRAPNEL);
		add(items, MineFantasyItems.MAGMA_CREAM_REFINED);
		add(items, MineFantasyItems.BOMB_CASING_UNCOOKED);
		add(items, MineFantasyItems.BOMB_CASING);
		add(items, MineFantasyItems.MINE_CASING_UNCOOKED);
		add(items, MineFantasyItems.MINE_CASING);
		add(items, MineFantasyItems.BOMB_CASING_IRON);
		add(items, MineFantasyItems.MINE_CASING_IRON);
		add(items, MineFantasyItems.BOMB_CASING_OBSIDIAN);
		add(items, MineFantasyItems.MINE_CASING_OBSIDIAN);
		add(items, MineFantasyItems.BOMB_CASING_CRYSTAL);
		add(items, MineFantasyItems.MINE_CASING_CRYSTAL);
		add(items, MineFantasyItems.BOMB_CASING_ARROW);
		add(items, MineFantasyItems.BOMB_CASING_BOLT);

		add(items, MineFantasyItems.CLAY_BRICK);
		add(items, MineFantasyItems.KAOLINITE);
		add(items, MineFantasyItems.KAOLINITE_DUST);
		add(items, MineFantasyItems.FIRECLAY);
		add(items, MineFantasyItems.FIRECLAY_BRICK);
		add(items, MineFantasyItems.STRONG_BRICK);
		add(items, MineFantasyItems.DRAGON_HEART);
		add(items, MineFantasyItems.ORNATE_ITEMS);

		add(items, MineFantasyItems.TALISMAN_LESSER);
		add(items, MineFantasyItems.TALISMAN_GREATER);

		add(items, MineFantasyItems.BOLT);
		add(items, MineFantasyItems.IRON_FRAME);
		add(items, MineFantasyItems.IRON_STRUT);
		add(items, MineFantasyItems.STEEL_TUBE);
		add(items, MineFantasyItems.BRONZE_GEARS);
		add(items, MineFantasyItems.TUNGSTEN_GEARS);
		add(items, MineFantasyItems.COGWORK_SHAFT);
		add(items, MineFantasyItems.COMPOSITE_ALLOY_INGOT);

		add(items, MineFantasyItems.CROSSBOW_HANDLE_WOOD);
		add(items, MineFantasyItems.CROSSBOW_STOCK_WOOD);
		add(items, MineFantasyItems.CROSSBOW_STOCK_IRON);

		add(items, MineFantasyItems.CROSSBOW_ARMS_BASIC);
		add(items, MineFantasyItems.CROSSBOW_ARMS_LIGHT);
		add(items, MineFantasyItems.CROSSBOW_ARMS_HEAVY);
		add(items, MineFantasyItems.CROSSBOW_ARMS_ADVANCED);

		add(items, MineFantasyItems.CROSSBOW_AMMO);
		add(items, MineFantasyItems.CROSSBOW_SCOPE);
		add(items, MineFantasyItems.CROSSBOW_BAYONET);

		add(items, MineFantasyItems.COPPER_COIN);
		add(items, MineFantasyItems.SILVER_COIN);
		add(items, MineFantasyItems.GOLD_COIN);
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

	public ItemStack construct(String main, int stacksize) {
		return CustomToolHelper.constructSingleColoredLayer(this, main, stacksize);
	}
	// ====================================================== CUSTOM END
	// ==============================================================\\

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack item) {
		if (isCustom) {
			return CustomToolHelper.getLocalisedName(item, "item.commodity_" + name + ".name");
		}
		String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
		return CustomToolHelper.getLocalisedName(item, unlocalName);
	}

	@Override
	public String getMaterialType(ItemStack item) {
		return isCustom ? materialType : null;
	}

	public ItemComponentMFR setStoragePlacement(String type, String tex) {
		this.blocktex = tex;
		this.storageType = type;
		return this;
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

		int size = BlockComponent.placeComponent(player, stack, world, pos, storageType, blocktex);

		stack.shrink(size);

		return EnumActionResult.SUCCESS;
	}
}
