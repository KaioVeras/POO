package br.edu.catolica.ucb.poo.model;
import jakarta.persistence.*;
@Entity @Table(name="moradores")
public class Morador {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String nome;
 @Column(nullable=false,unique=true,length=11) private String cpf;
 @Column(nullable=false,length=30) private String telefone;
 @ManyToOne(optional=false) @JoinColumn(name="apartamento_id",nullable=false) private Apartamento apartamento;
 protected Morador() {}
 public Morador(String nome,String cpf,String telefone,Apartamento apartamento) {
  this.nome=Validacao.texto(nome,"Nome",100);
  if(cpf==null || !cpf.matches("[0-9]{11}")) throw new IllegalArgumentException("CPF deve ter 11 dígitos, sem pontuação.");
  if(apartamento==null) throw new IllegalArgumentException("Apartamento obrigatório.");
  this.cpf=cpf;this.telefone=Validacao.texto(telefone,"Telefone",30);this.apartamento=apartamento;
 }
 public Long getId(){return id;} public String getNome(){return nome;} public String getCpf(){return cpf;}
 public String getTelefone(){return telefone;} public Apartamento getApartamento(){return apartamento;}
 @Override public String toString(){return "ID "+id+" | "+nome+" | CPF: "+cpf+" | Telefone: "+telefone+" | "+apartamento;}
}
