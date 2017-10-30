package minefantasy.mf2.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.entity.EntityMine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMine extends Render {
    private RenderBlocks blockRenderer = new RenderBlocks();

    public RenderMine() {
        this.shadowSize = 0.25F;
    }

    public void doRender(EntityMine mine, double x, double y, double z, float f, float f1) {
        Block block = mine.getCasing() == 3 ? Blocks.glass
                : mine.getCasing() == 2 ? Blocks.obsidian
                : mine.getCasing() == 1 ? Blocks.iron_block : Blocks.hardened_clay;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y - 0.3F, (float) z);

        this.bindEntityTexture(mine);
        GL11.glScalef(0.5F, 0.25F, 0.5F);
        this.blockRenderer.renderBlockAsItem(block, 0, mine.getBrightness(f1));

        GL11.glTranslatef(0F, 0.3F, 0F);
        GL11.glScalef(0.5F, 1.0F, 0.5F);
        this.blockRenderer.renderBlockAsItem(block, 0, mine.getBrightness(f1));

        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMine p_110775_1_) {
        return TextureMap.locationBlocksTexture;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityMine) p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
                         float p_76986_9_) {
        this.doRender((EntityMine) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}