package minefantasy.mfr.item;

import minefantasy.mfr.api.tool.IScope;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpyglass extends ItemBaseMFR implements IScope {

    public ItemSpyglass() {
        super("spyglass");
        this.setCreativeTab(MineFantasyTabs.tabGadget);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            world.playSound(player, player.getPosition(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.AMBIENT, 1.0F, 1.0F);
            toggleMode(stack);
            player.swingArm(hand);
        } else {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, stack);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerClient() {

        ModelLoaderHelper.registerItem(this, 0, "spyglass_small");
        ModelLoaderHelper.registerItem(this, 1, "spyglass_medium");
        ModelLoaderHelper.registerItem(this, 2, "spyglass_long");
    }
}
