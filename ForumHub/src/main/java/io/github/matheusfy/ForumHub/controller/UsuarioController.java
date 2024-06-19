package io.github.matheusfy.ForumHub.controller;

import org.springframework.web.bind.annotation.RestController;

import io.github.matheusfy.ForumHub.models.Usuario.CadastraUsuarioDTO;
import io.github.matheusfy.ForumHub.models.Usuario.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid CadastraUsuarioDTO usuario) {

		usuarioService.cadastrarUsuario(usuario);
		return ResponseEntity.ok().build();
	}

}
