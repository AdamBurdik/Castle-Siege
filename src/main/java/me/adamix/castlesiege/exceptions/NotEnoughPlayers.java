package me.adamix.castlesiege.exceptions;

public class NotEnoughPlayers  extends Exception {
	public NotEnoughPlayers() {
		super("Game cannot be started! Not enough players!");
	}

	public NotEnoughPlayers(Throwable cause) {
		super(cause);
	}
}
