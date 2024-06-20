package io.github.matheusfy.ForumHub.models.Usuario;

import io.github.matheusfy.ForumHub.models.Usuario.validation.ValidacaoUsuario;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	List<ValidacaoUsuario> validacoes;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Transactional
	public UserBasicInfoDTO cadastrarUsuario(CadastraUsuarioDTO usuarioDTO) {

		validacoes.forEach(validacao -> validacao.validar(usuarioDTO));
		Usuario usuario = new Usuario(usuarioDTO);

		return new UserBasicInfoDTO(usuarioRepository.save(usuario));
	}

}
