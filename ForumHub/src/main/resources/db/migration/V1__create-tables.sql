create table cursos (
  id bigint not null auto_increment primary key,
  nome varchar(255) not null,
  categoria varchar(255) not null
);

create table usuarios (
  id bigint not null auto_increment primary key,
  nome varchar(255) not null,
  email varchar(255) not null,
  senha varchar(255) not null
);

create table respostas (
  id bigint not null auto_increment primary key,
  mensagem text not null,
  topico_id bigint not null,
  data_criacao timestamp not null,
  usuario_id bigint not null,
  solucao varchar(255) not null,
  FOREIGN key (usuario_id) REFERENCES usuarios(id)
);

create table topicos (
  id bigint not null auto_increment primary key,
  titulo varchar(255) not null,
  mensagem text not null,
  data_criacao timestamp not null,
  status varchar(255) not null,
  curso_id bigint,
  usuario_id bigint not null,

  FOREIGN KEY (curso_id) REFERENCES cursos(id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

alter table respostas add constraint fk_respostas_topico_id foreign key (topico_id) references topicos(id);
