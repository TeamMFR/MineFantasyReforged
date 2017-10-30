package minefantasy.mf2.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.block.crafting.BlockRoast;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderRoast implements ISimpleBlockRenderingHandler {
    private static final TileEntityRoastRenderer invModel = new TileEntityRoastRenderer();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (!(block instanceof BlockRoast))
            return;
        BlockRoast roast = (BlockRoast) block;
        if (roast.isOven()) {
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            invModel.renderInvModel(roast.tex, true, 0F, 0F, 0F, 0F);
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
        return false;
    }

    @Override
    public int getRenderId() {
        return BlockRoast.roast_RI;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

}
