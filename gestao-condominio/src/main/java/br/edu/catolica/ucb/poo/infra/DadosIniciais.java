package br.edu.catolica.ucb.poo.infra;

import br.edu.catolica.ucb.poo.model.*;
import br.edu.catolica.ucb.poo.repository.CondominioRepository;

public final class DadosIniciais {
    private DadosIniciais() { }

    public static void inicializar(Banco banco) {
        banco.transacionar(em -> {
            CondominioRepository repository = new CondominioRepository(em);
            if (repository.consultarCondominio() != null) return null;

            repository.salvar(new Condominio("Residencial Golden Java", 2, 500, true));
            Unidade unidade101 = new Unidade(101, 500);
            Unidade unidade102 = new Unidade(102, 500, false);
            Unidade cobertura201 = new Cobertura(201, 500);
            repository.salvar(unidade101);
            repository.salvar(unidade102);
            repository.salvar(cobertura201);
            repository.salvar(new Morador("Carlos", "11111111111", unidade101));
            repository.salvar(new Morador("Ana", "22222222222", cobertura201));
            repository.salvar(new Funcionario("Paulo", 2500));
            repository.salvar(new Zelador("Marcos", 3000));
            return null;
        });
    }
}
