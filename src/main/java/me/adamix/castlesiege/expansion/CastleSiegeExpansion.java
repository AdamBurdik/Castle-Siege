package me.adamix.castlesiege.expansion;

import me.adamix.castlesiege.player.GamePlayer;
import me.adamix.castlesiege.player.GamePlayerManager;
import me.adamix.castlesiege.team.Team;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CastleSiegeExpansion extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "cs";
	}

	@Override
	public @NotNull String getAuthor() {
		return "AdamIx";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
		if (params.equalsIgnoreCase("playerteam")) {
			GamePlayer gamePlayer = GamePlayerManager.getGamePlayer((Player) player);
			Team team = gamePlayer.getTeam();
			return team.getName();
		}

		return null;
	}
}
