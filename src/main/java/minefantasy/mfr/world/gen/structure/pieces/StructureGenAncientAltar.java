package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenAncientAltar extends StructureModuleMFR {
    private final ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;

    public StructureGenAncientAltar(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
    }

    @Override
    public void generate() {
        int radius = 2;

        // Place Floor
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), x, 0 ,z);

                IBlockState state = getFoundation(radius, x, z);
                if (state != null) {
                    buildFoundation(state, x, -1, z, 32, 3, false);
                }
            }
        }

        //Place Stairs
        for (int p = -1; p <= 1; p++) {
            placeStairBlock(world, Blocks.STONE_BRICK_STAIRS, -radius, 0, p, facing, facing.rotateY()); //North
            placeStairBlock(world, Blocks.STONE_BRICK_STAIRS, radius, 0, p, facing, facing.rotateYCCW()); //South
            placeStairBlock(world, Blocks.STONE_BRICK_STAIRS, p, 0, -radius, facing, facing.rotateY().rotateY()); //West
            placeStairBlock(world, Blocks.STONE_BRICK_STAIRS, p, 0, radius, facing, facing); //East
        }


        //Place Spires

        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, -radius, 0, -radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, radius, 0, -radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, -radius, 0, radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, radius, 0, radius);

        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, -radius, 1, -radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, radius, 1, -radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, -radius, 1, radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, radius, 1, radius);

        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, -radius, 2, -radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, radius, 2, -radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, -radius, 2, radius);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_DECORATED, radius, 2, radius);


        // Place Chest and Spawner
        placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), 0, 1, 0);
        placeChest(world, 0, 2, 0, random, facing, lootType);
        placeSpawner(world, 0, 0, 0, EntityList.getKey(EntityEnderman.class));

        MineFantasyReforged.LOG.error("Placed Ancient Altar at: " + pos);
    }

    private IBlockState getFoundation(int radius, int x, int z) {
        if (x == -radius && z == -radius)
            return null;
        if (x == radius && z == -radius)
            return null;
        if (x == -radius && z == radius)
            return null;
        if (x == radius && z == radius)
            return null;

        if (x == 0 && z == -radius)
            return Blocks.OBSIDIAN.getDefaultState();
        if (x == 0 && z == radius)
            return Blocks.OBSIDIAN.getDefaultState();
        if (x == -radius && z == 0)
            return Blocks.OBSIDIAN.getDefaultState();
        if (x == radius && z == 0)
            return Blocks.OBSIDIAN.getDefaultState();

        return getRandomVariantBlock(Blocks.STONEBRICK, random);
    }

    private void placeChest(World world, int x, int y, int z, Random random, EnumFacing facing, ResourceLocation loot) {
        world.setBlockState(offsetPos(x, y, z, facing), Blocks.CHEST.getDefaultState(), 2);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(offsetPos(x, y, z, facing));

        if (chest != null) {
            chest.setLootTable(loot, random.nextLong());

            int artefactSlot = random.nextInt(chest.getSizeInventory());
            chest.setInventorySlotContents(artefactSlot, new ItemStack(MineFantasyItems.ANCIENT_JEWEL_MITHRIL, 1));
        }
    }
}
