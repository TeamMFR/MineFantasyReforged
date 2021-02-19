package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.client.model.entity.ModelCogwork;
import minefantasy.mfr.entity.EntityCogwork;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPowerArmour extends RenderLivingBase<EntityCogwork> {
	private static ModelBiped cogworkSuit = new ModelCogwork();

	public RenderPowerArmour(RenderManager renderManager) {
		super(renderManager, cogworkSuit, 1F);
	}

	@Override
	public void doRender(EntityCogwork entity, double x, double y, double z, float f, float f1) {
		super.doRender(entity, x, y, z, f, 1);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCogwork entity) {
		return TextureHelperMFR.getResource("textures/models/armour/cogwork/cogwork_suit_" + "base" + ".png");
	}
}