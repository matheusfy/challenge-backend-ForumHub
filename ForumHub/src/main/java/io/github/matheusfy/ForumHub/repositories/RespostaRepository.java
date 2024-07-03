package io.github.matheusfy.ForumHub.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.matheusfy.ForumHub.models.Resposta.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {

  // não buscar com * pois é realizado 2 querys, uma para buscar os objetos e
  // outra para pegar a quantidades dos objetos. Quando se usa * ocorre um erro na
  // conversão
  @Query(value = """
      select r.id, r.mensagem, r.topico_id, r.data_criacao, r.usuario_id, r.solucao from respostas as r
      join topicos on r.topico_id = topicos.id
      where topicos.id = ?1 and topicos.deleted = false
      """, nativeQuery = true)
  Page<Resposta> getRespostaTopico(Long idTopico, Pageable pageable);

}
