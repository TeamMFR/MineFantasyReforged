package minefantasy.mfr.api.mining;

import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

public class RandomOre {
	public static ArrayList<RandomOre> drops = new ArrayList<>();

	private final ItemStack loot;
	private final float chanceToDrop;
	private final Block block;
	private final int harvestLvl;
	private final int minHeight;
	private final int maxHeight;
	private final boolean doesSilktouchDisable;
	private final String research;

	public RandomOre(ItemStack drop, float chance, Block base, int harvestLevel, int min, int max, boolean silkDisable) {
		this(drop, chance, base, harvestLevel, min, max, silkDisable, null);
	}

	public RandomOre(ItemStack drop, float chance, Block base, int harvestLevel, int min, int max, boolean silkDisable, String research) {
		doesSilktouchDisable = silkDisable;
		minHeight = min;
		maxHeight = max;
		loot = drop;
		chanceToDrop = chance;
		harvestLvl = harvestLevel;

		block = base;
		this.research = research;
	}

	public static void addOre(ItemStack drop, float chance, String oreDict, int harvestLevel, int min, int max, boolean silkDisable) {
		for (ItemStack stack : OreDictionary.getOres(oreDict)) {
			Block block = Block.getBlockFromItem(stack.getItem());
			if (block != Blocks.AIR) {
				drops.add(new RandomOre(drop, chance / 100F, block, harvestLevel, min, max, silkDisable));
			}
		}
	}

	public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max, boolean silkDisable) {
		drops.add(new RandomOre(drop, chance / 100F, block, harvestLevel, min, max, silkDisable));
	}

	public static void addOre(ItemStack drop, float chance, String oreDict, int harvestLevel, int min, int max,
			boolean silkDisable, String research) {
		for (ItemStack stack : OreDictionary.getOres(oreDict)) {
			Block block = Block.getBlockFromItem(stack.getItem());
			if (block != Blocks.AIR) {
				drops.add(new RandomOre(drop, chance / 100F, block, harvestLevel, min, max, silkDisable, research));
			}
		}
	}

	public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max,
			boolean silkDisable, String research) {
		drops.add(new RandomOre(drop, chance / 100F, block, harvestLevel, min, max,
				silkDisable, research));
	}

	public static ArrayList<ItemStack> getDroppedItems(EntityLivingBase user, Block base, int harvest, int fortune, boolean silktouch, int y) {
		ArrayList<ItemStack> loot = new ArrayList<>();

		if (!drops.isEmpty()) {

			for (RandomOre ore : drops) {
				if (matchesOre(user, ore, base, harvest, fortune / 2F + 1F, silktouch, y)) {
					loot.add(ore.loot);
				}
			}
		}

		return loot;
	}

	private static boolean matchesOre(EntityLivingBase user, RandomOre ore, Block base, int harvest, float multiplier, boolean silktouch, int y) {
		Random random = new Random();

		if (ore.doesSilktouchDisable && silktouch) {
			return false;
		}
		if (user instanceof EntityPlayer && ore.research != null) {
			if (!ResearchLogic.hasInfoUnlocked((EntityPlayer) user, ore.research)) {
				return false;
			}
		}

		if (!(ore.minHeight == -1 && ore.maxHeight == -1)) {
			if (y < ore.minHeight || y > ore.maxHeight) {
				return false;
			}
		}
		if (ore.block != base) {
			return false;
		}
		if (ore.harvestLvl > harvest) {
			return false;
		}
		return random.nextFloat() < (ore.chanceToDrop * multiplier);
	}
}
