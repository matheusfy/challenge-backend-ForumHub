package io.github.matheusfy.ForumHub.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.dto.TopicoDetailsDTO;
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
	public ResponseEntity<Page<TopicoDetailsDTO>> buscarTodosTopicos(Pageable pageable) {

		Page<TopicoDetailsDTO> topicosEncontrados = topicoService.getAllTopicos(pageable);
		return ResponseEntity.ok().body(topicosEncontrados);
	}

	@PostMapping
	public ResponseEntity<TopicoDetailsDTO> cadastrarTopico(
			@RequestBody @Valid CadastroTopicoDTO topico, UriComponentsBuilder uriBuilder) {

		TopicoDetailsDTO topicoCadastrado = topicoService.cadastrarTopico(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoCadastrado.id()).toUri();
		return ResponseEntity.created(uri).body(topicoCadastrado);
	}

}
