package me.adamix.castlesiege.player;

import me.adamix.castlesiege.team.Team;
import org.bukkit.entity.Player;

public class GamePlayer {
	private final Player player;
	private Team team;

	public GamePlayer(Player player) {
		this.player = player;
		GamePlayerManager.addPlayer(this);
	}

	public Player getPlayer() {
		return player;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}
}
