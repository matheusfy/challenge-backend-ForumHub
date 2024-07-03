package io.github.matheusfy.ForumHub.controller;

import io.github.matheusfy.ForumHub.infra.configuration.DateTimeFormatterConfiguration;
import io.github.matheusfy.ForumHub.models.Resposta.Resposta;

public record RespostaDetailDto(Long id, String mensagem, String solucao, String nomeUsuario, String tituloTopico,
    String dataCriacaoResposta) {

  public RespostaDetailDto(Resposta resp) {
    this(resp.getId(), resp.getMensagem(), resp.getSolucao(), resp.getUsuario().getNome(),
        resp.getTopico().getTitulo(), resp.getDataCriacao().format(DateTimeFormatterConfiguration.formatter));
  }
}
