package me.adamix.castlesiege;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class MessageConfiguration {

	private static YamlConfiguration config;

	public static void init(Plugin plugin) {
		plugin.saveResource("messages.yml", false);
		File file = new File(plugin.getDataFolder(), "messages.yml");
		config = YamlConfiguration.loadConfiguration(file);
	}

	public static YamlConfiguration getConfig() {
		return config;
	}

}
