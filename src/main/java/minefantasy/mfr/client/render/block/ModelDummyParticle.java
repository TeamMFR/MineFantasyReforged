package minefantasy.mfr.client.render.block;

import codechicken.lib.model.DummyBakedModel;
import codechicken.lib.render.particle.IModelParticleProvider;
import codechicken.lib.texture.TextureUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public class ModelDummyParticle extends DummyBakedModel implements IModelParticleProvider {
	private Set<TextureAtlasSprite> textureSprites;
	private String blockTexture;

	public ModelDummyParticle(String blockTexture) {
		this.blockTexture = blockTexture;
	}

	private Set<TextureAtlasSprite> getTextureSprites() {
		if (textureSprites == null) {
			textureSprites = Collections.singleton(TextureUtils.getBlockTexture(blockTexture));
		}
		return textureSprites;
	}

	@Override
	public Set<TextureAtlasSprite> getHitEffects(@Nonnull RayTraceResult traceResult, IBlockState state, IBlockAccess world, BlockPos pos) {
		return getTextureSprites();
	}

	@Override
	public Set<TextureAtlasSprite> getDestroyEffects(IBlockState state, IBlockAccess world, BlockPos pos) {
		return getTextureSprites();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return getTextureSprites().iterator().next();
	}
}
