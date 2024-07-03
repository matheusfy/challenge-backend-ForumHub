package io.github.matheusfy.ForumHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<?> postarResposta(@RequestParam Long idTopico, @RequestBody @Valid RespostaDto respostaDto,
      HttpServletRequest request) {

    String userEmail = tokenService.getSubject(tokenService.stripBearer(request));
    respostaService.postarResposta(idTopico, respostaDto, userEmail);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/resposta/{idTopico}")
  public ResponseEntity<Page<RespostaDetailDto>> getRespostas(@RequestParam Long idTopico,
      @PageableDefault(size = 10, page = 0) Pageable pageable) {
    return ResponseEntity.ok(respostaService.getRespostas(idTopico, pageable));
  }

}
