package minefantasy.mfr.client.render.block;

import minefantasy.mfr.tile.TileEntityRoast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.animation.FastTESR;

public class TileEntityRoastRenderer <T extends TileEntity> extends FastTESR<T> {
	@Override
	public void renderTileEntityFast(T te, double x, double y, double z, float partialTick, int breakStage, float partial, BufferBuilder renderer){
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		renderHungItem((TileEntityRoast) te);
		GlStateManager.popMatrix();
	}

	private void renderHungItem(TileEntityRoast tile) {
		ItemStack stack = tile.getInventory().getStackInSlot(0);
		if (!stack.isEmpty()) {
			GlStateManager.rotate(90, 1.0F, 0F, 0F);
			GlStateManager.translate(.5F, .5F, -0.139F); //I know this is a weird number, but it works goddamnit
			GlStateManager.scale(.4f, .4f, .4f);
			GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

			GlStateManager.disableLighting();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 15 * 16, 15 * 16);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);

			GlStateManager.enableLighting();
		}
	}
}
