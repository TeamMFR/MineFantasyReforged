package minefantasy.mfr.mechanics.knowledge;

import net.minecraft.item.ItemStack;

public interface IArtefact {

	String[] getResearches();

	int getStudyTime(ItemStack item);
}
