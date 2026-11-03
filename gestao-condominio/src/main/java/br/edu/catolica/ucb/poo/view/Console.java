package br.edu.catolica.ucb.poo.view;

import java.io.PrintStream;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;
    private final PrintStream saida;
    private final boolean limparTela;

    /** Sem limpeza de tela: usado nos testes, que leem a saída como texto. */
    public Console(Scanner scanner, PrintStream saida) {
        this(scanner, saida, false);
    }

    public Console(Scanner scanner, PrintStream saida, boolean limparTela) {
        this.scanner = scanner;
        this.saida = saida;
        this.limparTela = limparTela;
    }

    public void mostrar(String texto) { saida.println(texto); }

    /** Apaga o que está no terminal para mostrar só a próxima tela. */
    public void limparTela() {
        if (!limparTela) return;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                // Se não for possível limpar, o sistema continua normalmente.
            }
        } else {
            // Códigos ANSI entendidos pelos terminais do macOS e do Linux.
            saida.print("\033[H\033[2J\033[3J");
            saida.flush();
        }
    }

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
