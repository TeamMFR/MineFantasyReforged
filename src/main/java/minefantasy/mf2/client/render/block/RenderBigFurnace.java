package minefantasy.mf2.client.render.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.block.refining.BlockBigFurnace;
import minefantasy.mf2.block.tileentity.TileEntityBigFurnace;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

public class RenderBigFurnace implements ISimpleBlockRenderingHandler 
{
	private static final TileEntityBigFurnaceRenderer invModel = new TileEntityBigFurnaceRenderer();
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if(block == null || !(block instanceof BlockBigFurnace))return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		BlockBigFurnace furnace = (BlockBigFurnace)block;
		invModel.renderInvModel(furnace.isHeater, furnace.isHeater ? "furnace_heater" : "furnace_rock", 0F, 0F, 0F, 0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockBigFurnace.furn_RI;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}
