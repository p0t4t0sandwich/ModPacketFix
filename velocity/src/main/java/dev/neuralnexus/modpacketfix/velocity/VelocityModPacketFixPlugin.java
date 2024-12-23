package dev.neuralnexus.modpacketfix.velocity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.ConnectionHandshakeEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.InboundConnection;
import com.velocitypowered.api.proxy.LoginPhaseConnection;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

/**
 * The ModPacketFix Velocity plugin.
 */
@Plugin(
        id = "modpacketfix",
        name = "ModPacketFix",
        version = "1.0.0",
        authors = "p0t4t0sandwich",
        description = "A plugin that fixes some of the too big/too small packet errors that occur when connecting to a Vanilla server with a Forge client",
        url = "https://github.com/p0t4t0sandwich/ModPacketFix"
//        dependencies = {
//                @Dependency(id = "ambassador")
//        }
)
public class VelocityModPacketFixPlugin {
    private Logger logger;
    private ProxyServer server;
    private PluginContainer plugin;

    @Inject
    public VelocityModPacketFixPlugin(Logger logger, ProxyServer server, PluginContainer plugin) {
        this.logger = logger;
        this.server = server;
        this.plugin = plugin;
    }

    public static final MinecraftChannelIdentifier NEO_REGISTER = MinecraftChannelIdentifier.create("neoforge", "register");
    public static final MinecraftChannelIdentifier NEO_NETWORK = MinecraftChannelIdentifier.create("neoforge", "network");

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.server.getChannelRegistrar().register(NEO_REGISTER);
        this.server.getChannelRegistrar().register(NEO_NETWORK);
    }

    @Subscribe
    public void onPluginMessageFromPlayer(PluginMessageEvent event) {
        if (!NEO_REGISTER.equals(event.getIdentifier()) && !NEO_NETWORK.equals(event.getIdentifier())) {
            return;
        }
        event.setResult(PluginMessageEvent.ForwardResult.handled());

        if (!(event.getSource() instanceof Player player)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        // handle packet data
    }

    public void sendPluginMessageToPlayer(InboundConnection conn, ChannelIdentifier identifier, byte[] data) {
        ((LoginPhaseConnection) conn).sendLoginPluginMessage(identifier, data, res -> {});
    }

    @Subscribe
    public void onPreLogin(ConnectionHandshakeEvent event) {
        sendPluginMessageToPlayer(event.getConnection(), NEO_REGISTER, new byte[0]);
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {}
}
