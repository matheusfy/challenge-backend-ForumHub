package io.github.matheusfy.ForumHub.models.Topico.validation.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.DuplicateTituloAndMessagemException;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.validation.atualizacao.IValidacaoUpdateTopico;
import io.github.matheusfy.ForumHub.repositories.TopicoRepository;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidaTopicoMensagemRepetida implements IValidacaoTopico, IValidacaoUpdateTopico {

	@Autowired
	TopicoRepository topicoRepository;

	@Override
	public void valida(CadastroTopicoDTO topicoDTO) {
		System.out.println("valida: ValidaTopicoMensagemRepetida");
		List<Topico> topicoBuscado = topicoRepository
				.findByTituloAndMensagemAndDeletedIsFalse(topicoDTO.titulo(), topicoDTO.mensagem());

		if (!topicoBuscado.isEmpty()) {
			throw new DuplicateTituloAndMessagemException(
					"Não é possível cadastrar um tópico com a mesma mensagem e título de outro já existente.");
		}
	}

	@Override
	public void validarUpdate(Topico topicoEntity, AtualizacaoTopicoDTO topico, Long topicoId, Curso curso) {
		System.out.println("validaUpdate: ValidaTopicoMensagemRepetida");
		List<Topico> topicoBuscado = topicoRepository
				.findByTituloAndMensagemAndDeletedIsFalse(topico.titulo(), topico.mensagem());
		topicoBuscado.forEach(t -> System.out.println(t));
		if (!topicoBuscado.isEmpty()) {
			throw new DuplicateTituloAndMessagemException(
					"Não é possível cadastrar um tópico com a mesma mensagem e título de outro já existente.");
		}
	}

}
