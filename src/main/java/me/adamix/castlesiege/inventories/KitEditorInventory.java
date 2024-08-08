package me.adamix.castlesiege.inventories;

import me.adamix.castlesiege.kits.KitConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;


// TODO Rewrite this shit
public class KitEditorInventory implements Listener {

	private static final Map<Player, KitEditorRecord> inventoryMap = new HashMap<>();

	public static void open(Player player, String kitName) {
		MiniMessage miniMessage = MiniMessage.miniMessage();

		Inventory inventory = Bukkit.createInventory(null, 9 * 5, miniMessage.deserialize("Kit Editor - Kit: " + kitName));

		ItemStack hotBarItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		hotBarItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<red>Hotbar slot"));
			meta.setCustomModelData(69420);
		});

		ItemStack inventoryItem = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
		inventoryItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<yellow>Inventory slot"));
			meta.setCustomModelData(69420);
		});

		ItemStack bootsItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
		bootsItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<dark_purple>Boots slot"));
			meta.setCustomModelData(69420);
		});

		ItemStack leggingsItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
		leggingsItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<dark_purple>Leggings slot"));
			meta.setCustomModelData(69420);
		});

		ItemStack chestplateItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
		chestplateItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<dark_purple>Chestplate slot"));
			meta.setCustomModelData(69420);
		});

		ItemStack helmetItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
		helmetItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<dark_purple>Helmet slot"));
			meta.setCustomModelData(69420);
		});

		ItemStack offHandItem = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
		offHandItem.editMeta(meta -> {
			meta.displayName(miniMessage.deserialize("<light_purple>Offhand slot"));
			meta.setCustomModelData(69420);
		});


		YamlConfiguration kitConfig = KitConfiguration.getConfig();
		List<?> genericKitContent = kitConfig.getList(kitName + ".content");

		List<ItemStack> kitContent;
		if (genericKitContent == null) {
			kitContent = new ArrayList<>(Collections.nCopies(41, null));
		} else {
			kitContent = (List<ItemStack>) kitConfig.getList(kitName + ".content");
		}
		int i = 0;
		assert kitContent != null;
		for (ItemStack itemStack : kitContent) {
			if (itemStack == null) {
				if (i < 9) {
					inventory.setItem(i, hotBarItem);
				}
				else if (i < 36) {
					inventory.setItem(i, inventoryItem);
				}
				else if (i == 36) {
					inventory.setItem(i, bootsItem);
				}
				else if (i == 37) {
					inventory.setItem(i, leggingsItem);
				}
				else if (i == 38) {
					inventory.setItem(i, chestplateItem);
				}
				else if (i == 39) {
					inventory.setItem(i, helmetItem);
				}
				else if (i == 40) {
					inventory.setItem(i, offHandItem);
				}

			} else {
				inventory.setItem(i, itemStack);
			}

			i++;
		}

		player.openInventory(inventory);
		inventoryMap.put(player, new KitEditorRecord(inventory, kitName));

	}
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		if (!event.getInventory().equals(inventoryMap.get((Player) event.getWhoClicked()).inventory())) {
			return;
		}

		final int slot = event.getRawSlot();

		if (slot > 53) {
			return;
		}

		MiniMessage miniMessage = MiniMessage.miniMessage();
		Player player = (Player) event.getWhoClicked();

		event.setCancelled(true);

		final ItemStack clickedItem = event.getCurrentItem();
		final ItemStack cursorItem = player.getItemOnCursor();
		final Inventory inventory = event.getInventory();

		if (clickedItem == null) {
			return;
		}

		if (clickedItem.getItemMeta().hasCustomModelData() && clickedItem.getItemMeta().getCustomModelData() == 69420) {
			if (cursorItem.getType() == Material.AIR) {
				return;
			}

			inventory.setItem(slot, cursorItem);
			player.setItemOnCursor(ItemStack.empty());
			return;
		}

		if (cursorItem.getType() == Material.AIR) {
			player.setItemOnCursor(clickedItem);

			ItemStack placeholderItem;
			if (slot < 9) {
				placeholderItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
				placeholderItem.editMeta(meta -> {
					meta.displayName(miniMessage.deserialize("<red>Hotbar slot"));
					meta.setCustomModelData(69420);
				});
			}
			else if (slot < 36) {
				placeholderItem = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
				placeholderItem.editMeta(meta -> {
					meta.displayName(miniMessage.deserialize("<yellow>Inventory slot"));
					meta.setCustomModelData(69420);
				});
			}
			else if (slot == 37) {
				placeholderItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
				placeholderItem.editMeta(meta -> {
					meta.displayName(miniMessage.deserialize("<dark_purple>Leggings slot"));
					meta.setCustomModelData(69420);
				});
			}
			else if (slot == 38) {
				placeholderItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
				placeholderItem.editMeta(meta -> {
					meta.displayName(miniMessage.deserialize("<dark_purple>Chestplate slot"));
					meta.setCustomModelData(69420);
				});
			}
			else if (slot == 39) {
				placeholderItem = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
				placeholderItem.editMeta(meta -> {
					meta.displayName(miniMessage.deserialize("<dark_purple>Helmet slot"));
					meta.setCustomModelData(69420);
				});
			}
			else if (slot == 40) {
				placeholderItem = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
				placeholderItem.editMeta(meta -> {
					meta.displayName(miniMessage.deserialize("<light_purple>OffHand slot"));
					meta.setCustomModelData(69420);
				});
			}
			else {
				placeholderItem = ItemStack.empty();
			}
			inventory.setItem(slot, placeholderItem);
		} else {
			player.setItemOnCursor(clickedItem);
			inventory.setItem(slot, cursorItem);
		}
	}

	@EventHandler
	public void onInventoryClose(final InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if (!inventoryMap.containsKey(player)) {
			return;
		}

		MiniMessage miniMessage = MiniMessage.miniMessage();

		String kitName = inventoryMap.get(player).kitName();
		inventoryMap.remove(player);

		ItemStack[] inventoryContent = event.getInventory().getContents();
		ItemStack[] newInventoryContent = new ItemStack[41];

		int i = -1;
		for (ItemStack itemStack : inventoryContent) {
			i++;
			if (i >= 41) {
				break;
			}

			if (itemStack != null) {
				if (itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() != 69420) {
					newInventoryContent[i] = itemStack;
					continue;
				}
			}

			newInventoryContent[i] = itemStack;
		}

		YamlConfiguration kitConfig = KitConfiguration.getConfig();

		String path = kitName + ".content";
		kitConfig.set(path, newInventoryContent);
		kitConfig.setInlineComments(path, List.of("PLEASE DO NOT EDIT THIS MANUALLY! USE /cs saveKit <kit> COMMAND!"));
		KitConfiguration.save();

		player.sendMessage(miniMessage.deserialize("<green>Kit " + kitName + " has been saved!"));

	}

}
