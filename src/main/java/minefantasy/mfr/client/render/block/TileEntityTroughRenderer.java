package minefantasy.mfr.client.render.block;

import minefantasy.mfr.block.BlockTrough;
import minefantasy.mfr.client.model.block.ModelTroughWater;
import minefantasy.mfr.tile.TileEntityTrough;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TileEntityTroughRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> {
	static TextureAtlasSprite TEXTURE;

	private final ModelTroughWater water;

	public TileEntityTroughRenderer() {
		water = new ModelTroughWater();
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te instanceof TileEntityTrough) {
			render((TileEntityTrough) te, x, y, z);
		}
	}

	void render(TileEntityTrough tile, double x, double y, double z) {
		if (tile.getFill() <= 0) {
			return;
		}

		EnumFacing facing = EnumFacing.NORTH;
		if (tile.hasWorld()) {
			IBlockState state = tile.getWorld().getBlockState(tile.getPos());
			facing = state.getValue(BlockTrough.FACING);
		}

		GlStateManager.pushMatrix(); // start
		float scale = 1.0F;
		float yOffset = 1 / 16F;
		GlStateManager.translate((float) x + 0.5F, (float) y + yOffset, (float) z + 0.5F); // size
		int angle = -facing.getHorizontalIndex() * 90;
		GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F); // rotate based on metadata
		GlStateManager.scale(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
		GlStateManager.pushMatrix();

		float height = (float) tile.getFill() / (float) tile.getCapacity();

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/trough_water.png")); // texture
		GlStateManager.translate(0F, -height * 0.35F, 0.125F);
		water.renderModel(0.0625F);

		GlStateManager.popMatrix();
		GlStateManager.popMatrix(); // end
	}

	@SubscribeEvent
	public static void onTextureStitchEvent(TextureStitchEvent.Pre event){
		TEXTURE = event.getMap().registerSprite(new ResourceLocation("minefantasyreforged:blocks/trough_water"));
	}
}
