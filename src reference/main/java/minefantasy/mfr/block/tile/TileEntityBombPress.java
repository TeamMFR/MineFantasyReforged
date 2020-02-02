package minefantasy.mfr.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityBombPress extends TileEntity implements ITickable {
    public float animation = 0F;

    @Override
    public void update() {
        if (world.isBlockPowered(pos)) {
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
        TileEntity under = world.getTileEntity(pos.add(0,-1,0));
        if (animation <= 0 && under != null && under instanceof TileEntityBombBench) {
            ((TileEntityBombBench) under).tryCraft(user, true);
            animation = 1.0F;
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.AMBIENT, 1.0F, 0.75F, false);
        }
    }
}
