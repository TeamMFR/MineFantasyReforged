package minefantasy.mfr.block.tile.blastfurnace;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.block.refining.BlockBFH;
import minefantasy.mfr.block.tile.TileEntityCrucible;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.entity.EntityFireBlast;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class TileEntityBlastFH extends TileEntityBlastFC {
    public static float maxProgress = 1200;
    public static int maxFurnaceHeight = 8;
    public int fuel;
    public int maxFuel;
    public float heat;
    public float progress;

    public static boolean isFuel(ItemStack item) {
        return getItemBurnTime(item) > 0;
    }

    public static int getItemBurnTime(ItemStack fuel) {
        return MineFantasyRebornAPI.getFuelValue(fuel) / 4;
    }

    @Override
    public void update() {
        super.update();

        boolean wasBurning = isBurning();
        if (fuel > 0) {
            --fuel;
        }
        if (isBuilt && smokeStorage < getMaxSmokeStorage()) {
            if (fuel > 0) {
                ++progress;
                float maxProgress = getMaxProgress();
                if (progress >= maxProgress) {
                    progress -= maxProgress;
                    smeltItem();
                }
                if (ticksExisted % 10 == 0) {
                    smokeStorage += 2;
                }
            } else if (!world.isRemote) {
                if (isFuel(items[0])) {
                    fuel = maxFuel = getItemBurnTime(items[0]);
                    decrStackSize(0, 1);
                }
            }
        }
        if (fireTime > 0) {
            if (fuel > 0)
                --fuel;
            smokeStorage++;
            fireTime--;
            if (ticksExisted % 2 == 0)
                shootFire();

        }
        if (!world.isRemote && wasBurning != isBurning()) {
            BlockBFH.updateFurnaceBlockState(isBurning(), this.world, this.pos);
        }
    }

    private float getMaxProgress() {
        return maxProgress;
    }

    private void smeltItem() {
        for (int y = 0; y < maxFurnaceHeight; y++) {
            TileEntity tileEntity = world.getTileEntity(pos.add(0,y+1,0));
            if (tileEntity != null && tileEntity instanceof TileEntityBlastFC
                    && !(tileEntity instanceof TileEntityBlastFH)) {
                ItemStack result = getSmeltedResult((TileEntityBlastFC) tileEntity, y + 1);
                if (result != null) {
                    dropItem(result);
                }
            } else {
                break;
            }
        }
        fireTime = 20;
        world.playSound(pos.getX()+0.5, pos.getY() +0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.AMBIENT,
                2.0F, 0.5F, true);
        world.playSound(pos.getX()+0.5, pos.getY() +0.25, pos.getZ() + 0.5, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT,
                1.0F, 0.75F, true);
        startFire(1, 0, 0);
        startFire(-1, 0, 0);
        startFire(0, 0, 1);
        startFire(0, 0, -1);
    }

    private void dropItem(ItemStack result) {
        TileEntity under = world.getTileEntity(pos.add(0,-1,0));
        if (under != null && under instanceof TileEntityCrucible) {
            TileEntityCrucible crucible = (TileEntityCrucible) under;
            int slot = crucible.getSizeInventory() - 1;
            {
                if (crucible.getStackInSlot(slot) == null) {
                    crucible.setInventorySlotContents(slot, result);
                    return;
                } else if (CustomToolHelper.areEqual(crucible.getStackInSlot(slot), result)) {
                    int freeSpace = crucible.getStackInSlot(slot).getMaxStackSize()
                            - crucible.getStackInSlot(slot).getCount();
                    if (freeSpace >= result.getCount()) {
                        crucible.getStackInSlot(slot).grow(result.getCount());
                        return;
                    } else {
                        crucible.getStackInSlot(slot).grow(freeSpace);
                        result.shrink(freeSpace);
                    }
                }
            }
        }
        if (result.getCount() <= 0)
            return;

        if (ConfigHardcore.HCCreduceIngots && rand.nextInt(3) == 0) {
            EntityItem entity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, result);
            world.spawnEntity(entity);
        }
    }

    private void startFire(int x, int y, int z) {
        BlockPos firePos = new BlockPos(x,y,z);
        if (!world.isRemote && world.isAirBlock(firePos)) {
            world.setBlockState(pos.add(x,y,z), (Blocks.FIRE).getDefaultState());
        }
    }

    private void shootFire() {
        if (!world.isRemote) {
            shootFire(-1, 0, 0);
            shootFire(1, 0, 0);
            shootFire(0, 0, -1);
            shootFire(0, 0, 1);
        }
    }

    private void shootFire(int x, int y, int z) {
        double v = 0.125D;
        EntityFireBlast fireball = new EntityFireBlast(world, pos.getX() + 0.5 + x, pos.getY(), pos.getZ() + 0.5 + z, x * v,
                y * v, z * v);
        fireball.getEntityData().setString("Preset", "BlastFurnace");
        fireball.modifySpeed(0.5F);
        world.spawnEntity(fireball);
    }

    private ItemStack getSmeltedResult(TileEntityBlastFC shaft, int y) {
        if (shaft.getIsBuilt()) {
            ItemStack input = shaft.getStackInSlot(1);
            if (shaft.tempUses <= 0 && shaft.getStackInSlot(0) == null || !isCarbon(shaft.getStackInSlot(0))) {
                return null;
            }
            if (input != null) {
                ItemStack result = getResult(input);
                if (result != null) {
                    for (int a = 0; a < shaft.getSizeInventory(); a++) {
                        if (a > 0 || shaft.shouldRemoveCarbon()) {
                            shaft.decrStackSize(a, 1);
                        }
                    }
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    protected void interact(TileEntityBlastFC tile) {

    }

    @Override
    public void updateBuild() {
        isBuilt = getIsBuilt();
    }

    @Override
    protected boolean getIsBuilt() {
        return (isFirebrick(-1, 0, -1) && isFirebrick(1, 0, -1) && isFirebrick(-1, 0, 1) && isFirebrick(1, 0, -1))
                && (isAir(-1, 0, 0) && isAir(1, 0, 0) && isAir(0, 0, -1) && isAir(0, 0, 1));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setFloat("progress", progress);
        nbt.setInteger("fuel", fuel);
        nbt.setInteger("maxFuel", maxFuel);
		return nbt;
	}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        progress = nbt.getFloat("progress");
        fuel = nbt.getInteger("fuel");
        maxFuel = nbt.getInteger("maxFuel");
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side != EnumFacing.UP ? new int[]{0} : new int[]{};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, EnumFacing direction) {
        return direction != EnumFacing.UP && isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (slot == 0) {
            return isFuel(item);
        }
        return false;
    }

    public boolean isBurning() {
        return fuel > 0;
    }

    public int getBurnTimeRemainingScaled(int i) {
        if (this.maxFuel <= 0) {
            return 0;
        }

        return fuel * i / maxFuel;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public int getMaxSmokeStorage() {
        return 10;
    }
}
