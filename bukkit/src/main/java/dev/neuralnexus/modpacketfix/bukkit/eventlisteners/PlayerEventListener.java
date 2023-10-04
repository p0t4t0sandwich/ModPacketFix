package dev.neuralnexus.modpacketfix.bukkit.eventlisteners;

import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerEventListener implements Listener {
    private final BukkitModPacketFixPlugin plugin;
    public PlayerEventListener(BukkitModPacketFixPlugin plugin) {
        this.plugin = plugin;
    }

    public HashMap<String, Long> joinTimes = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        joinTimes.put(event.getPlayer().getName(), System.currentTimeMillis());
    }

    /**
     * Remove the player from the forge users list when they disconnect
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.isForgeUser(event.getPlayer())) {
            plugin.removeForgeUser(event.getPlayer());
            plugin.getLogger().info("Unregistering " + event.getPlayer().getName() + " as a forge user");
        }

        // Add the user to the forge users list if the time delta is less than 5 seconds
        if (joinTimes.containsKey(event.getPlayer().getName())) {
            long joinTime = joinTimes.get(event.getPlayer().getName());
            long delta = System.currentTimeMillis() - joinTime;
            if (delta < 5000) {
                plugin.addForgeUser(event.getPlayer());
                plugin.getLogger().info("Disconnected too quickly, registering " + event.getPlayer().getName() + " as a forge user");
            }
            joinTimes.remove(event.getPlayer().getName());
        }
    }
}
