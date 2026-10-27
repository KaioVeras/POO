# Residencial Golden Java

Projeto de Programação Orientada a Objetos - Grupo 02: Kaio Veras e Emanuel Veloso.

Sistema de gestão de condomínio pelo terminal. Permite consultar condomínio, unidades, moradores e funcionários; calcular taxas e multas; registrar pagamentos e despesas; simular arrecadação e consultar o histórico financeiro. Os registros ficam em banco relacional.

**v1.0.0 - fase inicial:** núcleo e persistência prontos; cadastros/edições ainda não fazem parte do menu.
#### Documentações importantes
- [O que está pronto e o que falta](docs/estado-do-projeto.md)
- [Guia de estudo](docs/guia-de-estudo.md)
- [Conexão banco e H2](docs/banco-de-dados.md).

## Baixar a versão pronta

Na [página de Releases](https://github.com/KaioVeras/POO/releases), a versão `condominio-v1.0.0` disponibiliza o ZIP completo e pacotes de execução Windows e Mac com Java incluído. Para abrir por duplo clique, escolha o pacote do seu sistema e siga [estas instruções](docs/EXECUTAVEIS.md). Extraia a pasta inteira antes de executar.

Os pacotes nativos mantêm a interface de terminal: o executável apenas facilita a abertura. No Windows é `GoldenJava.exe`; no Mac é `GoldenJava.app`.

## Executar a entrega pronta

1. Instale Java **17 ou superior** e confira `java -version`.
2. Extraia **todo** o arquivo `grupo_2_projeto.zip`.
3. Abra o terminal na pasta `gestao-condominio` extraída.
4. Execute `sh iniciar.sh` no macOS/Linux ou `iniciar.bat` no Windows.

O pacote contém `gestao-condominio.jar` com suas dependências e `dados/condominio.mv.db`, o banco H2 de demonstração. Não precisa de internet, Maven, IDE ou servidor MySQL para essa execução. Se necessário, o banco também é criado na primeira abertura. Use os scripts para manter os dados na pasta correta.

**O ZIP padrão tem dados fictícios e caixa zerado.** Os pagamentos feitos depois da extração são gravados nessa cópia do banco. Não abra duas instâncias usando o mesmo arquivo. Para encerrar, use a opção `0`.

## Executar a partir do código

Requer JDK 17+ e internet na primeira compilação. O Maven Wrapper baixa o Maven automaticamente. Se `JAVA_HOME` estiver definido, ele precisa apontar para um JDK 17+.

macOS/Linux:

```sh
./mvnw test
./mvnw package
sh iniciar.sh
```

Windows:

```bat
mvnw.cmd test
mvnw.cmd package
iniciar.bat
```

Na IDE, abra o `pom.xml` como projeto Maven. A classe principal é `br.edu.catolica.ucb.poo.App`. Configure o diretório de trabalho como a pasta que contém o `pom.xml` para manter o banco em `dados/`.

## Demonstração rápida

Os dados iniciais são criados uma única vez:

| Objeto | Exemplo |
|---|---|
| Condomínio | Residencial Golden Java; 2 blocos; taxa padrão R$ 500,00; caixa R$ 0,00 |
| Unidade 101 | Comum, ocupada, taxa R$ 500,00; morador Carlos |
| Unidade 102 | Comum, desocupada, taxa R$ 500,00; sem morador |
| Unidade 201 | Cobertura, ocupada, taxa R$ 625,00; moradora Ana |
| Funcionário | Paulo, salário R$ 2.500,00 |
| Zelador | Marcos, salário calculado R$ 3.300,00 |

Roteiro: opção 5 → unidade 201 → multa 10 → R$ 687,50. Opção 6 permite registrar esse pagamento após confirmação. Uma despesa de R$ 87,50 deixa o caixa em R$ 600,00. Feche e reabra: o saldo e o histórico continuam lá.

Digite valores como `500`, `500.00` ou `500,00`, sem separador de milhar. Multa `10` significa 10%; aceita de 0% a 100%. Simular arrecadação considera as três unidades, inclusive a desocupada, totalizando R$ 1.625,00, sem alterar o caixa.

## Usar MySQL e Workbench

MySQL é a opção para trabalhar com um servidor e consultar os dados no Workbench. H2 permite distribuir uma demonstração com banco em arquivo. O Workbench é um cliente: ele precisa de um servidor MySQL em execução.

1. Em um servidor MySQL 8.4 ou compatível, abra `sql/mysql.sql` no Workbench e execute em uma base **nova**. O script cria esquema, tabelas e os mesmos dados fictícios. Se o banco já estiver em uso, não execute os INSERTs novamente.
2. Crie um usuário local ou use um usuário autorizado existente. Troque a senha no exemplo abaixo antes de executá-lo:

```sql
CREATE USER 'condominio'@'localhost' IDENTIFIED BY 'troque-esta-senha';
GRANT ALL PRIVILEGES ON gestao_condominio.* TO 'condominio'@'localhost';
```

3. Configure o ambiente. No macOS/Linux:

```sh
export BANCO=mysql
export DB_URL='jdbc:mysql://localhost:3306/gestao_condominio?serverTimezone=America/Sao_Paulo'
export DB_USER=condominio
read -s DB_PASSWORD
export DB_PASSWORD
sh iniciar.sh
```

O comando `read -s` permite digitar a senha sem exibi-la (Bash/Zsh). No PowerShell:

```powershell
$env:BANCO = 'mysql'
$env:DB_URL = 'jdbc:mysql://localhost:3306/gestao_condominio?serverTimezone=America/Sao_Paulo'
$env:DB_USER = 'condominio'
$credencial = Get-Credential -UserName 'condominio' -Message 'Senha do MySQL'
$env:DB_PASSWORD = $credencial.GetNetworkCredential().Password
.\iniciar.bat
```

Sem configuração, o aplicativo usa H2. Para voltar ao H2, use `BANCO=h2` e remova `DB_URL` (no Bash: `unset DB_URL`; no PowerShell: `Remove-Item Env:DB_URL -ErrorAction SilentlyContinue`). Os dois bancos são independentes: mudar `BANCO` não transfere registros entre eles.

Se preferir deixar o Hibernate criar as tabelas, crie apenas a base e o usuário e inicie a aplicação. A opção `hibernate.hbm2ddl.auto=update` é uma simplificação didática para este projeto; não substitui migrações controladas em um sistema em produção.

Consultas no Workbench:

```sql
USE gestao_condominio;
SELECT * FROM unidades;
SELECT * FROM moradores;
SELECT * FROM funcionarios;
SELECT saldo_caixa FROM condominios WHERE id = 1;
SELECT * FROM movimentacoes ORDER BY id;
```

### Compartilhar os registros do MySQL

O servidor MySQL não é copiado para dentro do ZIP. Para levar os registros reais, faça um **dump SQL** no Workbench (`Server > Data Export`, esquema `gestao_condominio`, arquivo independente) e entregue esse arquivo junto. Na outra máquina, restaure em um servidor MySQL e configure as credenciais locais. As senhas do servidor não vão no projeto.

`sql/mysql.sql` contém a demonstração inicial, não os pagamentos que você tenha feito no seu servidor. `sql/backup-h2.sql`, gerado no ZIP, pertence ao H2 e não deve ser executado no MySQL.

## Gerar o ZIP de entrega

Requer Python 3 apenas na máquina de quem gera o pacote. Quem recebe não precisa dele.

```sh
./mvnw package
python3 scripts/empacotar.py
```

No Windows, use `mvnw.cmd package` e `py scripts/empacotar.py`.

Resultado: `entrega/grupo_2_projeto.zip`, com código, testes, documentação, anexos, JAR, banco H2 pronto e cópia SQL do H2. O pacote não inclui caches, arquivos da IDE ou credenciais. O banco demonstrativo é preparado em outra pasta e não modifica os dados do seu projeto.

Para incluir os pagamentos já registrados no **H2 local**, feche a aplicação e use:

```sh
python3 scripts/empacotar.py --incluir-banco
```

Esse comando exporta o banco de `dados/condominio` e recria uma cópia consistente no pacote. A entrega substitui o ZIP anterior; confirme se deseja compartilhar os registros antes de enviar. Para dados do MySQL, use a exportação descrita acima.

## Organização e aprendizado

```text
src/main/java/br/edu/catolica/ucb/poo/
  App.java                       inicia recursos e monta dependências
  model/                         estado, validações, taxas, salários e caixa
  view/                          Scanner, mensagens e menu
  service/GestaoService.java      coordena operações e suas transações
  repository/                    consultas e persistência JPA
  infra/                         conexão e dados iniciais
src/test/java/                   testes de domínio, terminal e persistência
sql/                             criação MySQL e, no ZIP, backup H2
docs/                            explicações e materiais de referência
```

Leia `docs/guia-de-estudo.md` para entender o caminho de uma operação e preparar a apresentação. `docs/banco-de-dados.md` explica H2, MySQL, JDBC, JPA, conexão e tabelas. `docs/estado-do-projeto.md` registra decisões e pendências. O exemplo de loja do professor está preservado em `docs/referencias/loja-jpa-cl.zip`.

## Limites desta versão

- Um condomínio, com unidades e pessoas fictícias pré-cadastradas. Cadastro/edição de pessoas não faz parte desta versão.
- Não há login, controle de visitantes ou manutenção.
- Pagamentos são recebimentos avulsos; não há cobrança por competência nem detecção de pagamento repetido. Cada confirmação cria um novo recebimento.
- O domínio limita valores a R$ 9.999.999.999,99, taxa não negativa e movimentações positivas. Valores calculados usam duas casas e HALF_UP. Salário-base mantém `double` como no material didático; cálculos usam conversão decimal.
- Valores e nomes são validados pelo Java; alterações diretas via SQL podem violar as regras. Para operar, use o sistema.
- As datas do histórico representam a hora local do computador que executa a aplicação, sem fuso armazenado.
