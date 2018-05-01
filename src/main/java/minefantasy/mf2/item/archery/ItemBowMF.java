package minefantasy.mf2.item.archery;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.archery.*;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.list.CreativeTabMF;
import mods.battlegear2.api.weapons.IBattlegearWeapon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Optional.Interface(iface = "mods.battlegear2.api.weapons.IBattlegearWeapon", modid = "battlegear2")
public class ItemBowMF extends ItemBow implements ISpecialBow, IDisplayMFAmmo, IBattlegearWeapon, IFirearm {
    public static final DecimalFormat decimal_format = new DecimalFormat("#.##");
    private final EnumBowType model;
    public IIcon[] mainIcons = new IIcon[3];
    public IIcon cus_metal_standby;
    public IIcon[] cus_metal_pulling = new IIcon[3];
    public IIcon cus_wood_standby;
    public IIcon[] cus_wood_pulling = new IIcon[3];
    public IIcon cus_detail_standby;
    public IIcon[] cus_detail_pulling = new IIcon[3];
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

    public ItemBowMF(String name, EnumBowType type) {
        this(name, ToolMaterial.WOOD, type, 0);
    }

    public ItemBowMF(String name, ToolMaterial mat, EnumBowType type, int rarity) {
        this(name, (int) (mat.getMaxUses() * type.durabilityModifier), type, mat.getDamageVsEntity(), rarity);
        this.enchantmentLvl = mat.getEnchantability();
    }

    private ItemBowMF(String name, int dura, EnumBowType type, float damage, int rarity) {
        this.name = name;
        this.baseDamage = damage;
        model = type;
        this.maxStackSize = 1;
        this.setMaxDamage(dura);
        itemRarity = rarity;
        setTextureName("minefantasy2:Bow/" + name);
        this.setUnlocalizedName(name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        setCreativeTab(CreativeTabMF.tabOldTools);
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
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int time) {
        int power = (this.getMaxItemUseDuration(item) - time);

        ArrowLooseEvent event = new ArrowLooseEvent(player, item, power);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return;
        }
        boolean var5 = player.capabilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
        power = event.charge;

        if (var5 || player.inventory.hasItem(Items.arrow)) {
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

            int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);

            if (var9 > 0) {
                var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
            }

            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);

            if (var10 > 0) {
                var8.setKnockbackStrength(var10);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0) {
                var8.setFire(100);
            }

            AmmoMechanicsMF.damageContainer(item, player, 1);
            world.playSoundAtEntity(player, "minefantasy2:weapon.bowFire", 1.0F,
                    1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + firepower * 0.5F);

            if (var5) {
                var8.canBePickedUp = 2;
            } else {
                player.inventory.consumeInventoryItem(Items.arrow);
            }
            var8 = (EntityArrow) modifyArrow(item, var8);

            if (!world.isRemote) {
                world.spawnEntityInWorld(var8);
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
        return EnumAction.bow;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List desc, boolean flag) {
        super.addInformation(item, player, desc, flag);

        CustomToolHelper.addBowInformation(item, desc);
        ItemStack ammo = AmmoMechanicsMF.getAmmo(item);
        if (ammo != null) {
            desc.add(EnumChatFormatting.DARK_GRAY + ammo.getDisplayName() + " x" + ammo.stackSize);
        }

        desc.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.bowPower.name",
                decimal_format.format(getBowDamage(item))));
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed.
     * Args: itemStack, world, entityPlayer
     */
    @Override
    /// Item is the bow.
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {

        if (!world.isRemote && player.isSneaking() || AmmoMechanicsMF.isDepleted(item)) {
            reloadBow(item, player);
            return item;
        }
        ArrowNockEvent event = new ArrowNockEvent(player, item);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            return event.result;
        }
        return item;

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
        player.openGui(MineFantasyII.instance, 1, player.worldObj, 1, 0, 0);
    }

    @Override
    public int getItemEnchantability() {
        return enchantmentLvl;
    }

    @Override
    public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b) {
        super.onUpdate(item, world, entity, i, b);
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        item.stackTagCompound.setInteger("Use", i);
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

        EnumRarity[] rarity = new EnumRarity[]{EnumRarity.common, EnumRarity.uncommon, EnumRarity.rare,
                EnumRarity.epic};
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
        EnumRarity[] rarity = new EnumRarity[]{EnumRarity.common, EnumRarity.uncommon, EnumRarity.rare,
                EnumRarity.epic};
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
    public boolean sheatheOnBack(ItemStack item) {
        return true;
    }

    @Override
    public boolean isOffhandHandDual(ItemStack off) {
        return false;
    }

    @Override
    @Optional.Method(modid = "battlegear2")
    public boolean offhandAttackEntity(mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return false;
    }

    @Override
    public boolean offhandClickAir(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return false;
    }

    @Override
    public boolean offhandClickBlock(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return false;
    }

    @Override
    public void performPassiveEffects(Side effectiveSide, ItemStack mainhandItem, ItemStack offhandItem) {
    }

    @Override
    public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) {
        return false;
    }

    @Override
    public boolean canAcceptAmmo(ItemStack weapon, String ammo) {
        return ammo.equalsIgnoreCase("arrow");
    }

    @Override
    public int getAmmoCapacity(ItemStack item) {
        return 1;
    }

    public ItemBowMF setCustom(String designType) {
        canRepair = false;
        setTextureName("minefantasy2:custom/bow/" + designType + "/" + name);
        isCustom = true;
        this.designType = designType;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIcon(stack, pass, -1);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack bow, int layer, int pull) {
        if (!isCustom) {
            return pull >= 0 ? mainIcons[pull] : this.itemIcon;
        }

        if (layer == 0) {
            return pull >= 0 ? cus_metal_pulling[pull] : cus_metal_standby;
        }
        if (layer == 1) {
            return pull >= 0 ? cus_wood_pulling[pull] : cus_wood_standby;
        } else {
            return pull >= 0 ? cus_detail_pulling[pull] : cus_detail_standby;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(this.getIconString() + "_standby");
        for (int i = 0; i < 3; ++i) {
            this.mainIcons[i] = reg.registerIcon(this.getIconString() + "_pulling_" + (i));
        }

        if (isCustom) {
            this.cus_metal_standby = reg.registerIcon(this.getIconString() + "_standby");
            this.cus_wood_standby = reg.registerIcon(this.getIconString() + "_standby_haft");
            this.cus_detail_standby = reg.registerIcon(this.getIconString() + "_standby_detail");

            for (int i = 0; i < 3; ++i) {
                this.cus_metal_pulling[i] = reg.registerIcon(this.getIconString() + "_pulling_" + (i));
                this.cus_wood_pulling[i] = reg.registerIcon(this.getIconString() + "_pulling_" + (i) + "_haft");
                this.cus_detail_pulling[i] = reg.registerIcon(this.getIconString() + "_pulling_" + (i) + "_detail");
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        int c = CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));
        return c;
    }

    public ItemStack construct(String main, String haft) {
        return CustomToolHelper.construct(this, main, haft);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return isCustom;
    }

    // Returns the number of render passes this item has.
    @Override
    public int getRenderPasses(int metadata) {
        return 3;
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
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (isCustom) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                if (MineFantasyII.isDebug() || customMat.getItem() != null) {
                    list.add(this.construct("Iron", customMat.name));
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
