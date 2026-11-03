# Guia de estudo

Este guia explica como o Residencial Golden Java funciona e como apresentá-lo. Leia junto com o código: cada seção indica o arquivo correspondente.

## 1. O domínio

Um condomínio tem apartamentos. Cada apartamento tem bloco, número e, opcionalmente, uma vaga de garagem. Vários moradores podem morar no mesmo apartamento. Cada morador tem nome, CPF e telefone, e pode receber várias encomendas na portaria. O condomínio também tem funcionários, cada um com um cargo.

O sistema se divide em três setores, que são os três menus:

- **Administração:** apartamentos e funcionários.
- **Moradia:** moradores e a ficha de cada um.
- **Portaria:** recebimento e retirada de encomendas.

O morador é o elemento central: a ficha dele reúne apartamento, bloco, garagem e encomendas.

## 2. As camadas

```text
App > MenuPrincipal > GestaoService > CondominioRepository > Banco (JPA/Hibernate) > H2
                              │
                           model/
```

| Camada | Arquivo | Responsabilidade |
| --- | --- | --- |
| Início | `App.java` | Abre o banco, cria os dados iniciais e mostra o menu. |
| Visão | `view/MenuPrincipal.java` | Mostra os menus, lê as escolhas e exibe os resultados. |
| Visão | `view/Console.java` | Lê do teclado, escreve na tela e limpa o terminal. |
| Serviço | `service/GestaoService.java` | Executa cada operação e aplica regras que envolvem outros registros, como CPF repetido. |
| Repositório | `repository/CondominioRepository.java` | Faz as consultas e gravações com JPA. |
| Infraestrutura | `infra/Banco.java` | Abre conexões e controla transações (commit e rollback). |
| Infraestrutura | `infra/DadosIniciais.java` | Cria os dados de demonstração na primeira execução. |
| Modelo | `model/*.java` | As entidades do domínio e suas validações. |

Cada camada conversa apenas com a seguinte. O menu não acessa o banco diretamente, e as entidades não sabem nada sobre telas.

## 3. Caminho de um cadastro de morador

1. `MenuPrincipal` lê nome, CPF, telefone e o ID do apartamento.
2. `GestaoService.cadastrarMorador` pede ao `Banco` uma transação.
3. O repositório busca o apartamento pelo ID. Se não existir, a operação para com uma mensagem.
4. O construtor de `Morador` valida os campos (nome obrigatório, CPF com 11 dígitos).
5. O serviço confere se o CPF já está cadastrado.
6. O repositório chama `persist` e o Hibernate grava o registro, gerando o ID.
7. O `Banco` faz o commit e o menu mostra o morador cadastrado.

Se qualquer passo lançar um erro, o `Banco` faz rollback: nada fica gravado pela metade. O menu captura o erro e mostra a mensagem com "Atenção:".

## 4. Caminho de uma encomenda

A portaria informa o ID do morador e a descrição. A `Encomenda` guarda automaticamente a data de recebimento e fica **pendente**, porque `retiradaEm` está vazio (nulo).

Na retirada, o método `retirar()` preenche `retiradaEm`. O registro não é apagado: a ficha do morador mostra o histórico completo e conta somente as pendentes. Tentar retirar a mesma encomenda duas vezes gera a mensagem "Encomenda já retirada.".

## 5. Conceitos de POO no código

| Conceito | Onde aparece |
| --- | --- |
| **Classe e objeto** | Cada entidade (`Morador`, `Apartamento`...) é uma classe; cada cadastro cria um objeto. |
| **Abstração** | As classes guardam só o necessário para o sistema: o morador tem nome, CPF, telefone e apartamento, nada além disso. |
| **Encapsulamento** | Os atributos são `private`. Não há métodos `set`: os dados entram pelo construtor, já validados. A data de retirada só muda pelo método `retirar()`. |
| **Construtores** | Validam os dados antes de criar o objeto. Um morador sem apartamento nunca chega a existir. |
| **Associação** | `Morador` tem um `Apartamento`; `Encomenda` tem um `Morador`. |
| **Sobrescrita (`@Override`)** | `toString()` define como cada registro aparece na tela. |
| **Tratamento de exceções** | Regras violadas lançam `IllegalArgumentException`, que o menu mostra como aviso. |

**Por que não há herança?** Herança faz sentido quando uma classe é um tipo especial de outra, com comportamento diferente. Aqui, um zelador não faz nada diferente de outro funcionário no sistema, então "Zelador" é apenas o valor do atributo `cargo`. Criar uma subclasse só para demonstrar herança deixaria o código mais complicado sem motivo. O exemplo de herança da disciplina está em `Aulas/heranca`.

## 6. JPA em poucas palavras

JPA é a especificação Java para gravar objetos em bancos relacionais. O Hibernate é a implementação usada. As anotações nas entidades dizem como cada classe vira tabela:

| Anotação | Significado |
| --- | --- |
| `@Entity` | A classe é gravada no banco. |
| `@Table(name = "moradores")` | Nome da tabela. |
| `@Id` e `@GeneratedValue` | Chave primária, gerada automaticamente pelo banco. |
| `@Column(nullable = false, unique = true)` | Coluna obrigatória e sem repetição. |
| `@ManyToOne` e `@JoinColumn` | Chave estrangeira: muitos moradores para um apartamento. |

Cada entidade tem um construtor vazio `protected`, exigido pelo JPA para recriar os objetos ao ler do banco.

O `Banco` tem dois métodos principais:

- `consultar(...)`: abre um `EntityManager`, executa a leitura e fecha.
- `transacionar(...)`: abre um `EntityManager`, inicia uma transação, executa a operação e faz commit. Se ocorrer erro, faz rollback.

Os dois recebem a operação como uma **expressão lambda** (`em -> ...`): um pedaço de código passado como parâmetro, executado com o `EntityManager` já aberto.

Mais detalhes em [banco-de-dados.md](banco-de-dados.md).

## 7. Tela do terminal

A cada escolha no menu, `Console.limparTela()` apaga o terminal. Fica visível apenas o resultado da operação, com um título como `--- MORADORES ENCONTRADOS ---`, e logo abaixo o menu para a próxima escolha. No Windows a limpeza usa o comando `cls`; no macOS e no Linux, um código especial (ANSI) que o terminal entende. O `App` só liga a limpeza quando existe um terminal de verdade (`System.console()`); nos testes ela fica desligada para que a saída possa ser conferida como texto.

## 8. Testes

O arquivo `src/test/java/.../FluxoMoradiaTest.java` tem cinco testes com JUnit. Cada um cria um banco H2 descartável:

1. Cadastro, encomenda, retirada e permanência dos dados ao reabrir o banco.
2. Recusa de duplicidades e de IDs inexistentes, sem gravar nada pela metade.
3. Mesmo número de apartamento em blocos diferentes, apartamento sem vaga e cargo de funcionário.
4. Cadastro e ficha usando o menu de verdade, com entradas simuladas.
5. Cancelamento do cadastro quando a entrada termina no meio.

Execute com `./mvnw test`.

## 9. Perguntas prováveis na apresentação

**Por que o CPF é guardado como texto e não como número?** Porque pode começar com zero e não é usado em cálculos.

**O sistema valida se o CPF é verdadeiro?** Não. Confere apenas se tem 11 dígitos e se não está repetido. A apresentação usa CPFs fictícios.

**Por que a vaga de garagem não é uma classe?** Nesta versão, cada apartamento tem no máximo uma vaga e ela não tem comportamento próprio. Um campo basta. Se surgirem várias vagas ou veículos, ela pode virar uma entidade.

**O que acontece se der erro no meio de um cadastro?** O `Banco` faz rollback da transação e nada é gravado.

**Onde ficam os dados?** No arquivo `dados/moradia.mv.db`, criado pelo H2. Eles continuam lá após fechar o programa.

**Por que existe um `CondominioRepository` e um `GestaoService`?** O repositório só sabe consultar e gravar. O serviço decide o que fazer: por exemplo, verificar se o CPF já existe antes de gravar. Separar facilita entender e testar cada parte.

## 10. Arquivos para abrir na apresentação

1. `model/Morador.java` e `model/Apartamento.java`: entidades, validação e associação.
2. `model/Encomenda.java`: o método `retirar()`.
3. `service/GestaoService.java`: o método `cadastrarMorador`.
4. `repository/CondominioRepository.java`: as consultas.
5. `infra/Banco.java`: o método `transacionar`.

Depois, execute o roteiro do [README](../README.md).
