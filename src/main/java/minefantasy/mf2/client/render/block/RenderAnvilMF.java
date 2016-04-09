package minefantasy.mf2.client.render.block;

import minefantasy.mf2.block.crafting.BlockAnvilMF;
import minefantasy.mf2.block.tileentity.TileEntityAnvilMF;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderAnvilMF implements ISimpleBlockRenderingHandler 
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if(!(block instanceof BlockAnvilMF))return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		BlockAnvilMF anvil = (BlockAnvilMF)block;
		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityAnvilMF(anvil.getTier(), anvil.material.name).setDisplay(), 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockAnvilMF.anvil_RI;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}
