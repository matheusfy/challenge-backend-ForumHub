package io.github.matheusfy.ForumHub.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import io.github.matheusfy.ForumHub.controller.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopicoNotUpdatedException;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopiconNotFoundException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
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
		Curso curso = cursoRepository.getReferenceByNome(topicoDTO.cursoNome());
		Topico newTopico = new Topico(topicoDTO, curso, usuario);

		return new TopicoDetailsDTO(topicoRepository.save(newTopico));
	}

	public void validarTopico(CadastroTopicoDTO topicoDTO) {
		validacoesTopico.forEach(validacao -> validacao.valida(topicoDTO));
	}

	public Page<TopicoDetailsDTO> getAllTopicos(Pageable pageable) {
		return topicoRepository.findAllByDeletedIsFalse(pageable).map(TopicoDetailsDTO::new);
	}

	public Page<TopicoDetailsDTO> getTopicosRecentes(Pageable pageable) {
		return topicoRepository.findAllByDeletedIsFalseOrderByDataCriacaoDesc(pageable)
				.map(TopicoDetailsDTO::new);
	}

	public TopicoDetailsDTO buscaTopico(Long id) {

		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isEmpty()) {
			throw new TopiconNotFoundException("Topico não encontrado");
		}
		if (topico.get().isDeleted()) {
			throw new TopicoDeletedException("Este topico foi excluído pelo autor");
		}

		return new TopicoDetailsDTO(topico.get());
	}

	@Transactional
	public TopicoDetailsDTO atualizarTopico(AtualizacaoTopicoDTO topico, Long id) {

		// TODO: Deve ser validado se quem está alterando as informações do Topico é o próprio autor

		Optional<Topico> topicoEntity = topicoRepository.findByIdAndDeletedIsFalse(id);
		if (topicoEntity.isEmpty()) {
			throw new TopiconNotFoundException("Topico não encontrado para atualização");
		}

		Curso curso = cursoRepository.getReferenceByNome(topico.cursoNome());
		if (!topicoEntity.get().updated(topico, curso)) {
			throw new TopicoNotUpdatedException("Os dados informados são iguais aos atuais");
		}

		return new TopicoDetailsDTO(topicoRepository.save(topicoEntity.get()));
	}

	@Transactional
	public void deletarTopico(Long id) {


		// TODO: Adicionar logica para verificar se usuario é o autor do topico

		Optional<Topico> topico = topicoRepository.findByIdAndDeletedIsFalse(id);
		if (topico.isEmpty()) {
			throw new TopiconNotFoundException("Topico não encontrado");
		}

		// adicionar um logger antes da deleção e outro depois para registro de exclusão
		topico.get().delete();

		// verificar se tem algumas respostas vinculado a este tópico. Pensar se deve deletar também ou
		// não as respostas
	}
}
