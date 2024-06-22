package io.github.matheusfy.ForumHub.models.Curso.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroCursoDTO(

        @NotBlank(message = "nome do curso não foi informado.") String nome,
        @NotBlank(message = "Categoria do curso não foi informado") String categoria) {
}
