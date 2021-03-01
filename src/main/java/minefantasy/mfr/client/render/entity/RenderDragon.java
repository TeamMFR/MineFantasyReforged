package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.client.model.entity.ModelAshDragon;
import minefantasy.mfr.client.model.entity.ModelDragon;
import minefantasy.mfr.client.model.entity.ModelFrostDragon;
import minefantasy.mfr.client.model.entity.ModelVenomDragon;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderDragon extends RenderLiving<EntityDragon> {
	private static ModelBase dragonModel = new ModelDragon();
	private static ModelBase venomModel = new ModelVenomDragon();
	private static ModelBase frostModel = new ModelFrostDragon();
	private static ModelBase ashModel = new ModelAshDragon();

	public RenderDragon(RenderManager renderManager) {
		super(renderManager, dragonModel, 2F);
	}

	public void doRender(EntityDragon entity, double x, double y, double z, float f, float f1) {
		String breed = entity.getType().breedName;
		this.mainModel = breed.equalsIgnoreCase("ash") ? ashModel : breed.equalsIgnoreCase("white") ? frostModel : breed.equalsIgnoreCase("green") ? venomModel : dragonModel;
		super.doRender(entity, x, y, z, f, 1);
	}

	@Override
	protected void preRenderCallback(EntityDragon mob, float f) {
		super.preRenderCallback(mob, f);
		float scale = mob.getScale();
		GlStateManager.scale(scale, scale, scale);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityDragon entity) {
		return TextureHelperMFR.getResource("textures/models/monster/dragon/" + entity.getType().tex + ".png");
	}
}
