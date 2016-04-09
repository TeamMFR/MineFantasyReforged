package minefantasy.mf2.item.armour;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.config.ConfigClient;
import minefantasy.mf2.material.BaseMaterialMF;

public class ItemClothingMF extends ItemArmourMF
{
	/**
	 * @param piece 0head, 1body, 2legs, 3boots
	 */
	public ItemClothingMF(String name, BaseMaterialMF material, int piece, String tex, int rarity)
	{
		super(name, material, ArmourDesign.CLOTH, piece, tex, rarity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
		if(!ConfigClient.customModel)
		{
			return super.getArmorModel(entityLiving, itemStack, armorSlot);
		}
		
		ModelBiped model = new ModelBiped(0.25F);
		if(entityLiving != null)
		{
			model.heldItemRight = entityLiving.getHeldItem() != null ? 1 : 0;
		}
        return model;
    }
}
