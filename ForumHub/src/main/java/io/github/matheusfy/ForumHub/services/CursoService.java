package io.github.matheusfy.ForumHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Curso.CursoCategorias;
import io.github.matheusfy.ForumHub.models.Curso.dto.CadastroCursoDTO;
import io.github.matheusfy.ForumHub.models.Curso.dto.DetalheCursoDTO;
import io.github.matheusfy.ForumHub.repositories.CursoRepository;

@Service
public class CursoService {

  @Autowired
  CursoRepository cursoRepository;

  public DetalheCursoDTO cadastraCurso(CadastroCursoDTO cursoDTO) {

    CursoCategorias cursoCategoria = CursoCategorias.fromString(cursoDTO.categoria());
    Curso cursoEntity = new Curso(cursoDTO.nome(), cursoCategoria);
    return new DetalheCursoDTO(cursoRepository.save(cursoEntity));
  }
}
