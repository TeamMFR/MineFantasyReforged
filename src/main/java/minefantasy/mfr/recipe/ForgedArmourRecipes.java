package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.init.ArmourListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomArmourListMFR;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ForgedArmourRecipes {
    private static final Skill artisanry = SkillList.artisanry;
    private static final Skill engineering = SkillList.engineering;
    private static final Skill construction = SkillList.construction;

    public static void init() {
        addMetalComponents();
        assembleChainmail();
        assembleScalemail();
        assembleSplintmail();
        assembleFieldplate();
        assembleCogPlating();
    }

    private static void assembleChainmail() {
        Item helm = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 0);
        Item chest = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 1);
        Item legs = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 2);
        Item boots = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 3);

        Item mail = ComponentListMFR.chainmesh;
        Item rivet = ComponentListMFR.rivet;

        int time = 20;
        KnowledgeListMFR.mailHelmetR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_chain_helmet),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RMR", "MPM", "RMR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)}));
        time = 30;
        KnowledgeListMFR.mailChestR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_chain_chest),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RM MR", "RMPMR", "RM MR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)}));
        time = 20;
        KnowledgeListMFR.mailLegsR
                .add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_chain_legs),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RMPMR", "RM MR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)}));
        time = 10;
        KnowledgeListMFR.mailBootsR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_chain_boots),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"R R", "MPM",

                                'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)}));
        Salvage.addSalvage(CustomArmourListMFR.standard_chain_helmet, helm, mail, mail, mail, mail, // 4 Mail
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_chain_chest, chest, mail, mail, mail, mail, mail, mail, // 6 Mail
                rivet, rivet, rivet, rivet, rivet, rivet);// 6 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_chain_legs, legs, mail, mail, mail, mail, // 4 Mail
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_chain_boots, boots, mail, mail, // 2 Mail
                rivet, rivet);// 2 Rivet
    }

    private static void assembleScalemail() {
        Item helm = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 0);
        Item chest = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 1);
        Item legs = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 2);
        Item boots = ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 3);

        ItemStack mail = new ItemStack(ComponentListMFR.scalemesh);
        Item rivet = ComponentListMFR.rivet;

        int time = 20;
        KnowledgeListMFR.scaleHelmetR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_scale_helmet),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RMR", "MPM", "RMR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)}));
        time = 30;
        KnowledgeListMFR.scaleChestR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_scale_chest),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RM MR", "RMPMR", "RM MR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)}));
        time = 20;
        KnowledgeListMFR.scaleLegsR
                .add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_scale_legs),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RMPMR", "RM MR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)}));
        time = 10;
        KnowledgeListMFR.scaleBootsR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_scale_boots),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"R R", "MPM",

                                'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)}));
        Salvage.addSalvage(CustomArmourListMFR.standard_scale_helmet, helm, mail, mail, mail, mail, // 4 Mail
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_scale_chest, chest, mail, mail, mail, mail, mail, mail, // 6 Mail
                rivet, rivet, rivet, rivet, rivet, rivet);// 6 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_scale_legs, legs, mail, mail, mail, mail, // 4 Mail
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_scale_boots, boots, mail, mail, // 2 Mail
                rivet, rivet);// 2 Rivet
    }

    private static void assembleSplintmail() {
        Item helm = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 0);
        Item chest = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 1);
        Item legs = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 2);
        Item boots = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 3);

        ItemStack mail = new ItemStack(ComponentListMFR.splintmesh);
        Item rivet = ComponentListMFR.rivet;

        int time = 20;
        KnowledgeListMFR.splintHelmetR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_splint_helmet),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RMR", "MPM", "RMR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)}));
        time = 30;
        KnowledgeListMFR.splintChestR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_splint_chest),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RM MR", "RMPMR", "RM MR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)}));
        time = 20;
        KnowledgeListMFR.splintLegsR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_splint_legs),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"RMPMR", "RM MR",

                                'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)}));
        time = 10;
        KnowledgeListMFR.splintBootsR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_splint_boots),
                        "craftArmourMedium", true, "hammer", -1, -1, time, new Object[]{"R R", "MPM",

                                'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)}));
        Salvage.addSalvage(CustomArmourListMFR.standard_splint_helmet, helm, mail, mail, mail, mail, // 4 Mail
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_splint_chest, chest, mail, mail, mail, mail, mail, mail, // 6
                // Mail
                rivet, rivet, rivet, rivet, rivet, rivet);// 6 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_splint_legs, legs, mail, mail, mail, mail, // 4 Mail
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_splint_boots, boots, mail, mail, // 2 Mail
                rivet, rivet);// 2 Rivet
    }

    private static void assembleFieldplate() {
        Item helm = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 0);
        Item chest = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 1);
        Item legs = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 2);
        Item boots = ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 3);

        ItemStack plate = new ItemStack(ComponentListMFR.plate);
        Item rivet = ComponentListMFR.rivet;

        int time = 40;
        KnowledgeListMFR.plateHelmetR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_plate_helmet),
                        "craftArmourHeavy", false, "hammer", -1, -1, time, new Object[]{" R ", "PHP", " R ",

                                'R', rivet, 'P', plate, 'H', new ItemStack(helm, 1, 0),}));
        time = 60;
        KnowledgeListMFR.plateChestR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_plate_chest),
                        "craftArmourHeavy", false, "hammer", -1, -1, time, new Object[]{"RP PR", "RPCPR",

                                'R', rivet, 'P', plate, 'C', new ItemStack(chest, 1, 0),}));
        time = 40;
        KnowledgeListMFR.plateLegsR
                .add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_plate_legs),
                        "craftArmourHeavy", false, "hammer", -1, -1, time, new Object[]{"RPLPR", "RP PR",

                                'R', rivet, 'P', plate, 'L', new ItemStack(legs, 1, 0),}));
        time = 20;
        KnowledgeListMFR.plateBootsR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.standard_plate_boots),
                        "craftArmourHeavy", false, "hammer", -1, -1, time, new Object[]{"R R", "PBP",

                                'R', rivet, 'P', plate, 'B', new ItemStack(boots, 1, 0),}));

        Salvage.addSalvage(CustomArmourListMFR.standard_plate_helmet, helm, plate, plate, // 2 Plate
                rivet, rivet);// Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_plate_chest, chest, plate, plate, plate, plate, // 4 Plate
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_plate_legs, legs, plate, plate, plate, plate, // 4 Plate
                rivet, rivet, rivet, rivet);// 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.standard_plate_boots, boots, plate, plate, // 2 Plate
                rivet, rivet);// 2 Rivet

    }

    private static void assembleCogPlating() {

        ItemStack minorPiece = new ItemStack(ComponentListMFR.plate);
        ItemStack majorPiece = new ItemStack(ComponentListMFR.plate_huge);

        int time = 4;
        KnowledgeListMFR.hugePlateR
                .add(MineFantasyRebornAPI.addAnvilToolRecipe(engineering, majorPiece, "cogArmour", true, "hvyhammer", -1, -1,
                        time, new Object[]{" RR ", "RIIR", 'R', ComponentListMFR.rivet, 'I', minorPiece}));

        Salvage.addSalvage(majorPiece, minorPiece, minorPiece, new ItemStack(ComponentListMFR.rivet, 4));

        time = 25;
        KnowledgeListMFR.cogPlateR
                .add(MineFantasyRebornAPI.addAnvilToolRecipe(engineering, new ItemStack(ComponentListMFR.cogwork_armour),
                        "cogArmour", true, "hvyhammer", -1, -1, time, new Object[]{"  P  ", "pPPPp", "p P p", " pPp ",

                                'p', minorPiece, 'P', majorPiece,}));

        Salvage.addSalvage(ComponentListMFR.cogwork_armour, minorPiece, minorPiece, minorPiece, minorPiece, minorPiece,
                minorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece);
    }

    private static void addMetalComponents() {
        Item hunk = ComponentListMFR.metalHunk;
        Item bar = ComponentListMFR.bar;

        int time = 3;
        KnowledgeListMFR.mailRecipes
                .add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.chainmesh), "", true,
                        "hammer", -1, -1, time, new Object[]{" H ", "H H", " H ",

                                'H', hunk}));
        time = 3;
        KnowledgeListMFR.scaleRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry,
                new ItemStack(ComponentListMFR.scalemesh), "", true, "hammer", -1, -1, time, new Object[]{"HHH", " H ",

                        'H', hunk}));
        time = 4;
        KnowledgeListMFR.splintRecipes
                .add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.splintmesh), "", true,
                        "hammer", -1, -1, time, new Object[]{"RHR", " H ", " H ", " H ",

                                'H', hunk, 'R', ComponentListMFR.rivet,}));
        time = 4;
        KnowledgeListMFR.plateRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, ComponentListMFR.plate, "", true,
                "hvyhammer", -1, -1, time, new Object[]{"FF", "II", 'F', ComponentListMFR.flux, 'I', bar}));

        Salvage.addSalvage(ComponentListMFR.chainmesh, hunk);
        Salvage.addSalvage(ComponentListMFR.scalemesh, hunk);
        Salvage.addSalvage(ComponentListMFR.splintmesh, hunk, ComponentListMFR.rivet, ComponentListMFR.rivet);
        Salvage.addSalvage(ComponentListMFR.plate, bar, bar);
    }
}