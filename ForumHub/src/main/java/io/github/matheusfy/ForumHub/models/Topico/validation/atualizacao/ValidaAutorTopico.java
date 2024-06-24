package io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao;

import java.util.Optional;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.UserUnauthorizedException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.validation.delecao.IValidacaoDeleteTopico;

@Component
public class ValidaAutorTopico implements IValidacaoUpdateTopico, IValidacaoDeleteTopico {

  @Override
  public void validarUpdate(Topico topicoEntity, AtualizacaoTopicoDTO topico, Long topicoId, Curso curso) {
    String msg = "Usuário não autorizado a atualizar este tópico.";
    validarAutor(topicoEntity.getUsuario().getId(), topico.idAutor(), msg);
  }

  @Override
  public void validarDelete(Optional<Topico> topicoEntity, Long requestUserId) {

    String msg = "Usuário não autorizado a deletar este tópico.";
    validarAutor(topicoEntity.get().getUsuario().getId(), requestUserId, msg);
  }

  private void validarAutor(Long topicoAutorId, Long requestUserId, String message) {
    if (!topicoAutorId.equals(requestUserId)) {
      throw new UserUnauthorizedException(message);
    }
  }
}
