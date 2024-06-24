package io.github.matheusfy.ForumHub.controller;

import io.github.matheusfy.ForumHub.models.Usuario.dto.CadastraUsuarioDTO;
import io.github.matheusfy.ForumHub.models.Usuario.dto.UserBasicInfoDTO;
import io.github.matheusfy.ForumHub.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários")
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  // TODO: implementar para que quem possa fazer a chamada deste método seja
  // apenas um usuário com role de ADMIN
  // @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(summary = "Busca todos os usuários", description = "Retorna todos os usuários cadastrados")
  public ResponseEntity<Page<UserBasicInfoDTO>> buscaTodosUsuarios(Pageable Pageable) {

    Page<UserBasicInfoDTO> users = usuarioService.getAllUsers(Pageable);
    return ResponseEntity.ok().body(users);
  }

  @PostMapping
  @Operation(summary = "Cadastra um novo usuário", description = "Cadastra um novo usuário no sistema")
  public ResponseEntity<UserBasicInfoDTO> cadastrarUsuario(
      @RequestBody @Valid CadastraUsuarioDTO usuario, UriComponentsBuilder uriBuilder) {

    UserBasicInfoDTO userInfo = usuarioService.cadastrarUsuario(usuario);
    URI uri = uriBuilder.path("/{id}").buildAndExpand(userInfo.id()).toUri();
    return ResponseEntity.created(uri).body(userInfo);
  }

}
