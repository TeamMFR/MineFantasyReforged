package minefantasy.mfr.api.crafting;

import net.minecraft.tileentity.TileEntity;

public interface IHeatUser {
    public boolean canAccept(TileEntity tile);
}
