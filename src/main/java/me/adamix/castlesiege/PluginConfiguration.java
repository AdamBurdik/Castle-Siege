package me.adamix.castlesiege;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class PluginConfiguration {
	private static YamlConfiguration config;

	public static void init(Plugin plugin) {
		plugin.saveDefaultConfig();
		config = (YamlConfiguration) plugin.getConfig();
	}

	public static YamlConfiguration getConfig() {
		return config;
	}

}
