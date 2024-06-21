package io.github.matheusfy.ForumHub.models.Usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastraUsuarioDTO(
    @NotBlank(message = "Deve ser informado um nome de usuário") String nome,
    @NotBlank(message = "Email não pode ser vazio") String email,
    @NotBlank(message = "Senha deve ser informada para cadastrar usuário") String senha) {

}
