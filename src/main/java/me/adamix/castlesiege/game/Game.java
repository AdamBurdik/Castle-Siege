package me.adamix.castlesiege.game;

import me.adamix.castlesiege.Main;
import me.adamix.castlesiege.PluginConfiguration;
import me.adamix.castlesiege.exceptions.FullTeamException;
import me.adamix.castlesiege.exceptions.GameIsActive;
import me.adamix.castlesiege.exceptions.NotEnoughPlayers;
import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.player.GamePlayer;
import me.adamix.castlesiege.team.Team;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

		taskId[0] = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
			if (countdown[0] < 1) {

				attackerPlayers.forEach(gamePlayer -> {
					Player player = gamePlayer.getPlayer();
					player.teleport(map.getAttackersSpawn());
					player.sendMessage(miniMessage.deserialize("<green>Game started! You are in <red>attackers"));
				});
				defendersPlayers.forEach(gamePlayer -> {
					Player player = gamePlayer.getPlayer();
					player.teleport(map.getDefendersSpawn());
					player.sendMessage(miniMessage.deserialize("<green>Game started! You are in <blue>defenders"));
				});
				Bukkit.getScheduler().cancelTask(taskId[0]);
				return;
			}

			attackerPlayers.forEach(gamePlayer -> {
				Player player = gamePlayer.getPlayer();
				player.showTitle(
						Title.title(
								miniMessage.deserialize("<green>Starting game in"),
								miniMessage.deserialize("<yellow>" + countdown[0]),
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
								miniMessage.deserialize("<green>Starting game in"),
								miniMessage.deserialize("<yellow>" + countdown[0]),
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
