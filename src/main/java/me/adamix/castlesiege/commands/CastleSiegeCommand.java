package me.adamix.castlesiege.commands;

import me.adamix.castlesiege.exceptions.FullTeamException;
import me.adamix.castlesiege.exceptions.GameIsActive;
import me.adamix.castlesiege.exceptions.NotEnoughPlayers;
import me.adamix.castlesiege.game.Game;
import me.adamix.castlesiege.game.GameManager;
import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.map.MapConfiguration;
import me.adamix.castlesiege.map.MapManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CastleSiegeCommand implements CommandExecutor, TabExecutor {

	private static final MiniMessage minimessage = MiniMessage.miniMessage();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String aliases, @NotNull String[] args) {
		if (args.length < 1) {
			sender.sendMessage(minimessage.deserialize("<red>Invalid usage! Please specify argument /cs <start>"));
			return true;
		}

		String subCommand = args[0].toLowerCase();
		switch (subCommand) {
			case "creategame":
				return createGameSubcommand(sender, args);
			case "startgame":
				return startSubcommand(sender, args);
			case "setteamlocation":
				return setLocationSubCommand(sender, args);
			case "debug_loadmap":
				if (args.length < 2) {
					sender.sendMessage(minimessage.deserialize("<red>Invalid usage! Please specify argument /cs debug_loadmap <map>"));
					return true;
				}

				String mapName = args[1];

				YamlConfiguration mapConfig = MapConfiguration.getConfig();
				Location attackersSpawnLocation = mapConfig.getLocation(mapName + ".attackers.location");
				Location defendersSpawnLocation = mapConfig.getLocation(mapName + ".defenders.location");


				GameMap gameMap = new GameMap(attackersSpawnLocation, defendersSpawnLocation);
				MapManager.addGameMap(mapName, gameMap);
				sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>" + mapName + " was loaded!"));
				return false;
			default:
				sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Unknown subcommand!"));
		}

		return false;
	}

	private boolean createGameSubcommand(CommandSender sender, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(minimessage.deserialize("<red>Invalid usage! Please specify game id /cs createGame <gameId> <map>"));
			return true;
		}

		String gameId = args[1];
		String mapName = args[2];

		Game game = GameManager.createGame(gameId, mapName);
		if (sender instanceof Player player) {
			try {
				game.addPlayerToAttackers(player);
			} catch (FullTeamException e) {
				sender.sendMessage(minimessage.deserialize("<red>This team is full!"));
			}
		}

		sender.sendMessage(minimessage.deserialize("<green>Game created!"));

		return false;
	}

	private boolean startSubcommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(minimessage.deserialize("<red>Invalid usage! Please specify game id /cs start <gameId> [force]"));
			return true;
		}

		boolean force = false;
		if (args.length >= 3) {
			force = Boolean.parseBoolean(args[2]);
		}

		String gameId = args[1];

		Game game = GameManager.getGame(gameId);
		try {
			game.start(force);
		} catch (Exception e) {
			sender.sendMessage(minimessage.deserialize("<red>" + e.getMessage()));
		}

		return false;
	}

	private boolean setLocationSubCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("Only players can use this subcommand!");
			return true;
		}

		MiniMessage miniMessage = MiniMessage.miniMessage();

		if (args.length < 3) {
			sender.sendMessage(miniMessage.deserialize("<red>Invalid usage! Please specify map and team /cs setTeamLocation <map> <attackers|defenders>"));
			return true;
		}

		String mapName = args[1];

		String team = args[2];
		if (!team.equalsIgnoreCase("attackers") && !team.equalsIgnoreCase("defenders")) {
			sender.sendMessage(miniMessage.deserialize("<red>Please input valid team name! /cs setTeamLocation <map> <attackers|defenders>"));
			return true;
		}

		YamlConfiguration mapConfig = MapConfiguration.getConfig();

		if (!mapConfig.contains(mapName)) {
			sender.sendMessage(miniMessage.deserialize("<red>Maps.yml does not contain " + team + " map!"));
			return true;
		}

		String path = mapName + "." + team + ".location";

		Bukkit.getLogger().info(String.valueOf(mapConfig.get(path)));

		mapConfig.set(path, player.getLocation());
		mapConfig.setInlineComments(path, List.of("PLEASE DO NOT EDIT THIS MANUALLY! USE /cs setTeamLocation COMMAND!"));
		MapConfiguration.save();

		Bukkit.getLogger().info(String.valueOf(mapConfig.get(path)));

		sender.sendMessage(miniMessage.deserialize("<green>Location of " + team + " has been set to your location!"));

		return false;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		return List.of("createGame", "startGame", "setTeamLocation", "openKitEditor", "debug_loadMap");
	}
}
