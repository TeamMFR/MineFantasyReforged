package minefantasy.mfr.tile;

import minefantasy.mfr.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBombPress extends TileEntityBase implements ITickable {
    public float animation = 0F;

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

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
        if (animation <= 0 && under instanceof TileEntityBombBench) {
            ((TileEntityBombBench) under).tryCraft(user, true);
            animation = 1.0F;
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.AMBIENT, 1.0F, 0.75F, false);
        }
    }

    @Override
    protected ItemStackHandler createInventory() {
        return null;
    }

    @Override
    public ItemStackHandler getInventory() {
        return null;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return null;
    }

    @Override
    protected int getGuiId() {
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return false;
    }
}
