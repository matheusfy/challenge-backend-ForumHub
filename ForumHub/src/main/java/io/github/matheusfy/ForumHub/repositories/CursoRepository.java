package io.github.matheusfy.ForumHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.matheusfy.ForumHub.models.Curso.Curso;
import jakarta.validation.constraints.NotBlank;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

  Curso getReferenceByNome(
      @NotBlank(message = "Topico deve possuir um curso para criação") String cursoNome);

}
