package io.github.matheusfy.ForumHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.services.TopicoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoService topicoService;

	@GetMapping
	public ResponseEntity buscarTodosTopicos() {

		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<String> cadastrarTopico(@RequestBody @Valid CadastroTopicoDTO topico) {

		topicoService.cadastrarTopico(topico);
		return ResponseEntity.ok().build();
	}

}
