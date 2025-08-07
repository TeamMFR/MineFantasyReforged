package minefantasy.mfr.item;

import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemMetalComponent extends ItemComponentMFR {
	private final String name;
	private final float mass;
	private boolean canDamage = false;

	public ItemMetalComponent(String name, float mass) {
		super(name, CustomMaterialType.METAL_MATERIAL);
		this.name = name;

		this.setCreativeTab(CreativeTabs.MATERIALS);
		this.mass = mass;
	}

	public ItemMetalComponent setCanDamage() {
		this.canDamage = true;
		this.setHasSubtypes(false);
		return this;
	}

	@Override
	public boolean isDamageable() {
		return canDamage;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {

			ArrayList<CustomMaterial> metals = CustomMaterialRegistry.getList(CustomMaterialType.METAL_MATERIAL);
			for (CustomMaterial metal : metals) {
				items.add(this.createComponentItemStack(metal.getName()));
			}
		}
	}

	public float getWeightInKg(ItemStack tool) {
		CustomMaterial base = getBase(tool);
		if (base != CustomMaterialRegistry.NONE) {
			return base.getDensity() * mass;
		}
		return mass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack tool, World world, List<String> list, ITooltipFlag flag) {
		super.addInformation(tool, world, list, flag);
		if (!canDamage) {
			CustomToolHelper.addComponentString(list, getBase(tool), mass);
		}
		if (this == MineFantasyItems.COGWORK_ARMOUR) {
			int AR = EntityCogwork.getArmourRating(getBase(tool));
			list.add(I18n.format("attribute.armour.protection") + " " + AR);
			if (mass > 0)
				list.add(CustomMaterialRegistry.getWeightString(getWeightInKg(tool)));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack tool) {
		return CustomToolHelper.getLocalisedName(tool, "item.commodity_" + name + ".name");
	}

	public CustomMaterial getBase(ItemStack component) {
		return CustomToolHelper.getCustomPrimaryMaterial(component);
	}

	public ItemStack createComponentItemStack(String base) {
		return createComponentItemStack(base, 1);
	}

	public ItemStack createComponentItemStack(String base, int stack) {
		return createComponentItemStack(base, stack, 0);
	}

	public ItemStack createComponentItemStack(String base, int stack, float damage) {
		ItemStack item = new ItemStack(this, stack);
		CustomMaterialRegistry.addMaterial(item, CustomToolHelper.slot_main, base);
		int maxdam = this.getMaxDamage(item);

		item.setItemDamage((int) (maxdam * damage));
		return item;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		if (canDamage) {
			return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
		}
		return super.getMaxDamage(stack);
	}

	public ItemStack createComponentItemStack(String base, int stack, int damage) {
		ItemStack item = new ItemStack(this, stack, damage);
		CustomMaterialRegistry.addMaterial(item, CustomToolHelper.slot_main, base);
		return item;
	}
}