package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import minefantasy.mf2.MineFantasyII;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

import java.util.Hashtable;
import java.util.Map;

public class PacketHandlerMF {
    public Map<String, PacketMF> packetList = new Hashtable<String, PacketMF>();
    public Map<String, FMLEventChannel> channels = new Hashtable<String, FMLEventChannel>();

    public PacketHandlerMF() {
        packetList.put(StaminaPacket.packetName, new StaminaPacket());
        packetList.put(ParryPacket.packetName, new ParryPacket());
        packetList.put(HitSoundPacket.packetName, new HitSoundPacket());
        packetList.put(AnvilPacket.packetName, new AnvilPacket());
        packetList.put(CarpenterPacket.packetName, new CarpenterPacket());
        packetList.put(KnowledgePacket.packetName, new KnowledgePacket());
        packetList.put(ResearchRequest.packetName, new ResearchRequest());
        packetList.put(ChimneyPacket.packetName, new ChimneyPacket());
        packetList.put(SkillPacket.packetName, new SkillPacket());
        packetList.put(LevelupPacket.packetName, new LevelupPacket());
        packetList.put(ForgePacket.packetName, new ForgePacket());
        packetList.put(ResearchTablePacket.packetName, new ResearchTablePacket());
        packetList.put(TroughPacket.packetName, new TroughPacket());
        packetList.put(BombBenchPacket.packetName, new BombBenchPacket());
        packetList.put(TannerPacket.packetName, new TannerPacket());
        packetList.put(RoadPacket.packetName, new RoadPacket());
        packetList.put(BloomeryPacket.packetName, new BloomeryPacket());
        packetList.put(CrossbowBenchPacket.packetName, new CrossbowBenchPacket());
        packetList.put(TileInventoryPacket.packetName, new TileInventoryPacket());
        packetList.put(BigFurnacePacket.packetName, new BigFurnacePacket());
        packetList.put(DodgeCommand.packetName, new DodgeCommand());
        packetList.put(RackCommand.packetName, new RackCommand());
        packetList.put(AmmoBoxPacket.packetName, new AmmoBoxPacket());
        packetList.put(WoodDecorPacket.packetName, new WoodDecorPacket());
        packetList.put(CogworkControlPacket.packetName, new CogworkControlPacket());
        packetList.put(StorageBlockPacket.packetName, new StorageBlockPacket());
    }

    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        packetList.get(event.packet.channel()).process(event.packet.payload(),
                ((NetHandlerPlayServer) event.handler).playerEntity);
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        packetList.get(event.packet.channel()).process(event.packet.payload(), MineFantasyII.proxy.getClientPlayer());
    }

    public void sendPacketToPlayer(FMLProxyPacket packet, EntityPlayerMP player) {
        channels.get(packet.channel()).sendTo(packet, player);
    }

    public void sendPacketToServer(FMLProxyPacket packet) {
        channels.get(packet.channel()).sendToServer(packet);
    }

    public void sendPacketAround(Entity entity, double range, FMLProxyPacket packet) {
        channels.get(packet.channel()).sendToAllAround(packet,
                new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, range));
    }

    public void sendPacketToAll(FMLProxyPacket packet) {
        channels.get(packet.channel()).sendToAll(packet);
    }
}
