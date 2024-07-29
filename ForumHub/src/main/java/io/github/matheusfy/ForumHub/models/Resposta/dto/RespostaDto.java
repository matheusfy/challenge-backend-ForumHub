package io.github.matheusfy.ForumHub.models.Resposta.dto;

import java.time.LocalDateTime;

import io.github.matheusfy.ForumHub.models.Resposta.Resposta;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

public record RespostaDto(

        @NotBlank(message = "mensagem de resposta não pode ser vazia") String mensagem,
        String solucao) {

    public Resposta toResposta(Topico topico, Usuario usuario) {
        return new Resposta(null, mensagem, solucao, LocalDateTime.now(), false, topico, usuario);
    }
}
