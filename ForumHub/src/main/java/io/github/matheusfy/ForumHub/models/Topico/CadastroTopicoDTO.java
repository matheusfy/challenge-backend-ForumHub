package io.github.matheusfy.ForumHub.models.Topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroTopicoDTO(

		@NotBlank(message = "Topico deve possuir um título para criação") String titulo,
		@NotBlank(message = "Topico não pode ser cadastrado sem mensagem") String mensagem,
		@NotNull(message = "Topico deve possuir um autor para criação") Long idAutor,
		@NotBlank(message = "Topico deve possuir um curso para criação") String cursoNome) {
}
