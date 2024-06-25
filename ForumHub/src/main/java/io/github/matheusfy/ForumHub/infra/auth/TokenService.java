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
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

  private final String secret;

  private final String ISSUER = "api-forumhub";
  private final String AUTHORIZATION_HEADER = "Authorization";

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

  public DecodedJWT validateToken(String token) {

    Algorithm algorithm = Algorithm.HMAC256(secret);
    try {
      return JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
    } catch (TokenExpiradoException ex) {
      throw new TokenExpiradoException("Token expirado", ex.getExpiredOn());
    } catch (JWTVerificationException e) {
      throw new JWTVerificationException("Falha no processamento do token", e.getCause());
    }
  }

  public String getSubject(String token) {
    DecodedJWT decodedJWT = validateToken(token);
    return decodedJWT.getSubject();
  }

  public String stripBearer(HttpServletRequest request) {

    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      return null;
    }
    return bearerToken.replace("Bearer ", "");
  }
}
