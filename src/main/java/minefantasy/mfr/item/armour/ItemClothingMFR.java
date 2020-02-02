package minefantasy.mfr.item.armour;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemClothingMFR extends ItemArmourMFR {
    @SideOnly(Side.CLIENT)
    private Object model;

    /**
     * @param piece 0head, 1body, 2legs, 3boots
     */
    public ItemClothingMFR(String name, BaseMaterialMFR material, EntityEquipmentSlot piece, String tex, int rarity) {
        super(name, material, ArmourDesign.CLOTH, piece, tex, rarity);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        String tex = "minefantasy2:textures/models/armour/" + design.getName().toLowerCase() + "/" + texture;
        if (type == null && canColour())// bottom layer
        {
            return tex + "_cloth.png";
        }
        return tex + ".png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
                                                               int armorSlot) {

        if (!ConfigClient.customModel) {
            return super.getArmorModel(entityLiving, itemStack, armorSlot);
        }
        if (model == null) {
            model = new net.minecraft.client.model.ModelBiped(0.25F);
        }

        if (entityLiving != null) {
            ((net.minecraft.client.model.ModelBiped) model).heldItemRight = entityLiving.getHeldItem() != null ? 1 : 0;
        }
        return (net.minecraft.client.model.ModelBiped) model;
    }

}
