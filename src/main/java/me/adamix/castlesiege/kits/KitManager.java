package me.adamix.castlesiege.kits;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitManager {

	public static void setKit(Player player, String kitName) {
		YamlConfiguration kitConfig = KitConfiguration.getConfig();

		if (!kitConfig.contains(kitName)) {
			throw new RuntimeException("Unknown kit with name " + kitName + "!");
		}

		ItemStack[] kitContent = ((List<ItemStack>) kitConfig.getList(kitName + ".content")).toArray(new ItemStack[0]);
		player.getInventory().setContents(kitContent);
	}

}
