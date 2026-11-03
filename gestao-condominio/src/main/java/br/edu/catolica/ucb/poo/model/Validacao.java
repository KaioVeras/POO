package br.edu.catolica.ucb.poo.model;

/**
 * Validação de texto compartilhada pelas entidades.
 * A classe é final e o construtor é privado porque ela só tem um método estático.
 */
public final class Validacao {

    private Validacao() { }

    /**
     * Confere se o texto foi preenchido e respeita o limite de caracteres.
     * Devolve o texto sem espaços nas pontas.
     */
    public static String texto(String valor, String campo, int limite) {
        if (valor == null || valor.isBlank() || valor.trim().length() > limite) {
            throw new IllegalArgumentException(
                    "O campo " + campo + " é obrigatório e deve ter até " + limite + " caracteres.");
        }
        return valor.trim();
    }
}
