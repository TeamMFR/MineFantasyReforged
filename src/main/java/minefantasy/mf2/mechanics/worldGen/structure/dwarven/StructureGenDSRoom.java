package minefantasy.mf2.mechanics.worldGen.structure.dwarven;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemArtefact;
import minefantasy.mf2.mechanics.worldGen.structure.LootTypes;
import minefantasy.mf2.mechanics.worldGen.structure.StructureGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class StructureGenDSRoom extends StructureModuleMF
{
	private String type = "Basic";
	private static final String[] possible_types = new String[]{"Living", "Armoury", "Forge", "Study"};
	public StructureGenDSRoom(World world, StructureCoordinates position)
	{
		super(world, position);
		randomiseType();
	}
	public StructureGenDSRoom(World world, int x, int y, int z, int direction) 
	{
		super(world, x, y, z, direction);
		randomiseType();
	}
	private void randomiseType() 
	{
		type = possible_types[rand.nextInt(possible_types.length)];
	}
	@Override
	public boolean canGenerate() 
	{
		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		int filledSpaces = 0, emptySpaces = 0;
		for(int x = -width_span; x <= width_span; x ++)
		{
			for(int y = 0; y <= height; y ++)
			{
				for(int z = 1; z <= depth; z ++)
				{
					Block block = this.getBlock(x, y, z);
					if(!allowBuildOverBlock(block) || this.isUnbreakable(x, y, z, direction))
					{
						return false;
					}
					if(!block.getMaterial().isSolid())
					{
						++emptySpaces;
					}
					else
					{
						++filledSpaces;
					}
				}
			}
		}
		if(WorldGenDwarvenStronghold.debug_air)
		{
			return true;
		}
		return ((float) emptySpaces / (float)filledSpaces) < 0.25F;//at least 75% full
	}
	
	private boolean allowBuildOverBlock(Block block)
	{
		if(block == Blocks.stonebrick || block == BlockListMF.reinforced_stone)
		{
			return false;
		}
		return true;
	}
	@Override
	public void generate() 
	{
		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		for(int x = -width_span; x <= width_span; x ++)
		{
			for(int z = 0; z <= depth; z ++)
			{
				Object[] blockarray;
				//FLOOR
				blockarray = getFloor(width_span, depth, x, z);
				if(blockarray != null)
				{
					int meta = (Boolean)blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
					placeBlock((Block)blockarray[0], meta, x, 0, z);
				}
				//WALLS
				for(int y = 1; y <= height+1; y ++)
				{
					blockarray = getWalls(width_span, depth, x, z);
					if(blockarray != null)
					{
						int meta = (Boolean)blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
						placeBlock((Block)blockarray[0], meta, x, y, z);
					}
				}
				//CEILING
				blockarray = getCeiling(width_span, depth, x, z);
				if(blockarray != null)
				{
					int meta = (Boolean)blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
					placeBlock((Block)blockarray[0], meta, x, height+1, z);
				}
				
				//TRIM
				blockarray = getTrim(width_span, depth, x, z);
				if(blockarray != null)
				{
					int meta = (Boolean)blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
					placeBlock((Block)blockarray[0], meta, x, height, z);
					if((Block)blockarray[0] == BlockListMF.reinforced_stone_framed)
					{
						for(int h = height-1; h > 1; h --)
						{
							placeBlock(BlockListMF.reinforced_stone, 0, x, h, z);
						}
						placeBlock(BlockListMF.reinforced_stone_framed, 0, x, 1, z);
					}
				}
				
			}
		}
		
		//DOORWAY
		buildDoorway(width_span, depth, height);
		
		if(type.equalsIgnoreCase("Living"))
		{
			generateLivingHub();
		}
		if(type.equalsIgnoreCase("Armoury"))
		{
			generateArmoury();
		}
		if(type.equalsIgnoreCase("Forge"))
		{
			generateForge();
		}
		if(type.equalsIgnoreCase("Study"))
		{
			generateStudy();
		}
	}
	
	protected void buildDoorway(int width_span, int depth, int height) 
	{
		for(int x = -1; x <= 1; x++)
		{
			for(int y = 1; y <= 3; y ++)
			{
				for(int z = 0; z >= -1; z --)
				{
					placeBlock(Blocks.air, 0, x, y, z);
				}
			}
		}
	}
	protected void tryPlaceHall(int x, int y, int z, int d) 
	{
		Class extension = getRandomExtension();
		if(extension != null)
		{
			mapStructure(x, y, z, d, extension);
		}
	}
	protected int getHeight() 
	{
		if(type.equalsIgnoreCase("Forge"))
		{
			return 8;
		}
		return 5;
	}
	protected int getDepthSpan()
	{
		if(type.equalsIgnoreCase("Living"))
		{
			return 19;
		}
		return 13;
	}
	protected int getWidthSpan() {
		return 5;
	}
	protected Class<? extends StructureModuleMF> getRandomExtension() 
	{
		if(rand.nextInt(4) == 0)
		{
			return StructureGenDSIntersection.class;
		}
		if( rand.nextInt(8) == 0 && this.yCoord > 24)
		{
			return StructureGenDSStairs.class;
		}
		return StructureGenDSRoom.class;
	}
	
	
	private Object[] getTrim(int radius, int depth, int x, int z)
	{
		if(x == -radius || x == radius || z == depth || z == 0)
		{
			return null;
		}
		if(x == -(radius-1) || x == (radius-1))
		{
			if(z == (int)Math.ceil( (float)depth / 2) || z == (int)Math.floor( (float)depth / 2))
			{
				return new Object[]{BlockListMF.reinforced_stone_framed, false};
			}
		}
		if(x == -(radius-1) || x == (radius-1) || z == (depth-1) || z == 1)
		{
			if((x == -(radius-1) && (z == (depth-1) || z == 1)) || (x == (radius-1) && (z == (depth-1) || z == 1)))
			{
				return new Object[]{BlockListMF.reinforced_stone_framed, false};
			}
			return new Object[]{BlockListMF.reinforced_stone, false};
		}
		return null;
	}
	private Object[] getCeiling(int radius, int depth, int x, int z)
	{
		if(x == -radius || x == radius || z == depth || z == 0)
		{
			return null;
		}
		if(x == -(radius-1) || x == (radius-1) || z == (depth-1) || z == 1)
		{
			return new Object[]{BlockListMF.reinforced_stone, false};
		}
		return new Object[]{Blocks.stonebrick, true};
	}
	
	private Object[] getFloor(int radius, int depth, int x, int z)
	{
		if(type.equalsIgnoreCase("Living") || type.equalsIgnoreCase("Study"))
		{
			floor_block = BlockListMF.refined_planks;
			if(x > -2 && x < 2 && z > 3 && z < (depth-3))
			{
				floor_block = Blocks.obsidian;
			}
		}
		if(z < 2 && x >= -1 && x <= 1)
		{
			return new Object[]{floor_block, false};
		}
		if(x == -radius || x == radius || z == depth || z == 0)
		{
			return new Object[]{BlockListMF.reinforced_stone, false};
		}
		if(x == -(radius-1) || x == (radius-1) || z == (depth-1) || z == 1)
		{
			return new Object[]{BlockListMF.reinforced_stone, false};
		}
		return new Object[]{floor_block, false};
	}
	
	private Object[] getWalls(int radius, int depth, int x, int z)
	{
		if(x == -radius || x == radius || z == depth || z == 0)
		{
			if((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0)))
			{
				return new Object[]{BlockListMF.reinforced_stone, false};
			}
			
			return new Object[]{Blocks.stonebrick, true};
		}
		return new Object[]{Blocks.air, false};
	}

	protected String lootType = ChestGenHooks.DUNGEON_CHEST;
	public StructureModuleMF setLoot(String loot) 
	{
		this.lootType = loot;
		return this;
	}
	protected Block floor_block = BlockListMF.cobble_pavement;
	
	public void generateLivingHub()
	{
		int width_span = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		
		int zOffset = 4;
		
		tryPlaceMinorRoom(width_span, 0, zOffset, rotateLeft());
		tryPlaceMinorRoom(width_span, 0, depth-zOffset, rotateLeft());
		
		tryPlaceMinorRoom(-width_span, 0, zOffset, rotateRight());
		tryPlaceMinorRoom(-width_span, 0, depth-zOffset, rotateRight());
	}
	public void generateStudy()
	{
		int width = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		
		for(int z = 0; z < depth; z ++)
		{
			int ystart = 1;
			if(z == (int)Math.ceil((float)depth/2)+1 || z == (int)Math.floor((float)depth/2)-1)
			{
				ystart = 3;
				placeChest(-(width-1), 1, z, rotateLeft(), LootTypes.DWARVEN_STUDY);
				placeChest(width-1, 1, z, rotateRight(), LootTypes.DWARVEN_STUDY);
			}
			for(int y = ystart; y < height; y ++)
			{
				if(getBlock(-(width-1), y, z).getMaterial().isReplaceable())
				{
					placeBlock(Blocks.bookshelf, 0, -(width-1), y, z);
				}
				if(getBlock(width-1, y, z).getMaterial().isReplaceable())
				{
					placeBlock(Blocks.bookshelf, 0, width-1, y, z);
				}
			}
		}
		for(int x = 0; x < 3; x ++)
		{
			placeBlock(Blocks.double_stone_slab, 0, -1+x, 1, (int)Math.floor((float)depth/2)-1);
			placeBlock(Blocks.double_stone_slab, 0, -1+x, 1, (int)Math.ceil((float)depth/2)+1);
		}
	}
	
	public void generateArmoury()
	{
		int width = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		
		for(int y = 1; y <= 4; y ++)
		{
			placeBlock(BlockListMF.frame_block, 0, 0, y, depth-1);
			
			placeBlock(BlockListMF.frame_block, 0, -1, y, depth-2);
			placeBlock(BlockListMF.frame_block, 0,  1, y, depth-2);
		}
		placeBlock(BlockListMF.cogwork_builder, 0, 0, 4, depth-2);
		
		int z = (int)Math.ceil( (float)depth / 2);
		for(int x = -width; x <= width; x ++)
		{
			placeBlock(x == 0 ? BlockListMF.reinforced_stone_framediron : BlockListMF.reinforced_stone, 0, x, height, z);
			if(x <-1 || x > 1)
			{
				if(getBlock(x, 1, z).getMaterial().isReplaceable())
				{
					Block block = (x==-2 || x==2) ? BlockListMF.reinforced_stone_framed : BlockListMF.reinforced_stone;
					placeBlock(block, 0, x, 1, z);
				}
			}
			for(int y = 2; y < height; y ++)
			{
				if(x <-1 || x > 1 || y > 3)
				{
					if(getBlock(x, y, z).getMaterial().isReplaceable())
					{
						placeBlock(BlockListMF.bars[0], 0, x, y, z);
					}
				}
			}
		}
		placeChest(-(width-2), 1, z-1, direction, LootTypes.DWARVEN_ARMOURY);
		placeChest(-(width-3), 1, z-1, direction, LootTypes.DWARVEN_ARMOURY);
		
		placeChest((width-2), 1, z-1, direction, LootTypes.DWARVEN_ARMOURY);
		placeChest((width-3), 1, z-1, direction, LootTypes.DWARVEN_ARMOURY);
		
		for(int z1 = 1; z1 < z; z1 ++)
		{
			for(int y = 1; y < height; y ++)
			{
				if(getBlock(-(width-1), y, z1).getMaterial().isReplaceable())
				{
					placeBlock(Blocks.stone_slab, 3, -(width-1), y, z1);
				}
				if(getBlock((width-1), y, z1).getMaterial().isReplaceable())
				{
					placeBlock(Blocks.stone_slab, 3, (width-1), y, z1);
				}
			}
		}
	}
	
	private void generateForge() 
	{
		boolean reverseForge = rand.nextBoolean();
		int width = getWidthSpan();
		int depth = getDepthSpan();
		int height = getHeight();
		int z = (int)Math.ceil( (float)depth / 2);
		
		int position = reverseForge ? -1 : 1;
		
		placeBlock(BlockListMF.chimney_stone_extractor, 0, (width*position), 2, z);
		placeBlock(BlockListMF.chimney_stone_extractor, 0, (width*position), 2, z-1);
		placeBlock(BlockListMF.chimney_pipe, 0, (width*position), 3, z);
		placeBlock(BlockListMF.chimney_pipe, 0, (width*position), 3, z-1);
		placeBlock(BlockListMF.chimney_pipe, 0, (width*position), 3, z+1);
		placeBlock(BlockListMF.chimney_pipe, 0, (width*position), 3, z+2);
		placeBlock(BlockListMF.chimney_pipe, 0, ( (width-1)*position), 3, z+2);
		placeBlock(BlockListMF.chimney_pipe, 0, ( (width-1)*position), 4, z+2);
		placeBlock(BlockListMF.chimney_pipe, 0, ( (width-1)*position), 5, z+2);
		placeBlock(BlockListMF.chimney_stone, 0, ( (width-1)*position), 6, z+2);
		
		int chestFacing = position < 0 ? rotateLeft() : rotateRight();
		placeChest((-(width-2)*position), 1, z, chestFacing, LootTypes.DWARVEN_FORGE);
		placeChest((-(width-2)*position), 1, z-1, chestFacing, LootTypes.DWARVEN_FORGE);
		
		placeBlock(BlockListMF.forge, 0, ( (width-1)*position), 1, z);
		placeBlock(BlockListMF.forge, 0, ( (width-1)*position), 1, z-1);
		placeBlock(Blocks.air, 0, ( (width-1)*position), 2, z);
		placeBlock(Blocks.air, 0, ( (width-1)*position), 2, z-1);
		
		placeBlock(BlockListMF.reinforced_stone_framed, 0, ( (width-1)*position), 1, z-2);
		placeBlock(BlockListMF.reinforced_stone_framed, 0, ( (width-1)*position), 1, z+1);
		placeBlock(BlockListMF.reinforced_stone, 0, ( (width-1)*position), 2, z-2);
		placeBlock(BlockListMF.reinforced_stone, 0, ( (width-1)*position), 2, z+1);
		placeBlock(BlockListMF.reinforced_stone_framediron, 0, ( (width-1)*position), 3, z-2);
		placeBlock(BlockListMF.reinforced_stone_framediron, 0, ( (width-1)*position), 3, z+1);
		
		placeBlock(BlockListMF.anvil[1], rotateLeft(), position*2, 1, z);
		placeBlock(Blocks.cauldron, 0, position*2, 1, z-1);
		
		placeMiscMachine1(-(width-2)*position, 0, depth-3);
		placeMiscMachine1(-(width-2)*position, 0, 3);
	}
	
	private void placeMiscMachine1(int x, int y, int z)
	{
		placeBlock(Blocks.lava, 0, x, y, z);
		placeBlock(BlockListMF.reinforced_stone_framed, 0, x-1, y+1, z-1);
		placeBlock(BlockListMF.reinforced_stone_framed, 0, x-1, y+1, z+1);
		placeBlock(BlockListMF.reinforced_stone_framed, 0, x+1, y+1, z-1);
		placeBlock(BlockListMF.reinforced_stone_framed, 0, x+1, y+1, z+1);
		placeBlock(BlockListMF.reinforced_stone, 0, x-1, y+2, z-1);
		placeBlock(BlockListMF.reinforced_stone, 0, x-1, y+2, z+1);
		placeBlock(BlockListMF.reinforced_stone, 0, x+1, y+2, z-1);
		placeBlock(BlockListMF.reinforced_stone, 0, x+1, y+2, z+1);
		placeBlock(BlockListMF.reinforced_stone, 0, x, y+2, z);
		placeBlock(BlockListMF.reinforced_stone, 0, x, y+3, z);
		placeBlock(BlockListMF.reinforced_stone_framediron, 0, x+1, y+2, z);
		placeBlock(BlockListMF.reinforced_stone_framediron, 0, x-1, y+2, z);
		placeBlock(BlockListMF.reinforced_stone_framediron, 0, x, y+2, z+1);
		placeBlock(BlockListMF.reinforced_stone_framediron, 0, x, y+2, z-1);
		
	}
	private void placeChest(int x, int y, int z, int d, String loot)
	{
		placeBlock(Blocks.chest, d, x, y, z);
        TileEntityChest tile = (TileEntityChest)getTileEntity(x, y, z, direction);

        if (tile != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(loot, rand), tile, 5+rand.nextInt(5));
            if(rand.nextInt(5) == 0)
            {
	            int slot = rand.nextInt(tile.getSizeInventory());
	            tile.setInventorySlotContents(slot, ChestGenHooks.getOneItem(ItemArtefact.DWARVEN, rand));
            }
        }
	}
	protected void tryPlaceMinorRoom(int x, int y, int z, int d) 
	{
		Class extension = StructureGenDSRoomSml2.class;
		if(extension != null)
		{
			mapStructure(x, y, z, d, extension);
		}
	}
}
