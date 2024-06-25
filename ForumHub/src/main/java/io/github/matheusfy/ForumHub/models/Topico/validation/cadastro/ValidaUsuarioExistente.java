package io.github.matheusfy.ForumHub.models.Topico.validation.cadastro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.InvalidUserException;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;

@Component
public class ValidaUsuarioExistente implements IValidacaoTopico {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public void valida(CadastroTopicoDTO topicoDTO) {
		Optional<Usuario> usuario = usuarioRepository.findById(topicoDTO.idAutor());
		if (usuario.isEmpty()) {
			throw new InvalidUserException("Usuário não encontrado");
		}
	}
}
