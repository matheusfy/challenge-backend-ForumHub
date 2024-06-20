package io.github.matheusfy.ForumHub.models.Usuario.validation;

import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.models.Usuario.CadastraUsuarioDTO;

@Component
public interface ValidacaoUsuario {

	public void validar(CadastraUsuarioDTO usuarioDTO);
}
