package io.github.matheusfy.ForumHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.matheusfy.ForumHub.controller.RespostaDetailDto;
import io.github.matheusfy.ForumHub.models.Resposta.dto.RespostaDto;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.repositories.RespostaRepository;
import io.github.matheusfy.ForumHub.repositories.TopicoRepository;
import jakarta.transaction.Transactional;

@Service
public class RespostaService {

  @Autowired
  RespostaRepository respostaRepository;

  @Autowired
  TopicoRepository topicoRepository;

  @Autowired
  UsuarioService usuarioService;

  @Transactional
  public void postarResposta(Long idTopico, RespostaDto respostaDto, String userEmail) {

    // valida se topico existe
    Topico topico = topicoRepository.findReferenceByIdAndDeletedFalse(idTopico);
    if (topico == null) {
      throw new IllegalArgumentException("Tópico não encontrado");
    }

    Usuario usuario = usuarioService.getUserByEmail(userEmail);

    respostaRepository.save(respostaDto.toResposta(topico, usuario));
  }

  public Page<RespostaDetailDto> getRespostas(Long idTopico, Pageable pageable) {
    return respostaRepository.getRespostaTopico(idTopico, pageable)
        .map(resp -> new RespostaDetailDto(resp));
  }

}
