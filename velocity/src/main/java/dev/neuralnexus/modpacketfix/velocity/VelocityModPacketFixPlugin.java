package dev.neuralnexus.modpacketfix.velocity;

import com.velocitypowered.api.plugin.Dependency;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
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
        url = "https://github.com/p0t4t0sandwich/ModPacketFix",
        dependencies = {
                @Dependency(id = "ambassador")
        }
)
public class VelocityModPacketFixPlugin {
    @Inject private ProxyServer server;
    @Inject private Logger logger;

    private static ProxyServer proxyServer;
    /**
     * Get the proxy server.
     * @return The proxy server.
     */
    public static ProxyServer getProxyServer() {
        return proxyServer;
    }

    /**
     * Use whatever logger is being used.
     * @param message The message to log
     */
    public void useLogger(String message) {
        logger.info(message);
    }

    /**
     * Called when the proxy is initialized.
     * @param event The event.
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
//        proxyServer = server;

        // Not Yet Implemented
        useLogger("ModPacketFix has not yet been implemented for Velocity!");
    }

    /**
     * Called when the proxy is shutting down.
     * @param event The event.
     */
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {}
}
