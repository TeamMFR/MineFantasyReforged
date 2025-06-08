package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.client.model.entity.ModelCogwork;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.registry.CustomMaterialRegistry;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderPowerArmour extends RenderLivingBase<EntityLivingBase> {
	private static ModelBiped cogworkSuit = new ModelCogwork();

	public RenderPowerArmour(RenderManager renderManager) {
		super(renderManager, cogworkSuit, 1F);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(int layer) {
		String tex = layer == 0 ? "base" : "plating";
		return TextureHelperMFR.getResource("textures/models/armour/cogwork/cogwork_suit_" + tex + ".png");
	}

	protected void bindEntityTexture(int layer) {
		this.bindTexture(this.getEntityTexture(layer));
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityLivingBase entityCogwork) {
		return this.getEntityTexture(0);
	}



	@Override
	public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {

		EntityLivingBase user = entity;

		if (entity.getControllingPassenger() != null && entity.getControllingPassenger() instanceof EntityLivingBase) {
			user = (EntityLivingBase) entity.getControllingPassenger();
		}
		this.doRenderCogwork(user, x, y, z, entityYaw, partialTicks);
	}

	public void doRenderCogwork(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<>(entity, this, partialTicks, x, y, z)))
			return;

		EntityCogwork suit = this.getCogwork(entity);

		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
		boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
		this.mainModel.isRiding = shouldSit;
		this.mainModel.isChild = entity.isChild();

		try {
			float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
			float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
			float f2 = f1 - f;

			if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entity.getRidingEntity();
				f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
				f2 = f1 - f;
				float f3 = MathHelper.wrapDegrees(f2);

				if (f3 < -85.0F) {
					f3 = -85.0F;
				}

				if (f3 >= 85.0F) {
					f3 = 85.0F;
				}

				f = f1 - f3;

				if (f3 * f3 > 2500.0F) {
					f += f3 * 0.2F;
				}

				f2 = f1 - f;
			}

			float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
			this.renderLivingAt(entity, x, y, z);
			float ageInTicks = this.handleRotationFloat(entity, partialTicks);
			this.applyRotations(entity, ageInTicks, f, partialTicks);
			float f4 = this.prepareScale(entity, partialTicks);
			float limbSwingAmount = 0F;
			float limbSwing = 0F;

			if (!entity.isRiding() || (entity.getRidingEntity() != null && !entity.getRidingEntity().shouldRiderSit())) {
				limbSwingAmount = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
				limbSwing = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

				if (entity.isChild()) {
					limbSwing *= 3.0F;
				}

				if (limbSwingAmount > 1.0F) {
					limbSwingAmount = 1.0F;
				}

				f2 = f1 - f; // Forge: Fix MC-1207
			}

			if (!entity.isRiding()) {
				if (entity instanceof EntityCogwork && entity.getControllingPassenger() == null && ((EntityCogwork) entity).isUnderRepairFrame()) {
					GlStateManager.translate(0F, -0.5F, 0F);
				}
				if (suit != null) {
					GlStateManager.translate(0F, -0.15625F, 0F);
				}
			}

			float f14 = 0.0625F;
			GlStateManager.enableAlpha();
			this.mainModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
			this.mainModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, f2, f7, f4, entity);
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			this.renderModel(0, entity, limbSwing, limbSwingAmount, ageInTicks, f1 - f, f7, f14);

			CustomMaterial plating = getPlating(entity);
			if (plating != CustomMaterialRegistry.NONE) {
				colourPlating(plating);
				this.renderModel(1, entity, limbSwing, limbSwingAmount, ageInTicks, f1 - f, f7, f14);
			}
			GlStateManager.color(1.0F, 1.0F, 1.0F);

			if (this.renderOutlines) {
				boolean flag1 = this.setScoreTeamColor(entity);
				GlStateManager.enableColorMaterial();
				GlStateManager.enableOutlineMode(this.getTeamColor(entity));

				if (!this.renderMarker) {
					this.renderModel(0, entity, limbSwing, limbSwingAmount, ageInTicks, f2, f7, f4);
				}

				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
					this.renderLayers(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, f2, f7, f4);
				}

				GlStateManager.disableOutlineMode();
				GlStateManager.disableColorMaterial();

				if (flag1) {
					this.unsetScoreTeamColor();
				}
			} else {
				boolean flag = this.setDoRenderBrightness(entity, partialTicks);
				this.renderModel(0, entity, limbSwing, limbSwingAmount, ageInTicks, f2, f7, f4);

				if (flag) {
					this.unsetBrightness();
				}

				GlStateManager.depthMask(true);

				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
					this.renderLayers(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, f2, f7, f4);
				}
			}

			GlStateManager.disableRescaleNormal();
		}
		catch (Exception exception) {
			MineFantasyReforged.LOG.error("Couldn't render entity", exception);
		}

		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();

		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<>(entity, this, partialTicks, x, y, z));
	}

	private CustomMaterial getPlating(EntityLivingBase base) {
		EntityCogwork suit = getCogwork(base);
		if (suit != null) {
			return suit.getPlating();
		}
		return CustomMaterialRegistry.NONE;
	}

	private EntityCogwork getCogwork(EntityLivingBase base) {
		if (base instanceof EntityCogwork) {
			return (EntityCogwork) base;
		}
		if (base.getRidingEntity() != null && base.getRidingEntity() instanceof EntityCogwork) {
			return (EntityCogwork) base.getRidingEntity();
		}
		return null;
	}

	private void colourPlating(CustomMaterial material) {
		GlStateManager.color(material.getColourRGB()[0] / 255F, material.getColourRGB()[1] / 255F, material.getColourRGB()[2] / 255F);
	}

	@Override
	protected void renderModel(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		renderModel(0, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
	}

	protected void renderModel(int layer, EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.bindEntityTexture(layer);

		if (!entityLivingBase.isInvisible()) {
			this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		} else if (!entityLivingBase.isInvisibleToPlayer(Minecraft.getMinecraft().player)) {
			GlStateManager.pushMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
			GlStateManager.depthMask(false);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
			this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
		} else {
			this.mainModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityLivingBase);
		}
	}
}