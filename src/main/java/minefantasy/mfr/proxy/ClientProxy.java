package minefantasy.mfr.proxy;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.helpers.ClientTickHandler;
import minefantasy.mfr.client.KnowledgePageRegistry;
import minefantasy.mfr.client.model.BlockColorsMFR;
import minefantasy.mfr.client.model.ItemColorsMFR;
import minefantasy.mfr.client.render.AnimationHandlerMF;
import minefantasy.mfr.client.render.HudHandlerMF;
import minefantasy.mfr.client.render.RenderArrowMF;
import minefantasy.mfr.client.render.RenderBomb;
import minefantasy.mfr.client.render.RenderDragonBreath;
import minefantasy.mfr.client.render.RenderFireBlast;
import minefantasy.mfr.client.render.RenderMine;
import minefantasy.mfr.client.render.RenderParachute;
import minefantasy.mfr.client.render.RenderShrapnel;
import minefantasy.mfr.client.render.RenderSmoke;
import minefantasy.mfr.client.render.block.TileEntityAmmoBoxRenderer;
import minefantasy.mfr.client.render.block.TileEntityBellowsRenderer;
import minefantasy.mfr.client.render.block.TileEntityBigFurnaceRenderer;
import minefantasy.mfr.client.render.block.TileEntityBombPressRenderer;
import minefantasy.mfr.client.render.block.TileEntityQuernRenderer;
import minefantasy.mfr.client.render.block.TileEntityRackRenderer;
import minefantasy.mfr.client.render.block.TileEntityRoastRenderer;
import minefantasy.mfr.client.render.block.TileEntityTanningRackRenderer;
import minefantasy.mfr.client.render.mob.ModelHound;
import minefantasy.mfr.client.render.mob.RenderDragon;
import minefantasy.mfr.client.render.mob.RenderHound;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.entity.EntityBomb;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.EntityFireBlast;
import minefantasy.mfr.entity.EntityMine;
import minefantasy.mfr.entity.EntityParachute;
import minefantasy.mfr.entity.EntityShrapnel;
import minefantasy.mfr.entity.EntitySmoke;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.entity.mob.EntityHound;
import minefantasy.mfr.mechanics.ExtendedReachMFR;
import minefantasy.mfr.mechanics.PlayerTickHandlerMF;
import minefantasy.mfr.tile.TileEntityBellows;
import minefantasy.mfr.tile.TileEntityBigFurnace;
import minefantasy.mfr.tile.TileEntityBombPress;
import minefantasy.mfr.tile.TileEntityQuern;
import minefantasy.mfr.tile.TileEntityRoast;
import minefantasy.mfr.tile.TileEntityTanningRack;
import minefantasy.mfr.tile.decor.TileEntityAmmoBox;
import minefantasy.mfr.tile.decor.TileEntityRack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

/**
 * @author Anonymous Productions
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends ClientProxyBase {
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
        registerRenders();
        registerTickHandlers();
    }

    @Override
    public void registerMain() {
        super.registerMain();

    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        MineFantasyRebornAPI.init();
        KnowledgePageRegistry.registerPages();
    }

    @Override
    public void registerTickHandlers() {
        super.registerTickHandlers();
        FMLCommonHandler.instance().bus().register(new PlayerTickHandlerMF());
        FMLCommonHandler.instance().bus().register(new AnimationHandlerMF());
        FMLCommonHandler.instance().bus().register(new ExtendedReachMFR());
        MinecraftForge.EVENT_BUS.register(new HudHandlerMF());
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTanningRack.class, new TileEntityTanningRackRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBellows.class, new TileEntityBellowsRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmmoBox.class, new TileEntityAmmoBoxRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuern.class, new TileEntityQuernRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBigFurnace.class, new TileEntityBigFurnaceRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombPress.class, new TileEntityBombPressRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoast.class, new TileEntityRoastRenderer<>());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRack.class, new TileEntityRackRenderer<>());
    }

    public void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowMFR.class, new RenderArrowMF(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBomb());
        RenderingRegistry.registerEntityRenderingHandler(EntityMine.class, new RenderMine());
        RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new RenderShrapnel("shrapnel"));
        RenderingRegistry.registerEntityRenderingHandler(EntityFireBlast.class, RenderFireBlast::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySmoke.class, RenderSmoke::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDragonBreath.class, new RenderDragonBreath());
        RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, new RenderParachute());
        //RenderingRegistry.registerEntityRenderingHandler(EntityCogwork.class, new RenderPowerArmour()); //TODO: Fix if necessary

        RenderingRegistry.registerEntityRenderingHandler(EntityDragon.class, new RenderDragon(2F));
       // RenderingRegistry.registerEntityRenderingHandler(EntityMinotaur.class, new RenderMinotaur(new ModelMinotaur(), 1.5F));//TODO: Fix if necessary
        RenderingRegistry.registerEntityRenderingHandler(EntityHound.class, new RenderHound(new ModelHound()));
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    private void registerRenders() {
        registerEntityRenderer();

//        MinecraftForgeClient.registerItemRenderer(ToolListMFR.swordStone, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(ToolListMFR.maceStone, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(ToolListMFR.waraxeStone, new RenderSword().setAxe());
//        MinecraftForgeClient.registerItemRenderer(ToolListMFR.spearStone, new RenderSpear());
//        MinecraftForgeClient.registerItemRenderer(ToolListMFR.swordTraining, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(ToolListMFR.crossbow_custom, new RenderCrossbow(2.0F));
//
//        // STANDARD
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_dagger, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_sword, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_waraxe, new RenderSword().setAxe());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_mace, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_scythe, new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_warhammer,
//                new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_battleaxe,
//                new RenderHeavyWeapon().setBlunt().setParryable());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_greatsword,
//                new RenderHeavyWeapon().setGreatsword().setParryable());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_katana,
//                new RenderHeavyWeapon().setKatana().setParryable());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_spear, new RenderSpear());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_halbeard, new RenderSpear(true));
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_lance, new RenderLance());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_lumber, new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_saw, new RenderSaw());
//        MinecraftForgeClient.registerItemRenderer(CustomToolListMFR.standard_bow, new RenderBow(false));
//        // DRAGONFORGED
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_dagger, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_sword, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_waraxe, new RenderSword().setAxe());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_mace, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_warhammer,
//                new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_battleaxe,
//                new RenderHeavyWeapon().setBlunt().setParryable());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_greatsword,
//                new RenderHeavyWeapon().setGreatsword().setParryable());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_katana,
//                new RenderHeavyWeapon().setKatana().setParryable());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_spear, new RenderSpear());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_halbeard, new RenderSpear(true));
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_lance, new RenderLance());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_scythe,
//                new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_lumber,
//                new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_saw, new RenderSaw());
//        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_bow, new RenderBow(false));
//
//        // ORNATE
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_dagger, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_sword, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_waraxe, new RenderSword().setAxe());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_mace, new RenderSword());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_warhammer, new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_battleaxe,
//                new RenderHeavyWeapon().setBlunt().setParryable());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_greatsword,
//                new RenderHeavyWeapon().setGreatsword().setParryable());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_katana,
//                new RenderHeavyWeapon().setKatana().setParryable());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_spear, new RenderSpear());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_halbeard, new RenderSpear(true));
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_lance, new RenderLance());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_scythe, new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_lumber, new RenderHeavyWeapon().setBlunt());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_saw, new RenderSaw());
//        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_bow, new RenderBow(false));

    }
}
