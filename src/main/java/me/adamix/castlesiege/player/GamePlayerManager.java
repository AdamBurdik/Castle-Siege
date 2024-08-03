package me.adamix.castlesiege.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GamePlayerManager {
	private static Map<Player, GamePlayer> gamePlayers = new HashMap<>();

	public static GamePlayer getGamePlayer(Player player) {
		return gamePlayers.get(player);
	}

	public static void addPlayer(GamePlayer gamePlayer) {
		gamePlayers.put(gamePlayer.getPlayer(), gamePlayer);
	}
}
