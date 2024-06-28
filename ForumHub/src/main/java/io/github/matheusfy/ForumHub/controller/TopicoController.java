package io.github.matheusfy.ForumHub.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.matheusfy.ForumHub.infra.auth.TokenService;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.dto.TopicoDetailsDTO;
import io.github.matheusfy.ForumHub.services.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/topicos")
@Tag(name = "Tópicos")
public class TopicoController {

  @Autowired
  private TopicoService topicoService;

  @Autowired
  private TokenService tokenService;

  @GetMapping
  @Operation(summary = "Busca todos os tópicos", description = "Retorna todos os tópicos cadastrados")
  public ResponseEntity<Page<TopicoDetailsDTO>> buscarTodosTopicos(
      @PageableDefault(size = 10, page = 0) Pageable pageable) {

    Page<TopicoDetailsDTO> topicosEncontrados = topicoService.getAllTopicos(pageable);
    return ResponseEntity.ok().body(topicosEncontrados);
  }

  @GetMapping("/recentes")
  @Operation(summary = "Busca tópicos recentes", description = "Retorna os 10 tópicos mais recentes cadastrados por paginação")
  public ResponseEntity<Page<TopicoDetailsDTO>> buscarTopicosRecentes(
      @PageableDefault(size = 10, page = 0) Pageable pageable) {

    Page<TopicoDetailsDTO> topicosEncontrados = topicoService.getTopicosRecentes(pageable);
    return ResponseEntity.ok().body(topicosEncontrados);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Busca um tópico", description = "Retorna um tópico	específico")
  public ResponseEntity<TopicoDetailsDTO> buscaTopico(
      @PathVariable @Parameter(name = "id", description = "Topico id", example = "1") Long id) {
    return ResponseEntity.ok().body(topicoService.buscaTopico(id));
  }

  @PostMapping
  @Operation(summary = "Cadastra um tópico", description = "Cadastra um novo tópico")
  public ResponseEntity<TopicoDetailsDTO> cadastrarTopico(
      @RequestBody @Valid CadastroTopicoDTO topico, UriComponentsBuilder uriBuilder) {

    TopicoDetailsDTO topicoCadastrado = topicoService.cadastrarTopico(topico);
    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoCadastrado.id()).toUri();
    return ResponseEntity.created(uri).body(topicoCadastrado);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualiza um tópico", description = "Atualiza um tópico existente")
  public ResponseEntity<TopicoDetailsDTO> atualizarTopico(
      @RequestBody @Valid AtualizacaoTopicoDTO topico, @PathVariable Long id, HttpServletRequest request) {

    TopicoDetailsDTO topicoAtualizado = topicoService.atualizarTopico(topico, id);
    return ResponseEntity.ok().body(topicoAtualizado);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um tópico", description = "Deleta um tópico existente")
  public ResponseEntity<?> deletarTopico(@PathVariable Long id, HttpServletRequest request) {

    String email = tokenService.getSubject(tokenService.stripBearer(request));
    topicoService.deletarTopico(id, email);
    return ResponseEntity.noContent().build();
  }
}
