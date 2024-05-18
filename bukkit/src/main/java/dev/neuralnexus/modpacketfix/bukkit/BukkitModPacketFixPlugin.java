package dev.neuralnexus.modpacketfix.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.neuralnexus.modpacketfix.bukkit.config.ConfigLoader;
import dev.neuralnexus.modpacketfix.bukkit.eventlisteners.PlayerEventListener;
import dev.neuralnexus.modpacketfix.bukkit.messagelisteners.BrandListener;
import dev.neuralnexus.modpacketfix.bukkit.packetlisteners.server.RECIPES_SPacketRecipeBook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.ArrayList;

/**
 * The ModPacketFix Bukkit plugin.
 */
public class BukkitModPacketFixPlugin extends JavaPlugin {
    private final ArrayList<String> forgeUsers = new ArrayList<>();

    /**
     * If the user is using forge
     * @param player The player to check
     * @return If the user is using forge
     */
    public boolean isForgeUser(Player player) {
        return forgeUsers.contains(player.getName());
    }

    /**
     * Add a user to the forge users list
     * @param player The player to add
     */
    public void addForgeUser(Player player) {
        if (!isForgeUser(player)) forgeUsers.add(player.getName());
    }

    /**
     * Remove a user from the forge users list
     * @param player The player to remove
     */
    public void removeForgeUser(Player player) {
        forgeUsers.remove(player.getName());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        ConfigLoader.loadConfig();

        Messenger messenger = getServer().getMessenger();
        PluginManager pluginManager = getServer().getPluginManager();
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        // Clear the forge users list every 5 minutes
        getServer().getScheduler().scheduleSyncRepeatingTask(this, forgeUsers::clear, 0L, 6000L);

        // Register the plugin message listener
//        messenger.registerIncomingPluginChannel(this, "MC|Brand", new BrandListener(this));
        messenger.registerIncomingPluginChannel(this, "minecraft:brand", new BrandListener(this));

        // Register player event listener
        pluginManager.registerEvents(new PlayerEventListener(this), this);

        // Fixes the recipe book packet being too large for the client to handle.
        manager.addPacketListener(new RECIPES_SPacketRecipeBook(this));

        // Test packet listener
//        manager.addPacketListener(new TestPacketListener(this, PacketType.Login.Client.CUSTOM_PAYLOAD, false));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        getLogger().info("Clearing forge users list");
        forgeUsers.clear();
    }
}
