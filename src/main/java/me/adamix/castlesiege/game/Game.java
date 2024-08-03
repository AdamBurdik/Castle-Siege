package me.adamix.castlesiege.game;

import me.adamix.castlesiege.CastleSiege;
import me.adamix.castlesiege.MessageConfiguration;
import me.adamix.castlesiege.PluginConfiguration;
import me.adamix.castlesiege.exceptions.FullTeamException;
import me.adamix.castlesiege.exceptions.GameIsActive;
import me.adamix.castlesiege.exceptions.NotEnoughPlayers;
import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.player.GamePlayer;
import me.adamix.castlesiege.team.Team;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;


public class Game {

	private final GameMap map;
	private final Team attackers;
	private final Team defenders;
	private GameState state;

	public Game(GameMap map) {
		this.map = map;
		this.attackers = new Team("attackers");
		this.defenders = new Team("defenders");
		this.state = GameState.PENDING;
	}

	public void start() throws GameIsActive, NotEnoughPlayers {
		start(false);
	}

	public void start(boolean force) throws GameIsActive, NotEnoughPlayers {
		if (this.state == GameState.STARTING || this.state == GameState.PLAYING) {
			throw new GameIsActive();
		}

		if (!force) {
			int minPlayerPerTeam = PluginConfiguration.getConfig().getInt("AUTO_START.min_players_per_team");
			if (attackers.getPlayerCount() < minPlayerPerTeam || defenders.getPlayerCount() < minPlayerPerTeam) {
				throw new NotEnoughPlayers();
			}
		}

		List<GamePlayer> attackerPlayers = attackers.getPlayerList();
		List<GamePlayer> defendersPlayers = defenders.getPlayerList();

		MiniMessage miniMessage = MiniMessage.miniMessage();

		int[] countdown = {10};
		int[] taskId = new int[1];

		YamlConfiguration messageConfig = MessageConfiguration.getConfig();

		taskId[0] = Bukkit.getScheduler().runTaskTimer(CastleSiege.getInstance(), () -> {
			if (countdown[0] < 1) {


				attackerPlayers.forEach(gamePlayer -> {
					Player player = gamePlayer.getPlayer();
					String message = PlaceholderAPI.setPlaceholders(player, messageConfig.getString("GAME_STARTED"));

					player.teleport(map.getAttackersSpawn());
					player.sendMessage(miniMessage.deserialize(message));
				});
				defendersPlayers.forEach(gamePlayer -> {
					Player player = gamePlayer.getPlayer();
					String message = PlaceholderAPI.setPlaceholders(player, messageConfig.getString("GAME_STARTED"));


					player.teleport(map.getDefendersSpawn());
					player.sendMessage(miniMessage.deserialize(message));
				});
				Bukkit.getScheduler().cancelTask(taskId[0]);
				return;
			}

			attackerPlayers.forEach(gamePlayer -> {
				Player player = gamePlayer.getPlayer();
				player.showTitle(
						Title.title(
								miniMessage.deserialize(messageConfig.getString("GAME_STARTING_IN_TITLE")),
								miniMessage.deserialize(messageConfig.getString("GAME_STARTING_IN_SUBTITLE") + countdown[0]),
										Title.Times.times(
												Duration.ZERO, Duration.ofMillis(500L), Duration.ofMillis(250L)
										)
						)
				);
			});
			defendersPlayers.forEach(gamePlayer -> {
				Player player = gamePlayer.getPlayer();
				player.showTitle(
						Title.title(
								miniMessage.deserialize(messageConfig.getString("GAME_STARTING_IN_TITLE")),
								miniMessage.deserialize(messageConfig.getString("GAME_STARTING_IN_VALUE") + countdown[0]),
								Title.Times.times(
										Duration.ZERO, Duration.ofMillis(500L), Duration.ofMillis(250L)
								)
						)
				);
			});

			countdown[0]--;

		}, 0L, 20L).getTaskId();
	}

	public void addPlayerToAttackers(Player player) throws FullTeamException {
		attackers.addPlayer(player);
	}

	public void addPlayerToDefenders(Player player) throws FullTeamException {
		defenders.addPlayer(player);
	}


}
