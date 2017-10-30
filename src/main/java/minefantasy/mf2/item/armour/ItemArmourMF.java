package minefantasy.mf2.item.armour;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.IElementalResistance;
import minefantasy.mf2.api.armour.ItemArmourMFBase;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.config.ConfigClient;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemArmourMF extends ItemArmourMFBase implements IElementalResistance {
    @SideOnly(Side.CLIENT)
    private static Object fullplate;
    protected BaseMaterialMF baseMaterial;
    private int itemRarity;
    @SideOnly(Side.CLIENT)
    private IIcon clothIcon;
    @SideOnly(Side.CLIENT)
    private IIcon armourIcon;

    public ItemArmourMF(String name, BaseMaterialMF material, ArmourDesign AD, int slot, String tex, int rarity) {
        super(name, material.getArmourConversion(), AD, slot, tex);
        baseMaterial = material;
        this.setTextureName("minefantasy2:apparel/" + AD.getName().toLowerCase() + "/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        setCreativeTab(CreativeTabMF.tabArmour);

        itemRarity = rarity;
    }

    public ItemArmourMF(String name, BaseMaterialMF material, ArmourDesign AD, int slot, String tex, int rarity,
                        float customBulk) {
        this(name, material, AD, slot, tex, rarity);
        this.suitBulk = customBulk;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        if (source.isMagicDamage() && this.getMagicResistance(stack, source) > 100F) {
            return;
        }
        if (source.isFireDamage() && this.getFireResistance(stack, source) > 100F) {
            return;
        }
        if (ArmourListMF.isUnbreakable(baseMaterial, entity)) {
            return;
        }
        initArmourDamage(entity, stack, damage);
    }

    @Override
    public float getMagicResistance(ItemStack item, DamageSource source) {
        CustomMaterial custom = getCustomMaterial(item);
        if (custom != null) {
            return custom.resistance;
        }
        return material.magicResistanceModifier;
    }

    @Override
    public float getFireResistance(ItemStack item, DamageSource source) {
        CustomMaterial custom = getCustomMaterial(item);
        if (custom != null) {
            MFLogUtil.logDebug("Fire Resist: " + custom.getFireResistance());
            return custom.getFireResistance() * design.getRating();
        }
        return material.fireResistanceModifier;
    }

    @Override
    public float getArrowDeflection(ItemStack item, DamageSource source) {
        return (design == ArmourDesign.MAIL || design == ArmourDesign.PLATE) ? 0.5F : 0.0F;
    }

    @Override
    public float getBaseResistance(ItemStack item, DamageSource source) {
        if (baseMaterial == BaseMaterialMF.getMaterial("ender") && source.getSourceOfDamage() != null
                && source.getSourceOfDamage() instanceof EntityEnderPearl) {
            return 100F;
        }
        return 0;
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
        if (design == ArmourDesign.PLATE) {
            lvl++;
        }
        if (lvl >= ToolListMF.rarity.length) {
            lvl = ToolListMF.rarity.length - 1;
        }
        return ToolListMF.rarity[lvl];
    }

    @Override
    protected boolean isUnbreakable() {
        return baseMaterial == BaseMaterialMF.getMaterial("ender");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (this != ArmourListMF.leather[0]) {
            return;
        }
        list.add(new ItemStack(ArmourListMF.leatherapron));
        addSet(list, ArmourListMF.leather);
    }

    private void addSet(List list, Item[] items) {
        for (Item item : items) {
            list.add(new ItemStack(item));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        if (!canColour()) {
            super.registerIcons(reg);
        } else {
            String baseLayer = isMetal() ? "" : "_overlay";
            this.armourIcon = reg.registerIcon(this.getIconString() + baseLayer);
            String dyeLayer = isMetal() ? "_cloth" : "";
            this.clothIcon = reg.registerIcon(this.getIconString() + dyeLayer);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int dam, int layer) {
        if (!canColour()) {
            return super.getIconFromDamage(dam);
        }
        return layer == 1 ? armourIcon : clothIcon;
    }

    public boolean canColour() {
        return design == ArmourDesign.PADDING || design == ArmourDesign.LEATHER || design == ArmourDesign.CLOTH
                || isMetal();
    }

    public boolean isMetal() {
        return design == ArmourDesign.MAIL || design == ArmourDesign.PLATE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return canColour();
    }

    /**
     * Return whether the specified armor ItemStack has a color.
     */
    @Override
    public boolean hasColor(ItemStack item) {
        return !canColour() ? false
                : (!item.hasTagCompound() ? false
                : (!item.getTagCompound().hasKey("display", 10) ? false
                : item.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }

    /**
     * The Colour of the armour when not dyed
     */
    public int getBaseColour(ItemStack item) {
        if (!this.isCustom()) {
            return 10511680;
        }
        return -1;
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    @Override
    public int getColor(ItemStack item) {
        int metal = getBaseColour(item);
        if (!canColour()) {
            return metal;
        } else {
            NBTTagCompound nbttagcompound = item.getTagCompound();

            if (nbttagcompound == null) {
                return metal;
            } else {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
                return nbttagcompound1 == null ? metal
                        : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : metal);
            }
        }
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    @Override
    public void removeColor(ItemStack item) {
        if (canColour()) {
            NBTTagCompound nbttagcompound = item.getTagCompound();

            if (nbttagcompound != null) {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1.hasKey("color")) {
                    nbttagcompound1.removeTag("color");
                }
            }
        }
    }

    @Override
    public void func_82813_b(ItemStack item, int colour) {
        if (!canColour()) {
            return;
        } else {
            NBTTagCompound nbttagcompound = item.getTagCompound();

            if (nbttagcompound == null) {
                nbttagcompound = new NBTTagCompound();
                item.setTagCompound(nbttagcompound);
            }

            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (!nbttagcompound.hasKey("display", 10)) {
                nbttagcompound.setTag("display", nbttagcompound1);
            }

            nbttagcompound1.setInteger("color", colour);
        }
    }

    @Override
    public float getMagicAC(float AC, DamageSource source, double damage, EntityLivingBase player) {
        if (damage > 1 && material.isMythic) {
            return AC;
        }
        return 0F;
    }

    @Override
    public String getSuitWeigthType(ItemStack item) {
        if (design == ArmourDesign.MAIL) {
            return "medium";
        }
        if (design == ArmourDesign.PLATE) {
            return "heavy";
        }
        return super.getSuitWeigthType(item);
    }

    public ItemStack construct(String plate) {
        ItemStack item = new ItemStack(this);
        CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, plate.toLowerCase());
        return item;
    }

    /**
     * A bit of the new system, gets custom materials for armour Only used on
     * cogwork armour though
     */
    public CustomMaterial getCustomMaterial(ItemStack item) {
        CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
        if (material != null) {
            return material;
        }
        return null;
    }

    @Override
    public float getDRValue(EntityLivingBase user, ItemStack armour, DamageSource src) {
        float DR = getProtectionRatio(armour) * scalePiece();

        if (ArmourCalculator.advancedDamageTypes && !user.worldObj.isRemote) {
            DR = ArmourCalculator.adjustACForDamage(src, DR, getProtectiveTrait(armour, 0),
                    getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
        }
        MFLogUtil.logDebug(">>>>DR<<<< = " + DR);
        return DR;
    }

    @Override
    protected float getProtectionRatio(ItemStack item) {
        CustomMaterial main = getCustomMaterial(item);
        if (main != null) {
            return main.hardness * design.getRating();
        }
        return super.getProtectionRatio(item);
    }

    /**
     * Gets the modifier for a certain damage type (Cutting, Blunt, Piercing)
     */
    @Override
    public float getProtectiveTrait(ItemStack item, int dtype) {
        float value = super.getProtectiveTrait(item, dtype);
        float cutting = 1.0F;
        float piercing = 1.0F;
        float blunt = 1.0F;

        CustomMaterial material = getCustomMaterial(item);
        if (material != null) {
            cutting = material.getArmourProtection(0);
            blunt = material.getArmourProtection(1);
            piercing = material.getArmourProtection(2);
        }

        if (dtype == 0)// Cutting
        {
            value *= cutting;
        }
        if (dtype == 2)// Piercing
        {
            value *= piercing;
        }
        if (dtype == 1)// Blunt
        {
            value *= blunt;
        }
        return value;
    }

    public float getResistanceModifier(ItemStack item, String hazard) {
        CustomMaterial custom = getCustomMaterial(item);
        if (custom != null) {
            return custom.resistance;
        }
        return super.getResistanceModifier(item, hazard);
    }

    public boolean isCustom() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean full) {
        CustomToolHelper.addInformation(item, list);
        float mass = getPieceWeight(item, armorType);

        list.add(CustomMaterial.getWeightString(mass));
        super.addInformation(item, user, list, full);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (entity instanceof EntityPlayer && armorType < 2 && design == ArmourDesign.FIELDPLATE
                && ConfigClient.customModel) {
            return getArmourTextureName(stack, entity, slot, type) + "_S.png";
        }
        return getArmourTextureName(stack, entity, slot, type) + ".png";
    }

    public String getArmourTextureName(ItemStack stack, Entity entity, int slot, String type) {
        String tex = "minefantasy2:textures/models/armour/" + design.getName().toLowerCase() + "/" + texture;
        if (type == null && canColour())// bottom layer
        {
            return tex + "_cloth";
        }
        return tex;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
                                                               int armorSlot) {
        if (!(entityLiving instanceof EntityPlayer) || armorType >= 2 || !ConfigClient.customModel) {
            return super.getArmorModel(entityLiving, itemStack, armorSlot);
        }

        if (fullplate == null) {
            fullplate = new minefantasy.mf2.client.render.armour.ModelFullplate(1.0F);
        }
        net.minecraft.client.model.ModelBiped model = this.design == ArmourDesign.FIELDPLATE
                ? (net.minecraft.client.model.ModelBiped) fullplate
                : null;
        if (model == null) {
            return super.getArmorModel(entityLiving, itemStack, armorSlot);
        }
        if (entityLiving != null) {
            model.heldItemRight = entityLiving.getHeldItem() != null ? 1 : 0;
            model.aimedBow = false;
            if (entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLiving;
                ItemStack held = player.getHeldItem();
                if (held != null && player.getItemInUseCount() > 0) {
                    EnumAction enumaction = held.getItemUseAction();

                    if (enumaction == EnumAction.block) {
                        model.heldItemRight = 3;
                    } else if (enumaction == EnumAction.bow) {
                        model.aimedBow = true;
                    }
                }
            }
        }
        model.bipedHead.showModel = (this.armorType == 0);
        model.bipedHeadwear.showModel = (this.armorType == 0);

        model.bipedCloak.showModel = (this.armorType == 1);
        model.bipedBody.showModel = (this.armorType == 1);
        model.bipedLeftArm.showModel = (this.armorType == 1);
        model.bipedRightArm.showModel = (this.armorType == 1);
        return model;
    }
}
