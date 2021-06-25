package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.tile.TileEntityComponent;
import minefantasy.mfr.util.CustomToolHelper;
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
	String blockTexture;
	String storageType;

	private float unitCount = 1;
	private boolean isCustom = false;
	String materialType = CustomMaterial.NONE.name;

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
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (storageType == null) {
			return EnumActionResult.FAIL;
		}
		if (world.getTileEntity(pos) != null && !(world.getTileEntity(pos) instanceof TileEntityComponent)){
			return EnumActionResult.FAIL;
		}

		EnumFacing facingForPlacement = EnumFacing.getDirectionFromEntityLiving(pos, player);

		if (facingForPlacement != EnumFacing.UP && world.getBlockState(pos).getBlock() instanceof BlockComponent){
			return EnumActionResult.FAIL;
		}

		ItemStack stack = player.getHeldItem(hand);

		if (player.canPlayerEdit(pos.offset(facingForPlacement), facing, stack)) {
			int size = BlockComponent.placeComponent(player, stack, world, pos.offset(facingForPlacement), facing, hitX, hitY, hitZ, player, hand, storageType, blockTexture);
			if (!player.capabilities.isCreativeMode){
				stack.shrink(size);
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}
}
