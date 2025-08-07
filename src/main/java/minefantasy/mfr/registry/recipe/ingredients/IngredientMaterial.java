package minefantasy.mfr.registry.recipe.ingredients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import minefantasy.mfr.api.crafting.IMaterialComponent;
import minefantasy.mfr.api.crafting.IMaterialDoubleComponent;
import minefantasy.mfr.constants.Rarity;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IngredientMaterial extends Ingredient implements IIngredientCount{
	private final CustomMaterial requiredMaterial;
	private final List<CustomMaterial> excludedMaterials;
	private final int count;
	private ItemStack[] array = null;

	public IngredientMaterial(
			CustomMaterial requiredMaterial, List<CustomMaterial> excludedMaterials,
			int count, ItemStack... stacks) {
		super(stacks);
		this.requiredMaterial = requiredMaterial;
		this.excludedMaterials = excludedMaterials;
		this.count = count;
	}

	@Override
	public ItemStack[] getMatchingStacks() {
		if (array == null) {
			List<ItemStack> matchingStacks = Arrays.stream(super.getMatchingStacks()).map(s -> new ItemStack(s.serializeNBT())).collect(Collectors.toList());
			matchingStacks.forEach(s -> s.setCount(count));
			array = matchingStacks.toArray(new ItemStack[matchingStacks.size()]);
		}
		return array;
	}

	@Override
	public boolean apply(@Nullable ItemStack stack) {
		if (stack != null && super.apply(stack)) {
			CustomMaterial stackMaterial = CustomToolHelper.getCustomPrimaryMaterial(stack);
			return allRequirementsMatch(requiredMaterial, excludedMaterials, stackMaterial);
		}
		return false;
	}

	private static boolean allRequirementsMatch(
			CustomMaterial requiredMaterial, 
			List<CustomMaterial> excludedMaterials, 
			CustomMaterial inputMaterial) {

		if (excludedMaterials.contains(inputMaterial)) {
			return false;
		}
		else if (requiredMaterial.getHardness() != null && inputMaterial.getHardness() < requiredMaterial.getHardness()) {
			return false;
		}
		else if (requiredMaterial.getDurability() != null && inputMaterial.getDurability() < requiredMaterial.getDurability()) {
			return false;
		}
		else if (requiredMaterial.getFlexibility() != null && inputMaterial.getFlexibility() < requiredMaterial.getFlexibility()) {
			return false;
		}
		else if (requiredMaterial.getSharpness() != null && inputMaterial.getSharpness() < requiredMaterial.getSharpness()) {
			return false;
		}
		else if (requiredMaterial.getResistance() != null && inputMaterial.getResistance() < requiredMaterial.getResistance()) {
			return false;
		}
		else if (requiredMaterial.getDensity() != null && inputMaterial.getDensity() < requiredMaterial.getDensity()) {
			return false;
		}
		else if (requiredMaterial.getTier() != null && inputMaterial.getTier() < requiredMaterial.getTier()) {
			return false;
		}
		else if (requiredMaterial.getRarity() != null && inputMaterial.getRarity().getRarityValue() < requiredMaterial.getRarity().getRarityValue()) {
			return false;
		}
		else if (requiredMaterial.getEnchantability() != null && inputMaterial.getEnchantability() < requiredMaterial.getEnchantability()) {
			return false;
		}
		else if (requiredMaterial.getCrafterTier() != null && inputMaterial.getCrafterTier() < requiredMaterial.getCrafterTier()) {
			return false;
		}
		else if (requiredMaterial.getCrafterAnvilTier() != null && inputMaterial.getCrafterAnvilTier() < requiredMaterial.getCrafterAnvilTier()) {
			return false;
		}
		else if (requiredMaterial.getCraftTimeModifier() != null && inputMaterial.getCraftTimeModifier() < requiredMaterial.getCraftTimeModifier()) {
			return false;
		}
		else if (requiredMaterial.getMeltingPoint() != null && inputMaterial.getMeltingPoint() < requiredMaterial.getMeltingPoint()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public int getCount() {
		return count;
	}

	public CustomMaterial getRequiredMaterial() {
		return requiredMaterial;
	}

	public List<CustomMaterial> getExcludedMaterials() {
		return excludedMaterials;
	}

	public static class IngredientMaterialFactory implements IIngredientFactory {

		public IngredientMaterialFactory() {
		}

		@Nonnull
		@Override
		public Ingredient parse(JsonContext context, JsonObject json) {
			ItemStack stack = CraftingHelper.getItemStack(json, context);
			CustomMaterialType type = CustomMaterialType.deserialize(JsonUtils.getString(json, "material_type"));
			Float required_hardness = nullableFloat(JsonUtils.getFloat(json, "required_hardness", -1));
			Float required_durability = nullableFloat(JsonUtils.getFloat(json, "required_durability",-1));
			Float required_flexibility = nullableFloat(JsonUtils.getFloat(json, "required_flexibility",-1));
			Float required_sharpness = nullableFloat(JsonUtils.getFloat(json, "required_sharpness",-1));
			Float required_resistance = nullableFloat(JsonUtils.getFloat(json, "required_resistance",-1));
			Float required_density = nullableFloat(JsonUtils.getFloat(json, "required_density",-1));
			Integer required_tier = nullableInteger(JsonUtils.getInt(json, "required_tier",-1));
			Integer required_enchantability = nullableInteger(JsonUtils.getInt(json, "required_enchantability",-1));
			Integer required_crafter_tier = nullableInteger(JsonUtils.getInt(json, "required_crafter_tier",-1));
			Integer required_crafter_anvil_tier = nullableInteger(JsonUtils.getInt(json, "required_crafter_anvil_tier",-1));
			Float required_craft_time_modifier = nullableFloat(JsonUtils.getFloat(json, "required_craft_time_modifier",-1));
			Integer required_melting_point = nullableInteger(JsonUtils.getInt(json, "required_melting_point",-1));
			int count = JsonUtils.getInt(json, "count", 1);

			String required_rarity_string = JsonUtils.getString(json, "required_rarity","");
			Rarity required_rarity = StringUtils.isNotBlank(required_rarity_string)
					? Rarity.valueOf(required_rarity_string)
					: null;

			JsonArray excluded_materials = JsonUtils.getJsonArray(json, "excluded_materials", null);
			List<CustomMaterial> excludedMaterials = new ArrayList<>();
			if (excluded_materials != null) {
				excludedMaterials = StreamSupport
						.stream(excluded_materials
								.spliterator(), false)
						.map(JsonElement::getAsString)
						.map(CustomMaterialRegistry::getMaterial)
						.collect(Collectors.toList());
			}

			CustomMaterial requiredMaterial = new CustomMaterial("requiredMaterial", type, null, 
					null, required_hardness, required_durability, required_flexibility, required_sharpness,
					required_resistance, required_density, required_tier, required_rarity, required_enchantability,
					required_crafter_tier, required_crafter_anvil_tier, required_craft_time_modifier,
					required_melting_point, null, null);

			List<ItemStack> matchingStacks = new ArrayList<>();
			if (stack.getItem() instanceof IMaterialComponent) {
				if (stack.getItem() instanceof IMaterialDoubleComponent) {
					CustomMaterialType otherType = CustomMaterialType.METAL_MATERIAL.equals(type)
							? CustomMaterialType.WOOD_MATERIAL
							: CustomMaterialType.METAL_MATERIAL;

					for (CustomMaterial secondaryMaterial : CustomMaterialRegistry.getList(otherType)) {
						for (CustomMaterial mainMaterial : CustomMaterialRegistry.getList(type)) {
							if (allRequirementsMatch(requiredMaterial, excludedMaterials, mainMaterial)) {
								matchingStacks.add(CustomToolHelper
										.construct(stack.getItem(), mainMaterial, secondaryMaterial));
							}
						}
					}
				}
				else {
					for (CustomMaterial mainMaterial : CustomMaterialRegistry.getList(type)) {
						if (allRequirementsMatch(requiredMaterial, excludedMaterials, mainMaterial)) {
							matchingStacks.add(CustomToolHelper.constructMainSlot(stack.getItem(), mainMaterial));
						}
					}
				}
			}

			return new IngredientMaterial(requiredMaterial, excludedMaterials, count, matchingStacks.toArray(new ItemStack[0]));
		}

		private Float nullableFloat(float nullableFloat) {
			if (nullableFloat == -1) {
				return null;
			}
			else {
				return nullableFloat;
			}
		}

		private Integer nullableInteger(int nullableInt) {
			if (nullableInt == -1) {
				return null;
			}
			else {
				return nullableInt;
			}
		}
	}
}
