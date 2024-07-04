package io.github.matheusfy.ForumHub.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.matheusfy.ForumHub.infra.auth.TokenService;
import io.github.matheusfy.ForumHub.models.Resposta.dto.RespostaDto;
import io.github.matheusfy.ForumHub.services.RespostaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

  @Autowired
  RespostaService respostaService;

  @Autowired
  TokenService tokenService;

  @PostMapping("/resposta/{idTopico}")
  public ResponseEntity<?> postarResposta(@PathVariable Long idTopico, @RequestBody @Valid RespostaDto respostaDto,
      HttpServletRequest request) {

    String userEmail = tokenService.getSubject(tokenService.stripBearer(request));
    Long respostaId = respostaService.postarResposta(idTopico, respostaDto, userEmail);
    URI uri = UriComponentsBuilder.fromUriString("/resposta/{respostaId}").buildAndExpand(respostaId).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/{idTopico}")
  public ResponseEntity<Page<RespostaDetailDto>> getRespostas(@RequestParam Long idTopico,
      @PageableDefault(size = 10, page = 0) Pageable pageable) {
    return ResponseEntity.ok(respostaService.getRespostas(idTopico, pageable));
  }

  @GetMapping("/resposta/{idResposta}")
  public ResponseEntity<RespostaDetailDto> getResposta(@PathVariable Long idResposta) {
    return ResponseEntity.ok(respostaService.getResposta(idResposta));
  }

  @GetMapping("/user")
  public ResponseEntity<Page<RespostaDetailDto>> getRespostasUsuario(
      @PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request) {

    String userEmail = tokenService.getSubject(tokenService.stripBearer(request));
    return ResponseEntity.ok(respostaService.getRespostasUsuario(userEmail, pageable));
  }

  @PutMapping("/resposta/{idResposta}")
  public ResponseEntity<RespostaDetailDto> atualizarResposta(@PathVariable Long idResposta,
      @RequestBody @Valid RespostaDto respostaDto,
      HttpServletRequest request) {
    String userEmail = tokenService.getSubject(tokenService.stripBearer(request));
    RespostaDetailDto respDto = respostaService.atualizarResposta(idResposta, respostaDto, userEmail);
    return ResponseEntity.ok(respDto);
  }

  @DeleteMapping("/resposta/{idResposta}")
  public ResponseEntity<?> deletarResposta(@PathVariable Long idResposta, HttpServletRequest request) {
    String userEmail = tokenService.getSubject(tokenService.stripBearer(request));
    respostaService.deletarResposta(idResposta, userEmail);
    return ResponseEntity.noContent().build();
  }
}
