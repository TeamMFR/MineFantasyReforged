package minefantasy.mfr.item;

import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.knowledge.IArtefact;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemJewel extends ItemBaseMFR implements IArtefact {
	public static final String MYTHIC = "mythic_artefacts";
	public static final String DWARVEN = "dwarven_artefacts";
	public static final String GNOMISH = "gnomish_artefacts";

	public final int studyTime;
	public final EnumRarity rarity;
	public final String[] researches;
	public final String lootType;
	public final int dropWeight;

	public ItemJewel(String name, EnumRarity rarity, String lootType, int dropChance) {
		this(name, 0, rarity, lootType, dropChance);
	}

	public ItemJewel(String name, int studyTime, EnumRarity rarity, String lootType, int dropChance, String... researches) {
		super(name);
		this.studyTime = studyTime;
		this.rarity = rarity;
		this.researches = researches;
		this.lootType = lootType;
		this.dropWeight = dropChance;

		this.setCreativeTab(MineFantasyTabs.tabMaterials);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return rarity;
	}

	@Override
	public int getStudyTime(ItemStack item) {
		return studyTime;
	}

	@Override
	public String[] getResearches() {
		return researches;
	}
}
