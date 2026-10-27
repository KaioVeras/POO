package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.model.*;
import jakarta.persistence.*;
import java.util.List;

/** Consultas do projeto; a transação pertence ao serviço que coordena a operação. */
public class CondominioRepository {
    private final EntityManager em;

    public CondominioRepository(EntityManager em) { this.em = em; }

    public Condominio consultarCondominio() {
        return em.find(Condominio.class, 1L);
    }

    public Condominio bloquearCaixa() {
        // Dois recebimentos simultâneos não podem sobrescrever o saldo um do outro.
        return em.find(Condominio.class, 1L, LockModeType.PESSIMISTIC_WRITE);
    }

    public Unidade buscarUnidade(int numero) {
        Unidade unidade = em.find(Unidade.class, numero);
        if (unidade == null) throw new IllegalArgumentException("Unidade " + numero + " não encontrada.");
        return unidade;
    }

    public List<Unidade> listarUnidades() {
        return em.createQuery("select u from Unidade u order by u.numero", Unidade.class).getResultList();
    }

    public Morador buscarMorador(int numeroUnidade) {
        return em.createQuery("select m from Morador m join fetch m.unidade where m.unidade.numero = :numero", Morador.class)
                .setParameter("numero", numeroUnidade).getResultStream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Não há morador cadastrado nessa unidade."));
    }

    public List<Funcionario> listarFuncionarios() {
        return em.createQuery("select f from Funcionario f order by f.id", Funcionario.class).getResultList();
    }

    public List<MovimentacaoFinanceira> listarMovimentacoes() {
        return em.createQuery("select m from MovimentacaoFinanceira m order by m.id", MovimentacaoFinanceira.class).getResultList();
    }

    public void salvar(Object entidade) { em.persist(entidade); }
}
