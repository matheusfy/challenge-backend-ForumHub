package io.github.matheusfy.ForumHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.matheusfy.ForumHub.models.Curso.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
