package br.edu.catolica.ucb.poo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Conversões e validações pequenas compartilhadas pelo domínio. */
public final class Valores {
    private static final BigDecimal LIMITE = new BigDecimal("9999999999.99");

    private Valores() { }

    public static BigDecimal dinheiro(double valor) {
        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException("O valor deve ser finito e não negativo.");
        }
        return arredondar(BigDecimal.valueOf(valor));
    }

    private static BigDecimal arredondar(BigDecimal valor) {
        BigDecimal decimal = valor.setScale(2, RoundingMode.HALF_UP);
        if (decimal.compareTo(LIMITE) > 0) {
            throw new IllegalArgumentException("Valor acima do limite de R$ 9.999.999.999,99.");
        }
        return decimal;
    }

    public static BigDecimal positivo(double valor) {
        BigDecimal decimal = dinheiro(valor);
        if (decimal.signum() <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        return decimal;
    }

    public static double comAdicional(double base, double percentual) {
        if (!Double.isFinite(percentual) || percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("O percentual deve estar entre 0 e 100.");
        }
        BigDecimal fator = BigDecimal.ONE.add(BigDecimal.valueOf(percentual).movePointLeft(2));
        return arredondar(dinheiro(base).multiply(fator)).doubleValue();
    }

    public static String texto(String valor, String campo, int tamanho) {
        if (valor == null || valor.isBlank() || valor.trim().length() > tamanho) {
            throw new IllegalArgumentException(campo + " deve ter entre 1 e " + tamanho + " caracteres.");
        }
        return valor.trim();
    }
}
