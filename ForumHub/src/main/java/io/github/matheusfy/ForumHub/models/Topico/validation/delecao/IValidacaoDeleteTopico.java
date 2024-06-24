package io.github.matheusfy.ForumHub.models.Topico.validation.delecao;

import io.github.matheusfy.ForumHub.models.Topico.Topico;
import java.util.Optional;

public interface IValidacaoDeleteTopico {

  void validarDelete(Optional<Topico> topicoEntity, Long autorId);
}
