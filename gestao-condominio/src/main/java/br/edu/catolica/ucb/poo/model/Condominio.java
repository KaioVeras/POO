package br.edu.catolica.ucb.poo.model;
import jakarta.persistence.*;
@Entity @Table(name="condominios")
public class Condominio {
 @Id private Long id=1L;
 @Column(nullable=false,length=100) private String nome;
 protected Condominio() {}
 public Condominio(String nome) {this.nome=Validacao.texto(nome,"Nome",100);}
 public String getNome(){return nome;}
}
