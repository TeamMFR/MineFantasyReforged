package minefantasy.mfr.proxy;

import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.refine.ISmokeHandler;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.block.tile.TileEntityAnvilMFR;
import minefantasy.mfr.block.tile.TileEntityBigFurnace;
import minefantasy.mfr.block.tile.TileEntityBloomery;
import minefantasy.mfr.block.tile.TileEntityBombBench;
import minefantasy.mfr.block.tile.TileEntityCarpenterMFR;
import minefantasy.mfr.block.tile.TileEntityCrossbowBench;
import minefantasy.mfr.block.tile.TileEntityCrucible;
import minefantasy.mfr.block.tile.TileEntityForge;
import minefantasy.mfr.block.tile.TileEntityQuern;
import minefantasy.mfr.block.tile.TileEntityResearch;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFC;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFH;
import minefantasy.mfr.container.ContainerAnvilMF;
import minefantasy.mfr.container.ContainerBigFurnace;
import minefantasy.mfr.container.ContainerBlastChamber;
import minefantasy.mfr.container.ContainerBlastHeater;
import minefantasy.mfr.container.ContainerBloomery;
import minefantasy.mfr.container.ContainerBombBench;
import minefantasy.mfr.container.ContainerCarpenterMFR;
import minefantasy.mfr.container.ContainerCrossbowBench;
import minefantasy.mfr.container.ContainerCrucible;
import minefantasy.mfr.container.ContainerForge;
import minefantasy.mfr.container.ContainerQuern;
import minefantasy.mfr.container.ContainerReload;
import minefantasy.mfr.container.ContainerResearch;
import minefantasy.mfr.entity.EntitySmoke;
import minefantasy.mfr.hunger.HungerSystemMFR;
import minefantasy.mfr.init.EntityListMF;
import minefantasy.mfr.integration.CustomStone;
import minefantasy.mfr.item.archery.ArrowFireFlint;
import minefantasy.mfr.item.archery.ArrowFirerMF;
import minefantasy.mfr.mechanics.ArrowHandlerMF;
import minefantasy.mfr.mechanics.CombatMechanics;
import minefantasy.mfr.mechanics.EventManagerMFR;
import minefantasy.mfr.mechanics.MonsterUpgrader;
import minefantasy.mfr.mechanics.PlayerTickHandlerMF;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
/**
 * @author Anonymous Productions
 */
public class CommonProxy implements IGuiHandler, ISmokeHandler{

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 1 && x == 1 && player.getHeldItemMainhand() != null) {
            return new ContainerReload(player.inventory, player.getHeldItemMainhand());
        } else if (ID == 0) {
            TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
            if (tile == null) {
                return null;
            }

            if (tile instanceof TileEntityAnvilMFR) {
                return new ContainerAnvilMF(player.inventory, (TileEntityAnvilMFR) tile);
            }
            if (tile instanceof TileEntityCarpenterMFR) {
                return new ContainerCarpenterMFR(player.inventory, (TileEntityCarpenterMFR) tile);
            }
            if (tile instanceof TileEntityBombBench) {
                return new ContainerBombBench(player.inventory, (TileEntityBombBench) tile);
            }
            if (tile instanceof TileEntityBlastFH) {
                return new ContainerBlastHeater(player.inventory, (TileEntityBlastFH) tile);
            }
            if (tile instanceof TileEntityBlastFC) {
                return new ContainerBlastChamber(player.inventory, (TileEntityBlastFC) tile);
            }
            if (tile instanceof TileEntityCrucible) {
                return new ContainerCrucible(player.inventory, (TileEntityCrucible) tile);
            }
            if (tile instanceof TileEntityForge) {
                return new ContainerForge(player.inventory, (TileEntityForge) tile);
            }
            if (tile instanceof TileEntityResearch) {
                return new ContainerResearch(player.inventory, (TileEntityResearch) tile);
            }
            if (tile instanceof TileEntityBloomery) {
                return new ContainerBloomery(player.inventory, (TileEntityBloomery) tile);
            }
            if (tile instanceof TileEntityCrossbowBench) {
                return new ContainerCrossbowBench(player.inventory, (TileEntityCrossbowBench) tile);
            }
            if (tile instanceof TileEntityQuern) {
                return new ContainerQuern(player.inventory, (TileEntityQuern) tile);
            }
            if (tile instanceof TileEntityBigFurnace) {
                return new ContainerBigFurnace(player, (TileEntityBigFurnace) tile);
            }
        }
        return null;
    }

    public World getClientWorld() {
        return null;
    }

    public void registerMain() {
        AmmoMechanicsMFR.addHandler(new ArrowFireFlint());
        AmmoMechanicsMFR.addHandler(new ArrowFirerMF());
        SmokeMechanics.handler = this;
    }

//    protected void registerTileEntities() {
//        GameRegistry.registerTileEntity(TileEntityAnvilMFR.class, "MFR_Anvil");
//        GameRegistry.registerTileEntity(TileEntityCarpenterMFR.class, "MFR_CarpenterBench");
//        GameRegistry.registerTileEntity(TileEntityBombBench.class, "MFR_BombBench");
//        GameRegistry.registerTileEntity(TileEntityCrossbowBench.class, "MFR_CrossbowBench");
//        GameRegistry.registerTileEntity(TileEntityBlastFC.class, "MFR_BlastChamber");
//        GameRegistry.registerTileEntity(TileEntityBlastFH.class, "MFR_BlastHeater");
//        GameRegistry.registerTileEntity(TileEntityCrucible.class, "MFR_Crucible");
//        GameRegistry.registerTileEntity(TileEntityChimney.class, "MFR_Chimney");
//        GameRegistry.registerTileEntity(TileEntityTanningRack.class, "MFR_Tanner");
//        GameRegistry.registerTileEntity(TileEntityForge.class, "MFR_Forge");
//        GameRegistry.registerTileEntity(TileEntityBellows.class, "MFR_Bellows");
//        GameRegistry.registerTileEntity(TileEntityResearch.class, "MFR_Research");
//        GameRegistry.registerTileEntity(TileEntityTrough.class, "MFR_Trough");
//        GameRegistry.registerTileEntity(TileEntityBombPress.class, "MFR_BombPress");
//        GameRegistry.registerTileEntity(TileEntityBloomery.class, "MFR_Bloomery");
//        GameRegistry.registerTileEntity(TileEntityQuern.class, "MFR_Quern");
//        GameRegistry.registerTileEntity(TileEntityFirepit.class, "MFR_Firepit");
//        GameRegistry.registerTileEntity(TileEntityRoast.class, "MFR_SpitRoast");
//        GameRegistry.registerTileEntity(TileEntityBigFurnace.class, "MFR_BigFurnace");
//        GameRegistry.registerTileEntity(TileEntityRack.class, "MFR_Rack");
//        GameRegistry.registerTileEntity(TileEntityAmmoBox.class, "MFR_AmmoBox");
//        GameRegistry.registerTileEntity(TileEntityWorldGenMarker.class, "MFR_WorldGenFlag");
//        GameRegistry.registerTileEntity(TileEntityComponent.class, "MFR_ComponentTile");
//        GameRegistry.registerTileEntity(TileEntityRoad.class, "MFR_Road");
//    }

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void registerTickHandlers() {
        FMLCommonHandler.instance().bus().register(new PlayerTickHandlerMF());
        FMLCommonHandler.instance().bus().register(new HungerSystemMFR());
        MinecraftForge.EVENT_BUS.register(new EventManagerMFR());
        MinecraftForge.EVENT_BUS.register(new CombatMechanics());
        MinecraftForge.EVENT_BUS.register(new MonsterUpgrader());
        MinecraftForge.EVENT_BUS.register(new ArrowHandlerMF());
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

    public void postInit(FMLPostInitializationEvent e) {
        CustomStone.init();
        EntityListMF.register();
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