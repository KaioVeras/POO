# Residencial Golden Java (versão 2)

Projeto de POO do Grupo 02: Kaio Veras e Emanuel Veloso.

Sistema de terminal para a gestão de um condomínio, centrado no **morador**. Cadastra e consulta apartamentos, moradores, funcionários e encomendas, guardando tudo em um banco de dados relacional.

## O que o sistema faz

| Setor | Funções |
| --- | --- |
| **Administração** | Consultar o condomínio; cadastrar e listar apartamentos (bloco, número e vaga de garagem opcional); cadastrar e listar funcionários (nome e cargo). |
| **Moradia** | Cadastrar e listar moradores; consultar a ficha do morador com nome, CPF, telefone, bloco, apartamento, garagem e encomendas. |
| **Portaria** | Receber encomenda para um morador; listar encomendas pendentes; registrar a retirada. |

Os dados continuam salvos depois de fechar e abrir o programa. O sistema recusa apartamento repetido no mesmo bloco, vaga de garagem repetida, CPF repetido e vínculos com registros que não existem.

Zelador é um **cargo** de funcionário, não uma classe separada. Taxas, multas e gestão financeira foram retiradas por orientação do professor, para manter o foco no domínio principal do condomínio.

## Executar

### Aplicativo pronto (sem instalar nada)

Baixe na [release 2.1.0](https://github.com/KaioVeras/POO/releases/tag/condominio-v2.1.0) o arquivo do seu sistema. O Java já vem incluído. Veja [qual arquivo baixar](docs/EXECUTAVEIS.md).

### Pelo código-fonte

É necessário ter o Java 17 ou superior. Na pasta deste README:

```sh
./mvnw package
sh iniciar.sh
```

No Windows: `mvnw.cmd package` e depois duplo clique em `iniciar.bat`.

O `./mvnw` baixa o Maven e as bibliotecas automaticamente na primeira vez. O comando `package` também executa os testes automatizados.

### Navegação

O menu principal tem `1` Administração, `2` Moradia, `3` Portaria e `0` Sair. A cada escolha a tela é limpa, mostrando apenas o resultado da operação e o menu seguinte.

Na primeira abertura, o sistema cria dados de demonstração: o condomínio, o apartamento A/101 com vaga G01, o morador Carlos (ID 1) e o funcionário Marcos, com cargo Zelador.

## Roteiro de apresentação (cinco minutos)

1. Administração > Listar apartamentos. Mostre o A/101 com a vaga G01.
2. Administração > Cadastrar apartamento: bloco B, número 102, vaga G02. Anote o ID exibido.
3. Moradia > Cadastrar morador: Ana, CPF fictício `22222222222`, um telefone e o ID do apartamento cadastrado.
4. Moradia > Consultar ficha, usando o ID da Ana.
5. Portaria > Receber encomenda: ID da Ana e a descrição "Caixa de livros".
6. Moradia > Consultar ficha novamente: agora há uma encomenda pendente.
7. Portaria > Registrar retirada pelo ID da encomenda e confirmar com `s`.
8. Saia com `0` e abra novamente. A moradora e o histórico continuam lá, e a pendência desapareceu.

Ao repetir o roteiro, use outro CPF e outro apartamento: duplicidades são recusadas.

## Organização do código

```text
src/main/java/br/edu/catolica/ucb/poo/
├── App.java                     Inicia o banco, os dados iniciais e o menu
├── view/
│   ├── MenuPrincipal.java       Menus dos três setores
│   └── Console.java             Leitura do teclado, escrita e limpeza da tela
├── service/
│   └── GestaoService.java       Operações do sistema e regras entre registros
├── repository/
│   └── CondominioRepository.java  Consultas e gravações com JPA
├── model/                       Entidades: Condominio, Apartamento, Morador,
│                                Funcionario, Encomenda e a classe Validacao
└── infra/
    ├── Banco.java               Conexão, consultas e transações
    └── DadosIniciais.java       Dados de demonstração
```

Caminho de uma operação: **Menu > Serviço > Repositório > Banco (JPA/Hibernate)**. As entidades validam seus próprios dados no construtor.

## Documentação

| Documento | Conteúdo |
| --- | --- |
| [Guia de estudo](docs/guia-de-estudo.md) | Como o código funciona, conceitos de POO e perguntas para a apresentação. |
| [Banco de dados](docs/banco-de-dados.md) | H2, JPA, tabelas e uso opcional do MySQL. |
| [Estado do projeto](docs/estado-do-projeto.md) | O que está pronto, decisões, limites e verificações. |
| [Executáveis](docs/EXECUTAVEIS.md) | Pacotes para Windows e macOS. |
| [Entrega de 02/11/2026](docs/entregas/2026-11-02/README.md) | Documento entregue, orientações do professor e diagramas. |
| [Atividade de Collections](atividades/collections/README.md) | Programa separado com ArrayList, HashSet e HashMap (item 05). |

A pasta `docs/referencias` guarda a lista de exercícios, a proposta inicial do grupo e o exemplo Java/JPA do professor.

## Banco e testes

O banco padrão é o **H2**, um banco relacional embutido no programa, gravado em `dados/moradia.mv.db`. Não é preciso instalar servidor. O MySQL é opcional: veja [banco-de-dados.md](docs/banco-de-dados.md).

Para rodar somente os testes:

```sh
./mvnw test
```

Os testes conferem cadastro, duplicidades, vínculos, ficha pelo menu, retirada de encomenda, cancelamento de cadastro incompleto e a permanência dos dados após reabrir o banco. Eles usam bancos descartáveis e não alteram os seus dados.

## Gerar a entrega

```sh
python3 scripts/empacotar.py
```

Gera `entrega/grupo_2_projeto.zip` com JAR, código, documentação, banco de demonstração e SQL. Para incluir o seu banco H2 atual, feche o programa e use `--incluir-banco`. Os aplicativos para Windows e macOS são gerados por `scripts/empacotar_nativo.py`, conforme [EXECUTAVEIS.md](docs/EXECUTAVEIS.md).

A [release v1](https://github.com/KaioVeras/POO/releases/tag/condominio-v1.0.0) é histórica: contém o sistema financeiro anterior, não esta versão.
