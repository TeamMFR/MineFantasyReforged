package minefantasy.mf2.recipe;

import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SalvageRecipes {
    public static void init() {
        ItemStack ironHunk = ComponentListMF.metalHunk.createComm("iron");
        Salvage.addSalvage(Items.wooden_pickaxe, new ItemStack(Items.stick, 2), new ItemStack(Blocks.planks, 3));
        Salvage.addSalvage(Items.wooden_axe, new ItemStack(Items.stick, 2), new ItemStack(Blocks.planks, 3));
        Salvage.addSalvage(Items.wooden_shovel, new ItemStack(Items.stick, 2), new ItemStack(Blocks.planks, 1));
        Salvage.addSalvage(Items.wooden_hoe, new ItemStack(Items.stick, 2), new ItemStack(Blocks.planks, 2));
        Salvage.addSalvage(Items.wooden_sword, new ItemStack(Items.stick, 1), new ItemStack(Blocks.planks, 2));

        Salvage.addSalvage(Items.stone_pickaxe, new ItemStack(Items.stick, 2), new ItemStack(Blocks.cobblestone, 3));
        Salvage.addSalvage(Items.stone_axe, new ItemStack(Items.stick, 2), new ItemStack(Blocks.cobblestone, 3));
        Salvage.addSalvage(Items.stone_shovel, new ItemStack(Items.stick, 2), new ItemStack(Blocks.cobblestone, 1));
        Salvage.addSalvage(Items.stone_hoe, new ItemStack(Items.stick, 2), new ItemStack(Blocks.cobblestone, 2));
        Salvage.addSalvage(Items.stone_sword, new ItemStack(Items.stick, 1), new ItemStack(Blocks.cobblestone, 2));

        Salvage.addSalvage(Items.leather_helmet, new ItemStack(Items.leather, 5));
        Salvage.addSalvage(Items.leather_chestplate, new ItemStack(Items.leather, 8));
        Salvage.addSalvage(Items.leather_leggings, new ItemStack(Items.leather, 7));
        Salvage.addSalvage(Items.leather_boots, new ItemStack(Items.leather, 4));

        Salvage.addSalvage(Items.iron_pickaxe, new ItemStack(Items.stick, 2), new ItemStack(Items.iron_ingot, 3));
        Salvage.addSalvage(Items.iron_axe, new ItemStack(Items.stick, 2), new ItemStack(Items.iron_ingot, 3));
        Salvage.addSalvage(Items.iron_shovel, new ItemStack(Items.stick, 2), new ItemStack(Items.iron_ingot, 1));
        Salvage.addSalvage(Items.iron_hoe, new ItemStack(Items.stick, 2), new ItemStack(Items.iron_ingot, 2));
        Salvage.addSalvage(Items.iron_sword, new ItemStack(Items.stick, 1), new ItemStack(Items.iron_ingot, 2));
        Salvage.addSalvage(Items.chainmail_helmet, new ItemStack(Items.iron_ingot, 2));
        Salvage.addSalvage(Items.chainmail_chestplate, new ItemStack(Items.iron_ingot, 4));
        Salvage.addSalvage(Items.chainmail_leggings, new ItemStack(Items.iron_ingot, 4));
        Salvage.addSalvage(Items.chainmail_boots, new ItemStack(Items.iron_ingot, 2));
        Salvage.addSalvage(Items.iron_helmet, new ItemStack(Items.iron_ingot, 5));
        Salvage.addSalvage(Items.iron_chestplate, new ItemStack(Items.iron_ingot, 8));
        Salvage.addSalvage(Items.iron_leggings, new ItemStack(Items.iron_ingot, 7));
        Salvage.addSalvage(Items.iron_boots, new ItemStack(Items.iron_ingot, 4));
        Salvage.addSalvage(Items.iron_horse_armor, Items.leather, new ItemStack(Items.iron_ingot, 8));

        Salvage.addSalvage(Items.golden_pickaxe, new ItemStack(Items.stick, 2), new ItemStack(Items.gold_ingot, 3));
        Salvage.addSalvage(Items.golden_axe, new ItemStack(Items.stick, 2), new ItemStack(Items.gold_ingot, 3));
        Salvage.addSalvage(Items.golden_shovel, new ItemStack(Items.stick, 2), new ItemStack(Items.gold_ingot, 1));
        Salvage.addSalvage(Items.golden_hoe, new ItemStack(Items.stick, 2), new ItemStack(Items.gold_ingot, 2));
        Salvage.addSalvage(Items.golden_sword, new ItemStack(Items.stick, 1), new ItemStack(Items.gold_ingot, 2));
        Salvage.addSalvage(Items.golden_helmet, new ItemStack(Items.gold_ingot, 5));
        Salvage.addSalvage(Items.golden_chestplate, new ItemStack(Items.gold_ingot, 8));
        Salvage.addSalvage(Items.golden_leggings, new ItemStack(Items.gold_ingot, 7));
        Salvage.addSalvage(Items.golden_boots, new ItemStack(Items.gold_ingot, 4));
        Salvage.addSalvage(Items.golden_horse_armor, Items.leather, new ItemStack(Items.gold_ingot, 8));

        Salvage.addSalvage(Items.diamond_pickaxe, new ItemStack(Items.stick, 2), new ItemStack(Items.diamond, 3));
        Salvage.addSalvage(Items.diamond_axe, new ItemStack(Items.stick, 2), new ItemStack(Items.diamond, 3));
        Salvage.addSalvage(Items.diamond_shovel, new ItemStack(Items.stick, 2), new ItemStack(Items.diamond, 1));
        Salvage.addSalvage(Items.diamond_hoe, new ItemStack(Items.stick, 2), new ItemStack(Items.diamond, 2));
        Salvage.addSalvage(Items.diamond_sword, new ItemStack(Items.stick, 1), new ItemStack(Items.diamond, 2));
        Salvage.addSalvage(Items.diamond_helmet, new ItemStack(Items.diamond, 5));
        Salvage.addSalvage(Items.diamond_chestplate, new ItemStack(Items.diamond, 8));
        Salvage.addSalvage(Items.diamond_leggings, new ItemStack(Items.diamond, 7));
        Salvage.addSalvage(Items.diamond_boots, new ItemStack(Items.diamond, 4));
        Salvage.addSalvage(Items.diamond_horse_armor, Items.leather, new ItemStack(Items.diamond, 8));

        Salvage.addSalvage(Items.flint_and_steel, Items.flint, Items.iron_ingot);
        Salvage.addSalvage(Items.compass, Items.redstone, new ItemStack(Items.iron_ingot, 4));
        Salvage.addSalvage(Items.clock, Items.redstone, new ItemStack(Items.gold_ingot, 4));
        Salvage.addSalvage(Items.bow, new ItemStack(Items.stick, 3), new ItemStack(Items.string, 3));
        Salvage.addSalvage(Items.fishing_rod, new ItemStack(Items.stick, 3), new ItemStack(Items.string, 2));
        Salvage.addSalvage(Items.shears, new ItemStack(Items.iron_ingot, 2));
        Salvage.addSalvage(Items.minecart, new ItemStack(Items.iron_ingot, 5));
        Salvage.addSalvage(Items.boat, new ItemStack(Blocks.planks, 5));

        Salvage.addSalvage(Blocks.furnace, new ItemStack(Blocks.cobblestone, 8));
        Salvage.addSalvage(Blocks.dispenser, Items.bow, Items.redstone, new ItemStack(Blocks.cobblestone, 7));
        Salvage.addSalvage(Blocks.dropper, Items.redstone, new ItemStack(Blocks.cobblestone, 7));
        Salvage.addSalvage(Blocks.chest, new ItemStack(Blocks.planks, 8));
        Salvage.addSalvage(Blocks.piston, Items.redstone, Items.iron_ingot, new ItemStack(Blocks.cobblestone, 4),
                new ItemStack(Blocks.planks, 3));
        Salvage.addSalvage(Blocks.sticky_piston, Items.slime_ball, Blocks.piston);
        Salvage.addSalvage(Items.wooden_door, new ItemStack(Blocks.planks, 6));
        Salvage.addSalvage(Items.iron_door, new ItemStack(Items.iron_ingot, 6));
        Salvage.addSalvage(Blocks.trapdoor, new ItemStack(Blocks.planks, 3));
        Salvage.addSalvage(Blocks.hopper, Blocks.chest, new ItemStack(Items.iron_ingot, 5));
        Salvage.addSalvage(Items.cauldron, new ItemStack(Items.iron_ingot, 7));
        Salvage.addSalvage(Items.bed, new ItemStack(Blocks.planks, 3), new ItemStack(Blocks.wool, 3));
        Salvage.addSalvage(Items.book, new ItemStack(Items.paper, 3), Items.leather);
        Salvage.addSalvage(Items.brewing_stand, new ItemStack(Blocks.cobblestone, 3), Items.blaze_rod);
        Salvage.addSalvage(Items.carrot_on_a_stick, Items.carrot, Items.fishing_rod);
        Salvage.addSalvage(Items.comparator, new ItemStack(Blocks.redstone_torch, 3), new ItemStack(Blocks.stone, 3),
                Items.quartz);
        Salvage.addSalvage(Items.ender_eye, Items.ender_pearl, Items.blaze_powder);
        Salvage.addSalvage(Items.magma_cream, Items.blaze_powder, Items.slime_ball);
        Salvage.addSalvage(Items.painting, new ItemStack(Items.stick, 8), Blocks.wool);
        Salvage.addSalvage(Items.repeater, new ItemStack(Blocks.stone, 3), new ItemStack(Blocks.redstone_torch, 2),
                Items.redstone);
        Salvage.addSalvage(Items.saddle, Items.leather, 5);
        Salvage.addSalvage(Items.sign, new ItemStack(Blocks.planks, 2));
        Salvage.addSalvage(Items.speckled_melon, new ItemStack(Items.gold_nugget, 8), Items.melon);
        Salvage.addSalvage(Blocks.activator_rail, Items.iron_ingot, Items.redstone);
        Salvage.addSalvage(Blocks.anvil, new ItemStack(Items.iron_ingot, 31));
        Salvage.addSalvage(Blocks.beacon, Items.nether_star, new ItemStack(Blocks.glass, 5),
                new ItemStack(Blocks.obsidian, 3));
        Salvage.addSalvage(Blocks.bookshelf, new ItemStack(Blocks.planks, 6), new ItemStack(Items.book, 3));
        Salvage.addSalvage(Blocks.crafting_table, new ItemStack(Blocks.planks, 4));
        Salvage.addSalvage(Blocks.daylight_detector, new ItemStack(Blocks.glass, 3),
                new ItemStack(Blocks.wooden_slab, 3), new ItemStack(Items.quartz, 3));
        Salvage.addSalvage(Blocks.deadbush, new ItemStack(Items.stick, 4));
        Salvage.addSalvage(Blocks.detector_rail, Blocks.rail, Blocks.stone_pressure_plate);
        Salvage.addSalvage(Blocks.enchanting_table, new ItemStack(Blocks.obsidian, 4), new ItemStack(Items.diamond, 2),
                Items.book);
        Salvage.addSalvage(Blocks.ender_chest, new ItemStack(Blocks.obsidian, 8), Items.ender_eye);
        Salvage.addSalvage(Blocks.fence, new ItemStack(Items.stick, 3));
        Salvage.addSalvage(Blocks.fence_gate, new ItemStack(Blocks.planks, 2), new ItemStack(Items.stick, 4));
        Salvage.addSalvage(Blocks.golden_rail, Items.gold_ingot);
        Salvage.addSalvage(Blocks.heavy_weighted_pressure_plate, new ItemStack(Items.iron_ingot, 2));
        Salvage.addSalvage(Blocks.light_weighted_pressure_plate, new ItemStack(Items.gold_ingot, 2));
        Salvage.addSalvage(Blocks.jukebox, new ItemStack(Blocks.planks, 8), Items.diamond);
        Salvage.addSalvage(Blocks.ladder, new ItemStack(Items.stick, 2));
        Salvage.addSalvage(Blocks.lever, Items.stick, Blocks.cobblestone);
        Salvage.addSalvage(Blocks.lit_pumpkin, Blocks.pumpkin, Blocks.torch);
        Salvage.addSalvage(Blocks.noteblock, new ItemStack(Blocks.planks, 8), Items.redstone);
        Salvage.addSalvage(Blocks.rail, ironHunk);
        Salvage.addSalvage(Blocks.redstone_lamp, new ItemStack(Items.redstone, 4), Blocks.glowstone);
        Salvage.addSalvage(Blocks.redstone_torch, Items.stick, Items.redstone);
        Salvage.addSalvage(Blocks.sandstone, new ItemStack(Blocks.sand, 4));
        Salvage.addSalvage(Blocks.stonebrick, Blocks.stone);
        Salvage.addSalvage(Blocks.stone_button, Blocks.stone);
        Salvage.addSalvage(Blocks.stone_pressure_plate, new ItemStack(Blocks.stone, 2));
        Salvage.addSalvage(Blocks.tnt, new ItemStack(Blocks.sand, 4), Items.gunpowder, 5);
        Salvage.addSalvage(Blocks.trapped_chest, Blocks.chest, Blocks.tripwire_hook);
        Salvage.addSalvage(Blocks.tripwire_hook, ironHunk, ironHunk);
        Salvage.addSalvage(Blocks.wooden_button, Blocks.planks);
        Salvage.addSalvage(Blocks.wooden_pressure_plate, new ItemStack(Blocks.planks, 2));
        Salvage.addSalvage(Blocks.cobblestone_wall, Blocks.cobblestone);
        Salvage.addSalvage(Blocks.iron_bars, ironHunk, ironHunk);
        Salvage.addSalvage(Items.item_frame, new ItemStack(Items.stick, 8), Items.leather);
    }
}
