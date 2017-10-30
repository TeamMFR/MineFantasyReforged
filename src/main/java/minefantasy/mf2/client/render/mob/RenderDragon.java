package minefantasy.mf2.client.render.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.entity.mob.EntityDragon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDragon extends RenderLiving {
    private static ModelBase dragonModel = new ModelDragon();
    private static ModelBase venomModel = new ModelVenomDragon();
    private static ModelBase frostModel = new ModelFrostDragon();
    private static ModelBase ashModel = new ModelAshDragon();

    public RenderDragon(float shadow) {
        super(dragonModel, shadow);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntityDragon) {
            return TextureHelperMF
                    .getResource("textures/models/monster/dragon/" + ((EntityDragon) entity).getTexture() + ".png");
        }
        return TextureHelperMF.getResource("textures/models/monster/dragon/dragonGreen.png");
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        this.preRenderCallback((EntityDragon) entity, f);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float f, float f1) {
        String breed = ((EntityDragon) entity).getType().breedName;
        this.mainModel = breed.equalsIgnoreCase("ash") ? ashModel
                : breed.equalsIgnoreCase("white") ? frostModel
                : breed.equalsIgnoreCase("green") ? venomModel : dragonModel;
        super.doRender(entity, x, y, z, f, 1);
        BossStatus.setBossStatus((EntityDragon) entity, ((EntityDragon) entity).getType().tier == 4);
    }

    protected void preRenderCallback(EntityDragon mob, float f) {
        float scale = mob.getScale();
        GL11.glScalef(scale, scale, scale);
    }
}
