package io.github.matheusfy.ForumHub.models.Curso.dto;

import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Curso.CursoCategorias;

public record DetalheCursoDTO(Long id, String nome, CursoCategorias categoria) {

  public DetalheCursoDTO(Curso c) {
    this(c.getId(), c.getNome(), c.getCategoria());
  }
}
