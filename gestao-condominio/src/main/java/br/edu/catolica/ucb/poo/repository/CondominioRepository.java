package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.model.Encomenda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Único ponto do sistema que consulta e grava no banco por meio do JPA.
 * Recebe o EntityManager já aberto pelo Banco; não abre nem fecha transações.
 */
public class CondominioRepository {

    private final EntityManager em;

    public CondominioRepository(EntityManager em) {
        this.em = em;
    }

    /** Busca qualquer entidade pelo ID. Lança erro se o registro não existir. */
    public <T> T buscar(Class<T> tipo, long id) {
        T objeto = em.find(tipo, id);
        if (objeto == null) {
            throw new IllegalArgumentException(tipo.getSimpleName() + " com ID " + id + " não existe.");
        }
        return objeto;
    }

    /** Lista todos os registros de uma entidade, em ordem de ID. */
    public <T> List<T> listar(Class<T> tipo) {
        String jpql = "select e from " + tipo.getSimpleName() + " e order by e.id";
        return em.createQuery(jpql, tipo).getResultList();
    }

    public void salvar(Object objeto) {
        em.persist(objeto);
    }

    public boolean existeApartamento(String bloco, String numero) {
        return em.createQuery(
                        "select count(a) from Apartamento a where a.bloco = :bloco and a.numero = :numero", Long.class)
                .setParameter("bloco", bloco)
                .setParameter("numero", numero)
                .getSingleResult() > 0;
    }

    public boolean existeVaga(String vaga) {
        return em.createQuery("select count(a) from Apartamento a where a.vaga = :vaga", Long.class)
                .setParameter("vaga", vaga)
                .getSingleResult() > 0;
    }

    public boolean existeCpf(String cpf) {
        return em.createQuery("select count(m) from Morador m where m.cpf = :cpf", Long.class)
                .setParameter("cpf", cpf)
                .getSingleResult() > 0;
    }

    /** Encomendas de todos os moradores, somente as que ainda não foram retiradas. */
    public List<Encomenda> encomendasPendentes() {
        return em.createQuery(
                        "select e from Encomenda e where e.retiradaEm is null order by e.id", Encomenda.class)
                .getResultList();
    }

    /** Encomendas de um morador: todas (histórico) ou apenas as pendentes. */
    public List<Encomenda> encomendasDoMorador(long moradorId, boolean apenasPendentes) {
        String jpql = "select e from Encomenda e where e.morador.id = :morador";
        if (apenasPendentes) {
            jpql += " and e.retiradaEm is null";
        }
        TypedQuery<Encomenda> consulta = em.createQuery(jpql + " order by e.id", Encomenda.class);
        return consulta.setParameter("morador", moradorId).getResultList();
    }
}
