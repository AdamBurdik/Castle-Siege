package me.adamix.castlesiege.map;

import java.util.HashMap;
import java.util.Map;

public class MapManager {

	private static Map<String, GameMap> gameMaps = new HashMap<>();

	public static GameMap getGameMap(String name) {
		return gameMaps.get(name);
	}

	public static void addGameMap(String gameId, GameMap gameMap) {
		gameMaps.put(gameId, gameMap);
	}

}
