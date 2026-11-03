# Estado da versão 2 — 02/11/2026

## Pronto

Cadastro/listagem de apartamentos com vaga opcional, moradores e funcionários com cargo. Ficha do morador com dados pessoais, apartamento, garagem e histórico de encomendas. Recebimento, consulta de pendências e retirada. Persistência H2; configuração e script MySQL do novo modelo.

## Regras simples

CPF com 11 dígitos e único (sem validação de dígitos verificadores, pois a apresentação usa dados fictícios). Apartamento único por bloco/número; vaga não compartilhada entre apartamentos. Telefone obrigatório como texto, permitindo DDD e pontuação. Morador precisa de apartamento existente; encomenda precisa de morador existente. Retirada não pode ser repetida.

## Limites e próximos passos

Sem edição/exclusão nesta entrega; sem login, visitantes, veículos, múltiplas vagas ou manutenção. Sem financeiro. Encomendas são vinculadas a um morador; não há comprovação documental de quem retirou. Um condomínio por banco. Sem migração automática dos registros da v1. A apresentação deve se concentrar no cadastro e na ficha do morador.

A alteração anterior de IDs no SQL foi substituída pelo modelo coerente desta versão: todos os registros têm ID, e os vínculos utilizam IDs. Os materiais enviados anteriormente permanecem como histórico.
