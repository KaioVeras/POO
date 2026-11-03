package br.edu.catolica.ucb.poo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="encomendas")
public class Encomenda {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="morador_id",nullable=false) private Morador morador;
 @Column(nullable=false,length=200) private String descricao;
 @Column(name="recebida_em",nullable=false) private LocalDateTime recebidaEm;
 @Column(name="retirada_em") private LocalDateTime retiradaEm;
 protected Encomenda() {}
 public Encomenda(Morador morador,String descricao) {
  if(morador==null) throw new IllegalArgumentException("Morador obrigatório.");
  this.morador=morador;this.descricao=Validacao.texto(descricao,"Descrição",200);this.recebidaEm=LocalDateTime.now().withNano(0);
 }
 public Long getId(){return id;} public LocalDateTime getRetiradaEm(){return retiradaEm;}
 public boolean isPendente(){return retiradaEm==null;}
 public void retirar(){if(!isPendente())throw new IllegalArgumentException("Encomenda já retirada.");retiradaEm=LocalDateTime.now().withNano(0);}
 @Override public String toString(){return "ID "+id+" | "+morador.getNome()+" | Bloco "+morador.getApartamento().getBloco()+" / Apt "+morador.getApartamento().getNumero()+" | "+descricao+" | Recebida: "+recebidaEm+" | "+(isPendente()?"PENDENTE":"Retirada: "+retiradaEm);}
}
