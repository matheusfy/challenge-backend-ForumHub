package io.github.matheusfy.ForumHub.models.Topico;

import java.time.LocalDateTime;

import java.util.List;

import io.github.matheusfy.ForumHub.models.Resposta.Resposta;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "topicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;
	private String mensagem;

	@Enumerated(EnumType.STRING)
	private StatusTopico status;

	private LocalDateTime dataCriacao;
	private LocalDateTime dataAtualizacao;

	// private Long cursoId;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@OneToMany(mappedBy = "topico")
	private List<Resposta> respostas;

	public Topico(CadastroTopicoDTO topicoDTO, Usuario usuario) {
		this.titulo = topicoDTO.titulo();
		this.mensagem = topicoDTO.mensagem();
		this.usuario = usuario;
		this.status = StatusTopico.ABERTO;
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
		// this.cursoId = topicoDTO.cursoId();
		this.respostas = List.of();
	}
}
