package io.github.matheusfy.ForumHub.infra.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;

@Service
public class TokenService {

  private final String secret;

  private final String ISSUER = "api-forumhub";

  public TokenService(@Value("${jwt.secret}") String secret) {
    this.secret = secret;
  }


  public String generateToken(Authentication authentication) {

    Algorithm algorithm = Algorithm.HMAC256(secret);

    try {
      Usuario usuario = (Usuario) authentication.getPrincipal();
      return JWT.create().withIssuer(ISSUER).withSubject(usuario.getEmail())
          .withExpiresAt(getExpirationDate()).sign(algorithm);
    } catch (JWTCreationException e) {
      System.out.println("Erro ao gerar token: " + e.getMessage());
      throw new JWTCreationException("Erro ao gerar token: " + e.getMessage(), e);
    }
  }

  private Date getExpirationDate() {
    return Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
  }
}
