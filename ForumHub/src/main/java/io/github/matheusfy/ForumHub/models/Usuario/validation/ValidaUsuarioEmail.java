package io.github.matheusfy.ForumHub.models.Usuario.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.models.Usuario.CadastraUsuarioDTO;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;

@Component
public class ValidaUsuarioEmail implements ValidacaoUsuario {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public void validar(CadastraUsuarioDTO usuario) {

		// TODO: Retornar uma exception personalizada

		if (usuarioRepository.findByEmail(usuario.email()).isPresent()) {
			throw new RuntimeException("Email já cadastrado");
		}
	}
}
