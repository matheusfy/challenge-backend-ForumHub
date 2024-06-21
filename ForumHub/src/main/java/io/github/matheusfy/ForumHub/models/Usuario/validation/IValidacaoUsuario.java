package io.github.matheusfy.ForumHub.models.Usuario.validation;

import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.models.Usuario.dto.CadastraUsuarioDTO;

@Component
public interface IValidacaoUsuario {

	public void validar(CadastraUsuarioDTO usuarioDTO);
}
