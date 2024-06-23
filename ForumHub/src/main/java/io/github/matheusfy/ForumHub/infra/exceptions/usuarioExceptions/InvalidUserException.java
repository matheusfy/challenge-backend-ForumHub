package io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions;

public class InvalidUserException extends RuntimeException {
	public InvalidUserException(String message) {
		super(message);
	}
}
