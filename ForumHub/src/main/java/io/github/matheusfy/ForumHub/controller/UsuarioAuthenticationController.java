package io.github.matheusfy.ForumHub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.matheusfy.ForumHub.infra.auth.DataTokenDTO;
import io.github.matheusfy.ForumHub.infra.auth.LoginDTO;
import io.github.matheusfy.ForumHub.infra.auth.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class UsuarioAuthenticationController {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<DataTokenDTO> loginUser(@RequestBody @Valid LoginDTO userLogin) {

    UsernamePasswordAuthenticationToken userAuth =
        new UsernamePasswordAuthenticationToken(userLogin.login(), userLogin.senha());

    Authentication authentication = authManager.authenticate(userAuth);

    String token = tokenService.generateToken(authentication);

    return ResponseEntity.ok().body(new DataTokenDTO(token));
  }
}
