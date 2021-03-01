package minefantasy.mfr.util;

import net.minecraft.network.Packet;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
            playerInstanceClass = PlayerInteractionManager.class.getDeclaredClasses()[0];
            getOrCreateChunkWatcher = ReflectionHelper.findMethod(PlayerInteractionManager.class, null, "getOrCreateChunkWatcher", int.class, int.class, boolean.class);
            sendToAllPlayersWatchingChunk = ReflectionHelper.findMethod(playerInstanceClass, null, "sendToAllPlayersWatchingChunk"  , Packet.class);
            getOrCreateChunkWatcher.setAccessible(true);
            sendToAllPlayersWatchingChunk.setAccessible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void sendToWatchers(Packet packet, WorldServer world, BlockPos pos) {
        try {
            Object playerInstance = getOrCreateChunkWatcher.invoke(world.getPlayerChunkMap(), pos.getX() >> 4, pos.getZ() >> 4, false);
            if (playerInstance != null)
                sendToAllPlayersWatchingChunk.invoke(playerInstance, packet);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
