package me.adamix.castlesiege.kits;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class KitConfiguration {
	private static YamlConfiguration config;
	private static File file;

	public static void init(Plugin plugin) {
		plugin.saveResource("kits.yml", false);
		file = new File(plugin.getDataFolder(), "kits.yml");
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
