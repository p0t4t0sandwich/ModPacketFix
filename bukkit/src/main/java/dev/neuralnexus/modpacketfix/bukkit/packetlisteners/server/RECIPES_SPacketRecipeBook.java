package dev.neuralnexus.modpacketfix.bukkit.packetlisteners.server;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

/**
 * Fixes the recipe book packet being too large for the client to handle.
 */
public class RECIPES_SPacketRecipeBook extends PacketAdapter {
    private final BukkitModPacketFixPlugin plugin;
    public RECIPES_SPacketRecipeBook(BukkitModPacketFixPlugin plugin) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.RECIPES);
        this.plugin = plugin;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        if (plugin.isForgeUser(player)) {
            event.setCancelled(true);
            plugin.getLogger().info("Skipping Recipe Book packet [SPacketRecipeBook] being sent to " + player.getName());
        }
    }
}
