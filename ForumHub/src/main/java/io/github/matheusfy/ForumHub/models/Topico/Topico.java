package io.github.matheusfy.ForumHub.models.Topico;

import java.time.LocalDateTime;

import java.util.List;

import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Resposta.Resposta;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
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

	private boolean deleted;

	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@OneToMany(mappedBy = "topico")
	private List<Resposta> respostas;

	public Topico(CadastroTopicoDTO topicoDTO, Curso curso, Usuario usuario) {
		this.titulo = topicoDTO.titulo();
		this.mensagem = topicoDTO.mensagem();
		this.usuario = usuario;
		this.status = StatusTopico.ABERTO;
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
		this.curso = curso;
		this.respostas = List.of();
		this.deleted = false;
	}

	public void updateDataAtualizacao() {
		this.dataAtualizacao = LocalDateTime.now();
	}

	private boolean validString(String str) {
		return str != null && !str.isEmpty();
	}

	public void delete() {
		this.deleted = true;
		this.updateDataAtualizacao();
		this.status = StatusTopico.ENCERRADO;
	}

	public boolean updated(AtualizacaoTopicoDTO topico, Curso curso) {
		boolean updated = false;
		if (validString(topico.titulo()) && !topico.titulo().equals(this.titulo)) {
			this.titulo = topico.titulo();
			updated = true;
		}
		if (validString(topico.mensagem()) && !topico.mensagem().equals(this.mensagem)) {
			this.mensagem = topico.mensagem();
			updated = true;
		}
		if ((topico.status() != null) && !topico.status().equals(this.status)) {
			this.status = topico.status();
			updated = true;
		}
		if (this.curso != null && !curso.equals(this.curso)) {
			this.curso = curso;
			updated = true;
		}
		if (updated) {
			updateDataAtualizacao();
		}
		return updated;
	}

	@Override
	public String toString() {
		return "Topico [id=" + id + ", titulo=" + titulo + ", mensagem=" + mensagem + ", status=" + status
				+ ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + ", deleted=" + deleted
				+ ", curso=" + curso + ", usuario=" + usuario + ", respostas=" + respostas + "]";
	}
}
