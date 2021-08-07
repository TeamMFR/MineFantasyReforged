package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenAncientForgeRoom extends StructureModuleMFR {

	private ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;
	private boolean hasTrinket = false;

	public StructureGenAncientForgeRoom(World world, BlockPos pos, EnumFacing facing, Random random) {
		super(world, pos, facing, random);
	}

	@Override
	public void generate() {
		int width = 7;
		int depth = 6;
		// FLOOR
		for (int x = -width / 2; x <= width / 2; x++) {
			for (int z = 0; z <= depth; z++) {
				placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), x, 0, z);
			}
		}

		// CEIL
		for (int x = -width / 2; x <= width / 2; x++) {
			for (int z = 0; z <= depth; z++) {
				placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), x, 5, z);
			}
		}
		// WALLS
		for (int x = -width / 2; x <= width / 2; x++) {
			for (int z = 0; z <= depth; z++) {
				for (int y = 1; y <= 4; y++) {
					placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), x, y, z);
				}
			}
		}
		// HOLLOW
		for (int x = (-width / 2) + 1; x <= (width / 2) - 1; x++) {
			for (int z = 1; z <= depth - 1; z++) {
				for (int y = 1; y <= 4; y++) {
					placeBlock(world, Blocks.AIR, x, y, z);
				}
			}
		}

		//ENTRANCE

		for (int x2 = -1; x2 <= 1; x2++) {
			placeBlock(world, Blocks.AIR, x2, 1, 0);
			placeBlock(world, Blocks.AIR, x2, 2, 0);

		}
		placeBlock(world, Blocks.AIR, 0, 3, 0);

		// CHEST
		int z = depth / 2;

		placeChest(world, 0, 1, depth - 1, random, facing, lootType);
		placeSpawner(world, 0, 1, z, EntityList.getKey(EntitySilverfish.class));

	}

	private void placeChest(World world, int x, int y, int z, Random random, EnumFacing facing, ResourceLocation loot) {
		world.setBlockState(offsetPos(x, y, z, facing), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, facing), 2);
		TileEntityChest chest = (TileEntityChest) world.getTileEntity(offsetPos(x, y, z, facing));

		if (chest != null) {
			chest.setLootTable(loot, random.nextLong());
			if (hasTrinket) {
                int artId = random.nextInt(chest.getSizeInventory());
                Item jewel = null;
                if (facing == EnumFacing.NORTH || facing == EnumFacing.WEST){
                	jewel = MineFantasyItems.ANCIENT_JEWEL_ADAMANT;
				}
                if (facing == EnumFacing.SOUTH || facing == EnumFacing.EAST){
                	jewel = MineFantasyItems.ANCIENT_JEWEL_MITHRIL;
				}
                if (jewel != null){
					chest.setInventorySlotContents(artId, new ItemStack(jewel, 1));
				}
			}
		}
	}

	public void setLoot(ResourceLocation loot) {
		this.lootType = loot;
    }

	public void giveTrinket() {
		hasTrinket = true;
    }
}
