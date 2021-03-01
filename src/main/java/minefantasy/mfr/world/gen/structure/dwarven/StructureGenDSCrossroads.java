package minefantasy.mfr.world.gen.structure.dwarven;

import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureGenDSCrossroads extends StructureModuleMFR {
	protected ResourceLocation lootType = LootTableList.CHESTS_ABANDONED_MINESHAFT;
	protected Block floor_block = MineFantasyBlocks.COBBLESTONE_ROAD;

	public StructureGenDSCrossroads(World world, StructureCoordinates position) {
		super(world, position);
	}

	public StructureGenDSCrossroads(World world, BlockPos pos, int direction) {
		super(world, pos, direction);
	}

	@Override
	public boolean canGenerate() {
		if (lengthId == -100) {
			return true;
		}
		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		int filledSpaces = 0, emptySpaces = 0;
		for (int x = -width_span; x <= width_span; x++) {
			for (int y = 0; y <= height; y++) {
				for (int z = 1; z <= depth; z++) {
					BlockPos pos = new BlockPos(x, y, z);
					Block block = (Block) this.getBlock(pos);
					if (!allowBuildOverBlock(block) || this.isUnbreakable(pos, direction)) {
						return false;
					}
					if (!block.getMaterial(block.getDefaultState()).isSolid()) {
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
		if (lengthId == -100) {
			this.lengthId = ConfigWorldGen.DSLength;
			this.deviationCount = ConfigWorldGen.DSDeviations;
		}

		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		for (int x = -width_span; x <= width_span; x++) {
			for (int z = 0; z <= depth; z++) {
				BlockPos floorPos = new BlockPos(x, 0, z);
				Object[] blockarray;
				// FLOOR
				blockarray = getFloor(width_span, depth, x, z);
				if (blockarray != null) {
					placeBlock((Block) blockarray[0], floorPos);
				}
				// WALLS
				for (int y = 1; y <= height + 1; y++) {
					BlockPos wallPos = new BlockPos(x, y, z);
					blockarray = getWalls(width_span, depth, x, z);
					if (blockarray != null) {
						placeBlock((Block) blockarray[0], wallPos);
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
					placeBlock((Block) blockarray[0], new BlockPos(x, height, z));
					placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(x, 4, z));
					if (blockarray[0] == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
						for (int h = height - 1; h > 1; h--) {
							placeBlock(h == 5 ? Blocks.GLOWSTONE : MineFantasyBlocks.REINFORCED_STONE, new BlockPos(x, h, z));
						}
						placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, new BlockPos(x, 1, z));
					}
				}

			}
		}

		// DOORWAY
		buildDoorway(width_span, depth, height);

		generateCrossroad();
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

	protected int getHeight() {
		return 7;
	}

	protected int getDepthSpan() {
		return 19;
	}

	protected int getWidthSpan() {
		return 7;
	}

	private Object[] getTrim(int radius, int depth, int x, int z) {
		if (x == -radius || x == radius || z == depth || z == 0) {
			return null;
		}
		if (x == -(radius - 1) || x == (radius - 1)) {
			if (z == (int) Math.ceil((float) depth / 2) || z == (int) Math.floor((float) depth / 2)) {
				return new Object[] {MineFantasyBlocks.REINFORCED_STONE_FRAMED, false};
			}
		}
		if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
			if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
					|| (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
				return new Object[] {MineFantasyBlocks.REINFORCED_STONE_FRAMED, false};
			}
			int m = 0;
			if (x == 0 || z == 5 || z == (depth - 5)) {
				m = 1;
			}
			return new Object[] {MineFantasyBlocks.REINFORCED_STONE, m};
		}
		return null;
	}

	private Object[] getCeiling(int radius, int depth, int x, int z) {
		if (x == -radius || x == radius || z == depth || z == 0) {
			return null;
		}
		if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
			return new Object[] {MineFantasyBlocks.REINFORCED_STONE, false};
		}
		return new Object[] {MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
	}

	private Object[] getFloor(int radius, int depth, int x, int z) {
		if (z < 2 && x >= -1 && x <= 1) {
			return new Object[] {floor_block, false};
		}
		if (x == -radius || x == radius || z == depth || z == 0) {
			return new Object[] {MineFantasyBlocks.REINFORCED_STONE, false};
		}
		if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
			return new Object[] {MineFantasyBlocks.REINFORCED_STONE, false};
		}
		return new Object[] {floor_block, false};
	}

	private Object[] getWalls(int radius, int depth, int x, int z) {
		if (x == -radius || x == radius || z == depth || z == 0) {
			if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
				return new Object[] {MineFantasyBlocks.REINFORCED_STONE, false};
			}

			return new Object[] {MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
		}
		return new Object[] {Blocks.AIR, false};
	}

	public StructureModuleMFR setLoot(ResourceLocation loot) {
		this.lootType = loot;
		return this;
	}

	public void generateCrossroad() {
		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();

		int zOffset = 4;

		boolean stairSide = rand.nextBoolean();

		tryPlaceCrossroadHall(0, 0, depth + 1, direction, stairSide, 0);

		tryPlaceCrossroadHall((width_span + 1), 0, zOffset, rotateLeft(), stairSide, 20);
		tryPlaceCrossroadHall((width_span + 1), 0, depth - zOffset, rotateLeft(), !stairSide, 40);

		tryPlaceCrossroadHall(-(width_span + 1), 0, zOffset, rotateRight(), stairSide, 60);
		tryPlaceCrossroadHall(-(width_span + 1), 0, depth - zOffset, rotateRight(), !stairSide, 80);

		this.placeMinotaur(0, 1, depth - 2, 1);
		this.placeMinotaur(0, 1, 2, 1);

		int w = 2, d = 3;
		for (int x = -w; x <= w; x++) {
			for (int z = -d; z <= d; z++) {
				Block block = Blocks.WATER;
				if (z == -d || z == d || x == -w || x == w) {
					block = MineFantasyBlocks.REINFORCED_STONE;
				}
				this.placeBlock(block, new BlockPos(x, 1, depth / 2 + z));
			}
		}

	}

	private void placeMinotaur(int x, int y, int z, int tier) {
		EntityMinotaur mob = new EntityMinotaur(world);
		this.placeEntity(mob, new BlockPos(x, y, z));
		mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
		mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), tier);
	}

	protected void tryPlaceCrossroadHall(int x, int y, int z, int d, boolean stair, int delay) {
		Class extension = stair ? StructureGenDSStairs.class : StructureGenDSHall.class;
		if (extension != null) {
			mapStructure(new BlockPos(x, y, z), d, extension, delay);
		}
	}
}
