create table item_pedido (
	id bigint not null auto_increment,
	produto_id bigint not null,
	pedido_id bigint not null,
	quantidade bigint not null,
	preco_unitario decimal(10,2) not null,
	preco_total decimal(10,2) not null,
	observacao varchar(20) not null,
  primary key (id),
  constraint fk_item_pedido_produto foreign key (produto_id) references produto(id),
  constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido(id)
) engine=InnoDB default charset=utf8;
