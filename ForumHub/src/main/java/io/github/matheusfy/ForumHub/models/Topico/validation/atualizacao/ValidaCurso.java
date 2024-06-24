package io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.cursoExceptions.CursoInvalidoException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.repositories.CursoRepository;

@Component
public class ValidaCurso implements IValidacaoUpdateTopico {

  @Autowired
  CursoRepository cursoRepository;

  @Override
  public void validarUpdate(Topico topicoEntity, AtualizacaoTopicoDTO topico, Long topicoId, Curso curso) {
    if (curso == null) {
      throw new CursoInvalidoException("Curso informado não existe.");
    }
  }
}
