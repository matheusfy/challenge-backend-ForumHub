package io.github.matheusfy.ForumHub.infra.filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.matheusfy.ForumHub.infra.auth.TokenService;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository userRepository;

  private final String AUTHORIZATION_HEADER = "Authorization";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = stripBearer(request);

    if (token != null) {

      DecodedJWT decodedJWT = tokenService.validateToken(token);
      String email = decodedJWT.getSubject();
      UserDetails usuario = userRepository.findByEmail(email).get();

      UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(usuario, null,
          usuario.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(userAuth);
    }

    filterChain.doFilter(request, response);
  }

  private String stripBearer(HttpServletRequest request) {

    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      return null;
    }
    return bearerToken.replace("Bearer ", "");
  }
}
