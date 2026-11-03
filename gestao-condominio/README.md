# Residencial Golden Java — versão 2

Projeto de POO do Grupo 02: Kaio Veras e Emanuel Veloso. Versão enxuta, centrada no morador, para apresentar em 03/11/2026.

## Baixar os aplicativos atualizados

A [release 2.0.0](https://github.com/KaioVeras/POO/releases/tag/condominio-v2.0.0) reúne o projeto completo e os aplicativos Windows x64, Mac Apple Silicon e Mac Intel, com Java incluído nos pacotes nativos. Veja [qual arquivo baixar](docs/EXECUTAVEIS.md). Extraia a pasta inteira antes de abrir o aplicativo.

## O que funciona

- **Administração:** consultar condomínio; cadastrar/listar apartamentos e funcionários.
- **Moradia:** cadastrar/listar moradores e consultar a ficha com CPF, telefone, bloco, apartamento, garagem e encomendas.
- **Portaria:** receber encomenda para um morador, listar pendências e confirmar retirada.

Taxas, multas, caixa, salários especiais e classes Cobertura/Zelador foram retirados do sistema. Zelador é um cargo de Funcionario. Manutenção é um setor futuro; não há menu vazio simulando funcionalidade.

## Executar

Com Java 17+ instalado, na pasta que contém este README:

```sh
./mvnw package
sh iniciar.sh
```

Windows: `mvnw.cmd package`, depois duplo clique em `iniciar.bat`. No ZIP completo já compilado, use diretamente o iniciador. O programa continua sendo uma TUI.

Banco padrão: H2 em `dados/moradia.mv.db`. O arquivo antigo `dados/condominio.mv.db` não é usado nem apagado. A primeira abertura cria condomínio, apartamento A/101, vaga G01, Carlos (ID 1) e funcionário Marcos, cargo Zelador. Os cadastros seguintes permanecem após fechar e reabrir.

## Roteiro de apresentação — cinco minutos

1. Administração → listar apartamentos. Mostre A/101 e a vaga G01.
2. Administração → cadastrar apartamento: bloco B, número 102, vaga G02. Anote o ID exibido.
3. Moradia → cadastrar morador: Ana, CPF fictício `22222222222`, telefone e ID do apartamento cadastrado.
4. Moradia → consultar ficha, usando o ID da Ana.
5. Portaria → receber encomenda: ID da Ana e descrição “Caixa de livros”.
6. Consulte novamente a ficha: agora há uma encomenda pendente.
7. Portaria → registrar retirada pelo ID da encomenda e confirmar com `s`.
8. Feche com `0` e reabra. A moradora e o histórico permanecem, e a pendência desaparece.

Ao repetir o roteiro, consulte os registros existentes ou use outro CPF/apartamento: duplicidades são recusadas.

## Entender o código

Leia [o guia de estudo](docs/guia-de-estudo.md), [o modelo e o banco](docs/banco-de-dados.md) e [o estado da versão](docs/estado-do-projeto.md).

```text
App → MenuPrincipal → GestaoService → CondominioRepository → Banco/JPA
                          ↓
                       model/
```

`model` representa o condomínio; `view` lê e apresenta; o serviço coordena cadastro e vínculos; o repositório consulta/grava; Banco abre/fecha conexões e controla transações. Todos os registros têm ID; CPF é uma informação única do morador. Apartamento é identificado pelo par bloco/número.

## Banco e testes

H2 é um banco relacional embutido, em arquivo; dispensa servidor para a apresentação. MySQL permanece opcional: crie uma **base nova** usando `sql/mysql.sql`, configure `BANCO=mysql`, `DB_USER`, `DB_PASSWORD` e, se necessário, `DB_URL`. A URL padrão aponta para `gestao_moradia`. Os dados financeiros antigos não são migrados.

`./mvnw test` verifica cadastro, duplicidade, vínculos, ficha no terminal, retirada e recuperação após reabrir. Os testes usam bancos descartáveis, sem alterar seus dados.

## Entrega

```sh
python3 scripts/empacotar.py
```

Gera `entrega/grupo_2_projeto.zip` com JAR, fontes, documentação, banco demonstrativo e SQL. Para incluir seu H2 atual, feche a aplicação e use `--incluir-banco`. Os pacotes Windows/Mac com Java incluído podem ser gerados no sistema correspondente por `scripts/empacotar_nativo.py`; consulte [EXECUTAVEIS.md](docs/EXECUTAVEIS.md).

A [release v1](https://github.com/KaioVeras/POO/releases/tag/condominio-v1.0.0) é histórica e contém o sistema financeiro anterior, não esta versão. Não a use para demonstrar o novo cadastro.

## Materiais entregues anteriormente

[PDF, orientações e diagramas](docs/entregas/2026-11-02/README.md) e [atividade isolada de Collections](atividades/collections/README.md) ficam preservados como evidência. As propostas antigas não representam as funcionalidades atuais. Os guias antigos estão em `docs/historico-v1/`.
