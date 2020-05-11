package minefantasy.mfr.item.gadget;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.archery.IDisplayMFRAmmo;
import minefantasy.mfr.api.archery.IFirearm;
import minefantasy.mfr.api.archery.ISpecialBow;
import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.api.crafting.engineer.ICrossbowPart;
import minefantasy.mfr.api.helpers.PowerArmour;
import minefantasy.mfr.api.weapon.IDamageModifier;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.block.tile.decor.TileEntityRack;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.item.archery.ItemArrowMFR;
import minefantasy.mfr.mechanics.CombatMechanics;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class ItemCrossbow extends Item
        implements IFirearm, IDisplayMFRAmmo, IDamageModifier, IRackItem, IDamageType, IScope, ISpecialSalvage {
    private static final String partNBT = "MineFantasy_GunPiece_";
    public static String useTypeNBT = "MF_ActionInUse";
    private String[] fullParts = new String[]{"mod", "muzzle", "mechanism", "stock"};

    public ItemCrossbow() {
        String name = "MF_CrossbowCustom";
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);

        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.setFull3D();
        this.setMaxDamage(150);
        this.setMaxStackSize(1);
    }

    public static void setUseAction(ItemStack item, String action) {
        AmmoMechanicsMFR.getNBT(item).setString(useTypeNBT, action);
    }

    public static String getUseAction(ItemStack item) {
        String action = AmmoMechanicsMFR.getNBT(item).getString(useTypeNBT);

        return action != null ? action : "null";
    }

    public static void setPart(String part, ItemStack item, int id) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setInteger(partNBT + part, id);
    }

    public static int getPart(String part, ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (nbt.hasKey(partNBT + part)) {
            return nbt.getInteger(partNBT + part);
        }
        return -1;
    }

    public static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        return item.getTagCompound();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        String action = getUseAction(item);
        if (action.equalsIgnoreCase("reload")) {
            return EnumAction.BLOCK;
        }
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (!world.isRemote && user.isSneaking() || AmmoMechanicsMFR.isDepleted(item))// OPEN INV
        {
            user.openGui(MineFantasyReborn.instance, 1, user.world, 1, 0, 0);
            return ActionResult.newResult(EnumActionResult.FAIL, item);
        }
        ItemStack loaded = AmmoMechanicsMFR.getArrowOnBow(item);
        if (loaded == null || user.isSwingInProgress)// RELOAD
        {
            startUse(user, item, "reload");
            return ActionResult.newResult(EnumActionResult.FAIL, item);
        } else // FIRE
        {
        }
        startUse(user, item, "fire");
        return ActionResult.newResult(EnumActionResult.PASS, item);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack item, World world, EntityLivingBase user) {
        boolean infinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(51), item) > 0;
        user.swingArm(EnumHand.MAIN_HAND);
        ItemStack loaded = AmmoMechanicsMFR.getArrowOnBow(item);
        ItemStack storage = AmmoMechanicsMFR.getAmmo(item);
        String action = getUseAction(item);
        boolean shouldConsume = true;

        if (action.equalsIgnoreCase("reload")) {
            if (storage == null && infinity) {
                shouldConsume = false;
                storage = CustomToolListMFR.STANDARD_BOLT.construct("Magic");
            }
            if (storage != null)// RELOAD
            {
                boolean success = false;
                if (loaded == null) {
                    ItemStack ammo = storage.copy();
                    ammo.setCount(1);
                    if (shouldConsume)
                        AmmoMechanicsMFR.consumeAmmo((EntityPlayer) user, item);
                    AmmoMechanicsMFR.putAmmoOnFirearm(item, ammo);
                    success = true;
                } else if (loaded.isItemEqual(storage) && loaded.getCount() < getAmmoCapacity(item)) {
                    if (shouldConsume)
                        AmmoMechanicsMFR.consumeAmmo((EntityPlayer) user, item);
                    loaded.grow(1);
                    AmmoMechanicsMFR.putAmmoOnFirearm(item, loaded);
                    success = true;
                }
                if (success) {
                    user.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0F, 1.0F);
                }
            }
        }
        return super.onItemUseFinish(item, world, user);
    }

    @Override
    public void onUsingTick(ItemStack item, EntityLivingBase player, int time) {
        ItemStack loaded = AmmoMechanicsMFR.getArrowOnBow(item);
        int max = getMaxItemUseDuration(item);

        if (time == (max - 5) && getUseAction(item).equalsIgnoreCase("reload")
                && (loaded == null || loaded.getCount() < getAmmoCapacity(item))) {
            player.playSound(SoundsMFR.CROSSBOW_LOAD, 1.0F, 1 / (getFullValue(item, "speed") / 4F));
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityLivingBase user, int timeLeft) {
        ItemStack loaded = AmmoMechanicsMFR.getArrowOnBow(item);
        String action = getUseAction(item);

        if (action.equalsIgnoreCase("fire") && this.onFireArrow(user.world, AmmoMechanicsMFR.getArrowOnBow(item),
                item, (EntityPlayer) user, this.getFullValue(item, "power"), false)) {
            if (loaded != null) {
                loaded.grow(1);
                AmmoMechanicsMFR.putAmmoOnFirearm(item, (loaded.getCount() > 0 ? loaded : null));
            }
            recoilUser((EntityPlayer) user, getFullValue(item, "recoil"));
            AmmoMechanicsMFR.damageContainer(item, (EntityPlayer) user, 1);
        }
        stopUse(item);
    }

    public void startUse(EntityPlayer user, ItemStack item, String action) {
        setUseAction(item, action);
        if (user != null)
            user.setActiveHand(user.swingingHand);
    }

    public void stopUse(ItemStack item) {
        startUse(null, item, "null");
    }

    private void recoilUser(EntityPlayer user, float value) {
        if (PowerArmour.isPowered(user)) {
            return;
        }
        float str = CombatMechanics.getStrengthEnhancement(user) + 1;
        value /= str;

        float angle = value;
        user.rotationPitch -= itemRand.nextFloat() * angle;
        user.rotationYawHead += itemRand.nextFloat() * angle - 0.5F;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        String action = getUseAction(item);
        if (action.equalsIgnoreCase("reload")) {
            return (int) (getFullValue(item, "speed") * 20F);
        }
        return 72000;
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag fullInfo) {
        super.addInformation(item, world, list, fullInfo);

        list.add(I18n.translateToLocalFormatted("attribute.crossbow.power.name", getFullValue(item, "power")));
        list.add(I18n.translateToLocalFormatted("attribute.crossbow.speed.name", getFullValue(item, "speed")));
        list.add(I18n.translateToLocalFormatted("attribute.crossbow.recoil.name",
                getFullValue(item, "recoil")));
        list.add(I18n.translateToLocalFormatted("attribute.crossbow.spread.name",
                getFullValue(item, "spread")));
        list.add(I18n.translateToLocalFormatted("attribute.crossbow.capacity.name", getAmmoCapacity(item)));
        list.add(I18n.translateToLocalFormatted("attribute.crossbow.bash.name", getMeleeDmg(item)));
    }

    public String getItemStackDisplayName(ItemStack item) {
        String base = getNameModifier(item, "stock");
        String arms = getNameModifier(item, "mechanism");
        String mod = getNameModifier(item, "mod");

        String fullName = "Crossbow";

        if (base != null)
            fullName = base;
        if (arms != null)
            fullName = arms + " " + fullName;
        if (mod != null)
            fullName = mod + " " + fullName;

        return fullName;
    }

    /**
     * Constructs a crossbow with a list of parts
     */
    public ItemStack constructCrossbow(ICrossbowPart... crossbowParts) {
        ItemStack crossbow = new ItemStack(this);

        for (ICrossbowPart part : crossbowParts) {
            if (part != null) {
                setPart(crossbow, part);
            }
        }
        return crossbow;
    }

    /**
     * Adds a part to a crossbow
     */
    public ItemStack setPart(ItemStack item, ICrossbowPart part) {
        setPart(part.getComponentType(), item, part.getID());
        return item;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(constructCrossbow((ICrossbowPart) ComponentListMFR.CROSSBOW_HANDLE_WOOD,
                (ICrossbowPart) ComponentListMFR.CROSS_ARMS_BASIC));
        items.add(constructCrossbow((ICrossbowPart) ComponentListMFR.CROSSBOW_STOCK_WOOD,
                (ICrossbowPart) ComponentListMFR.CROSS_ARMS_LIGHT));

        items.add(constructCrossbow((ICrossbowPart) ComponentListMFR.CROSSBOW_STOCK_WOOD,
                (ICrossbowPart) ComponentListMFR.CROSS_ARMS_BASIC, (ICrossbowPart) ComponentListMFR.CROSS_AMMO));
        items.add(constructCrossbow((ICrossbowPart) ComponentListMFR.CROSSBOW_STOCK_WOOD,
                (ICrossbowPart) ComponentListMFR.CROSS_ARMS_HEAVY, (ICrossbowPart) ComponentListMFR.CROSS_BAYONET));
        items.add(constructCrossbow((ICrossbowPart) ComponentListMFR.CROSSBOW_STOCK_IRON,
                (ICrossbowPart) ComponentListMFR.CROSS_ARMS_ADVANCED, (ICrossbowPart) ComponentListMFR.CROSS_SCOPE));
    }

    public String getNameModifier(ItemStack item, String partname) {
        ICrossbowPart part = ItemCrossbowPart.getPart(partname, getPart(partname, item));
        if (part != null) {
            String name = part.getUnlocalisedName();
            if (name != null) {
                return I18n.translateToLocal(name);
            }
        }
        return null;
    }

    /**
     * Get the modifier for a part (such as power, speed, recoil, capacity and
     * spread)
     */
    public float getModifierForPart(ItemStack item, String partName, String variable) {
        ICrossbowPart part = ItemCrossbowPart.getPart(partName, getPart(partName, item));
        if (part != null) {
            return part.getModifier(variable);
        }
        return 0F;
    }

    /**
     * Checks all "fullParts" for value modifiers
     */
    public float getFullValue(ItemStack item, String variable) {
        float min = variable.equalsIgnoreCase("speed") ? 0.5F : 0F;
        float value = 0F;

        for (String part : fullParts) {
            value += getModifierForPart(item, part, variable);
        }

        return Math.max(min, value);
    }

    @Override
    public int getAmmoCapacity(ItemStack item) {
        return 1 + (int) getModifierForPart(item, "mod", "capacity");// only mod affects capacity
    }

    public float getMeleeDmg(ItemStack item) {
        return 1 + getModifierForPart(item, "muzzle", "bash");// only muzzle affects capacity
    }

    @Override
    public boolean canAcceptAmmo(ItemStack weapon, String ammo) {
        return ammo.equalsIgnoreCase("bolt");
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        if (implement.length > 0 && implement[0] instanceof ItemStack) {
            if (this.getMeleeDmg((ItemStack) implement[0]) > 1.0F)// Bayonet is used
            {
                return new float[]{0F, 0F, 1F};
            }
        }

        return new float[]{0F, 1F, 0F};
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0;
    }

    @Override
    public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam,
                              boolean properHit) {
        return initialDam + this.getMeleeDmg(item) - 1;
    }

    public boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge,
                               boolean infinite) {
        if (arrow == null || !(arrow.getItem() instanceof ItemArrowMFR)) {
            return false;
        }
        ItemArrowMFR ammo = (ItemArrowMFR) arrow.getItem();
        if (!(ammo.getAmmoType(arrow).equalsIgnoreCase("bolt"))) {
            return false;
        }
        // TODO Arrow entity instance
        EntityArrowMFR entArrow = ammo
                .getFiredArrow(new EntityArrowMFR(world, user, getFullValue(bow, "spread"), charge * 2.0F), arrow);

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(48), bow);
        entArrow.setPower(1 + (0.25F * var9));

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(49), bow);

        if (var10 > 0) {
            entArrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), bow) > 0) {
            entArrow.setFire(100);
        }

        if (infinite) {
            entArrow.canBePickedUp = 2;
        }

        if (bow != null && bow.getItem() != null && bow.getItem() instanceof ISpecialBow) {
            entArrow = (EntityArrowMFR) ((ISpecialBow) bow.getItem()).modifyArrow(bow, entArrow);
        }
        if (!world.isRemote) {
            world.spawnEntity(entArrow);
        }
        world.playSound(user, user.getPosition(), SoundsMFR.CROSSBOW_FIRE, SoundCategory.NEUTRAL, 1.0F, 1.0F);

        return true;
    }

    @Override
    public float getZoom(ItemStack item) {
        return getUseAction(item).equalsIgnoreCase("fire") ? getModifierForPart(item, "mod", "zoom") : 0F;// only mod
        // affects
        // zoom;
    }

    @Override
    public Object[] getSalvage(ItemStack item) {
        return new Object[]{getItem(item, "stock"), getItem(item, "mechanism"), getItem(item, "muzzle"),
                getItem(item, "mod")};
    }

    private Object getItem(ItemStack item, String type) {
        return ItemCrossbowPart.getPart(type, this.getPart(type, item));
    }

    @Override
    public int getMaxDamage(ItemStack item) {
        return super.getMaxDamage(item) + (int) getFullValue(item, "durability");
    }

    @Override
    public float getScale(ItemStack itemstack) {
        return 1.0F;
    }

    @Override
    public float getOffsetX(ItemStack itemstack) {
        return 0F;
    }

    @Override
    public float getOffsetY(ItemStack itemstack) {
        return (isHandCrossbow(itemstack) ? 0F : 0.5F) + 1 / 8F;
    }

    @Override
    public float getOffsetZ(ItemStack itemstack) {
        return 0.25F;
    }

    @Override
    public float getRotationOffset(ItemStack itemstack) {
        return 0;
    }

    @Override
    public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
        if (slot == 0 || slot == 3)
            return false;

        return isHandCrossbow(item) || rack.hasRackBelow(slot);
    }

    @Override
    public boolean isSpecialRender(ItemStack item) {
        return true;
    }

    public boolean isHandCrossbow(ItemStack item) {
        ICrossbowPart part = ItemCrossbowPart.getPart("stock", getPart("stock", item));
        if (part != null) {
            return part.makesSmallWeapon();
        }
        return true;
    }
}