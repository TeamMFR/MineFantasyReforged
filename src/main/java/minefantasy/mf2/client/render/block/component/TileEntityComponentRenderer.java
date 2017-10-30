package minefantasy.mf2.client.render.block.component;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.tileentity.TileEntityComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileEntityComponentRenderer extends TileEntitySpecialRenderer {
    private ModelBarStack bars;
    private ModelSheetStack sheets;
    private ModelPlankStack planks;
    private ModelPotStack pots;
    private ModelBigPlateStack bigplates;
    private ModelJugStack jugs;

    public TileEntityComponentRenderer() {
        this.bars = new ModelBarStack();
        this.sheets = new ModelSheetStack();
        this.planks = new ModelPlankStack();
        this.pots = new ModelPotStack();
        this.bigplates = new ModelBigPlateStack();
        this.jugs = new ModelJugStack();
    }

    public void renderModelAt(TileEntityComponent tile, double d, double d1, double d2, float f) {
        if (tile.item != null) {
            int i = tile.getBlockMetadata();

            int j = 180 + (-90 * i);

            GL11.glPushMatrix(); // start
            float scale = 1.0F;
            float yOffset = 1.0F;
            GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
            GL11.glRotatef(j, 0.0F, 1.0F, 0.0F); // rotate based on metadata
            GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
            GL11.glPushMatrix();
            float level = 0F;

            CustomMaterial material = tile.material;
            if (material != null) {
                GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F,
                        material.colourRGB[2] / 255F);
            }

            bindTextureByName("textures/models/object/component/placed_" + tile.tex + ".png"); // texture
            render(tile.type, tile.stackSize, 0.0625F);

            GL11.glColor3f(1F, 1F, 1F);

            GL11.glPopMatrix();
            GL11.glColor3f(1F, 1F, 1F);
            GL11.glPopMatrix(); // end
        }

    }

    private void render(String type, int stackSize, float f) {
        if (type.equalsIgnoreCase("bar")) {
            bars.render(stackSize, f);
        }
        if (type.equalsIgnoreCase("sheet")) {
            sheets.render(stackSize, f);
        }
        if (type.equalsIgnoreCase("plank")) {
            planks.render(stackSize, f);
        }
        if (type.equalsIgnoreCase("pot")) {
            pots.render(stackSize, f);
        }
        if (type.equalsIgnoreCase("bigplate")) {
            bigplates.render(stackSize, f);
        }
        if (type.equalsIgnoreCase("jug")) {
            jugs.render(stackSize, f);
        }
    }

    private void bindTextureByName(String image) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderModelAt((TileEntityComponent) tileentity, d, d1, d2, f); // where to render
    }

}
