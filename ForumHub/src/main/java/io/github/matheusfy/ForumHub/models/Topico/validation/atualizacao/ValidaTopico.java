package io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao;

import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopiconNotFoundException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;

@Component
public class ValidaTopico implements IValidacaoUpdateTopico {

  @Override
  public void validarUpdate(Topico topicoEntity, AtualizacaoTopicoDTO topico, Long topicoId, Curso curso) {
    if (topicoEntity == null) {
      throw new TopiconNotFoundException("Topico não encontrado para atualização");
    }
  }
}
