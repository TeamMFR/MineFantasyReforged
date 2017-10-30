package minefantasy.mf2.util;

import com.google.common.collect.Lists;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.config.ConfigIntegration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Method;
import java.util.List;

public final class BukkitUtils {

    private static final Method getBukkitEntity;
    private static final List<RegisteredListener> listeners = Lists.newArrayList();

    static {
        try {
            getBukkitEntity = Entity.class.getDeclaredMethod("getBukkitEntity");
            getBukkitEntity.setAccessible(true);
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to hook CraftBukkit method!", throwable);
        }
    }

    public static final boolean cantBreakBlock(EntityPlayer player, int x, int y, int z) {
        try {
            Player bukkitPlayer = toBukkitEntity(player);
            BlockBreakEvent event = new BlockBreakEvent(bukkitPlayer.getWorld().getBlockAt(x, y, z), bukkitPlayer);
            callEvent(event);
            return event.isCancelled();
        } catch (Throwable throwable) {
            MFLogUtil.logWarn(String.format("Failed call BlockBreakEvent: [Player: %s, X:%d, Y:%d, Z:%d]",
                    String.valueOf(player), x, y, z));
            if (MineFantasyII.isDebug())
                throwable.printStackTrace();
        }
        return true;
    }

    public static final boolean cantDamage(Entity damager, Entity damagee) {
        try {
            @SuppressWarnings("deprecation")
            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(toBukkitEntity(damager),
                    toBukkitEntity(damagee), DamageCause.ENTITY_ATTACK, 0D);
            callEvent(event);
            return event.isCancelled();
        } catch (Throwable throwable) {
            MFLogUtil.logWarn(String.format("Failed call EntityDamageByEntityEvent: [Damager: %s, Damagee: %s]",
                    String.valueOf(damager), String.valueOf(damagee)));
            if (MineFantasyII.isDebug())
                throwable.printStackTrace();
        }
        return true;
    }

    private static final org.bukkit.entity.Entity toBukkitEntity(Entity entity) throws Exception {
        return (org.bukkit.entity.Entity) getBukkitEntity.invoke(entity);
    }

    private static final Player toBukkitEntity(EntityPlayer player) throws Exception {
        return (Player) getBukkitEntity.invoke(player);
    }

    public static final void onServerStarted() {
        PluginManager plManager = Bukkit.getPluginManager();
        for (String plName : ConfigIntegration.pluginsList)
            listeners.addAll(HandlerList.getRegisteredListeners(plManager.getPlugin(plName)));
    }

    private static final void callEvent(Event event) {
        for (RegisteredListener listener : listeners)
            try {
                listener.callEvent(event);
            } catch (Throwable throwable) {
                if (MineFantasyII.isDebug())
                    throwable.printStackTrace();
            }
    }
}
