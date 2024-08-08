package me.adamix.castlesiege.game;

import me.adamix.castlesiege.CastleSiege;
import me.adamix.castlesiege.MessageConfiguration;
import me.adamix.castlesiege.PluginConfiguration;
import me.adamix.castlesiege.exceptions.FullTeamException;
import me.adamix.castlesiege.exceptions.GameIsActive;
import me.adamix.castlesiege.exceptions.NotEnoughPlayers;
import me.adamix.castlesiege.kits.KitManager;
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
	private int[] countdownTaskId;

	public Game(GameMap map) {
		this.map = map;

		YamlConfiguration pluginConfig = PluginConfiguration.getConfig();

		this.attackers = new Team(pluginConfig.getString("ATTACKERS.name"));
		this.defenders = new Team(pluginConfig.getString("DEFENDERS.name"));
		this.state = GameState.PENDING;
	}

	public void start() throws GameIsActive, NotEnoughPlayers {
		start(false);
	}

	public void start(boolean force) throws GameIsActive, NotEnoughPlayers {
		if (this.state == GameState.STARTING || this.state == GameState.PLAYING) {
			throw new GameIsActive();
		}

		YamlConfiguration pluginConfig = PluginConfiguration.getConfig();

		if (!force) {
			int minPlayerPerTeam = pluginConfig.getInt("START.min_players_per_team");
			if (attackers.getPlayerCount() < minPlayerPerTeam || defenders.getPlayerCount() < minPlayerPerTeam) {
				throw new NotEnoughPlayers();
			}
		}

		List<GamePlayer> attackerPlayers = attackers.getPlayerList();
		List<GamePlayer> defendersPlayers = defenders.getPlayerList();

		MiniMessage miniMessage = MiniMessage.miniMessage();

		YamlConfiguration messageConfig = MessageConfiguration.getConfig();

		int[] countdown = {pluginConfig.getInt("START.cooldown")};
		countdownTaskId = new int[1];

		countdownTaskId[0] = Bukkit.getScheduler().runTaskTimer(CastleSiege.getInstance(), () -> {
			if (countdown[0] < 1) {
				Bukkit.getScheduler().cancelTask(countdownTaskId[0]);

				attackerPlayers.forEach(gamePlayer -> {
					Player player = gamePlayer.getPlayer();
					String gameStartMessage = messageConfig.getString("GAME_STARTED");
					if (gameStartMessage == null) {
						throw new RuntimeException("Please specify valid GAME_START messages.yml!");
					}

					String message = PlaceholderAPI.setPlaceholders(player, gameStartMessage);

					player.teleport(map.getAttackersSpawn());
					player.sendMessage(miniMessage.deserialize(message));
					String kitName = pluginConfig.getString("DEFENDERS.default_kit");
					if (kitName != null) {
						KitManager.setKit(player, kitName);
					}

					gamePlayer.showScoreboard();
				});
				defendersPlayers.forEach(gamePlayer -> {
					Player player = gamePlayer.getPlayer();
					String gameStartMessage = messageConfig.getString("GAME_STARTED");
					if (gameStartMessage == null) {
						throw new RuntimeException("Please specify valid GAME_START messages.yml!");
					}

					String message = PlaceholderAPI.setPlaceholders(player, gameStartMessage);

					player.teleport(map.getDefendersSpawn());
					player.sendMessage(miniMessage.deserialize(message));

					String kitName = pluginConfig.getString("DEFENDERS.default_kit");
					if (kitName != null) {
						KitManager.setKit(player, kitName);
					}

					gamePlayer.showScoreboard();
				});
				return;
			}

			attackerPlayers.forEach(gamePlayer -> {
				Player player = gamePlayer.getPlayer();
				player.showTitle(
						Title.title(
								miniMessage.deserialize(messageConfig.getString("GAME_STARTING_IN_TITLE")),
								miniMessage.deserialize(messageConfig.getString("GAME_STARTING_IN_SUBTITLE") + countdown[0]),
										Title.Times.times(
												Duration.ofMillis(pluginConfig.getLong("START.title_fade_in")),
												Duration.ofMillis(pluginConfig.getLong("START.title_stay")),
												Duration.ofMillis(pluginConfig.getLong("START.title_fade_out"))
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
										Duration.ofMillis(pluginConfig.getLong("START.title_fade_in")),
										Duration.ofMillis(pluginConfig.getLong("START.title_stay")),
										Duration.ofMillis(pluginConfig.getLong("START.title_fade_out"))
								)
						)
				);
			});

			countdown[0]--;

		}, 0L, 20L).getTaskId();
	}

	public void stop() {
		Bukkit.getScheduler().cancelTask(countdownTaskId[0]);
	}

	public void addPlayerToAttackers(Player player) throws FullTeamException {
		attackers.addPlayer(player);
	}

	public void addPlayerToDefenders(Player player) throws FullTeamException {
		defenders.addPlayer(player);
	}


}
