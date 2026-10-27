package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.infra.Banco;
import br.edu.catolica.ucb.poo.model.*;
import br.edu.catolica.ucb.poo.repository.CondominioRepository;
import java.math.BigDecimal;
import java.util.List;

public class GestaoService {
    private final Banco banco;

    public GestaoService(Banco banco) { this.banco = banco; }

    public Condominio consultarCondominio() {
        return banco.consultar(em -> new CondominioRepository(em).consultarCondominio());
    }

    public List<Unidade> listarUnidades() {
        return banco.consultar(em -> new CondominioRepository(em).listarUnidades());
    }

    public Unidade consultarUnidade(int numero) {
        return banco.consultar(em -> new CondominioRepository(em).buscarUnidade(numero));
    }

    public Morador consultarMorador(int numeroUnidade) {
        return banco.consultar(em -> new CondominioRepository(em).buscarMorador(numeroUnidade));
    }

    public List<Funcionario> listarFuncionarios() {
        return banco.consultar(em -> new CondominioRepository(em).listarFuncionarios());
    }

    public List<MovimentacaoFinanceira> listarMovimentacoes() {
        return banco.consultar(em -> new CondominioRepository(em).listarMovimentacoes());
    }

    public double simularArrecadacao() {
        BigDecimal total = BigDecimal.ZERO;
        for (Unidade unidade : listarUnidades()) {
            total = total.add(Valores.dinheiro(unidade.calcularTaxa()));
        }
        return total.doubleValue();
    }

    public MovimentacaoFinanceira registrarPagamento(int numeroUnidade, double percentualMulta) {
        return banco.transacionar(em -> {
            CondominioRepository repository = new CondominioRepository(em);
            Condominio condominio = repository.bloquearCaixa();
            Unidade unidade = repository.buscarUnidade(numeroUnidade);
            double valor = unidade.calcularTaxa(percentualMulta);
            condominio.receberPagamento(valor);
            MovimentacaoFinanceira movimento = new MovimentacaoFinanceira(
                    MovimentacaoFinanceira.Tipo.PAGAMENTO, valor,
                    "Taxa da unidade " + numeroUnidade + " (multa " + percentualMulta + "%)", unidade);
            repository.salvar(movimento);
            return movimento;
        });
    }

    public MovimentacaoFinanceira registrarDespesa(String descricao, double valor) {
        return banco.transacionar(em -> {
            CondominioRepository repository = new CondominioRepository(em);
            Condominio condominio = repository.bloquearCaixa();
            condominio.pagarDespesa(valor);
            MovimentacaoFinanceira movimento = new MovimentacaoFinanceira(
                    MovimentacaoFinanceira.Tipo.DESPESA, valor, descricao, null);
            repository.salvar(movimento);
            return movimento;
        });
    }
}
