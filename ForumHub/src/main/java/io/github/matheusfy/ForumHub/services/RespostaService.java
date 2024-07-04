package io.github.matheusfy.ForumHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.matheusfy.ForumHub.controller.RespostaDetailDto;
import io.github.matheusfy.ForumHub.models.Resposta.Resposta;
import io.github.matheusfy.ForumHub.models.Resposta.dto.RespostaDto;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.repositories.RespostaRepository;
import io.github.matheusfy.ForumHub.repositories.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class RespostaService {

  @Autowired
  RespostaRepository respostaRepository;

  @Autowired
  TopicoRepository topicoRepository;

  @Autowired
  UsuarioService usuarioService;

  @Transactional
  public Long postarResposta(Long idTopico, RespostaDto respostaDto, String userEmail) {

    // valida se topico existe
    Topico topico = topicoRepository.findReferenceByIdAndDeletedFalse(idTopico);
    if (topico == null) {
      throw new IllegalArgumentException("Tópico não encontrado");
    }

    Usuario usuario = usuarioService.getUserByEmail(userEmail);

    Resposta resposta = respostaRepository.save(respostaDto.toResposta(topico, usuario));
    return resposta.getId();
  }

  public Page<RespostaDetailDto> getRespostas(Long idTopico, Pageable pageable) {
    return respostaRepository.getRespostasTopico(idTopico, pageable)
        .map(resp -> new RespostaDetailDto(resp));
  }

  public RespostaDetailDto getResposta(Long idResposta) {
    return respostaRepository.findById(idResposta).map(resp -> new RespostaDetailDto(resp))
        .orElseThrow(() -> new IllegalArgumentException("Resposta não encontrada"));
  }

  public Page<RespostaDetailDto> getRespostasUsuario(String email, Pageable pageable) {
    return respostaRepository.getRespostasUsuario(email, pageable).map(resp -> new RespostaDetailDto(resp));
  }

  @Transactional
  public void deletarResposta(Long idResposta, String userEmail) {
    Resposta resposta = respostaRepository.findById(idResposta)
        .orElseThrow(() -> new IllegalArgumentException("Resposta não encontrada"));

    verifyUser(userEmail, resposta);

    resposta.setDeleted(true);
  }

  @Transactional
  public RespostaDetailDto atualizarResposta(Long idResposta, @Valid RespostaDto respostaDto, String userEmail) {

    Resposta resposta = respostaRepository.getReferenceById(idResposta);

    verifyUser(userEmail, resposta);

    if (resposta.isDeleted()) {
      throw new IllegalArgumentException("Resposta não encontrada");
    }
    // TODO: Caso os dados da atualização seja a mesma, não atualizar retorna 304
    resposta.update(respostaDto);

    return new RespostaDetailDto(resposta);
  }

  private void verifyUser(String userEmail, Resposta resposta) {
    if (!resposta.getUsuario().getEmail().equals(userEmail)) {
      throw new IllegalArgumentException("Usuário não autorizado");
    }
  }
}
