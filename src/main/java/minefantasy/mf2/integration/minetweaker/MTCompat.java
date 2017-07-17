package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.integration.minetweaker.tweakers.Anvil;
import minefantasy.mf2.integration.minetweaker.tweakers.Bloomery;
import minefantasy.mf2.integration.minetweaker.tweakers.CarpentersBench;
import minefantasy.mf2.integration.minetweaker.tweakers.Crucible;
import minefantasy.mf2.integration.minetweaker.tweakers.Forge;
import minefantasy.mf2.integration.minetweaker.tweakers.TanningRack;
import minetweaker.MineTweakerAPI;

/* 
 * Based on @Coolmanzz2 MineFantasyTweaker mod
 * https://github.com/Coolmanzz2/MineFantasyTweaker/
 */

public class MTCompat {

	private static final Class<?>[] tweakers = { Anvil.class, Bloomery.class, CarpentersBench.class, Crucible.class,
			Forge.class, TanningRack.class };

	public static void loadTweakers() {
		for (Class<?> tweaker : tweakers) {
			MineTweakerAPI.registerClass(tweaker);
		}
	}
}
