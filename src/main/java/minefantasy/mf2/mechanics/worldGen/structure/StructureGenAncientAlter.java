package minefantasy.mf2.mechanics.worldGen.structure;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class StructureGenAncientAlter extends StructureModuleMF {
    private String lootType = ChestGenHooks.DUNGEON_CHEST;

    public StructureGenAncientAlter(World world, int x, int y, int z, int d) {
        super(world, x, y, z, d);
    }

    StructureGenAncientAlter(World world, StructureCoordinates position) {
        super(world, position);
    }

    @Override
    public void generate() {
        int radius = 2;
        // FLOOR
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), x, 0, z);

                Object[] array = getFoundation(radius, x, z);
                if (array != null) {
                    int m = (Boolean) array[1] ? -1 : 0;
                    buildFoundation((Block) array[0], m, x, -1, z, 32, 3, false);
                }
            }
        }
        for (int p = -1; p <= 1; p++) {
            placeBlock(Blocks.stone_brick_stairs, 0, -radius, 0, p, 0);
            placeBlock(Blocks.stone_brick_stairs, 1, radius, 0, p, 0);
            placeBlock(Blocks.stone_brick_stairs, 2, p, 0, -radius, 0);
            placeBlock(Blocks.stone_brick_stairs, 3, p, 0, radius, 0);
        }
        placeBlock(BlockListMF.mythic_decor, 1, -radius, 0, -radius);
        placeBlock(BlockListMF.mythic_decor, 1, radius, 0, -radius);
        placeBlock(BlockListMF.mythic_decor, 1, -radius, 0, radius);
        placeBlock(BlockListMF.mythic_decor, 1, radius, 0, radius);

        placeBlock(BlockListMF.mythic_decor, 0, -radius, 1, -radius);
        placeBlock(BlockListMF.mythic_decor, 0, radius, 1, -radius);
        placeBlock(BlockListMF.mythic_decor, 0, -radius, 1, radius);
        placeBlock(BlockListMF.mythic_decor, 0, radius, 1, radius);

        placeBlock(BlockListMF.mythic_decor, 1, -radius, 2, -radius);
        placeBlock(BlockListMF.mythic_decor, 1, radius, 2, -radius);
        placeBlock(BlockListMF.mythic_decor, 1, -radius, 2, radius);
        placeBlock(BlockListMF.mythic_decor, 1, radius, 2, radius);

        // CHEST
        {
            int x = 0;
            int y = 0;
            int z = 0;

            placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), x, y + 1, z);
            placeChest(0, y + 2, z, lootType);
            placeSpawner(0, y, z, "Enderman");
        }

    }

    private Object[] getFoundation(int radius, int x, int z) {
        if (x == -radius && z == -radius)
            return null;
        if (x == radius && z == -radius)
            return null;
        if (x == -radius && z == radius)
            return null;
        if (x == radius && z == radius)
            return null;

        if (x == 0 && z == -radius)
            return new Object[]{Blocks.obsidian, false};
        if (x == 0 && z == radius)
            return new Object[]{Blocks.obsidian, false};
        if (x == -radius && z == 0)
            return new Object[]{Blocks.obsidian, false};
        if (x == radius && z == 0)
            return new Object[]{Blocks.obsidian, false};

        return new Object[]{Blocks.stonebrick, true};
    }

    private void placeChest(int x, int y, int z, String loot) {
        int[] coords = this.offsetPos(x, y, z, direction);
        worldObj.setBlock(coords[0], coords[1], coords[2], Blocks.chest, direction, 2);
        TileEntityChest tileentitychest = (TileEntityChest) worldObj.getTileEntity(coords[0], coords[1], coords[2]);

        if (tileentitychest != null) {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(loot, rand), tileentitychest,
                    ChestGenHooks.getCount(loot, rand));

            int artId = rand.nextInt(tileentitychest.getSizeInventory());
            tileentitychest.setInventorySlotContents(artId, new ItemStack(ComponentListMF.artefacts, 1, 2));
        }
    }

    public StructureModuleMF setLoot(String loot) {
        this.lootType = loot;
        return this;
    }
}
