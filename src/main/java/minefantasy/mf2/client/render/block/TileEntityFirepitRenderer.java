package minefantasy.mf2.client.render.block;


import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityFirepit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityFirepitRenderer extends TileEntitySpecialRenderer 
{
	private ModelForgeTop topModel;
    public TileEntityFirepitRenderer() 
    {
        model = new ModelFirepit();
        topModel = new ModelForgeTop();
    }

    public void renderAModelAt(TileEntityFirepit tile, double d, double d1, double d2, float f) {
        bindTextureByName("textures/models/tileentity/firepit.png"); 
        GL11.glPushMatrix();
        float yOffset = 0.0625F * 5F;
        
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F); 
        
		model.renderModel(tile, 0.0625F);
		
		if(tile.hasBlockAbove())
        {
			GL11.glTranslatef(0F, -(1.5F-yOffset), 0F);
        	bindTextureByName("textures/models/tileentity/forge_top.png"); //texture
        	topModel.render(0.0625F);
        }
        GL11.glPopMatrix();
        

    }
    
    private void bindTextureByName(String image)
    {
    	bindTexture(TextureHelperMF.getResource(image));
	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityFirepit) tileentity, d, d1, d2, f); 
    }
	
    private ModelFirepit model;
}