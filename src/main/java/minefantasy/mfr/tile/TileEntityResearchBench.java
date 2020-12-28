package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.knowledge.IArtefact;
import minefantasy.mfr.api.knowledge.InformationBase;
import minefantasy.mfr.api.knowledge.ResearchArtefacts;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerResearchBench;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.ResearchTablePacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TileEntityResearchBench extends TileEntityBase implements IBasicMetre {
    public float progress;
    public float maxProgress;
    public int researchID = -1;
    private Random rand = new Random();
    private int ticksExisted;

    public final ItemStackHandler inventory = createInventory();

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(1);
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return new ContainerResearchBench(player,this);
    }

    @Override
    protected int getGuiId() {
        return NetworkHandler.GUI_RESEARCH_BENCH;
    }

    public static ArrayList<String> getInfo(ItemStack item) {
        if (item.isEmpty()) {
            return null;
        }

        return ResearchArtefacts.getResearchNames(item);
    }

    public static boolean canAccept(ItemStack item) {
        ArrayList<String> info = getInfo(item);
        return info != null && info.size() > 0;
    }

    public boolean interact(EntityPlayer user) {
        if (world.isRemote) {
            return true;
        }
        ArrayList<String> research = getInfo(getInventory().getStackInSlot(0));
        int result = canResearch(user, research);

        if (research != null && research.size() > 0 && result != 0) {
            if (result == -1) {
                if (!user.world.isRemote)
                    user.sendMessage(new TextComponentString(I18n.format("research.noskill")));
                return true;
            }
            maxProgress = getMaxTime();
            if (maxProgress > 0) {
                addProgress(user);

                if (progress >= maxProgress) {
                    addResearch(research, user);
                    progress = 0;
                }

                return true;
            }
        } else {
            if (result == 0) {
                if (!user.world.isRemote)
                    user.sendMessage(new TextComponentString(I18n.format("research.noskill")));
            }
            progress = 0;
        }

        return !getInventory().getStackInSlot(0).isEmpty();
    }

    private void addResearch(ArrayList<String> research, EntityPlayer user) {
        for (String s : research) {
            InformationBase base = ResearchLogic.getResearch(s);
            if (base != null && !ResearchLogic.alreadyUsedArtefact(user, base, getInventory().getStackInSlot(0))
                    && ResearchLogic.canPurchase(user, base) && base.hasSkillsUnlocked(user)) {
                int artefacts = ResearchArtefacts.useArtefact(getInventory().getStackInSlot(0), base, user);
                world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, MineFantasySounds.UPDATE_RESEARCH, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
                if (!user.world.isRemote) {
                    String name = I18n.format("knowledge." + s);
                    if (artefacts == -1) {
                        user.sendMessage(new TextComponentString(I18n.format("research.finishResearch",name)));
                    } else {
                        user.sendMessage(new TextComponentString(I18n.format("research.addArtefact", name, artefacts, base.getArtefactCount())));
                    }
                }
                return;
            }

        }
    }

    // 0 nothing, -1 for no skill, 1 for yes
    private int canResearch(EntityPlayer user, ArrayList<String> research) {
        if (research == null) {
            return 0;
        }
        int result = 0;

        for (String s : research) {
            InformationBase base = ResearchLogic.getResearch(s);
            if (base != null && !ResearchLogic.alreadyUsedArtefact(user, base, getInventory().getStackInSlot(0))) {
                if (ResearchLogic.canPurchase(user, base) && base.hasSkillsUnlocked(user)) {
                    return 1;
                } else if (!ResearchLogic.hasInfoUnlocked(user, base)) {
                    result = -1;
                }
            }
        }
        return result;
    }

    private float getMaxTime() {
        int t = 10;
        if (!getInventory().getStackInSlot(0).isEmpty() && getInventory().getStackInSlot(0).getItem() instanceof IArtefact) {
            return ((IArtefact) getInventory().getStackInSlot(0).getItem()).getStudyTime(getInventory().getStackInSlot(0));
        }
        return getInfo(getInventory().getStackInSlot(0)) != null ? t : 0;
    }

    private void addProgress(EntityPlayer user) {
        ItemStack held = user.getHeldItemMainhand();
        if (!held.isEmpty() && (held.getItem() == ComponentListMFR.TALISMAN_LESSER || held.getItem() == ComponentListMFR.TALISMAN_GREATER)) {
            progress = maxProgress;
            if (!user.capabilities.isCreativeMode && held.getItem() == ComponentListMFR.TALISMAN_LESSER) {
                held.shrink(1);
                if (held.getCount() <= 0) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
            return;
        }
        float efficiency = 1.0F;
        if (user.swingProgress > 0) {
            efficiency *= Math.max(0F, 1.0F - user.swingProgress);
        }
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, MineFantasySounds.FLIP_PAGE, SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() * 0.4F + 0.8F, true);
        efficiency *= getEnvironmentBoost();
        progress += efficiency;
    }

    private float getEnvironmentBoost() {
        int books = 0;
        for (int x = -8; x <= 8; x++) {
            for (int y = -8; y <= 8; y++) {
                for (int z = -8; z <= 8; z++) {
                    if (world.getBlockState(pos.add(x,y,z)).getBlock() == Blocks.BOOKSHELF) {
                        ++books;
                    }
                }
            }
        }
        return 1.0F + (0.1F * books);
    }

    @Override
    public void markDirty() {
        syncData();
        sendUpdates();
    }

    public void syncData() {
        if (world.isRemote)
            return;
        NetworkHandler.sendToAllTrackingChunk (world, pos.getX() >> 4, pos.getZ() >> 4, new ResearchTablePacket(this));
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return canAccept(item);
    }

    @Override
    public int getMetreScale(int size) {
        if (maxProgress <= 0) {
            return 0;
        }
        return (int) Math.min(size, Math.ceil(size / maxProgress * progress));
    }

    @Override
    public boolean shouldShowMetre() {
        return maxProgress > 0;
    }

    @Override
    public String getLocalisedName() {
        return "";
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        ticksExisted = nbt.getInteger("ticksExisted");
        researchID = nbt.getInteger("researchID");
        progress = nbt.getFloat("progress");
        maxProgress = nbt.getFloat("maxProgress");

        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("researchID", researchID);
        nbt.setInteger("ticksExisted", ticksExisted);
        nbt.setFloat("progress", progress);
        nbt.setFloat("maxProgress", maxProgress);

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
