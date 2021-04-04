package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyLoot;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.item.ItemBomb;
import minefantasy.mfr.item.ItemMine;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.WoodMaterial;
import minefantasy.mfr.tile.TileEntityAmmoBox;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import minefantasy.mfr.world.gen.structure.WorldGenDwarvenStronghold;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.List;
import java.util.Random;

public class StructureGenDSRoom extends StructureModuleMFR {
    private static final String[] possible_types = new String[]{"living", "forge", "study", "armoury"};
    protected ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;
    protected Block floor_block = MineFantasyBlocks.COBBLE_BRICK;
    private String type = "basic";

    public StructureGenDSRoom(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
        randomiseType();
    }

    private void randomiseType() {
        type = getRandomType();
    }

    /**
     * @return 10% for Armoury----20& for Forge----30% for Study----40% for Living
     */
    private String getRandomType() {
        int c = random.nextInt(10);
        if (c == 0)
            return "armoury";// 0 (10%)
        else if (c < 3)
            return "forge";// 1,2 (20%)
        else if (c < 6)
            return "study";// 3,4,5 (30%)

        return "living";// 6,7,8,9 (40%)
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
                    IBlockState state = world.getBlockState(offsetPos(x, y, z, facing));
                    if (!allowBuildOverBlock(state) || this.isUnbreakable(world, x, y, z, facing)) {
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

    private boolean allowBuildOverBlock(IBlockState state) {
        return state != MineFantasyBlocks.REINFORCED_STONE_BRICKS.getDefaultState() && state != MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
    }

    @Override
    public void generate() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        for (int x = -width_span; x <= width_span; x++) {
            for (int z = 0; z <= depth; z++) {
                Block block;
                // FLOOR
                block = getFloor(width_span, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, 0, z);
                }
                // WALLS
                for (int y = 1; y <= height + 1; y++) {
                    block = getWalls(width_span, depth, x, z);
                    if (block != null) {
                        placeBlock(world, block,  x, y, z);
                    }
                }
                // CEILING
                block = getCeiling(width_span, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, height + 1, z);
                }

                // TRIM
                block = getTrim(width_span, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, height, z);
                    if (block == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(world, h == 4 ? Blocks.GLOWSTONE : MineFantasyBlocks.REINFORCED_STONE, x, h,
                                    z);
                        }
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x, 1, z);
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway();

        if (type.equalsIgnoreCase("living")) {
            generateLivingHub();
        }
        if (type.equalsIgnoreCase("armoury")) {
            generateArmoury();
        }
        if (type.equalsIgnoreCase("forge")) {
            generateForge();
        }
        if (type.equalsIgnoreCase("study")) {
            generateStudy();
        }

        MineFantasyReborn.LOG.error("Placed DS Room at: " + pos);
    }

    protected void buildDoorway() {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(world, Blocks.AIR, x, y, z);
                }
            }
        }
    }

    protected int getHeight() {
        if (type.equalsIgnoreCase("forge")) {
            return 7;
        }
        return 5;
    }

    protected int getDepthSpan() {
        if (type.equalsIgnoreCase("living")) {
            return 19;
        }
        return 13;
    }

    protected int getWidthSpan() {
        return 5;
    }

    private Block getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2) || z == (int) Math.floor((float) depth / 2)) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED;
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED;
            }
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return null;
    }

    private Block getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return MineFantasyBlocks.REINFORCED_STONE_BRICKS;
    }

    private Block getFloor(int radius, int depth, int x, int z) {
        if (type.equalsIgnoreCase("living") || type.equalsIgnoreCase("study")) {
            floor_block = MineFantasyBlocks.REFINED_PLANKS;
            if (x > -2 && x < 2 && z > 3 && z < (depth - 3)) {
                floor_block = Blocks.OBSIDIAN;
            }
        }
        if (z < 2 && x >= -1 && x <= 1) {
            return floor_block;
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return floor_block;
    }

    private Block getWalls(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return MineFantasyBlocks.REINFORCED_STONE;
            }

            return MineFantasyBlocks.REINFORCED_STONE_BRICKS;
        }
        return Blocks.AIR;
    }

    public StructureModuleMFR setLoot(ResourceLocation loot) {
        this.lootType = loot;
        return this;
    }

    public void generateLivingHub() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();

        int zOffset = 4;

        boolean hall1 = random.nextInt(3) == 0, hall2 = random.nextInt(3) == 0, hall3 = random.nextInt(3) == 0;
        int offset1 = hall1 ? width_span + 1 : width_span;
        int offset2 = hall2 ? -(width_span + 1) : -width_span;
        int offset3 = hall3 ? depth + 1 : depth;

        tryPlaceMinorRoom(width_span, 0, zOffset, false);
        tryPlaceMinorRoom(offset1, 0, depth - zOffset, hall1);

        tryPlaceMinorRoom(-width_span, 0, zOffset, false);
        tryPlaceMinorRoom(offset2, 0, depth - zOffset, hall2);
        tryPlaceMinorRoom(0, 0, offset3, hall3);
        // this.placeSpawner(0, 1, depth-5);

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(world, mob, 0, 1, depth - 5);
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
                placeChest(world, -(width - 1), 1, z, random, facing.rotateYCCW(), MineFantasyLoot.DWARVEN_STUDY);
                placeChest(world, width - 1, 1, z, random, facing.rotateY(), MineFantasyLoot.DWARVEN_STUDY);
            }
            for (int y = ystart; y < height; y++) {
                if (world.getBlockState(offsetPos(-(width - 1), y, z, facing)).getMaterial().isReplaceable()) {
                    placeBlock(world, Blocks.BOOKSHELF, -(width - 1), y, z);
                }
                if (world.getBlockState(offsetPos(width - 1, y, z, facing)).getMaterial().isReplaceable()) {
                    placeBlock(world, Blocks.BOOKSHELF, width - 1, y, z);
                }
            }
        }
        for (int x = 0; x < 3; x++) {
            placeBlock(world, Blocks.DOUBLE_STONE_SLAB, -1 + x, 1, (int) Math.floor((float) depth / 2) - 1);
            placeBlock(world, Blocks.DOUBLE_STONE_SLAB, -1 + x, 1, (int) Math.ceil((float) depth / 2) + 1);
        }
        placeBlock(world, MineFantasyBlocks.SCHEMATIC_ALLOY,0, 2, (int) Math.floor((float) depth / 2) - 1);
        placeBlock(world, MineFantasyBlocks.SCHEMATIC_ALLOY,  0, 2, (int) Math.ceil((float) depth / 2) + 1);
        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(world, mob, 0, 1, depth / 2);
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 2);
    }

    public void generateArmoury() {
        int width = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();

        for (int y = 1; y <= 4; y++) {
            placeBlock(world, MineFantasyBlocks.FRAME_BLOCK,0, y, depth - 1);

            placeBlock(world, MineFantasyBlocks.FRAME_BLOCK,-1, y, depth - 2);
            placeBlock(world, MineFantasyBlocks.FRAME_BLOCK,1, y, depth - 2);
        }
        placeBlock(world, MineFantasyBlocks.COGWORK_HOLDER,0, 4, depth - 2);

        int z = (int) Math.ceil((float) depth / 2);
        for (int x = -width; x <= width; x++) {
            placeBlock(world, x == 0 ? MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON : MineFantasyBlocks.REINFORCED_STONE, x, height, z);
            if (x < -1 || x > 1) {
                if (world.getBlockState(offsetPos(x, 1, z, facing)).getMaterial().isReplaceable()) {
                    Block block = (x == -2 || x == 2) ? MineFantasyBlocks.REINFORCED_STONE_FRAMED : MineFantasyBlocks.REINFORCED_STONE;
                    placeBlock(world, block, x, 1, z);
                }
            }
            for (int y = 2; y < height; y++) {
                if (x < -1 || x > 1 || y > 3) {
                    if (world.getBlockState(offsetPos(x, y, z, facing)).getMaterial().isReplaceable()) {
                        placeBlock(world, MineFantasyBlocks.BRONZE_BARS,  x, y, z);
                    }
                }
            }
        }
        placeChest(world, -(width - 2), 1, z - 1, random, facing, MineFantasyLoot.DWARVEN_ARMOURY);
        placeChest(world, -(width - 3), 1, z - 1, random, facing, MineFantasyLoot.DWARVEN_ARMOURY);
        placeChest(world, (width - 2), 1, z - 1, random, facing, MineFantasyLoot.DWARVEN_ARMOURY);
        placeChest(world, (width - 3), 1, z - 1, random, facing, MineFantasyLoot.DWARVEN_ARMOURY);

        for (int z1 = 1; z1 < z; z1++) {
            for (int y = 1; y < height; y++) {
                if (world.getBlockState(offsetPos(-(width - 1), y, z1, facing)).getMaterial().isReplaceable()) {
                    if (y == 1 || y == 3) {
                        placeAmmoBox(world, -(width - 1), y, z1, facing.rotateY(), MineFantasyLoot.DWARVEN_AMMO_BOX);
                    } else {
                        placeBlock(world, Blocks.STONE_SLAB, -(width - 1), y, z1);
                    }
                }
                if (world.getBlockState(offsetPos((width - 1), y, z1, facing)).getMaterial().isReplaceable()) {
                    if (y == 1 || y == 3) {
                        placeAmmoBox(world, (width - 1), y, z1, facing.rotateYCCW(), MineFantasyLoot.DWARVEN_AMMO_BOX);
                    } else {
                        placeBlock(world, Blocks.STONE_SLAB, (width - 1), y, z1);
                    }
                }
            }
        }
        for (int z1 = 0; z1 < 4; z1++) {
            placeRack(world, (width - 1), 2, depth - 2 - z1, facing.rotateY());
            placeRack(world, -(width - 1), 2, depth - 2 - z1, facing.rotateYCCW());
        }
        placeBlock(world, MineFantasyBlocks.SCHEMATIC_ALLOY, -2, 2, depth - 1);
        placeBlock(world, MineFantasyBlocks.SCHEMATIC_ALLOY, 2, 2, depth - 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, -2, 1, depth - 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2, 1, depth - 1);
        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(world, mob, 0, 1, z);
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 3);
    }

    private void placeRack(World world, int x, int y, int z, EnumFacing newFacing) {
        placeBlock(world, MineFantasyBlocks.TOOL_RACK_WOOD, x, y, z, newFacing);
        TileEntity tile = world.getTileEntity(offsetPos(x, y, z, facing));
        if (tile instanceof TileEntityRack) {
            setupRack((TileEntityRack) tile);
        }
    }

    private void setupRack(TileEntityRack rack) {
        rack.setMaterial(CustomMaterial.getMaterial(MineFantasyMaterials.Names.SCRAP_WOOD));
        ItemWeaponMFR[] items = new ItemWeaponMFR[] {MineFantasyItems.STANDARD_SWORD, MineFantasyItems.STANDARD_WARAXE,
                MineFantasyItems.STANDARD_MACE, MineFantasyItems.STANDARD_DAGGER};
        for (int i = 0; i < rack.getInventory().getSlots(); i++) {
            if (random.nextInt(3) != 0) {
                ItemWeaponMFR loot = items[random.nextInt(items.length)];
                rack.getInventory().setStackInSlot(i, loot.construct(MineFantasyMaterials.Names.IRON, MineFantasyMaterials.Names.SCRAP_WOOD));
            }
        }
    }

    private void generateForge() {
        boolean reverseForge = random.nextBoolean();
        int width = getWidthSpan();
        int depth = getDepthSpan();
        int z = (int) Math.ceil((float) depth / 2);

        int position = reverseForge ? -1 : 1;

        placeBlock(world, MineFantasyBlocks.CHIMNEY_STONE_EXTRACTOR, (width * position), 2, z);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_STONE_EXTRACTOR, (width * position), 2, z - 1);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_PIPE, (width * position), 3, z);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_PIPE, (width * position), 3, z - 1);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_PIPE, (width * position), 3, z + 1);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_PIPE, (width * position), 3, z + 2);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_PIPE, ((width - 1) * position), 3, z + 2);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_PIPE, ((width - 1) * position), 4, z + 2);
        placeBlock(world, MineFantasyBlocks.CHIMNEY_PIPE, ((width - 1) * position), 5, z + 2);

        EnumFacing chestFacing = position < 0 ? facing.rotateYCCW() : facing.rotateY();
        placeChest(world, (-(width - 2) * position), 1, z, random, chestFacing, MineFantasyLoot.DWARVEN_FORGE);
        placeChest(world, (-(width - 2) * position), 1, z - 1, random, chestFacing, MineFantasyLoot.DWARVEN_FORGE);

        placeBlock(world, MineFantasyBlocks.FORGE,((width - 1) * position), 1, z);
        placeBlock(world, MineFantasyBlocks.FORGE,((width - 1) * position), 1, z - 1);
        placeBlock(world, Blocks.AIR,((width - 1) * position), 2, z);
        placeBlock(world, Blocks.AIR,((width - 1) * position), 2, z - 1);

        placeBlock(world, MineFantasyBlocks.SCHEMATIC_ALLOY, ((width - 1) * position), 2, z + 2);
        placeBlock(world, MineFantasyBlocks.SCHEMATIC_ALLOY, ((width - 1) * position), 2, z - 3);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, ((width - 1) * position), 1, z + 2);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, ((width - 1) * position), 1, z - 3);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, ((width - 1) * position), 1, z - 2);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, ((width - 1) * position), 1, z + 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, ((width - 1) * position), 2, z - 2);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, ((width - 1) * position), 2, z + 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, ((width - 1) * position), 3, z - 2);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, ((width - 1) * position), 3, z + 1);

        placeBlock(world, MineFantasyBlocks.ANVIL_IRON, position * 2, 1, z);
        placeBlock(world, Blocks.CAULDRON,  position * 2, 1, z - 1);

        placeMiscMachine1(-(width - 2) * position, 0, depth - 3);
        placeMiscMachine1(-(width - 2) * position, 0, 3);
        // this.placeSpawner(0, 1, depth/2);

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(world, mob, 0, 1, depth / 2);
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 2);

    }

    private void placeMiscMachine1(int x, int y, int z) {
        placeBlock(world, Blocks.LAVA, x, y, z);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x - 1, y + 1, z - 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x - 1, y + 1, z + 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x + 1, y + 1, z - 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x + 1, y + 1, z + 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x - 1, y + 2, z - 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x - 1, y + 2, z + 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x + 1, y + 2, z - 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x + 1, y + 2, z + 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, y + 2, z);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, y + 3, z);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, x + 1, y + 2, z);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, x - 1, y + 2, z);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, x, y + 2, z + 1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, x, y + 2, z - 1);

    }

    private void placeChest(World world, int x, int y, int z, Random random, EnumFacing facing, ResourceLocation loot) {
        placeBlock(world, Blocks.CHEST, x, y, z);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(offsetPos(x, y, z, facing));

        if (chest != null) {
            chest.setLootTable(loot, random.nextLong());
        }
    }

    private void placeAmmoBox(World world, int x, int y, int z, EnumFacing facing, ResourceLocation loot) {
        placeBlock(world, MineFantasyBlocks.AMMO_BOX_BASIC, x, y, z);
        TileEntityAmmoBox ammoBox = (TileEntityAmmoBox) world.getTileEntity(offsetPos(x, y, z, facing));

        if (ammoBox != null) {
            LootContext.Builder lootContext = new LootContext.Builder((WorldServer)this.world);
            List<ItemStack> result = this.world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(this.random, lootContext.build());
            ItemStack ammo = result.get(random.nextInt());
            ammoBox.setMaterial(WoodMaterial.getMaterial(MineFantasyMaterials.Names.REFINED_WOOD));
            ammoBox.inventoryStack = ammo;
            ammoBox.stock = ammo.getMaxStackSize() * (random.nextInt(2) + 1)
                    + (ammo.getCount() > 1 ? random.nextInt(ammo.getCount()) : 0);
            if (ammo.getItem() instanceof ItemBomb || ammo.getItem() instanceof ItemMine) {
                ammoBox.stock = Math.max(1, ammoBox.stock / 4);
            }
        }
    }

    protected void tryPlaceMinorRoom(int x, int y, int z, boolean hall) {
        Class<? extends StructureModuleMFR> extension = hall ? StructureGenDSHall.class : StructureGenDSRoomSml2.class;
        mapStructure(x, y, z, extension);
    }
}
