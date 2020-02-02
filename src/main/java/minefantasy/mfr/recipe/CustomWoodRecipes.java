package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.block.decor.BlockWoodDecor;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomWoodRecipes {
    public static final String basic = "step.wood";
    public static final String chopping = "dig.wood";
    public static final String sawing = "minefantasy2:block.sawcarpenter";
    public static final String nailHammer = "minefantasy2:block.hammercarpenter";
    public static final String woodHammer = "minefantasy2:block.carpentermallet";
    private static final Skill construction = SkillList.construction;

    public static void init() {
        ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
        Iterator iterator = wood.iterator();
        while (iterator.hasNext()) {
            CustomMaterial customMat = (CustomMaterial) iterator.next();
            addRecipes(customMat);
        }
    }

    private static void addRecipes(CustomMaterial material) {
        ItemStack timber = ComponentListMFR.plank.construct(material.name);
        ItemStack cutPlank = ((ItemComponentMFR) ComponentListMFR.plank_cut).construct(material.name);
        ItemStack woodpane = ((ItemComponentMFR) ComponentListMFR.plank_pane).construct(material.name);

        ItemStack result;
        int time;

        time = 5;
        KnowledgeListMFR.sawnPlankR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, cutPlank, "", sawing, "saw",
                material.tier, (int) (material.durability * time), new Object[]{"T", 'T', timber,}));

        time = 20;
        KnowledgeListMFR.plankPaneR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, woodpane, "", nailHammer,
                "hammer", material.tier, (int) (material.durability * time),
                new Object[]{"NNN", "CTC", "CTC", 'T', timber, 'C', cutPlank, 'N', ComponentListMFR.nail,}));
        Salvage.addSalvage(woodpane, cutPlank, cutPlank, cutPlank, cutPlank, timber, timber,
                new ItemStack(ComponentListMFR.nail, 3));

        time = 10;
        result = ((BlockWoodDecor) BlockListMFR.RACK_WOOD).construct(material.name);
        KnowledgeListMFR.rackRecipe.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "", nailHammer, "hammer",
                material.tier, (int) (material.durability * time),
                new Object[]{"N N", "T T", "CCC", "T T", 'N', ComponentListMFR.nail, 'T', timber, 'C', cutPlank,}));
        Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, timber, timber, timber, timber,
                new ItemStack(ComponentListMFR.nail, 2));

        time = 20;
        result = ((BlockWoodDecor) BlockListMFR.FOOD_BOX_BASIC).construct(material.name);
        KnowledgeListMFR.foodboxR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "food_box", nailHammer,
                "hammer", material.tier, (int) (material.durability * time), new Object[]{"HC ", "C C", "NCN", 'N',
                        ComponentListMFR.nail, 'H', ComponentListMFR.hinge, 'C', cutPlank,}));
        Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, cutPlank, new ItemStack(ComponentListMFR.nail, 2),
                ComponentListMFR.hinge);

        time = 30;
        result = ((BlockWoodDecor) BlockListMFR.AMMO_BOX_BASIC).construct(material.name);
        KnowledgeListMFR.ammoboxR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "ammo_box", nailHammer,
                "hammer", material.tier, (int) (material.durability * time), new Object[]{"HPH", "C C", "NPN", 'N',
                        ComponentListMFR.nail, 'H', ComponentListMFR.hinge, 'P', woodpane, 'C', cutPlank,}));
        Salvage.addSalvage(result, cutPlank, cutPlank, woodpane, woodpane, new ItemStack(ComponentListMFR.nail, 2),
                new ItemStack(ComponentListMFR.hinge, 2));

        time = 50;
        result = ((BlockWoodDecor) BlockListMFR.CRATE_BASIC).construct(material.name);
        KnowledgeListMFR.bigboxR.add(
                MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "big_box", nailHammer, "hammer", material.tier,
                        (int) (material.durability * time), new Object[]{"NNNN", "HPPH", "P  P", " PP ", 'N',
                                ComponentListMFR.nail, 'H', ComponentListMFR.hinge, 'P', woodpane, 'C', cutPlank,}));
        Salvage.addSalvage(result, woodpane, woodpane, woodpane, woodpane, woodpane, woodpane,
                new ItemStack(ComponentListMFR.nail, 4), new ItemStack(ComponentListMFR.hinge, 2));

        time = 15;
        result = ((BlockWoodDecor) BlockListMFR.TROUGH_WOOD).construct(material.name);
        KnowledgeListMFR.nailTroughR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction, result, "", nailHammer,
                "hammer", material.tier, (int) (material.durability * time),
                new Object[]{"P P", "PPP", "NNN", 'N', ComponentListMFR.nail, 'P', timber,}));
        Salvage.addSalvage(result, timber, timber, timber, timber, timber, new ItemStack(ComponentListMFR.nail, 3));

    }

}
