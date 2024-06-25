package io.github.matheusfy.ForumHub.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopicoDeletedException;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopiconNotFoundException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.dto.TopicoDetailsDTO;
import io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao.IValidacaoUpdateTopico;
import io.github.matheusfy.ForumHub.models.Topico.validation.cadastro.IValidacaoTopico;
import io.github.matheusfy.ForumHub.models.Topico.validation.delecao.IValidacaoDeleteTopico;
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

  @Autowired
  List<IValidacaoUpdateTopico> validacoesUpdateTopico;

  @Autowired
  List<IValidacaoDeleteTopico> validacoesDeleteTopico;

  @Transactional
  public TopicoDetailsDTO cadastrarTopico(CadastroTopicoDTO topicoDTO) {

    validacoesTopico.forEach(validacao -> validacao.valida(topicoDTO));

    Usuario usuario = usuarioRepository.getReferenceById(topicoDTO.idAutor());
    Curso curso = cursoRepository.getReferenceByNome(topicoDTO.cursoNome());
    Topico newTopico = new Topico(topicoDTO, curso, usuario);

    return new TopicoDetailsDTO(topicoRepository.save(newTopico));
  }

  public Page<TopicoDetailsDTO> getAllTopicos(Pageable pageable) {
    return topicoRepository.findAllByDeletedIsFalse(pageable).map(TopicoDetailsDTO::new);
  }

  public Page<TopicoDetailsDTO> getTopicosRecentes(Pageable pageable) {
    return topicoRepository.findAllByDeletedIsFalseOrderByDataCriacaoDesc(pageable)
        .map(TopicoDetailsDTO::new);
  }

  public TopicoDetailsDTO buscaTopico(Long id) {

    Topico topico = topicoRepository.findById(id).get();

    if (topico == null) {
      throw new TopiconNotFoundException("Topico não encontrado");
    }
    if (topico.isDeleted()) {
      throw new TopicoDeletedException("Este topico foi excluído pelo autor");
    }

    return new TopicoDetailsDTO(topico);
  }

  @Transactional
  public TopicoDetailsDTO atualizarTopico(AtualizacaoTopicoDTO topicoDTO, Long topicoId) {

    Topico topicoEntity = topicoRepository.findByIdAndDeletedIsFalse(topicoId).get();
    Curso curso = cursoRepository.getReferenceByNome(topicoDTO.cursoNome());

    validacoesUpdateTopico.forEach(validacao -> validacao.validarUpdate(topicoEntity, topicoDTO, topicoId, curso));

    return new TopicoDetailsDTO(topicoEntity);
  }

  @Transactional
  public void deletarTopico(Long topicoId, String email) {

    Long requestUserId = usuarioRepository.findByEmail(email).get().getId();

    Optional<Topico> topico = topicoRepository.findByIdAndDeletedIsFalse(topicoId);

    validacoesDeleteTopico.forEach(validacao -> validacao.validarDelete(topico, requestUserId));

    // adicionar um logger antes da deleção e outro depois para registro de exclusão
    topico.get().delete();

    // verificar se tem algumas respostas vinculado a este tópico. Pensar se deve
    // deletar também ou
    // não as respostas
  }
}
