package minefantasy.mf2.recipe;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.decor.BlockWoodDecor;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
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
        ItemStack timber = ComponentListMF.plank.construct(material.name);
        ItemStack cutPlank = ((ItemComponentMF) ComponentListMF.plank_cut).construct(material.name);
        ItemStack woodpane = ((ItemComponentMF) ComponentListMF.plank_pane).construct(material.name);

        ItemStack result;
        int time;

        time = 5;
        KnowledgeListMF.sawnPlankR.add(MineFantasyAPI.addCarpenterRecipe(construction, cutPlank, "", sawing, "saw",
                material.tier, (int) (material.durability * time), new Object[]{"T", 'T', timber,}));

        time = 20;
        KnowledgeListMF.plankPaneR.add(MineFantasyAPI.addCarpenterRecipe(construction, woodpane, "", nailHammer,
                "hammer", material.tier, (int) (material.durability * time),
                new Object[]{"NNN", "CTC", "CTC", 'T', timber, 'C', cutPlank, 'N', ComponentListMF.nail,}));
        Salvage.addSalvage(woodpane, cutPlank, cutPlank, cutPlank, cutPlank, timber, timber,
                new ItemStack(ComponentListMF.nail, 3));

        time = 10;
        result = ((BlockWoodDecor) BlockListMF.rack_wood).construct(material.name);
        KnowledgeListMF.rackRecipe.add(MineFantasyAPI.addCarpenterRecipe(construction, result, "", nailHammer, "hammer",
                material.tier, (int) (material.durability * time),
                new Object[]{"N N", "T T", "CCC", "T T", 'N', ComponentListMF.nail, 'T', timber, 'C', cutPlank,}));
        Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, timber, timber, timber, timber,
                new ItemStack(ComponentListMF.nail, 2));

        time = 20;
        result = ((BlockWoodDecor) BlockListMF.food_box_basic).construct(material.name);
        KnowledgeListMF.foodboxR.add(MineFantasyAPI.addCarpenterRecipe(construction, result, "food_box", nailHammer,
                "hammer", material.tier, (int) (material.durability * time), new Object[]{"HC ", "C C", "NCN", 'N',
                        ComponentListMF.nail, 'H', ComponentListMF.hinge, 'C', cutPlank,}));
        Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, cutPlank, new ItemStack(ComponentListMF.nail, 2),
                ComponentListMF.hinge);

        time = 30;
        result = ((BlockWoodDecor) BlockListMF.ammo_box_basic).construct(material.name);
        KnowledgeListMF.ammoboxR.add(MineFantasyAPI.addCarpenterRecipe(construction, result, "ammo_box", nailHammer,
                "hammer", material.tier, (int) (material.durability * time), new Object[]{"HPH", "C C", "NPN", 'N',
                        ComponentListMF.nail, 'H', ComponentListMF.hinge, 'P', woodpane, 'C', cutPlank,}));
        Salvage.addSalvage(result, cutPlank, cutPlank, woodpane, woodpane, new ItemStack(ComponentListMF.nail, 2),
                new ItemStack(ComponentListMF.hinge, 2));

        time = 50;
        result = ((BlockWoodDecor) BlockListMF.crate_basic).construct(material.name);
        KnowledgeListMF.bigboxR.add(
                MineFantasyAPI.addCarpenterRecipe(construction, result, "big_box", nailHammer, "hammer", material.tier,
                        (int) (material.durability * time), new Object[]{"NNNN", "HPPH", "P  P", " PP ", 'N',
                                ComponentListMF.nail, 'H', ComponentListMF.hinge, 'P', woodpane, 'C', cutPlank,}));
        Salvage.addSalvage(result, woodpane, woodpane, woodpane, woodpane, woodpane, woodpane,
                new ItemStack(ComponentListMF.nail, 4), new ItemStack(ComponentListMF.hinge, 2));

        time = 15;
        result = ((BlockWoodDecor) BlockListMF.trough_wood).construct(material.name);
        KnowledgeListMF.nailTroughR.add(MineFantasyAPI.addCarpenterRecipe(construction, result, "", nailHammer,
                "hammer", material.tier, (int) (material.durability * time),
                new Object[]{"P P", "PPP", "NNN", 'N', ComponentListMF.nail, 'P', timber,}));
        Salvage.addSalvage(result, timber, timber, timber, timber, timber, new ItemStack(ComponentListMF.nail, 3));

    }

}
