# Executáveis da versão 2.1

## Qual arquivo baixar

Baixe na [release 2.1.0](https://github.com/KaioVeras/POO/releases/tag/condominio-v2.1.0), na seção Assets:

| Arquivo | Uso |
| --- | --- |
| `GoldenJava-2.1.0-windows-x64.zip` | Windows 64 bits (Intel/AMD). Extraia e abra `GoldenJava.exe`. |
| `GoldenJava-2.1.0-macos-arm64.zip` | Mac Apple Silicon (M1 ou mais recente). Extraia e abra `GoldenJava.app`. |
| `GoldenJava-2.1.0-macos-x64.zip` | Mac Intel. Extraia e abra `GoldenJava.app`. |
| `grupo_2_projeto.zip` | Código, testes, documentação, banco e JAR. Requer Java 17 ou superior instalado. |

Os três pacotes nativos já incluem o Java: não é preciso instalar Java, Maven ou MySQL. Extraia a pasta inteira antes de abrir. O aplicativo para Mac abre o sistema no Terminal.

Os aplicativos não têm assinatura comercial nem notarização, então o Windows ou o macOS podem pedir autorização para abri-los. O arquivo `SHA256SUMS.txt` permite conferir a integridade dos quatro ZIPs.

## Onde ficam os dados

| Forma de execução | Arquivo do banco |
| --- | --- |
| Windows (pacote nativo) | `dados/moradia.mv.db`, dentro da pasta do pacote |
| Mac (pacote nativo) | `~/Library/Application Support/GoldenJavaMoradia/dados/moradia.mv.db` |
| Código-fonte ou ZIP completo | `dados/moradia.mv.db`, na pasta do iniciador |

## Executar pelo ZIP completo ou pelo código

No Windows, dê duplo clique em `iniciar.bat`. No macOS ou Linux, execute `sh iniciar.sh`. É necessário Java 17 ou superior instalado.

## Gerar os pacotes

1. Compile com `./mvnw package` (Windows: `mvnw.cmd package`).
2. Execute `python3 scripts/empacotar_nativo.py` no sistema de destino. O Windows gera o pacote Windows; o Mac gera o pacote do seu processador.
3. Para conferir um pacote extraído, use `python3 scripts/verificar_nativo.py <pasta-extraída>`.

O workflow `.github/workflows/condominio-release.yml` faz esses passos automaticamente no GitHub Actions para Windows, Mac Intel e Mac Apple Silicon, e guarda os ZIPs como artefatos da execução.

A release 2.0.0 e a release v1 continuam publicadas como histórico. Use sempre a release mais recente.
