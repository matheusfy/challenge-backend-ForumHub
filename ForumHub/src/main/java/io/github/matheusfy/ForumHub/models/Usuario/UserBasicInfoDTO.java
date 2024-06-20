package io.github.matheusfy.ForumHub.models.Usuario;

public record UserBasicInfoDTO(
    Long id,
    String nome,
    String email
) {
    public UserBasicInfoDTO(Usuario user) {
        this(user.getId(),user.getNome(), user.getEmail());
    }
}
