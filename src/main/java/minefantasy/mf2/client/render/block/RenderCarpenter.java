package minefantasy.mf2.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.block.crafting.BlockCarpenter;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderCarpenter implements ISimpleBlockRenderingHandler {
    private static final TileEntityCarpenterRenderer invModel = new TileEntityCarpenterRenderer();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        invModel.renderModelAt("carpenter", 0, 0F, 0F, 0F, 0F, 0);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
        return false;
    }

    @Override
    public int getRenderId() {
        return BlockCarpenter.carpenter_RI;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

}
