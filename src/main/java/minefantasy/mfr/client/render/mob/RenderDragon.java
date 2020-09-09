package minefantasy.mfr.client.render.mob;

import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.entity.mob.EntityDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderDragon extends RenderLiving<EntityDragon> {
    private static ModelBase dragonModel = new ModelDragon();
    private static ModelBase venomModel = new ModelVenomDragon();
    private static ModelBase frostModel = new ModelFrostDragon();
    private static ModelBase ashModel = new ModelAshDragon();

    public RenderDragon(float shadow) {
        super(Minecraft.getMinecraft().getRenderManager(), dragonModel, shadow);
    }

    public void doRender(EntityDragon entity, double x, double y, double z, float f, float f1) {
        String breed = entity.getType().breedName;
        this.mainModel = breed.equalsIgnoreCase("ash") ? ashModel : breed.equalsIgnoreCase("white") ? frostModel : breed.equalsIgnoreCase("green") ? venomModel : dragonModel;
        super.doRender(entity, x, y, z, f, 1);
    }

    @Override
    protected void preRenderCallback(EntityDragon mob, float f) {
        float scale = mob.getScale();
        GL11.glScalef(scale, scale, scale);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityDragon entity) {
        return TextureHelperMFR.getResource("textures/models/monster/dragon/" + ((EntityDragon) entity).getTexture() + ".png");
    }
}
