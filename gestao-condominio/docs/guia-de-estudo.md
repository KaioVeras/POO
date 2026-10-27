# Guia de estudo e apresentação

## Comece por uma operação concreta

Ao registrar o pagamento da cobertura 201 com multa de 10%:

1. `MenuPrincipal` lê 201 e 10 pelo `Console` e apresenta R$ 687,50.
2. Só depois da resposta `s`, chama `GestaoService.registrarPagamento(201, 10)`.
3. O serviço abre uma transação e busca os objetos pelo repositório.
4. O banco reconstrói a unidade como `Cobertura`, por causa da coluna `tipo`.
5. `calcularTaxa()` chama o cálculo de Unidade e acrescenta 25%: 500 × 1,25 = 625.
6. A sobrecarga `calcularTaxa(10)` usa o método sem parâmetros: 625 × 1,10 = 687,50.
7. `Condominio.receberPagamento` valida e aumenta o caixa. O serviço cria o lançamento.
8. O commit grava ambos. Se houver erro antes do commit, o rollback desfaz toda a operação.

O método com multa não sabe se recebeu uma unidade comum ou uma cobertura: ele chama `calcularTaxa()` e o Java escolhe a implementação do objeto real. Esse é o exemplo central de polimorfismo do projeto.

## Quem faz o quê

| Parte | Responsabilidade | Pergunta para você responder |
|---|---|---|
| App | Abre banco, inicializa dados, monta serviço e menu, fecha banco | Por que o main não contém os cálculos? |
| Console e MenuPrincipal | Interação com o usuário | Por que não gravar antes da confirmação? |
| Unidade e Cobertura | Taxa normal, multa e adicional | Qual a diferença entre sobrecarga e sobrescrita? |
| Condominio | Manter um saldo válido | O que acontece se a despesa ultrapassar o saldo? |
| Funcionario e Zelador | Salário e adicional | Como o exemplo do zelador confirma que você entendeu a herança? |
| GestaoService | Coordenar os objetos dentro de uma transação | Por que saldo e lançamento precisam ser gravados juntos? |
| CondominioRepository | Consultas JPA e persistência | O que é responsabilidade do banco, e o que é regra do condomínio? |
| Banco | Conexão e controle de transações | Por que fechamos EntityManager após cada operação? |

## POO no código

- **Abstração:** Unidade representa os dados úteis para esta aplicação: número, taxa-base e ocupação.
- **Encapsulamento:** campos privados e métodos que impedem saldo inválido; não existe setter público para saldo.
- **Herança:** Cobertura é uma Unidade; Zelador é um Funcionario. Construtores usam `super`.
- **Polimorfismo:** uma variável Unidade pode guardar uma Cobertura; `calcularTaxa()` executa a regra especial.
- **Sobrecarga:** dois construtores de Unidade e duas assinaturas de `calcularTaxa`.
- **Sobrescrita:** Cobertura redefine `calcularTaxa`, Zelador redefine `calcularSalario`, classes redefinem `toString`.

## Banco sem mistério

JPA é a especificação de mapeamento dos objetos Java para tabelas; Hibernate é a implementação utilizada. JDBC é o caminho de comunicação com o banco. MySQL e H2 são os bancos que armazenam os dados.

- `@Entity` identifica uma classe persistente.
- `@Id` define a chave primária. Unidade usa número; Morador usa CPF fictício; funcionários e lançamentos recebem IDs gerados.
- `@Column` define informações como nome da coluna e precisão decimal.
- `@ManyToOne` liga moradores e pagamentos à unidade.
- `@Inheritance(SINGLE_TABLE)` guarda uma hierarquia em uma tabela. A coluna `tipo` distingue COMUM/COBERTURA ou COMUM/ZELADOR.
- O construtor sem parâmetros protegido permite ao JPA reconstruir um objeto. Os construtores públicos são usados pela aplicação e validam os dados.
- Objetos consultados dentro da transação ficam gerenciados. Quando o serviço altera o saldo, o Hibernate detecta essa mudança e emite o UPDATE no commit.
- `PESSIMISTIC_WRITE` impede dois pagamentos simultâneos de sobrescreverem o mesmo saldo. O bloqueio termina no commit/rollback.

## Por que BigDecimal aparece se o exercício usa double?

Decimais como 0,1 não têm representação exata em ponto flutuante binário. Dinheiro é mantido como BigDecimal e gravado em DECIMAL com duas casas. Os métodos públicos com double facilitam a comparação com os exercícios; `Valores` concentra a conversão e os limites.

Não é necessário memorizar toda a API. Entenda a intenção: somar dez centavos com vinte centavos deve produzir trinta centavos. O teste `caixaUsaCentavosDecimais` demonstra isso.

## Roteiro para apresentar

1. Execute a versão de demonstração e consulte condomínio, cobertura, morador e funcionários.
2. Mostre taxa da cobertura 625 e com multa de 10% 687,50. Aponte os métodos correspondentes.
3. Registre o pagamento. Tente gastar mais que o saldo e mostre a recusa.
4. Registre despesa de 87,50, deixando 600. Consulte o histórico.
5. Feche e reabra o programa para demonstrar persistência.
6. Mostre uma consulta no Workbench se estiver usando MySQL.
7. Execute os testes e explique dois: cálculo da cobertura e despesa recusada preservando o saldo.

## Próximos passos para aprender

Antes de mudar qualquer regra, escreva a saída esperada e um teste. Experimente alterar o adicional da cobertura e veja quais resultados mudam. Depois restaure a regra de 25%. O importante é conseguir explicar o efeito de uma alteração, e não decorar os arquivos.

Os 20 exercícios orientam os conceitos usados. Não são vinte aplicativos nem etapas obrigatórias do produto. A prioridade acordada é um sistema integrado pequeno e compreensível.
