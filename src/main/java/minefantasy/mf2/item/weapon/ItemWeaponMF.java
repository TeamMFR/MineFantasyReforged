package minefantasy.mf2.item.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.crafting.exotic.ISpecialDesign;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.helpers.TacticalManager;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.stamina.IHeldStaminaItem;
import minefantasy.mf2.api.stamina.IStaminaWeapon;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.weapon.*;
import minefantasy.mf2.api.weapon.ISpecialEffect;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.config.ConfigWeapon;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.tool.crafting.ItemKnifeMF;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.util.MFLogUtil;
import mods.battlegear2.api.weapons.IBackStabbable;
import mods.battlegear2.api.weapons.IBattlegearWeapon;
import mods.battlegear2.api.weapons.IExtendedReachWeapon;
import mods.battlegear2.api.weapons.IHitTimeModifier;
import mods.battlegear2.api.weapons.IPenetrateWeapon;
import mods.battlegear2.api.weapons.WeaponRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
//Made this extend the sword class (allows them to be enchanted)
public abstract class ItemWeaponMF extends ItemSword implements ISpecialDesign, IPowerAttack, IDamageType,
        IKnockbackWeapon, IWeaponSpeed, IHeldStaminaItem, IStaminaWeapon, IBattlegearWeapon, IToolMaterial,
        IWeightedWeapon, IParryable, ISpecialEffect, IDamageModifier, IWeaponClass, IRackItem {
    public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
    public static float axeAPModifier = -0.1F;
    protected static int speedModHeavy = 5;
    protected static int speedModSword = 0;
    protected static int speedModAxe = 1;
    protected static int speedModMace = 2;
    protected static int speedModKatana = -5;
    protected static int speedModSpear = 2;
    protected static float damageModSword = 0.0F;
    protected static float damageModAxe = 0.5F;
    protected static float damageModMace = 1.0F;
    protected final ToolMaterial material;
    public String designType = "standard";
    protected String name;
    protected Random rand = new Random();
    protected float lunge_cost = 25;

    // MECHANICS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    protected float charge_cost = 10;
    protected float jump_cost = 2;
    protected float cleave_cost = 40;
    protected float[] slashingDamage = new float[]{1F, 0F, 0F};
    protected float[] crushingDamage = new float[]{0F, 1F, 0F};
    protected float[] hackingDamage = new float[]{4F, 1F, 0F};
    protected float[] hvyHackingDamage = new float[]{3F, 1F, 0F};
    protected float[] piercingDamage = new float[]{0F, 0F, 1F};
    protected float[] hvyPiercingDamage = new float[]{0F, 1F, 9F};
    protected float[] hvySlashingDamage = new float[]{9F, 1F, 0F};
    protected int defParryTime = 15;
    protected int swordParryTime = 10;
    protected int axeParryTime = 15;
    protected int maceParryTime = 15;
    protected int daggerParryTime = 5;
    protected int spearParryTime = 20;
    protected int heavyParryTime = 10;
    protected float heavyParryFatigue = 2.0F;
    protected float daggerStaminaCost = 0.50F;
    protected float swordStaminaCost = 1.00F;
    protected float katanaStaminaCost = 0.75F;
    protected float axeStaminaCost = 1.20F;
    protected float maceStaminaCost = 1.30F;
    protected float spearStaminaCost = 1.40F;
    protected float heavyStaminaCost = 1.50F;
    protected int itemRarity;
    /**
     * The damage of the weapon without material modifiers
     */
    private float baseDamage;
    private float materialWeight = 1.0F;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private IIcon detailTex = null;
    private IIcon haftTex = null;

    /**
     * The base file for Weapons in MineFantasy
     * <p>
     * Size Varients, (Normal and Heavy) Normal weapons are as is, regular damage
     * and weight Heavy weapons do more damage, exhaust more and have balance offset
     * (+50% dam)
     * <p>
     * Weapon Types: Blade: Parry Defensive, average damage and speed Axe: Brutal
     * Offensive, slower than sword, more damage, Good against Armour Blunt: Simple
     * Offensive, slower than axe, more damage, Good against Medium Armour Polearm:
     * Ranged Defensive, Good against Heavy Armour Lightblade: Fast Offensive,
     * Better against Unarmoured
     */
    public ItemWeaponMF(ToolMaterial material, String named, int rarity, float weight) {
        super(material);
        materialWeight = weight;
        itemRarity = rarity;
        // May be unsafe, but will allow others to add weapons using custom materials
        // (also more efficent)
        name = named;
        this.material = material;
        setCreativeTab(CreativeTabMF.tabOldTools);
        setTextureName("minefantasy2:Weapon/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);

        this.baseDamage = 4 + getDamageModifier();

        if (material == ToolMaterial.WOOD) {
            baseDamage = 0F;
        }
        if(Loader.isModLoaded("battlegear2")) {
            if (isHeavyWeapon()) {
                WeaponRegistry.addTwoHanded(new ItemStack(this));
            } else {
                WeaponRegistry.addDualWeapon(new ItemStack(this));
            }
        }
    }

    public static int getParry(ItemStack item) {
        if (item.hasTagCompound()) {
            if (item.getTagCompound().hasKey("ParryAnimation")) {
                return item.getTagCompound().getInteger("ParryAnimation");
            }
        }
        return -1;
    }

    public static void setParry(ItemStack item, int i) {
        NBTTagCompound nbt = getOrCreateNBT(item);

        nbt.setInteger("ParryAnimation", i);
    }

    public static NBTTagCompound getOrCreateNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    public static boolean canPerformAbility(EntityLivingBase user, float points) {
        return tryPerformAbility(user, points, false, true, true, false);
    }

    public static boolean tryPerformAbility(EntityLivingBase user, float points) {
        return tryPerformAbility(user, points, true, true);
    }

    public static boolean tryPerformAbility(EntityLivingBase user, float points, boolean armour, boolean weapon) {
        return tryPerformAbility(user, points, true, armour, weapon, true);
    }

    public static boolean tryPerformAbility(EntityLivingBase user, float points, boolean flash, boolean armour,
                                            boolean weapon, boolean takePoints) {
        if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(user)) {
            points *= StaminaBar.getBaseDecayModifier(user, armour, weapon);
            if (StaminaBar.isStaminaAvailable(user, points, flash)) {
                if (takePoints && !user.worldObj.isRemote) {
                    applyFatigue(user, points);
                }
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void applyFatigue(EntityLivingBase user, float points) {
        applyFatigue(user, points, 50F);
    }

    public static void applyFatigue(EntityLivingBase user, float points, float pause) {
        if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(user)) {
            float stam = StaminaBar.getStaminaValue(user);
            if (stam > 0) {
                StaminaBar.modifyStaminaValue(user, -points);
            }
            StaminaBar.setIdleTime(user, pause * StaminaBar.pauseModifier);

            if (!user.worldObj.isRemote) {
                MFLogUtil.logDebug("Spent " + points + " Stamina Pts");
            }
        }
    }

    /**
     * Gets the amount more damaged added to each item
     */
    public float getDamageModifier() {
        return 0;
    }

    /**
     * Determines if the item can block/parry
     */
    public boolean canBlock() {
        return true;
    }

    /**
     * Determines if the weapon can parry
     */
    public boolean canWeaponParry() {
        return true;
    }

    /**
     * Gets the angle the weapon can parry
     */
    public float getParryAngleModifier(EntityLivingBase user) {
        return 0.75F;
    }

    /**
     * Gets the multiplier for the parry threshold
     *
     * @return
     */
    public float getParryDamageModifier(EntityLivingBase user) {
        return 1.0F;
    }

    /**
     * Determines if the weapon can do those cool ninja evades
     *
     * @return
     */
    public boolean canWeaponEvade() {
        return true;
    }

    @Override
    public ToolMaterial getMaterial() {
        return this.material;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack weapon, EntityPlayer user, List list, boolean extra) {
        super.addInformation(weapon, user, list, extra);

        if (material == ToolMaterial.WOOD) {
            return;
        }

        if (isCustom) {
            CustomToolHelper.addInformation(weapon, list);
        }

        if (this instanceof IExtendedReachWeapon || this instanceof IPenetrateWeapon
                || this instanceof IHitTimeModifier) {
            list.add("");

            if (this instanceof IPenetrateWeapon) {
                list.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted(
                        "attribute.modifier.plus." + 1, decimal_format.format(getAPDamText()),
                        StatCollector.translateToLocal("attribute.weapon.penetrateArmor")));
            }

            if (this instanceof IExtendedReachWeapon) {
                float reach = ((IExtendedReachWeapon) this).getReachModifierInBlocks(weapon);

                if (reach > 0) {
                    list.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted(
                            "attribute.modifier.plus." + 0, decimal_format.format(reach),
                            StatCollector.translateToLocal("attribute.weapon.extendedReach")));
                } else {
                    list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
                            "attribute.modifier.take." + 0, decimal_format.format(-1 * reach),
                            StatCollector.translateToLocal("attribute.weapon.extendedReach")));
                }
            }

            if (this instanceof IHitTimeModifier) {
                int hitMod = ((IHitTimeModifier) this).getHitTime(weapon, null);
                if (hitMod > 0) {
                    list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
                            "attribute.modifier.take." + 1, decimal_format.format(hitMod / 10F * 100),
                            StatCollector.translateToLocal("attribute.weapon.attackSpeed")));
                } else {
                    list.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted(
                            "attribute.modifier.plus." + 1, decimal_format.format(-(float) hitMod / 10F * 100),
                            StatCollector.translateToLocal("attribute.weapon.attackSpeed")));
                }
            }

            if (this instanceof IBackStabbable) {
                list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("attribute.weapon.backstab"));

            }
        }
    }

    protected float getAPDamText() {
        return 0F;
    }

    @Override
    public boolean sheatheOnBack(ItemStack item) {
        return isHeavyWeapon();
    }

    public boolean isHeavyWeapon() {
        return false;
    }

    @Override
    public boolean isOffhandHandDual(ItemStack off) {
        return true;
    }

    @Override
    @Optional.Method(modid = "battlegear2")
    public boolean offhandAttackEntity(mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return true;
    }

    @Override
    public boolean offhandClickAir(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return true;
    }

    @Override
    public boolean offhandClickBlock(PlayerInteractEvent event, ItemStack mainhandItem, ItemStack offhandItem) {
        return true;
    }

    @Override
    public void performPassiveEffects(Side effectiveSide, ItemStack mainhandItem, ItemStack offhandItem) {
    }

    @Override
    public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) {
        return offhand != null;
    }

    protected void addXp(EntityLivingBase user, int chance) {
        if (ConfigWeapon.xpTrain && user instanceof EntityPlayer && material == ToolMaterial.WOOD) {
            if (chance == 0 || user.getRNG().nextInt(chance) == 0) {
                ((EntityPlayer) user).addExperience(1);
            }
        }
    }

    @Override
    public void onParry(DamageSource source, EntityLivingBase user, Entity attacker, float dam) {
        ItemStack weapon = user.getHeldItem();
        int pd = getParryDamage(dam);
        if (pd > 0) {
            weapon.damageItem(pd, user);
        }

        addXp(user, 30);
    }

    /**
     * Gets the amount of durability lost when parrying
     */
    protected int getParryDamage(float dam) {
        return (int) dam;
    }

    @Override
    public float getMaxDamageParry(EntityLivingBase user, ItemStack weapon) {
        float mod = 1.0F;
        return getMeleeDamage(weapon) * getParryDamageModifier(user) * mod;
    }

    @Override
    public float getParryAngle(DamageSource source, EntityLivingBase blocker, ItemStack item) {
        float base = 30;

        if (source.isProjectile()) {
            if (!(blocker instanceof EntityPlayer)) {
                base = 0F;
            }
            base *= 0.25F;
        }
        if (blocker instanceof EntityZombie && !(blocker instanceof EntityPigZombie)) {
            base *= 2;
        }
        return base * getParryAngleModifier(blocker);
    }

    @Override
    public void onProperHit(EntityLivingBase user, ItemStack weapon, Entity hit, float dam) {
        if (ConfigWeapon.xpTrain && user instanceof EntityLivingBase && material == ToolMaterial.WOOD) {
            addXp(user, 50);
        }
    }

    protected float getKnockbackStrength() {
        return 0;
    }

    @Override
    public boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon) {
        if (material == ToolMaterial.WOOD) {
            blocker.worldObj.playSoundAtEntity(blocker, "minefantasy2:weapon.wood_parry", 1.0F, 1.0F);
            return true;
        }
        if (material == BaseMaterialMF.stone.getToolConversion()) {
            blocker.worldObj.playSoundAtEntity(blocker, "minefantasy2:weapon.wood_parry", 1.0F, 0.5F);
            return true;
        }
        return false;
    }

    @Override
    public boolean canParry(DamageSource source, EntityLivingBase blocker, ItemStack item) {
        return canWeaponParry();
    }

    @Override
    public float getBalance(EntityLivingBase user) {
        return getBalance();
    }

    public float getBalance() {
        return 0.0F;
    }

    @Override
    public float getStaminaDrainOnHit(EntityLivingBase user, ItemStack item) {
        return 2F * getStaminaMod() * getWeightModifier(item);
    }

    protected float getStaminaMod() {
        return 1.0F;
    }

    @Override
    public float getDecayMod(EntityLivingBase user, ItemStack item) {
        return getDecayModifier(user, item) * getWeightModifier(item);
    }

    /**
     * The Modifier for the weapon
     */
    public float getDecayModifier(EntityLivingBase user, ItemStack item) {
        return 1.0F;
    }

    @Override
    public float getRegenModifier(EntityLivingBase user, ItemStack item) {
        return 1.0F;
    }

    @Override
    public float getIdleModifier(EntityLivingBase user, ItemStack item) {
        return 1.0F;
    }

    protected void hurtInRange(EntityLivingBase user, double range) {
        AxisAlignedBB bb = user.boundingBox.expand(range, range, range);
        List<Entity> hurt = user.worldObj.getEntitiesWithinAABBExcludingEntity(user, bb);
        Iterator list = hurt.iterator();
        while (list.hasNext()) {
            Entity hit = (Entity) list.next();

            if (user.canEntityBeSeen(hit)) {
                TacticalManager.knockbackEntity(hit, user, 1.5F, 0.2F);
                if (StaminaBar.isSystemActive) {
                    StaminaBar.setIdleTime(user, 60);
                }
                if (hit instanceof EntityLivingBase) {
                    for (int a = 0; a < 4; a++) {
                        hit.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.redstone_block) + "_0",
                                hit.posX, hit.posY + hit.getEyeHeight(), hit.posZ, rand.nextDouble() / 2D,
                                rand.nextDouble() / 2D, rand.nextDouble() / 2D);
                    }
                    ((EntityLivingBase) hit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 0));
                }
            }
        }
    }

    @Override
    public boolean canUserParry(EntityLivingBase user) {
        return user instanceof EntityPlayer || user instanceof ISpecialCombatMob
                || (canAnyMobParry() || rand.nextFloat() < 0.20F);
    }

    protected boolean canAnyMobParry() {
        return false;
    }

    @Override
    public float getAddedKnockback(EntityLivingBase user, ItemStack item) {
        return getKnockbackStrength();
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        return 0;
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        if (implement.length > 1) {
            return getWeaponRatio((ItemStack) implement[0], (EntityLivingBase) implement[1]);
        }
        return getWeaponRatio((ItemStack) implement[0]);
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        ItemStack item = (ItemStack) implement;
        return 0F;
    }

    protected float[] getWeaponRatio(ItemStack implement) {
        return new float[]{1F, 0F, 0F};
    }

    protected float[] getWeaponRatio(ItemStack implement, EntityLivingBase user) {
        if (canCounter(user, implement) == 1) {
            return getCounterRatio();
        }
        return getWeaponRatio(implement);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (isCustom) {
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                if (MineFantasyII.isDebug() || customMat.getItem() != null) {
                    list.add(this.construct(customMat.name, "OakWood"));
                }
            }
            return;
        }

        if (this instanceof ItemKnifeMF) {
            super.getSubItems(item, tab, list);
            return;
        }
        if (this != ToolListMF.swordTraining) {
            return;
        }
        list.add(new ItemStack(ToolListMF.swordTraining));
        list.add(new ItemStack(ToolListMF.waraxeTraining));
        list.add(new ItemStack(ToolListMF.maceTraining));
        list.add(new ItemStack(ToolListMF.spearTraining));

        list.add(new ItemStack(ToolListMF.swordStone));
        list.add(new ItemStack(ToolListMF.waraxeStone));
        list.add(new ItemStack(ToolListMF.maceStone));
        list.add(new ItemStack(ToolListMF.spearStone));
    }

    private void addSet(List list, Item[] items) {
        for (Item item : items) {
            list.add(new ItemStack(item));
        }
    }

    @Override
    public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam,
                              boolean properHit) {
        if (canCounter(wielder, item) == 1) {
            initialDam *= getCounterDamage();
        }
        if (canCounter(wielder, item) == 0) {
            initialDam *= getFailCounterDamage();
        }
        return initialDam;
    }

    @Override
    public float getParryStaminaDecay(DamageSource source, ItemStack weapon) {
        return 1.0F;
    }

    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return 15;
    }

    @Override
    public int getParryModifier(ItemStack weapon, EntityLivingBase user, Entity target) {
        return 30;
    }

    @Override
    public void onPowerAttack(float dam, EntityLivingBase user, Entity target, boolean properHit) {

    }

    public boolean canCounter() {
        return true;
    }

    public float[] getCounterRatio() {
        return new float[]{0, 0, 1};
    }

    public float getCounterDamage() {
        return 0.75F;
    }

    public float getFailCounterDamage() {
        return 0.5F;
    }

    /*
     * 0 = Cannot Counter 1 = Can Counter -1 = Not Possible
     */
    private int canCounter(EntityLivingBase user, ItemStack item) {
        if (user != null && user instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) user;
            if (getParry(item) > 0) {
                if (ResearchLogic.hasInfoUnlocked(player, "counteratt")) {
                    return 1;// Can
                }
                return 0;// Cannot
            }
        }
        return -1;// N/A
    }

    public ItemWeaponMF setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/weapon/" + s + "/" + name);
        designType = s;
        isCustom = true;
        return this;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack item) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Weapon modifier", getMeleeDamage(item), 0));

        return map;
    }

    public ItemWeaponMF modifyBaseDamage(float mod) {
        this.baseDamage = Math.max(1.0F, baseDamage + mod);
        return this;
    }

    public ItemWeaponMF setBaseDamage(float baseDamage) {
        this.baseDamage = baseDamage;
        return this;
    }

    /**
     * Gets a stack-sensitive value for the melee dmg
     */
    protected float getMeleeDamage(ItemStack item) {
        return baseDamage + CustomToolHelper.getMeleeDamage(item, material.getDamageVsEntity());
    }

    protected float getWeightModifier(ItemStack stack) {
        return CustomToolHelper.getWeightModifier(stack, materialWeight);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        if (isCustom) {
            haftTex = reg.registerIcon(this.getIconString() + "_haft");
            detailTex = reg.registerIcon(this.getIconString() + "_detail");

        }
        super.registerIcons(reg);
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
    public IIcon getIcon(ItemStack stack, int pass) {
        if (isCustom && pass == 1 && haftTex != null) {
            return haftTex;
        }
        if (isCustom && pass == 2 && detailTex != null) {
            return detailTex;
        }
        return super.getIcon(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        return CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
    }

    public ItemStack construct(String main, String haft) {
        return CustomToolHelper.construct(this, main, haft);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }

    // ====================================================== CUSTOM END
    // ==============================================================\\

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }

    public ItemWeaponMF setTab(CreativeTabs tab) {
        setCreativeTab(tab);
        return this;
    }

    @Override
    public float getScale(ItemStack itemstack) {
        return 1.0F;
    }

    @Override
    public float getOffsetX(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getOffsetY(ItemStack itemstack) {
        if (this.getScale(itemstack) > 1.5F) {
            return 0.5F;
        }
        return 0;
    }

    @Override
    public float getOffsetZ(ItemStack itemstack) {
        return 0;
    }

    @Override
    public float getRotationOffset(ItemStack itemstack) {
        return 0;
    }

    @Override
    public boolean canHang(TileEntityRack rack, ItemStack item, int slot) {
        float scale = this.getScale(item);
        if (scale > 1.5F && !rack.hasRackBelow(slot)) {
            return false;
        }
        if (scale > 2.5F && !rack.hasRackAbove(slot)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSpecialRender(ItemStack item) {
        return false;
    }

    @Override
    public String getDesign(ItemStack item) {
        return designType;
    }
}
