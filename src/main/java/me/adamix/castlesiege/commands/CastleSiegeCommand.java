package me.adamix.castlesiege.commands;

import me.adamix.castlesiege.exceptions.FullTeamException;
import me.adamix.castlesiege.game.Game;
import me.adamix.castlesiege.game.GameManager;
import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.map.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CastleSiegeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String aliases, @NotNull String[] args) {
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Invalid usage! Please specify argument /cs <start>");
			return true;
		}

		String subCommand = args[0].toLowerCase();
		switch (subCommand) {
			case "creategame":
				return createGameSubcommand(sender, args);
			case "start":
				return startSubcommand(sender, args);
			case "debug":
				World world = ((Player) sender).getWorld();

				Bukkit.getLogger().info(world.toString());

				Location attackersSpawnLocation = new Location(world, -340.5f, 70, 432.5f, -90, 0);
				Location defendersSpawnLocation = new Location(world, -220.5f, 70, 435.5, 90, 0);

				GameMap gameMap = new GameMap(attackersSpawnLocation, defendersSpawnLocation);
				MapManager.addGameMap("exampleMap", gameMap);
		}

		return false;
	}

	private boolean createGameSubcommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Invalid usage! Please specify game id /cs createGame <gameId>");
			return true;
		}

		String gameId = args[1];

		Game game = GameManager.createGame(gameId, "exampleMap");
		if (sender instanceof Player player) {
			try {
				game.addPlayerToAttackers(player);
			} catch (FullTeamException e) {
				sender.sendMessage(ChatColor.RED + "This team is full!");
			}
		}

		sender.sendMessage(ChatColor.GREEN + "Game created!");

		return false;
	}

	private boolean startSubcommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Invalid usage! Please specify game id /cs start <gameId>");
			return true;
		}

		boolean force = false;
		if (args.length >= 3) {
			force = Boolean.parseBoolean(args[2]);
		}

		String gameId = args[1];

		Game game = GameManager.getGame(gameId);
		game.start(force);

		return false;
	}
}
