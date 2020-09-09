package minefantasy.mfr.client.render.block;
// Made with Blockbench 3.6.5

import minefantasy.mfr.tile.TileEntityBigFurnace;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBigFurnace extends ModelBase {
	private final ModelRenderer Wall4t;
	private final ModelRenderer Wall1;
	private final ModelRenderer Wall2;
	private final ModelRenderer Top;
	private final ModelRenderer Wall3;
	private final ModelRenderer Wall4;
	private final ModelRenderer lava;
	private final ModelRenderer Base;
	private final ModelRenderer contents;
	public final ModelRenderer Door;

	public ModelBigFurnace() {
		textureWidth = 128;
		textureHeight = 64;

		Wall4t = new ModelRenderer(this);
		Wall4t.setRotationPoint(0.0F, 11.5F, -7.0F);
		setRotationAngle(Wall4t, 0.0F, 0.0F, 0.0F);
		Wall4t.setTextureOffset(76, 50).addBox(-6.0F, -1.5F, -1.0F, 12, 3, 2, true);

		Wall1 = new ModelRenderer(this);
		Wall1.setRotationPoint(7.0F, 16.0F, 0.0F);
		setRotationAngle(Wall1, 0.0F, 1.5708F, 0.0F);
		Wall1.setTextureOffset(0, 27).addBox(-8.0F, -6.0F, -1.0F, 16, 12, 2, true);

		Wall2 = new ModelRenderer(this);
		Wall2.setRotationPoint(-6.0F, 16.0F, 0.0F);
		setRotationAngle(Wall2, 0.0F, -1.5708F, 0.0F);
		Wall2.setTextureOffset(0, 27).addBox(-8.0F, -6.0F, 0.0F, 16, 12, 2, true);

		Top = new ModelRenderer(this);
		Top.setRotationPoint(0.0F, 9.0F, 0.0F);
		Top.setTextureOffset(0, 0).addBox(-8.0F, -1.0F, -8.0F, 16, 2, 16, true);

		Wall3 = new ModelRenderer(this);
		Wall3.setRotationPoint(0.0F, 16.0F, 7.0F);
		setRotationAngle(Wall3, 0.0F, 0.0F, 0.0F);
		Wall3.setTextureOffset(48, 50).addBox(-6.0F, -6.0F, -1.0F, 12, 12, 2, true);

		Wall4 = new ModelRenderer(this);
		Wall4.setRotationPoint(0.0F, 21.0F, -7.0F);
		setRotationAngle(Wall4, 0.0F, 0.0F, 0.0F);
		Wall4.setTextureOffset(76, 60).addBox(-6.0F, -1.0F, -1.0F, 12, 2, 2, true);

		lava = new ModelRenderer(this);
		lava.setRotationPoint(0.0F, 20.0F, 0.0F);
		lava.setTextureOffset(26, 27).addBox(-5.0F, 0.0F, -5.0F, 10, 0, 10, true);

		Base = new ModelRenderer(this);
		Base.setRotationPoint(0.0F, 23.0F, 0.0F);
		setRotationAngle(Base, 3.1416F, 0.0F, 0.0F);
		Base.setTextureOffset(0, 0).addBox(-8.0F, -1.0F, -8.0F, 16, 2, 16, true);

		contents = new ModelRenderer(this);
		contents.setRotationPoint(0.0F, 21.5F, 0.0F);
		contents.setTextureOffset(0, 41).addBox(-6.0F, -0.5F, -6.0F, 12, 1, 12, true);

		Door = new ModelRenderer(this);
		Door.setRotationPoint(0.0F, 21.0F, -7.0F);
		Door.setTextureOffset(73, 15).addBox(-1.1F, -9.0F, -6.0F, 1, 9, 12, false);

	}

	public void renderModel(boolean lit, float angle, float f) {
		if (lit) {
			lava.render(f);
		}

		setRotationAngle(Door, 0.0F, -1.5708F, angle);
		Door.renderWithRotation(f);

		Top.render(f);
		contents.render(f);
		Base.render(f);
		Wall4t.render(f);
		Wall1.render(f);
		Wall2.render(f);
		Wall3.render(f);
		Wall4.render(f);

	}

	public void renderModel(float f) {
		Top.render(f);
		Base.render(f);
		Wall4t.render(f);
		Wall1.render(f);
		Wall2.render(f);
		Wall3.render(f);
		Wall4.render(f);
		Door.render(f);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}