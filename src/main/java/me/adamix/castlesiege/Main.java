package me.adamix.castlesiege;

import me.adamix.castlesiege.commands.CastleSiegeCommand;
import me.adamix.castlesiege.game.Game;
import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.map.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("cs")).setExecutor(new CastleSiegeCommand());

    }

    @Override
    public void onDisable() {

    }

}
