package io.github.matheusfy.ForumHub.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.dto.TopicoDetailsDTO;
import io.github.matheusfy.ForumHub.services.TopicoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoService topicoService;

	@GetMapping
	public ResponseEntity<Page<TopicoDetailsDTO>> buscarTodosTopicos(
			@PageableDefault(size = 10, page = 0) Pageable pageable) {

		Page<TopicoDetailsDTO> topicosEncontrados = topicoService.getAllTopicos(pageable);
		return ResponseEntity.ok().body(topicosEncontrados);
	}

	@GetMapping("/recentes")
	public ResponseEntity<Page<TopicoDetailsDTO>> buscarTopicosRecentes(
			@PageableDefault(size = 10, page = 0) Pageable pageable) {

		Page<TopicoDetailsDTO> topicosEncontrados = topicoService.getTopicosRecentes(pageable);
		return ResponseEntity.ok().body(topicosEncontrados);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicoDetailsDTO> buscaTopico(@PathVariable Long id) {
		return ResponseEntity.ok().body(topicoService.buscaTopico(id));
	}


	@PostMapping
	public ResponseEntity<TopicoDetailsDTO> cadastrarTopico(
			@RequestBody @Valid CadastroTopicoDTO topico, UriComponentsBuilder uriBuilder) {

		TopicoDetailsDTO topicoCadastrado = topicoService.cadastrarTopico(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoCadastrado.id()).toUri();
		return ResponseEntity.created(uri).body(topicoCadastrado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TopicoDetailsDTO> atualizarTopico(
			@RequestBody @Valid AtualizacaoTopicoDTO topico, @PathVariable Long id) {

		TopicoDetailsDTO topicoAtualizado = topicoService.atualizarTopico(topico, id);
		return ResponseEntity.ok().body(topicoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarTopico(@PathVariable Long id) {
		topicoService.deletarTopico(id);
		return ResponseEntity.noContent().build();
	}
}
