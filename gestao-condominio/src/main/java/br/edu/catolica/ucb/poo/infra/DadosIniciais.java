package br.edu.catolica.ucb.poo.infra;
import br.edu.catolica.ucb.poo.model.*;
public final class DadosIniciais {
 private DadosIniciais(){}
 public static void inicializar(Banco banco){banco.transacionar(em->{
  if(em.find(Condominio.class,1L)!=null)return null;
  em.persist(new Condominio("Residencial Golden Java"));
  var a=new Apartamento("A","101","G01");em.persist(a);
  em.persist(new Morador("Carlos","11111111111","61999990000",a));
  em.persist(new Funcionario("Marcos","Zelador"));return null;
 });}
}
