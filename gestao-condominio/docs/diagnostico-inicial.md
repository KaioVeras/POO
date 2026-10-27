# Residencial Golden Java — diagnóstico e proposta técnica

> Documento histórico. Decisões posteriores aprovadas na conversa: os exercícios são treinamento, não etapas obrigatórias; implementar um produto integrado pequeno. O escopo atual está no README e em docs/plano-implementacao.md. Não foram mantidas exigências de assinatura literal de todos os exercícios.

Análise dos três anexos em 26/10/2026. Este documento é uma proposta para revisão, anterior à implementação.

## 1. O que precisa ser entregue

O enunciado pede dois arquivos: `grupo_2_docs.pdf`, reunindo Business Model Canvas, telas e pesquisa bibliográfica; e `grupo_2_projeto.zip`, com o código do projeto Java. Os exercícios são cumulativos: cada exercício refatora o anterior, até formar uma aplicação única.

A lista informa `AT1 = (item 2 + item 3) * 0.4` e `AT2 = (item 1 + item 4) * 0.5`, com pesos 1, 2, 3 e 4 para os respectivos itens. Convém preservar essa redação: ela não esclarece sozinha a escala de notas. Não há data de entrega explícita nas páginas fornecidas. O cadastro de todos os integrantes no grupo do AVA e o acesso público ao Figma são exigências expressas.

O banco relacional é um requisito informado pelo usuário; não aparece como obrigação nos 20 exercícios. Será uma extensão técnica integrada ao mesmo sistema.

## 2. Diagnóstico da proposta do grupo

O PDF identifica Grupo 02, Kaio Veras e Emanuel Veloso, e o produto Residencial Golden Java.

- **BMC:** os nove blocos estão presentes na imagem da página 2. A imagem exige ampliação para leitura confortável; uma versão maior melhoraria a entrega. Há um link para o original no Google Drive.
- **Perfis:** síndico, porteiro, chefe de manutenção, morador, funcionário e visitante/prestador estão descritos.
- **Telas:** sete telas são descritas e há um link do Figma. O PDF não contém as imagens dos sete wireframes. A existência e o acesso público aos frames ainda precisam ser verificados no Figma, inclusive sem autenticação.
- **Pesquisa:** os quatro pilares de POO estão definidos e relacionados a exemplos do domínio. Existem referências e atribuições, mas não foi realizada conferência dos trechos com o livro ou com as fontes externas. Se o professor exigir maior precisão bibliográfica, acrescentar páginas do livro e associar cada definição à sua fonte.
- **Coerência:** o BMC menciona visitantes como usuários, enquanto a seção de perfis esclarece que eles não acessam diretamente o sistema. Ajustar o BMC para tratá-los como pessoas registradas pela portaria.
- **Escopo:** autenticação, permissões, visitantes, chamados, filtros e cadastros completos ampliam os exercícios. Proponho deixá-los como evolução futura e entregar primeiro todo o núcleo avaliado.

Não foi fornecido código inicial do sistema do grupo: o ZIP contém o exemplo de loja do professor.

## 3. Matriz dos 20 exercícios

| Exercício | Evidência exigida | Onde aparecerá na evolução | Verificação principal |
|---|---|---|---|
| 1 | App, variáveis, ficha do condomínio e portaria 24h | Primeira versão e consulta final | Ficha contém os campos solicitados |
| 2 | Entrada com Scanner | Configuração inicial e entradas do menu | Nome com espaços e entradas numéricas válidas/inválidas |
| 3 | if/else: econômica, intermediária e elevada | Classificação da taxa padrão | 400, 400,01, 700 e 700,01 |
| 4 | Arrecadação e uma linha por unidade com for | Simulação | Soma e linhas correspondem às unidades |
| 5 | Menu contínuo e opção 0 | MenuPrincipal | Repetir operações e sair |
| 6 | Morador, atributos e instanciação | Etapa introdutória | Criar e consultar moradores |
| 7 | Construtor de Morador e this | Morador | Valores preservados na construção |
| 8 | Unidade, número, taxa-base, ocupação e construtor | Unidade | Unidades 101, 102 e 201 |
| 9 | calcularTaxa e exibirSituacao | Unidade | Taxa normal e situação textual |
| 10 | Atributos privados, getters e taxa não negativa | Unidade | Taxa negativa rejeitada; zero permitido |
| 11 | Condominio, receberPagamento, pagarDespesa e saldo | Condominio | Valores positivos; saldo insuficiente rejeitado |
| 12 | Unidade calcula e Condominio recebe | Serviço financeiro e menu | Recebimento aumenta saldo pelo valor calculado |
| 13 | Dois construtores e this(...); ocupada por padrão | Unidade | Construtor curto cria unidade ocupada |
| 14 | calcularTaxa(double percentualMulta), sem duplicação | Unidade | 500 com 10% resulta em 550 |
| 15 | Cobertura extends Unidade e super(...) | Cobertura | Reutiliza construtor da superclasse |
| 16 | Override com super.calcularTaxa e adicional 25% | Cobertura | 500 resulta em 625 |
| 17 | Funcionario, nome privado, salarioBase protected; Zelador | Funcionario e Zelador | Construtores e salário-base |
| 18 | Zelador sobrescreve salário com adicional 10% | Zelador | 3000 resulta em 3300 |
| 19 | toString nas cinco classes indicadas | Classes de domínio | Representações identificam tipo e valores |
| 20 | Menu integrado com nove operações e saída | MenuPrincipal | Percurso completo, incluindo erros recuperáveis |

Os exercícios iniciais apresentam estados transitórios, como atributos públicos e valores fixos, que depois são substituídos. A versão final deve conter as refatorações; um diário de evolução explicará os passos anteriores sem manter vinte programas. Os exemplos de números e nomes não obrigam o produto a chamar-se Java Garden: manteremos o nome do grupo e os objetos demonstrativos pedidos.

## 4. O que aproveitar do professor

O projeto usa Java 17, Maven, Jakarta Persistence 3.1, Hibernate 6.6.4.Final, H2 2.3.232 e MySQL Connector/J 9.1.0. Essas são as versões do anexo, não uma afirmação de que sejam as mais recentes.

O fluxo é `App → view → service → repository → JPA`. As entidades Produto e Categoria demonstram anotações, chaves e relacionamento. Os repositórios abrem e fecham EntityManager e usam transações com rollback nas escritas. ProdutoRepository usa JOIN FETCH para disponibilizar a categoria após fechar a sessão. O banco é selecionado por propriedade ou variável de ambiente.

Adaptações necessárias:

1. Manter validações essenciais nos próprios objetos, conforme a lista; o exemplo concentra muitas validações nos serviços.
2. Registrar pagamento/despesa e atualizar o saldo dentro da mesma transação. Duas gravações independentes poderiam deixar histórico e saldo divergentes.
3. Injetar as dependências pelos construtores para testar serviços sem banco.
4. Ler credenciais do ambiente, evitando copiar a senha fixa do exemplo.
5. Tratar o fim da entrada como encerramento/cancelamento. O exemplo retorna texto `0` ao terminar a entrada, o que pode ser interpretado como um dado real.
6. Inicializar os dados de demonstração apenas quando ainda não existirem, preservando pagamentos após reiniciar.

## 5. Alternativas e recomendação

| Caminho | Benefício | Custo |
|---|---|---|
| A — Núcleo de POO em memória, seguido de JPA/MySQL | Facilita aprender e testar os exercícios antes do banco; aproxima-se progressivamente do professor | Exige uma etapa posterior de mapeamento e integração |
| B — JPA/MySQL desde o primeiro incremento | Persistência disponível desde o início | Mistura instalação, mapeamento, transações e conceitos básicos de Java |

**Recomendação: A.** MySQL será o banco principal; H2 poderá apoiar testes de integração. A comparação é uma proposta do assistente, ainda não uma escolha do usuário.

## 6. Arquitetura proposta

```text
App                          monta dependências e inicia/encerra recursos
view/Console                 lê dados com Scanner e apresenta mensagens
view/MenuPrincipal           mantém o laço e encaminha opções
model/                       contém estado, comportamento e invariantes
service/                     coordena operações entre objetos
repository/                  consulta e persiste objetos
infra/                       configura JPA e dados iniciais
```

Classes do núcleo: `Condominio`, `Unidade`, `Cobertura`, `Morador`, `Funcionario` e `Zelador`. Uma futura `MovimentacaoFinanceira` guardará tipo, data, valor, descrição e unidade quando houver pagamento.

Exemplo didático do fluxo: a tela pede a unidade 201; o serviço a encontra como Unidade; o objeto real é Cobertura e calcula 625 para base 500; Condominio recebe esse valor; a infraestrutura grava o saldo e o lançamento na mesma transação. O menu apenas apresenta o resultado. Essa sequência permite explicar polimorfismo, colaboração entre objetos e persistência.

### Compatibilidade com a lista

- Manter os construtores e métodos com `double` indicados pelo professor, inclusive `calcularTaxa()`, `calcularTaxa(double)`, `receberPagamento(double)` e `pagarDespesa(double)`.
- Proposta para dinheiro: usar representação decimal interna e colunas `DECIMAL`, convertendo na fronteira dos métodos exigidos. Documentar arredondamento em duas casas. Não trocar silenciosamente as assinaturas exigidas por BigDecimal.
- Manter `protected double salarioBase` em Funcionario, como solicitado. Validar finitude, sinal e arredondar o resultado monetário; documentar essa exceção ao uso interno de decimal.
- A multa recebe `10` para representar 10%, e deve reutilizar `calcularTaxa()`. Assim, 10% sobre uma cobertura de 625 produz 687,50, sem perder o adicional de 25%.
- Taxa zero é válida; recebimentos e despesas de zero não são. Rejeitar NaN e infinitos nos métodos que recebem double.
- Não impor dígitos verificadores de CPF: o próprio exercício usa CPFs fictícios. Validação de formato e unicidade podem ser acrescentadas na etapa de cadastro.
- Preservar `exibirSituacao()` na classe por exigência pedagógica; concentrar o restante da apresentação na view.

### Persistência proposta

Tabelas para condomínio, unidades, moradores, funcionários e movimentações. Heranças Unidade/Cobertura e Funcionario/Zelador podem usar `SINGLE_TABLE` com discriminador para diminuir a complexidade inicial. Morador referencia uma unidade; pagamento referencia a unidade correspondente. Saldo e movimentação devem ser atômicos. Usar controle de versão ou bloqueio ao alterar o caixa para evitar perda de atualizações concorrentes.

O modelo inicial atende um condomínio. Como só há um condomínio, o número da unidade pode ser único globalmente; múltiplos blocos com números repetidos exigiriam identificação composta por bloco e número, fora desta primeira versão.

Pagamentos da lista não possuem competência mensal nem regra contra repetição: cada confirmação representa um novo recebimento. Controle de inadimplência e cobrança mensal exigiriam modelar competências antes de serem implementados.

## 7. Etapas e critérios de conclusão

1. **Fundamentos, exercícios 1–5:** ficha, leitura, classificação, simulação e menu. Registrar no diário como o código será refatorado.
2. **Objetos, exercícios 6–12:** moradores, unidades, caixa e pagamento colaborativo. Testar invariantes e garantir que uma operação recusada não altera o saldo.
3. **Reuso e polimorfismo, exercícios 13–19:** sobrecargas, cobertura, funcionários, zelador e representações textuais. Testar chamadas pela referência da superclasse.
4. **Integração, exercício 20:** as nove opções previstas e saída, dados demonstrativos, mensagens claras, entrada inválida e fim da entrada.
5. **Persistência:** mapeamentos JPA, MySQL configurável, dados iniciais idempotentes e histórico financeiro transacional. Verificar persistência após reabrir e rollback.
6. **Preparação da apresentação e entrega:** README de execução, esquema de dados, matriz dos exercícios, diário de aprendizado e roteiro demonstrativo; gerar `grupo_2_projeto.zip` somente após validar.

Cada incremento deve apresentar: conceito aprendido, mudança pequena no código, execução observável, teste relevante e uma pergunta de revisão para o aluno explicar com suas palavras.

## 8. Testes previstos

- Domínio: limites da classificação; taxa negativa/zero; multa; ocupação padrão; cobertura; salário de zelador; pagamentos e despesas inválidos; despesa igual ao saldo; saldo preservado após erro.
- Polimorfismo: Unidade apontando para Cobertura e Funcionario apontando para Zelador; multa sobre o valor calculado da cobertura.
- Terminal: nome com espaços, vírgula ou ponto decimal, texto em campo numérico, opção inexistente, objeto não encontrado, retorno ao menu e saída por 0 ou fim da entrada.
- Integração JPA: persistir e recuperar subclasses, relacionamentos, saldo e lançamentos; rollback; reinicialização sem duplicar dados.
- MySQL real: executar separadamente os testes de integração e reabrir o sistema. Testes no H2 não serão apresentados como prova de compatibilidade total com MySQL.

## 9. Ambiente e pontos ainda abertos

Nesta sessão, `java -version` encontrou Java 26.0.1. `mvn -version` não encontrou Maven no PATH. O projeto terá alvo Java 17 para acompanhar o professor. A disponibilidade de MySQL e as credenciais ainda não foram verificadas; Workbench é um cliente e requer um servidor MySQL para guardar os dados.

Não houve implementação ou execução de testes nesta etapa de diagnóstico. Antes de instalar ferramentas ou escrever o produto, falta escolher o caminho A/B e revisar esta proposta. Os links externos de Figma e Drive foram extraídos do PDF, mas seus conteúdos e permissões não foram auditados.
