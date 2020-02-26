package minefantasy.mfr.proxy;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.helpers.ClientTickHandler;
import minefantasy.mfr.api.knowledge.InformationList;
import minefantasy.mfr.block.tile.*;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFC;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFH;
import minefantasy.mfr.block.tile.decor.TileEntityAmmoBox;
import minefantasy.mfr.block.tile.decor.TileEntityRack;
import minefantasy.mfr.block.tile.decor.TileEntityTrough;
import minefantasy.mfr.client.KnowledgePageRegistry;
import minefantasy.mfr.client.gui.*;
import minefantasy.mfr.client.render.*;
import minefantasy.mfr.client.render.block.*;
import minefantasy.mfr.client.render.block.component.TileEntityComponentRenderer;
import minefantasy.mfr.client.render.mob.*;
import minefantasy.mfr.entity.*;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.entity.mob.EntityHound;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.init.DragonforgedStyle;
import minefantasy.mfr.init.OrnateStyle;
import minefantasy.mfr.mechanics.ExtendedReachMFR;
import minefantasy.mfr.mechanics.PlayerTickHandlerMF;
import minefantasy.mfr.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author Anonymous Productions
 */
public class ClientProxy extends CommonProxy {

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
    }

    @Override
    public void registerMain() {
        super.registerMain();
//        registerRenders();
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

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvilMFR.class, new TileEntityAnvilMFRRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCarpenterMFR.class, new TileEntityCarpenterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombBench.class, new TileEntityBombBenchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTanningRack.class, new TileEntityTanningRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForge.class, new TileEntityForgeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBellows.class, new TileEntityBellowsRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityResearch.class, new TileEntityResearchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrough.class, new TileEntityTroughRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombPress.class, new TileEntityBombPressRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBloomery.class, new TileEntityBloomeryRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrossbowBench.class, new TileEntityCrossbowBenchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuern.class, new TileEntityQuernRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFirepit.class, new TileEntityFirepitRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoast.class, new TileEntityRoastRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBigFurnace.class, new TileEntityBigFurnaceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRack.class, new TileEntityRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmmoBox.class, new TileEntityAmmoBoxRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChimney.class, new TileEntitySmokePipeRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityComponent.class, new TileEntityComponentRenderer());
    }

    public void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowMFR.class, new RenderArrowMF());
        RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBombIcon());// Switch to RenderBomb
        // when syncing is
        // fixed
        RenderingRegistry.registerEntityRenderingHandler(EntityMine.class, new RenderMine());
        RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new RenderShrapnel("shrapnel"));
        RenderingRegistry.registerEntityRenderingHandler(EntityFireBlast.class, new RenderFireBlast());
        RenderingRegistry.registerEntityRenderingHandler(EntitySmoke.class, new RenderFireBlast());
        RenderingRegistry.registerEntityRenderingHandler(EntityDragonBreath.class, new RenderDragonBreath());
        RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, new RenderParachute());
        RenderingRegistry.registerEntityRenderingHandler(EntityCogwork.class, new RenderPowerArmour());

        RenderingRegistry.registerEntityRenderingHandler(EntityDragon.class, new RenderDragon(2F));
        RenderingRegistry.registerEntityRenderingHandler(EntityMinotaur.class,
                new RenderMinotaur(new ModelMinotaur(), 1.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHound.class, new RenderHound(new ModelHound()));
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Minecraft mc = Minecraft.getMinecraft();
        if (ID == 0) {
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            // int meta = world.getBlockMetadata(x, y, z);

            if (tile == null) {
                return null;
            }

            if (tile instanceof TileEntityAnvilMFR) {
                return new GuiAnvilMF(player.inventory, (TileEntityAnvilMFR) tile);
            }
            if (tile instanceof TileEntityCarpenterMFR) {
                return new GuiCarpenterMF(player.inventory, (TileEntityCarpenterMFR) tile);
            }
            if (tile instanceof TileEntityBombBench) {
                return new GuiBombBench(player.inventory, (TileEntityBombBench) tile);
            }
            if (tile instanceof TileEntityBlastFH) {
                return new GuiBlastHeater(player.inventory, (TileEntityBlastFH) tile);
            }
            if (tile instanceof TileEntityBlastFC) {
                return new GuiBlastChamber(player.inventory, (TileEntityBlastFC) tile);
            }
            if (tile instanceof TileEntityCrucible) {
                return new GuiCrucible(player.inventory, (TileEntityCrucible) tile);
            }
            if (tile instanceof TileEntityForge) {
                return new GuiForge(player.inventory, (TileEntityForge) tile);
            }
            if (tile instanceof TileEntityResearch) {
                return new GuiResearchBlock(player.inventory, (TileEntityResearch) tile);
            }
            if (tile instanceof TileEntityBloomery) {
                return new GuiBloomery(player.inventory, (TileEntityBloomery) tile);
            }
            if (tile instanceof TileEntityCrossbowBench) {
                return new GuiCrossbowBench(player.inventory, (TileEntityCrossbowBench) tile);
            }
            if (tile instanceof TileEntityQuern) {
                return new GuiQuern(player.inventory, (TileEntityQuern) tile);
            }
            if (tile instanceof TileEntityBigFurnace) {
                return new GuiBigFurnace(player, (TileEntityBigFurnace) tile);
            }
            return null;
        }
        if (ID == 1) {
            if (x == 0) {// GuiAchievements
                if (y >= 0) {
                    return new GuiKnowledgeEntry(mc.currentScreen, InformationList.knowledgeList.get(y));
                }
                return new GuiKnowledge(player);
            }
            if (x == 1 && player.getHeldItemMainhand() != null) {
                return new GuiReload(player.inventory, player.getHeldItemMainhand());
            }
        }
        return null;
    }

//    private void registerRenders() {
//        registerEntityRenderer();
//
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
//
//    }
}
