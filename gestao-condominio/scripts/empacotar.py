#!/usr/bin/env python3
"""Gera uma entrega limpa com fontes, JAR e um banco H2 fechado."""
import argparse
from pathlib import Path
import shutil
import subprocess
import tempfile
import zipfile


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--incluir-banco', action='store_true',
                        help='inclui os registros do H2 em dados/condominio; feche a aplicação antes')
    args = parser.parse_args()
    raiz = Path(__file__).resolve().parents[1]
    jar = raiz / 'target/gestao-condominio.jar'
    if not jar.is_file():
        parser.error('Compile antes com ./mvnw package (Windows: mvnw.cmd package).')
    if args.incluir_banco and not (raiz / 'dados/condominio.mv.db').is_file():
        parser.error('Não existe banco H2 em dados/condominio.mv.db.')
    entrega = raiz / 'entrega'
    entrega.mkdir(exist_ok=True)
    destino = entrega / 'grupo_2_projeto.zip'

    with tempfile.TemporaryDirectory(prefix='golden-java-') as temporario:
        pacote = Path(temporario) / 'gestao-condominio'
        pacote.mkdir()
        for nome in ('src', 'docs', 'sql', 'scripts', '.mvn'):
            shutil.copytree(raiz / nome, pacote / nome)
        for nome in ('pom.xml', 'README.md', 'LICENSE', '.gitignore', 'mvnw', 'mvnw.cmd', 'iniciar.sh', 'iniciar.bat'):
            shutil.copy2(raiz / nome, pacote / nome)
        shutil.copy2(jar, pacote / 'gestao-condominio.jar')
        (pacote / 'dados').mkdir()
        url_pacote = 'jdbc:h2:' + str(pacote / 'dados/condominio')
        backup = pacote / 'sql/backup-h2.sql'
        if args.incluir_banco:
            # Exportar por JDBC garante um banco consistente e recusa o arquivo em uso.
            url_origem = 'jdbc:h2:' + str(raiz / 'dados/condominio') + ';IFEXISTS=TRUE'
            subprocess.run(['java', '-cp', str(jar), 'org.h2.tools.Script',
                            '-url', url_origem, '-user', 'sa', '-script', str(backup)], check=True)
            subprocess.run(['java', '-cp', str(jar), 'org.h2.tools.RunScript',
                            '-url', url_pacote, '-user', 'sa', '-script', str(backup)], check=True)
        else:
            subprocess.run(['java', '-Dbanco=h2', '-Ddb.url=' + url_pacote,
                            '-jar', str(jar), '--inicializar'], check=True)
            subprocess.run(['java', '-cp', str(jar), 'org.h2.tools.Script',
                            '-url', url_pacote, '-user', 'sa', '-script', str(backup)], check=True)

        # Gravar só depois de preparar o banco; falhas anteriores preservam o ZIP existente.
        with zipfile.ZipFile(destino, 'w', zipfile.ZIP_DEFLATED) as arquivo:
            for caminho in sorted(pacote.rglob('*')):
                if caminho.is_file():
                    arquivo.write(caminho, caminho.relative_to(pacote.parent))
    print('Entrega gerada:', destino)
    print('Banco incluído:', 'registros locais do H2' if args.incluir_banco else 'demonstração com caixa zerado')


if __name__ == '__main__':
    main()
