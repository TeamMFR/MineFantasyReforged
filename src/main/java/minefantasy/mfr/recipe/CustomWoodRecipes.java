package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.block.BlockWoodDecor;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.item.ItemComponentMFR;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;

public class CustomWoodRecipes {
    public static final String basic = "step.wood";
    public static final String chopping = "dig.wood";
    public static final SoundEvent sawing = MineFantasySounds.SAW_CARPENTER;
    public static final SoundEvent nailHammer = MineFantasySounds.HAMMER_CARPENTER;
    public static final SoundEvent woodHammer = MineFantasySounds.CARPENTER_MALLET;
    private static final Skill construction = SkillList.construction;

    public static void init() {
        ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
        for (CustomMaterial customMat : wood) {
            addRecipes(customMat);
        }
    }

    private static void addRecipes(CustomMaterial material) {
        ItemStack timber = ComponentListMFR.TIMBER.construct(material.name);
        ItemStack cutPlank = ((ItemComponentMFR) ComponentListMFR.TIMBER_CUT).construct(material.name);
        ItemStack woodpane = ((ItemComponentMFR) ComponentListMFR.TIMBER_PANE).construct(material.name);

        ItemStack result;
        int time;

        time = 5;
        KnowledgeListMFR.sawnPlankR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, cutPlank, "", sawing, "saw", material.tier, (int) (material.durability * time), "T", 'T', timber));

        time = 20;
        KnowledgeListMFR.plankPaneR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, woodpane, "", nailHammer, "hammer", material.tier, (int) (material.durability * time), "NNN", "CTC", "CTC", 'T', timber, 'C', cutPlank, 'N', ComponentListMFR.NAIL));
        Salvage.addSalvage(woodpane, cutPlank, cutPlank, cutPlank, cutPlank, timber, timber, new ItemStack(ComponentListMFR.NAIL, 3));

        time = 10;
        result = ((BlockWoodDecor) MineFantasyBlocks.TOOL_RACK_WOOD).construct(material.name);
        KnowledgeListMFR.rackRecipe.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "", nailHammer, "hammer", material.tier, (int) (material.durability * time), "N N", "T T", "CCC", "T T", 'N', ComponentListMFR.NAIL, 'T', timber, 'C', cutPlank));
        Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, timber, timber, timber, timber,
                new ItemStack(ComponentListMFR.NAIL, 2));

        time = 20;
        result = ((BlockWoodDecor) MineFantasyBlocks.FOOD_BOX_BASIC).construct(material.name);
        KnowledgeListMFR.foodboxR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "food_box", nailHammer, "hammer", material.tier, (int) (material.durability * time), "HC ", "C C", "NCN", 'N', ComponentListMFR.NAIL, 'H', ComponentListMFR.HINGE, 'C', cutPlank));
        Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, cutPlank, new ItemStack(ComponentListMFR.NAIL, 2), ComponentListMFR.HINGE);

        time = 30;
        result = ((BlockWoodDecor) MineFantasyBlocks.AMMO_BOX_BASIC).construct(material.name);
        KnowledgeListMFR.ammoboxR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "ammo_box", nailHammer, "hammer", material.tier, (int) (material.durability * time), "HPH", "C C", "NPN", 'N', ComponentListMFR.NAIL, 'H', ComponentListMFR.HINGE, 'P', woodpane, 'C', cutPlank));
        Salvage.addSalvage(result, cutPlank, cutPlank, woodpane, woodpane, new ItemStack(ComponentListMFR.NAIL, 2), new ItemStack(ComponentListMFR.HINGE, 2));

        time = 50;
        result = ((BlockWoodDecor) MineFantasyBlocks.CRATE_BASIC).construct(material.name);
        KnowledgeListMFR.bigboxR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "big_box", nailHammer, "hammer", material.tier, (int) (material.durability * time), "NNNN", "HPPH", "P  P", " PP ", 'N', ComponentListMFR.NAIL, 'H', ComponentListMFR.HINGE, 'P', woodpane, 'C', cutPlank));
        Salvage.addSalvage(result, woodpane, woodpane, woodpane, woodpane, woodpane, woodpane, new ItemStack(ComponentListMFR.NAIL, 4), new ItemStack(ComponentListMFR.HINGE, 2));

        time = 15;
        result = ((BlockWoodDecor) MineFantasyBlocks.TROUGH_WOOD).construct(material.name);
        KnowledgeListMFR.nailTroughR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "", nailHammer, "hammer", material.tier, (int) (material.durability * time), "P P", "PPP", "NNN", 'N', ComponentListMFR.NAIL, 'P', timber));
        Salvage.addSalvage(result, timber, timber, timber, timber, timber, new ItemStack(ComponentListMFR.NAIL, 3));

    }

}
