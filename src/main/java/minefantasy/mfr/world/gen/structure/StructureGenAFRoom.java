package minefantasy.mfr.world.gen.structure;

import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureGenAFRoom extends StructureModuleMFR {

	private ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;
	private boolean hasTrinket = false;

	public StructureGenAFRoom(World world, BlockPos pos, int d) {
		super(world, pos, d);
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
				placeBlock(Blocks.STONEBRICK, new BlockPos(x, 0, z));
			}
		}

		// CEIL
		for (int x = -width / 2; x <= width / 2; x++) {
			for (int z = 0; z <= depth; z++) {
				placeBlock(Blocks.STONEBRICK, new BlockPos(x, 5, z));
			}
		}
		// INT
		for (int x = -width / 2; x <= width / 2; x++) {
			for (int z = 0; z <= depth; z++) {
				for (int y = 1; y <= 4; y++) {
					placeBlock(Blocks.STONEBRICK, new BlockPos(x, y, z));
				}
			}
		}
		// HOLLOW
		for (int x = (-width / 2) + 1; x <= (width / 2) - 1; x++) {
			for (int z = 1; z <= depth - 1; z++) {
				for (int y = 1; y <= 4; y++) {
					placeBlock(Blocks.AIR, new BlockPos(x, y, z));
				}
			}
		}

		// CHEST
		{
			int x = 0;
			int y = 0;
			int z = depth / 2;

			placeChest(new BlockPos(0, 1, depth - 1), lootType);
			placeSpawner(new BlockPos(0, 1, z), EntityList.getKey(EntitySilverfish.class));

			for (int x2 = -1; x2 <= 1; x2++) {
				placeBlock(Blocks.AIR, new BlockPos(x2, 1, 0));
				placeBlock(Blocks.AIR, new BlockPos(x2, 2, 0));
			}
			placeBlock(Blocks.AIR, new BlockPos(0, 3, 0));

		}
	}

	private void placeChest(BlockPos pos, ResourceLocation loot) {
		BlockPos coords = this.offsetPos(pos, direction);
		world.setBlockState(coords, (IBlockState) Blocks.CHEST, direction);
		TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(coords);

		if (tileentitychest != null) {
			tileentitychest.setLootTable(loot, 2 + rand.nextInt(3));
			if (hasTrinket) {
				// North = -z, South = +z
				int sp = world.getSpawnPoint().getZ();
				int item = (coords.getY() < sp) ? 1 : 0;
				int artId = rand.nextInt(tileentitychest.getSizeInventory());
				tileentitychest.setInventorySlotContents(artId, new ItemStack(ComponentListMFR.ANCIENT_JEWEL_ADAMANT, 1, item));
			}
		}
	}

	public StructureModuleMFR setLoot(ResourceLocation loot) {
		this.lootType = loot;
		return this;
	}

	public StructureModuleMFR giveTrinket() {
		hasTrinket = true;
		return this;
	}
}
