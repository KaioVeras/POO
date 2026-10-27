#!/bin/sh
set -eu
cd "$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
if [ -f target/gestao-condominio.jar ]; then
    exec java -Dfile.encoding=UTF-8 -jar target/gestao-condominio.jar "$@"
elif [ -f gestao-condominio.jar ]; then
    exec java -Dfile.encoding=UTF-8 -jar gestao-condominio.jar "$@"
else
    echo 'Compile primeiro: ./mvnw package'
    exit 1
fi
