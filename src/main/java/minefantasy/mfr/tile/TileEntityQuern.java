package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.refine.QuernRecipes;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerQuern;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.network.NetworkHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityQuern extends TileEntityBase implements ITickable {
    private int postUseTicks;
    public int turnAngle;

    public static int getMaxRevs() {
        return 100;
    }

    public final ItemStackHandler inventory = createInventory();

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(3);
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return new ContainerQuern(player.inventory,this);
    }

    @Override
    protected int getGuiId() {
        return NetworkHandler.GUI_QUERN;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public void update() {
        int max = getMaxRevs();
        int levels = max / 4;

        if (postUseTicks > 0) {
            --postUseTicks;
        }
        if (postUseTicks > 0 || !((turnAngle == levels || turnAngle == levels * 2 || turnAngle == levels * 3 || turnAngle == 0))) {
            this.turnAngle++;
            if (!world.isRemote && (turnAngle == levels || turnAngle == levels * 2 || turnAngle == levels * 3 || turnAngle == max)) {
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), MineFantasySounds.QUERN, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
                onRevolutionComplete();
            }
            if (turnAngle >= max) {
                turnAngle = 0;
            }
        }
    }



    public static boolean isInput(ItemStack input) {
        return getResult(input) != null;
    }

    private static QuernRecipes getResult(ItemStack input) {
        return QuernRecipes.getResult(input);
    }

    public static boolean isPot(ItemStack item) {
        return item != null && item.getItem() == ComponentListMFR.CLAY_POT;
    }

    public void onUse() {
        if (postUseTicks == 0) {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), MineFantasySounds.QUERN, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
        }
        this.postUseTicks = 10;
    }

    public void onRevolutionComplete() {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), MineFantasySounds.CRAFT_PRIMITIVE, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
        QuernRecipes result = getResult(getInventory().getStackInSlot(0));
        if (result != null && (!result.consumePot || !getInventory().getStackInSlot(1).isEmpty()) && result.tier <= getTier()) {
            ItemStack craft = result.result;
            if (canFitResult(craft)) {
                if (!world.isRemote) {
                    tryCraft(craft, result.consumePot);
                } else {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5F, pos.getY() + 1F, pos.getZ() + 0.5F, 0F, 0.2F, 0F);
                }
            }
        }
    }

    private int getTier() {
        return 0;
    }

    private boolean canFitResult(ItemStack result) {
        ItemStack out = getInventory().getStackInSlot(2);
        if (out.isEmpty())
            return true;

        if (!out.isItemEqual(result))
            return false;
        return out.getCount() + result.getCount() <= result.getMaxStackSize();
    }

    private void tryCraft(ItemStack result, boolean consumePot) {
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), MineFantasySounds.CRAFT_PRIMITIVE, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
        /*
         * if(rand.nextFloat() > 0.20F)//20% success rate {
         * worldObj.playSoundEffect(xCoord, yCoord, zCoord, "dig.gravel", 1.0F, 0.5F);
         * return false; } else
         */

        this.getInventory().extractItem(0, 1, false);
        if (consumePot) {
            this.getInventory().extractItem(1, 1, false);
        }
        ItemStack out = getInventory().getStackInSlot(2);
        if (out.isEmpty()) {
            this.getInventory().setStackInSlot(2, result.copy());
        } else {
            out.grow(result.getCount());
        }

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (item != null && getResult(item) != null) {
            return slot == 0;
        }
        if (item != null && item.getItem() == ComponentListMFR.CLAY_POT) {
            return slot == 1;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("inventory", inventory.serializeNBT());
        return nbt;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }
}