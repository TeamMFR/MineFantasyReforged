package minefantasy.mf2.client.render.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.decor.BlockRack;
import minefantasy.mf2.block.decor.BlockTrough;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

public class RenderRack implements ISimpleBlockRenderingHandler 
{
	private static final TileEntityRackRenderer invModel = new TileEntityRackRenderer();
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if(block == null || !(block instanceof BlockRack))return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		BlockRack rack = (BlockRack)block;
		invModel.renderInvModel(CustomMaterial.getMaterial("refinedWood"), rack.getFullTexName(), 0F, 0F, 0F, 0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockRack.rack_RI;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}
