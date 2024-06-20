package io.github.matheusfy.ForumHub.controller;

import io.github.matheusfy.ForumHub.models.Usuario.CadastraUsuarioDTO;
import io.github.matheusfy.ForumHub.models.Usuario.UserBasicInfoDTO;
import io.github.matheusfy.ForumHub.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<UserBasicInfoDTO> cadastrarUsuario(@RequestBody @Valid CadastraUsuarioDTO usuario,
			UriComponentsBuilder uriBuilder) {

		UserBasicInfoDTO userInfo = usuarioService.cadastrarUsuario(usuario);
		URI uri = uriBuilder.path("/{id}").buildAndExpand(userInfo.id()).toUri();
		return ResponseEntity.created(uri).body(userInfo);
	}

}
