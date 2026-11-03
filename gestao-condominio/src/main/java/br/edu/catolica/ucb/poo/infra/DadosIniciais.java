package br.edu.catolica.ucb.poo.infra;

import br.edu.catolica.ucb.poo.model.*;

/**
 * Cria os dados de demonstração na primeira abertura do banco.
 * Se o condomínio já existe, não faz nada, para não duplicar registros.
 */
public final class DadosIniciais {

    private DadosIniciais() { }

    public static void inicializar(Banco banco) {
        banco.transacionar(em -> {
            if (em.find(Condominio.class, 1L) != null) {
                return null;
            }
            em.persist(new Condominio("Residencial Golden Java"));
            Apartamento apartamento = new Apartamento("A", "101", "G01");
            em.persist(apartamento);
            em.persist(new Morador("Carlos", "11111111111", "61999990000", apartamento));
            em.persist(new Funcionario("Marcos", "Zelador"));
            return null;
        });
    }
}
