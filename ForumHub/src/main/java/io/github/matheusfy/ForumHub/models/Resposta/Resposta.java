package io.github.matheusfy.ForumHub.models.Resposta;

import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mensagem;
	private String solucao;
	private LocalDateTime dataCriacao;

	@ManyToOne
	@JoinColumn(name = "topico_id")
	private Topico topico;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

}
