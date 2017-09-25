package minefantasy.mf2.api.armour;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.lwjgl.opengl.GL11;

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
     *         to a horse by this armor item.
     */
    public int getArmorValue (EntityHorse horse, ItemStack stack) {
        return 0;
    }

    /**
     * Called every time an EntityHorse updates while wearing this piece of armor. Allows for
     * armor to alter the behavior and abilities of a horse.
     *
     * @param horse: An instance of the horse which is currently wearing this piece of armor.
     * @param stack: An instance of the ItemStack currently in the horse's armor slot.
     */
    public void onHorseUpdate (EntityHorse horse, ItemStack stack) {}

    /**
     * Called when a horse wearing this piece of armor has been hurt.
     *
     * @param horse: An instance of the horse which is currently wearing this piece of armor.
     * @param stack: An instance of the ItemStack currently in the horse's armor slot.
     * @param source: The DamageSource which caused the horse to be hurt.
     * @param damage: The amount of damage that the horse received.
     * @return boolean: If true, the horse will not be hurt and the event will be canceled.
     */
    public boolean onHorseDamaged (EntityHorse horse, ItemStack stack, DamageSource source, float damage) {
        stack.damageItem((int)(damage / 5F) + 1, horse);
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
     *         EG: testmod:textures/entity/horse/armor/horse_armor_test.png
     */
    @SideOnly(Side.CLIENT)
    public String getArmorTexture (EntityHorse horse, ItemStack stack) {
        return armorTexture;
    }

    /**
     * Allows for special rendering code to be done, when a horse is rendered with this piece
     * of armor on.
     *
     * @param horse: An instance of the EntityHorse that is wearing the armor.
     * @param armorStack: An instance of the ItemStack which is in the armor slot.
     * @param event: Access to the RenderLivingEvent being called.
     * @param flag: A convenient flag which can be used to easily switch between the different
     *            render events. 0:pre 1:post 2:special-pre 3:special-post
     */

    /*
                this.bindEntityTexture(p_77036_1_);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            Item customArmor = p_77036_1_.getDataWatcher().getWatchableObjectItemStack(23).getItem();
            if(customArmor != null && customArmor instanceof ItemHorseArmorMFBase) {
                CustomMaterial material = CustomMaterial.getMaterial("Ignotumite");
                if (material != null) {
                    this.bindTexture(TextureHelperMF.getResource("textures/models/animal/horse/armor/standard_plate_layer_1.png"));
                    GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);
                    this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                }
            }
     */

    @SideOnly(Side.CLIENT)
    public void onHorseRendered (EntityHorse horse, ItemStack armorStack, RenderLivingEvent event, byte flag) {
    }

}
