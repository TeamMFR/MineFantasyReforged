package minefantasy.mf2.client.render.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.armour.ItemHorseArmorMFBase;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.material.CustomMaterial;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.Item;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderVanillaHorse extends RenderHorse {

    public RenderVanillaHorse(ModelBase modelBase, float p_i1256_2_) {
        super(modelBase, p_i1256_2_);
        this.setRenderPassModel(modelBase);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase horse, int pass, float f) {
        return shouldRenderPass((EntityHorse) horse, pass, f);
    }

    private int shouldRenderPass(EntityHorse horse, int pass, float f) {
        if (pass == 2) {
            Item armor = horse.getDataWatcher().getWatchableObjectItemStack(23).getItem();
            if (armor instanceof ItemHorseArmorMFBase) {
                CustomMaterial material = CustomMaterial.getMaterial("Ignotumite");
                if (material != null) {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    this.bindTexture(TextureHelperMF.getResource("textures/models/animal/horse/armor/standard_plate_layer_1.png"));
                    GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }
            }
            return 1;
        }
        return -1;
    }
}
