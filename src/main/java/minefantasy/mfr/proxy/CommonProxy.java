package minefantasy.mfr.proxy;

import minefantasy.mfr.api.refine.ISmokeHandler;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.block.BlockLeavesMF;
import minefantasy.mfr.entity.EntitySmoke;
import minefantasy.mfr.integration.CustomStone;
import minefantasy.mfr.item.ArrowFireFlint;
import minefantasy.mfr.item.ArrowFirerMF;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.mechanics.ArrowHandler;
import minefantasy.mfr.mechanics.MonsterUpgrader;
import minefantasy.mfr.mechanics.PlayerTickHandler;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.entity.player.EntityPlayer;
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
		AmmoMechanics.addHandler(new ArrowFireFlint());
		AmmoMechanics.addHandler(new ArrowFirerMF());
		SmokeMechanics.handler = this;
	}

	public void preInit(FMLPreInitializationEvent e) {
	}

	public void registerTickHandlers() {
		MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
		//MinecraftForge.EVENT_BUS.register(new CombatMechanics());
		MinecraftForge.EVENT_BUS.register(new MonsterUpgrader());
		MinecraftForge.EVENT_BUS.register(new ArrowHandler());
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

	public void setGraphicsLevel(BlockLeavesMF parBlock, boolean parFancyEnabled) {
		parBlock.setGraphicsLevel(parFancyEnabled);
	}

	public void postInit(FMLPostInitializationEvent e) {
		CustomStone.init();
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