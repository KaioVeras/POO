# Banco da versão 2

H2 é um banco relacional escrito em Java. Na apresentação, ele funciona dentro do programa e grava `dados/moradia.mv.db`. Não precisa instalar um servidor. Fechar o programa não apaga os cadastros.

JPA define como persistir objetos; Hibernate implementa JPA e transforma as operações em SQL; o driver JDBC conversa com H2 ou MySQL. Banco.java escolhe a URL, cria uma EntityManagerFactory e abre um EntityManager por operação. O serviço usa transações para confirmar ou desfazer um cadastro inteiro.

## Tabelas

| Tabela | Campos principais e vínculos |
|---|---|
| condominios | id (único condomínio), nome |
| apartamentos | id gerado, bloco, numero, vaga opcional |
| moradores | id gerado, nome, cpf único, telefone, apartamento_id |
| funcionarios | id gerado, nome, cargo |
| encomendas | id gerado, morador_id, descricao, recebida_em, retirada_em opcional |

Apartamento tem vários moradores. Morador tem várias encomendas. Condomínio e funcionários são registros do único condomínio da aplicação. Não há tabela de cargos nem tabela de vagas, porque nesta fase bastam essas informações nos próprios registros.

O SQL MySQL está em sql/mysql.sql e usa a base nova gestao_moradia. O Hibernate cria as tabelas H2 pelas anotações. Não use o SQL sobre a base antiga: esta é uma mudança de modelo, não uma migração.

## Consultar H2 no navegador

Feche o programa e execute na pasta do projeto:

```sh
java -cp target/gestao-condominio.jar org.h2.tools.Server -web -webPort 8082 -properties null -ifExists
```

Abra http://localhost:8082. Driver org.h2.Driver; URL `jdbc:h2:./dados/moradia;IFEXISTS=TRUE`; usuário sa; senha vazia. No ZIP completo, remova target/ do caminho do JAR. Desconecte o console antes de abrir o programa novamente.

```sql
SELECT * FROM APARTAMENTOS;
SELECT * FROM MORADORES;
SELECT * FROM ENCOMENDAS WHERE RETIRADA_EM IS NULL;
```

O banco da v1 em dados/condominio.mv.db foi preservado; esta aplicação usa outro arquivo. Copie bancos somente com a aplicação fechada. Um backup-h2.sql gerado pelo empacotador pertence ao H2, não ao MySQL.
