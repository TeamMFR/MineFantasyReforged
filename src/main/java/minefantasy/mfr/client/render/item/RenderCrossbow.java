package minefantasy.mfr.client.render.item;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemCrossbow;
import minefantasy.mfr.mechanics.AmmoMechanics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class RenderCrossbow extends WrappedItemModel implements IItemRenderer {
	public RenderCrossbow(Supplier<ModelResourceLocation> wrappedModel) {
		super(wrappedModel);
	}

	@Override
	public void renderItem(ItemStack stack, TransformType type) {
		GlStateManager.pushMatrix();

		if (type != TransformType.GUI && type != TransformType.FIRST_PERSON_LEFT_HAND && type != TransformType.FIRST_PERSON_RIGHT_HAND) {
			GlStateManager.scale(2F, 2F, 2F);
			GlStateManager.translate(0.15F, 0.6F, 0.30F);
			renderPart(stack, "stock", type);
			renderPart(stack, "mechanism", type);
			renderPart(stack, "muzzle", type);
			renderPart(stack, "mod", type);
			if (AmmoMechanics.isFirearmLoaded(stack)) {
				renderString("string_loaded");
			} else {
				renderString("string_unloaded");
			}
			renderArrow(stack);
		}
		else if (type == TransformType.FIRST_PERSON_LEFT_HAND || type == TransformType.FIRST_PERSON_RIGHT_HAND){
			if (stack.getItemUseAction() == EnumAction.BLOCK){
				GlStateManager.scale(3F, 3F, 3F);
				GlStateManager.translate(0.1F, 0.4F, 0.25F);
				GlStateManager.rotate(90, 0F, 1F, 0F);
			}
			else{
				GlStateManager.scale(2F, 2F, 2F);
				GlStateManager.translate(0.1F, 0.4F, 0.25F);
			}
			renderPart(stack, "stock", type);
			renderPart(stack, "mechanism", type);
			renderPart(stack, "muzzle", type);
			renderPart(stack, "mod", type);
			if (AmmoMechanics.isFirearmLoaded(stack)) {
				renderString("string_loaded");
			} else {
				renderString("string_unloaded");
			}
			renderArrow(stack);

		}
		else {
			GlStateManager.scale(1.5F, 1.5F, 1.5F);
			GlStateManager.translate(0.2F, 0.5F, 0.35F);
			renderPart(stack, "stock", type);
			renderPart(stack, "mechanism", type);
			renderPart(stack, "muzzle", type);
			renderPart(stack, "mod", type);
			if (AmmoMechanics.isFirearmLoaded(stack)) {
				renderString("string_loaded");
			} else {
				renderString("string_unloaded");
			}
		}

		GlStateManager.popMatrix();
	}

	private void renderPart(ItemStack stack, String part_name, TransformType type) {
		boolean isArms = part_name.equalsIgnoreCase("mechanism");
		boolean isStock = part_name.equalsIgnoreCase("stock");

		ItemCrossbow crossbow = (ItemCrossbow) stack.getItem();
		ItemStack part = new ItemStack((Item) crossbow.getItem(stack, part_name));

		GlStateManager.pushMatrix();

		if (type != TransformType.GUI) {
			if (isArms) {
				GlStateManager.rotate(90, -1, 1, 0);
			}
		}

		Minecraft.getMinecraft().getRenderItem().renderItem(part, TransformType.NONE);

		GlStateManager.popMatrix();

	}

	private void renderString(String part_name) {
		GlStateManager.pushMatrix();

		ItemStack string_stack = part_name.equalsIgnoreCase("string_unloaded") ? new ItemStack(MineFantasyItems.CROSSBOW_STRING_UNLOADED) : new ItemStack(MineFantasyItems.CROSSBOW_STRING_LOADED);
		GlStateManager.rotate(90, -1, 1, 0);

		Minecraft.getMinecraft().getRenderItem().renderItem(string_stack, TransformType.NONE);

		GlStateManager.popMatrix();
	}

	private void renderArrow(ItemStack stack) {
		ItemStack arrowStack = AmmoMechanics.getArrowOnBow(stack);

		if (!arrowStack.isEmpty() && AmmoMechanics.isFirearmLoaded(stack)) {

			GlStateManager.pushMatrix(); //arrow start

			GlStateManager.scale(0.375F, 0.375F, 0.375F);
			GlStateManager.translate(0.2F, -0.05F, 0F);
			GlStateManager.rotate(90, 0, 0, 1);
			GlStateManager.rotate(90, 1, 1, 0);

			Minecraft.getMinecraft().getRenderItem().renderItem(arrowStack, TransformType.NONE);

			GlStateManager.popMatrix(); //arrow end
		}
	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_BOW;
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