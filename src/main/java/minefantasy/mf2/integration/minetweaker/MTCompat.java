package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.integration.minetweaker.helpers.MaterialExpansion;
import minefantasy.mf2.integration.minetweaker.tweakers.*;
import minetweaker.IRecipeRemover;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;

public class MTCompat implements IRecipeRemover {

    private static final String[] COMMAND_DESC = {
            "MineFantasy commands:",
            "   /minetweaker mf materials",
            "   Lists all MF materials",
            "   /minetweaker mf skills",
            "   Lists all MF skills"
    };

    public static void loadTweakers() {
        MineTweakerAPI.registerClass(Anvil.class);
        MineTweakerAPI.registerClass(Bloomery.class);
        MineTweakerAPI.registerClass(BigFurnace.class);
        MineTweakerAPI.registerClass(BlastFurnace.class);
        MineTweakerAPI.registerClass(CarpentersBench.class);
        MineTweakerAPI.registerClass(CustomMaterialHandler.class);
        MineTweakerAPI.registerClass(Cooking.class);
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Forge.class);
        MineTweakerAPI.registerClass(MaterialExpansion.class);
        MineTweakerAPI.registerClass(TanningRack.class);
        MineTweakerAPI.registerClass(Quern.class);
        MineTweakerAPI.registerClass(SalvageTweaker.class);
    }

    public static void registerCommands() {
        MineTweakerAPI.server.addMineTweakerCommand("mf", COMMAND_DESC, new MTCommands());
    }

    @Override
    public void remove(IIngredient iIngredient) {
        Bloomery.remove(iIngredient, null);
        Quern.remove(iIngredient, null);
        TanningRack.remove(iIngredient, null);
    }
}
