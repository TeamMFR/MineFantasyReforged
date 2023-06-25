package minefantasy.mfr.proxy;

import minefantasy.mfr.api.refine.ISmokeHandler;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.block.BlockLeavesMF;
import minefantasy.mfr.client.particle.CustomParticle;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.entity.EntitySmoke;
import minefantasy.mfr.integration.CustomSand;
import minefantasy.mfr.integration.CustomStone;
import minefantasy.mfr.item.ArrowHandlerMFR;
import minefantasy.mfr.item.ArrowHandlerVanilla;
import minefantasy.mfr.item.BoltHandlerMFR;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.mechanics.MonsterUpgrader;
import minefantasy.mfr.mechanics.PlayerTickHandler;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Anonymous Productions
 */
public class CommonProxy implements ISmokeHandler {

	public World getClientWorld() {
		return null;
	}

	public void registerMain() {
		AmmoMechanics.addHandler(Constants.VANILLA_ARROW_HANDLER, new ArrowHandlerVanilla());
		AmmoMechanics.addHandler(Constants.MFR_ARROW_HANDLER, new ArrowHandlerMFR());
		AmmoMechanics.addHandler(Constants.MFR_BOLT_HANDLER, new BoltHandlerMFR());
		SmokeMechanics.handler = this;
	}

	public void preInit(FMLPreInitializationEvent e) {
	}

	public void registerTickHandlers() {
		MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
		//MinecraftForge.EVENT_BUS.register(new CombatMechanics());
		MinecraftForge.EVENT_BUS.register(new MonsterUpgrader());
	}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	@Override
	public void spawnSmoke(World world, double x, double y, double z, int value) {
		XSTRandom random = new XSTRandom();
		for (int a = 0; a < value; a++) {
			float sprayRange = 0.005F;
			float sprayX = (random.nextFloat() * sprayRange) - (sprayRange / 2);
			float sprayZ = (random.nextFloat() * sprayRange) - (sprayRange / 2);
			float height = 0.001F;
			if (random.nextInt(2) == 0) {
				EntitySmoke smoke = new EntitySmoke(world, x, y - 0.5D, z, sprayX, height, sprayZ);
				world.spawnEntity(smoke);
			}
		}
	}

	/** Called from init() in the main mod class to initialise the particle factories. */
	public void registerParticles(){} // Does nothing since particles are client-side only

	/** Creates a new particle of the specified type from the appropriate particle factory. <i>Does not actually spawn the
	 * particle; use {@link minefantasy.mfr.util.ParticleBuilder ParticleBuilder} to spawn particles.</i> */
	public CustomParticle createParticle(ResourceLocation type, World world, double x, double y, double z){
		return null;
	}

	public void setGraphicsLevel(BlockLeavesMF parBlock, boolean parFancyEnabled) {
		parBlock.setGraphicsLevel(parFancyEnabled);
	}

	public void postInit(FMLPostInitializationEvent e) {
		CustomStone.init();
		CustomSand.init();
	}

	public void addClientRegister(IClientRegister register) {
		//NOOP for commonProxy
	}

	public void preInit() {
		//NOOP for commonProxy
	}

	public void init() {
		//NOOP for commonProxy
	}

	public void postInit() {
		//NOOP for commonProxy
	}
}