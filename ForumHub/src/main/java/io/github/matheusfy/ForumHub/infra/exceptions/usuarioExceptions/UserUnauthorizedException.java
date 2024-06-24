package io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions;

public class UserUnauthorizedException extends RuntimeException {

  public UserUnauthorizedException(String message) {
    super(message);
  }
}
