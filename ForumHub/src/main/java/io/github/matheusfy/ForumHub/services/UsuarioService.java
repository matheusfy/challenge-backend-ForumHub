package io.github.matheusfy.ForumHub.services;

import io.github.matheusfy.ForumHub.models.Usuario.CadastraUsuarioDTO;
import io.github.matheusfy.ForumHub.models.Usuario.UserBasicInfoDTO;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.models.Usuario.validation.ValidacaoUsuario;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<UserBasicInfoDTO> getAllUsers(Pageable pageable) {
		return usuarioRepository.findAll(pageable).map(UserBasicInfoDTO::new);
	}

}
