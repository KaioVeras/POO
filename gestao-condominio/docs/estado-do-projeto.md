# Estado do projeto (versão 2)

## Decisões aprovadas com o professor

A primeira versão abrangia taxas, multas, finanças, salários e tipos especiais de funcionários. O professor considerou o escopo aprofundado demais e orientou começar pelo domínio principal do condomínio, detalhando apenas o necessário. Por isso:

- O sistema foi organizado em Administração, Moradia e Portaria, com o morador como elemento central.
- Taxas, multas e gestão financeira foram retiradas.
- Zelador passou a ser um cargo de funcionário, não uma classe própria.
- Herança e padrões de projeto só são usados quando há motivo concreto.

## O que está pronto

- Consulta do condomínio.
- Cadastro e listagem de apartamentos, com vaga de garagem opcional.
- Cadastro e listagem de funcionários com cargo.
- Cadastro e listagem de moradores.
- Ficha do morador com dados pessoais, apartamento, bloco, garagem e encomendas.
- Recebimento de encomendas, lista de pendentes e registro de retirada.
- Persistência em banco H2; configuração e script SQL para MySQL.
- Limpeza da tela do terminal a cada escolha no menu.

## Regras de validação

- CPF com 11 dígitos e sem repetição. Os dígitos verificadores não são calculados, pois a apresentação usa dados fictícios.
- Apartamento único por bloco e número; o mesmo número pode existir em blocos diferentes.
- Uma vaga de garagem não pode pertencer a dois apartamentos.
- Campos obrigatórios não podem ficar vazios.
- Morador precisa de um apartamento existente; encomenda precisa de um morador existente.
- Uma encomenda não pode ser retirada duas vezes.
- Se a entrada terminar no meio de um cadastro, nada é gravado.

## Limites intencionais

Estes itens ficaram fora da versão 2 de propósito:

- Edição e exclusão de cadastros.
- Login e permissões.
- Gestão financeira.
- Cadastro de veículos e várias vagas por apartamento.
- Módulo de manutenção.
- Mais de um condomínio por banco.
- Migração automática dos dados da versão 1.

## Verificações realizadas

- Cinco testes automatizados com JUnit passam (`./mvnw test`). O conteúdo de cada um está no [guia de estudo](guia-de-estudo.md#8-testes).
- O ZIP completo e os pacotes nativos de Windows, Mac Intel e Mac Apple Silicon foram verificados no GitHub Actions por `scripts/verificar_nativo.py`: cadastro de morador, recebimento e retirada de encomenda, com reabertura do banco entre as etapas.
- O script `sql/mysql.sql` foi alinhado ao modelo atual, mas **não foi testado em um servidor MySQL real**. A apresentação usa o H2.

## Bancos da versão anterior

A versão 2 usa o arquivo `dados/moradia.mv.db` e a base MySQL `gestao_moradia`. O banco da versão 1 (`dados/condominio.mv.db`) não é usado nem apagado. Os documentos e o código da versão 1 continuam disponíveis na [release v1](https://github.com/KaioVeras/POO/releases/tag/condominio-v1.0.0) e no histórico do Git.
