package br.edu.catolica.ucb.poo.model;
import jakarta.persistence.*;
@Entity @Table(name="funcionarios")
public class Funcionario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String nome;
 @Column(nullable=false,length=50) private String cargo;
 protected Funcionario() {}
 public Funcionario(String nome,String cargo){this.nome=Validacao.texto(nome,"Nome",100);this.cargo=Validacao.texto(cargo,"Cargo",50);}
 public Long getId(){return id;} public String getCargo(){return cargo;}
 @Override public String toString(){return "ID "+id+" | "+nome+" | Cargo: "+cargo;}
}
