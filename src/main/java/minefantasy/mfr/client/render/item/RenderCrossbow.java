package minefantasy.mfr.client.render.item;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.item.gadget.ItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;
import org.lwjgl.opengl.GL11;

import java.util.function.Supplier;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */

public class RenderCrossbow extends WrappedItemModel implements IItemRenderer {

    public RenderCrossbow(Supplier<ModelResourceLocation> wrappedModel) {
        super(wrappedModel);
    }

    @Override
    public void renderItem(ItemStack item, ItemCameraTransforms.TransformType type) {
        ItemCrossbow crossbow = (ItemCrossbow) item.getItem();

        if (type != ItemCameraTransforms.TransformType.NONE) {
            renderPart(item, "stock");
            renderPart(item, "mechanism");
            renderPart(item, "string_unloaded");
            renderPart(item, "muzzle");
            renderPart(item, "mod");
        }
        if (type == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND && crossbow.getZoom(item) < 0.1F) {
            renderPart(item, "stock");
            renderPart(item, "mechanism");
            renderPart(item, "string_unloaded");
            renderPart(item, "muzzle");
            renderPart(item, "mod");
        }
    }

    private void renderPart(ItemStack item, String part_name) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(0.75F, -0.25F, 0F);
        GlStateManager.translate(0f, 0f, 0);
        doRenderPart(item, part_name);

        GlStateManager.popMatrix();

    }

    private void doRenderPart(ItemStack item, String part_name) {
        boolean isArms = part_name.equalsIgnoreCase("mechanism");
        GlStateManager.pushMatrix();

        GlStateManager.scale(1.5F, 1.5F, 1.5F);
        GlStateManager.translate(-0.4F, 0.5F, 0F);

        if (isArms){
         GlStateManager.rotate(90, -1, 1, 0);
        }

        ItemCrossbow crossbow = (ItemCrossbow) item.getItem();
        ItemStack part = new ItemStack((Item) crossbow.getItem(item, part_name));

        Minecraft.getMinecraft().getRenderItem().renderItem(part, ItemCameraTransforms.TransformType.NONE);

        GlStateManager.popMatrix();

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