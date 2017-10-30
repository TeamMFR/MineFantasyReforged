package minefantasy.mf2.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.block.refining.BlockChimney;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderSmokePipe implements ISimpleBlockRenderingHandler {
    private static final TileEntitySmokePipeRenderer tileRender = new TileEntitySmokePipeRenderer();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (!(block instanceof BlockChimney))
            return;

        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        if (((BlockChimney) block).isPipe()) {
            tileRender.renderAModelAt(null, 0D, 0D, 0D, 0F);
        }
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
        return false;
    }

    @Override
    public int getRenderId() {
        return BlockChimney.pipe_RI;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

}
