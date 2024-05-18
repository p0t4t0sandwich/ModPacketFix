package dev.neuralnexus.modpacketfix.bukkit.packetlisteners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;

import java.lang.reflect.Field;

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

//        PacketContainer packet = event.getPacket();
//        System.out.println("Modifiers: " + packet.getModifier().size());
//        System.out.println("Modifier: " + packet.getModifier().read(0));
//        packet.getModifier().modify(0, select -> {
//            System.out.println("Class: " + select.getClass());
//            return select;
//        });
//
//        System.out.println("Modifier: " + packet.getModifier().read(1));
//        packet.getModifier().modify(1, select -> {
//            System.out.println("Class: " + select);
//            try {
//                Field[] fields = select.getClass().getDeclaredFields();
//                for (Field field : fields) {
//                    field.setAccessible(true);
//                    System.out.println(field.getName() + ": " + field.get(select));
//                }
//
//                Field field = select.getClass().getDeclaredField("buffer");
//                field.setAccessible(true);
//                Field[] bufferFields = field.get(select).getClass().getDeclaredFields();
//                for (Field bufferField : bufferFields) {
//                    bufferField.setAccessible(true);
//                    System.out.println(bufferField.getName() + ": " + bufferField.get(field.get(select)));
//                }
//
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                System.out.println(e);
//            }
//            return select;
//        });
    }
}
