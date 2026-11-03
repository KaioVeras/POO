# Residencial Golden Java 2.0.0 — moradia e portaria

Versão simplificada para a apresentação do Grupo 02, centrada no morador e nos setores primários do condomínio.

## Downloads

- **GoldenJava-2.0.0-windows-x64.zip**: Windows Intel/AMD 64 bits. Extraia tudo e abra GoldenJava.exe.
- **GoldenJava-2.0.0-macos-arm64.zip**: Mac Apple Silicon (M1 e posteriores). Extraia e abra GoldenJava.app.
- **GoldenJava-2.0.0-macos-x64.zip**: Mac Intel. Extraia e abra GoldenJava.app.
- **grupo_2_projeto.zip**: fontes, testes, documentação, banco H2 demonstrativo, scripts SQL e JAR. Requer Java 17+ para executar esta opção.

Os pacotes nativos incluem Java. Não é necessário instalar Java, Maven ou MySQL nem digitar comandos para abrir. Mantenha toda a pasta extraída. Os arquivos automáticos Source code do GitHub não incluem o aplicativo compilado.

## Funcionalidades

- Administração: cadastro/listagem de apartamentos com vaga opcional e funcionários com cargo.
- Moradia: cadastro/listagem de moradores e ficha com CPF, telefone, bloco, apartamento, garagem e encomendas.
- Portaria: recebimento de encomenda para um morador, consulta de pendências, confirmação de retirada e histórico.
- Persistência após fechar e reabrir; validação de duplicidades e vínculos.

O financeiro, as taxas e as classes especiais Cobertura e Zelador foram retirados. Zelador é agora um cargo de Funcionario. Edição/exclusão, autenticação, veículos e manutenção ficam fora desta entrega.

## Banco separado da versão anterior

A versão 2 usa dados/moradia.mv.db (Windows/pacote completo) e a base MySQL gestao_moradia. No aplicativo Mac, os dados ficam em ~/Library/Application Support/GoldenJavaMoradia/dados/moradia.mv.db. Os registros financeiros da v1 não são apagados nem migrados automaticamente. Não substitua um banco existente sem backup.

## Verificação

Os builds do Windows, Mac Apple Silicon e Mac Intel passaram com Java 17 no [workflow 41556709947](https://github.com/KaioVeras/POO/actions/runs/41556709947). Cada plataforma executou os cinco testes e validou o aplicativo depois de extrair o ZIP: cadastro, ficha, recebimento, retirada e recuperação dos dados após reiniciar.

H2 é a opção demonstrativa verificada. O SQL MySQL foi alinhado ao modelo novo; esta release não afirma validação de um servidor MySQL real na v2.

O README inclui um roteiro de cinco minutos. Os aplicativos não possuem assinatura comercial/notarização e podem exigir autorização conforme a política do Windows/macOS. A v1 permanece disponível como histórico, mas não contém o novo cadastro.
