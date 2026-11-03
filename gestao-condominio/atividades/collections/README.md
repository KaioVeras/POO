# Collections no condomínio (Item 05)

Programa independente do Grupo 02, de Kaio Veras e Emanuel Veloso, fornecido em **um único arquivo [App.java](App.java)**. Demonstra `ArrayList`, `HashSet` e `HashMap` usando exemplos do Residencial Golden Java.

O arquivo foi copiado do material enviado pelo grupo, sem alterar o código. Não faz parte do menu da aplicação principal e não usa Maven, JPA ou banco de dados. As coleções são criadas na memória a cada execução; os dados não são persistidos.

## Executar

Com Java 17 ou superior instalado, abra o terminal na pasta `gestao-condominio/atividades/collections` e execute:

```sh
java -Dfile.encoding=UTF-8 App.java
```

Esse comando compila e executa o arquivo isolado. Também é possível abrir apenas este arquivo na IDE e executar seu `main`.

Para separar compilação e execução:

```sh
javac -encoding UTF-8 App.java
java App
```

O arquivo `App.class` gerado é ignorado pelo Git. Para entregar somente o que o item 05 solicita, envie **App.java**.

## O que cada coleção demonstra

| Coleção | Exemplo no programa | Comportamento observado |
|---|---|---|
| `ArrayList<String>` | Carlos e Ana, com suas unidades | Mantém a ordem de inserção; o programa percorre os elementos e mostra total 2. |
| `HashSet<String>` | Visitantes do dia | A string `Marina` é adicionada duas vezes, mas ocupa um único elemento; total 2 visitantes distintos. |
| `HashMap<Integer, Double>` | Número da unidade associado à taxa | Permite buscar a taxa da unidade 201 e somar as taxas das três unidades. |

Resultados esperados: taxa da unidade 201 de **R$ 625,00** e arrecadação prevista de **R$ 1.625,00**. O separador decimal depende da configuração regional do Java.

A ordem de exibição de `HashSet` e `HashMap` não é garantida. Isso é diferente do `ArrayList`, que mantém Carlos antes de Ana neste exemplo. O `HashSet` compara as strings: esta demonstração não identifica pessoas reais nem trata homônimos.

## Diferença para o sistema completo

A aplicação principal está em [`src/main/java/br/edu/catolica/ucb/poo/App.java`](../../src/main/java/br/edu/catolica/ucb/poo/App.java), com entidades, menu, serviços e banco relacional. Aqui, os valores ficam nas coleções apenas para demonstrar seus comportamentos.

O uso de `Double` nas taxas foi mantido como no arquivo fornecido para a atividade. As taxas aparecem só neste exercício: o sistema principal não tem gestão financeira. Da mesma forma, o exemplo de visitantes não significa que o sistema principal controle visitantes; a portaria dele trata apenas de encomendas.

Consulte o [índice da entrega](../../docs/entregas/2026-11-02/README.md) para localizar o PDF, as orientações e os diagramas.
