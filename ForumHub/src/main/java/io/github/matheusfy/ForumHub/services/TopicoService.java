package io.github.matheusfy.ForumHub.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.repositories.CursoRepository;
import io.github.matheusfy.ForumHub.repositories.TopicoRepository;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;

@Service
public class TopicoService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	TopicoRepository topicoRepository;

	@Autowired
	CursoRepository cursoRepository;

	public void cadastrarTopico(CadastroTopicoDTO topicoDTO) {
		validarTopico(topicoDTO);
		Usuario usuario = usuarioRepository.getReferenceById(topicoDTO.idAutor());
		Topico newTopico = new Topico(topicoDTO, usuario);
		topicoRepository.save(newTopico);
	}

	public void validarTopico(CadastroTopicoDTO topicoDTO) {

		Optional<Usuario> usuario = usuarioRepository.findById(topicoDTO.idAutor());
		if (usuario.isEmpty()) {
			throw new InvalidUserException("Usuário não encontrado");
		}

		// TODO: Implementar validação de curso
		// if (topicoDTO.idCurso() != null) {
		// Optional<Curso> curso = cursoRepository.findById(topicoDTO.idCurso());
		// }
	}

}
