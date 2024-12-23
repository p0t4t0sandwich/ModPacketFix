package dev.neuralnexus.modpacketfix.bukkit.messagelisteners;

import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listens for the NeoForge network plugin message containing the client's modlist
 */
public class NeoForgeListener implements PluginMessageListener {
    public static final String REGISTER = "neoforge:register";
    public static final String NETWORK = "neoforge:network";

    private final BukkitModPacketFixPlugin plugin;
    public NeoForgeListener(BukkitModPacketFixPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equals(NETWORK) && !channel.equals(REGISTER)) {
            return;
        }
        // Handle incoming message
        System.out.println("Received message from " + player.getName());
        System.out.println("Message: " + new String(message));
    }
}
