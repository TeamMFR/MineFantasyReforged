package minefantasy.mf2.client.render.block;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.tileentity.TileEntityComponent;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityComponentRenderer extends TileEntitySpecialRenderer
{
    public TileEntityComponentRenderer() 
    {
        this.bars = new ModelBarStack();
    }

	public void renderModelAt(TileEntityComponent tile, double d, double d1, double d2, float f) 
    {
		if(tile.item != null)
		{
			int i = tile.getBlockMetadata();
	
			int j = 180 + (-90 * i);
	
	        GL11.glPushMatrix(); //start
	        float scale = 1.0F;
	        float yOffset = 1.0F;
	        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); //size
	        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F); //rotate based on metadata
	        GL11.glScalef(scale, -scale, -scale); //if you read this comment out this line and you can see what happens
	        GL11.glPushMatrix();
	        float level = 0F;
	        
	        CustomMaterial material = tile.material;
	        if(material != null)
	        {
	        	GL11.glColor3f((float)material.colourRGB[0]/255F, (float)material.colourRGB[1]/255F, (float)material.colourRGB[2]/255F);
	        }
	        
	        bindTextureByName("textures/models/object/component/placed_"+tile.tex+".png"); //texture
	        render(tile.type, tile.stackSize, 0.0625F);
	        
	    	
	    	GL11.glColor3f(1F, 1F, 1F);
	    	
	    	GL11.glPopMatrix();
	        GL11.glColor3f(1F, 1F, 1F);
	        GL11.glPopMatrix(); //end
		}

    }

    private void render(String type, int stackSize, float f)
    {
    	if(type.equalsIgnoreCase("bar"))
    	{
    		bars.render(stackSize, f);
    	}
	}

	private void bindTextureByName(String image)
    {
    	Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderModelAt((TileEntityComponent) tileentity, d, d1, d2, f); //where to render
    }
	
    private ModelBarStack bars;
    private Random random = new Random();
}
