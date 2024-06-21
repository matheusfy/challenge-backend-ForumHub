package io.github.matheusfy.ForumHub.models.Topico.dto;

import io.github.matheusfy.ForumHub.models.Topico.StatusTopico;
import io.github.matheusfy.ForumHub.models.Topico.Topico;

public record TopicoDetailsDTO(Long id, String titulo, String autor, String mensagem,
    StatusTopico status

) {
  public TopicoDetailsDTO(Topico t) {
    this(t.getId(), t.getTitulo(), t.getUsuario().getNome(), t.getMensagem(), t.getStatus());
  }
}
