package dev.neuralnexus.modpacketfix.bukkit.packetlisteners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;

public class TestPacketListener extends PacketAdapter {
    private final BukkitModPacketFixPlugin plugin;
    private final boolean cancelled;

    public TestPacketListener(BukkitModPacketFixPlugin plugin, PacketType packetType, boolean cancelled) {
        super(plugin, ListenerPriority.HIGHEST, packetType);
        this.plugin = plugin;
        this.cancelled = cancelled;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        plugin.getLogger().info("Packet: Server" + event.getPacketType().name());
        event.setCancelled(cancelled);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        plugin.getLogger().info("Packet: Client" + event.getPacketType().name());
        event.setCancelled(cancelled);
    }
}
