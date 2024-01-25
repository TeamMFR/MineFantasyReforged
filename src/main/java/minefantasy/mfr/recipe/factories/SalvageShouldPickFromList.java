package minefantasy.mfr.recipe.factories;

import com.google.gson.JsonObject;
import minefantasy.mfr.config.ConfigCrafting;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class SalvageShouldPickFromList implements IConditionFactory {
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> ConfigCrafting.shouldSalvagePickFromList;
	}
}