package me.adamix.castlesiege.expansions;

import me.adamix.castlesiege.player.GamePlayer;
import me.adamix.castlesiege.player.GamePlayerManager;
import me.adamix.castlesiege.team.Team;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerTeamExpansion extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "cs_playerteam";
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
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
		GamePlayer gamePlayer = GamePlayerManager.getGamePlayer(player);
		Team team = gamePlayer.getTeam();
		return team.getName();
	}
}
