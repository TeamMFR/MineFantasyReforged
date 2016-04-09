package minefantasy.mf2.network;

import minefantasy.mf2.api.helpers.ClientTickHandler;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.block.tileentity.TileEntityAnvilMF;
import minefantasy.mf2.block.tileentity.TileEntityBellows;
import minefantasy.mf2.block.tileentity.TileEntityBloomery;
import minefantasy.mf2.block.tileentity.TileEntityBombBench;
import minefantasy.mf2.block.tileentity.TileEntityBombPress;
import minefantasy.mf2.block.tileentity.TileEntityCarpenterMF;
import minefantasy.mf2.block.tileentity.TileEntityCrossbowBench;
import minefantasy.mf2.block.tileentity.TileEntityCrucible;
import minefantasy.mf2.block.tileentity.TileEntityForge;
import minefantasy.mf2.block.tileentity.TileEntityQuern;
import minefantasy.mf2.block.tileentity.TileEntityResearch;
import minefantasy.mf2.block.tileentity.TileEntityTanningRack;
import minefantasy.mf2.block.tileentity.TileEntityTrough;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFC;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFH;
import minefantasy.mf2.client.KnowledgePageRegistry;
import minefantasy.mf2.client.gui.GuiAnvilMF;
import minefantasy.mf2.client.gui.GuiBlastChamber;
import minefantasy.mf2.client.gui.GuiBlastHeater;
import minefantasy.mf2.client.gui.GuiBloomery;
import minefantasy.mf2.client.gui.GuiBombBench;
import minefantasy.mf2.client.gui.GuiCarpenterMF;
import minefantasy.mf2.client.gui.GuiCrossbowBench;
import minefantasy.mf2.client.gui.GuiCrucible;
import minefantasy.mf2.client.gui.GuiForge;
import minefantasy.mf2.client.gui.GuiKnowledge;
import minefantasy.mf2.client.gui.GuiKnowledgeEntry;
import minefantasy.mf2.client.gui.GuiQuern;
import minefantasy.mf2.client.gui.GuiReload;
import minefantasy.mf2.client.gui.GuiResearchBlock;
import minefantasy.mf2.client.render.AnimationHandlerMF;
import minefantasy.mf2.client.render.HudHandlerMF;
import minefantasy.mf2.client.render.RenderArrowMF;
import minefantasy.mf2.client.render.RenderBombIcon;
import minefantasy.mf2.client.render.RenderBow;
import minefantasy.mf2.client.render.RenderCrossbow;
import minefantasy.mf2.client.render.RenderDragonBreath;
import minefantasy.mf2.client.render.RenderFireBlast;
import minefantasy.mf2.client.render.RenderHeavyWeapon;
import minefantasy.mf2.client.render.RenderLance;
import minefantasy.mf2.client.render.RenderMine;
import minefantasy.mf2.client.render.RenderParachute;
import minefantasy.mf2.client.render.RenderSaw;
import minefantasy.mf2.client.render.RenderShrapnel;
import minefantasy.mf2.client.render.RenderSpear;
import minefantasy.mf2.client.render.RenderSword;
import minefantasy.mf2.client.render.block.RenderAnvilMF;
import minefantasy.mf2.client.render.block.RenderBellows;
import minefantasy.mf2.client.render.block.RenderBloomery;
import minefantasy.mf2.client.render.block.RenderBombBench;
import minefantasy.mf2.client.render.block.RenderBombPress;
import minefantasy.mf2.client.render.block.RenderCarpenter;
import minefantasy.mf2.client.render.block.RenderCrossbowBench;
import minefantasy.mf2.client.render.block.RenderForge;
import minefantasy.mf2.client.render.block.RenderQuern;
import minefantasy.mf2.client.render.block.RenderResearch;
import minefantasy.mf2.client.render.block.RenderTanningRack;
import minefantasy.mf2.client.render.block.RenderTrough;
import minefantasy.mf2.client.render.block.TileEntityAnvilMFRenderer;
import minefantasy.mf2.client.render.block.TileEntityBellowsRenderer;
import minefantasy.mf2.client.render.block.TileEntityBloomeryRenderer;
import minefantasy.mf2.client.render.block.TileEntityBombBenchRenderer;
import minefantasy.mf2.client.render.block.TileEntityBombPressRenderer;
import minefantasy.mf2.client.render.block.TileEntityCarpenterRenderer;
import minefantasy.mf2.client.render.block.TileEntityCrossbowBenchRenderer;
import minefantasy.mf2.client.render.block.TileEntityForgeRenderer;
import minefantasy.mf2.client.render.block.TileEntityQuernRenderer;
import minefantasy.mf2.client.render.block.TileEntityResearchRenderer;
import minefantasy.mf2.client.render.block.TileEntityTanningRackRenderer;
import minefantasy.mf2.client.render.block.TileEntityTroughRenderer;
import minefantasy.mf2.client.render.mob.ModelDragon;
import minefantasy.mf2.client.render.mob.ModelMinotaur;
import minefantasy.mf2.client.render.mob.RenderDragon;
import minefantasy.mf2.client.render.mob.RenderMinotaur;
import minefantasy.mf2.entity.EntityArrowMF;
import minefantasy.mf2.entity.EntityBomb;
import minefantasy.mf2.entity.EntityDragonBreath;
import minefantasy.mf2.entity.EntityFireBlast;
import minefantasy.mf2.entity.EntityMine;
import minefantasy.mf2.entity.EntityParachute;
import minefantasy.mf2.entity.EntityShrapnel;
import minefantasy.mf2.entity.EntitySmoke;
import minefantasy.mf2.entity.mob.EntityDragon;
import minefantasy.mf2.entity.mob.EntityMinotaur;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.mechanics.ExtendedReachMF;
import minefantasy.mf2.mechanics.PlayerTickHandlerMF;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
/**
 * @author Anonymous Productions
 */
public class ClientProxyMF extends CommonProxyMF
{

	@Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }
	
	@Override
	public void preInit()
	{
	}
	
	@Override
	public void registerMain() 
	{
		super.registerMain();
	}
	
	@Override
	public void postInit()
	{
		super.postInit();
		registerRenders();
		KnowledgePageRegistry.registerPages();
	}
	
	@Override
	public void registerTickHandlers()
	{
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrossbowBench.class, new TileEntityCrossbowBenchRenderer());
		RenderingRegistry.registerBlockHandler(new RenderQuern());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuern.class, new TileEntityQuernRenderer());
	}
	
	public void registerEntityRenderer()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityArrowMF.class, new RenderArrowMF());
		RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBombIcon());//Switch to RenderBomb when syncing is fixed
		RenderingRegistry.registerEntityRenderingHandler(EntityMine.class, new RenderMine());
		RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new RenderShrapnel("shrapnel"));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireBlast.class, new RenderFireBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntitySmoke.class, new RenderFireBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonBreath.class, new RenderDragonBreath());
		RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, new RenderParachute());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityDragon.class, new RenderDragon(new ModelDragon(), 2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMinotaur.class, new RenderMinotaur(new ModelMinotaur(), 1.5F));
	}
	
	@Override
    public EntityPlayer getClientPlayer()
	{
        return Minecraft.getMinecraft().thePlayer;
    }
	@Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
		Minecraft mc = Minecraft.getMinecraft();
		if(ID == 0)
		{
	    	TileEntity tile = world.getTileEntity(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			
			if(tile != null && tile instanceof TileEntityAnvilMF)
			{
				return new GuiAnvilMF(player.inventory, (TileEntityAnvilMF) tile);
			}
			if(tile != null && tile instanceof TileEntityCarpenterMF)
			{
				return new GuiCarpenterMF(player.inventory, (TileEntityCarpenterMF) tile);
			}
			if(tile != null && tile instanceof TileEntityBombBench)
			{
				return new GuiBombBench(player.inventory, (TileEntityBombBench) tile);
			}
			if(tile != null && tile instanceof TileEntityBlastFH)
			{
				return new GuiBlastHeater(player.inventory, (TileEntityBlastFH) tile);
			}
			if(tile != null && tile instanceof TileEntityBlastFC)
			{
				return new GuiBlastChamber(player.inventory, (TileEntityBlastFC) tile);
			}
			if(tile != null && tile instanceof TileEntityCrucible)
			{
				return new GuiCrucible(player.inventory, (TileEntityCrucible) tile);
			}
			if(tile != null && tile instanceof TileEntityForge)
			{
				return new GuiForge(player.inventory, (TileEntityForge) tile);
			}
			if(tile != null && tile instanceof TileEntityResearch)
			{
				return new GuiResearchBlock(player.inventory, (TileEntityResearch) tile);
			}
			if(tile != null && tile instanceof TileEntityBloomery)
			{
				return new GuiBloomery(player.inventory, (TileEntityBloomery) tile);
			}
			if(tile != null && tile instanceof TileEntityCrossbowBench)
			{
				return new GuiCrossbowBench(player.inventory, (TileEntityCrossbowBench) tile);
			}
			if(tile != null && tile instanceof TileEntityQuern)
			{
				return new GuiQuern(player.inventory, (TileEntityQuern) tile);
			}
			 return null;
		}
		if(ID == 1)
		{
			if(x == 0)
			{//GuiAchievements
				if(y >= 0)
				{
					return new GuiKnowledgeEntry(mc.currentScreen, InformationList.knowledgeList.get(y));
				}
				return new GuiKnowledge(player);
			}
			if(x == 1 && player.getHeldItem() != null)
	    	{
	    		return new GuiReload(player.inventory, player.getHeldItem());
	    	}
		}
        return null;
    }

	private void registerRenders() 
	{
		registerEntityRenderer();
		
		MinecraftForgeClient.registerItemRenderer(ToolListMF.swordStone, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(ToolListMF.maceStone, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(ToolListMF.waraxeStone, new RenderSword().setAxe());
		MinecraftForgeClient.registerItemRenderer(ToolListMF.spearStone, new RenderSpear());
		MinecraftForgeClient.registerItemRenderer(ToolListMF.swordTraining, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(ToolListMF.crossbow_custom, new RenderCrossbow(2.0F));
	
		//STANDARD
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_dagger, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_sword, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_waraxe, new RenderSword().setAxe());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_mace, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_scythe, new RenderHeavyWeapon().setBlunt());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_warhammer, new RenderHeavyWeapon().setBlunt());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_battleaxe, new RenderHeavyWeapon().setBlunt().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_greatsword, new RenderHeavyWeapon().setGreatsword().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_katana, new RenderHeavyWeapon().setKatana().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_spear, new RenderSpear());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_halbeard, new RenderSpear(true));
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_lance, new RenderLance());
		
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_dagger, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_sword, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_waraxe, new RenderSword().setAxe());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_mace, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_warhammer, new RenderHeavyWeapon().setBlunt());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_battleaxe, new RenderHeavyWeapon().setBlunt().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_greatsword, new RenderHeavyWeapon().setGreatsword().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_katana, new RenderHeavyWeapon().setKatana().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_spear, new RenderSpear());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_halbeard, new RenderSpear(true));
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dragonforged_lance, new RenderLance());
		
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_saw, new RenderSaw());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.standard_bow, new RenderBow(false));
		
		//DRAGONFORGED
		
		//DWARVEN
		/*
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_sword, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_waraxe, new RenderSword().setAxe());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_mace, new RenderSword());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_scythe, new RenderHeavyWeapon().setBlunt());
		
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_warhammer, new RenderHeavyWeapon().setBlunt());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_battleaxe, new RenderHeavyWeapon().setBlunt().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_greatsword, new RenderHeavyWeapon().setGreatsword().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_katana, new RenderHeavyWeapon().setKatana().setParryable());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_spear, new RenderSpear());
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.dwarven_halbeard, new RenderSpear(true));
		
		//GNOMISH
		MinecraftForgeClient.registerItemRenderer(CustomToolListMF.gnomish_saw, new RenderSaw());
		*/
	}
}
