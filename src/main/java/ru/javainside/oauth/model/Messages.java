package ru.javainside.oauth.model;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Data
public class Messages {
    private static FileConfiguration config;

    public Messages(Plugin plugin) {
        File languageFile = new File(plugin.getDataFolder(), "language.yml");
        if (languageFile.exists()) {
            config = YamlConfiguration.loadConfiguration(
                    languageFile);
        } else {
            config = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(plugin.getResource("language.yml")));
            try {
                config.save(languageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMessage(String key) {
        return config.getString(key);
    }
}
