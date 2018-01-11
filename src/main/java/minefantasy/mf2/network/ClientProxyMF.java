package minefantasy.mf2.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.helpers.ClientTickHandler;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.block.tileentity.*;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFC;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFH;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import minefantasy.mf2.block.tileentity.decor.TileEntityTrough;
import minefantasy.mf2.client.KnowledgePageRegistry;
import minefantasy.mf2.client.gui.*;
import minefantasy.mf2.client.render.*;
import minefantasy.mf2.client.render.block.*;
import minefantasy.mf2.client.render.block.component.TileEntityComponentRenderer;
import minefantasy.mf2.client.render.mob.*;
import minefantasy.mf2.entity.*;
import minefantasy.mf2.entity.mob.EntityDragon;
import minefantasy.mf2.entity.mob.EntityHound;
import minefantasy.mf2.entity.mob.EntityMinotaur;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.list.styles.DragonforgedStyle;
import minefantasy.mf2.item.list.styles.OrnateStyle;
import minefantasy.mf2.mechanics.ExtendedReachMF;
import minefantasy.mf2.mechanics.PlayerTickHandlerMF;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

/**
 * @author Anonymous Productions
 */
public class ClientProxyMF extends CommonProxyMF {

    /**
     * Is the player trying to jump (assuming no screens are open)
     */
    public static boolean isUserJumpCommand(Entity user) {
        return Minecraft.getMinecraft().currentScreen == null && user == Minecraft.getMinecraft().thePlayer
                && Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode());
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public void preInit() {
    }

    @Override
    public void registerMain() {
        super.registerMain();
        registerRenders();
    }

    @Override
    public void postInit() {
        super.postInit();
        MineFantasyAPI.init();
        KnowledgePageRegistry.registerPages();
    }

    @Override
    public void registerTickHandlers() {
        super.registerTickHandlers();
        FMLCommonHandler.instance().bus().register(new PlayerTickHandlerMF());
        FMLCommonHandler.instance().bus().register(new AnimationHandlerMF());
        FMLCommonHandler.instance().bus().register(new ExtendedReachMF());
        MinecraftForge.EVENT_BUS.register(new HudHandlerMF());
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());

        RenderingRegistry.registerBlockHandler(new RenderAnvilMF());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvilMF.class, new TileEntityAnvilMFRenderer());
        RenderingRegistry.registerBlockHandler(new RenderCarpenter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCarpenterMF.class, new TileEntityCarpenterRenderer());
        RenderingRegistry.registerBlockHandler(new RenderBombBench());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombBench.class, new TileEntityBombBenchRenderer());
        RenderingRegistry.registerBlockHandler(new RenderTanningRack());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTanningRack.class, new TileEntityTanningRackRenderer());
        RenderingRegistry.registerBlockHandler(new RenderForge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForge.class, new TileEntityForgeRenderer());
        RenderingRegistry.registerBlockHandler(new RenderBellows());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBellows.class, new TileEntityBellowsRenderer());
        RenderingRegistry.registerBlockHandler(new RenderResearch());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityResearch.class, new TileEntityResearchRenderer());
        RenderingRegistry.registerBlockHandler(new RenderTrough());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrough.class, new TileEntityTroughRenderer());
        RenderingRegistry.registerBlockHandler(new RenderBombPress());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombPress.class, new TileEntityBombPressRenderer());
        RenderingRegistry.registerBlockHandler(new RenderBloomery());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBloomery.class, new TileEntityBloomeryRenderer());
        RenderingRegistry.registerBlockHandler(new RenderCrossbowBench());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrossbowBench.class,
                new TileEntityCrossbowBenchRenderer());
        RenderingRegistry.registerBlockHandler(new RenderQuern());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuern.class, new TileEntityQuernRenderer());
        RenderingRegistry.registerBlockHandler(new RenderFirepit());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFirepit.class, new TileEntityFirepitRenderer());
        RenderingRegistry.registerBlockHandler(new RenderRoast());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoast.class, new TileEntityRoastRenderer());
        RenderingRegistry.registerBlockHandler(new RenderBigFurnace());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBigFurnace.class, new TileEntityBigFurnaceRenderer());
        RenderingRegistry.registerBlockHandler(new RenderRack());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRack.class, new TileEntityRackRenderer());
        RenderingRegistry.registerBlockHandler(new RenderAmmoBox());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmmoBox.class, new TileEntityAmmoBoxRenderer());
        RenderingRegistry.registerBlockHandler(new RenderSmokePipe());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChimney.class, new TileEntitySmokePipeRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityComponent.class, new TileEntityComponentRenderer());
    }

    public void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowMF.class, new RenderArrowMF());
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
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Minecraft mc = Minecraft.getMinecraft();
        if (ID == 0) {
            TileEntity tile = world.getTileEntity(x, y, z);
            // int meta = world.getBlockMetadata(x, y, z);

            if (tile == null) {
                return null;
            }

            if (tile instanceof TileEntityAnvilMF) {
                return new GuiAnvilMF(player.inventory, (TileEntityAnvilMF) tile);
            }
            if (tile instanceof TileEntityCarpenterMF) {
                return new GuiCarpenterMF(player.inventory, (TileEntityCarpenterMF) tile);
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
            if (x == 1 && player.getHeldItem() != null) {
                return new GuiReload(player.inventory, player.getHeldItem());
            }
        }
        return null;
    }

    private void registerRenders() {
        registerEntityRenderer();

        MinecraftForgeClient.registerItemRenderer(ToolListMF.swordStone, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(ToolListMF.maceStone, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(ToolListMF.waraxeStone, new RenderSword().setAxe());
        MinecraftForgeClient.registerItemRenderer(ToolListMF.spearStone, new RenderSpear());
        MinecraftForgeClient.registerItemRenderer(ToolListMF.swordTraining, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(ToolListMF.crossbow_custom, new RenderCrossbow(2.0F));

        // STANDARD
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_dagger, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_sword, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_waraxe, new RenderSword().setAxe());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_mace, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_scythe, new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_warhammer,
                new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_battleaxe,
                new RenderHeavyWeapon().setBlunt().setParryable());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_greatsword,
                new RenderHeavyWeapon().setGreatsword().setParryable());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_katana,
                new RenderHeavyWeapon().setKatana().setParryable());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_spear, new RenderSpear());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_halbeard, new RenderSpear(true));
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_lance, new RenderLance());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_lumber, new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_saw, new RenderSaw());
        MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_bow, new RenderBow(false));
        // DRAGONFORGED
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_dagger, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_sword, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_waraxe, new RenderSword().setAxe());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_mace, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_warhammer,
                new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_battleaxe,
                new RenderHeavyWeapon().setBlunt().setParryable());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_greatsword,
                new RenderHeavyWeapon().setGreatsword().setParryable());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_katana,
                new RenderHeavyWeapon().setKatana().setParryable());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_spear, new RenderSpear());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_halbeard, new RenderSpear(true));
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_lance, new RenderLance());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_scythe,
                new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_lumber,
                new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_saw, new RenderSaw());
        MinecraftForgeClient.registerItemRenderer(DragonforgedStyle.dragonforged_bow, new RenderBow(false));

        // ORNATE
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_dagger, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_sword, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_waraxe, new RenderSword().setAxe());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_mace, new RenderSword());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_warhammer, new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_battleaxe,
                new RenderHeavyWeapon().setBlunt().setParryable());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_greatsword,
                new RenderHeavyWeapon().setGreatsword().setParryable());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_katana,
                new RenderHeavyWeapon().setKatana().setParryable());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_spear, new RenderSpear());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_halbeard, new RenderSpear(true));
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_lance, new RenderLance());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_scythe, new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_lumber, new RenderHeavyWeapon().setBlunt());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_saw, new RenderSaw());
        MinecraftForgeClient.registerItemRenderer(OrnateStyle.ornate_bow, new RenderBow(false));

    }
}
