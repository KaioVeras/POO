# Verificação da versão 2

Cinco testes automatizados: cadastro e persistência após reabrir; rejeição de duplicidades e vínculos inexistentes; apartamentos em blocos distintos e vaga opcional; cadastro/ficha pelo menu; cancelamento por fim de entrada. Resultado: cinco passaram, nenhum ignorado.

O ZIP completo e o pacote Mac são verificados executando o cadastro de morador, recebimento e retirada de encomenda, com reabertura do banco entre operações. O SQL MySQL foi alinhado ao novo modelo, mas esta etapa não valida um servidor MySQL real. A apresentação utiliza H2 em arquivo.

O banco antigo não é apagado: a versão atual usa dados/moradia.mv.db e base MySQL gestao_moradia. Os downloads da release v1 são históricos.
