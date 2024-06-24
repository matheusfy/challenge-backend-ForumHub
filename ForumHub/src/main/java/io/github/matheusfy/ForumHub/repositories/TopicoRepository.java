package io.github.matheusfy.ForumHub.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.matheusfy.ForumHub.models.Topico.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	List<Topico> findByTituloAndMensagemAndDeletedIsFalse(String titulo, String mensagem);

	Page<Topico> findTop10ByDeletedIsFalseOrderByDataCriacaoDesc(Pageable pageable);

	Page<Topico> findAllByDeletedIsFalseOrderByDataCriacaoDesc(Pageable pageable);

	Optional<Topico> findByIdAndDeletedIsFalse(Long id);

	Page<Topico> findAllByDeletedIsFalse(Pageable pageable);

}
