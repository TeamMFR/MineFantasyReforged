package minefantasy.mf2.integration.minetweaker;

import minetweaker.MineTweakerAPI;

public class MTCompat {

	private static final Class<?>[] tweakers = { /*QuernTweaker.class,*/ BloomTweaker.class };

	public static void loadTweakers() {
		for (Class<?> tweaker : tweakers) {
			MineTweakerAPI.registerClass(tweaker);
		}
	}
}
