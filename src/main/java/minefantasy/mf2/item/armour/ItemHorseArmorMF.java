package minefantasy.mf2.item.armour;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.ItemHorseArmorMFBase;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.client.render.mob.RenderVanillaHorse;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.RenderLivingEvent;
import sun.java2d.pipe.RenderingEngine;

public class ItemHorseArmorMF extends ItemHorseArmorMFBase {

    protected BaseMaterialMF baseMaterial;
    private String specialDesign = "standard";
    private String name;
    private ArmourDesign armourDesign;
    private int itemRarity;
    private String armorTexture;
    private IIcon saddleClothLayer;
    private IIcon overLayer;
    private String texturePath = "minefantasy2:animal/horse/armor/horse_armor";

    public ItemHorseArmorMF(String name, BaseMaterialMF material, ArmourDesign design, String texture, int rarity) {
        super(name, material.getArmourConversion(), design, texture);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setCreativeTab(CreativeTabMF.tabArmour);
        this.setTextureName(texturePath + "_main");
        baseMaterial = material;
        armourDesign = design;
        itemRarity = rarity;
        armorTexture = texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata) {
        return 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        overLayer = register.registerIcon(texturePath + "_overlay");
        saddleClothLayer = register.registerIcon(texturePath + "_saddlecloth");
        super.registerIcons(register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 1 && overLayer != null) {
            return overLayer;
        }
        if (pass == 2 && saddleClothLayer != null) {
            return saddleClothLayer;
        }
        return super.getIcon(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        if (layer == 0) {
            CustomMaterial base = CustomToolHelper.getCustomPrimaryMaterial(item);
            base = CustomMaterial.getMaterial("Ignotumite");
            if (base != null) {
                return base.getColourInt();
            }
        }
        return super.getColorFromItemStack(item, layer);
    }

    @Override
    public String getArmorTexture(EntityHorse horse, ItemStack stack) {
        return "minefantasy2:textures/models/animal/horse/armor/standard_plate_layer_1.png";
    }
}
