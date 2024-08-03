package me.adamix.castlesiege.exceptions;

public class GameIsActive extends Exception {
	public GameIsActive() {
		super("Game cannot be started! Game is active!");
	}

	public GameIsActive(Throwable cause) {
		super(cause);
	}
}
