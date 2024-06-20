package io.github.matheusfy.ForumHub.models.Topico;

import jakarta.validation.constraints.NotBlank;

public record CadastroTopicoDTO(

    @NotBlank(message = "Topico deve possuir um título para criação") String titulo,
    @NotBlank(message = "Topico não pode ser cadastrado sem mensagem") String mensagem,
    @NotBlank(message = "Topico deve possuir um autor para criação") Long idAutor,
    Long idCurso) {
}
