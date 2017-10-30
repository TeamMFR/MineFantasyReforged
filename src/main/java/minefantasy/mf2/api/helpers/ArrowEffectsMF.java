package minefantasy.mf2.api.helpers;

import minefantasy.mf2.api.archery.IArrowRetrieve;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class ArrowEffectsMF {
    // THE ARROW STICKING CODE IS EXPERIMENTAL AND NOT ACTIVE BY DEFAULT

    /**
     * Sticks an arrow into an entity THIS WILL ONLY BE DROPPED ON LIVING ENTITIES.
     * (This does not render arrows, simply drops them when an enemy is killed)
     *
     * @param hit        the entity the arrow hit
     * @param projectile the arrow entity, it will get the item to drop
     */
    public static void stickArrowIn(Entity hit, Entity projectile) {
        stickArrowIn(hit, getDroppedArrow(projectile), projectile);
    }

    /**
     * Sticks an arrow into an entity THIS WILL ONLY BE DROPPED ON LIVING ENTITIES.
     * (This does not render arrows, simply drops them when an enemy is killed)
     *
     * @param hit        the entity the arrow hit
     * @param arrow      the item that gets dropped when "hit" is killed
     * @param projectile the arrow entity
     */
    public static void stickArrowIn(Entity hit, ItemStack arrow, Entity projectile) {
        if (!shouldArrowStick(projectile)) {
            return;
        }
        NBTTagCompound nbt = hit.getEntityData();

        int slot = 0;
        if (nbt.hasKey("stuckArrowCountMF")) {
            slot = hit.getEntityData().getInteger("stuckArrowCountMF");
        }

        NBTTagCompound stuckArrow = new NBTTagCompound();// makes the arrow instance
        arrow.writeToNBT(stuckArrow);// put arrow in instance

        nbt.setTag("StuckArrowMF" + slot, stuckArrow);// save arrow list
        nbt.setInteger("stuckArrowCountMF", slot + 1);// Slot increases
    }

    /**
     * Gets the arrow dropped when an enemy is killed. MODS NEED TO ADD THEIR OWN
     * SUPPORT
     *
     * @param arrow the arrow fired
     */
    public static ItemStack getDroppedArrow(Entity arrow) {
        if (arrow instanceof IArrowRetrieve)// implement this method in your entity arrow classes
        {
            return ((IArrowRetrieve) arrow).getDroppedItem();
        }
        if (arrow.getEntityData().hasKey("MF_ArrowItem")) {
            NBTTagCompound heldArrow = arrow.getEntityData().getCompoundTag("MF_ArrowItem");
            return ItemStack.loadItemStackFromNBT(heldArrow);
        }
        return new ItemStack(Items.arrow);
    }

    /**
     * Gets if an arrow entity will drop items when the enemy is killed.
     *
     * @param projectile the arrow entity fired
     */
    public static boolean shouldArrowStick(Entity projectile) {
        if (projectile instanceof IArrowRetrieve) {
            return ((IArrowRetrieve) projectile).canBePickedUp();
        }
        if (projectile instanceof EntityArrow) {
            return ((EntityArrow) projectile).canBePickedUp == 1;
        }
        return true;
    }

    /**
     * Gets the list of items that arrows have stuck in from the "stickArrowIn"
     * method; THIS IS ONLY CALLED BY LIVING ENTITIES
     *
     * @param entity the entity killed
     */
    public static List<ItemStack> getStuckArrows(Entity entity) {
        List<ItemStack> arrows = new ArrayList<ItemStack>();
        NBTTagCompound nbt = entity.getEntityData();

        int arrowCount = 0;
        if (nbt.hasKey("stuckArrowCountMF")) {
            arrowCount = entity.getEntityData().getInteger("stuckArrowCountMF");
        }

        if (arrowCount > 0) {
            for (int a = 0; a < arrowCount; a++) {
                NBTTagCompound stuckArrow = (NBTTagCompound) nbt.getTag("StuckArrowMF" + a);
                if (stuckArrow != null) {
                    arrows.add(ItemStack.loadItemStackFromNBT(stuckArrow));
                }
            }
        }
        return arrows;
    }
}
