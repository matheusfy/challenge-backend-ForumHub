alter table topicos add column deleted boolean default false not null;
alter table usuarios add column ativo boolean default true not null;