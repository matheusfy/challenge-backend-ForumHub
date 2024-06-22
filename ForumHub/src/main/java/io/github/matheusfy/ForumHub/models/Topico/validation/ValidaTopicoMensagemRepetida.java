package io.github.matheusfy.ForumHub.models.Topico.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.DuplicateTituloAndMessagemException;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.repositories.TopicoRepository;

@Component
public class ValidaTopicoMensagemRepetida implements IValidacaoTopico {

	@Autowired
	TopicoRepository topicoRepository;

	@Override
	public void valida(CadastroTopicoDTO topicoDTO) {

		Optional<Topico> topicoBuscado = topicoRepository
				.findByTituloAndMensagemAndDeletedIsFalse(topicoDTO.titulo(), topicoDTO.mensagem());

		if (topicoBuscado.isPresent()) {
			throw new DuplicateTituloAndMessagemException(
					"Não é possível cadastrar um tópico com a mesma mensagem e título de outro já existente.");
		}
	}

}
