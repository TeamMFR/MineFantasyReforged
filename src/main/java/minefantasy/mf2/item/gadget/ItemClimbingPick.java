package minefantasy.mf2.item.gadget;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemClimbingPick extends ItemPickaxe implements IToolMaterial {
    private Random rand = new Random();
    private int itemRarity;

    public ItemClimbingPick(String name, ToolMaterial material, int rarity) {
        super(material);
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabGadget);

        setTextureName("minefantasy2:Other/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        setMaxDamage(material.getMaxUses());
    }

    public static boolean tryPerformAbility(EntityLivingBase user, float points) {
        if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(user)) {
            points *= StaminaBar.getClimbinbDecayModifier(user, true);
            if (StaminaBar.isStaminaAvailable(user, points, true)) {
                ItemWeaponMF.applyFatigue(user, points);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        int lvl = itemRarity + 1;

        if (item.isItemEnchanted()) {
            if (lvl == 0) {
                lvl++;
            }
            lvl++;
        }
        if (lvl >= ToolListMF.rarity.length) {
            lvl = ToolListMF.rarity.length - 1;
        }
        return ToolListMF.rarity[lvl];
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
    }

    public boolean isInWall(EntityPlayer player, boolean init) {
        if (player.getHeldItem() == null)
            return false;

        World world = player.worldObj;
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            return false;
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                NBTTagCompound nbt = getOrCreateNBT(player.getHeldItem());
                if (init) {
                    nbt.setInteger("MF_HeldPosX", x);
                    nbt.setInteger("MF_HeldPosY", y);
                    nbt.setInteger("MF_HeldPosZ", z);
                } else {
                    int x1 = nbt.getInteger("MF_HeldPosX");
                    int y1 = nbt.getInteger("MF_HeldPosY");
                    int z1 = nbt.getInteger("MF_HeldPosZ");

                    if (x1 != x || y1 != y || z1 != z) {
                        player.stopUsingItem();
                        return false;
                    }
                }

                Block block = world.getBlock(x, y, z);

                if (player.posY < (y + 2.8F) && player.posY > (y - 3)
                        && player.getDistance(x + 0.5, player.posY, z + 0.5) < 1D && player.fallDistance < 5
                        && block.getMaterial().isSolid() && block.isOpaqueCube()) {
                    return true;
                }
            }
        }
        return false;
    }

    private NBTTagCompound getOrCreateNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        float cost = 0.5F;
        if (stack.getItemDamage() >= stack.getMaxDamage()) {
            player.destroyCurrentEquippedItem();
        }
        World world = player.worldObj;
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null) {
            if (!world.isRemote && StaminaBar.isSystemActive && !tryPerformAbility(player, cost)) {
                player.stopUsingItem();
            }
            return;
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;
                Block block = world.getBlock(x, y, z);

                if (isInWall(player, false)) {
                    if (!player.isSwingInProgress) {
                        cost *= 0.5F;
                        player.motionY = 0;
                    } else {

                        player.fallDistance = 0;
                        if (!player.isSneaking() && player.posY < y + 3) {
                            player.motionY = 0.05D;
                        } else if (player.isSneaking() && player.posY > y - 2) {
                            player.motionY = -0.05D;
                            cost *= 0.75F;
                        } else {
                            player.motionY = 0;
                            cost *= 0.5F;
                        }
                    }
                }
            }
        }
        if (!world.isRemote && StaminaBar.isSystemActive && !tryPerformAbility(player, cost)) {
            player.stopUsingItem();
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (user.isUsingItem())
            return item;
        if (StaminaBar.isSystemActive && !StaminaBar.isAnyStamina(user, true)) {
            return item;
        }

        if (isInWall(user, true)) {
            user.setItemInUse(item, getMaxItemUseDuration(item));
            if (!world.isRemote) {
                item.damageItem(1, user);
            }
            user.playSound("mob.sheep.shear", 1.0F, 2.0F);
        }
        return item;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return Integer.MAX_VALUE;
    }
}
