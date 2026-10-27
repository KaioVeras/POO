package br.edu.catolica.ucb.poo.distribuicao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class IniciadorTest {
    @TempDir Path pasta;

    @Test @EnabledOnOs({OS.MAC, OS.LINUX})
    void executaJarNovoAposRecompilarUmaEntrega() throws Exception {
        Files.copy(Path.of("iniciar.sh"), pasta.resolve("iniciar.sh"));
        Files.createDirectories(pasta.resolve("target"));
        Files.writeString(pasta.resolve("gestao-condominio.jar"), "antigo");
        Files.writeString(pasta.resolve("target/gestao-condominio.jar"), "novo");
        Path javaFalso = pasta.resolve("java");
        Files.writeString(javaFalso, "#!/bin/sh\ncase \"$*\" in *target/gestao-condominio.jar*) echo NOVO;; *) echo ANTIGO;; esac\n");
        assertTrue(javaFalso.toFile().setExecutable(true));
        ProcessBuilder builder = new ProcessBuilder("sh", pasta.resolve("iniciar.sh").toString());
        builder.environment().put("PATH", pasta + ":" + System.getenv("PATH"));
        Process processo = builder.start();
        String saida = new String(processo.getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        assertEquals(0, processo.waitFor());
        assertEquals("NOVO", saida.trim());
    }
}
