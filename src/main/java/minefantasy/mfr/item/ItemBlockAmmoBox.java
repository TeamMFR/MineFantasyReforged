package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.IMaterialSingleComponent;
import minefantasy.mfr.api.tool.IStorageBlock;
import minefantasy.mfr.block.BlockAmmoBox;
import minefantasy.mfr.block.BlockTileEntity;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.tile.TileEntityAmmoBox;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemBlockAmmoBox extends ItemBlock implements IStorageBlock, IMaterialSingleComponent {


	public ItemBlockAmmoBox(BlockTileEntity block) {
		super(block);

		//noinspection ConstantConditions
		setRegistryName(block.getRegistryName());

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack item, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (item.hasTagCompound() && item.getTagCompound().hasKey(BlockAmmoBox.NBT_Ammo) && item.getTagCompound().hasKey(BlockAmmoBox.NBT_Stock)) {
			ItemStack ammo = new ItemStack(item.getTagCompound().getCompoundTag(BlockAmmoBox.NBT_Ammo));
			int stock = item.getTagCompound().getInteger(BlockAmmoBox.NBT_Stock);
			if (!ammo.isEmpty()) {
				tooltip.add(ammo.getDisplayName() + " x" + stock);
			}
		}
		CustomMaterial material = CustomMaterialRegistry.getMaterialFor(item, CustomToolHelper.slot_main);
		if (material != CustomMaterialRegistry.NONE) {
			tooltip.add(I18n.format("attribute.box.capacity.name",
					TileEntityAmmoBox.getCapacity(material.getTier())));
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
	public CustomMaterialType getMaterialType() {
		return CustomMaterialType.WOOD_MATERIAL;
	}
}
