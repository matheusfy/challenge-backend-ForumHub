package io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopicoNotUpdatedException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ValidaTopicoUpdate implements IValidacaoUpdateTopico {

  @Override
  public void validarUpdate(Topico topicoEntity, AtualizacaoTopicoDTO topico, Long topicoId, Curso curso) {
    if (!topicoEntity.updated(topico, curso)) {
      throw new TopicoNotUpdatedException("Os dados informados são iguais aos atuais");
    }
  }
}
