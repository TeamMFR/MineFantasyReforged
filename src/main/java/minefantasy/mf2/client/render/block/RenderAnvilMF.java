package minefantasy.mf2.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.block.crafting.BlockAnvilMF;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderAnvilMF implements ISimpleBlockRenderingHandler {
    private static final TileEntityAnvilMFRenderer renderAnvil = new TileEntityAnvilMFRenderer();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (!(block instanceof BlockAnvilMF))
            return;

        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        BlockAnvilMF anvil = (BlockAnvilMF) block;
        renderAnvil.renderModelAt(anvil.material.name, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
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
