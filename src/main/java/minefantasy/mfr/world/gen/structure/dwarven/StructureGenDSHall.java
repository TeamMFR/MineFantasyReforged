package minefantasy.mfr.world.gen.structure.dwarven;

import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.world.gen.structure.StructureGenAncientForge;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenDSHall extends StructureModuleMFR {
	protected static Block floor = MineFantasyBlocks.COBBLESTONE_ROAD;
	protected ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;

	public StructureGenDSHall(World world, StructureCoordinates position) {
		super(world, position);
	}

	public StructureGenDSHall(World world, BlockPos pos, int direction) {
		super(world, pos, direction);
	}

	public static int getRandomEngravedWall(Random rand) {
		return 2 + rand.nextInt(3);
	}

	@Override
	public boolean canGenerate() {
		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		int empty_spaces = 0;
		int filledSpaces = 0, emptySpaces = 0;
		for (int x = -width_span; x <= width_span; x++) {
			for (int y = 0; y <= height; y++) {
				for (int z = 1; z <= depth; z++) {
					IBlockState block = this.getBlock(new BlockPos(x, y, z));
					if (!allowBuildOverBlock(block.getBlock()) || this.isUnbreakable(new BlockPos(x, y, z), direction)) {
						return false;
					}
					if (!block.getMaterial().isSolid()) {
						++emptySpaces;
					} else {
						++filledSpaces;
					}
				}
			}
		}
		if (WorldGenDwarvenStronghold.debug_air) {
			return true;
		}
		return ((float) emptySpaces / (float) (emptySpaces + filledSpaces)) < WorldGenDwarvenStronghold.maxAir;// at
		// least
		// 75%
		// full
	}

	private boolean allowBuildOverBlock(Block block) {
		if (block == MineFantasyBlocks.REINFORCED_STONE_BRICKS || block == MineFantasyBlocks.REINFORCED_STONE) {
			return false;
		}
		return true;
	}

	@Override
	public void generate() {
		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		for (int x = -width_span; x <= width_span; x++) {
			for (int z = 0; z <= depth; z++) {
				Object[] blockarray;

				// FLOOR
				blockarray = getFloor(width_span, depth, x, z);
				if (blockarray != null) {
					int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
					placeBlock((Block) blockarray[0], new BlockPos(x, 0, z));
				}
				// WALLS
				for (int y = 1; y <= height + 1; y++) {
					blockarray = getWalls(width_span, height, depth, new BlockPos(x, y, z));
					if (blockarray != null) {
						placeBlock((Block) blockarray[0], new BlockPos(x, y, z));
					}
				}
				// CEILING
				blockarray = getCeiling(width_span, depth, x, z);
				if (blockarray != null) {
					placeBlock((Block) blockarray[0], new BlockPos(x, height + 1, z));
				}

				// TRIM
				blockarray = getTrim(width_span, depth, x, z);
				if (blockarray != null) {
					int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
					placeBlock((Block) blockarray[0], new BlockPos(x, height, z));
					if ((Block) blockarray[0] == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
						for (int h = height - 1; h > 1; h--) {
							placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(x, h, z));
						}
						placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, new BlockPos(x, 1, z));
					}
				}

			}
		}

		// DOORWAY
		buildDoorway(width_span, depth, height);

		if (lengthId > 0) {
			buildNext(width_span, depth, height);
		} else {
			mapStructure(new BlockPos(0, 0, depth), StructureGenDSRoom.class);
		}
		if (rand.nextInt(3) != 0) {
			tryPlaceMinorRoom(new BlockPos(width_span, 0, (int) Math.floor((float) depth / 2)), rotateLeft());
		}
		if (rand.nextInt(3) != 0) {
			tryPlaceMinorRoom(new BlockPos(-(width_span), 0, (int) Math.floor((float) depth / 2)), rotateRight());
		}
		if (this instanceof StructureGenDSIntersection || this.lengthId % 2 == 0) {
			placeBlock(Blocks.GLOWSTONE, new BlockPos(0, 0, height + 1), depth / 2);
		}
	}

	protected void buildDoorway(int width_span, int depth, int height) {
		for (int x = -1; x <= 1; x++) {
			for (int y = 1; y <= 3; y++) {
				for (int z = 0; z >= -1; z--) {
					placeBlock(Blocks.AIR, new BlockPos(x, y, z));
				}
			}
		}
	}

	protected void buildNext(int width_span, int depth, int height) {
		tryPlaceHall(new BlockPos(0, 0, depth), direction);
	}

	protected void tryPlaceHall(BlockPos pos, int d) {
		Class extension = getRandomExtension();
		if (extension != null) {
			mapStructure(pos, d, extension);
		}
	}

	protected void tryPlaceMinorRoom(BlockPos pos, int d) {
		Class extension = getRandomMinorRoom();
		if (extension != null) {
			mapStructure(pos, d, extension);
		}
	}

	protected int getHeight() {
		return 4;
	}

	protected int getDepthSpan() {
		return 9;
	}

	protected int getWidthSpan() {
		return 3;
	}

	protected Class<? extends StructureModuleMFR> getRandomMinorRoom() {
		return StructureGenDSRoomSml.class;
	}

	protected Class<? extends StructureModuleMFR> getRandomExtension() {
		if (lengthId == 1) {
			return StructureGenDSRoom.class;
		}
		if (deviationCount > 0 && rand.nextInt(4) == 0) {
			return StructureGenDSIntersection.class;
		}
		if (rand.nextInt(16) == 0 && this.pos.getY() >= StructureGenDSStairs.minLevel) {
			return StructureGenDSStairs.class;
		}
		return StructureGenDSHall.class;
	}

	protected Object[] getTrim(int radius, int depth, int x, int z) {
		if (x == -radius || x == radius) {
			return null;
		}

		if (x == -(radius - 1) || x == (radius - 1)) {
			if (z == Math.floor((float) depth / 2)) {
				return new Object[] {MineFantasyBlocks.REINFORCED_STONE_FRAMED, false};
			}
			return new Object[] {MineFantasyBlocks.REINFORCED_STONE, false};
		}
		return null;
	}

	protected Object[] getCeiling(int radius, int depth, int x, int z) {
		return x == 0 ? new Object[] {MineFantasyBlocks.REINFORCED_STONE, false}
				: new Object[] {MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
	}

	protected Object[] getFloor(int radius, int depth, int x, int z) {
		if (x >= -1 && x <= 1) {
			return new Object[] {floor, false};
		}
		if (x == -radius || x == radius || z == depth || z == 0) {
			return new Object[] {MineFantasyBlocks.REINFORCED_STONE, false};
		}
		if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
			return new Object[] {MineFantasyBlocks.REINFORCED_STONE, false};
		}
		return new Object[] {floor, false};
	}

	protected Object[] getWalls(int radius, int height, int depth, BlockPos pos) {
		if (pos.getX() != -radius && pos.getX() != radius && pos.getZ() == 0) {
			return new Object[] {Blocks.AIR, false};
		}
		if (pos.getX() == -radius || pos.getX() == radius || pos.getZ() == depth) {
			return pos.getY() == height / 2 ? new Object[] {MineFantasyBlocks.REINFORCED_STONE, "Hall"}
					: new Object[] {MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
		}
		return new Object[] {Blocks.AIR, false};
	}

	public StructureModuleMFR setLoot(ResourceLocation loot) {
		this.lootType = loot;
		return this;
	}
}
