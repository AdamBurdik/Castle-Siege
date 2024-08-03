package me.adamix.castlesiege.team;

import me.adamix.castlesiege.exceptions.FullTeamException;
import me.adamix.castlesiege.player.GamePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

	private String name;
	private List<GamePlayer> playerList = new ArrayList<>();
	private int maxPlayerCount = 50;

	public Team(String name) {
		this.name = name;
	}

	public void addPlayer(Player player) throws FullTeamException {
		if (playerList.size() + 1 >= maxPlayerCount) {
			throw new FullTeamException();
		}
		GamePlayer gamePlayer = new GamePlayer(player);
		gamePlayer.setTeam(this);

		playerList.add(gamePlayer);
	}

	public void removePlayer(Player player) {
		playerList.remove(player);
	}

	public List<GamePlayer> getPlayerList() {
		return this.playerList;
	}

	public int getPlayerCount() {
		return this.playerList.size();
	}

	public String getName() {
		return name;
	}

	public int getMaxPlayerCount() {
		return maxPlayerCount;
	}

}
