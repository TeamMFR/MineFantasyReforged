package minefantasy.mf2.mechanics.worldGen.structure;

import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class StructureGenAFRoom extends StructureModuleMF {

    private String lootType = ChestGenHooks.DUNGEON_CHEST;
    private boolean hasTrinket = false;

    public StructureGenAFRoom(World world, int x, int y, int z, int d) {
        super(world, x, y, z, d);
    }

    public StructureGenAFRoom(World world, StructureCoordinates position) {
        super(world, position);
    }

    @Override
    public void generate() {
        int width = 7;
        int depth = 6;
        // FLOOR
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), x, 0, z);
            }
        }

        // CEIL
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), x, 5, z);
            }
        }
        // INT
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                for (int y = 1; y <= 4; y++) {
                    placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), x, y, z);
                }
            }
        }
        // HOLLOW
        for (int x = (-width / 2) + 1; x <= (width / 2) - 1; x++) {
            for (int z = 1; z <= depth - 1; z++) {
                for (int y = 1; y <= 4; y++) {
                    placeBlock(Blocks.air, 0, x, y, z);
                }
            }
        }

        // CHEST
        {
            int x = 0;
            int y = 0;
            int z = depth / 2;

            placeChest(0, 1, depth - 1, lootType);
            placeSpawner(0, 1, z, "Silverfish");

            for (int x2 = -1; x2 <= 1; x2++) {
                placeBlock(Blocks.air, 0, x2, 1, 0);
                placeBlock(Blocks.air, 0, x2, 2, 0);
            }
            placeBlock(Blocks.air, 0, 0, 3, 0);

        }
    }

    private void placeChest(int x, int y, int z, String loot) {
        int[] coords = this.offsetPos(x, y, z, direction);
        worldObj.setBlock(coords[0], coords[1], coords[2], Blocks.chest, direction, 2);
        TileEntityChest tileentitychest = (TileEntityChest) worldObj.getTileEntity(coords[0], coords[1], coords[2]);

        if (tileentitychest != null) {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(loot, rand), tileentitychest,
                    ChestGenHooks.getCount(loot, rand));
            if (hasTrinket) {
                // North = -z, South = +z
                int sp = worldObj.getSpawnPoint().posZ;
                int item = (coords[2] < sp) ? 1 : 0;
                int artId = rand.nextInt(tileentitychest.getSizeInventory());
                tileentitychest.setInventorySlotContents(artId, new ItemStack(ComponentListMF.artefacts, 1, item));
            }
        }
    }

    public StructureModuleMF setLoot(String loot) {
        this.lootType = loot;
        return this;
    }

    public StructureModuleMF giveTrinket() {
        hasTrinket = true;
        return this;
    }
}
