package minefantasy.mfr.api.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISalvageDrop {
	boolean canSalvage(EntityPlayer user, ItemStack item);

}
