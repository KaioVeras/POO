# Publicação v1 e execução por duplo clique

Pedido aprovado: publicar a fase inicial no GitHub, criar uma release com ZIP completo e executáveis para Windows e Mac. Não alterar as regras do sistema nem acrescentar cadastros nesta entrega.

## Entregas

- Documentação do escopo atual, decisões, pendências, arquitetura, conexão, H2 e apresentação.
- ZIP completo: fonte, JAR, referências, dados fictícios e scripts SQL.
- Windows x64: app-image com GoldenJava.exe em modo console e Java Temurin 17 incluído.
- macOS Apple Silicon e Intel: GoldenJava.app que abre a TUI no Terminal, com runtime incluído e dados em Application Support do usuário.
- Pipeline de build e testes em cada sistema no GitHub Actions. Release só publica os artefatos após todos os builds e testes.

## Verificação

1. Executar a suíte Java local, antes do commit.
2. Executar o teste de pacote nativo antes de existir o pacote, confirmando que detecta a ausência do executável.
3. Implementar o empacotador e rodar o teste contra o runtime incluído, em banco temporário.
4. Revisar workflow e documentação, fazer commit e push da branch autorizada.
5. Executar os três builds no GitHub. Cada build extrai o ZIP, executa a TUI e confere saldo e histórico após reinício.
6. Integrar a v1 no repositório e publicar release no commit verificado, com downloads reais.

## Limites

Executáveis não possuem certificado comercial nem notarização Apple. Não há instalador nem interface gráfica nova: o duplo clique abre a TUI. H2 é local (Windows: pasta extraída; Mac: Application Support), e MySQL continua opcional. Não existe um único binário para todos os sistemas/arquiteturas.
