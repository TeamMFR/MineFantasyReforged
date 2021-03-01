package minefantasy.mfr.api.crafting;

import net.minecraft.tileentity.TileEntity;

public interface IHeatUser {
    boolean canAccept(TileEntity tile);
}
