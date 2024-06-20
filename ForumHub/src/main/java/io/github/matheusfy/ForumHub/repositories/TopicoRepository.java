package io.github.matheusfy.ForumHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.matheusfy.ForumHub.models.Topico.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

}
