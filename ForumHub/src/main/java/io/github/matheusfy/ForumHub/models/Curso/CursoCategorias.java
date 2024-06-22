package io.github.matheusfy.ForumHub.models.Curso;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.InvalidCursoException;

public enum CursoCategorias {
	PROGRAMACAO("Programação"), ENGENHARIA("Engenharia");

	private String categoria;

	CursoCategorias(String categoria) {
		this.categoria = categoria;
	}

	public static CursoCategorias fromString(String categoria) {

		for (CursoCategorias curso : CursoCategorias.values()) {
			if (curso.categoria.equalsIgnoreCase(categoria)) {
				return curso;
			}
		}
		throw new InvalidCursoException("Categoria do curso informado é inválida.");
	}

}
