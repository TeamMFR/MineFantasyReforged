package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ForgedArmourRecipes {

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

		Item mail = MineFantasyItems.CHAIN_MESH;
		Item rivet = MineFantasyItems.RIVET;

		int time = 20;
		MineFantasyKnowledgeList.mailHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_CHAIN_HELMET), "craftArmourMedium", true, "hammer", -1, -1, time, "RMR", "MPM", "RMR", 'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)));
		time = 30;
		MineFantasyKnowledgeList.mailChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_CHAIN_CHEST), "craftArmourMedium", true, "hammer", -1, -1, time, "RM MR", "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)));
		time = 20;
		MineFantasyKnowledgeList.mailLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_CHAIN_LEGS), "craftArmourMedium", true, "hammer", -1, -1, time, "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)));
		time = 10;
		MineFantasyKnowledgeList.mailBootsR.add(
				MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_CHAIN_BOOTS), "craftArmourMedium", true, "hammer", -1, -1, time, "R R", "MPM", 'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)));
		Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_CHEST, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);// 6 Mail 6 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_LEGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);// 4 Mail, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_BOOTS, boots, mail, mail, rivet, rivet);// 2 Mail, 2 Rivet
	}

	private static void assembleScalemail() {
		Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0);
		Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1);
		Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2);
		Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3);

		ItemStack mail = new ItemStack(MineFantasyItems.SCALE_MESH);
		Item rivet = MineFantasyItems.RIVET;

		int time = 20;
		MineFantasyKnowledgeList.scaleHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SCALE_HELMET), "craftArmourMedium", true, "hammer", -1, -1, time, "RMR", "MPM", "RMR", 'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)));
		time = 30;
		MineFantasyKnowledgeList.scaleChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SCALE_CHEST), "craftArmourMedium", true, "hammer", -1, -1, time, "RM MR", "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)));
		time = 20;
		MineFantasyKnowledgeList.scaleLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SCALE_LEGS), "craftArmourMedium", true, "hammer", -1, -1, time, "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)));
		time = 10;
		MineFantasyKnowledgeList.scaleBootsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SCALE_BOOTS), "craftArmourMedium", true, "hammer", -1, -1, time, "R R", "MPM", 'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)));
		Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_CHEST, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);//6 Mail 6 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_LEGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_BOOTS, boots, mail, mail, rivet, rivet);//2 Mail, 2 Rivet
	}

	private static void assembleSplintmail() {
		Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0);
		Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1);
		Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2);
		Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3);

		ItemStack mail = new ItemStack(MineFantasyItems.SPLINT_MESH);
		Item rivet = MineFantasyItems.RIVET;

		int time = 20;
		MineFantasyKnowledgeList.splintHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SPLINT_HELMET), "craftArmourMedium", true, "hammer", -1, -1, time, "RMR", "MPM", "RMR", 'R', rivet, 'M', mail, 'P', new ItemStack(helm, 1, 0)));
		time = 30;
		MineFantasyKnowledgeList.splintChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SPLINT_CHEST), "craftArmourMedium", true, "hammer", -1, -1, time, "RM MR", "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(chest, 1, 0)));
		time = 20;
		MineFantasyKnowledgeList.splintLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SPLINT_LEGS), "craftArmourMedium", true, "hammer", -1, -1, time, "RMPMR", "RM MR", 'R', rivet, 'M', mail, 'P', new ItemStack(legs, 1, 0)));
		time = 10;
		MineFantasyKnowledgeList.splintBootsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_SPLINT_BOOTS), "craftArmourMedium", true, "hammer", -1, -1, time, "R R", "MPM", 'R', rivet, 'M', mail, 'P', new ItemStack(boots, 1, 0)));
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_CHEST, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);//6 Mail, 6 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_LEGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_BOOTS, boots, mail, mail, rivet, rivet);//2 Mail 2 Rivet
	}

	private static void assembleFieldplate() {
		Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0);
		Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1);
		Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2);
		Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3);

		ItemStack plate = new ItemStack(MineFantasyItems.PLATE);
		Item rivet = MineFantasyItems.RIVET;

		int time = 40;
		MineFantasyKnowledgeList.plateHelmetR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_PLATE_HELMET), "craftArmourHeavy", false, "hammer", -1, -1, time, " R ", "PHP", " R ", 'R', rivet, 'P', plate, 'H', new ItemStack(helm, 1, 0)));
		time = 60;
		MineFantasyKnowledgeList.plateChestR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_PLATE_CHEST), "craftArmourHeavy", false, "hammer", -1, -1, time, "RP PR", "RPCPR", 'R', rivet, 'P', plate, 'C', new ItemStack(chest, 1, 0)));
		time = 40;
		MineFantasyKnowledgeList.plateLegsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_PLATE_LEGS), "craftArmourHeavy", false, "hammer", -1, -1, time, "RPLPR", "RP PR", 'R', rivet, 'P', plate, 'L', new ItemStack(legs, 1, 0)));
		time = 20;
		MineFantasyKnowledgeList.plateBootsR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.STANDARD_PLATE_BOOTS), "craftArmourHeavy", false, "hammer", -1, -1, time, "R R", "PBP", 'R', rivet, 'P', plate, 'B', new ItemStack(boots, 1, 0)));

		Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_HELMET, helm, plate, plate, rivet, rivet);//2 Plate, Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_CHEST, chest, plate, plate, plate, plate, rivet, rivet, rivet, rivet);//4 Plate, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_LEGS, legs, plate, plate, plate, plate, rivet, rivet, rivet, rivet);//4 Plate, 4 Rivet
		Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_BOOTS, boots, plate, plate, rivet, rivet);//2 Plate, 2 Rivet

	}

	private static void assembleCogPlating() {

		ItemStack minorPiece = new ItemStack(MineFantasyItems.PLATE);
		ItemStack majorPiece = new ItemStack(MineFantasyItems.PLATE_HUGE);

		int time = 4;
		MineFantasyKnowledgeList.hugePlateR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ENGINEERING, majorPiece, "cogArmour", true, "hvyhammer", -1, -1, time, " RR ", "RIIR", 'R', MineFantasyItems.RIVET, 'I', minorPiece));

		Salvage.addSalvage(majorPiece, minorPiece, minorPiece, new ItemStack(MineFantasyItems.RIVET, 4));

		time = 25;
		MineFantasyKnowledgeList.cogPlateR.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.COGWORK_ARMOUR), "cogArmour", true, "hvyhammer", -1, -1, time, "  P  ", "pPPPp", "p P p", " pPp ", 'p', minorPiece, 'P', majorPiece));

		Salvage.addSalvage(MineFantasyItems.COGWORK_ARMOUR, minorPiece, minorPiece, minorPiece, minorPiece, minorPiece, minorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece);
	}

	private static void addMetalComponents() {
		Item hunk = MineFantasyItems.METAL_HUNK;
		Item bar = MineFantasyItems.BAR;

		int time = 3;
		MineFantasyKnowledgeList.mailRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.CHAIN_MESH), "", true, "hammer", -1, -1, time, " H ", "H H", " H ", 'H', hunk));
		time = 3;
		MineFantasyKnowledgeList.scaleRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.SCALE_MESH), "", true, "hammer", -1, -1, time, "HHH", " H ", 'H', hunk));
		time = 4;
		MineFantasyKnowledgeList.splintRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.SPLINT_MESH), "", true, "hammer", -1, -1, time, "RHR", " H ", " H ", " H ", 'H', hunk, 'R', MineFantasyItems.RIVET));
		time = 4;
		MineFantasyKnowledgeList.plateRecipes.add(MineFantasyRebornAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.PLATE, "", true, "hvyhammer", -1, -1, time, "FF", "II", 'F', MineFantasyItems.FLUX, 'I', bar));

		Salvage.addSalvage(MineFantasyItems.CHAIN_MESH, hunk);
		Salvage.addSalvage(MineFantasyItems.SCALE_MESH, hunk);
		Salvage.addSalvage(MineFantasyItems.SPLINT_MESH, hunk, MineFantasyItems.RIVET, MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.PLATE, bar, bar);
	}
}