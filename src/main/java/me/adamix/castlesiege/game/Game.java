package me.adamix.castlesiege.game;

import me.adamix.castlesiege.exceptions.FullTeamException;
import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;


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

	public void start() {
		start(false);
	}

	public void start(boolean force) {

		Location attackersLocation = map.getAttackersSpawn();
		Bukkit.getLogger().info(attackersLocation.toString());
		for (Player player : attackers.getPlayerList()) {
			player.teleport(attackersLocation);
		}

		Location defendersLocation = map.getDefendersSpawn();
		Bukkit.getLogger().info(defendersLocation.toString());
		for (Player player : defenders.getPlayerList()) {
			player.teleport(defendersLocation);
		}

	}

	public void addPlayerToAttackers(Player player) throws FullTeamException {
		attackers.addPlayer(player);
	}

	public void addPlayerToDefenders(Player player) throws FullTeamException {
		defenders.addPlayer(player);
	}


}
