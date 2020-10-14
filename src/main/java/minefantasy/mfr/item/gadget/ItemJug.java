package minefantasy.mfr.item.gadget;

import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.block.decor.BlockComponent;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class ItemJug extends ItemComponentMFR {
    private String type;
    private Random rand = new Random();

    public ItemJug(String type) {
        super("jug_" + type, 0);
        setCreativeTab(CreativeTabMFR.tabFood);
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        if (type.equalsIgnoreCase("uncooked")) {
            return super.onItemRightClick(world, player, hand);
        }
        if (type.equalsIgnoreCase("empty")) {
            rightClickEmpty(item, world, player);
            return rightClickEmpty(item, world, player);
        }
        return useFilled(item, world, player);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack item, World world, EntityLivingBase entityLiving) {
        EntityPlayer user = (EntityPlayer) entityLiving;
        if (!world.isRemote) {
            if (type.equalsIgnoreCase("milk")) {
                curePotionEffects(user);
            }
            if (StaminaBar.isSystemActive) {
                StaminaBar.modifyStaminaValue(user, 10);
            }
        }
        ItemStack container = getContainerItem(item);
        if (item.getCount() > 1) {
            if (!user.capabilities.isCreativeMode) {
                item.shrink(1);
            }
            if (!user.inventory.addItemStackToInventory(container)) {
                user.entityDropItem(container, 0F);
            }
            return item;
        }

        return container;
    }

    private void curePotionEffects(EntityPlayer user) {
        user.clearActivePotions();
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return 24;
    }

    /**
     * returns the action that specifies what animation to play when the items is
     * being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.DRINK;
    }

    public ActionResult<ItemStack> rightClickEmpty(ItemStack item, World world, EntityPlayer player) {
        RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

        if (rayTraceResult == null) {
            return super.onItemRightClick(world, player, player.getActiveHand());
        } else {
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = rayTraceResult.getBlockPos();

                if (!world.canMineBlockBody(player, pos)) {
                    return super.onItemRightClick(world, player, player.getActiveHand());
                }

                if (!player.canPlayerEdit(pos, rayTraceResult.sideHit, item)) {
                    return super.onItemRightClick(world, player, player.getActiveHand());
                }

                if (isWaterSource(world, pos)) {
                    gather(item, world, player);
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
            }
            return super.onItemRightClick(world, player, player.getActiveHand());
        }
    }

    private void gather(ItemStack item, World world, EntityPlayer player) {
        player.swingArm(player.swingingHand);
        if (!world.isRemote) {
            world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
            item.shrink(1);
            EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ,
                    new ItemStack(FoodListMFR.JUG_WATER));
            world.spawnEntity(resultItem);
        }
    }

    private boolean isWaterSource(World world, BlockPos pos) {
        return TongsHelper.getWaterSource(world, pos) >= 0;
    }

    public ActionResult<ItemStack> useFilled(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote && storageType != null) {
            RayTraceResult rayTraceResult = this.rayTrace(world, user, false);

            if (rayTraceResult == null) {
                return ActionResult.newResult(EnumActionResult.PASS, item);
            } else {
                int placed = BlockComponent.useComponent(item, storageType, blocktex, world, user,
                        rayTraceResult);
                if (placed > 0) {
                    item.shrink(placed);
                    return ActionResult.newResult(EnumActionResult.PASS, item);
                }
            }
        }
        user.setActiveHand(user.swingingHand);
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }
}
