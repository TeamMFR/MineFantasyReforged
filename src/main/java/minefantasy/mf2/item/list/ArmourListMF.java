package minefantasy.mf2.item.list;

import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.ArmourMaterialMF;
import minefantasy.mf2.api.crafting.exotic.SpecialForging;
import minefantasy.mf2.item.armour.ItemApron;
import minefantasy.mf2.item.armour.ItemArmourMF;
import minefantasy.mf2.item.armour.ItemCogworkArmour;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 */
public class ArmourListMF
{
	public static ArmourMaterialMF LEATHER = new ArmourMaterialMF("Leather",         5,   0.30F,  18,  1.00F);
	public static ArmourMaterialMF APRON = new ArmourMaterialMF("Apron",             6,   0.30F,  0,   1.00F);
	
	public static final String[] leathermats = new String[]
	{
		"hide",
		"roughleather",
		"strongleather",
		"studleather",
		"scaleleather",
		"padded",
	};
	
	public static final String[] mats = new String[]
	{
		"copper",
		"bronze",
		"iron",
		"steel",
		"encrusted",
		"blacksteel",
		"dragonforge",
		"redsteel",
		"bluesteel",
		"adamantium",
		"mithril",
		"ignotumite",
		"mithium",
		"ender",
	};
	
	public static ItemArmourMF[] leather = new ItemArmourMF[leathermats.length*4];
	public static ItemArmourMF leatherapron, cogwork_frame_helmet, cogwork_frame_chest, cogwork_frame_legs, cogwork_frame_boots, cogwork_armour_helmet, cogwork_armour_chest, cogwork_armour_legs, cogwork_armour_boots;//, cogwork_dwarf_armour_helmet, cogwork_dwarf_armour_chest, cogwork_dwarf_armour_legs, cogwork_dwarf_armour_boots;
	//public static ItemArmourMF[] chainmail = new ItemArmourMF[mats.length*4];
	//public static ItemArmourMF[] fieldplate = new ItemArmourMF[mats.length*4 - 4];
	
	
	public static void load() 
	{
		leatherapron = new ItemApron("leatherapron", BaseMaterialMF.leatherapron, "leatherapron_layer_1", 0);
		
		cogwork_frame_helmet = new ItemCogworkArmour("cogwork_frame_helmet", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 0, "cogwork_frame_layer_1", 2).setAsFrame();
		cogwork_frame_chest = new ItemCogworkArmour("cogwork_frame_chest", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 1, "cogwork_frame_layer_1", 2).setAsFrame();
		cogwork_frame_legs = new ItemCogworkArmour("cogwork_frame_legs", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 2, "cogwork_frame_layer_2", 2).setAsFrame();
		cogwork_frame_boots = new ItemCogworkArmour("cogwork_frame_boots", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 3, "cogwork_frame_layer_1", 2).setAsFrame();
		
		cogwork_armour_helmet = (ItemArmourMF) new ItemCogworkArmour("cogwork_armour_helmet", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 0, "cogwork_armour_layer_1", 2);
		cogwork_armour_chest = (ItemArmourMF) new ItemCogworkArmour("cogwork_armour_chest", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 1, "cogwork_armour_layer_1", 2);
		cogwork_armour_legs = (ItemArmourMF) new ItemCogworkArmour("cogwork_armour_legs", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 2, "cogwork_armour_layer_2", 2);
		cogwork_armour_boots = (ItemArmourMF) new ItemCogworkArmour("cogwork_armour_boots", ArmourDesign.COGWORK, BaseMaterialMF.cogworks, 3, "cogwork_armour_layer_1", 2);
		
		//cogwork_dwarf_armour_helmet = (ItemArmourMF) new ItemCogworkArmour("cogwork_dwarf_armour_helmet", ArmourDesign.DWARFCOG, BaseMaterialMF.cogworks, 0, "cogwork_dwarf_armour_layer_1", 2).setContainerItem(cogwork_frame_helmet);
		//cogwork_dwarf_armour_chest = (ItemArmourMF) new ItemCogworkArmour("cogwork_dwarf_armour_chest", ArmourDesign.DWARFCOG, BaseMaterialMF.cogworks, 1, "cogwork_dwarf_armour_layer_1", 2).setFuelCost(2).setContainerItem(cogwork_frame_chest);
		//cogwork_dwarf_armour_legs = (ItemArmourMF) new ItemCogworkArmour("cogwork_dwarf_armour_legs", ArmourDesign.DWARFCOG, BaseMaterialMF.cogworks, 2, "cogwork_dwarf_armour_layer_2", 2).setContainerItem(cogwork_frame_legs);
		//cogwork_dwarf_armour_boots = (ItemArmourMF) new ItemCogworkArmour("cogwork_dwarf_armour_boots", ArmourDesign.DWARFCOG, BaseMaterialMF.cogworks, 3, "cogwork_dwarf_armour_layer_1", 2).setContainerItem(cogwork_frame_boots);
		
		for(int a = 0; a < leathermats.length; a ++)
		{
			BaseMaterialMF baseMat = BaseMaterialMF.getMaterial(leathermats[a]);
			String matName = baseMat.name;
			int rarity = baseMat.rarity;
			int id = a*4;
			float bulk = baseMat.weight;
			ArmourDesign design = baseMat == BaseMaterialMF.padding ? ArmourDesign.PADDING : ArmourDesign.LEATHER;
			
			leather[id+0] = new ItemArmourMF(matName.toLowerCase()+"_helmet", baseMat, design, 0, matName.toLowerCase()+"_layer_1", rarity, bulk);
			leather[id+1] = new ItemArmourMF(matName.toLowerCase()+"_chest", baseMat, design, 1, matName.toLowerCase()+"_layer_1", rarity, bulk);
			leather[id+2] = new ItemArmourMF(matName.toLowerCase()+"_legs", baseMat, design, 2, matName.toLowerCase()+"_layer_2", rarity, bulk);
			leather[id+3] = new ItemArmourMF(matName.toLowerCase()+"_boots", baseMat, design, 3, matName.toLowerCase()+"_layer_1", rarity, bulk);
		}
	}
	
	public static ItemStack armour(ItemArmourMF[] pool, int tier, int piece)
	{
		int slot = tier*4+piece;
		if(slot >= pool.length)
		{
			slot = pool.length-1;
		}
		return new ItemStack(pool[slot]);
	}
	public static Item armourItem(ItemArmourMF[] pool, int tier, int piece)
	{
		int slot = tier*4+piece;
		if(slot >= pool.length)
		{
			slot = pool.length-1;
		}
		return pool[slot];
	}
	
	
	public static boolean isUnbreakable(BaseMaterialMF material, EntityLivingBase user) 
	{
		if(material == BaseMaterialMF.enderforge || material == BaseMaterialMF.ignotumite || material == BaseMaterialMF.mithium)
		{
			return true;
		}
		return false;
	}
}
