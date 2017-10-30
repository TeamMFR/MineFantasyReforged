package minefantasy.mf2.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBombPress extends TileEntity {
    public float animation = 0F;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
            if (animation <= 0) {
                use(null);
            }
            animation = 1.0F;
        } else {
            if (animation > 0) {
                animation -= 0.05F;
            }
        }
    }

    public void use(EntityPlayer user) {
        TileEntity under = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        if (animation <= 0 && under != null && under instanceof TileEntityBombBench) {
            ((TileEntityBombBench) under).tryCraft(user, true);
            animation = 1.0F;
            worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "tile.piston.out", 1.0F, 0.75F);
        }
    }
}
