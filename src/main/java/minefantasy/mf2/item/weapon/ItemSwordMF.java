package minefantasy.mf2.item.weapon;

import minefantasy.mf2.api.weapon.WeaponClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author Anonymous Productions
 */
public class ItemSwordMF extends ItemWeaponMF {
    /**
     * Swords are the average weapon. They do regular damage and are extremely
     * effective at parrying balancing both attack and defense
     * <p>
     * These are for the average player
     */
    public ItemSwordMF(String name, ToolMaterial material, int rarity, float weight) {
        super(material, name, rarity, weight);
    }

    @Override
    public boolean canBlock() {
        return true;
    }

    /**
     * Determines if the weapon can parry
     */
    @Override
    public boolean canWeaponParry() {
        return true;
    }

    @Override
    protected int getParryDamage(float dam) {
        return 1;
    }

    /**
     * Gets the angle the weapon can parry
     */
    @Override
    public float getParryAngleModifier(EntityLivingBase user) {
        return 1.0F;
    }

    /**
     * Gets the multiplier for the parry threshold
     *
     * @return
     */
    @Override
    public float getParryDamageModifier(EntityLivingBase user) {
        return user instanceof EntityPlayer ? 2.0F : 1.5F;
    }

    /**
     * Determines if the weapon can do those cool ninja evades
     *
     * @return
     */
    @Override
    public boolean canWeaponEvade() {
        return true;
    }

    @Override
    protected boolean canAnyMobParry() {
        return true;
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        return super.modifyHitTime(user, item) + speedModSword;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return swordParryTime;
    }

    @Override
    protected float getStaminaMod() {
        return swordStaminaCost;
    }

    @Override
    public WeaponClass getWeaponClass() {
        return WeaponClass.BLADE;
    }
}
