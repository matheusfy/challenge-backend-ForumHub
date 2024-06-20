package io.github.matheusfy.ForumHub.models.Curso;

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
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String nome;
	CursoCategorias categoria;
}
