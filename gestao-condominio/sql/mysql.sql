-- Versão 2: base nova. Não executar sobre a base financeira da v1.
CREATE DATABASE IF NOT EXISTS gestao_moradia CHARACTER SET utf8mb4;
USE gestao_moradia;
CREATE TABLE condominios (id BIGINT PRIMARY KEY, nome VARCHAR(100) NOT NULL);
CREATE TABLE apartamentos (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, bloco VARCHAR(20) NOT NULL,
 numero VARCHAR(20) NOT NULL, vaga VARCHAR(20) UNIQUE, UNIQUE(bloco, numero)
);
CREATE TABLE moradores (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(100) NOT NULL,
 cpf VARCHAR(11) NOT NULL UNIQUE, telefone VARCHAR(30) NOT NULL,
 apartamento_id BIGINT NOT NULL, FOREIGN KEY(apartamento_id) REFERENCES apartamentos(id)
);
CREATE TABLE funcionarios (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(100) NOT NULL, cargo VARCHAR(50) NOT NULL
);
CREATE TABLE encomendas (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, morador_id BIGINT NOT NULL,
 descricao VARCHAR(200) NOT NULL, recebida_em DATETIME NOT NULL, retirada_em DATETIME,
 FOREIGN KEY(morador_id) REFERENCES moradores(id)
);
INSERT INTO condominios VALUES(1,'Residencial Golden Java');
INSERT INTO apartamentos(bloco,numero,vaga) VALUES('A','101','G01');
INSERT INTO moradores(nome,cpf,telefone,apartamento_id) VALUES('Carlos','11111111111','61999990000',1);
INSERT INTO funcionarios(nome,cargo) VALUES('Marcos','Zelador');
