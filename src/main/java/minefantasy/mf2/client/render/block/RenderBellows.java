package minefantasy.mf2.client.render.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.block.refining.BlockBellows;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBellows implements ISimpleBlockRenderingHandler {
	private static final TileEntityBellowsRenderer invModel = new TileEntityBellowsRenderer();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if (block == null || !(block instanceof BlockBellows))
			return;

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		invModel.renderInvModel(0F, 0F, 0F, 0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockBellows.bellows_RI;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}
