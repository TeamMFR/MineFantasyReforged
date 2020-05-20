package minefantasy.mfr.item.gadget;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSpyglass extends Item implements IScope {

    public ItemSpyglass() {
        setRegistryName("spyglass");
        setUnlocalizedName("spyglass");

        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (user.isSneaking()) {
            world.playSound(user, user.getPosition(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.AMBIENT, 1.0F, 1.0F);
            toggleMode(item);
            user.swingArm(EnumHand.MAIN_HAND);
        } else {
            user.swingArm(user.swingingHand);
        }
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }

    private void toggleMode(ItemStack item) {
        int dam = item.getItemDamage();
        item.setItemDamage(dam == 2 ? 0 : dam + 1);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getZoom(ItemStack item) {
        int id = item.getItemDamage();
        return 0.5F + (0.25F * id);
    }
}
