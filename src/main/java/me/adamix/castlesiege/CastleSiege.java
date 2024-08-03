package me.adamix.castlesiege;

import me.adamix.castlesiege.commands.CastleSiegeCommand;
import me.adamix.castlesiege.expansions.PlayerTeamExpansion;
import me.adamix.castlesiege.map.MapConfiguration;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CastleSiege extends JavaPlugin {

    private static CastleSiege instance;

    @Override
    public void onEnable() {
        instance = this;

        getDataFolder().mkdirs();

        PluginConfiguration.init(this);
        MapConfiguration.init(this);
        MessageConfiguration.init(this);


        Objects.requireNonNull(getCommand("cs")).setExecutor(new CastleSiegeCommand());
        Objects.requireNonNull(getCommand("cs")).setTabCompleter(new CastleSiegeCommand());

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlayerTeamExpansion().register();
        }

    }

    @Override
    public void onDisable() {

    }

    public static CastleSiege getInstance() {
        return instance;
    }

}
