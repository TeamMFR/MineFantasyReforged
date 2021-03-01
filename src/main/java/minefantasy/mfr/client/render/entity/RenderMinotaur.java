package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.client.model.entity.ModelMinotaur;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMinotaur extends RenderBiped<EntityMinotaur> {

	private static ModelBiped minotaurModel = new ModelMinotaur();

	public RenderMinotaur(RenderManager renderManager) {
		super(renderManager, minotaurModel, 1F);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerBipedArmor(this));
	}

	@Override
	public void doRender(EntityMinotaur entity, double x, double y, double z, float f, float f1) {
		super.doRender(entity, x, y, z, f, 1);
	}


	@Override
	protected ResourceLocation getEntityTexture(EntityMinotaur entity) {
		return TextureHelperMFR.getResource("textures/models/monster/minotaur/" + entity.getTexture() + ".png");
	}
}
