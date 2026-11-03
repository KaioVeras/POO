package br.edu.catolica.ucb.poo.view;

import java.io.PrintStream;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;
    private final PrintStream saida;

    public Console(Scanner scanner, PrintStream saida) {
        this.scanner = scanner;
        this.saida = saida;
    }

    public void mostrar(String texto) { saida.println(texto); }

    public String lerTexto(String rotulo) {
        saida.print(rotulo);
        if (!scanner.hasNextLine()) throw new FimDaEntrada();
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String rotulo) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(rotulo));
            } catch (NumberFormatException e) {
                mostrar("Digite um número inteiro.");
            }
        }
    }

    public boolean confirmar(String rotulo) {
        while (true) {
            String resposta = lerTexto(rotulo + " (s/n): ");
            if (resposta.equalsIgnoreCase("s")) return true;
            if (resposta.equalsIgnoreCase("n")) return false;
            mostrar("Responda s ou n.");
        }
    }

    public static class FimDaEntrada extends RuntimeException { }
}
