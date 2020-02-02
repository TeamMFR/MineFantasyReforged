package minefantasy.mfr.item.archery;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.archery.IAmmo;
import minefantasy.mfr.api.archery.IDisplayMFRAmmo;
import minefantasy.mfr.api.archery.IFirearm;
import minefantasy.mfr.api.archery.ISpecialBow;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Optional.Interface(iface = "mods.battlegear2.api.weapons.IBattlegearWeapon", modid = "battlegear2")
public class ItemBowMFR extends ItemBow implements ISpecialBow, IDisplayMFRAmmo, IFirearm {
    public static final DecimalFormat decimal_format = new DecimalFormat("#.##");
    private final EnumBowType model;
    private int itemRarity;
    private float baseDamage = 1.0F;
    private String name;
    /**
     * Return the enchantability factor of the item, most of the time is based on
     * material.
     */
    private int enchantmentLvl = 1;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private String designType = "standard";

    public ItemBowMFR(String name, EnumBowType type) {
        this(name, ToolMaterial.WOOD, type, 0);
    }

    public ItemBowMFR(String name, ToolMaterial mat, EnumBowType type, int rarity) {
        this(name, (int) (mat.getMaxUses() * type.durabilityModifier), type, mat.getAttackDamage(), rarity);
        this.enchantmentLvl = mat.getEnchantability();
    }

    private ItemBowMFR(String name, int dura, EnumBowType type, float damage, int rarity) {
        this.name = name;
        this.baseDamage = damage;
        model = type;
        this.maxStackSize = 1;
        this.setMaxDamage(dura);
        itemRarity = rarity;
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
        setCreativeTab(CreativeTabMFR.tabOldTools);
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D() {
        return true;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world,
     * entityplayer, itemInUseCount
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityLivingBase player, int time) {
        int power = (this.getMaxItemUseDuration(item) - time);

        ArrowLooseEvent event = new ArrowLooseEvent((EntityPlayer) player, item, world, power, true);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return;
        }
        boolean var5 = ((EntityPlayer) player).capabilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(51), item) > 0;
        power = event.getCharge();

        if (var5 || ((EntityPlayer) player).inventory.mainInventory.contains(Items.ARROW)) {
            float firepower = power / model.chargeTime;

            if (firepower < 0.1D) {
                return;
            }
            if (firepower > 1.0F) {
                firepower = 1.0F;
            }

            EntityArrow var8 = new EntityArrow(world, player, firepower * 2.0F);

            if (firepower == 1.0F) {
                var8.setIsCritical(true);
            }

            int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(48), item);

            if (var9 > 0) {
                var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
            }

            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(49), item);

            if (var10 > 0) {
                var8.setKnockbackStrength(var10);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), item) > 0) {
                var8.setFire(100);
            }

            AmmoMechanicsMFR.damageContainer(item, (EntityPlayer) player, 1);
            world.playSound(player.posX, player.posY, player.posZ, SoundsMFR.BOW_FIRE, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + firepower * 0.5F, true);

            if (var5) {
                var8.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
            } else {
                ((EntityPlayer) player).inventory.mainInventory.remove(Items.ARROW);
            }
            var8 = (EntityArrow) modifyArrow(item, var8);

            if (!world.isRemote) {
                world.spawnEntity(var8);
            }
        }
    }

    public ItemStack onFoodEaten(ItemStack item, World world, EntityPlayer player) {
        return item;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is
     * being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.BOW;
    }

    @Override
    public void addInformation(ItemStack item, World world, List desc, ITooltipFlag flag) {
        super.addInformation(item, world, desc, flag);

        CustomToolHelper.addBowInformation(item, desc);
        ItemStack ammo = AmmoMechanicsMFR.getAmmo(item);
        if (ammo != null) {
            desc.add(TextFormatting.DARK_GRAY + ammo.getDisplayName() + " x" + ammo.getCount());
        }

        desc.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("attribute.bowPower.name",
                decimal_format.format(getBowDamage(item))));
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed.
     * Args: itemStack, world, entityPlayer
     */
    @Override
    /// Item is the bow.
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        if (!world.isRemote && player.isSneaking() || AmmoMechanicsMFR.isDepleted(item)) {
            reloadBow(item, player);
            return ActionResult.newResult(EnumActionResult.PASS, item);
        }
        ArrowNockEvent event = new ArrowNockEvent(player, item, hand, world, true);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            return event.getAction();
        }
        return ActionResult.newResult(EnumActionResult.FAIL, item);

        // player.inventory.hasItem(Items.arrow)
    }

    public boolean canAccept(ItemStack ammo) {
        String ammoType = "null";
        ItemStack weapon = new ItemStack(this);
        if (ammo != null && ammo.getItem() instanceof IAmmo) {
            ammoType = ((IAmmo) ammo.getItem()).getAmmoType(ammo);
        }

        if (weapon != null && weapon.getItem() instanceof IFirearm) {
            return ((IFirearm) weapon.getItem()).canAcceptAmmo(weapon, ammoType);
        }

        return ammoType.equalsIgnoreCase("arrow");
    }

    private void reloadBow(ItemStack item, EntityPlayer player) {
        player.openGui(MineFantasyReborn.instance, 1, player.world, 1, 0, 0);
    }

    @Override
    public int getItemEnchantability() {
        return enchantmentLvl;
    }

    @Override
    public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b) {
        super.onUpdate(item, world, entity, i, b);
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
            item.getTagCompound().setInteger("Use", i);
        }
    }

    public int getDrawAmount(int timer) {
        float maxCharge = this.getMaxCharge();
        if (timer > (maxCharge * 0.9F))
            return 2;
        else if (timer > (maxCharge * 0.65F))
            return 1;
        else if (timer > 0)
            return 0;

        return -2;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        int lvl = itemRarity;

        EnumRarity[] rarity = new EnumRarity[]{EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC};
        if (item.isItemEnchanted()) {
            if (lvl == 0) {
                lvl++;
            }
            lvl++;
        }
        if (lvl >= rarity.length) {
            lvl = rarity.length - 1;
        }
        return rarity[lvl];
    }

    private EnumRarity rarity(ItemStack item, int lvl) {
        EnumRarity[] rarity = new EnumRarity[]{EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC};
        if (item.isItemEnchanted()) {
            if (lvl == 0) {
                lvl++;
            }
            lvl++;
        }
        if (lvl >= rarity.length) {
            lvl = rarity.length - 1;
        }
        return rarity[lvl];
    }

    @Override
    public Entity modifyArrow(ItemStack bow, Entity arrow) {
        if (this.isCustom) {
            CustomMaterial custom = CustomToolHelper.getCustomPrimaryMaterial(bow);
            if (custom != null) {
                if (custom.name.equalsIgnoreCase("silver")) {
                    arrow.getEntityData().setBoolean("MF_Silverbow", true);
                }
            }
        }

        float dam = getBowDamage(bow);

        arrow.getEntityData().setFloat("MF_Bow_Damage", dam);
        arrow.getEntityData().setString("Design", designType);

        return arrow;
    }

    public boolean canUseRenderer(ItemStack item) {
        return true;
    }

    private void addSet(List list, Item[] items) {
        for (Item item : items) {
            list.add(new ItemStack(item));
        }
    }

    @Override
    public boolean canAcceptAmmo(ItemStack weapon, String ammo) {
        return ammo.equalsIgnoreCase("arrow");
    }

    @Override
    public int getAmmoCapacity(ItemStack item) {
        return 1;
    }

    public ItemBowMFR setCustom(String designType) {
        canRepair = false;
        isCustom = true;
        this.designType = designType;
        return this;
    }

    public ItemStack construct(String main, String haft) {
        return CustomToolHelper.construct(this, main, haft);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getWoodenLocalisedName(item, unlocalName);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isCustom) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                if (MineFantasyReborn.isDebug() || customMat.getItem() != null) {
                    items.add(this.construct("Iron", customMat.name));
                }
            }
            return;
        }
    }

    public float getBowDamage(ItemStack item) {
        return CustomToolHelper.getBowDamage(item, baseDamage) * model.damageModifier;
    }

    @Override
    public float getRange(ItemStack item) {
        return model.velocity;
    }

    @Override
    public float getSpread(ItemStack item) {
        return model.spread;
    }

    // ====================================================== CUSTOM END
    // ==============================================================\\
    @Override
    public float getMaxCharge() {
        return model.chargeTime;
    }
}
