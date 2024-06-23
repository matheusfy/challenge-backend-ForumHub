package io.github.matheusfy.ForumHub.services;

import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.models.Usuario.dto.CadastraUsuarioDTO;
import io.github.matheusfy.ForumHub.models.Usuario.dto.UserBasicInfoDTO;
import io.github.matheusfy.ForumHub.models.Usuario.validation.IValidacaoUsuario;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	List<IValidacaoUsuario> validacoes;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Transactional
	public UserBasicInfoDTO cadastrarUsuario(CadastraUsuarioDTO usuarioDTO) {

		validacoes.forEach(validacao -> validacao.validar(usuarioDTO));
		Usuario usuario = new Usuario(usuarioDTO.nome(), usuarioDTO.email(),
				passwordEncoder.encode(usuarioDTO.senha()));

		return new UserBasicInfoDTO(usuarioRepository.save(usuario));
	}

	public Page<UserBasicInfoDTO> getAllUsers(Pageable pageable) {
		return usuarioRepository.findAll(pageable).map(UserBasicInfoDTO::new);
	}

}
