package io.github.matheusfy.ForumHub.models.Topico.dto;

import io.github.matheusfy.ForumHub.models.Topico.StatusTopico;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.infra.DateTimeFormatterConfiguration;

public record TopicoDetailsDTO(Long id, String titulo, String autor, String mensagem,
    StatusTopico status, String dataCriacao, String dataUpdate, String curso

) {
  public TopicoDetailsDTO(Topico t) {
    this(t.getId(), t.getTitulo(), t.getUsuario().getNome(), t.getMensagem(), t.getStatus(),
        t.getDataCriacao().format(DateTimeFormatterConfiguration.formatter),
        t.getDataAtualizacao().format(DateTimeFormatterConfiguration.formatter),
        t.getCurso().getNome());
  }
}
