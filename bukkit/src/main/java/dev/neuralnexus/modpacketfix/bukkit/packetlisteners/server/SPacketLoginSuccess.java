package dev.neuralnexus.modpacketfix.bukkit.packetlisteners.server;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Listens for hello packets from the client.
 */
public class SPacketLoginSuccess extends PacketAdapter {
    private final BukkitModPacketFixPlugin plugin;
    public SPacketLoginSuccess(BukkitModPacketFixPlugin plugin) {
        super(plugin, ListenerPriority.HIGHEST,
                PacketType.Login.Server.SUCCESS
//                PacketType.Login.Server.ENCRYPTION_BEGIN,
//                PacketType.Login.Server.SET_COMPRESSION,
//                PacketType.Login.Client.ENCRYPTION_BEGIN,
//                PacketType.Play.Client.CUSTOM_PAYLOAD,
//                PacketType.Play.Server.CUSTOM_PAYLOAD
        );
        this.plugin = plugin;
    }

    private void handlePacket(PacketEvent event) {
        try {
            // null, otherwise I'd send the actual packet
            System.out.println(((CraftPlayer) event.getPlayer()).getHandle().connection);
//                .connection.send(new ClientboundCustomPayloadPacket(new ModdedNetworkQueryPayload(Map.of())));

            ClientboundCustomPayloadPacket payload =
                    new ClientboundCustomPayloadPacket(new ModdedNetworkQueryPayload(Map.of()));
            PacketContainer container = PacketContainer.fromPacket(payload);

            ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), container);
        } catch (Exception e) {
            System.out.println("Failed to read packet");
            e.printStackTrace();
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        this.handlePacket(event);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        this.handlePacket(event);
    }

    public record ModdedNetworkQueryPayload(
            Map<ConnectionProtocol, Set<ModdedNetworkQueryComponent>> queries) implements CustomPacketPayload {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("neoforge", "register");
        public static final Type<ModdedNetworkQueryPayload> TYPE = new Type<>(ID);
        public static StreamCodec<FriendlyByteBuf, ModdedNetworkQueryPayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.map(IdentityHashMap::new,
                        ByteBufCodecs.idMapper(b -> ConnectionProtocol.values()[b], ConnectionProtocol::ordinal),
                        ByteBufCodecs.collection(HashSet::new, ModdedNetworkQueryComponent.STREAM_CODEC)),
                ModdedNetworkQueryPayload::queries, ModdedNetworkQueryPayload::new);

        @Override
        public @NotNull Type<ModdedNetworkQueryPayload> type() {
            return TYPE;
        }
    }

    public static <B extends FriendlyByteBuf, V extends Enum<V>> StreamCodec<B, V> enumCodec(Class<V> enumClass) {
        return new StreamCodec<>() {
            @Override
            public @NotNull V decode(@NotNull B buf) {
                return buf.readEnum(enumClass);
            }

            @Override
            public void encode(@NotNull B buf, @NotNull V value) {
                buf.writeEnum(value);
            }
        };
    }

    public record ModdedNetworkQueryComponent(ResourceLocation id, String version, Optional<PacketFlow> flow, boolean optional) {
        public static final StreamCodec<FriendlyByteBuf, ModdedNetworkQueryComponent> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, ModdedNetworkQueryComponent::id,
                ByteBufCodecs.STRING_UTF8, ModdedNetworkQueryComponent::version,
                ByteBufCodecs.optional(enumCodec(PacketFlow.class)), ModdedNetworkQueryComponent::flow,
                ByteBufCodecs.BOOL, ModdedNetworkQueryComponent::optional,
                ModdedNetworkQueryComponent::new);
    }
}
