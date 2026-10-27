# Residencial Golden Java v1.0.0 — fase inicial

Primeira versão funcional do projeto de POO do Grupo 02. A aplicação mantém uma interface de texto e demonstra encapsulamento, herança, polimorfismo, persistência JPA e transações financeiras.

## Downloads

- **grupo_2_projeto.zip**: projeto completo, fontes, testes, guias de estudo, anexos, JAR, banco H2 demonstrativo e scripts SQL. Para executar esta opção, requer Java 17 ou superior.
- **GoldenJava-1.0.0-windows-x64.zip**: extraia tudo e dê duplo clique em GoldenJava.exe. Inclui Java; não exige instalação de Java, Maven ou MySQL.
- **GoldenJava-1.0.0-macos-arm64.zip**: Mac Apple Silicon. Extraia e abra GoldenJava.app; ele inicia a TUI no Terminal com Java incluído.
- **GoldenJava-1.0.0-macos-x64.zip**: Mac Intel, com o mesmo funcionamento.

Os arquivos automáticos “Source code” do GitHub contêm somente o repositório. Para a entrega pronta, use os ZIPs acima.

## O que está pronto

Consultas de condomínio, unidades, moradores e funcionários; taxas e multas; adicional da cobertura e do zelador; pagamentos e despesas com confirmação; proteção contra saldo insuficiente; histórico financeiro; simulação de arrecadação; persistência H2 e MySQL.

Os dados cadastrais desta v1 são demonstrativos e pré-cadastrados. Cadastro, edição e exclusão são a próxima fase. Não há login, visitantes, manutenção nem cobrança por competência; cada pagamento confirmado é um novo recebimento.

## Banco e documentação

Os downloads incluem banco H2 demonstrativo e SQL MySQL. H2 é um banco relacional embutido que guarda os registros em arquivo. No Windows, os registros ficam na pasta dados do pacote; no Mac, em ~/Library/Application Support/GoldenJava/dados. MySQL é opcional e exige um servidor configurado separadamente.

Leia o README e os documentos estado-do-projeto, guia-de-estudo, banco-de-dados e EXECUTAVEIS. Eles explicam a estrutura, os motivos das decisões, o caminho de uma operação, a conexão com o banco e o que falta implementar.

## Validação e limites

Testes automatizados das regras, do menu e da persistência; builds com Java 17 em Windows, Mac Apple Silicon e Mac Intel; testes dos executáveis e de persistência após extrair os ZIPs. MySQL 8.4 também foi validado separadamente em um servidor temporário durante o desenvolvimento inicial.

Os executáveis não possuem certificado comercial de assinatura/notarização. Windows/macOS podem exigir confirmação ou autorização conforme a política da máquina. Extraia a pasta inteira, mantenha seus arquivos juntos e encerre pela opção 0 antes de copiar o banco.
