package io.github.matheusfy.ForumHub.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.matheusfy.ForumHub.models.Topico.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);

}
