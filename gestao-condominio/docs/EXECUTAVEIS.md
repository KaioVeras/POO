# Executar a versão 2

No projeto compilado ou ZIP completo: Windows — duplo clique em iniciar.bat; macOS/Linux — sh iniciar.sh. É necessário Java 17+ instalado. O banco é dados/moradia.mv.db, relativo à pasta do iniciador.

Para gerar um pacote com Java incluído, compile com o Maven e execute python scripts/empacotar_nativo.py no sistema de destino. Windows gera GoldenJava-2.0.0-windows-x64.zip; Mac gera o pacote correspondente ao processador. Extraia a pasta inteira. Windows: GoldenJava.exe. Mac: GoldenJava.app, que abre a TUI no Terminal.

No pacote Mac, o banco de uso fica em ~/Library/Application Support/GoldenJavaMoradia/dados/moradia.mv.db. No Windows fica na pasta dados do pacote. O Java incluído é específico de cada plataforma. Os pacotes não possuem assinatura comercial ou notarização.

Os downloads da release v1 são históricos e não contêm os novos cadastros. Não confunda arquivos antigos de entrega com o pacote gerado nesta versão.

O workflow condominio-publicar.yml é o publicador histórico da v1; não deve ser usado com os pacotes 2.0.0. O workflow de testes gera os pacotes atuais como artefatos do run.
