# Plano de implementação — Residencial Golden Java

**Objetivo:** entregar um projeto pequeno, explicável, funcional e transportável.
**Arquitetura:** App monta dependências; view lê e apresenta; service coordena; model protege regras; repository usa JPA. Sem framework web, autenticação ou funcionalidades operacionais extras.
**Tecnologias:** Java com alvo 17, Maven, JPA/Hibernate, MySQL; H2 em arquivo para demonstração sem servidor.
**Referência:** diagnóstico inicial e decisões aprovadas na conversa. Os exercícios são material de treinamento; desenvolver um produto integrado, organizado por funcionalidades.

## Decisões atualizadas

- Dinheiro será representado por BigDecimal internamente, com métodos double didáticos para aproximar o código dos exercícios. Entradas monetárias têm até duas casas, arredondamento HALF_UP nos cálculos.
- Condomínio único, três unidades de demonstração e consultas por número. Dados iniciais criados uma única vez. Pagamentos são recebimentos avulsos, sem competência mensal.
- Despesas e pagamentos atualizam caixa e histórico na mesma transação, com bloqueio no condomínio.
- A entrega contém fonte, JAR com dependências, banco H2 de demonstração, scripts MySQL e instruções. Java 17+ é pré-requisito; Maven apenas para recompilar. O banco MySQL é recriado por SQL, pois um servidor não é transportado copiando arquivos de programa.
- Trabalhar diretamente na pasta pedida pelo usuário, em branch própria. Não fazer commits/push automaticamente.

## Tarefas

### 1. Domínio e regras
- [x] Escrever e executar testes de taxas, multa, salário, caixa e validações antes das classes.
- [x] Implementar model/Condominio, Unidade, Cobertura, Morador, Funcionario, Zelador e Valores.
- [x] Verificar: testes JUnit de domínio passam.

### 2. Persistência e operações
- [x] Escrever testes reais JPA/H2: recebimento, despesa, rollback, subclasses e reabertura.
- [x] Implementar infra/Banco, infra/DadosIniciais, repository/CondominioRepository e service/GestaoService.
- [x] Verificar: saldo e histórico duráveis; inicialização idempotente.

### 3. Terminal
- [x] Escrever teste de menu com entradas reais, incluindo erros e fim da entrada.
- [x] Implementar view/Console, view/MenuPrincipal e App.
- [x] Verificar: nove opções, vírgula decimal, erro recuperável, saída sem alteração involuntária.

### 4. Distribuição
- [x] Criar SQL MySQL, instruções, roteiro de apresentação e scripts de execução.
- [x] Gerar JAR e ZIP com fonte, referência e banco demonstrativo.
- [x] Extrair em outra pasta e executar para verificar portabilidade e persistência após reinício.

## Atenção na revisão

Fim da entrada durante operação cancela sem gravar; multa sobre cobertura inclui adicional; gasto recusado não grava lançamento; erro de banco não é confundido com regra de domínio; pacote não contém credenciais nem cache de ferramentas.

## Comandos de verificação

`./mvnw test` e `./mvnw package`; testes de MySQL real dependem de servidor disponível e serão reportados separadamente.
