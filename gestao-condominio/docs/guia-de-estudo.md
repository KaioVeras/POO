# Como explicar a versão 2

## Domínio primeiro

Um condomínio tem apartamentos. Um apartamento tem bloco, número e, opcionalmente, uma vaga. Vários moradores podem compartilhar o mesmo apartamento. Cada morador tem contato e pode receber várias encomendas. Um funcionário tem um cargo; zelador é um valor desse cargo, sem comportamento diferente que justifique outra classe.

A vaga é um campo do apartamento nesta versão, limitado a uma vaga por apartamento. Não precisamos de uma classe que ainda não possui comportamento próprio. Se surgir necessidade de várias vagas, veículos ou controle de acesso, o modelo pode evoluir.

## Caminho de um cadastro

1. App abre Banco e prepara os dados iniciais apenas uma vez.
2. MenuPrincipal lê nome, CPF, telefone e o ID do apartamento.
3. GestaoService abre uma transação e busca o apartamento existente.
4. Morador valida os campos no construtor. O serviço verifica se o CPF já existe.
5. O repositório chama persist; Hibernate grava e gera o ID.
6. O commit confirma e a tela mostra o registro. Se faltar um dado ou ocorrer erro, o rollback desfaz a operação.

## Caminho de uma encomenda

A portaria seleciona o ID do morador e informa a descrição. Encomenda recebe automaticamente data de recebimento e fica pendente, pois retiradaEm é nula. A retirada preenche essa data. Não se apaga o registro: a ficha mostra o histórico e conta somente as encomendas ainda pendentes. Retirar duas vezes é recusado.

## POO aplicada sem forçar herança

- Abstração: representar apenas informações necessárias ao fluxo.
- Encapsulamento: campos privados; o método retirar protege a situação da encomenda.
- Associação: Morador possui referência a Apartamento; Encomenda possui referência a Morador.
- Construtores: validam os dados antes de formar os objetos.
- toString: produz a identificação legível de cada registro.

Não precisamos de herança para comprovar que existe POO. Nesta fase, associações e responsabilidades representam melhor o domínio.

## Arquivos para abrir na apresentação

Comece em model/Morador.java e model/Apartamento.java. Mostre model/Encomenda.java e seu método retirar. Depois acompanhe cadastrarMorador no GestaoService e a busca/gravação no CondominioRepository. Por último, explique Banco e execute o roteiro do README.
