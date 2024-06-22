create table cursos (
  id bigint not null auto_increment primary key,
  nome varchar(255) not null,
  categoria varchar(255) not null
);

alter table topicos add column curso_id bigint,
add constraint topicos_ibfk_1 foreign key (curso_id) references cursos(id);