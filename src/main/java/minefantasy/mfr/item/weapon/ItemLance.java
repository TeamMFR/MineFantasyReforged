package minefantasy.mfr.item.weapon;

import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.client.render.item.RenderLance;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemLance extends ItemSpear {
    /**
     */
    public ItemLance(String name, Item.ToolMaterial material, int rarity, float weight) {
        super(name, material, rarity, weight);
        setMaxDamage(getMaxDamage(new ItemStack(this)) * 2);
    }

    @Override
    public void addInformation(ItemStack weapon, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(weapon, world, list, flag);

        list.add(TextFormatting.BLUE + I18n.format("attribute.modifier.plus." + 0,
                decimal_format.format(getJoustDamage(weapon)),
                I18n.format("attribute.weapon.joustDam")));
    }

    @Override
    public float getReachModifierInBlocks(ItemStack stack) {
        return 3.0F;
    }

    @Override
    protected float[] getWeaponRatio(ItemStack implement) {
        return hvyPiercingDamage;
    }

    @Override
    public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam, boolean properHit) {
        float dam = super.modifyDamage(item, wielder, hit, initialDam, properHit);
        if (hit instanceof EntityLivingBase) {
            return joust((EntityLivingBase) hit, wielder, dam);
        }
        return dam;
    }

    @Override
    public boolean canWeaponParry() {
        return false;
    }

    // Higher stamina means more precice hits: Full stamina hits are perfect
    @Override
    public float getBalance(EntityLivingBase user) {
        if (StaminaBar.isSystemActive) {
            return 0.0F + (2 * (1 - StaminaBar.getStaminaDecimal(user)));
        }
        return 0.0F;
    }

    @Override
    protected float getKnockbackStrength() {
        return 5.0F;
    }

    @Override
    protected float getStaminaMod() {
        return 5.0F;
    }

    @Override
    public boolean canBlock() {
        return false;
    }

    public float joust(EntityLivingBase target, EntityLivingBase attacker, float dam) {
        float speedMod = 20F;
        float speedCap = 5F;

        if (attacker.isRiding()) {
            Entity mount = attacker.getRidingEntity();
            if (mount != null) {
                float speed = (float) Math.hypot(mount.motionX, mount.motionZ) * speedMod;
                if (speed > speedCap)
                    speed = speedCap;

                dam += getJoustDamage(target.getHeldItemMainhand()) / speedCap * speed;

                if (attacker instanceof EntityPlayer) {
                    ((EntityPlayer) attacker).onCriticalHit(target);
                }

                if (target.isRiding()) {
                    target.dismountRidingEntity();
                }
            }
        }
        return dam;
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        return super.modifyHitTime(user, item) + speedModSpear * 2;
    }

    @Override
    protected float getMeleeDamage(ItemStack item) {
        return 2F;
    }

    protected float getJoustDamage(ItemStack item) {
        return super.getMeleeDamage(item) * 2.5F;
    }

    @Override
    public void registerClient() {
        ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "normal");
        ModelLoaderHelper.registerWrappedItemModel(this, new RenderLance(() -> modelLocation), modelLocation);
    }
}
