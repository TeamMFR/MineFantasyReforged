package minefantasy.mf2.item.gadget;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.archery.IDisplayMFAmmo;
import minefantasy.mf2.api.archery.IFirearm;
import minefantasy.mf2.api.archery.ISpecialBow;
import minefantasy.mf2.api.crafting.ISpecialSalvage;
import minefantasy.mf2.api.crafting.engineer.ICrossbowPart;
import minefantasy.mf2.api.helpers.PowerArmour;
import minefantasy.mf2.api.weapon.IDamageModifier;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.api.weapon.IRackItem;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.entity.EntityArrowMF;
import minefantasy.mf2.item.archery.ItemArrowMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.mechanics.CombatMechanics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemCrossbow extends Item
        implements IFirearm, IDisplayMFAmmo, IDamageModifier, IRackItem, IDamageType, IScope, ISpecialSalvage {
    private static final String partNBT = "MineFantasy_GunPiece_";
    public static String useTypeNBT = "MF_ActionInUse";
    private String[] fullParts = new String[]{"mod", "muzzle", "mechanism", "stock"};
    private IIcon[] strings;

    public ItemCrossbow() {
        String name = "MF_CrossbowCustom";
        this.setCreativeTab(CreativeTabMF.tabGadget);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        this.setFull3D();
        this.setTextureName("minefantasy2:gun/stock/cross_stock_wood");
        this.setMaxDamage(150);
        this.setMaxStackSize(1);
    }

    public static void setUseAction(ItemStack item, String action) {
        AmmoMechanicsMF.getNBT(item).setString(useTypeNBT, action);
    }

    public static String getUseAction(ItemStack item) {
        String action = AmmoMechanicsMF.getNBT(item).getString(useTypeNBT);

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
            return EnumAction.block;
        }
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote && user.isSneaking() || AmmoMechanicsMF.isDepleted(item))// OPEN INV
        {
            user.openGui(MineFantasyII.instance, 1, user.worldObj, 1, 0, 0);
            return item;
        }
        ItemStack loaded = AmmoMechanicsMF.getArrowOnBow(item);
        if (loaded == null || user.isSwingInProgress)// RELOAD
        {
            startUse(user, item, "reload");
            return item;
        } else // FIRE
        {
        }
        startUse(user, item, "fire");
        return item;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer user) {
        boolean infinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
        user.swingItem();
        ItemStack loaded = AmmoMechanicsMF.getArrowOnBow(item);
        ItemStack storage = AmmoMechanicsMF.getAmmo(item);
        String action = getUseAction(item);
        boolean shouldConsume = true;

        if (action.equalsIgnoreCase("reload")) {
            if (storage == null && infinity) {
                shouldConsume = false;
                storage = CustomToolListMF.standard_bolt.construct("Magic");
            }
            if (storage != null)// RELOAD
            {
                boolean success = false;
                if (loaded == null) {
                    ItemStack ammo = storage.copy();
                    ammo.stackSize = 1;
                    if (shouldConsume)
                        AmmoMechanicsMF.consumeAmmo(user, item);
                    AmmoMechanicsMF.putAmmoOnFirearm(item, ammo);
                    success = true;
                } else if (loaded.isItemEqual(storage) && loaded.stackSize < getAmmoCapacity(item)) {
                    if (shouldConsume)
                        AmmoMechanicsMF.consumeAmmo(user, item);
                    ++loaded.stackSize;
                    AmmoMechanicsMF.putAmmoOnFirearm(item, loaded);
                    success = true;
                }
                if (success) {
                    user.playSound("random.click", 1.0F, 1.0F);
                }
            }
        }
        return super.onEaten(item, world, user);
    }

    @Override
    public void onUsingTick(ItemStack item, EntityPlayer player, int time) {
        ItemStack loaded = AmmoMechanicsMF.getArrowOnBow(item);
        int max = getMaxItemUseDuration(item);

        if (time == (max - 5) && getUseAction(item).equalsIgnoreCase("reload")
                && (loaded == null || loaded.stackSize < getAmmoCapacity(item))) {
            player.playSound("minefantasy2:weapon.crossbowload", 1.0F, 1 / (getFullValue(item, "speed") / 4F));
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer user, int time) {
        ItemStack loaded = AmmoMechanicsMF.getArrowOnBow(item);
        String action = getUseAction(item);

        if (action.equalsIgnoreCase("fire") && this.onFireArrow(user.worldObj, AmmoMechanicsMF.getArrowOnBow(item),
                item, user, this.getFullValue(item, "power"), false)) {
            if (loaded != null) {
                --loaded.stackSize;
                AmmoMechanicsMF.putAmmoOnFirearm(item, (loaded.stackSize > 0 ? loaded : null));
            }
            recoilUser(user, getFullValue(item, "recoil"));
            AmmoMechanicsMF.damageContainer(item, user, 1);
        }
        stopUse(item);
    }

    public void startUse(EntityPlayer user, ItemStack item, String action) {
        setUseAction(item, action);
        if (user != null)
            user.setItemInUse(item, getMaxItemUseDuration(item));
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
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        super.addInformation(item, user, list, fullInfo);

        list.add(StatCollector.translateToLocalFormatted("attribute.crossbow.power.name", getFullValue(item, "power")));
        list.add(StatCollector.translateToLocalFormatted("attribute.crossbow.speed.name", getFullValue(item, "speed")));
        list.add(StatCollector.translateToLocalFormatted("attribute.crossbow.recoil.name",
                getFullValue(item, "recoil")));
        list.add(StatCollector.translateToLocalFormatted("attribute.crossbow.spread.name",
                getFullValue(item, "spread")));
        list.add(StatCollector.translateToLocalFormatted("attribute.crossbow.capacity.name", getAmmoCapacity(item)));
        list.add(StatCollector.translateToLocalFormatted("attribute.crossbow.bash.name", getMeleeDmg(item)));
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
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer) {
        IIcon stock = getPartIcon(item, "stock");
        if (stock != null && layer == 0) {
            return stock;
        }
        IIcon muzzle = getPartIcon(item, "muzzle");
        if (muzzle != null && layer == 1) {
            return muzzle;
        }
        IIcon mod = getPartIcon(item, "mod");
        if (mod != null && layer == 2) {
            return mod;
        }
        IIcon head = getPartIcon(item, "mechanism");
        return head != null ? head : getIconIndex(item);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata) {
        return 4;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(constructCrossbow((ICrossbowPart) ComponentListMF.crossbow_handle_wood,
                (ICrossbowPart) ComponentListMF.cross_arms_basic));
        list.add(constructCrossbow((ICrossbowPart) ComponentListMF.crossbow_stock_wood,
                (ICrossbowPart) ComponentListMF.cross_arms_light));

        list.add(constructCrossbow((ICrossbowPart) ComponentListMF.crossbow_stock_wood,
                (ICrossbowPart) ComponentListMF.cross_arms_basic, (ICrossbowPart) ComponentListMF.cross_ammo));
        list.add(constructCrossbow((ICrossbowPart) ComponentListMF.crossbow_stock_wood,
                (ICrossbowPart) ComponentListMF.cross_arms_heavy, (ICrossbowPart) ComponentListMF.cross_bayonet));
        list.add(constructCrossbow((ICrossbowPart) ComponentListMF.crossbow_stock_iron,
                (ICrossbowPart) ComponentListMF.cross_arms_advanced, (ICrossbowPart) ComponentListMF.cross_scope));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getPartIcon(ItemStack item, String partname) {
        if (partname.equalsIgnoreCase("string")) {
            return strings[AmmoMechanicsMF.getArrowOnBow(item) != null ? 1 : 0];
        }
        ICrossbowPart part = ItemCrossbowPart.getPart(partname, getPart(partname, item));
        if (part != null) {
            return part.getIcon();
        }
        return null;
    }

    public String getNameModifier(ItemStack item, String partname) {
        ICrossbowPart part = ItemCrossbowPart.getPart(partname, getPart(partname, item));
        if (part != null) {
            String name = part.getUnlocalisedName();
            if (name != null) {
                return StatCollector.translateToLocal(name);
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
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        super.registerIcons(reg);
        strings = new IIcon[2];
        strings[0] = reg.registerIcon("minefantasy2:gun/mechanism/cross_string_unload");
        strings[1] = reg.registerIcon("minefantasy2:gun/mechanism/cross_string_load");
    }

    @Override
    public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam,
                              boolean properHit) {
        return initialDam + this.getMeleeDmg(item) - 1;
    }

    public boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge,
                               boolean infinite) {
        if (arrow == null || !(arrow.getItem() instanceof ItemArrowMF)) {
            return false;
        }
        ItemArrowMF ammo = (ItemArrowMF) arrow.getItem();
        if (!(ammo.getAmmoType(arrow).equalsIgnoreCase("bolt"))) {
            return false;
        }
        // TODO Arrow entity instance
        EntityArrowMF entArrow = ammo
                .getFiredArrow(new EntityArrowMF(world, user, getFullValue(bow, "spread"), charge * 2.0F), arrow);

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, bow);
        entArrow.setPower(1 + (0.25F * var9));

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, bow);

        if (var10 > 0) {
            entArrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, bow) > 0) {
            entArrow.setFire(100);
        }

        if (infinite) {
            entArrow.canBePickedUp = 2;
        }

        if (bow != null && bow.getItem() != null && bow.getItem() instanceof ISpecialBow) {
            entArrow = (EntityArrowMF) ((ISpecialBow) bow.getItem()).modifyArrow(bow, entArrow);
        }
        if (!world.isRemote) {
            world.spawnEntityInWorld(entArrow);
        }
        world.playSoundAtEntity(user, "minefantasy2:weapon.crossbow_fire", 1.0F, 1.0F);

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