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
            banco_padrao = pacote / 'dados/condominio.mv.db'
            if (Path(temporario) / 'dados').exists():
                raise AssertionError('O launcher criou banco relativo à pasta de quem o chamou.')
        else:
            banco_padrao = Path(ambiente['GOLDENJAVA_DATA_DIR']) / 'dados/condominio.mv.db'
        if not banco_padrao.is_file():
            raise AssertionError('Banco padrão não encontrado em ' + str(banco_padrao))

        ambiente['DB_URL'] = 'jdbc:h2:' + str(Path(temporario) / 'banco-financeiro')
        primeiro = executar('1\n6\n201\n10\ns\n7\nLimpeza\n87,50\ns\n0\n')
        if 'Pagamento registrado: R$ 687,50' not in primeiro:
            raise AssertionError('O executável não registrou o pagamento esperado:\n' + primeiro)
        segundo = executar('1\n10\n0\n')
        if 'Saldo do condomínio: R$ 600,00' not in segundo or 'PAGAMENTO' not in segundo:
            raise AssertionError('Saldo/histórico não foram recuperados após reabrir:\n' + segundo)
    print('OK: executável com runtime próprio, pagamento, despesa e persistência após reinício.')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('pasta', type=Path)
    verificar(parser.parse_args().pasta.resolve())
