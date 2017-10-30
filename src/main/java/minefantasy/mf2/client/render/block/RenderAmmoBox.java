package minefantasy.mf2.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.decor.BlockAmmoBox;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderAmmoBox implements ISimpleBlockRenderingHandler {
    private static final TileEntityAmmoBoxRenderer invModel = new TileEntityAmmoBoxRenderer();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (block == null || !(block instanceof BlockAmmoBox))
            return;

        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        BlockAmmoBox box = (BlockAmmoBox) block;
        invModel.renderInvModel(box.getFullTexName(), box.storageType, CustomMaterial.getMaterial("refinedWood"), 0F,
                0F, 0F, 0F);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
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
