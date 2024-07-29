package me.adamix.castlesiege.game;

import me.adamix.castlesiege.map.GameMap;
import me.adamix.castlesiege.map.MapManager;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

	private static Map<String, Game> games = new HashMap<>();

	public static Game createGame(String gameId, String gameMapName) {
		GameMap map = MapManager.getGameMap(gameMapName);
		Bukkit.getLogger().info(map.toString());
		Game game = new Game(map);
		games.put(gameId, game);
		return game;
	}

	public static Game getGame(String gameId) {
		return games.get(gameId);
	}

}
