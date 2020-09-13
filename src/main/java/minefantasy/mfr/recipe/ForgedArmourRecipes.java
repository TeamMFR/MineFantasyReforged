package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomArmourListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
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
        Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0);
        Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1);
        Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2);
        Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3);

        Item mail = ComponentListMFR.CHAIN_MESH;
        Item rivet = ComponentListMFR.RIVET;

        int time = 20;
        KnowledgeListMFR.mailHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_CHAIN_HELMET), "craftArmourMedium", true, "hammer", -1, -1, time, "RMR", "MPM", "RMR", 'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)));
        time = 30;
        KnowledgeListMFR.mailChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_CHAIN_CHEST), "craftArmourMedium", true, "hammer", -1, -1, time, "RM MR", "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)));
        time = 20;
        KnowledgeListMFR.mailLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_CHAIN_LEGS), "craftArmourMedium", true, "hammer", -1, -1, time, "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)));
        time = 10;
        KnowledgeListMFR.mailBootsR.add(
                MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_CHAIN_BOOTS), "craftArmourMedium", true, "hammer", -1, -1, time, "R R", "MPM", 'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)));
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_CHAIN_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_CHAIN_CHEST, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);// 6 Mail 6 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_CHAIN_LEGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);// 4 Mail, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_CHAIN_BOOTS, boots, mail, mail, rivet, rivet);// 2 Mail, 2 Rivet
    }

    private static void assembleScalemail() {
        Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0);
        Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1);
        Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2);
        Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3);

        ItemStack mail = new ItemStack(ComponentListMFR.SCALE_MESH);
        Item rivet = ComponentListMFR.RIVET;

        int time = 20;
        KnowledgeListMFR.scaleHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SCALE_HELMET), "craftArmourMedium", true, "hammer", -1, -1, time, "RMR", "MPM", "RMR", 'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)));
        time = 30;
        KnowledgeListMFR.scaleChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SCALE_CHEST), "craftArmourMedium", true, "hammer", -1, -1, time, "RM MR", "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)));
        time = 20;
        KnowledgeListMFR.scaleLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SCALE_LEGS), "craftArmourMedium", true, "hammer", -1, -1, time, "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)));
        time = 10;
        KnowledgeListMFR.scaleBootsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SCALE_BOOTS), "craftArmourMedium", true, "hammer", -1, -1, time, "R R", "MPM", 'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)));
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SCALE_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SCALE_CHEST, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);//6 Mail 6 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SCALE_LEGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SCALE_BOOTS, boots, mail, mail, rivet, rivet);//2 Mail, 2 Rivet
    }

    private static void assembleSplintmail() {
        Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0);
        Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1);
        Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2);
        Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3);

        ItemStack mail = new ItemStack(ComponentListMFR.SPLINT_MESH);
        Item rivet = ComponentListMFR.RIVET;

        int time = 20;
        KnowledgeListMFR.splintHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SPLINT_HELMET), "craftArmourMedium", true, "hammer", -1, -1, time, "RMR", "MPM", "RMR", 'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)));
        time = 30;
        KnowledgeListMFR.splintChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SPLINT_CHEST), "craftArmourMedium", true, "hammer", -1, -1, time, "RM MR", "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)));
        time = 20;
        KnowledgeListMFR.splintLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SPLINT_LEGS), "craftArmourMedium", true, "hammer", -1, -1, time, "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)));
        time = 10;
        KnowledgeListMFR.splintBootsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_SPLINT_BOOTS), "craftArmourMedium", true, "hammer", -1, -1, time, "R R", "MPM", 'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)));
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SPLINT_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SPLINT_CHEST, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);//6 Mail, 6 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SPLINT_LEGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_SPLINT_BOOTS, boots, mail, mail, rivet, rivet);//2 Mail 2 Rivet
    }

    private static void assembleFieldplate() {
        Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0);
        Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1);
        Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2);
        Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3);

        ItemStack plate = new ItemStack(ComponentListMFR.PLATE);
        Item rivet = ComponentListMFR.RIVET;

        int time = 40;
        KnowledgeListMFR.plateHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_PLATE_HELMET), "craftArmourHeavy", false, "hammer", -1, -1, time, " R ", "PHP", " R ", 'R', rivet, 'P', plate, 'H', new ItemStack(helm, 1, 0)));
        time = 60;
        KnowledgeListMFR.plateChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_PLATE_CHEST), "craftArmourHeavy", false, "hammer", -1, -1, time, "RP PR", "RPCPR", 'R', rivet, 'P', plate, 'C', new ItemStack(chest, 1, 0)));
        time = 40;
        KnowledgeListMFR.plateLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_PLATE_LEGS), "craftArmourHeavy", false, "hammer", -1, -1, time, "RPLPR", "RP PR", 'R', rivet, 'P', plate, 'L', new ItemStack(legs, 1, 0)));
        time = 20;
        KnowledgeListMFR.plateBootsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(CustomArmourListMFR.STANDARD_PLATE_BOOTS), "craftArmourHeavy", false, "hammer", -1, -1, time, "R R", "PBP", 'R', rivet, 'P', plate, 'B', new ItemStack(boots, 1, 0)));

        Salvage.addSalvage(CustomArmourListMFR.STANDARD_PLATE_HELMET, helm, plate, plate, rivet, rivet);//2 Plate, Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_PLATE_CHEST, chest, plate, plate, plate, plate, rivet, rivet, rivet, rivet);//4 Plate, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_PLATE_LEGS, legs, plate, plate, plate, plate, rivet, rivet, rivet, rivet);//4 Plate, 4 Rivet
        Salvage.addSalvage(CustomArmourListMFR.STANDARD_PLATE_BOOTS, boots, plate, plate, rivet, rivet);//2 Plate, 2 Rivet

    }

    private static void assembleCogPlating() {

        ItemStack minorPiece = new ItemStack(ComponentListMFR.PLATE);
        ItemStack majorPiece = new ItemStack(ComponentListMFR.PLATE_HUGE);

        int time = 4;
        KnowledgeListMFR.hugePlateR.add(MineFantasyRebornAPI.addAnvilToolRecipe(engineering, majorPiece, "cogArmour", true, "hvyhammer", -1, -1, time, " RR ", "RIIR", 'R', ComponentListMFR.RIVET, 'I', minorPiece));

        Salvage.addSalvage(majorPiece, minorPiece, minorPiece, new ItemStack(ComponentListMFR.RIVET, 4));

        time = 25;
        KnowledgeListMFR.cogPlateR.add(MineFantasyRebornAPI.addAnvilToolRecipe(engineering, new ItemStack(ComponentListMFR.COGWORK_ARMOUR), "cogArmour", true, "hvyhammer", -1, -1, time, "  P  ", "pPPPp", "p P p", " pPp ", 'p', minorPiece, 'P', majorPiece));

        Salvage.addSalvage(ComponentListMFR.COGWORK_ARMOUR, minorPiece, minorPiece, minorPiece, minorPiece, minorPiece, minorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece);
    }

    private static void addMetalComponents() {
        Item hunk = ComponentListMFR.METAL_HUNK;
        Item bar = ComponentListMFR.BAR;

        int time = 3;
        KnowledgeListMFR.mailRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.CHAIN_MESH), "", true, "hammer", -1, -1, time, " H ", "H H", " H ", 'H', hunk));
        time = 3;
        KnowledgeListMFR.scaleRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.SCALE_MESH), "", true, "hammer", -1, -1, time, "HHH", " H ", 'H', hunk));
        time = 4;
        KnowledgeListMFR.splintRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.SPLINT_MESH), "", true, "hammer", -1, -1, time, "RHR", " H ", " H ", " H ", 'H', hunk, 'R', ComponentListMFR.RIVET));
        time = 4;
        KnowledgeListMFR.plateRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, ComponentListMFR.PLATE, "", true, "hvyhammer", -1, -1, time, "FF", "II", 'F', ComponentListMFR.FLUX, 'I', bar));

        Salvage.addSalvage(ComponentListMFR.CHAIN_MESH, hunk);
        Salvage.addSalvage(ComponentListMFR.SCALE_MESH, hunk);
        Salvage.addSalvage(ComponentListMFR.SPLINT_MESH, hunk, ComponentListMFR.RIVET, ComponentListMFR.RIVET);
        Salvage.addSalvage(ComponentListMFR.PLATE, bar, bar);
    }
}