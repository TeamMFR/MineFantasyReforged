package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.entity.EntityMine;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMine extends Render<EntityMine> {

	public RenderMine(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0F;
	}

	public void doRender(EntityMine mine, double x, double y, double z, float f, float f1) {
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

		Block block = mine.getCasing().equals("crystal") ? Blocks.GLASS : mine.getCasing().equals("obsidian") ? Blocks.OBSIDIAN : mine.getCasing().equals("iron") ? Blocks.IRON_BLOCK : Blocks.HARDENED_CLAY;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y - 0.3F, (float) z);

		this.bindEntityTexture(mine);
		GL11.glScalef(0.5F, 0.25F, 0.5F);
		blockrendererdispatcher.renderBlockBrightness(block.getDefaultState(), mine.getBrightness());

		GL11.glTranslatef(0.25F, 0.3F, 0.75F);
		GL11.glScalef(0.5F, 1.0F, 0.5F);
		blockrendererdispatcher.renderBlockBrightness(block.getDefaultState(), mine.getBrightness());

		GL11.glPopMatrix();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityMine p_110775_1_) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}