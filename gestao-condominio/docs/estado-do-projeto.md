# Estado do projeto — v1.0.0 (fase inicial)

## O que esta versão entrega

A fase inicial está concluída: uma TUI integrada que representa o condomínio e seus objetos, executa as regras financeiras, persiste os dados e pode ser apresentada em outra máquina. Isso não significa que todos os módulos da ideia original do grupo estejam implementados.

| Funcionalidade | Situação |
|---|---|
| Consultar condomínio, unidades e moradores | Implementada, com dados demonstrativos pré-cadastrados |
| Consultar funcionários e salário do zelador | Implementada |
| Taxa comum e cobertura com adicional de 25% | Implementada |
| Multa percentual sobre a taxa efetiva | Implementada; de 0% a 100% |
| Registrar pagamento e despesa | Implementada, com confirmação |
| Proteger o caixa contra despesa acima do saldo | Implementada |
| Consultar histórico e simular arrecadação | Implementada |
| Gravar e recuperar dados ao reiniciar | Implementada em H2 e MySQL |
| ZIP completo para estudo/entrega | Implementado |
| Execução por duplo clique | Pacotes Windows e Mac gerados e verificados pelo workflow de distribuição |
| Cadastrar, editar e excluir unidades/pessoas | Próxima fase; ainda não existe no menu |
| Editar dados gerais do condomínio | Próxima fase |
| Mensalidade por competência e impedir repetição | Não implementado; cada confirmação é um recebimento avulso |
| Login, permissões, visitantes e manutenção | Evoluções futuras; fora da v1 |

## O que foi pensado

O primeiro objetivo foi transformar os conceitos de POO em um fluxo que o grupo consiga acompanhar e explicar. O produto usa as ideias dos exercícios, mas não é uma coleção de vinte programas. A prioridade é demonstrar o caminho completo: escolha no terminal, chamada de objetos, validação e gravação no banco.

Usamos dados pré-cadastrados para testar esse caminho sem acrescentar formulários de cadastro, edição, exclusão e suas regras. A próxima fase acrescenta esses casos sobre a mesma estrutura. Por exemplo, excluir uma unidade que possui moradores e pagamentos exige decidir como preservar o histórico antes de escrever o botão de exclusão.

## Por que esta organização

- **App pequeno:** torna visível como o sistema começa e quais objetos colaboram.
- **Model com regras:** uma despesa inválida é rejeitada mesmo se for chamada por outro menu ou por um teste.
- **View separada:** o cálculo não precisa de teclado; a interação pode evoluir sem reescrever a regra.
- **Service:** junta ações que precisam acontecer como uma só operação, como aumentar o caixa e criar um lançamento.
- **Repository:** deixa o SQL/JPQL e a busca no banco fora da tela e do domínio.
- **JPA/Hibernate:** acompanha o exemplo do professor e mostra como classes e relacionamentos são mapeados.
- **H2 padrão:** reduz preparação na apresentação e leva o banco dentro da pasta do projeto.
- **MySQL opcional:** permite usar o Workbench e estudar um banco em servidor já familiar ao grupo.
- **BigDecimal:** protege o arredondamento de dinheiro sem abandonar os exemplos didáticos dos métodos com double.
- **Testes pequenos:** demonstram que as regras são verificáveis e ajudam a explicar o comportamento após uma mudança.

## Próxima fase proposta

1. Cadastro e edição de unidades, com validação de número e taxa.
2. Cadastro de moradores vinculado a uma unidade existente.
3. Cadastro de funcionários comuns e zeladores.
4. Edição dos dados do condomínio.
5. Exclusões com regras que preservem relacionamentos e histórico.

Cada parte deve incluir explicação, execução e testes. Evitar acrescentar todos os módulos da proposta inicial de uma vez.

## Ordem sugerida de leitura

1. `README.md`: executar e localizar as pastas.
2. `docs/estado-do-projeto.md`: saber o que existe e os limites.
3. `docs/guia-de-estudo.md`: acompanhar uma operação concreta e reconhecer POO.
4. `docs/banco-de-dados.md`: entender H2, MySQL, JDBC, JPA e a conexão.
5. `App.java`, `MenuPrincipal.java` e `GestaoService.java`: seguir o fluxo real.
6. As classes em `model/`, seguidas do repositório e de `Banco.java`.
7. Os testes: comparar cenários com resultados esperados.

O diagnóstico inicial fica preservado como histórico; este documento e o README descrevem a versão atual. A release fixa o código e os downloads de uma etapa. Novas funcionalidades devem formar outra versão, sem substituir silenciosamente a v1.
