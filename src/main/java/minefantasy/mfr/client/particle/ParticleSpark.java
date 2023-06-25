package minefantasy.mfr.client.particle;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ParticleSpark extends CustomParticle {

	private static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "particles/spark");

	public ParticleSpark(World world, double x, double y, double z){

		super(world, x, y, z, TEXTURE); // This particle only has 1 texture
		this.canCollide = true;
	}

	@SubscribeEvent
	public static void onTextureStitchEvent(TextureStitchEvent.Pre event){
		event.getMap().registerSprite(TEXTURE);
	}
}