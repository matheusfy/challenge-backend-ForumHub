package io.github.matheusfy.ForumHub.infra.exceptions;

public class InvalidUserException extends RuntimeException {
	public InvalidUserException(String message) {
		super(message);
	}
}
