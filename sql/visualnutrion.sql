create database visualnutrion;

use visualnutrion;

create table if not exists clientes(
	cliente_cod int not null auto_increment,
	cliente_nome varchar(255) not null,
    dt_nasc date,
	primary key(cliente_cod)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

create table if not exists telefone(
	fk_cliente_cod int not null,
	ddd varchar(3),
	antesh varchar(5),
	depoish varchar(4)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table if not exists produtos(
	produto_foto longblob not null,
	produto_cod int not null auto_increment,
	produto_nome varchar(255) not null,
	fk_cod_fornecedor int not null,
	preco_uni_compra decimal(8,2) not null,
        preco_uni_venda decimal(8,2) not null,
        categoria enum('Proteina','Multivitaminico','Acessorio','Pro-Hormonal',
	'Hipercalorico','Termogenico','Anti-oxidante','Repositor energetico',
	'Repositor hidroeletrolitico','Aminoacidos','Outro') not null,
	descricao_produto varchar(255),
        unidade_medida_peso varchar(255),
	peso_produto decimal(8,2),
	fk_estoque_cod int not null,
	primary key(produto_cod)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

create table if not exists fornecedor(
	fornecedor_cod int not null auto_increment,
	fornecedor_nome varchar(255) not null,
	primary key(fornecedor_cod)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

create table if not exists estoque(
	estoque_cod int not null auto_increment,
	qnt_estoque int not null,
	validade date,
	primary key(estoque_cod)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

create table if not exists endereco(
	fk_cliente_cod int not null,
	tipolog varchar(255) not null,
	logradouro varchar(255) not null,
	bairro varchar(255) not null,
	complemento varchar(255),
	cidade varchar(255) not null,
	estado varchar(255) not null,
    cep varchar(15) not null
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table if not exists pedido(
	cod_pedido int not null auto_increment,
	dt_pedido date not null,
	pedido_vl_tot decimal(8,2) not null,
	pagamento enum('Cartao de Debito','Cartao de Credito','Boleto','A Vista') not null,
	primary key(cod_pedido),
        desconto int not null
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

create table if not exists pedido_item(
	fk_cod_cliente int not null,
	fk_cod_pedido int not null,
	fk_cod_produto int not null,
	quantidade int not null,
	pedido_item_vl_tot decimal(8,2) not null,
        pedido_item_vl_liq decimal(8,2) not null
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table pedido_item add constraint fk_cod_cli2
foreign key(fk_cod_cliente) references clientes(cliente_cod);

alter table pedido_item add constraint fk_cod_ped 
foreign key(fk_cod_pedido) references pedido(cod_pedido);

alter table pedido_item add constraint fk_cod_pro
foreign key(fk_cod_produto) references produtos(produto_cod);

alter table telefone add constraint fk_cod_cli
foreign key(fk_cliente_cod) references clientes(cliente_cod);

alter table produtos add constraint fk_cod_for
foreign key(fk_cod_fornecedor) references fornecedor(fornecedor_cod);

alter table produtos add constraint fk_cod_est
foreign key(fk_estoque_cod) references estoque(estoque_cod);

alter table endereco add constraint fk_cod_cliente
foreign key(fk_cliente_cod) references clientes(cliente_cod);

