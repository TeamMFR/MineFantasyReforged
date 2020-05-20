package minefantasy.mfr.item.gadget;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.archery.IAmmo;
import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.entity.EntityBomb;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.mechanics.BombDispenser;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class ItemBomb extends Item implements ISpecialSalvage, IAmmo {
    private static final String powderNBT = "MineFantasy_PowderType";
    private static final String fuseNBT = "MineFantasy_FuseType";
    private static final String fillingNBT = "MineFantasy_ExplosiveType";
    private static final String casingNBT = "MineFantasy_CaseType";

    public ItemBomb(String name) {
        this.maxStackSize = 16;
        setRegistryName(name);
        setUnlocalizedName(name);

        this.setCreativeTab(CreativeTabMFR.tabGadget);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new BombDispenser());
    }

    public static void setSticky(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setBoolean("stickyBomb", true);
    }

    public static void setFuse(ItemStack item, byte fuse) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(fuseNBT, fuse);
    }

    public static byte getFuse(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemFuse(nbt.getByte(fuseNBT));
        }
        return nbt.getByte(fuseNBT);
    }

    public static void setPowder(ItemStack item, byte powder) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(powderNBT, powder);
    }

    public static byte getPowder(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemPowder(nbt.getByte(powderNBT));
        }
        return nbt.getByte(powderNBT);
    }

    /**
     * 0 = Basic 1 = Shrapnel 2 = Fire
     */
    public static void setFilling(ItemStack item, byte filling) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(fillingNBT, filling);
    }

    public static byte getFilling(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemFilling(nbt.getByte(fillingNBT));
        }
        return nbt.getByte(fillingNBT);
    }

    /**
     * 0 = Ceramic 1 = Iron
     */
    public static void setCasing(ItemStack item, byte casing) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(casingNBT, casing);
    }

    public static byte getCasing(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemCasing(nbt.getByte(casingNBT));
        }
        return nbt.getByte(casingNBT);
    }

    public static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        return item.getTagCompound();
    }

    public static ItemStack createExplosive(Item item, byte casing, byte filling, byte fuse, byte powder, int stackSize,
                                            boolean sticky) {
        ItemStack bomb = new ItemStack(item, stackSize);
        setFilling(bomb, filling);
        setCasing(bomb, casing);
        setFuse(bomb, fuse);
        setPowder(bomb, powder);
        if (sticky) {
            setSticky(bomb);
        }
        return bomb;
    }

    public static ItemStack createExplosive(Item item, byte casing, byte filling, byte fuse, byte powder,
                                            int stackSize) {
        return createExplosive(item, casing, filling, fuse, powder, stackSize, false);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.BLOCK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (!user.isSwingInProgress) {
            world.playSound(user, user.getPosition(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 1.0F, 1.0F);
            world.playSound(user, user.getPosition(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.AMBIENT, 1.0F, 1.0F);
            user.setActiveHand(hand);
        }
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return 15;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack item, World world, EntityLivingBase user) {
        user.swingArm(user.swingingHand);
            item.shrink(1);
        if (!world.isRemote) {
            EntityBomb bomb = new EntityBomb(world, user).setType(getFilling(item), getCasing(item), getFuse(item),
                    getPowder(item));
            world.spawnEntity(bomb);
            if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
                bomb.getEntityData().setBoolean("stickyBomb", true);
            }
        }
        return item;
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        super.addInformation(item, world, list, flag);

        if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
            list.add(TextFormatting.GREEN + I18n.translateToLocal("bomb.case.sticky")
                    + TextFormatting.GRAY);
        }
        EnumExplosiveType fill = EnumExplosiveType.getType(getFilling(item));
        EnumCasingType casing = EnumCasingType.getType(getCasing(item));
        EnumFuseType fuse = EnumFuseType.getType(getFuse(item));
        EnumPowderType powder = EnumPowderType.getType(getPowder(item));

        int damage = (int) (fill.damage * casing.damageModifier * powder.damageModifier);
        float range = fill.range * casing.rangeModifier * powder.rangeModifier;
        float fusetime = fuse.time / 20F;

        list.add(I18n.translateToLocal("bomb.case." + casing.name + ".name"));
        list.add(I18n.translateToLocal("bomb.powder." + powder.name + ".name"));
        list.add(I18n.translateToLocal("bomb.fuse." + fuse.name + ".name"));
        list.add("");
        list.add(I18n.translateToLocalFormatted("bomb.fusetime.name", fusetime));
        list.add(I18n.translateToLocal("bomb.damage.name") + ": " + damage);
        list.add(I18n.translateToLocalFormatted("bomb.range.metric.name", range));
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        EnumExplosiveType type = EnumExplosiveType.getType(getFilling(item));
        return "item.bomb_" + type.name;
    }

    public ItemStack createBomb(byte casing, byte filling, byte fuse, byte powder, int stackSize) {
        return ItemBomb.createExplosive(this, casing, filling, fuse, powder, stackSize);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(createBomb((byte) 0, (byte) 0, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 0, (byte) 1, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 0, (byte) 2, (byte) 0, (byte) 0, 1));

        items.add(createBomb((byte) 1, (byte) 0, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 1, (byte) 1, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 1, (byte) 2, (byte) 0, (byte) 0, 1));

        items.add(createBomb((byte) 2, (byte) 0, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 2, (byte) 1, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 2, (byte) 2, (byte) 0, (byte) 0, 1));

        items.add(createBomb((byte) 3, (byte) 0, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 3, (byte) 1, (byte) 0, (byte) 0, 1));
        items.add(createBomb((byte) 3, (byte) 2, (byte) 0, (byte) 0, 1));
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        if (getFilling(item) >= 2 || getCasing(item) >= 2) {
            return EnumRarity.UNCOMMON;
        }
        return EnumRarity.COMMON;
    }

    @Override
    public Object[] getSalvage(ItemStack item) {
        return new Object[]{ItemBombComponent.getBombComponent("bombcase", getCasing(item)),
                ItemBombComponent.getBombComponent("fuse", getFuse(item)),
                ItemBombComponent.getBombComponent("powder", getPowder(item)),
                ItemBombComponent.getBombComponent("filling", getFilling(item)),};
    }

    public byte getItemFuse(byte value) {
        return value;
    }

    public byte getItemFilling(byte value) {
        return value;
    }

    public byte getItemCasing(byte value) {
        return value;
    }

    public byte getItemPowder(byte value) {
        return value;
    }

    @Override
    public String getAmmoType(ItemStack arrow) {
        return "mine";
    }
}