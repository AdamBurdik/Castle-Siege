package me.adamix.castlesiege.exceptions;

public class FullTeamException extends Exception {
	public FullTeamException() {
		super("Team is full! No more players can join");
	}

	public FullTeamException(Throwable cause) {
		super(cause);
	}
}
