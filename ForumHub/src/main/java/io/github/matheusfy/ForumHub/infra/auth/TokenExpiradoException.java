package io.github.matheusfy.ForumHub.infra.auth;

import java.time.Instant;

import com.auth0.jwt.exceptions.TokenExpiredException;

public class TokenExpiradoException extends TokenExpiredException {

  public TokenExpiradoException(String message, Instant expiredOn) {
    super(message, expiredOn);
  }
}
