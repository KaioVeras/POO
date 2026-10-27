#!/usr/bin/env python3
"""Monta pacotes de execução com Java incluído, no próprio Windows ou macOS."""
import os
from pathlib import Path
import platform
import plistlib
import shutil
import subprocess
import tempfile
import zipfile


def ferramenta(nome):
    sufixo = '.exe' if platform.system() == 'Windows' else ''
    java_home = os.environ.get('JAVA_HOME')
    caminho = Path(java_home) / 'bin' / (nome + sufixo) if java_home else None
    if caminho and caminho.is_file():
        return str(caminho)
    encontrado = shutil.which(nome)
    if not encontrado:
        raise SystemExit('Ferramenta ausente no JDK: ' + nome)
    return encontrado


def main():
    sistema = platform.system()
    if sistema not in ('Windows', 'Darwin'):
        raise SystemExit('Gere o pacote no Windows ou macOS de destino.')
    raiz = Path(__file__).resolve().parents[1]
    jar = raiz / 'target/gestao-condominio.jar'
    if not jar.is_file():
        raise SystemExit('Compile primeiro com o Maven Wrapper: package.')
    arquitetura = platform.machine().lower()
    arquitetura = {'amd64': 'x64', 'x86_64': 'x64', 'aarch64': 'arm64'}.get(arquitetura, arquitetura)
    plataforma = 'windows' if sistema == 'Windows' else 'macos'
    entrega = raiz / 'entrega'
    entrega.mkdir(exist_ok=True)
    destino = entrega / ('GoldenJava-1.0.0-' + plataforma + '-' + arquitetura + '.zip')
    with tempfile.TemporaryDirectory(prefix='golden-java-native-') as temporario:
        area = Path(temporario)
        runtime = area / 'runtime'
        subprocess.run([ferramenta('jlink'), '--add-modules', 'java.se,jdk.crypto.ec,jdk.unsupported,jdk.charsets,jdk.localedata',
                        '--strip-debug', '--no-header-files', '--no-man-pages', '--output', str(runtime)], check=True)
        if sistema == 'Windows':
            entrada = area / 'entrada'
            entrada.mkdir()
            shutil.copy2(jar, entrada / jar.name)
            subprocess.run([ferramenta('jpackage'), '--type', 'app-image', '--name', 'GoldenJava',
                            '--app-version', '1.0.0', '--input', str(entrada), '--main-jar', jar.name,
                            '--runtime-image', str(runtime), '--dest', str(area / 'saida'), '--win-console',
                            '--java-options', '-Dfile.encoding=UTF-8',
                            '--java-options', '-Duser.dir=$APPDIR/..'], check=True)
            pacote = area / 'saida/GoldenJava'
        else:
            pacote = area / 'GoldenJava'
            recursos = pacote / 'GoldenJava.app/Contents/Resources'
            executaveis = pacote / 'GoldenJava.app/Contents/MacOS'
            recursos.mkdir(parents=True)
            executaveis.mkdir()
            shutil.move(str(runtime), recursos / 'runtime')
            shutil.copy2(jar, recursos / jar.name)
            iniciar = recursos / 'iniciar.command'
            iniciar.write_text('''#!/bin/sh
set -eu
RECURSOS="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
DIRETORIO_DADOS="${GOLDENJAVA_DATA_DIR:-$HOME/Library/Application Support/GoldenJava}"
mkdir -p "$DIRETORIO_DADOS"
cd "$DIRETORIO_DADOS"
exec "$RECURSOS/runtime/bin/java" -Dfile.encoding=UTF-8 -jar "$RECURSOS/gestao-condominio.jar" "$@"
''')
            iniciar.chmod(0o755)
            launcher = executaveis / 'GoldenJava'
            launcher.write_text('''#!/bin/sh
set -eu
EXECUTAVEIS="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
exec /usr/bin/open -a Terminal "$EXECUTAVEIS/../Resources/iniciar.command"
''')
            launcher.chmod(0o755)
            with (pacote / 'GoldenJava.app/Contents/Info.plist').open('wb') as arquivo:
                plistlib.dump({'CFBundleName': 'GoldenJava', 'CFBundleDisplayName': 'Residencial Golden Java',
                               'CFBundleIdentifier': 'br.edu.catolica.ucb.poo.goldenjava',
                               'CFBundleExecutable': 'GoldenJava', 'CFBundlePackageType': 'APPL',
                               'CFBundleShortVersionString': '1.0.0', 'CFBundleVersion': '1',
                               'LSMinimumSystemVersion': '11.0'}, arquivo)

        for nome in ('docs', 'sql'):
            shutil.copytree(raiz / nome, pacote / nome)
        shutil.copy2(raiz / 'README.md', pacote / 'README-projeto.md')
        shutil.copy2(raiz / 'LICENSE', pacote / 'LICENSE')
        shutil.copy2(raiz / 'docs/EXECUTAVEIS.md', pacote / 'LEIA-ME.md')
        (pacote / 'dados').mkdir()
        url = 'jdbc:h2:' + str(pacote / 'dados/condominio')
        subprocess.run([ferramenta('java'), '-Dbanco=h2', '-Ddb.url=' + url,
                        '-jar', str(jar), '--inicializar'], check=True)
        subprocess.run([ferramenta('java'), '-cp', str(jar), 'org.h2.tools.Script',
                        '-url', url, '-user', 'sa', '-script', str(pacote / 'sql/backup-h2.sql')], check=True)

        # O teste usa os binários e o Java incluídos, sem depender de Java no PATH.
        subprocess.run([os.sys.executable, str(raiz / 'scripts/verificar_nativo.py'), str(pacote)], check=True)
        if sistema == 'Darwin':
            # ditto preserva permissões e metadados do .app ao extrair no Finder.
            subprocess.run(['ditto', '-c', '-k', '--sequesterRsrc', '--keepParent', str(pacote), str(destino)], check=True)
        else:
            with zipfile.ZipFile(destino, 'w', zipfile.ZIP_DEFLATED) as arquivo:
                for caminho in sorted(pacote.rglob('*')):
                    if caminho.is_file():
                        arquivo.write(caminho, caminho.relative_to(pacote.parent))
    print('Pacote nativo criado:', destino)


if __name__ == '__main__':
    main()
