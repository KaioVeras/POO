package br.edu.catolica.ucb.poo.view;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Locale;
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

    public double lerDecimal(String rotulo) {
        while (true) {
            String texto = lerTexto(rotulo);
            // Evita interpretar 1.000 (mil ou um?) e não aceita NaN/infinito.
            if (texto.matches("-?[0-9]{1,10}([,.][0-9]{1,2})?")) {
                return new BigDecimal(texto.replace(',', '.')).doubleValue();
            }
            mostrar("Digite um valor sem separador de milhar e com até duas casas. Exemplo: 500,00.");
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

    public String dinheiro(double valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", valor);
    }

    public static class FimDaEntrada extends RuntimeException { }
}
