package me.adamix.castlesiege;

import me.adamix.castlesiege.commands.CastleSiegeCommand;
import me.adamix.castlesiege.game.Game;
import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.map.MapConfiguration;
import me.adamix.castlesiege.map.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getDataFolder().mkdirs();

        PluginConfiguration.init(this);
        MapConfiguration.init(this);
        MessageConfiguration.init(this);


        Objects.requireNonNull(getCommand("cs")).setExecutor(new CastleSiegeCommand());
        Objects.requireNonNull(getCommand("cs")).setTabCompleter(new CastleSiegeCommand());

    }

    @Override
    public void onDisable() {

    }

    public static Main getInstance() {
        return instance;
    }

}
