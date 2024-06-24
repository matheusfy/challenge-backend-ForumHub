package io.github.matheusfy.ForumHub.models.Topico.validation.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.cursoExceptions.CursoInvalidoException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.repositories.CursoRepository;

@Component
public class ValidaCursoExistente implements IValidacaoTopico {

  @Autowired
  CursoRepository cursoRepository;

  @Override
  public void valida(CadastroTopicoDTO topicoDTO) {
    Curso curso = cursoRepository.getReferenceByNome(topicoDTO.cursoNome());
    if (curso == null) {
      throw new CursoInvalidoException("Curso não encontrado");
    }
  }

}
