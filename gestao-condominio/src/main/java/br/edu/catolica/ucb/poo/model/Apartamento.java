package br.edu.catolica.ucb.poo.model;
import jakarta.persistence.*;
@Entity @Table(name="apartamentos",uniqueConstraints=@UniqueConstraint(columnNames={"bloco","numero"}))
public class Apartamento {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=20) private String bloco;
 @Column(nullable=false,length=20) private String numero;
 @Column(unique=true,length=20) private String vaga;
 protected Apartamento() {}
 public Apartamento(String bloco,String numero,String vaga) {
  this.bloco=Validacao.texto(bloco,"Bloco",20).toUpperCase(java.util.Locale.ROOT);
  this.numero=Validacao.texto(numero,"Número",20);
  this.vaga=vaga==null||vaga.isBlank()?null:Validacao.texto(vaga,"Vaga",20).toUpperCase(java.util.Locale.ROOT);
 }
 public Long getId(){return id;} public String getBloco(){return bloco;}
 public String getNumero(){return numero;} public String getVaga(){return vaga;}
 @Override public String toString(){return "ID "+id+" | Bloco "+bloco+" | Apartamento "+numero+" | Garagem: "+(vaga==null?"sem vaga":vaga);}
}
