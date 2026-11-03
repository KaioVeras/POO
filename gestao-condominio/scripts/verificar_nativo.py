#!/usr/bin/env python3
"""Teste de execução real de um pacote nativo já extraído, em dados descartáveis."""
import argparse
import os
from pathlib import Path
import platform
import subprocess
import tempfile


def verificar(pacote):
    if platform.system() == 'Windows':
        comando = [str(pacote / 'GoldenJava.exe')]
    else:
        comando = ['sh', str(pacote / 'GoldenJava.app/Contents/Resources/iniciar.command')]
    with tempfile.TemporaryDirectory(prefix='golden-java-smoke-') as temporario:
        ambiente = os.environ.copy()
        ambiente['BANCO'] = 'h2'
        ambiente.pop('DB_URL', None)
        ambiente.pop('DB_USER', None)
        ambiente.pop('DB_PASSWORD', None)
        if platform.system() == 'Darwin':
            ambiente['GOLDENJAVA_DATA_DIR'] = str(Path(temporario) / 'dados-mac')

        def executar(entrada):
            resultado = subprocess.run(comando, input=entrada.encode('utf-8'), capture_output=True,
                                       cwd=temporario, env=ambiente, timeout=60, check=True)
            return resultado.stdout.decode('utf-8', errors='replace')

        # Executar de outra pasta SEM DB_URL testa a localização padrão do banco.
        executar('1\n0\n')
        if platform.system() == 'Windows':
            banco_padrao = pacote / 'dados/moradia.mv.db'
            if (Path(temporario) / 'dados').exists():
                raise AssertionError('O launcher criou banco relativo à pasta de quem o chamou.')
        else:
            banco_padrao = Path(ambiente['GOLDENJAVA_DATA_DIR']) / 'dados/moradia.mv.db'
        if not banco_padrao.is_file():
            raise AssertionError('Banco padrão não encontrado em ' + str(banco_padrao))

        ambiente['DB_URL'] = 'jdbc:h2:' + str(Path(temporario) / 'teste-moradia')
        primeiro = executar('2\n1\nAna\n22222222222\n61999998888\n1\n0\n3\n1\n2\nCaixa de livros\n0\n0\n')
        if 'Morador cadastrado:' not in primeiro or 'Encomenda recebida:' not in primeiro:
            raise AssertionError(primeiro)
        segundo = executar('2\n3\n2\n0\n3\n3\n1\ns\n0\n0\n')
        if 'Encomendas pendentes: 1' not in segundo or 'Retirada registrada.' not in segundo:
            raise AssertionError(segundo)
        terceiro = executar('2\n3\n2\n0\n0\n')
        if 'Encomendas pendentes: 0' not in terceiro:
            raise AssertionError(terceiro)
    print('OK: cadastro, ficha, recebimento, retirada e persistência no pacote nativo.')



if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('pasta', type=Path)
    verificar(parser.parse_args().pasta.resolve())
