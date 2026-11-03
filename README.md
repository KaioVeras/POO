# POO

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Uso educacional](https://img.shields.io/badge/Uso-Educacional-success)
![Última atualização](https://img.shields.io/badge/%C3%9Altima%20atualiza%C3%A7%C3%A3o-03%2F11%2F2026-blue)

Repositório da disciplina de Programação Orientada a Objetos, com exemplos das aulas e o trabalho prático do Grupo 02.

## Conteúdo

| Pasta | O que contém |
| --- | --- |
| [gestao-condominio](gestao-condominio/README.md) | Trabalho prático: **Residencial Golden Java**, sistema de condomínio em Java com JPA e banco de dados. |
| [Aulas/helloWorld](Aulas/helloWorld) | Primeiro programa em Java. |
| [Aulas/heranca](Aulas/heranca) | Exemplo de herança: `CarroEletrico` estende `Carro`. |

## Trabalho prático: Residencial Golden Java

Sistema de terminal centrado no morador, organizado em três setores: Administração, Moradia e Portaria.

- [Visão geral e como executar](gestao-condominio/README.md)
- [Guia de estudo do código](gestao-condominio/docs/guia-de-estudo.md)
- [Baixar a versão 2.1.0 para Windows e macOS](https://github.com/KaioVeras/POO/releases/tag/condominio-v2.1.0)

**Para consulta do professor (Grupo 02):** [documentação entregue, orientações e diagramas](gestao-condominio/docs/entregas/2026-11-02/README.md) · [programa de Collections (item 05)](gestao-condominio/atividades/collections/App.java) · [como executar Collections](gestao-condominio/atividades/collections/README.md).

## Executar os exemplos das aulas

Com Java 17 ou superior instalado, entre na pasta de código do exemplo, compile e execute:

```sh
cd Aulas/heranca/src/main/java
javac br/edu/catolica/ucb/poo/*.java
java br.edu.catolica.ucb.poo.App
```

Para o primeiro exemplo, use a pasta `Aulas/helloWorld/src/main/java` e a classe `br.edu.catolica.ucb.poo.helloWorld`. Os projetos também podem ser abertos diretamente no IntelliJ IDEA pelo `pom.xml`.

Os arquivos `.class` gerados pela compilação são ignorados pelo Git.

## Autor

Kaio Vítor Pinheiro Veras
