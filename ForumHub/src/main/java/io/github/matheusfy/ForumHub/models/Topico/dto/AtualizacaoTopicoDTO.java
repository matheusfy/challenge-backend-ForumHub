package io.github.matheusfy.ForumHub.models.Topico.dto;

import io.github.matheusfy.ForumHub.models.Topico.StatusTopico;

public record AtualizacaoTopicoDTO(String titulo, String mensagem, Long idAutor, String cursoNome,
        StatusTopico status) {

}
