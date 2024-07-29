package me.adamix.castlesiege.map;


import org.bukkit.Location;

public class GameMap {
	private Location attackersSpawnLocation;
	private Location defendersSpawnLocation;
	private float spawnRadius = 0;


	public GameMap(Location attackersSpawnLocation, Location defendersSpawnLocation) {
		this.attackersSpawnLocation = attackersSpawnLocation;
		this.defendersSpawnLocation = defendersSpawnLocation;
	}

	public Location getAttackersSpawn() {
		return this.attackersSpawnLocation;
	}

	public Location getDefendersSpawn() {
		return this.defendersSpawnLocation;
	}

}
