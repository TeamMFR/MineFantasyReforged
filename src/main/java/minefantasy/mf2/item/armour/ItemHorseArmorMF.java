package minefantasy.mf2.item.armour;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.ItemHorseArmorMFBase;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderLivingEvent;


public class ItemHorseArmorMF extends ItemHorseArmorMFBase {

    private String name;
    protected BaseMaterialMF baseMaterial;
    private ArmourDesign armourDesign;
    private String itemTexture;
    private int itemRarity;


    public ItemHorseArmorMF(String name, BaseMaterialMF material, ArmourDesign design, String texture, int rarity) {
        this.name = name;
        baseMaterial = material;
        armourDesign = design;
        itemTexture = texture;
        itemRarity = rarity;
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        setCreativeTab(CreativeTabMF.tabArmour);
    }

    @Override
    public int getArmorValue(EntityHorse horse, ItemStack stack) {
        return 0;
    }

    @Override
    public void onHorseUpdate(EntityHorse horse, ItemStack stack) {
        System.out.println("UPDATE MY HORSE!");
    }

    @Override
    public boolean onHorseDamaged(EntityHorse horse, ItemStack stack, DamageSource source, float damage) {
        damage = 0F;
        return false;
    }

    @Override
    public String getArmorTexture(EntityHorse horse, ItemStack stack) {
        return itemTexture;
    }

    @Override
    public void onHorseRendered(EntityHorse horse, ItemStack armorStack, RenderLivingEvent event, byte flag) {

    }
}
