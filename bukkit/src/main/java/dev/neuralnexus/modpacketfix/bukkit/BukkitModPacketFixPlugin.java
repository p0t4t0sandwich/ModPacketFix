package dev.neuralnexus.modpacketfix.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The ModPacketFix Bukkit plugin.
 */
public class BukkitModPacketFixPlugin extends JavaPlugin {
    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    public void useLogger(String message) {
        getLogger().info(message);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        useLogger("ModPacketFix has been enabled!");

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        // Server.AUTO_RECIPE
        // Server.RECIPE_UPDATE
        // Client.AUTO_RECIPE
        // Client.RECIPE_SETTINGS
        // Client.RECIPE_DISPLAYED
        // PacketType.Login.Client.START -> onPacketReceiving -> packet.getStrings().read(0) -> username
//        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Login.Server.CUSTOM_PAYLOAD) {
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                Player player = event.getPlayer();
//                PacketContainer packet = event.getPacket();
//                useLogger(packet.getStrings().toString());
//            }
//        });
        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.RECIPES) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                useLogger("Skipping Recipe Book packet [SPacketRecipeBook] being sent to " + player.getName());
                event.setCancelled(true);
            }
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        useLogger("ModPacketFix has been disabled!");
    }
}
