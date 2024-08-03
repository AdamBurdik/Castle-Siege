package me.adamix.castlesiege.map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class MapConfiguration {

	private static YamlConfiguration config;
	private static File file;

	public static void init(Plugin plugin) {
		plugin.saveResource("maps.yml", false);
		file = new File(plugin.getDataFolder(), "maps.yml");
		config = YamlConfiguration.loadConfiguration(file);
	}

	public static YamlConfiguration getConfig() {
		return config;
	}

	public static void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
