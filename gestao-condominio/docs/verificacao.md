# Registro de implementação e verificação

Data: 26/10/2026. Branch: `feat/gestao-condominio`. Sem commit ou publicação automática.

## Implementado

Núcleo integrado, TUI, JPA, MySQL e H2, dados iniciais idempotentes, transações financeiras, confirmação/cancelamento, histórico e pacote portátil. Os materiais anteriores foram copiados para o repositório sem apagar os originais.

## Evidências

- Compilação com Java 26.0.1 e `--release 17`; Maven 3.9.16 incluído no IntelliJ.
- Maven Wrapper 3.9.9 executado com sucesso: 23 testes passaram e 1 teste opcional MySQL foi ignorado nessa execução, conforme configuração.
- Suíte completa com `TESTAR_MYSQL=true`: **24 testes, 0 falhas, 0 erros, 0 ignorados**.
- MySQL 8.4 em container temporário separado, porta local 3307: importação do arquivo `sql/mysql.sql`, reconstrução das subclasses, pagamento, despesa, recusa, rollback e recuperação dos dados após reabrir a conexão.
- H2: testes com banco temporário em arquivo e reabertura, além de testes de menu com Scanner.
- ZIP extraído em pasta temporária: aplicação executada pelo iniciador, pagamento gravado e recuperado após reinício.
- Empacotamento `--incluir-banco` executado nessa cópia: segunda extração preservou saldo R$ 500,00 e histórico do pagamento.
- Pacote conferido: fonte, testes, anexos, JAR, banco H2 e backups/scripts SQL; sem cache de ferramentas, pasta Git ou arquivo `.env`.
- Scripts de execução macOS/Linux testados. Script Windows revisado, sem execução nativa neste ambiente.

## Revisão independente e correções

Um agente separado revisou o código sem editar os arquivos. Três falhas foram reproduzidas por testes antes das correções:

1. O iniciador executava o JAR antigo da raiz após recompilar. Agora prioriza `target/` quando existe.
2. Um percentual sobre valor elevado perdia um centavo ao converter o produto para double antes do arredondamento. Agora arredonda em BigDecimal primeiro.
3. Uma falha de consulta após commit podia afirmar que o pagamento não tinha sido confirmado. A mensagem agora orienta conferir o histórico antes de repetir a operação.

Após corrigir, toda a suíte com MySQL passou. Não ficaram achados da revisão pendentes.

## Decisões práticas

- Trabalho na pasta solicitada e em branch própria, sem criar outro checkout.
- H2 é a execução padrão do pacote para dispensar servidor na apresentação. MySQL continua disponível por configuração, com SQL de demonstração testado.
- Não expandir para login, visitantes, manutenção, cadastro completo ou cobrança por competência. Essas funcionalidades exigiriam outras regras e tornariam a primeira apresentação maior.
- O banco H2 da entrega padrão contém somente demonstração. Registros locais entram apenas com `--incluir-banco`; o MySQL real é compartilhado por dump SQL.

## Repetir os testes

`./mvnw test` executa os testes locais. No Windows: `mvnw.cmd test`.

O teste MySQL exige base descartável com os dados iniciais e caixa zerado. Configure `BANCO=mysql`, `DB_URL`, `DB_USER`, `DB_PASSWORD` e `TESTAR_MYSQL=true` no ambiente antes de executar `./mvnw test`. Ele registra pagamento e despesa; **não use uma base com registros que queira preservar**.

## Limitações da verificação

Não houve execução nativa em Windows nem em um JDK 17 instalado; o alvo de compilação é 17 e a execução local usou JDK 26. Não foi testada transferência automática entre H2 e MySQL, pois essa função não faz parte do projeto. Mudanças no código exigem recompilar e gerar novamente a entrega.
