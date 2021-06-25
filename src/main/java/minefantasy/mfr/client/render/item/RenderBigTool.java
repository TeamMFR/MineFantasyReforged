package minefantasy.mfr.client.render.item;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class RenderBigTool extends WrappedItemModel implements IItemRenderer {
	/**
	 * Defines the rotation of the model on the Z axis. Can be used to e.g. hold have a katana's edge point higher to the air.
	 * Positive value makes it point close to the ground.
	 */
	private int rotation = 0;

	/**
	 * The scaling factor of the model. Changing this likely means that offset also needs to be fine-tuned for proper positioning.
	 */
	private float scale;

	/**
	 * This will move the item in the hand of the player forward or backwards, to allow positioning correctly in the hands.
	 */
	private float offset;

	/**
	 * Allows to properly set the item in the fist when rotation messes it up.
	 */
	private float yOffset;

	public RenderBigTool(Supplier<ModelResourceLocation> wrappedModel, float scale, float offset) {
		super(wrappedModel);
		this.scale = scale;
		this.offset = offset;
	}

	// use if the weapon needs to be rotated by default
	public RenderBigTool(Supplier<ModelResourceLocation> wrappedModel, float scale, float offset, int rotation, float yOffset) {
		super(wrappedModel);
		this.scale = scale;
		this.offset = offset;
		this.rotation = rotation;
		this.yOffset = yOffset;
	}

	/////////////////
	// TODO: remove - from 1.7.10 .. might be useful as reference?
	//	public RenderHeavyWeapon setParryable() {
	//		super(wrappedModel);
	//		doesRenderParry = true;
	//		return this;
	//	}
	//
	//	public RenderHeavyWeapon setKatana() {
	//		offset = 1.0F;
	//		willStab = true;
	//		return this;
	//	}
	//
	//	public RenderHeavyWeapon setGreatsword() {
	//		offset = 1.0F;
	//		return this;
	//	}
	//
	//	public RenderHeavyWeapon setAxe() {
	//		offset = 1.0F;
	//		return this;
	//	}
	//
	//	public RenderHeavyWeapon setBlunt() {
	//		offset = 1.2F;
	//		return this;
	//	}
	//
	//	public RenderHeavyWeapon setScythe() {
	//		offset = 1.8F;
	//		return this;
	//	}
	/////////////////

	@Override
	public void renderItem(ItemStack item, ItemCameraTransforms.TransformType transformType) {
		GlStateManager.pushMatrix();

		if (transformType != ItemCameraTransforms.TransformType.GUI && transformType != ItemCameraTransforms.TransformType.GROUND) {

			if (entity != null) {
				GlStateManager.translate(offset, offset, 0);
				if (rotation != 0 & transformType != ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND && transformType != ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
					GlStateManager.rotate(rotation, 0, 0, 1);
					GlStateManager.translate(0, yOffset, 0);
				}
				GlStateManager.scale(scale, scale, 1);
			}
		} else if (transformType == ItemCameraTransforms.TransformType.GROUND) {
			GlStateManager.scale(scale, scale, 1);
		}

		renderWrapped(item);
		GlStateManager.popMatrix();
	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_TOOL;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}
}