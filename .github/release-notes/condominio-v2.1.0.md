# Residencial Golden Java 2.1.0: tela limpa e código organizado

Versão de apresentação do Grupo 02. Mantém as mesmas funcionalidades da 2.0.0, com navegação mais limpa, código organizado e documentação revisada.

## Downloads

- **GoldenJava-2.1.0-windows-x64.zip**: Windows 64 bits (Intel/AMD). Extraia tudo e abra GoldenJava.exe.
- **GoldenJava-2.1.0-macos-arm64.zip**: Mac Apple Silicon (M1 ou mais recente). Extraia e abra GoldenJava.app.
- **GoldenJava-2.1.0-macos-x64.zip**: Mac Intel. Extraia e abra GoldenJava.app.
- **grupo_2_projeto.zip**: código, testes, documentação, banco H2 de demonstração, script SQL e JAR. Requer Java 17 ou superior.

Os pacotes nativos incluem o Java. Não é preciso instalar Java, Maven ou MySQL. Mantenha toda a pasta extraída. Os arquivos automáticos "Source code" do GitHub não incluem o aplicativo compilado.

## Novidades

- A tela do terminal é limpa a cada escolha no menu: aparecem apenas o resultado da operação, com um título, e o menu seguinte.
- Código reorganizado no formato padrão do Java, com comentários explicando cada classe e método.
- Mensagens corrigidas (concordância e ficha do morador sem IDs repetidos) e datas no formato dd/MM/aaaa HH:mm.
- Documentação revisada, com guia de estudo ampliado e perguntas para a apresentação.
- Arquivos obsoletos da versão 1 removidos do repositório (continuam disponíveis na release v1).

## Funcionalidades

- **Administração:** consultar o condomínio; cadastrar e listar apartamentos com vaga opcional e funcionários com cargo.
- **Moradia:** cadastrar e listar moradores; ficha com CPF, telefone, bloco, apartamento, garagem e encomendas.
- **Portaria:** receber encomenda para um morador, listar pendências e registrar a retirada.
- Dados preservados após fechar e reabrir; recusa de duplicidades e de vínculos inexistentes.

## Dados

O banco continua o mesmo da 2.0.0 (`dados/moradia.mv.db`; no Mac, `~/Library/Application Support/GoldenJavaMoradia/dados/moradia.mv.db`), então os cadastros feitos na 2.0.0 continuam válidos.

O H2 é a opção verificada. O script MySQL está alinhado ao modelo, mas não foi testado em um servidor MySQL real. Os aplicativos não têm assinatura comercial nem notarização e podem exigir autorização do Windows ou do macOS.
