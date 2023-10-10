package dev.neuralnexus.modpacketfix.bukkit.messagelisteners;

import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listens for the brand plugin message and registers the player as a forge user if they are using forge
 */
public class BrandListener implements PluginMessageListener {
    private final BukkitModPacketFixPlugin plugin;
    public BrandListener(BukkitModPacketFixPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (channel.equals("MC|Brand") || channel.equals("minecraft:brand")) {
            String clientName = new String(message).trim().toLowerCase();
            if (clientName.contains("forge")) {
                plugin.addForgeUser(player);
                plugin.getLogger().info("Registered " + player.getName() + " as a forge user");
            } else {
                if (plugin.isForgeUser(player)) {
                    plugin.removeForgeUser(player);
                    plugin.getLogger().info("Unregistered " + player.getName() + " as a forge user");
                }
            }
        }
    }
}
