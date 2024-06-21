package io.github.matheusfy.ForumHub.models.Usuario.validation;

import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.SenhaInvalidException;
import io.github.matheusfy.ForumHub.models.Usuario.dto.CadastraUsuarioDTO;

import org.springframework.stereotype.Component;

@Component
public class ValidaUsuarioSenha implements IValidacaoUsuario {

	@Override
	public void validar(CadastraUsuarioDTO usuarioDTO) {
		// Verifica se a senha tem pelo menos 6 caracteres
		if (usuarioDTO.senha().length() < 6) {
			throw new SenhaInvalidException();
		}
	}

}
