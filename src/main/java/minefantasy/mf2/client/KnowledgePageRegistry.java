package minefantasy.mf2.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.knowledge.client.*;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.recipe.ForgingRecipes;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class KnowledgePageRegistry {
    public static void registerPages() {
        ItemStack pigiron = ComponentListMF.bar("PigIron");
        ItemStack black = ComponentListMF.bar("BlackSteel");
        ItemStack red = ComponentListMF.bar("RedSteel");
        ItemStack blue = ComponentListMF.bar("BlueSteel");
        if (ConfigHardcore.HCCallowRocks) {
            KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.hcc"),
                    new EntryPageRecipeBase(KnowledgeListMF.carpenterRecipe),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.dirtRockR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stonePickR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneHammerR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.sharpRocksR));
            KnowledgeListMF.gettingStarted.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.stoneSpearR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneAxeR));
        }
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.1"),
                new EntryPageText("knowledge.gettingStarted.2"));

        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.carpenter"),
                new EntryPageRecipeBase(KnowledgeListMF.carpenterRecipe));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.fire"),
                new EntryPageRecipeBase(KnowledgeListMF.firepitRecipe),
                new EntryPageRecipeBase(KnowledgeListMF.dryrocksR));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.food"),
                new EntryPageRecipeBase(KnowledgeListMF.cooktopRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneovenRecipe));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.tanning"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.tannerRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneKnifeR));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.forging"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneAnvilRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.forgeRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneHammerR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneTongsR),
                new EntryPageRecipeBase(KnowledgeListMF.dryrocksR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.apronRecipe));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.forgingbars"),
                new EntryPageRecipeAnvil(KnowledgeListMF.barR));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.flux"),
                new EntryPageRecipeAnvil(KnowledgeListMF.fluxR));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.3"),
                new EntryPageRecipeAnvil(KnowledgeListMF.shearsR), new EntryPageText("knowledge.gettingStarted.4"),
                new EntryPageText("knowledge.gettingStarted.5"), new EntryPageRecipeAnvil(KnowledgeListMF.hammerR),
                new EntryPageRecipeAnvil(KnowledgeListMF.tongsR), new EntryPageText("knowledge.gettingStarted.6"));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.upgrade"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.researchTableRecipe),
                new EntryPageText("knowledge.gettingStarted.7"));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageText("knowledge.gettingStarted.10"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.potRecipe),
                new EntryPageSmelting(ComponentListMF.clay_pot_uncooked, ComponentListMF.clay_pot),
                new EntryPageRecipeCarpenter(KnowledgeListMF.quernR), new EntryPageText("knowledge.gettingStarted.11"),
                new EntryPageText("knowledge.gettingStarted.12"));
        KnowledgeListMF.gettingStarted.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.bSalvageR),
                new EntryPageText("knowledge.gettingStarted.13"));

        KnowledgeListMF.research.addPages(new EntryPageText("knowledge.research.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.researchTableRecipe),
                new EntryPageText("knowledge.research.2"));

        //IDKH to make it look not ugly without significant code changes
        KnowledgeListMF.talisman.addPages(new EntryPageText("knowledge.talisman.1"));
        if (!ConfigHardcore.HCCRemoveTalismansCraft) {
            KnowledgeListMF.talisman.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.talismanRecipe));
        }
        if (!ConfigHardcore.HCCRemoveBooksCraft) {
            KnowledgeListMF.talisman.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.artBookR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.conBookR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.proBookR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.engBookR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.comBookR));
        }
        KnowledgeListMF.talisman.addPages(
                new EntryPageText("knowledge.talisman.2"));
        if (!ConfigHardcore.HCCRemoveTalismansCraft) {
            KnowledgeListMF.talisman.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.greatTalismanRecipe));
        }
        if (!ConfigHardcore.HCCRemoveBooksCraft) {
            KnowledgeListMF.talisman.addPages(
                    new EntryPageRecipeCarpenter(KnowledgeListMF.artBook2R),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.conBook2R),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.proBook2R),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.engBook2R),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.comBook2R));
        }

        KnowledgeListMF.stamina.addPages(new EntryPageText("knowledge.stamina.1"));
        KnowledgeListMF.combat.addPages(new EntryPageText("knowledge.combat.1"),
                new EntryPageText("knowledge.parry.info"), new EntryPageText("knowledge.advparry.info"),
                new EntryPageText("knowledge.poweratt.info"), new EntryPageText("knowledge.dodge.info"),
                new EntryPageText("knowledge.armour.info"));
        KnowledgeListMF.craftArmourBasic.addPages(new EntryPageText("knowledge.craftArmourBasic.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.hideHelmR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.hideChestR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.hideLegsR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.hideBootsR));
        KnowledgeListMF.craftArmourBasic.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.roughHelmetR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.roughChestR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.roughLegsR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.roughBootsR));

        KnowledgeListMF.carpenter.addPages(new EntryPageText("knowledge.carpenter.1"),
                new EntryPageRecipeBase(KnowledgeListMF.carpenterRecipe));
        KnowledgeListMF.salvage.addPages(new EntryPageText("knowledge.salvage.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bSalvageR), new EntryPageText("knowledge.salvage.2"));

        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.1"));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.plank"),
                new EntryPageRecipeBase(KnowledgeListMF.plankRecipe),
                new EntryPageRecipeBase(KnowledgeListMF.stickRecipe));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.refinedplank"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.jugRecipe),
                new EntryPageSmelting(FoodListMF.jug_uncooked, FoodListMF.jug_empty),
                new EntryPageRecipeBase(KnowledgeListMF.plantOilR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.refinedPlankR));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.flux"),
                new EntryPageRecipeAnvil(KnowledgeListMF.fluxR));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.bar.1"));
        KnowledgeListMF.commodities.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.barR),
                new EntryPageRecipeAnvil(KnowledgeListMF.baringotR));

        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.hunks"),
                new EntryPageRecipeAnvil(KnowledgeListMF.hunkR), new EntryPageRecipeAnvil(KnowledgeListMF.ingotR));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.nail"),
                new EntryPageRecipeAnvil(KnowledgeListMF.nailR));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.rivet"),
                new EntryPageRecipeAnvil(KnowledgeListMF.rivetR));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.leatherstrip"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.lStripsR));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.thread"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.threadR1),
                new EntryPageRecipeCarpenter(KnowledgeListMF.threadR2),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stringR));
        KnowledgeListMF.commodities.addPages(new EntryPageText("knowledge.commodities.bucket"),
                new EntryPageRecipeAnvil(KnowledgeListMF.bucketR));

        KnowledgeListMF.dust.addPages(new EntryPageText("knowledge.dust.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.potRecipe),
                new EntryPageSmelting(ComponentListMF.clay_pot_uncooked, ComponentListMF.clay_pot),
                new EntryPageRecipeCarpenter(KnowledgeListMF.quernR), new EntryPageText("knowledge.dust.quern"));
        KnowledgeListMF.dust.addPages(
                new EntryPageGrind(new ItemStack(Items.coal), new ItemStack(ComponentListMF.coalDust)),
                new EntryPageGrind(new ItemStack(ComponentListMF.kaolinite),
                        new ItemStack(ComponentListMF.kaolinite_dust)),
                new EntryPageGrind(new ItemStack(Items.wheat), new ItemStack(FoodListMF.flour)),
                new EntryPageGrind(new ItemStack(Items.dye, 1, 3), new ItemStack(FoodListMF.coca_powder)));
        KnowledgeListMF.dust.addPages(new EntryPageText("knowledge.dust.icing"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.spoonR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.icingRecipe));

        KnowledgeListMF.ores.addPages(new EntryPageText("knowledge.ores.1"), new EntryPageText(""));
        KnowledgeListMF.ores.addPages(assembleOreDescHC("copper", BlockListMF.oreCopper, ComponentListMF.ingots[0]));
        KnowledgeListMF.ores.addPages(assembleOreDescHC("tin", BlockListMF.oreTin, ComponentListMF.ingots[1]));
        KnowledgeListMF.ores.addPages(assembleOreDescHC("silver", BlockListMF.oreSilver, ComponentListMF.ingots[8]));
        KnowledgeListMF.ores.addPages(assembleOreDesc("wolframite", BlockListMF.oreTungsten));
        KnowledgeListMF.ores.addPages(new EntryPageCrucible(KnowledgeListMF.wolframiteR));
        KnowledgeListMF.ores.addPages(assembleOreDesc("mythic", BlockListMF.oreMythic));
        KnowledgeListMF.ores.addPages(new EntryPageText("knowledge.ores.2"));
        KnowledgeListMF.ores.addPages(assembleMineralDesc("clay", BlockListMF.oreClay));
        KnowledgeListMF.ores.addPages(assembleMineralDesc("kaolinite", BlockListMF.oreKaolinite));
        KnowledgeListMF.ores.addPages(assembleMineralDesc("limestone", BlockListMF.limestone));
        KnowledgeListMF.ores.addPages(assembleMineralDesc("borax", BlockListMF.oreBorax));
        KnowledgeListMF.ores.addPages(assembleMineralDesc("nitre", BlockListMF.oreNitre));
        KnowledgeListMF.ores.addPages(assembleMineralDesc("sulfur", BlockListMF.oreSulfur));

        KnowledgeListMF.plants.addPages(new EntryPageText("knowledge.plants.1"));
        KnowledgeListMF.plants.addPages(assembleImgPage("berry", BlockListMF.berryBush));
        KnowledgeListMF.plants.addPages(new EntryPageText("knowledge.plants.2"));
        KnowledgeListMF.plants.addPages(assembleImgPage("yew", BlockListMF.log_yew));
        KnowledgeListMF.plants.addPages(assembleImgPage("ironbark", BlockListMF.log_ironbark));
        KnowledgeListMF.plants.addPages(assembleImgPage("ebony", BlockListMF.log_ebony));

        KnowledgeListMF.minotaurs.addPages(new EntryPageText("knowledge.minotaurs.1"),
                new EntryPageText("knowledge.minotaurs.2"), new EntryPageText("knowledge.minotaurs.3"),
                new EntryPageText("knowledge.minotaurs.4"));
        KnowledgeListMF.minotaurs.addPages(assembleMobDesc("minotaur"));
        KnowledgeListMF.minotaurs.addPages(assembleMobDesc("minotaur_frost"));
        KnowledgeListMF.minotaurs.addPages(assembleMobDesc("minotaur_dread"));
        KnowledgeListMF.minotaurs.addPages(new EntryPageText("knowledge.minotaurs.combat"));

        KnowledgeListMF.dragons.addPages(new EntryPageText("knowledge.dragons.1"),
                new EntryPageText("knowledge.dragons.2"));
        KnowledgeListMF.dragons.addPages(assembleMobDesc("red_dragon"));
        KnowledgeListMF.dragons.addPages(assembleMobDesc("blue_dragon"));
        KnowledgeListMF.dragons.addPages(assembleMobDesc("green_dragon"));
        KnowledgeListMF.dragons.addPages(assembleMobDesc("ash_dragon"));
        KnowledgeListMF.dragons.addPages(new EntryPageText("knowledge.dragons.combat"));

        KnowledgeListMF.chimney.addPages(new EntryPageText("knowledge.chimney.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.chimneyRecipe), new EntryPageText("knowledge.chimney.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.wideChimneyRecipe),
                new EntryPageText("knowledge.chimney.3"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.extractChimneyRecipe));
        KnowledgeListMF.chimney.addPages(new EntryPageText("knowledge.chimney.pipe"),
                new EntryPageRecipeAnvil(KnowledgeListMF.smokePipeR));
        KnowledgeListMF.chimney.addPages(assembleSimpleImgPage("smoke_pipe_example", "knowledge.chimney.pipe.2"));

        KnowledgeListMF.tanning.addPages(new EntryPageText("knowledge.tanning.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.tannerRecipe), new EntryPageText("knowledge.tanning.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.strongRackR));

        KnowledgeListMF.bloomery.addPages(new EntryPageText("knowledge.bloomery.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bloomeryR), new EntryPageText("knowledge.bloomery.2"),
                new EntryPageText("knowledge.bloomery.3"));
        KnowledgeListMF.crucible.addPages(assembleSimpleImgPage("crucible_example", "knowledge.crucible.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crucibleRecipe),
                new EntryPageText("knowledge.crucible.2"));
        if (ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMF.crucible.addPages(new EntryPageText("knowledge.crucible.hcc"),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.mouldRecipe),
                    new EntryPageSmelting(ComponentListMF.ingot_mould_uncooked, ComponentListMF.ingot_mould));
        }
        KnowledgeListMF.crucible2.addPages(assembleSimpleImgPage("fire_crucible_example", "knowledge.crucible2.1"),
                new EntryPageGrind(new ItemStack(ComponentListMF.kaolinite),
                        new ItemStack(ComponentListMF.kaolinite_dust)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireclayR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.advCrucibleRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBricksR),
                assembleSimpleImgPage("fire_crucible", "knowledge.crucible2.blocks"));

        KnowledgeListMF.bar.addPages(new EntryPageText("knowledge.bar.1"));
        KnowledgeListMF.bar.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.barR),
                new EntryPageRecipeAnvil(KnowledgeListMF.baringotR));
        KnowledgeListMF.smeltCopper.addPages(new EntryPageText("knowledge.smeltCopper.1"));
        KnowledgeListMF.smeltBronze.addPages(new EntryPageText("knowledge.smeltBronze.1"));
        if (ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMF.smeltIron.addPages(new EntryPageText("knowledge.smeltIron.1"));
            KnowledgeListMF.smeltCopper.addPages(new EntryPageRecipeBloom(new ItemStack(BlockListMF.oreCopper),
                    new ItemStack(ComponentListMF.ingots[0])));
            KnowledgeListMF.smeltBronze.addPages(new EntryPageRecipeBloom(new ItemStack(BlockListMF.oreTin),
                    new ItemStack(ComponentListMF.ingots[1])));
        } else {
            KnowledgeListMF.smeltCopper.addPages(new EntryPageSmelting(new ItemStack(BlockListMF.oreCopper),
                    new ItemStack(ComponentListMF.ingots[0])));
            KnowledgeListMF.smeltBronze.addPages(
                    new EntryPageSmelting(new ItemStack(BlockListMF.oreTin), new ItemStack(ComponentListMF.ingots[1])));
        }
        KnowledgeListMF.smeltBronze.addPages(new EntryPageCrucible(KnowledgeListMF.bronze));
        KnowledgeListMF.smeltIron.addPages(new EntryPageText("knowledge.smeltIron.2"));
        KnowledgeListMF.smeltPig.addPages(new EntryPageText("knowledge.smeltPig.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.ironPrepR),
                new EntryPageRecipeAnvil(KnowledgeListMF.ironPrepR2),
                new EntryPageBlastFurnace(ComponentListMF.iron_prep, pigiron),
                new EntryPageText("knowledge.blastfurn.9"));
        KnowledgeListMF.smeltSteel.addPages(new EntryPageText("knowledge.smeltSteel.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.steelR));
        if (!ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMF.smeltSteel.addPages(new EntryPageCrucible(KnowledgeListMF.steel));
        }
        KnowledgeListMF.encrusted.addPages(new EntryPageText("knowledge.smeltEncrusted.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.diamondR),
                new EntryPageRecipeAnvil(KnowledgeListMF.encrustedR));
        KnowledgeListMF.obsidian.addPages(new EntryPageText("knowledge.smeltObsidian.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.obsidianHunkR),
                new EntryPageCrucible(KnowledgeListMF.obsidalloy));

        if (ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMF.smeltIron.addPages(
                    new EntryPageRecipeBloom(new ItemStack(Blocks.iron_ore), new ItemStack(Items.iron_ingot)));
        } else {
            KnowledgeListMF.smeltIron
                    .addPages(new EntryPageSmelting(new ItemStack(Blocks.iron_ore), new ItemStack(Items.iron_ingot)));
        }

        KnowledgeListMF.apron.addPages(new EntryPageText("knowledge.apron.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.apronRecipe));
        KnowledgeListMF.bellows.addPages(new EntryPageText("knowledge.bellows.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bellowsRecipe));
        KnowledgeListMF.trough.addPages(new EntryPageText("knowledge.trough.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.woodTroughRecipe), new EntryPageText("knowledge.trough.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.nailTroughR));
        KnowledgeListMF.forge.addPages(assembleSimpleImgPage("forge_example", "knowledge.forge.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.forgeRecipe), new EntryPageText("knowledge.forge.2"),
                new EntryPageText("knowledge.forge.3"));
        KnowledgeListMF.anvil.addPages(assembleSimpleImgPage("smithy_example", "knowledge.anvil.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneAnvilRecipe),
                new EntryPageRecipeAnvil(ForgingRecipes.recipeMap.get("anvilCrafting")),
                new EntryPageText("knowledge.anvil.2"),
                new EntryPageImage("textures/gui/knowledge/anvilGuiExample.png", 128, 128, ""),
                new EntryPageText("knowledge.anvil.3"), new EntryPageText("knowledge.anvil.4"),
                new EntryPageText("knowledge.anvil.5"));
        KnowledgeListMF.anvil.addPages(new EntryPageText("knowledge.anvil.6"),
                new EntryPageImage("textures/gui/knowledge/qualityExample.png", 128, 128, "knowledge.anvil.7"));

        KnowledgeListMF.smeltDragonforge.addPages(new EntryPageText("knowledge.smeltDragonforge.1"),
                new EntryPageText("knowledge.smeltDragonforge.2"));
        KnowledgeListMF.craftOrnate.addPages(new EntryPageText("knowledge.craftOrnate.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.crestR));

        KnowledgeListMF.craftArmourLight.addPages(new EntryPageText("knowledge.craftArmourLight.1"),
                new EntryPageText("knowledge.craftArmourLight.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.roughHelmetR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.roughChestR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.roughLegsR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.roughBootsR));
        KnowledgeListMF.craftArmourLight.addPages(new EntryPageText("knowledge.craftArmourLight.3"));
        KnowledgeListMF.craftArmourLight.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.padding[0]),
                new EntryPageRecipeCarpenter(KnowledgeListMF.padding[1]),
                new EntryPageRecipeCarpenter(KnowledgeListMF.padding[2]),
                new EntryPageRecipeCarpenter(KnowledgeListMF.padding[3]));
        KnowledgeListMF.craftArmourLight.addPages(new EntryPageText("knowledge.craftArmourLight.4"));
        KnowledgeListMF.craftArmourLight.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.reHelmetR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.reChestR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.reLegsR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.reBootsR));
        KnowledgeListMF.craftArmourLight.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.studHelmetR),
                new EntryPageRecipeAnvil(KnowledgeListMF.studChestR),
                new EntryPageRecipeAnvil(KnowledgeListMF.studLegsR),
                new EntryPageRecipeAnvil(KnowledgeListMF.studBootsR));
        KnowledgeListMF.craftArmourMedium.addPages(new EntryPageText("knowledge.craftArmourMedium.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.mailRecipes),
                new EntryPageRecipeAnvil(KnowledgeListMF.mailHelmetR),
                new EntryPageRecipeAnvil(KnowledgeListMF.mailChestR),
                new EntryPageRecipeAnvil(KnowledgeListMF.mailLegsR),
                new EntryPageRecipeAnvil(KnowledgeListMF.mailBootsR));
        KnowledgeListMF.craftArmourMedium.addPages(new EntryPageText("knowledge.craftArmourMedium.scale"),
                new EntryPageRecipeAnvil(KnowledgeListMF.scaleRecipes),
                new EntryPageRecipeAnvil(KnowledgeListMF.scaleHelmetR),
                new EntryPageRecipeAnvil(KnowledgeListMF.scaleChestR),
                new EntryPageRecipeAnvil(KnowledgeListMF.scaleLegsR),
                new EntryPageRecipeAnvil(KnowledgeListMF.scaleBootsR));
        KnowledgeListMF.craftArmourHeavy.addPages(new EntryPageText("knowledge.craftArmourHeavy.1"),
                new EntryPageText("knowledge.craftArmourHeavy.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.padding));
        KnowledgeListMF.craftArmourHeavy.addPages(new EntryPageText("knowledge.craftArmourHeavy.splint"),
                new EntryPageRecipeAnvil(KnowledgeListMF.splintRecipes),
                new EntryPageRecipeAnvil(KnowledgeListMF.splintHelmetR),
                new EntryPageRecipeAnvil(KnowledgeListMF.splintChestR),
                new EntryPageRecipeAnvil(KnowledgeListMF.splintLegsR),
                new EntryPageRecipeAnvil(KnowledgeListMF.splintBootsR));
        KnowledgeListMF.craftArmourHeavy.addPages(new EntryPageText("knowledge.craftArmourHeavy.plate"),
                new EntryPageRecipeAnvil(KnowledgeListMF.plateRecipes),
                new EntryPageRecipeAnvil(KnowledgeListMF.plateHelmetR),
                new EntryPageRecipeAnvil(KnowledgeListMF.plateChestR),
                new EntryPageRecipeAnvil(KnowledgeListMF.plateLegsR),
                new EntryPageRecipeAnvil(KnowledgeListMF.plateBootsR));

        KnowledgeListMF.coalflux.addPages(new EntryPageText("knowledge.coalflux.1"),
                new EntryPageGrind(new ItemStack(ComponentListMF.flux), new ItemStack(ComponentListMF.flux_pot)),
                new EntryPageRecipeAnvil(KnowledgeListMF.coalfluxR));

        KnowledgeListMF.bigfurn.addPages(assembleSimpleImgPage("furnace_example", "knowledge.bigfurn.1"),
                new EntryPageText("knowledge.bigfurn.2"));
        KnowledgeListMF.bigfurn.addPages(
                new EntryPageGrind(new ItemStack(ComponentListMF.kaolinite),
                        new ItemStack(ComponentListMF.kaolinite_dust)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireclayR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBrickR),
                new EntryPageSmelting(new ItemStack(ComponentListMF.fireclay_brick),
                        new ItemStack(ComponentListMF.strong_brick)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBricksR));
        KnowledgeListMF.bigfurn.addPages(new EntryPageText("knowledge.bigfurn.heater"),
                new EntryPageRecipeAnvil(KnowledgeListMF.bigHeatR),
                assembleSimpleImgPage("furnace_heater", BlockListMF.furnace_heater.getUnlocalizedName() + ".name"));
        KnowledgeListMF.bigfurn.addPages(new EntryPageText("knowledge.bigfurn.top"),
                new EntryPageRecipeAnvil(KnowledgeListMF.bigFurnR),
                assembleSimpleImgPage("furnace_full", "knowledge.bigfurn.structure"));

        KnowledgeListMF.blastfurn.addPages(new EntryPageText("knowledge.blastfurn.1"),
                new EntryPageText("knowledge.blastfurn.2"));
        KnowledgeListMF.blastfurn.addPages(
                new EntryPageGrind(new ItemStack(ComponentListMF.kaolinite),
                        new ItemStack(ComponentListMF.kaolinite_dust)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireclayR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBrickR),
                new EntryPageSmelting(new ItemStack(ComponentListMF.fireclay_brick),
                        new ItemStack(ComponentListMF.strong_brick)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBricksR));
        KnowledgeListMF.blastfurn.addPages(new EntryPageText("knowledge.blastfurn.3"),
                new EntryPageRecipeAnvil(KnowledgeListMF.blastChamR),
                new EntryPageRecipeAnvil(KnowledgeListMF.blastHeatR), new EntryPageText("knowledge.blastfurn.4"),
                new EntryPageText("knowledge.blastfurn.5"));
        KnowledgeListMF.blastfurn.addPages(
                new EntryPageImage("textures/gui/knowledge/blast_example.png", 96, 96, "knowledge.blastfurn.6"));
        KnowledgeListMF.blastfurn.addPages(new EntryPageText("knowledge.blastfurn.7"));
        if (ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMF.blastfurn.addPages(new EntryPageText("knowledge.blastfurn.hcc"));
        }

        KnowledgeListMF.etools.addPages(new EntryPageText("knowledge.etools.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.spannerR), new EntryPageRecipeAnvil(KnowledgeListMF.eatoolsR));
        KnowledgeListMF.ecomponents.addPages(new EntryPageText("knowledge.ecomponents.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.boltR), new EntryPageText("knowledge.ecomponents.2"),
                new EntryPageRecipeAnvil(KnowledgeListMF.iframeR), new EntryPageRecipeAnvil(KnowledgeListMF.bgearR),
                new EntryPageRecipeAnvil(KnowledgeListMF.tgearR), new EntryPageRecipeAnvil(KnowledgeListMF.istrutR),
                new EntryPageRecipeAnvil(KnowledgeListMF.stubeR));
        KnowledgeListMF.tungsten.addPages(new EntryPageText("knowledge.tungsten.1"),
                new EntryPageText("knowledge.tungsten.2"), new EntryPageText("knowledge.tungsten.3"),
                new EntryPageCrucible(KnowledgeListMF.wolframiteR));
        KnowledgeListMF.climber.addPages(new EntryPageText("knowledge.climber.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.climbPickbR), new EntryPageText("knowledge.climber.2"));
        KnowledgeListMF.spyglass.addPages(new EntryPageText("knowledge.spyglass.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.spyglassR));
        KnowledgeListMF.parachute.addPages(new EntryPageText("knowledge.parachute.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.parachuteR));
        KnowledgeListMF.syringe.addPages(new EntryPageText("knowledge.syringe.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.syringeR));
        KnowledgeListMF.engTanner.addPages(new EntryPageText("knowledge.engTanner.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.engTannerR));
        KnowledgeListMF.advcrucible.addPages(
                assembleSimpleImgPage("auto_crucible_example", BlockListMF.crucibleauto.getUnlocalizedName() + ".name"),
                new EntryPageText("knowledge.advcrucible.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.autoCrucibleR),
                new EntryPageText("knowledge.crucible2.blocks"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBricksR),
                assembleSimpleImgPage("auto_crucible", "knowledge.basicstructure"));
        KnowledgeListMF.advforge.addPages(new EntryPageText("knowledge.advforge.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.advancedForgeR));
        KnowledgeListMF.coke.addPages(new EntryPageText("knowledge.coke.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.coalPrepR),
                new EntryPageSmelting(ComponentListMF.coal_prep, ComponentListMF.coke));

        KnowledgeListMF.blackpowder.addPages(new EntryPageText("knowledge.blackpowder.1"),
                new EntryPageGrind(new ItemStack(Items.coal), new ItemStack(ComponentListMF.coalDust)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.blackpowderRec),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crudeBombR));
        KnowledgeListMF.advblackpowder.addPages(new EntryPageText("knowledge.advblackpowder.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.advblackpowderRec));
        KnowledgeListMF.bombs.addPages(new EntryPageText("knowledge.bombs.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bombBenchCraft), new EntryPageText("knowledge.bombs.2"),
                new EntryPageImage("textures/gui/knowledge/bombGuiExample.png", 128, 128, "knowledge.guiSubtitle"),
                new EntryPageText("knowledge.bombs.3"), new EntryPageText("knowledge.bombs.4"));
        KnowledgeListMF.bpress.addPages(new EntryPageText("knowledge.bpress.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bombPressCraft));
        KnowledgeListMF.bombarrow.addPages(new EntryPageText("knowledge.bombarrow.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.bombarrowR),
                new EntryPageRecipeAnvil(KnowledgeListMF.bombBoltR));
        KnowledgeListMF.shrapnel.addPages(new EntryPageText("knowledge.shrapnel.1"),
                new EntryPageGrind(new ItemStack(Items.flint), new ItemStack(ComponentListMF.shrapnel)));
        KnowledgeListMF.firebomb.addPages(new EntryPageText("knowledge.firebomb.1"),
                new EntryPageText("knowledge.firebomb.2"), new EntryPageText("knowledge.firebomb.3"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.magmaRefinedR));
        KnowledgeListMF.bombCeramic.addPages(new EntryPageText("knowledge.bombCeramic.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bombCaseCeramicR),
                new EntryPageSmelting(ComponentListMF.bomb_casing_uncooked, ComponentListMF.bomb_casing));
        KnowledgeListMF.bombIron.addPages(new EntryPageText("knowledge.bombIron.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.bombCaseIronR));
        KnowledgeListMF.bombObsidian.addPages(new EntryPageText("knowledge.bombObsidian.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.bombCaseObsidianR));
        KnowledgeListMF.bombCrystal.addPages(new EntryPageText("knowledge.bombCrystal.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bombCaseCrystalR));

        KnowledgeListMF.mineCeramic.addPages(new EntryPageText("knowledge.mineCeramic.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.mineCaseCeramicR),
                new EntryPageSmelting(ComponentListMF.mine_casing_uncooked, ComponentListMF.mine_casing));
        KnowledgeListMF.mineIron.addPages(new EntryPageText("knowledge.mineIron.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.mineCaseIronR));
        KnowledgeListMF.mineObsidian.addPages(new EntryPageText("knowledge.mineObsidian.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.mineCaseObsidianR));
        KnowledgeListMF.mineCrystal.addPages(new EntryPageText("knowledge.mineCrystal.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.mineCaseCrystalR));
        KnowledgeListMF.bombFuse.addPages(new EntryPageText("knowledge.bombFuse.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bombFuseR), new EntryPageText("knowledge.bombFuse.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.longFuseR));
        KnowledgeListMF.stickybomb.addPages(new EntryPageText("knowledge.stickybomb.1"),
                new EntryPageText("knowledge.stickybomb.2"));

        KnowledgeListMF.crossbows.addPages(new EntryPageText("knowledge.crossbows.1"),
                new EntryPageText("knowledge.crossbows.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossBenchCraft),
                new EntryPageText("knowledge.crossbows.3"), new EntryPageText("knowledge.crossbows.4"),
                new EntryPageText("knowledge.crossbows.5"), new EntryPageText("knowledge.crossbows.6"),
                new EntryPageRecipeAnvil(KnowledgeListMF.crossBoltR));
        KnowledgeListMF.crossShafts.addPages(new EntryPageText("knowledge.crossShafts.handle"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossHandleWoodR),
                new EntryPageText("knowledge.crossShafts.stock"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossStockWoodR));
        KnowledgeListMF.crossHeads.addPages(new EntryPageText("knowledge.crossHeads.light"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossHeadLightR),
                new EntryPageText("knowledge.crossHeads.medium"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossHeadMediumR),
                new EntryPageText("knowledge.crossHeads.heavy"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossHeadHeavyR));
        KnowledgeListMF.crossHeadAdvanced.addPages(new EntryPageText("knowledge.crossHeads.advanced"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossHeadAdvancedR));
        KnowledgeListMF.crossShaftAdvanced.addPages(new EntryPageText("knowledge.crossShafts.advanced"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossStockIronR));
        KnowledgeListMF.crossScope.addPages(new EntryPageText("knowledge.crossScope.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossScopeR));
        KnowledgeListMF.crossAmmo.addPages(new EntryPageText("knowledge.crossAmmo.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.crossAmmoR));
        KnowledgeListMF.crossBayonet.addPages(new EntryPageText("knowledge.crossBayonet.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.crossBayonetR));

        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.1"),
                new EntryPageText("knowledge.cogArmour.2"));
        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.station"),
                new EntryPageRecipeAnvil(KnowledgeListMF.frameBlockR),
                assembleSimpleImgPage("cogwork_station", "knowledge.cogwork_station"));
        // EXAMPLE
        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.station.2"),
                new EntryPageRecipeAnvil(KnowledgeListMF.cogPulleyR),
                assembleSimpleImgPage("cogwork_station_2", "knowledge.rightclickspanner"));

        // RECIPE
        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.crafting"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.cogShaftR),
                new EntryPageRecipeAnvil(KnowledgeListMF.cogHelmR), new EntryPageRecipeAnvil(KnowledgeListMF.cogChestR),
                new EntryPageRecipeAnvil(KnowledgeListMF.cogLegsR),
                assembleSimpleImgPage("cogwork_suit_craft", "knowledge.rightclickspanner"));

        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.armour"));
        KnowledgeListMF.cogArmour.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.hugePlateR),
                new EntryPageRecipeAnvil(KnowledgeListMF.cogPlateR));

        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.advantage"),
                new EntryPageText("knowledge.cogArmour.advantage.2"),
                new EntryPageText("knowledge.cogArmour.advantage.3"));
        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.disadvantage"),
                new EntryPageText("knowledge.cogArmour.disadvantage.2"));
        KnowledgeListMF.cogArmour.addPages(new EntryPageText("knowledge.cogArmour.removal"));

        KnowledgeListMF.compPlate.addPages(new EntryPageText("knowledge.compPlate.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.compPlateR));
        // KnowledgeListMF.craftOrnateWeapons.addPages(new
        // EntryPageText("knowledge.craftOrnateWeapons.1"));

        KnowledgeListMF.repair_basic.addPages(new EntryPageText("knowledge.repair_basic.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.repairBasicR));
        KnowledgeListMF.repair_advanced.addPages(new EntryPageText("knowledge.repair_advanced.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.repairAdvancedR));
        KnowledgeListMF.repair_ornate.addPages(new EntryPageText("knowledge.repair_ornate.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.repairOrnateR));

        KnowledgeListMF.refined_planks.addPages(new EntryPageText("knowledge.refined_planks.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.nailPlanksR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.refinedPlankBlockR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.nailStairR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.refinedStairR));
        KnowledgeListMF.reinforced_stone.addPages(new EntryPageText("knowledge.reinforced_stone.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.obsidianHunkR),
                new EntryPageCrucible(KnowledgeListMF.reStone));
        KnowledgeListMF.brickworks.addPages(new EntryPageText("knowledge.brickworks.1"),
                new EntryPageRecipeBase(KnowledgeListMF.stoneBricksR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBricksR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fireBrickStairR));
        KnowledgeListMF.clay_wall.addPages(new EntryPageText("knowledge.clay_wall.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.clayWallR));
        KnowledgeListMF.glass.addPages(new EntryPageText("knowledge.glass.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.framedGlassR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.windowR));
        KnowledgeListMF.thatch.addPages(new EntryPageText("knowledge.thatch.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.thatchR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.thatchStairR));
        KnowledgeListMF.bars.addPages(new EntryPageText("knowledge.bars.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.barsR));
        KnowledgeListMF.paint_brush.addPages(new EntryPageText("knowledge.paint_brush.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.brushRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.easyPaintPlank));
        KnowledgeListMF.decorated_stone.addPages(new EntryPageText("knowledge.decorated_stone.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.framedStoneR),
                new EntryPageRecipeAnvil(KnowledgeListMF.iframedStoneR));

        KnowledgeListMF.bed_roll.addPages(new EntryPageText("knowledge.bed_roll.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bedrollR));
        KnowledgeListMF.tool_rack.addPages(new EntryPageText("knowledge.tool_rack.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.rackRecipe),
                new EntryPageText("knowledge.tool_rack.rules"));
        KnowledgeListMF.food_box.addPages(new EntryPageText("knowledge.food_box.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.foodboxR), new EntryPageText("knowledge.ammo_box.2"));
        KnowledgeListMF.ammo_box.addPages(new EntryPageText("knowledge.ammo_box.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.ammoboxR), new EntryPageText("knowledge.ammo_box.2"));
        KnowledgeListMF.big_box.addPages(new EntryPageText("knowledge.big_box.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bigboxR), new EntryPageText("knowledge.ammo_box.2"));

        KnowledgeListMF.constructionPts.addPages(new EntryPageText("knowledge.constructionPts.1"));
        KnowledgeListMF.constructionPts.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.sawnPlankR));
        KnowledgeListMF.constructionPts.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.plankPaneR));
        KnowledgeListMF.constructionPts.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.hingeRecipe));
        KnowledgeListMF.constructionPts.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.jugRecipe),
                new EntryPageSmelting(FoodListMF.jug_uncooked, FoodListMF.jug_empty),
                new EntryPageRecipeBase(KnowledgeListMF.plantOilR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.refinedPlankR));

        if (ConfigHardcore.HCCallowRocks) {
            KnowledgeListMF.craftHCCTools.addPages(new EntryPageText("knowledge.craftHCCTools.1"),
                    new EntryPageText("knowledge.craftHCCTools.2"));
            KnowledgeListMF.craftHCCTools.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.sharpRocksR),
                    new EntryPageText("knowledge.craftHCCTools.3"));
            KnowledgeListMF.craftHCCTools.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.stonePickR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneSpadeR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneAxeR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneHoeR));
            KnowledgeListMF.craftHCCTools.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.stoneKnifeR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneHammerR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneTongsR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.boneNeedleR));
            KnowledgeListMF.craftHCCTools.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.stoneSwordR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneMaceR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneWarR),
                    new EntryPageRecipeCarpenter(KnowledgeListMF.stoneSpearR));
        }
        KnowledgeListMF.craftTools.addPages(new EntryPageText("knowledge.craftTools.1"));
        KnowledgeListMF.craftTools.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.pickR));
        KnowledgeListMF.craftTools.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.axeR));
        KnowledgeListMF.craftTools.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.spadeR));
        KnowledgeListMF.craftTools.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.hoeR));
        KnowledgeListMF.craftTools.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.shearsR));

        KnowledgeListMF.craftAdvTools.addPages(new EntryPageText("knowledge.craftAdvTools.1"));
        KnowledgeListMF.craftAdvTools.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.handpickR));
        KnowledgeListMF.craftAdvTools.addPages(new EntryPageText("knowledge.hvypick.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.hvyPickR));
        KnowledgeListMF.craftAdvTools.addPages(new EntryPageText("knowledge.trow.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.trowR));
        KnowledgeListMF.craftAdvTools.addPages(new EntryPageText("knowledge.hvyshovel.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.hvyShovelR));
        KnowledgeListMF.craftAdvTools.addPages(new EntryPageText("knowledge.scythe.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.scytheR));
        KnowledgeListMF.craftAdvTools.addPages(new EntryPageText("knowledge.mattock.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.mattockR), new EntryPageText("knowledge.mattock.use"));
        KnowledgeListMF.craftAdvTools.addPages(new EntryPageText("knowledge.lumber.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.lumberR));

        KnowledgeListMF.firemaker.addPages(new EntryPageText("knowledge.dryrocks.info"),
                new EntryPageRecipeBase(KnowledgeListMF.dryrocksR));
        KnowledgeListMF.firemaker.addPages(new EntryPageText("knowledge.tinderbox.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.tinderboxR));
        KnowledgeListMF.firemaker.addPages(new EntryPageText("knowledge.flintsteel.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.flintAndSteelR));

        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.craftCrafters.1"));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.stoneHammerR),
                new EntryPageRecipeAnvil(KnowledgeListMF.hammerR), new EntryPageText(""));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.hvyhammer.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.hvyHammerR));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.tongs.info"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneTongsR),
                new EntryPageRecipeAnvil(KnowledgeListMF.tongsR), new EntryPageText(""));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.knife.info"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneKnifeR),
                new EntryPageRecipeAnvil(KnowledgeListMF.knifeR), new EntryPageText(""));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.saw.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.sawsR));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.needle.info"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.boneNeedleR),
                new EntryPageRecipeAnvil(KnowledgeListMF.needleR), new EntryPageText(""));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.mallet.info"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.malletR));
        KnowledgeListMF.craftCrafters.addPages(new EntryPageText("knowledge.spoon.info"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.spoonR));

        KnowledgeListMF.craftWeapons.addPages(new EntryPageText("knowledge.craftWeapons.1"));
        KnowledgeListMF.craftWeapons.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.daggerR));
        KnowledgeListMF.craftWeapons.addPages(new EntryPageText("knowledge.sword.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.swordR));
        KnowledgeListMF.craftWeapons.addPages(new EntryPageText("knowledge.waraxe.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.waraxeR));
        KnowledgeListMF.craftWeapons.addPages(new EntryPageText("knowledge.mace.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.maceR));
        KnowledgeListMF.craftWeapons.addPages(new EntryPageText("knowledge.spear.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.spearR));
        KnowledgeListMF.craftWeapons.addPages(new EntryPageText("knowledge.bow.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.bowR));

        KnowledgeListMF.craftAdvWeapons.addPages(new EntryPageText("knowledge.craftAdvWeapons.1"));
        KnowledgeListMF.craftAdvWeapons.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.katanaR));
        KnowledgeListMF.craftAdvWeapons.addPages(new EntryPageText("knowledge.greatsword.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.gswordR));
        KnowledgeListMF.craftAdvWeapons.addPages(new EntryPageText("knowledge.battleaxe.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.battleaxeR));
        KnowledgeListMF.craftAdvWeapons.addPages(new EntryPageText("knowledge.warhammer.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.whammerR));
        KnowledgeListMF.craftAdvWeapons.addPages(new EntryPageText("knowledge.halbeard.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.halbeardR));
        KnowledgeListMF.craftAdvWeapons.addPages(new EntryPageText("knowledge.lance.info"),
                new EntryPageRecipeAnvil(KnowledgeListMF.lanceR));

        KnowledgeListMF.arrows.addPages(new EntryPageText("knowledge.arrows.1"));
        KnowledgeListMF.arrows.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.fletchingR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.fletchingR2));
        KnowledgeListMF.arrows.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.arrowheadR));
        KnowledgeListMF.arrows.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.arrowR));
        KnowledgeListMF.arrowsBodkin.addPages(new EntryPageText("knowledge.arrowsBodkin.1"));
        KnowledgeListMF.arrowsBodkin.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.bodkinheadR));
        KnowledgeListMF.arrowsBroad.addPages(new EntryPageText("knowledge.arrowsBroad.1"));
        KnowledgeListMF.arrowsBroad.addPages(new EntryPageRecipeAnvil(KnowledgeListMF.broadheadR));

        KnowledgeListMF.smeltBlackSteel.addPages(new EntryPageText("knowledge.smeltBlackSteel.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.obsidianHunkR), new EntryPageCrucible(KnowledgeListMF.black),
                new EntryPageBlastFurnace(ComponentListMF.ingots[6], black));
        KnowledgeListMF.smeltRedSteel.addPages(new EntryPageText("knowledge.smeltRedSteel.1"),
                new EntryPageCrucible(KnowledgeListMF.red), new EntryPageBlastFurnace(ComponentListMF.ingots[9], red));
        KnowledgeListMF.smeltBlueSteel.addPages(new EntryPageText("knowledge.smeltBlueSteel.1"),
                new EntryPageCrucible(KnowledgeListMF.blue),
                new EntryPageBlastFurnace(ComponentListMF.ingots[11], blue));
        KnowledgeListMF.smeltAdamant.addPages(new EntryPageText("knowledge.smeltAdamantium.1"),
                new EntryPageText("knowledge.smeltAdamantium.2"), new EntryPageCrucible(KnowledgeListMF.adamantium));
        KnowledgeListMF.smeltMithril.addPages(new EntryPageText("knowledge.smeltMithril.1"),
                new EntryPageText("knowledge.smeltMithril.2"), new EntryPageCrucible(KnowledgeListMF.mithril));

        KnowledgeListMF.smeltMaster.addPages(new EntryPageText("knowledge.smeltMaster.1"),
                new EntryPageText("knowledge.smeltMaster.2"), new EntryPageText("knowledge.smeltMaster.3"));
        KnowledgeListMF.smeltMaster.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.trilogyRecipe));
        KnowledgeListMF.smeltIgnotumite.addPages(new EntryPageText("knowledge.smeltIgnotumite.1"),
                new EntryPageCrucible(KnowledgeListMF.ignotumite));
        KnowledgeListMF.smeltMithium.addPages(new EntryPageText("knowledge.smeltMithium.1"),
                new EntryPageCrucible(KnowledgeListMF.mithium));
        KnowledgeListMF.smeltEnderforge.addPages(new EntryPageText("knowledge.smeltEnderforge.1"),
                new EntryPageCrucible(KnowledgeListMF.enderforge));

        KnowledgeListMF.firepit.addPages(new EntryPageText("knowledge.firepit.1"),
                new EntryPageRecipeBase(KnowledgeListMF.firepitRecipe));
        KnowledgeListMF.firepit.addPages(new EntryPageText("knowledge.firepit.2"),
                new EntryPageRecipeBase(KnowledgeListMF.cooktopRecipe));
        KnowledgeListMF.firepit.addPages(new EntryPageText("knowledge.firepit.3"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stoneovenRecipe));
        KnowledgeListMF.firepit.addPages(new EntryPageText("knowledge.firepit.4"));

        KnowledgeListMF.cookingutensil.addPages(new EntryPageText("knowledge.cookingutensil.1"),
                new EntryPageRecipeAnvil(KnowledgeListMF.caketinRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.pieTrayRecipe),
                new EntryPageSmelting(ComponentListMF.pie_tray_uncooked, FoodListMF.pie_tray));
        KnowledgeListMF.salt.addPages(new EntryPageText("knowledge.salt.1"),
                new EntryPageSmelting(new ItemStack(FoodListMF.bowl_water_salt), new ItemStack(FoodListMF.salt)));
        KnowledgeListMF.jug.addPages(new EntryPageText("knowledge.jug.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.jugRecipe),
                new EntryPageSmelting(FoodListMF.jug_uncooked, FoodListMF.jug_empty),
                new EntryPageText("knowledge.jug.2"), new EntryPageRecipeBase(KnowledgeListMF.waterJugR),
                new EntryPageRecipeBase(KnowledgeListMF.milkJugR));

        KnowledgeListMF.generic_meat.addPages(new EntryPageText("knowledge.generic_meat.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.meatRecipes),
                new EntryPageRecipeCarpenter(KnowledgeListMF.meatStripR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.meatHunkR),
                new EntryPageGrind(new ItemStack(FoodListMF.generic_meat_uncooked),
                        new ItemStack(FoodListMF.generic_meat_mince_uncooked)));
        KnowledgeListMF.stew.addPages(new EntryPageText("knowledge.stew.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.stewRecipe));
        KnowledgeListMF.jerky.addPages(new EntryPageText("knowledge.jerky.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.jerkyRecipe));
        KnowledgeListMF.saussage.addPages(new EntryPageText("knowledge.saussage.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.gutsRecipe),
                new EntryPageGrind(new ItemStack(FoodListMF.breadroll), new ItemStack(FoodListMF.breadcrumbs)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.saussageR), new EntryPageSmelting(
                        new ItemStack(FoodListMF.saussage_raw), new ItemStack(FoodListMF.saussage_cooked)));
        KnowledgeListMF.sandwitch.addPages(new EntryPageText("knowledge.sandwitch.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.breadSliceR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.sandwitchRecipe));
        KnowledgeListMF.sandwitchBig.addPages(new EntryPageText("knowledge.sandwitchBig.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.sandwitchBigRecipe));
        KnowledgeListMF.meatpie.addPages(new EntryPageText("knowledge.meatpie.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.meatPieRecipe),
                new EntryPageSmelting(new ItemStack(FoodListMF.pie_meat_uncooked),
                        new ItemStack(FoodListMF.pie_meat_cooked)),
                new EntryPageRecipeBase(KnowledgeListMF.meatpieOut));
        KnowledgeListMF.shepardpie.addPages(new EntryPageText("knowledge.shepardpie.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.shepardRecipe),
                new EntryPageSmelting(new ItemStack(FoodListMF.pie_shepard_uncooked),
                        new ItemStack(FoodListMF.pie_shepard_cooked)),
                new EntryPageRecipeBase(KnowledgeListMF.shepardOut));

        KnowledgeListMF.bread.addPages(new EntryPageText("knowledge.bread.1"),
                new EntryPageGrind(new ItemStack(Items.wheat), new ItemStack(FoodListMF.flour)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.doughRecipe),
                new EntryPageSmelting(new ItemStack(FoodListMF.dough), new ItemStack(FoodListMF.breadroll)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.breadRecipe),
                new EntryPageSmelting(new ItemStack(FoodListMF.raw_bread), new ItemStack(Items.bread)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.pastryRecipe),
                new EntryPageGrind(new ItemStack(FoodListMF.breadroll), new ItemStack(FoodListMF.breadcrumbs)));
        KnowledgeListMF.bread.addPages(new EntryPageText("knowledge.bread.other"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.pumpPieR),
                new EntryPageSmelting(new ItemStack(FoodListMF.pie_pumpkin_uncooked),
                        new ItemStack(FoodListMF.pie_pumpkin_cooked)),
                new EntryPageRecipeBase(KnowledgeListMF.pumpPieOut));
        KnowledgeListMF.bread.addPages(new EntryPageRecipeCarpenter(KnowledgeListMF.simpCakeR),
                new EntryPageSmelting(new ItemStack(FoodListMF.cake_simple_raw),
                        new ItemStack(FoodListMF.cake_simple_uniced)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.simpCakeOut));

        KnowledgeListMF.oats.addPages(new EntryPageText("knowledge.oats.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.oatsRecipe));

        KnowledgeListMF.berry.addPages(assembleImgPage("berry", BlockListMF.berryBush));
        KnowledgeListMF.icing.addPages(new EntryPageText("knowledge.icing.1"),
                new EntryPageGrind(new ItemStack(Items.reeds), new ItemStack(FoodListMF.sugarpot)),
                new EntryPageRecipeBase(KnowledgeListMF.sugarRecipe), new EntryPageText("knowledge.icing.2"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.icingRecipe));
        KnowledgeListMF.sweetroll.addPages(new EntryPageText("knowledge.sweetroll.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.sweetrollRecipe),
                new EntryPageSmelting(new ItemStack(FoodListMF.sweetroll_raw),
                        new ItemStack(FoodListMF.sweetroll_uniced)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.iceSR));
        KnowledgeListMF.cake.addPages(new EntryPageText("knowledge.cake.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.cakeR),
                new EntryPageSmelting(new ItemStack(FoodListMF.cake_raw), new ItemStack(FoodListMF.cake_uniced)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.cakeI));
        KnowledgeListMF.carrotcake.addPages(new EntryPageText("knowledge.carrotcake.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.carrotCakeR),
                new EntryPageSmelting(new ItemStack(FoodListMF.cake_carrot_raw),
                        new ItemStack(FoodListMF.cake_carrot_uniced)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.carrotCakeI));
        KnowledgeListMF.chococake.addPages(new EntryPageText("knowledge.chococake.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.chocoRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.chocoCakeR),
                new EntryPageSmelting(new ItemStack(FoodListMF.cake_choc_raw),
                        new ItemStack(FoodListMF.cake_choc_uniced)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.chocoCakeI));
        KnowledgeListMF.bfcake.addPages(new EntryPageText("knowledge.bfcake.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.chocoRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bfCakeR),
                new EntryPageSmelting(new ItemStack(FoodListMF.cake_bf_raw), new ItemStack(FoodListMF.cake_bf_uniced)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bfCakeI));
        KnowledgeListMF.berrypie
                .addPages(new EntryPageText("knowledge.berrypie.1"),
                        new EntryPageRecipeCarpenter(KnowledgeListMF.berryR),
                        new EntryPageSmelting(new ItemStack(FoodListMF.pie_berry_uncooked),
                                new ItemStack(FoodListMF.pie_berry_cooked)),
                        new EntryPageRecipeBase(KnowledgeListMF.berryOut));
        KnowledgeListMF.applepie
                .addPages(new EntryPageText("knowledge.applepie.1"),
                        new EntryPageRecipeCarpenter(KnowledgeListMF.appleR),
                        new EntryPageSmelting(new ItemStack(FoodListMF.pie_apple_uncooked),
                                new ItemStack(FoodListMF.pie_apple_cooked)),
                        new EntryPageRecipeBase(KnowledgeListMF.appleOut));
        KnowledgeListMF.eclair.addPages(new EntryPageText("knowledge.eclair.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.eclairDoughR),
                new EntryPageSmelting(new ItemStack(FoodListMF.eclair_raw), new ItemStack(FoodListMF.eclair_uniced)),
                new EntryPageRecipeCarpenter(KnowledgeListMF.chocoRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.eclairIceR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.custardRecipe),
                new EntryPageRecipeCarpenter(KnowledgeListMF.eclairFillR));

        KnowledgeListMF.cheese.addPages(new EntryPageText("knowledge.cheese.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.curdRecipe),
                new EntryPageSmelting(new ItemStack(FoodListMF.curds), new ItemStack(FoodListMF.cheese_pot)),
                new EntryPageRecipeBase(KnowledgeListMF.cheeseOut));
        KnowledgeListMF.cheeseroll.addPages(new EntryPageText("knowledge.cheeseroll.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.cheeserollR));

        KnowledgeListMF.bandage.addPages(new EntryPageText("knowledge.bandage.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.badBandageR),
                new EntryPageRecipeCarpenter(KnowledgeListMF.bandageR));
        KnowledgeListMF.bandageadv.addPages(new EntryPageText("knowledge.bandageadv.1"),
                new EntryPageRecipeCarpenter(KnowledgeListMF.goodBandageR));

        // MASTERY
        KnowledgeListMF.toughness.addPages(new EntryPageText("knowledge.toughness.1"));
        KnowledgeListMF.fitness.addPages(new EntryPageText("knowledge.fitness.1"));
        KnowledgeListMF.armourpro.addPages(new EntryPageText("knowledge.armourpro.1"));
        KnowledgeListMF.parrypro.addPages(new EntryPageText("knowledge.parrypro.1"));
        KnowledgeListMF.counteratt.addPages(new EntryPageText("knowledge.counteratt.1"),
                new EntryPageText("knowledge.counteratt.2"));
        KnowledgeListMF.autoparry.addPages(new EntryPageText("knowledge.autoparry.1"));
        KnowledgeListMF.firstaid.addPages(new EntryPageText("knowledge.firstaid.1"));
        KnowledgeListMF.doctor.addPages(new EntryPageText("knowledge.doctor.1"));
        KnowledgeListMF.scrapper.addPages(new EntryPageText("knowledge.scrapper.1"));
    }

    private static EntryPage[] assembleOreDesc(String orename, Block ore, Item ingot) {
        return new EntryPage[]{new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
                "knowledge.ores." + orename), new EntryPageSmelting(new ItemStack(ore), new ItemStack(ingot))};
    }

    private static EntryPage[] assembleOreDescHC(String orename, Block ore, Item ingot) {
        if (ConfigHardcore.HCCreduceIngots) {
            return new EntryPage[]{
                    new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
                            "knowledge.ores." + orename),
                    new EntryPageRecipeBloom(new ItemStack(ore), new ItemStack(ingot))};
        }
        return assembleOreDesc(orename, ore, ingot);
    }

    private static EntryPage[] assembleOreDesc(String orename, Block ore) {
        return new EntryPage[]{new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
                "knowledge.ores." + orename)};
    }

    private static EntryPage[] assembleMineralDesc(String orename, Block ore) {
        return new EntryPage[]{new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
                "knowledge.minerals." + orename)};
    }

    private static EntryPage[] assembleImgPage(String name, Block blockname) {
        return new EntryPage[]{new EntryPageImage("textures/gui/knowledge/image/" + name + ".png", 96, 96,
                "knowledge." + name + ".1")};
    }

    private static EntryPage[] assembleMobDesc(String name) {
        return new EntryPage[]{new EntryPageImage("textures/gui/knowledge/image/mob/" + name + ".png", 96, 96,
                "knowledge." + name + ".1")};
    }

    private static EntryPage assembleSimpleImgPage(String name, String text) {
        return new EntryPageImage("textures/gui/knowledge/image/" + name + ".png", 96, 96, text);
    }
}
