## Criando o banco de dados
drop database dbLoja;
create database if not exists dbLoja;
## escolhendo o banco de dados que desejo utilizar 
use dbLoja;

## Criando as tabelas 
# tabela usuario 
create table tbUsuarios(
idUser int primary key, 
usuario varchar(50) not null, 
login varchar(15) not null unique,
senha varchar(15) not null
);

create table tbCliente(
idCliente int primary key auto_increment, 
nome varchar(50) not null, 
data_nasc varchar(10) not null,
endereco varchar(100),
telefone varchar(15) not null,
email varchar(50)
);

create table tbProduto(
idProduto int primary key auto_increment, 
nome varchar(50) not null, 
preco double not null,
quantidade int not null,
tipo varchar(100)
);

create table tbVendedor(
idVendedor int primary key auto_increment,
nome varchar(50) not null
);

create  table tbCompra(
idCompra int primary key auto_increment,
idCliente_tbCliente int not null,
foreign key(idCliente_tbCliente) references tbCliente(idCliente) ON DELETE CASCADE ON UPDATE CASCADE
);

create table tbProdutoComprado(
idProdutoComprado int primary key auto_increment,
quantidade int not null,
idProduto_tbProduto int not null,
idCompra_tbCompra int not null, 
foreign key(idCompra_tbCompra) references tbCompra(idCompra) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(idProduto_tbProduto) references tbProduto(idProduto) ON DELETE CASCADE ON UPDATE CASCADE
);

create table tbNotaFiscal(
idNota int primary key auto_increment,
data_nt timestamp default current_timestamp,
idVendedor_tbVendedor int not null, 
idCompra_tbCompra int not null,
foreign key(idVendedor_tbVendedor) references tbVendedor(idVendedor) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(idCompra_tbCompra) references tbCompra(idCompra) ON DELETE CASCADE ON UPDATE CASCADE
);


## inserindo usuarios 
insert into tbUsuarios(idUser,usuario,login,senha) values (1,'Karolina Oliveira', 'karol_o', '1234567');
insert into tbUsuarios(idUser,usuario,login,senha) values (2,'Bruno Quirino', 'kirino', 'genshin');
insert into tbUsuarios(idUser,usuario,login,senha) values (3,'Administrador', 'admin', 'admin');

#Monstrando usuarios
select * from tbUsuarios;

#Modificando infos
update tbUsuarios set senha = '102471' where idUser = 1;

# Removendo usuario 
delete from tbUsuarios where idUser = 3; 

# inserindo cliente
insert into tbCliente(nome, data_nasc, telefone, email) values ('Yago Oliveira', '05/10/1995', '(35)99987-5876', 'yago.oliveira@gmail.com');  

#Mostrando cliente 
select * from tbCliente;

# inserindo vendedor
insert into tbVendedor(nome) values ('Julia');  

#inserindo Produto
insert into tbProduto(nome, preco, quantidade, tipo) values ('Camisa Azul', 34.5, 100, 'camisa');
insert into tbProduto(nome, preco, quantidade, tipo) values ('Camisa Verde', 34.5, 100, 'camisa');

#inserindo um compra
insert into tbCompra(idCliente_tbCliente) values (1);

#inserindo Produtos na compra 1
insert into tbProdutoComprado(quantidade, idProduto_tbProduto, idCompra_tbCompra) values (1, 1, 1);
insert into tbProdutoComprado(quantidade, idProduto_tbProduto, idCompra_tbCompra) values (1, 2, 1);

#atualizando produto
update tbProduto set quantidade = 99 where idProduto =  1; 
update tbProduto set quantidade = 99 where idProduto =  2; 

# gerando nota fiscal
insert into tbNotaFiscal(idVendedor_tbVendedor, idCompra_tbCompra) values (1,1); 

#buscando info
select nf.IdNota as 'Nota', nf.data_nt as 'Data', v.nome as 'Vendedor', c.nome as 'Cliente', c.endereco as 'Endereço', p.nome as 'Produto', pcom.quantidade as 'Quantidade', p.preco as 'Valor', com.idCompra as "ID_Compra"
from tbProdutoComprado as pcom inner join tbCompra as com on pcom.idCompra_tbCompra = com.idCompra 
inner join tbNotaFiscal as nf on nf.idCompra_tbCompra = com.idCompra
inner join tbVendedor as v on v.idVendedor = nf.idVendedor_tbVendedor
inner join tbCliente as c on c.idCliente = com.idCliente_tbCliente
inner join tbProduto as p on p.idProduto = pcom.idProduto_tbProduto 
and com.idCompra = 1;

# Adicionando campo na tabela usuario
alter table tbUsuarios add column perfil varchar(20) not null;
select * from tbUsuarios;
update tbUsuarios set perfil = "admin" where idUser = 1; 
update tbUsuarios set perfil = "user" where idUser = 2;

# Adicionando campo na tabela vendendor
alter table tbVendedor add column salario double not null;
alter table tbVendedor add column telefone varchar(15) not null;
alter table tbVendedor add column endereco varchar(50) not null;
alter table tbVendedor add column data_nasc varchar(10) not null;
alter table tbVendedor add column SSN double not null;

select sum(pcom.quantidade * p.preco) as 'Total'
from tbProdutoComprado as pcom inner join tbCompra as com on pcom.idCompra_tbCompra = com.idCompra 
inner join tbProduto as p on p.idProduto = pcom.idProduto_tbProduto 
and com.idCompra = 27;

