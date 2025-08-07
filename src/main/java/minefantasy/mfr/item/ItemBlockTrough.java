package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.IMaterialSingleComponent;
import minefantasy.mfr.api.tool.IStorageBlock;
import minefantasy.mfr.block.BlockTrough;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.tile.TileEntityTrough;
import minefantasy.mfr.util.BlockUtils;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.NbtUtils;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemBlockTrough extends ItemBlockBase implements IStorageBlock, IMaterialSingleComponent {
	private Random rand = new Random();

	public ItemBlockTrough(Block base) {
		super(base);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
		if (item.hasTagCompound() && item.getTagCompound().hasKey(BlockTrough.FILL_LEVEL)) {
			int stock = item.getTagCompound().getInteger(BlockTrough.FILL_LEVEL);
			if (stock > 0) {
				list.add(I18n.format("attribute.fill", stock));
			}
		}
		CustomMaterial material = CustomMaterialRegistry.getMaterialFor(item, CustomToolHelper.slot_main);
		if (material != CustomMaterialRegistry.NONE) {
			list.add(I18n.format("attribute.fill.capacity.name",
					TileEntityTrough.getCapacity(material.getTier()) * TileEntityTrough.getCapacityScale()));
		}
	}

	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(itemIn)) {
			return;
		}
		ArrayList<CustomMaterial> wood = CustomMaterialRegistry.getList(CustomMaterialType.WOOD_MATERIAL);
		for (CustomMaterial customMat : wood) {
			items.add(CustomToolHelper.constructMainSlot(this, customMat.getName()));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack item) {
		return CustomToolHelper.getLocalisedName(item, this.getUnlocalizedNameInefficiently(item) + ".name");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

		if (rayTraceResult == null) {
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		} else {
			if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos hit = rayTraceResult.getBlockPos();
				ItemStack item = player.getHeldItem(hand);

				if (!world.canMineBlockBody(player, hit)) {
					return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
				}

				if (!player.canPlayerEdit(hit, rayTraceResult.sideHit, item)) {
					return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
				}

				if (BlockUtils.isWaterSource(world, hit)) {
					gather(player);
					world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
					return EnumActionResult.FAIL;
				}
			}
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
	}

	private void gather(EntityPlayer player) {
		ItemStack item = player.getHeldItemMainhand();
		if (!item.isEmpty()) {
			int tier = 0;
			CustomMaterial material = CustomMaterialRegistry.getMaterialFor(item, CustomToolHelper.slot_main);
			if (material != CustomMaterialRegistry.NONE) {
				tier = material.getTier();
			}
			NBTTagCompound nbt = NbtUtils.getOrCreateNBT(item);
			nbt.setInteger(BlockTrough.FILL_LEVEL, TileEntityTrough.getCapacity(tier) * TileEntityTrough.getCapacityScale());
		}
		player.swingArm(EnumHand.MAIN_HAND);

	}

	@Override
	public CustomMaterialType getMaterialType() {
		return CustomMaterialType.WOOD_MATERIAL;
	}
}
