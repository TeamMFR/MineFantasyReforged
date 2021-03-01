package minefantasy.mfr.item;

import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.mechanics.EventManagerMFRToRemove;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBandage extends ItemBaseMFR {
    private static final String healingID = "MF_Bandage_progress";
    private String name;
    private float healPwr;
    private float secondsToUse = 5F;

    public ItemBandage(String name, float healAmount) {
        super(name);
        this.name = name;
        healPwr = healAmount;
        setMaxStackSize(16);
        setCreativeTab(MineFantasyTabs.tabGadget);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        if (healPwr <= 5) {
            return ToolListMFR.POOR;
        }
        return super.getRarity(item);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed.
     * Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (!user.isSneaking() && canHeal(user) && user.hurtResistantTime <= 0) {
            int time = this.getMaxItemUseDuration(item);
            if (ResearchLogic.hasInfoUnlocked(user, "firstaid")) {
                time /= 3;
            }
            user.setActiveHand(hand);
        }
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }

    /**
     * returns the action that specifies what animation to play when the items is
     * being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return (int) (20F * secondsToUse);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack item, World world, EntityLivingBase player) {
        return heal((EntityPlayer) player, player, item);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        super.onUsingTick(stack, player, count);

        if (!canHeal(player) && !player.world.isRemote) {
            player.stopActiveHand();
        }
        if (count % 5 == 0) {
            player.playSound(SoundEvents.BLOCK_CLOTH_BREAK, 1F, 0.005F);
        }
        player.swingArm(player.getActiveHand());
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if (player.isSneaking() && isReadyToHeal(player, entity)) {
            heal(player, entity, itemstack);
            return true;
        }
        return super.itemInteractionForEntity(itemstack, player, entity, hand);
    }

    private ItemStack heal(EntityPlayer player, EntityLivingBase toHeal, ItemStack item) {
        if (player.world.isRemote) {
            return item;
        }

        if (toHeal != null) {
            if (canHeal(toHeal)) {
                toHeal.world.playSound(player, player.getPosition(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.AMBIENT, 1.0F, 3.0F);
                player.swingArm(player.getActiveHand());

                if (!player.world.isRemote) {
                    float power = healPwr;
                    if (ResearchLogic.hasInfoUnlocked(player, "doctor")) {
                        power *= 1.5F;
                    }
                    toHeal.heal(power);

                    if (!player.capabilities.isCreativeMode) {
                        item.shrink(1);
                        if (item.getCount() <= 0) {

                        }
                    }
                }
                toHeal.getEntityData().setInteger(EventManagerMFRToRemove.injuredNBT, 0);
            }
        }
        return item;
    }

    private boolean canHeal(EntityLivingBase toHeal) {
        return toHeal.hurtResistantTime <= 0
                && (toHeal.getHealth() <= (toHeal.getMaxHealth() - 1F) || EventManagerMFRToRemove.getInjuredTime(toHeal) > 0)
                && !toHeal.isBurning();
    }

    public boolean isReadyToHeal(EntityPlayer player, EntityLivingBase patient) {
        int time = getUserHealTime(patient) + 1;
        if (ResearchLogic.hasInfoUnlocked(player, "firstaid")) {
            time += 3;
        }
        patient.playSound(SoundEvents.BLOCK_CLOTH_BREAK, 1F, 0.005F);
        player.swingArm(player.getActiveHand());

        if (time >= (int) (4 * secondsToUse)) {
            setUserHealTime(patient, 0);
            return true;
        }

        setUserHealTime(patient, time);
        return false;
    }

    public void setUserHealTime(EntityLivingBase user, int value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setInteger(healingID, value);
        }
    }

    public int getUserHealTime(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            if (user.getEntityData().hasKey(healingID)) {
                return user.getEntityData().getInteger(healingID);
            }
        }
        return 0;
    }
}
