package minefantasy.mfr.util;

import com.google.common.collect.Maps;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.Map;

public class TextureHelperMFR {
	public static final ResourceLocation ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);
	private static final Map<Object, Object> resourceList = Maps.newHashMap();

	/**
	 * This gets the resource location from just a simple directory(Beats the shit
	 * you have to do nower days!)
	 */
	public static ResourceLocation getResource(String directory) {
		ResourceLocation resourcelocation = (ResourceLocation) resourceList.get(directory);

		if (resourcelocation == null) {
			MFRLogUtil.logDebug("MineFantasy: Added Resource: " + directory);

			resourcelocation = new ResourceLocation(MineFantasyReforged.MOD_ID, directory);
			resourceList.put(directory, resourcelocation);
		}

		return resourcelocation;
	}

	public static boolean isLightMapDisabled() {
		return FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled;
	}

	public static VertexFormat getFormatWithLightMap(VertexFormat format) {
		if (isLightMapDisabled()) {
			return format;
		}

		if (format == DefaultVertexFormats.BLOCK) {
			return DefaultVertexFormats.BLOCK;
		} else if (format == DefaultVertexFormats.ITEM) {
			return ITEM_FORMAT_WITH_LIGHTMAP;
		} else if (!format.hasUvOffset(1)) {
			VertexFormat result = new VertexFormat(format);

			result.addElement(DefaultVertexFormats.TEX_2S);

			return result;
		}

		return format;
	}

	public static void renderEffect(IBakedModel model, ItemStack stack) {
		GlStateManager.depthMask(false);
		GlStateManager.depthFunc(514);
		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
		Minecraft.getMinecraft().renderEngine.bindTexture(ITEM_GLINT);
		GlStateManager.matrixMode(5890);
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
		GlStateManager.translate(f, 0.0F, 0.0F);
		GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
		renderModel(model, stack);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f1 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
		GlStateManager.translate(-f1, 0.0F, 0.0F);
		GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
		renderModel(model, stack);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableLighting();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}

	private static void renderModel(IBakedModel model, ItemStack stack) {
		if (net.minecraftforge.common.ForgeModContainer.allowEmissiveItems) {
			net.minecraftforge.client.ForgeHooksClient.renderLitItem(Minecraft.getMinecraft().getRenderItem(), model, -8372020, stack);
			return;
		}
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

		for (EnumFacing enumfacing : EnumFacing.values()) {
			Minecraft.getMinecraft().getRenderItem().renderQuads(bufferbuilder, model.getQuads(null, enumfacing, 0L), -8372020, stack);
		}

		Minecraft.getMinecraft().getRenderItem().renderQuads(bufferbuilder, model.getQuads(null, null, 0L), -8372020, stack);
		tessellator.draw();
	}
}
