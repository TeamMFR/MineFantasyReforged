package minefantasy.mfr.proxy;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.block.BlockLeavesMF;
import minefantasy.mfr.client.KnowledgePageRegistry;
import minefantasy.mfr.client.model.BlockColorsMFR;
import minefantasy.mfr.client.model.ItemColorsMFR;
import minefantasy.mfr.client.particle.CustomParticle;
import minefantasy.mfr.client.particle.ParticleSpark;
import minefantasy.mfr.client.render.BlockingAnimationHandler;
import minefantasy.mfr.client.render.HudHandler;
import minefantasy.mfr.client.render.block.TileEntityAmmoBoxRenderer;
import minefantasy.mfr.client.render.block.TileEntityAnvilRenderer;
import minefantasy.mfr.client.render.block.TileEntityBellowsRenderer;
import minefantasy.mfr.client.render.block.TileEntityBigFurnaceRenderer;
import minefantasy.mfr.client.render.block.TileEntityBombPressRenderer;
import minefantasy.mfr.client.render.block.TileEntityComponentRenderer;
import minefantasy.mfr.client.render.block.TileEntityKitchenBenchRenderer;
import minefantasy.mfr.client.render.block.TileEntityQuernRenderer;
import minefantasy.mfr.client.render.block.TileEntityRackRenderer;
import minefantasy.mfr.client.render.block.TileEntityRoastRenderer;
import minefantasy.mfr.client.render.block.TileEntityTanningRackRenderer;
import minefantasy.mfr.client.render.entity.RenderArrowMF;
import minefantasy.mfr.client.render.entity.RenderBomb;
import minefantasy.mfr.client.render.entity.RenderDragon;
import minefantasy.mfr.client.render.entity.RenderDragonBreath;
import minefantasy.mfr.client.render.entity.RenderFireBlast;
import minefantasy.mfr.client.render.entity.RenderHound;
import minefantasy.mfr.client.render.entity.RenderMine;
import minefantasy.mfr.client.render.entity.RenderMinotaur;
import minefantasy.mfr.client.render.entity.RenderParachute;
import minefantasy.mfr.client.render.entity.RenderPowerArmour;
import minefantasy.mfr.client.render.entity.RenderShrapnel;
import minefantasy.mfr.client.render.entity.RenderSmoke;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.entity.EntityBomb;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.EntityFireBlast;
import minefantasy.mfr.entity.EntityMine;
import minefantasy.mfr.entity.EntityParachute;
import minefantasy.mfr.entity.EntityShrapnel;
import minefantasy.mfr.entity.EntitySmoke;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.entity.mob.EntityHound;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.init.MineFantasyKeybindings;
import minefantasy.mfr.mechanics.ExtendedReach;
import minefantasy.mfr.mechanics.PlayerTickHandler;
import minefantasy.mfr.tile.TileEntityAmmoBox;
import minefantasy.mfr.tile.TileEntityAnvil;
import minefantasy.mfr.tile.TileEntityBellows;
import minefantasy.mfr.tile.TileEntityBigFurnace;
import minefantasy.mfr.tile.TileEntityBombPress;
import minefantasy.mfr.tile.TileEntityComponent;
import minefantasy.mfr.tile.TileEntityKitchenBench;
import minefantasy.mfr.tile.TileEntityQuern;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.tile.TileEntityRoast;
import minefantasy.mfr.tile.TileEntityTanningRack;
import minefantasy.mfr.util.ClientTickHandler;
import minefantasy.mfr.util.ParticleBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anonymous Productions
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends ClientProxyBase {

	/** Static particle factory map */
	private static final Map<ResourceLocation, CustomParticle.ICustomParticleFactory> factories = new HashMap<>();


	@Override
	public void preInit() {
		super.preInit();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void init() {
		super.init();

		BlockColorsMFR.init();
		ItemColorsMFR.init();
		registerParticles();
	}

	@Override
	public void postInit() {
		super.postInit();
	}

	/**
	 * Is the player trying to jump (assuming no screens are open)
	 */
	public static boolean isUserJumpCommand(Entity user) {
		return Minecraft.getMinecraft().currentScreen == null && user == Minecraft.getMinecraft().player && Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode());
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().world;
	}

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		registerEntityRenderer();
		registerTickHandlers();
		MineFantasyKeybindings.registerKeyBindings();
	}

	@Override
	public void registerMain() {
		super.registerMain();

	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		MineFantasyReforgedAPI.init();
		KnowledgePageRegistry.registerPages();
	}

	@Override
	public void registerTickHandlers() {
		super.registerTickHandlers();
		FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
		MinecraftForge.EVENT_BUS.register(new ExtendedReach());
		MinecraftForge.EVENT_BUS.register(new HudHandler());
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new BlockingAnimationHandler());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTanningRack.class, new TileEntityTanningRackRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBellows.class, new TileEntityBellowsRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmmoBox.class, new TileEntityAmmoBoxRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuern.class, new TileEntityQuernRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBigFurnace.class, new TileEntityBigFurnaceRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombPress.class, new TileEntityBombPressRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoast.class, new TileEntityRoastRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvil.class, new TileEntityAnvilRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRack.class, new TileEntityRackRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityComponent.class, new TileEntityComponentRenderer<>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKitchenBench.class, new TileEntityKitchenBenchRenderer<>());
	}

	public void registerEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(EntityArrowMFR.class, RenderArrowMF::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, RenderBomb::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMine.class, RenderMine::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, RenderShrapnel::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBlast.class, RenderFireBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySmoke.class, RenderSmoke::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonBreath.class, RenderDragonBreath::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, RenderParachute::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCogwork.class, RenderPowerArmour::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityDragon.class, RenderDragon::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMinotaur.class, RenderMinotaur::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityHound.class, RenderHound::new);
	}

	/** Use {@link CustomParticle#registerParticle(ResourceLocation, CustomParticle.ICustomParticleFactory)}, this is internal. */
	// I mean, it does exactly the same thing but I might want to make it do something else in future...

	public static void addParticleFactory(ResourceLocation name, CustomParticle.ICustomParticleFactory factory){
		factories.put(name, factory);
	}

	@Override
	public void registerParticles() {
		// I'll be a good programmer and use the API method rather than the one above. Lead by example, as they say...
		 CustomParticle.registerParticle(ParticleBuilder.Type.SPARK, ParticleSpark::new);
	}

	@Override
	public CustomParticle createParticle(ResourceLocation type, World world, double x, double y, double z){
		CustomParticle.ICustomParticleFactory factory = factories.get(type);
		if(factory == null){
			MineFantasyReforged.LOG.warn("Unrecognised particle type {} ! Ensure the particle is properly registered.", type);
			return null;
		}
		return factory.createParticle(world, x, y, z);
	}

	@Override
	public void setGraphicsLevel(BlockLeavesMF parBlock, boolean parFancyEnabled) {
		parBlock.setGraphicsLevel(parFancyEnabled);
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}

}
