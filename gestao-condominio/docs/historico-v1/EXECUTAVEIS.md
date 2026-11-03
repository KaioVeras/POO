# Abrir por duplo clique — v1.0.0

## Qual arquivo baixar

Na página de Releases do repositório, abra a versão `condominio-v1.0.0` e a seção **Assets**:

| Download | Para quem |
|---|---|
| `grupo_2_projeto.zip` | Estudar o código, executar testes, recompilar ou usar MySQL. Requer Java 17+ instalado. |
| `GoldenJava-1.0.0-windows-x64.zip` | Windows Intel/AMD de 64 bits, com Java incluído. |
| `GoldenJava-1.0.0-macos-arm64.zip` | Mac com Apple Silicon (M1/M2/M3/M4 e posteriores), com Java incluído. |
| `GoldenJava-1.0.0-macos-x64.zip` | Mac Intel, com Java incluído. |

Não escolha os arquivos automáticos “Source code” se deseja apenas executar: eles contêm o repositório, sem o banco e o executável prontos.

## Windows

1. Baixe e use **Extrair tudo**. Não execute diretamente de dentro do ZIP.
2. Abra a pasta `GoldenJava` extraída.
3. Dê duplo clique em **GoldenJava.exe**.

O programa abre uma janela de console com o menu. O Java está dentro de `runtime/`. Não mova apenas o `.exe`: mantenha a pasta inteira, pois ele depende do runtime e do JAR que a acompanham.

## Mac

1. Extraia o ZIP correspondente ao processador do Mac.
2. Abra a pasta `GoldenJava` extraída.
3. Dê duplo clique em **GoldenJava.app** (o Finder pode mostrar só “GoldenJava”).

O aplicativo abre o Terminal e inicia a TUI com o Java incluído. Os registros de uso ficam em `~/Library/Application Support/GoldenJava/dados/condominio.mv.db`, uma pasta gravável do seu usuário. Assim, o banco não depende do local em que o macOS executa o aplicativo baixado. O banco em `dados/` no ZIP é uma cópia demonstrativa para consulta separada.

Esta distribuição educacional não tem certificado comercial de assinatura nem notarização Apple. O sistema operacional pode pedir confirmação ou impedir a abertura de um download não identificado. Não é um instalador. Em um computador da faculdade, a política da instituição pode exigir liberação do responsável; faça uma execução de teste antes da apresentação.

## Onde está o banco?

No Windows, o banco de uso está em `dados/condominio.mv.db`, dentro da pasta extraída. No Mac, fica em `~/Library/Application Support/GoldenJava/dados/condominio.mv.db`. Para levar os pagamentos a outro computador, encerre pela opção `0`, faça uma cópia de segurança e transfira esse arquivo para o diretório de dados da outra instalação. Não copie o banco enquanto o programa estiver aberto.

Os bancos dos pacotes Windows e Mac usam a mesma versão do H2. O banco pode ser transferido entre eles com ambos os programas fechados, mas mantenha cópia de segurança antes de substituir um arquivo existente. A v1 não sincroniza computadores e não tem importação automática.

O menu inclui consultas e movimentações financeiras. Ainda não permite cadastrar/editar/excluir unidades, moradores e funcionários. Consulte `docs/estado-do-projeto.md` para o escopo completo.

## Para quem mantém o projeto

`scripts/empacotar_nativo.py` deve rodar em cada plataforma com JDK completo. Usa `jlink` para preparar Java reduzido e `jpackage` no Windows para gerar o launcher com console. No Mac, um bundle `.app` abre o Terminal com o runtime incluído. `scripts/verificar_nativo.py` testa pagamentos, despesas e recuperação do saldo usando o binário distribuído.

O workflow `.github/workflows/condominio-release.yml`, na raiz do repositório, gera os pacotes no Windows e nos dois tipos de Mac. As plataformas de build seguem os [runners oficiais do GitHub Actions](https://docs.github.com/en/actions/reference/runners/github-hosted-runners).
