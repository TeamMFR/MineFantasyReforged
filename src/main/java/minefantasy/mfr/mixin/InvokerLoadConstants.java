package minefantasy.mfr.mixin;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.File;
import java.io.IOException;

@Mixin(CraftingHelper.class)
public interface InvokerLoadConstants {

	@SuppressWarnings("UnusedReturnValue")
	@Invoker("loadContext")
	static JsonContext loadContext(JsonContext ctx, File file) throws IOException {
		throw new IOException("Error loading constants from file: " + file.getAbsolutePath());
	}
}
