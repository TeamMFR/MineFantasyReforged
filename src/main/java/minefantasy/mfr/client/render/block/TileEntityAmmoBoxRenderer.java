package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.decor.BlockAmmoBox;
import minefantasy.mfr.block.refining.BlockBigFurnace;
import minefantasy.mfr.client.model.block.ModelAmmoBox;
import minefantasy.mfr.client.model.block.ModelFoodBox;
import minefantasy.mfr.client.model.block.ModelSmallCrate;
import minefantasy.mfr.itemblock.ItemBlockAmmoBox;
import minefantasy.mfr.tile.decor.TileEntityAmmoBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.model.IModelState;
import org.lwjgl.opengl.GL11;

public class TileEntityAmmoBoxRenderer <T extends TileEntity> extends FastTESR<T> implements IItemRenderer {
    private ModelAmmoBox modelbig, model, modelsml;

    public TileEntityAmmoBoxRenderer() {
        model = new ModelAmmoBox();
        modelbig = new ModelSmallCrate();
        modelsml = new ModelFoodBox();
    }

    @Override
    public void renderTileEntityFast(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        renderAModelAt((TileEntityAmmoBox) te, x, y, z, partialTicks);
    }

    public void renderAModelAt(TileEntityAmmoBox tile, double d, double d1, double d2, float f) {
        EnumFacing facing = EnumFacing.NORTH;
        if (tile.hasWorld()) {
            IBlockState state = tile.getWorld().getBlockState(tile.getPos());
            facing = state.getValue(BlockBigFurnace.FACING);
        }
        this.renderModelAt(tile, -facing.getHorizontalAngle(), d, d1, d2, f);
    }

    public void renderModelAt(TileEntityAmmoBox tile, float facingAngle, double d, double d1, double d2, float f) {
        ModelAmmoBox baseMdl = tile.getStorageType() == 0 ? modelsml : tile.getStorageType() == 1 ? model : modelbig;

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1 / 16F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(facingAngle, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        float level = 0F;

        CustomMaterial material = tile.getMaterial();
        GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);

        this.bindTexture(new ResourceLocation("minefantasyreborn:textures/blocks/" + tile.getTexName() + "_base.png")); // texture
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, tile.angle);

        GL11.glColor3f(1F, 1F, 1F);

        this.bindTexture(new ResourceLocation("minefantasyreborn:textures/blocks/" + tile.getTexName() + "_detail.png")); // texture
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, tile.angle);
        GL11.glPopMatrix();
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glPopMatrix(); // end

    }

    public void renderInvModel(String tex, byte type, CustomMaterial material, double d, double d1, double d2, float f) {
        ModelAmmoBox baseMdl = type == 0 ? modelsml : type == 1 ? model : modelbig;

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1 / 16F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(90, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        float level = 0F;

        GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minefantasyreborn:textures/blocks/" + tex + "_base.png"));
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, 0F);

        GL11.glColor3f(1F, 1F, 1F);

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minefantasyreborn:textures/blocks/" + tex + "_detail.png"));
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, 0F);
        GL11.glPopMatrix();
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glPopMatrix(); // end

    }

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {
        ItemBlock itemBlock;

        BlockAmmoBox box = new BlockAmmoBox("food_box_basic", (byte) 0);

        if (stack.getItem() instanceof ItemBlockAmmoBox){
            itemBlock = (ItemBlock) stack.getItem();
            box = (BlockAmmoBox) itemBlock.getBlock();
        }

        GlStateManager.pushMatrix();

        renderInvModel(box.getFullTexName(), box.storageType, CustomMaterial.getMaterial("refined_wood"), 0F, 0F, 0F, 0F);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.popMatrix();
    }

    @Override
    public IModelState getTransforms() {
        return TransformUtils.DEFAULT_BLOCK;
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
