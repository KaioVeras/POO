package br.edu.catolica.ucb.poo.model;
public final class Validacao {
 private Validacao() {}
 public static String texto(String valor,String campo,int limite) {
  if(valor==null || valor.isBlank() || valor.trim().length()>limite) throw new IllegalArgumentException(campo+" é obrigatório e deve ter até "+limite+" caracteres.");
  return valor.trim();
 }
}
