CREATE DATABASE IF NOT EXISTS gestao_condominio CHARACTER SET utf8mb4;
USE gestao_condominio;

CREATE TABLE condominios (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    blocos INT NOT NULL,
    taxa_padrao DECIMAL(12,2) NOT NULL,
    portaria_24h BOOLEAN NOT NULL,
    saldo_caixa DECIMAL(12,2) NOT NULL
);
CREATE TABLE unidades (
    id BIGINT PRIMARY KEY,
    numero INT,
    tipo VARCHAR(20) NOT NULL,
    taxa_base DECIMAL(12,2) NOT NULL,
    ocupada BOOLEAN NOT NULL
);
CREATE TABLE moradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(11) UNIQUE,
    nome VARCHAR(100) NOT NULL,
    unidade_numero INT NOT NULL,
    FOREIGN KEY (unidade_numero) REFERENCES unidades(numero)
);
CREATE TABLE funcionarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    salario_base DOUBLE NOT NULL
);
CREATE TABLE movimentacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    registrada_em DATETIME(6) NOT NULL,
    unidade_numero INT,
    FOREIGN KEY (unidade_numero) REFERENCES unidades(numero)
);

INSERT INTO condominios VALUES (1, 'Residencial Golden Java', 2, 500.00, TRUE, 0.00);
INSERT INTO unidades (numero, tipo, taxa_base, ocupada) VALUES
    (101, 'COMUM', 500.00, TRUE),
    (102, 'COMUM', 500.00, FALSE),
    (201, 'COBERTURA', 500.00, TRUE);
INSERT INTO moradores VALUES ('11111111111', 'Carlos', 101), ('22222222222', 'Ana', 201);
INSERT INTO funcionarios (tipo, nome, salario_base) VALUES
    ('COMUM', 'Paulo', 2500.00), ('ZELADOR', 'Marcos', 3000.00);
