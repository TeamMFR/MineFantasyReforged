package minefantasy.mf2.item.armour;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.config.ConfigClient;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemClothingMF extends ItemArmourMF {
    @SideOnly(Side.CLIENT)
    private Object model;

    /**
     * @param piece 0head, 1body, 2legs, 3boots
     */
    public ItemClothingMF(String name, BaseMaterialMF material, int piece, String tex, int rarity) {
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
