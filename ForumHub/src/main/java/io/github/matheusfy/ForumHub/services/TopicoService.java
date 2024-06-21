package io.github.matheusfy.ForumHub.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.TopicoDetailsDTO;
import io.github.matheusfy.ForumHub.models.Topico.validation.IValidacaoTopico;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.repositories.CursoRepository;
import io.github.matheusfy.ForumHub.repositories.TopicoRepository;
import io.github.matheusfy.ForumHub.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class TopicoService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	TopicoRepository topicoRepository;

	@Autowired
	CursoRepository cursoRepository;

	@Autowired
	List<IValidacaoTopico> validacoesTopico;


	@Transactional
	public TopicoDetailsDTO cadastrarTopico(CadastroTopicoDTO topicoDTO) {

		validarTopico(topicoDTO);

		Usuario usuario = usuarioRepository.getReferenceById(topicoDTO.idAutor());
		Topico newTopico = new Topico(topicoDTO, usuario);

		return new TopicoDetailsDTO(topicoRepository.save(newTopico));
	}

	public void validarTopico(CadastroTopicoDTO topicoDTO) {

		validacoesTopico.forEach(validacao -> validacao.valida(topicoDTO));
		// TODO: Implementar validação de curso
		// if (topicoDTO.idCurso() != null) {
		// Optional<Curso> curso = cursoRepository.findById(topicoDTO.idCurso());
		// }
	}

	public Page<TopicoDetailsDTO> getAllTopicos(Pageable pageable) {
		return topicoRepository.findAll(pageable).map(TopicoDetailsDTO::new);
	}

}
