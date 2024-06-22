package io.github.matheusfy.ForumHub.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import io.github.matheusfy.ForumHub.models.Curso.dto.CadastroCursoDTO;
import io.github.matheusfy.ForumHub.models.Curso.dto.DetalheCursoDTO;
import io.github.matheusfy.ForumHub.services.CursoService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/cursos")
public class CursoController {

  @Autowired
  CursoService cursoService;

  @PostMapping
  public ResponseEntity<DetalheCursoDTO> cadastraCurso(
      @RequestBody @Valid CadastroCursoDTO cursoDTO, UriComponentsBuilder uriBuilder) {

    DetalheCursoDTO cursoCadastrado = cursoService.cadastraCurso(cursoDTO);
    URI uri = uriBuilder.path("/{id}").buildAndExpand(cursoCadastrado.id()).toUri();
    return ResponseEntity.created(uri).body(cursoCadastrado);
  }

}
