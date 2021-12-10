package minefantasy.mfr.mixin;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("MinefantasyReforged")
public class MFRCoreMod implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Nullable
	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins." + MineFantasyReforged.MOD_ID + ".json");
		MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
