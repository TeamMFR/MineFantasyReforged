package minefantasy.mf2.network;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.network.Packet;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;

import java.lang.reflect.Method;

/**
 * Here I used code in Railcraft.
 * <p>
 * https://github.com/Railcraft/Railcraft/blob/mc-1.7.10/src/main/java/mods/railcraft/common/util/network/PacketDispatcher.java
 */

public class NetworkUtils {
    private static final Class playerInstanceClass;
    private static final Method getOrCreateChunkWatcher;
    private static final Method sendToAllPlayersWatchingChunk;

    static {
        try {
            playerInstanceClass = PlayerManager.class.getDeclaredClasses()[0];
            getOrCreateChunkWatcher = ReflectionHelper.findMethod(PlayerManager.class, null, new String[]{"func_72690_a", "getOrCreateChunkWatcher"}, int.class, int.class, boolean.class);
            sendToAllPlayersWatchingChunk = ReflectionHelper.findMethod(playerInstanceClass, null, new String[]{"func_151251_a", "sendToAllPlayersWatchingChunk"}, Packet.class);
            getOrCreateChunkWatcher.setAccessible(true);
            sendToAllPlayersWatchingChunk.setAccessible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void sendToWatchers(Packet packet, WorldServer world, int worldX, int worldZ) {
        try {
            Object playerInstance = getOrCreateChunkWatcher.invoke(world.getPlayerManager(), worldX >> 4, worldZ >> 4, false);
            if (playerInstance != null)
                sendToAllPlayersWatchingChunk.invoke(playerInstance, packet);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
