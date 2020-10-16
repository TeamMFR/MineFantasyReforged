package minefantasy.mfr.item.weapon;

import minefantasy.mfr.api.weapon.WeaponClass;
import minefantasy.mfr.client.render.item.RenderBigTool;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;

/**
 * @author Anonymous Productions
 */
public class ItemGreatsword extends ItemHeavyWeapon {

    /**
     * Greatswords are heavy counterparts to swords, with added damage, knockback
     * and parrying arc
     */
    public ItemGreatsword(String name, Item.ToolMaterial material, int rarity, float weight) {
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
    public boolean canWeaponEvade() {
        return true;
    }

    @Override
    protected int getParryDamage(float dam) {
        return 1;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(EntityLivingBase user) {
        return 12;
    }

    /**
     * Gets the angle the weapon can parry
     */
    @Override
    public float getParryAngleModifier(EntityLivingBase user) {
        return 1.5F;
    }

    /**
     * Gets the multiplier for the parry threshold
     *
     * @return
     */
    @Override
    public float getParryDamageModifier(EntityLivingBase user) {
        return user instanceof EntityPlayer ? 3.0F : 1.5F;
    }

    @Override
    public boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon) {
        blocker.world.playSound(blocker.posX, blocker.posY, blocker.posZ, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.AMBIENT, 1.0F, 0.75F, true );
        return true;
    }

    @Override
    public float getBalance() {
        return 0.5F;
    }

    @Override
    protected boolean canAnyMobParry() {
        return true;
    }

    @Override
    public float getAttackSpeed(ItemStack item) {
        return super.getAttackSpeed(item) + -1.5F;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return swordParryTime + heavyParryTime;
    }

    @Override
    protected float getStaminaMod() {
        return heavyStaminaCost * swordStaminaCost;
    }

    @Override
    public WeaponClass getWeaponClass() {
        return WeaponClass.BLADE;
    }

    @Override
    public boolean canCounter() {
        return true;
    }

    @Override
    public float[] getCounterRatio() {
        return crushingDamage;
    }

    @Override
    public float getCounterDamage() {
        return 0.5F;
    }

    @Override
    public void registerClient() {
        ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "normal");
        ModelLoaderHelper.registerWrappedItemModel(this, new RenderBigTool(() -> modelLocation, 2F,-0.27F), modelLocation);
    }
}
