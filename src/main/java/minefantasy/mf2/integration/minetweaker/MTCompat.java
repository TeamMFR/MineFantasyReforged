package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.integration.minetweaker.helpers.MTCommands;
import minefantasy.mf2.integration.minetweaker.helpers.MaterialExpansion;
import minefantasy.mf2.integration.minetweaker.tweakers.*;
import minetweaker.IRecipeRemover;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.minecraft.MineTweakerMC;

public class MTCompat /*implements IRecipeRemover */{

    private static final Class<?>[] tweakers = {Anvil.class, Bloomery.class, CarpentersBench.class, CustomMaterialHandler.class, Crucible.class,
            Forge.class, MaterialExpansion.class, TanningRack.class, Quern.class, SalvageTweaker.class};

    private static final String[] COMMAND_DESC = {
            "    MineFantasy commands:",
            "      /minetweaker mf materials",
            "        Lists all MF materials"
    };

    public static void loadTweakers() {
        for (Class<?> tweaker : tweakers) {
            MineTweakerAPI.registerClass(tweaker);
        }
    }

    public static void registerCommands() {
        MineTweakerAPI.server.addMineTweakerCommand("mf", COMMAND_DESC, new MTCommands());
    }

   /* @Override
    public void remove(IIngredient iIngredient) {
        Bloomery.remove(iIngredient, null);
        Quern.remove(iIngredient, null);
    }*/
}
