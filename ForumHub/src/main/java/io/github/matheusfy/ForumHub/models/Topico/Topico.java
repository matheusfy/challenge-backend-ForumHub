package io.github.matheusfy.ForumHub.models.Topico;

import java.time.LocalDateTime;

import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Usuario autor;
	private StatusTopico status;
	private LocalDateTime dataCriacao;

	public Topico(CadastroTopicoDTO topicoDTO, Usuario usuario) {
		this.titulo = topicoDTO.titulo();
		this.mensagem = topicoDTO.mensagem();
		this.autor = usuario;
		this.status = StatusTopico.NAO_RESPONDIDO;
		this.dataCriacao = LocalDateTime.now();
	}
}
