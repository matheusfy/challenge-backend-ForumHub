package io.github.matheusfy.ForumHub.models.Usuario.dto;

import io.github.matheusfy.ForumHub.models.Usuario.Usuario;

public record UserBasicInfoDTO(
        Long id,
        String nome,
        String email) {
    public UserBasicInfoDTO(Usuario user) {
        this(user.getId(), user.getNome(), user.getEmail());
    }
}
