package minefantasy.mf2.item.gadget;

import minefantasy.mf2.api.heating.TongsHelper;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.block.decor.BlockComponent;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

public class ItemJug extends ItemComponentMF {
    private String type;
    private Random rand = new Random();

    public ItemJug(String type) {
        super("jug_" + type, 0);
        setCreativeTab(CreativeTabMF.tabFood);
        this.type = type;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (type.equalsIgnoreCase("uncooked")) {
            return super.onItemRightClick(item, world, player);
        }
        if (type.equalsIgnoreCase("empty")) {
            return rightClickEmpty(item, world, player);
        }
        return useFilled(item, world, player);
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote) {
            if (type.equalsIgnoreCase("milk")) {
                curePotionEffects(user);
            }
            if (StaminaBar.isSystemActive) {
                StaminaBar.modifyStaminaValue(user, 10);
            }
        }
        ItemStack container = getContainerItem(item);
        if (item.stackSize > 1) {
            if (!user.capabilities.isCreativeMode) {
                --item.stackSize;
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
        return EnumAction.drink;
    }

    private ItemStack rightClickEmpty(ItemStack item, World world, EntityPlayer player) {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            return super.onItemRightClick(item, world, player);
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k)) {
                    return super.onItemRightClick(item, world, player);
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {
                    return super.onItemRightClick(item, world, player);
                }

                if (isWaterSource(world, i, j, k)) {
                    gather(item, world, player);
                    return item;
                }
            }
            return super.onItemRightClick(item, world, player);
        }
    }

    private void gather(ItemStack item, World world, EntityPlayer player) {
        player.swingItem();
        if (!world.isRemote) {
            world.playSoundAtEntity(player, "random.splash", 0.125F + rand.nextFloat() / 4F, 0.5F + rand.nextFloat());
            item.stackSize--;
            EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ,
                    new ItemStack(FoodListMF.jug_water));
            world.spawnEntityInWorld(resultItem);
        }
    }

    private boolean isWaterSource(World world, int i, int j, int k) {
        return TongsHelper.getWaterSource(world, i, j, k) >= 0;
    }

    public ItemStack useFilled(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote && storageType != null) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, user, false);

            if (movingobjectposition == null) {
                return item;
            } else {
                int placed = BlockComponent.useComponent(item, storageType, blocktex, world, user,
                        movingobjectposition);
                if (placed > 0) {
                    item.stackSize -= placed;
                    return item;
                }
            }
        }
        user.setItemInUse(item, getMaxItemUseDuration(item));
        return item;
    }
}
