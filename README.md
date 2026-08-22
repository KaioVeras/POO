# POO

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Uso educacional](https://img.shields.io/badge/Uso-Educacional-success)
![Última atualização](https://img.shields.io/badge/%C3%9Altima%20atualiza%C3%A7%C3%A3o-21%2F08%2F2026-blue)

Conteúdo em Java organizado por aulas, com foco em programação orientada a objetos, interfaces gráficas com Swing e separação de responsabilidades entre controle e visão.

Repositório público da disciplina de Programação Orientada a Objetos, com exemplos práticos, exercícios e implementações pensados para consulta rápida, revisão antes das aulas e evolução contínua ao longo do semestre.

O que você encontra aqui: exemplos incrementais em Java, organizados do básico ao mais estruturado, com cada aula servindo como ponto de entrada para os projetos relacionados.

## Acesso Rápido

- [Visão geral](#poo)
- [Aulas](#aulas)
- [Trabalhos práticos](#trabalhos-práticos)
- [Como executar](#como-executar)
- [Atualização](#atualização)
- [Autor](#autor)

## Aulas

| Aula | Tema | Data |
| --- | --- | --- |
| Aula 01 | Revisão em C e introdução à POO: classe, objeto, atributos, métodos e encapsulamento (get/set). | 2026-08-07 |
| Aula 02 | Interfaces gráficas com Swing, tratamento de eventos e separação entre os pacotes `br.com.controle` e `br.com.visao`. | 2026-08-14 |
| Aula 03 | Sistema de vendas (em andamento). | 2026-08-21 |

## Trabalhos práticos

| Trabalho | Descrição | Conceitos Utilizados |
| --- | --- | --- |
| — | Os trabalhos serão adicionados ao longo do semestre. | — |

## Organização dos Projetos

Cada aula pode conter mais de um projeto do Apache NetBeans. Dentro de cada projeto, o código segue a divisão:

```
src/
└── br/com/
    ├── controle/   Classes com atributos e regras da aplicação
    └── visao/      Telas em Swing e interação com o usuário
```

Os arquivos `.form` são do editor visual do NetBeans e não são necessários para compilar.

## Como Executar

### Opção 1 — Apache NetBeans (recomendado)

Cada pasta dentro de `Aulas/` é um projeto do NetBeans. Basta abrir o projeto desejado (`File > Open Project`) e executar com `F6`.

### Opção 2 — Terminal

Compile os pacotes e execute a tela principal do projeto:

```
cd Aulas/aula002/AulaPoo002/src
javac br/com/controle/*.java br/com/visao/*.java
java br.com.visao.TelaDobro
```

Se o exercício tiver mais de uma tela, execute a classe principal correspondente em `br/com/visao`.

Alguns exemplos iniciais usam C para revisão. Nesses casos, basta compilar o `main.c`:

```
cd Aulas/aula001
gcc main.c -o main
./main
```

Observação: as telas Swing precisam de ambiente gráfico para serem exibidas.

## Atualização

Última atualização: 21/08/2026

## Autor

Kaio Vítor Pinheiro Veras
