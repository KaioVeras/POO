package br.edu.catolica.ucb.poo.repository;
import jakarta.persistence.*;
import java.util.List;
import br.edu.catolica.ucb.poo.model.*;
public class CondominioRepository {
 private final EntityManager em;
 public CondominioRepository(EntityManager em){this.em=em;}
 public <T> T buscar(Class<T> tipo,long id){T objeto=em.find(tipo,id);if(objeto==null)throw new IllegalArgumentException(tipo.getSimpleName()+" não encontrado: "+id);return objeto;}
 public <T> List<T> listar(Class<T> tipo){return em.createQuery("select e from "+tipo.getSimpleName()+" e order by e.id",tipo).getResultList();}
 public void salvar(Object objeto){em.persist(objeto);}
 public boolean existeApartamento(String bloco,String numero){return em.createQuery("select count(a) from Apartamento a where a.bloco=:b and a.numero=:n",Long.class).setParameter("b",bloco).setParameter("n",numero).getSingleResult()>0;}
 public boolean existeVaga(String vaga){return em.createQuery("select count(a) from Apartamento a where a.vaga=:v",Long.class).setParameter("v",vaga).getSingleResult()>0;}
 public boolean existeCpf(String cpf){return em.createQuery("select count(m) from Morador m where m.cpf=:c",Long.class).setParameter("c",cpf).getSingleResult()>0;}
 public List<Encomenda> encomendas(Long moradorId,boolean apenasPendentes){
  String jpql="select e from Encomenda e where 1=1";
  if(moradorId!=null)jpql+=" and e.morador.id=:m";
  if(apenasPendentes)jpql+=" and e.retiradaEm is null";
  var query=em.createQuery(jpql+" order by e.id",Encomenda.class);
  if(moradorId!=null)query.setParameter("m",moradorId);
  return query.getResultList();
 }
}
