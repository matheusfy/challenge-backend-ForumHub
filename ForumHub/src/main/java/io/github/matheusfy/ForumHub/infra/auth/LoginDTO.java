package io.github.matheusfy.ForumHub.infra.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank(message = "login não informado") String login,
    @NotBlank(message = "senha não foi informado") String senha) {
}
