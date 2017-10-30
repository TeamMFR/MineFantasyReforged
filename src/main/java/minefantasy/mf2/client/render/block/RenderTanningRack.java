package minefantasy.mf2.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.block.crafting.BlockTanningRack;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderTanningRack implements ISimpleBlockRenderingHandler {
    private static final TileEntityTanningRackRenderer invModel = new TileEntityTanningRackRenderer();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (!(block instanceof BlockTanningRack))
            return;

        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        BlockTanningRack rack = (BlockTanningRack) block;
        invModel.renderInvModel(rack.tex, rack.tex.equalsIgnoreCase("metal"), 0F, 0F, 0F, 0F);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
        return false;
    }

    @Override
    public int getRenderId() {
        return BlockTanningRack.tanner_RI;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

}
