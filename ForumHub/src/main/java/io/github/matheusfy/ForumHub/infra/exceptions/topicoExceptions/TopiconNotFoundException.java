package io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions;


public class TopiconNotFoundException extends RuntimeException {

  public TopiconNotFoundException(String message) {
    super(message);
  }
}
