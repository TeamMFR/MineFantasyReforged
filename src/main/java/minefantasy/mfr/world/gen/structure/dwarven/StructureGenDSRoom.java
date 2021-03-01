package minefantasy.mfr.world.gen.structure.dwarven;

import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyLoot;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.material.WoodMaterial;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.world.gen.structure.StructureGenAncientForge;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureGenDSRoom extends StructureModuleMFR {
    protected ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;
    protected Block floor_block = MineFantasyBlocks.COBBLESTONE_ROAD;
    private String type = "Basic";

    public StructureGenDSRoom(World world, StructureCoordinates position) {
        super(world, position);
        randomiseType();
    }

    public StructureGenDSRoom(World world, BlockPos pos, int direction) {
        super(world, pos, direction);
        randomiseType();
    }

    private void randomiseType() {
        type = geRandomType();
    }

    /**
     * @return 10% for Armoury----20& for Forge----30% for Study----40% for Living
     */
    private String geRandomType() {
        int c = rand.nextInt(10);
        if (c == 0)
            return "Armoury";// 0 (10%)
        else if (c < 3)
            return "Forge";// 1,2 (20%)
        else if (c < 6)
            return "Study";// 3,4,5 (30%)

        return "Living";// 6,7,8,9 (40%)
    }

    @Override
    public boolean canGenerate() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        int filledSpaces = 0, emptySpaces = 0;
        for (int x = -width_span; x <= width_span; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = 1; z <= depth; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    IBlockState state = this.getBlock(pos);
                    if (!allowBuildOverBlock(state.getBlock()) || this.isUnbreakable(pos, direction)) {
                        return false;
                    }
                    if (!state.getMaterial().isSolid()) {
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
                    placeBlock((Block) blockarray[0], new BlockPos(x, 0, z) );
                }
                // WALLS
                for (int y = 1; y <= height + 1; y++) {
                    blockarray = getWalls(width_span, depth, x, z);
                    if (blockarray != null) {
                        int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                        placeBlock((Block) blockarray[0], new BlockPos(x, y, z) );
                    }
                }
                // CEILING
                blockarray = getCeiling(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], new BlockPos(x, height + 1, z) );
                }

                // TRIM
                blockarray = getTrim(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], new BlockPos(x, height, z) );
                    if ((Block) blockarray[0] == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(h == 4 ? Blocks.GLOWSTONE : MineFantasyBlocks.REINFORCED_STONE, new BlockPos(x, h, z) );
                        }
                        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, new BlockPos( x, 1, z));
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway(width_span, depth, height);

        if (type.equalsIgnoreCase("Living")) {
            generateLivingHub();
        }
        if (type.equalsIgnoreCase("Armoury")) {
            generateArmoury();
        }
        if (type.equalsIgnoreCase("Forge")) {
            generateForge();
        }
        if (type.equalsIgnoreCase("Study")) {
            generateStudy();
        }
    }

    protected void buildDoorway(int width_span, int depth, int height) {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(Blocks.AIR, new BlockPos(x, y, z) );
                }
            }
        }
    }

    protected void tryPlaceHall(BlockPos pos, int d) {
        Class extension = getRandomExtension();
        if (extension != null) {
            mapStructure(pos, d, extension);
        }
    }

    protected int getHeight() {
        if (type.equalsIgnoreCase("Forge")) {
            return 7;
        }
        return 5;
    }

    protected int getDepthSpan() {
        if (type.equalsIgnoreCase("Living")) {
            return 19;
        }
        return 13;
    }

    protected int getWidthSpan() {
        return 5;
    }

    protected Class<? extends StructureModuleMFR> getRandomExtension() {
        if (rand.nextInt(4) == 0) {
            return StructureGenDSIntersection.class;
        }
        if (rand.nextInt(8) == 0 && this.pos.getY() > 24) {
            return StructureGenDSStairs.class;
        }
        return StructureGenDSRoom.class;
    }

    private Object[] getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2) || z == (int) Math.floor((float) depth / 2)) {
                return new Object[]{MineFantasyBlocks.REINFORCED_STONE_FRAMED, false};
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return new Object[]{MineFantasyBlocks.REINFORCED_STONE_FRAMED, false};
            }
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        return null;
    }

    private Object[] getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        return new Object[]{MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
    }

    private Object[] getFloor(int radius, int depth, int x, int z) {
        if (type.equalsIgnoreCase("Living") || type.equalsIgnoreCase("Study")) {
            floor_block = MineFantasyBlocks.REFINED_PLANKS;
            if (x > -2 && x < 2 && z > 3 && z < (depth - 3)) {
                floor_block = Blocks.OBSIDIAN;
            }
        }
        if (z < 2 && x >= -1 && x <= 1) {
            return new Object[]{floor_block, false};
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        return new Object[]{floor_block, false};
    }

    private Object[] getWalls(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
            }

            return new Object[]{MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
        }
        return new Object[]{Blocks.AIR, false};
    }

    public StructureModuleMFR setLoot(ResourceLocation loot) {
        this.lootType = loot;
        return this;
    }

    public void generateLivingHub() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();

        int zOffset = 4;

        boolean hall1 = rand.nextInt(3) == 0, hall2 = rand.nextInt(3) == 0, hall3 = rand.nextInt(3) == 0;
        int offset1 = hall1 ? width_span + 1 : width_span;
        int offset2 = hall2 ? -(width_span + 1) : -width_span;
        int offset3 = hall3 ? depth + 1 : depth;

        tryPlaceMinorRoom(new BlockPos(width_span, 0, zOffset), rotateLeft(), false);
        tryPlaceMinorRoom(new BlockPos(offset1, 0, depth - zOffset), rotateLeft(), hall1);

        tryPlaceMinorRoom(new BlockPos(-width_span, 0, zOffset), rotateRight(), false);
        tryPlaceMinorRoom(new BlockPos(offset2, 0, depth - zOffset), rotateRight(), hall2);
        tryPlaceMinorRoom(new BlockPos(0, 0, offset3), direction, hall3);
        // this.placeSpawner(0, 1, depth-5);

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(mob, new BlockPos(0, 1, depth - 5));
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 1);

    }

    public void generateStudy() {
        int width = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();

        for (int z = 0; z < depth; z++) {
            int ystart = 1;
            if (z == (int) Math.ceil((float) depth / 2) + 1 || z == (int) Math.floor((float) depth / 2) - 1) {
                ystart = 3;
                placeChest(new BlockPos(-(width - 1), 1, z), rotateLeft(), MineFantasyLoot.DWARVEN_STUDY);
                placeChest(new BlockPos(width - 1, 1, z), rotateRight(), MineFantasyLoot.DWARVEN_STUDY);
            }
            for (int y = ystart; y < height; y++) {
                if (getBlock(new BlockPos(-(width - 1), y, z)).getBlock().isReplaceable(world, pos)) {
                    placeBlock(Blocks.BOOKSHELF, new BlockPos( -(width - 1), y, z));
                }
                if (getBlock(new BlockPos(width - 1, y, z)).getBlock().isReplaceable(world, pos)) {
                    placeBlock(Blocks.BOOKSHELF, new BlockPos(width - 1, y, z) );
                }
            }
        }
        for (int x = 0; x < 3; x++) {
            placeBlock(Blocks.DOUBLE_STONE_SLAB,new BlockPos(-1 + x, 1, (int) Math.floor((float) depth / 2) - 1) );
            placeBlock(Blocks.DOUBLE_STONE_SLAB, new BlockPos(-1 + x, 1, (int) Math.ceil((float) depth / 2) + 1) );
        }
        placeBlock(MineFantasyBlocks.SCHEMATIC_GENERAL, new BlockPos(0, 2, (int) Math.floor((float) depth / 2) - 1));
        placeBlock(MineFantasyBlocks.SCHEMATIC_GENERAL, new BlockPos(0, 2, (int) Math.ceil((float) depth / 2) + 1) );
        // this.placeSpawner(0, 1, depth/2, "Silverfish");
        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(mob,new BlockPos(0, 1, depth / 2) );
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 2);
    }

    public void generateArmoury() {
        int width = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();

        for (int y = 1; y <= 4; y++) {
            placeBlock(MineFantasyBlocks.FRAME_BLOCK, new BlockPos(0, y, depth - 1) );

            placeBlock(MineFantasyBlocks.FRAME_BLOCK, new BlockPos(-1, y, depth - 2) );
            placeBlock(MineFantasyBlocks.FRAME_BLOCK, new BlockPos(1, y, depth - 2) );
        }
        placeBlock(MineFantasyBlocks.COGWORK_HOLDER, new BlockPos(0, 4, depth - 2) );

        int z = (int) Math.ceil((float) depth / 2);
        for (int x = -width; x <= width; x++) {
            placeBlock(x == 0 ? MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON : MineFantasyBlocks.REINFORCED_STONE,new BlockPos(x, height, z) );
            if (x < -1 || x > 1) {
                if (getBlock(new BlockPos(x, 1, z)).getBlock().isReplaceable(world, pos)) {
                    Block block = (x == -2 || x == 2) ? MineFantasyBlocks.REINFORCED_STONE_FRAMED
                            : MineFantasyBlocks.REINFORCED_STONE;
                    placeBlock(block, new BlockPos(x, 1, z) );
                }
            }
            for (int y = 2; y < height; y++) {
                if (x < -1 || x > 1 || y > 3) {
                    if (getBlock(new BlockPos(x, y, z)).getBlock().isReplaceable(world, pos)) {
                        placeBlock(MineFantasyBlocks.BRONZE_BARS, new BlockPos(x, y, z) );
                    }
                }
            }
        }
        placeChest(new BlockPos(-(width - 2), 1, z - 1), direction, MineFantasyLoot.DWARVEN_ARMOURY);
        placeChest(new BlockPos(-(width - 3), 1, z- 1) , direction, MineFantasyLoot.DWARVEN_ARMOURY);
        placeChest(new BlockPos((width - 2), 1, z - 1), direction, MineFantasyLoot.DWARVEN_ARMOURY);
        placeChest(new BlockPos((width - 3), 1, z - 1), direction, MineFantasyLoot.DWARVEN_ARMOURY);

        for (int z1 = 1; z1 < z; z1++) {
            for (int y = 1; y < height; y++) {
                if (getBlock(new BlockPos(-(width - 1), y, z1)).getBlock().isReplaceable(world, pos)) {
                    if (y == 1 || y == 3) {
                        placeChest(new BlockPos(-(width - 1), y, z1), rotateRight(), MineFantasyLoot.DWARVEN_HOME);
                    } else {
                        placeBlock(Blocks.STONE_SLAB, new BlockPos(-(width - 1), y, z1) );
                    }
                }
                if (getBlock(new BlockPos((width - 1), y, z1)).getBlock().isReplaceable(world, pos)) {
                    if (y == 1 || y == 3) {
                        placeChest(new BlockPos((width - 1), y, z1), rotateLeft(), MineFantasyLoot.DWARVEN_HOME);
                    } else {
                        placeBlock(Blocks.STONE_SLAB, new BlockPos((width - 1), y, z), 1);
                    }
                }
            }
        }
        for (int z1 = 0; z1 < 4; z1++) {
            placeRack(new BlockPos((width - 1), 2, depth - 2 - z1), rotateRight());
            placeRack(new BlockPos(-(width - 1), 2, depth - 2 - z1), rotateLeft());
        }
        placeBlock(MineFantasyBlocks.SCHEMATIC_GENERAL, new BlockPos(-2, 2, depth - 1) );
        placeBlock(MineFantasyBlocks.SCHEMATIC_GENERAL, new BlockPos(2, 2, depth - 1) );
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(-2, 1, depth - 1) );
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(2, 1, depth - 1) );
        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(mob, new BlockPos(0, 1, z));
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 3);
    }

    private void placeRack(BlockPos pos, int newDirection) {
        placeBlock(MineFantasyBlocks.TOOL_RACK_WOOD, pos, newDirection );
        TileEntity tile = this.getTileEntity(pos, direction);
        if (tile != null && tile instanceof TileEntityRack) {
            setupRack((TileEntityRack) tile);
        }
    }

    private void setupRack(TileEntityRack rack) {
        rack.setMaterial(CustomMaterial.getMaterial("ScrapWood"));
        ItemWeaponMFR[] items = new ItemWeaponMFR[]{CustomToolListMFR.STANDARD_SWORD, CustomToolListMFR.STANDARD_WARAXE,
                CustomToolListMFR.STANDARD_MACE, CustomToolListMFR.STANDARD_DAGGER};
        for (int i = 0; i < rack.getInventory().getSlots(); i++) {
            if (rand.nextInt(3) != 0) {
                ItemWeaponMFR loot = items[rand.nextInt(items.length)];
                rack.getInventory().setStackInSlot(i, loot.construct(MetalMaterial.IRON, WoodMaterial.SCRAP_WOOD));
            }
        }
    }

    private void generateForge() {
        boolean reverseForge = rand.nextBoolean();
        int width = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        int z = (int) Math.ceil((float) depth / 2);

        int position = reverseForge ? -1 : 1;

        placeBlock(MineFantasyBlocks.CHIMNEY_STONE_EXTRACTOR, new BlockPos((width * position), 2, z));
        placeBlock(MineFantasyBlocks.CHIMNEY_STONE_EXTRACTOR, new BlockPos((width * position), 2, z - 1) );
        placeBlock(MineFantasyBlocks.CHIMNEY_PIPE, new BlockPos((width * position), 3, z));
        placeBlock(MineFantasyBlocks.CHIMNEY_PIPE, new BlockPos((width * position), 3, z - 1));
        placeBlock(MineFantasyBlocks.CHIMNEY_PIPE, new BlockPos((width * position), 3, z + 1) );
        placeBlock(MineFantasyBlocks.CHIMNEY_PIPE, new BlockPos((width * position), 3, z + 2) );
        placeBlock(MineFantasyBlocks.CHIMNEY_PIPE, new BlockPos(((width - 1) * position), 3, z + 2) );
        placeBlock(MineFantasyBlocks.CHIMNEY_PIPE, new BlockPos(((width - 1) * position), 4, z + 2) );
        placeBlock(MineFantasyBlocks.CHIMNEY_PIPE, new BlockPos(((width - 1) * position), 5, z + 2) );

        int chestFacing = position < 0 ? rotateLeft() : rotateRight();
        placeChest(new BlockPos((-(width - 2) * position), 1, z), chestFacing, MineFantasyLoot.DWARVEN_FORGE);
        placeChest(new BlockPos((-(width - 2) * position), 1, z - 1), chestFacing, MineFantasyLoot.DWARVEN_FORGE);

        placeBlock(MineFantasyBlocks.FORGE,new BlockPos(((width - 1) * position), 1, z) );
        placeBlock(MineFantasyBlocks.FORGE, new BlockPos(((width - 1) * position), 1, z - 1) );
        placeBlock(Blocks.AIR, new BlockPos(((width - 1) * position), 2, z));
        placeBlock(Blocks.AIR, new BlockPos(((width - 1) * position), 2, z - 1)) ;

        placeBlock(MineFantasyBlocks.SCHEMATIC_GENERAL, new BlockPos(((width - 1) * position), 2, z + 2) );
        placeBlock(MineFantasyBlocks.SCHEMATIC_GENERAL, new BlockPos(((width - 1) * position), 2, z - 3));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(((width - 1) * position), 1, z + 2));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(((width - 1) * position), 1, z - 3));

        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, new BlockPos(((width - 1) * position), 1, z - 2)) ;
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, new BlockPos(((width - 1) * position), 1, z + 1)) ;
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(((width - 1) * position), 2, z - 2)) ;
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(((width - 1) * position), 2, z + 1)) ;
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, new BlockPos(((width - 1) * position), 3, z - 2)) ;
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, new BlockPos(((width - 1) * position), 3, z + 1) );

        placeBlock(MineFantasyBlocks.ANVIL_IRON, new BlockPos(position * 2, 1, z));
        placeBlock(Blocks.CAULDRON, new BlockPos(position * 2, 1, z - 1));

        placeMiscMachine1(new BlockPos(-(width - 2) * position, 0, depth - 3));
        placeMiscMachine1(new BlockPos(-(width - 2) * position, 0, 3));
        // this.placeSpawner(0, 1, depth/2);

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(mob,new BlockPos( 0, 1, depth / 2));
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 2);

    }

    private void placeMiscMachine1(BlockPos pos) {
        placeBlock(Blocks.LAVA, pos);
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, pos.add(-1, 1, -1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, pos.add(-1, 1, 1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, pos.add(1, 1, 1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED, pos.add(1, 1, -1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, pos.add(-1, 2, -1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, pos.add(-1, 2, 1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, pos.add(1, 2, -1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, pos.add(1, 2, 1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, pos.add(0, 2, 0));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, pos.add(0, 3, 0));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, pos.add(1, 2, 0));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, pos.add(-1, 2, 0));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, pos.add(0, 2, 1));
        placeBlock(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, pos.add(0, 2, -1));

    }

    private void placeChest(BlockPos pos, int d, ResourceLocation dwarvenArmoury) {

        placeBlock(Blocks.CHEST,pos, d);
        TileEntityChest tile = (TileEntityChest) getTileEntity(pos, direction);

        if (tile != null) {

            tile.setLootTable(lootType, 5 + rand.nextInt(5));
        }
    }

    protected void tryPlaceMinorRoom(BlockPos pos, int d, boolean hall) {
        Class extension = hall ? StructureGenDSHall.class : StructureGenDSRoomSml2.class;
        if (extension != null) {
            mapStructure(pos, d, extension);
        }
    }
}
