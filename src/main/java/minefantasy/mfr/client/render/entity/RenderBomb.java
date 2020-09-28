package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.entity.EntityBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBomb extends Render<EntityBomb> {

	public RenderBomb(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.1F;
	}

	public void doRender(EntityBomb entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();

		ItemStack bombStack = entity.getItem();

		Minecraft.getMinecraft().getRenderItem().renderItem(bombStack, ItemCameraTransforms.TransformType.NONE);

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Texture is derived from the ItemStack {@link minefantasy.mfr.entity.EntityBomb#getItem()}. Returning null here
	 */
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityBomb entity) {
		return null;
	}

}