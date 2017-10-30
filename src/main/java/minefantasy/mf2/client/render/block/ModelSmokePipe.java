package minefantasy.mf2.client.render.block;

import minefantasy.mf2.block.tileentity.TileEntityChimney;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelSmokePipe extends ModelBase {
    ModelRenderer centre;
    ModelRenderer north;
    ModelRenderer south;
    ModelRenderer up;
    ModelRenderer down;
    ModelRenderer east;
    ModelRenderer west;

    public ModelSmokePipe() {
        textureWidth = 64;
        textureHeight = 64;

        centre = new ModelRenderer(this, 16, 12);
        centre.addBox(-6F, -6F, -6F, 12, 12, 12);
        centre.setRotationPoint(0F, 0F, 0F);
        centre.setTextureSize(64, 64);

        setRotation(centre, 0F, 0F, 0F);
        north = new ModelRenderer(this, 0, 0);
        north.addBox(6F, -6F, -6F, 2, 12, 12);
        north.setRotationPoint(0F, 0F, 0F);
        north.setTextureSize(64, 64);

        setRotation(north, 0F, 1.570796F, 0F);
        south = new ModelRenderer(this, 0, 0);
        south.addBox(-8F, -6F, -6F, 2, 12, 12);
        south.setRotationPoint(0F, 0F, 0F);
        south.setTextureSize(64, 64);

        setRotation(south, 0F, 1.570796F, 0F);
        up = new ModelRenderer(this, 0, 0);
        up.addBox(-8F, -6F, -6F, 2, 12, 12);
        up.setRotationPoint(0F, 0F, 0F);
        up.setTextureSize(64, 64);

        setRotation(up, 0F, 0F, 1.570796F);
        down = new ModelRenderer(this, 0, 0);
        down.addBox(6F, -6F, -6F, 2, 12, 12);
        down.setRotationPoint(0F, 0F, 0F);
        down.setTextureSize(64, 64);

        setRotation(down, 0F, 0F, 1.570796F);
        east = new ModelRenderer(this, 0, 0);
        east.addBox(-8F, -6F, -6F, 2, 12, 12);
        east.setRotationPoint(0F, 0F, 0F);
        east.setTextureSize(64, 64);

        setRotation(east, 0F, 0F, 0F);
        west = new ModelRenderer(this, 0, 0);
        west.addBox(6F, -6F, -6F, 2, 12, 12);
        west.setRotationPoint(0F, 0F, 0F);
        west.setTextureSize(64, 64);

        setRotation(west, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    protected void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(TileEntityChimney tile, float f) {
        centre.render(f);
        if (tile == null) {
            east.render(f);
            west.render(f);
        } else {
            if (tile.canAccept(ForgeDirection.UP))
                up.render(f);
            if (tile.canAccept(ForgeDirection.DOWN))
                down.render(f);
            if (tile.canAccept(ForgeDirection.NORTH))
                north.render(f);
            if (tile.canAccept(ForgeDirection.SOUTH))
                south.render(f);
            if (tile.canAccept(ForgeDirection.EAST))
                east.render(f);
            if (tile.canAccept(ForgeDirection.WEST))
                west.render(f);
        }
    }
}