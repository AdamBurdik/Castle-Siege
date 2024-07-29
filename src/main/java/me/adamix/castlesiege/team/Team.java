package me.adamix.castlesiege.team;

import me.adamix.castlesiege.exceptions.FullTeamException;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

	private String name;
	private List<Player> playerList = new ArrayList<>();
	private int maxPlayerCount = 50;

	public Team(String name) {
		this.name = name;
	}

	public void addPlayer(Player player) throws FullTeamException {
		if (playerList.size() + 1 >= maxPlayerCount) {
			throw new FullTeamException();
		}
		playerList.add(player);
	}

	public void removePlayer(Player player) {
		playerList.remove(player);
	}

	public List<Player> getPlayerList() {
		return this.playerList;
	}

}
