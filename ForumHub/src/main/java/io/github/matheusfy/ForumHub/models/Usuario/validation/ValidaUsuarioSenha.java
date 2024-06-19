package io.github.matheusfy.ForumHub.models.Usuario.validation;

import io.github.matheusfy.ForumHub.models.Usuario.CadastraUsuarioDTO;

public class ValidaUsuarioSenha implements ValidacaoUsuario {

	@Override
	public void validar(CadastraUsuarioDTO usuarioDTO) {
		// Verifica se a senha tem pelo menos 6 caracteres
		if (usuarioDTO.senha().length() < 6) {
			throw new RuntimeException("Senha deve ter pelo menos 6 caracteres");
		}
	}

}
