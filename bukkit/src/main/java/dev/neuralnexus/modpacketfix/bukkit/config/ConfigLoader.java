package dev.neuralnexus.modpacketfix.bukkit.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

// Load the config from plugins/ModPacketFix/config.json
public class ConfigLoader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Config config;

    public static Config getConfig() {
        return config;
    }

    public static void loadConfig() {
        File configFile = new File("plugins/ModPacketFix/config.json");
        if (!configFile.exists()) {
            config = new Config();
            saveConfig();
        } else {
            try {
                Reader reader = Files.newBufferedReader(configFile.toPath());
                config = gson.fromJson(reader, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveConfig() {
        File configFile = new File("plugins/ModPacketFix/config.json");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
        }
        try {
            configFile.createNewFile();
            String json = gson.toJson(config);
            Files.write(configFile.toPath(), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
