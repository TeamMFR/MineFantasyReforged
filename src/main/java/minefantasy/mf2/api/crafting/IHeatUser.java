package minefantasy.mf2.api.crafting;

import net.minecraft.tileentity.TileEntity;

public interface IHeatUser {
    public boolean canAccept(TileEntity tile);
}
