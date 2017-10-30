package minefantasy.mf2.api.armour;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemHorseArmorMFBase extends Item {

    public ArmourMaterialMF armorMaterial;
    public ArmourDesign armorDesign;
    public String armorTexture;

    public ItemHorseArmorMFBase(String name, ArmourMaterialMF material, ArmourDesign design, String texture) {
        this.setUnlocalizedName(name);
        armorMaterial = material;
        armorDesign = design;
        armorTexture = texture;

        float baseDura = material.durability * design.getDurability() / 2F;
        this.setMaxDamage((int) baseDura);
    }

    /**
     * Used to calculate the amount of protection this item should provide the horse with.
     *
     * @param horse: An instance of the horse which is currently wearing this piece of armor.
     * @param stack: An instance of the ItemStack currently in the horse's armor slot.
     * @return int: An integer based representation of the armor points which should be given
     * to a horse by this armor item.
     */
    public int getArmorValue(EntityHorse horse, ItemStack stack) {
        return 0;
    }

    /**
     * Called every time an EntityHorse updates while wearing this piece of armor. Allows for
     * armor to alter the behavior and abilities of a horse.
     *
     * @param horse: An instance of the horse which is currently wearing this piece of armor.
     * @param stack: An instance of the ItemStack currently in the horse's armor slot.
     */
    public void onHorseUpdate(EntityHorse horse, ItemStack stack) {
    }

    /**
     * Called when a horse wearing this piece of armor has been hurt.
     *
     * @param horse:  An instance of the horse which is currently wearing this piece of armor.
     * @param stack:  An instance of the ItemStack currently in the horse's armor slot.
     * @param source: The DamageSource which caused the horse to be hurt.
     * @param damage: The amount of damage that the horse received.
     * @return boolean: If true, the horse will not be hurt and the event will be canceled.
     */
    public boolean onHorseDamaged(EntityHorse horse, ItemStack stack, DamageSource source, float damage) {
        stack.damageItem((int) (damage / 5F) + 1, horse);
        return true;
    }

    /**
     * Used to provide a resource path for the armor's texture. Textures follow the same rules
     * and layouts as the vanilla horse armor textures. The domain for the texture can be
     * switched from minecraft to one of your chosing by preceding the path with 'domain:'
     *
     * @param horse: An instance of the horse which is currently wearing this piece of armor.
     * @param stack: An instance of the ItemStack currently in the horse's armor slot.
     * @return String: A string based representation of the armor texture resource location.
     * EG: testmod:textures/entity/horse/armor/horse_armor_test.png
     */
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(EntityHorse horse, ItemStack stack) {
        return armorTexture;
    }
}
