package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.integration.minetweaker.helpers.MaterialExpansion;
import minefantasy.mf2.integration.minetweaker.tweakers.*;
import minetweaker.MineTweakerAPI;

/* 
 * Based on @Coolmanzz2 MineFantasyTweaker mod
 * https://github.com/Coolmanzz2/MineFantasyTweaker/
 */

public class MTCompat {

	private static final Class<?>[] tweakers = { Anvil.class, Bloomery.class, CarpentersBench.class, CustomMaterialHandler.class, Crucible.class,
			Forge.class, MaterialExpansion.class, TanningRack.class };

	public static void loadTweakers() {
		for (Class<?> tweaker : tweakers) {
			MineTweakerAPI.registerClass(tweaker);
		}
	}
}
