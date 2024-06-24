package io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao;

import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.UserUnauthorizedException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;

@Component
public class ValidaAutorTopico implements IValidacaoUpdateTopico {

  @Override
  public void validarUpdate(Topico topicoEntity, AtualizacaoTopicoDTO topico, Long topicoId, Curso curso) {
    if (topicoEntity.getUsuario().getId() != topico.idAutor()) {
      throw new UserUnauthorizedException("Usuário não autorizado a atualizar este tópico.");
    }
  }
}
