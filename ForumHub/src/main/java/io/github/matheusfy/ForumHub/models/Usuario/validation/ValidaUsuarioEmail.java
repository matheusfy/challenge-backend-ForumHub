package io.github.matheusfy.ForumHub.models.Usuario.validation;

import io.github.matheusfy.ForumHub.models.Usuario.exceptions.DuplicatedEmailException;
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
		if (usuarioRepository.findByEmail(usuario.email()).isPresent()) {
			throw new DuplicatedEmailException("Email já cadastrado");
		}
	}
}
