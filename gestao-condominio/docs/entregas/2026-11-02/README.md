# Entrega do Grupo 02: documentação e Collections

> Registro dos materiais enviados antes da simplificação do domínio. A aplicação atual está descrita no [README do projeto](../../../README.md).

Materiais recebidos e organizados em **02/11/2026** para consulta do professor. Projeto: **Residencial Golden Java**, de **Kaio Veras e Emanuel Veloso**.

## Acesso direto

| Material | Onde encontrar |
|---|---|
| Documentação entregue pelo grupo | [Projeto_POO_Grupo02.pdf](Projeto_POO_Grupo02.pdf) |
| Orientações fotografadas na aula | [Foto das orientações](orientacoes-professor.jpeg) |
| Programa independente do item 05 | [Collections: App.java](../../../atividades/collections/App.java) |
| Como executar e entender o programa de Collections | [README da atividade](../../../atividades/collections/README.md) |
| Aplicação principal atual | [App.java do sistema](../../../src/main/java/br/edu/catolica/ucb/poo/App.java) |
| Estrutura e execução do sistema | [README do projeto](../../../README.md) |
| Funcionalidades implementadas e pendências | [Estado do projeto](../../estado-do-projeto.md) |
| Versão atual para download | [Release condominio-v2.1.0](https://github.com/KaioVeras/POO/releases/tag/condominio-v2.1.0) |

## Conferência por item das orientações

| Item na foto | Evidência disponível | Observação |
|---|---|---|
| 00 - Git/Drive e acesso ao material | Este repositório e os links acima | Os materiais estão reunidos para consulta; permissões individuais de edição não foram auditadas nesta organização. |
| 01 - Módulos iniciais, entre 10 e 15 | PDF, página 2 | Proposta com 10 módulos e agrupamentos lógicos. |
| 02 - Personas/perfis | PDF, página 3 | Síndico, morador, porteiro e equipe de manutenção. |
| 03 - Diagrama inicial de casos de uso em PDF | PDF, página 3; [imagem do diagrama](diagramas/casos-de-uso.png) | Representa a proposta geral do sistema. |
| 04 - Modelo de dados | PDF, página 4; [imagem do modelo](diagramas/modelo-de-dados.png) | Núcleo do modelo de dados; o mapeamento implementado também está em [banco-de-dados.md](../../banco-de-dados.md). |
| 04 - Executar/analisar loja-jpa-cl no IntelliJ | Análise registrada no PDF, página 4; [exemplo do professor](../../referencias/loja-jpa-cl.zip) | A foto repete o número 04. O PDF contém notas breves da análise, sem capturas que comprovem execução no IntelliJ. |
| 05 - Programa Java único com ArrayList, HashSet e HashMap | [App.java independente](../../../atividades/collections/App.java) | Complemento ao trecho colocado na página 5 do PDF; não depende de JPA nem de banco. |

Este índice descreve os materiais disponíveis, não atribui nota nem substitui a avaliação do professor.

## Esclarecimento sobre os dois App.java

O texto enviado pelo grupo explicou a dúvida no item 05 e informou o envio de um programa separado com Collections. A organização do repositório mantém essa distinção:

- **App.java da aplicação principal:** inicia a conexão, prepara os dados e abre o menu do sistema completo. Está no pacote `br.edu.catolica.ucb.poo`, em `src/main/java/`.
- **App.java de Collections:** programa isolado, em arquivo único, com `ArrayList`, `HashSet` e `HashMap` no contexto do condomínio. Está em `atividades/collections/` e é o arquivo complementar para o item 05.

O PDF foi preservado **sem alterar o documento já enviado**. Na página 5, o trecho apresentado utiliza `JPAUtil` e não demonstra as três coleções. A versão atual da aplicação principal utiliza `Banco`; portanto, para consultar ou executar o código vigente, siga o link da aplicação principal acima. Para a exigência de Collections, consulte o arquivo independente.

## Proposta geral e implementação atual

O PDF descreve uma proposta mais ampla, com autenticação, permissões, portaria, reservas, manutenção e relatórios. Os perfis e casos de uso representam esse planejamento inicial.

Depois da entrega, o professor orientou reduzir o escopo ao domínio principal do condomínio. A versão 2 implementa Administração, Moradia e Portaria, com o morador como elemento central, e retira a parte financeira que existia na versão 1. As decisões estão em [estado-do-projeto.md](../../estado-do-projeto.md).

Diferenças entre o modelo do PDF e o código atual são esperadas: o PDF foi mantido como foi entregue, sem alterações. O modelo implementado está em [banco-de-dados.md](../../banco-de-dados.md).

## Diagramas

As imagens abaixo foram extraídas das páginas 3 e 4 do PDF fornecido, sem redesenhar ou mudar seu conteúdo. O PDF continua sendo o documento de referência da entrega. Os arquivos-fonte Mermaid desses dois diagramas não foram fornecidos separadamente.

### Casos de uso (proposta inicial)

![Casos de uso da proposta do grupo](diagramas/casos-de-uso.png)

### Modelo de dados (núcleo inicial)

![Modelo de dados do núcleo](diagramas/modelo-de-dados.png)
