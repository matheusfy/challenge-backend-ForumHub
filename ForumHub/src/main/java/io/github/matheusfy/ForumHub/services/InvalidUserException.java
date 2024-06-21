package io.github.matheusfy.ForumHub.services;

public class InvalidUserException extends RuntimeException {
	public InvalidUserException(String message) {
		super(message);
	}
}
