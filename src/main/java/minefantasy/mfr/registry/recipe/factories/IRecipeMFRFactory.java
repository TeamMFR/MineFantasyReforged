package minefantasy.mfr.registry.recipe.factories;

import com.google.gson.JsonObject;
import minefantasy.mfr.registry.recipe.IRecipeMFR;
import net.minecraftforge.common.crafting.JsonContext;

public interface IRecipeMFRFactory<T extends IRecipeMFR> {

	T parse(JsonContext context, JsonObject json);
}
