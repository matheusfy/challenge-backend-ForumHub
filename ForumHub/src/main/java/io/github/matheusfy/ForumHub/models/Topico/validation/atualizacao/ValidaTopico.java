package io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao;

import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopiconNotFoundException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.validation.delecao.IValidacaoDeleteTopico;

@Component
@Order(1)
public class ValidaTopico implements IValidacaoUpdateTopico, IValidacaoDeleteTopico {

  @Override
  public void validarUpdate(Topico topicoEntity, AtualizacaoTopicoDTO topico, Long topicoId, Curso curso) {
    if (topicoEntity == null) {
      throw new TopiconNotFoundException("Topico não encontrado para atualização");
    }
  }

  @Override
  public void validarDelete(Optional<Topico> topicoEntity, Long requestUserId) {
    if (!topicoEntity.isPresent()) {
      throw new TopiconNotFoundException("Topico não encontrado");
    }
  }
}
