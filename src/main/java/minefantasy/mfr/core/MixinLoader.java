package minefantasy.mfr.core;

import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MixinLoader implements IEarlyMixinLoader {
	@Override
	public List<String> getMixinConfigs() {
		ArrayList<String> ret = new ArrayList<>();
		ret.add("mixins.minefantasyreforged.json");
		return ret;
	}
}
