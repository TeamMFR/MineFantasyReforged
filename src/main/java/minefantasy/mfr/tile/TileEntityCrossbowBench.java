package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.engineer.ICrossbowPart;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerCrossbowBench;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.network.CrossbowBenchPacket;
import minefantasy.mfr.network.NetworkHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityCrossbowBench extends TileEntityBase implements IBasicMetre {
    public float progress;
    public float maxProgress = 25F;
    public boolean hasRecipe;
    /**
     * Stock, Head, Mod, Muzzle, Result
     */
    public final ItemStackHandler inventory = createInventory();

    private int ticksExisted;

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(5);
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return new ContainerCrossbowBench(player,this);
    }

    @Override
    protected int getGuiId() {
        return NetworkHandler.GUI_CROSSBOW_BENCH;
    }

    private static ICrossbowPart getCrossbowPart(ItemStack item) {
        if (!item.isEmpty() && item.getItem() instanceof ICrossbowPart) {
            return (ICrossbowPart) item.getItem();
        }
        return null;
    }

    public static boolean isMatch(ItemStack item, String var) {
        ICrossbowPart part = getCrossbowPart(item);
        if (part != null) {
            return part.getComponentType().equalsIgnoreCase(var);
        }
        return false;
    }

    @Override
    public void markDirty() {
        ++ticksExisted;
        if (!world.isRemote && (hasRecipe || ticksExisted % 100 == 0)) {
            syncData();
        }
    }

    public boolean tryCraft(EntityPlayer user) {
        ItemStack result = findResult();
        hasRecipe = !result.isEmpty();

        if (result.isEmpty()) {
            progress = 0F;
        } else if (ToolHelper.getCrafterTool(user.getHeldItemMainhand()).equalsIgnoreCase("spanner")) {
            if (!user.getHeldItemMainhand().isEmpty()) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, MineFantasySounds.TWIST_BOLT, SoundCategory.NEUTRAL, 0.25F, 1.0F, true);
                user.getHeldItemMainhand().damageItem(1, user);
                if (user.getHeldItemMainhand().getItemDamage() >= user.getHeldItemMainhand().getMaxDamage()) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
            float efficiency = ToolHelper.getCrafterEfficiency(user.getHeldItemMainhand());

            if (user.swingProgress > 0 && user.swingProgress <= 1.0) {
                efficiency *= (0.5F - user.swingProgress);
            }

            if (!world.isRemote) {
                progress += efficiency;
            }
            if ((progress >= maxProgress && craftItem(result))) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.AMBIENT, 0.35F, 0.5F, false);
                progress = 0;
                if (user != null) {
                    SkillList.engineering.addXP(user, 10);
                }
                for (int a = 0; a < 4; a++) {
                    inventory.extractItem(a, 1, false);
                }
            }
            return true;
        }
        return false;
    }

    private boolean craftItem(ItemStack result) {
        if (inventory.getStackInSlot(4).isEmpty()) {
            this.inventory.setStackInSlot(4, result);
            return true;
        } else {
            return false;
        }
    }

    public void syncData() {
        if (world.isRemote)
            return;
        NetworkHandler.sendToAllTrackingChunk (world, pos.getX() >> 4, pos.getZ() >> 4, new CrossbowBenchPacket(this));
    }

    private ItemStack findResult() {
        ICrossbowPart stock = getCrossbowPart(inventory.getStackInSlot(0));
        ICrossbowPart head = getCrossbowPart(inventory.getStackInSlot(1));
        ICrossbowPart mod = getCrossbowPart(inventory.getStackInSlot(2));
        ICrossbowPart muzzle = getCrossbowPart(inventory.getStackInSlot(3));

        if (stock == null || head == null)
            return ItemStack.EMPTY;

        return ToolListMFR.CROSSBOW_CUSTOM.constructCrossbow(stock, head, mod, muzzle);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (isMatch(item, "stock")) {
            return slot == 0;
        }
        if (isMatch(item, "mechanism")) {
            return slot == 1;
        }
        if (isMatch(item, "mod")) {
            return slot == 2;
        }
        if (isMatch(item, "muzzle")) {
            return slot == 3;
        }
        return false;
    }

    @Override
    public int getMetreScale(int size) {
        return (int) Math.min(size, size / maxProgress * progress);
    }

    @Override
    public boolean shouldShowMetre() {
        return true;
    }

    @Override
    public String getLocalisedName() {
        return I18n.format("tile.crossbowBench.name");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));

        progress = nbt.getFloat("progress");
        maxProgress = nbt.getFloat("maxProgress");
        hasRecipe = nbt.getBoolean("hasRecipe");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setTag("inventory", inventory.serializeNBT());

        nbt.setFloat("progress", progress);
        nbt.setFloat("maxProgress", maxProgress);
        nbt.setBoolean("hasRecipe", hasRecipe);
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
