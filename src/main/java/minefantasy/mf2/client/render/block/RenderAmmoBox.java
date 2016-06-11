package minefantasy.mf2.client.render.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.decor.BlockAmmoBox;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

public class RenderAmmoBox implements ISimpleBlockRenderingHandler 
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if(block == null || !(block instanceof BlockAmmoBox))return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		BlockAmmoBox forge = (BlockAmmoBox)block;
		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityAmmoBox(forge.getFullTexName(), CustomMaterial.getMaterial("RefinedWood"), forge.storageType), 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockAmmoBox.ammo_RI;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}
