package me.adamix.castlesiege;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class PluginConfiguration {
	private static YamlConfiguration config;
	private static File file;

	public static void init(Plugin plugin) {
		config = (YamlConfiguration) plugin.getConfig();
	}

	public static YamlConfiguration getConfig() {
		return config;
	}

}
