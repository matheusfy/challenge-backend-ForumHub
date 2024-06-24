package io.github.matheusfy.ForumHub.models.Topico.validation.cadastro;

import org.springframework.stereotype.Component;

import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;

@Component
public interface IValidacaoTopico {

	void valida(CadastroTopicoDTO topicoDTO);
}
