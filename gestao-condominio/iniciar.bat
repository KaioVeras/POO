@echo off
chcp 65001 >nul
cd /d "%~dp0"
if exist target\gestao-condominio.jar (
    java -Dfile.encoding=UTF-8 -jar target\gestao-condominio.jar %*
) else if exist gestao-condominio.jar (
    java -Dfile.encoding=UTF-8 -jar gestao-condominio.jar %*
) else (
    echo Compile primeiro: mvnw.cmd package
    exit /b 1
)
