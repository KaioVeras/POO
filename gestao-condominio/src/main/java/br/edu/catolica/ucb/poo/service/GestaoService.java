package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.infra.Banco;
import br.edu.catolica.ucb.poo.model.*;
import br.edu.catolica.ucb.poo.repository.CondominioRepository;
import java.util.List;

/**
 * Operações que o menu oferece. Cada método:
 * 1. pede ao Banco uma consulta ou uma transação;
 * 2. usa o repositório para buscar e gravar;
 * 3. aplica as regras que dependem de outros registros (duplicidades e vínculos).
 * Se algo falhar dentro de uma transação, o Banco desfaz tudo (rollback).
 */
public class GestaoService {

    private final Banco banco;

    public GestaoService(Banco banco) {
        this.banco = banco;
    }

    // ---------- Consultas ----------

    public Condominio consultarCondominio() {
        return banco.consultar(em -> new CondominioRepository(em).buscar(Condominio.class, 1));
    }

    public List<Apartamento> listarApartamentos() {
        return banco.consultar(em -> new CondominioRepository(em).listar(Apartamento.class));
    }

    public List<Morador> listarMoradores() {
        return banco.consultar(em -> new CondominioRepository(em).listar(Morador.class));
    }

    public List<Funcionario> listarFuncionarios() {
        return banco.consultar(em -> new CondominioRepository(em).listar(Funcionario.class));
    }

    public Morador consultarMorador(long id) {
        return banco.consultar(em -> new CondominioRepository(em).buscar(Morador.class, id));
    }

    public List<Encomenda> listarPendentes() {
        return banco.consultar(em -> new CondominioRepository(em).encomendasPendentes());
    }

    public List<Encomenda> encomendasPendentes(long moradorId) {
        return banco.consultar(em -> {
            CondominioRepository repositorio = new CondominioRepository(em);
            repositorio.buscar(Morador.class, moradorId); // confirma que o morador existe
            return repositorio.encomendasDoMorador(moradorId, true);
        });
    }

    public List<Encomenda> historicoEncomendas(long moradorId) {
        return banco.consultar(em -> {
            CondominioRepository repositorio = new CondominioRepository(em);
            repositorio.buscar(Morador.class, moradorId);
            return repositorio.encomendasDoMorador(moradorId, false);
        });
    }

    // ---------- Cadastros e alterações ----------

    public Apartamento cadastrarApartamento(String bloco, String numero, String vaga) {
        return banco.transacionar(em -> {
            CondominioRepository repositorio = new CondominioRepository(em);
            Apartamento apartamento = new Apartamento(bloco, numero, vaga);
            if (repositorio.existeApartamento(apartamento.getBloco(), apartamento.getNumero())) {
                throw new IllegalArgumentException("Apartamento já cadastrado nesse bloco.");
            }
            if (apartamento.getVaga() != null && repositorio.existeVaga(apartamento.getVaga())) {
                throw new IllegalArgumentException("Vaga já vinculada a outro apartamento.");
            }
            repositorio.salvar(apartamento);
            return apartamento;
        });
    }

    public Morador cadastrarMorador(String nome, String cpf, String telefone, long apartamentoId) {
        return banco.transacionar(em -> {
            CondominioRepository repositorio = new CondominioRepository(em);
            Apartamento apartamento = repositorio.buscar(Apartamento.class, apartamentoId);
            Morador morador = new Morador(nome, cpf, telefone, apartamento);
            if (repositorio.existeCpf(cpf)) {
                throw new IllegalArgumentException("CPF já cadastrado.");
            }
            repositorio.salvar(morador);
            return morador;
        });
    }

    public Funcionario cadastrarFuncionario(String nome, String cargo) {
        return banco.transacionar(em -> {
            Funcionario funcionario = new Funcionario(nome, cargo);
            new CondominioRepository(em).salvar(funcionario);
            return funcionario;
        });
    }

    public Encomenda receberEncomenda(long moradorId, String descricao) {
        return banco.transacionar(em -> {
            CondominioRepository repositorio = new CondominioRepository(em);
            Encomenda encomenda = new Encomenda(repositorio.buscar(Morador.class, moradorId), descricao);
            repositorio.salvar(encomenda);
            return encomenda;
        });
    }

    /** A alteração é salva no commit, pois o JPA acompanha o objeto buscado na transação. */
    public void retirarEncomenda(long id) {
        banco.transacionar(em -> {
            Encomenda encomenda = new CondominioRepository(em).buscar(Encomenda.class, id);
            encomenda.retirar();
            return null;
        });
    }
}
