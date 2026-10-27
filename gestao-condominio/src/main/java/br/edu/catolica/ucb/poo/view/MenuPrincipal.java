package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.*;
import br.edu.catolica.ucb.poo.service.GestaoService;
import jakarta.persistence.PersistenceException;

public class MenuPrincipal {
    private final GestaoService service;
    private final Console console;

    public MenuPrincipal(GestaoService service, Console console) {
        this.service = service;
        this.console = console;
    }

    public void exibir() {
        try {
            int opcao;
            do {
                console.mostrar("\n=====================================");
                console.mostrar("RESIDENCIAL GOLDEN JAVA");
                console.mostrar("=====================================");
                console.mostrar("1 - Consultar condomínio\n2 - Consultar unidade\n3 - Consultar morador");
                console.mostrar("4 - Calcular taxa de unidade\n5 - Calcular taxa com multa");
                console.mostrar("6 - Registrar pagamento\n7 - Registrar despesa\n8 - Consultar funcionários");
                console.mostrar("9 - Simular arrecadação\n10 - Consultar histórico financeiro\n0 - Sair");
                opcao = console.lerInteiro("Opção: ");
                executar(opcao);
            } while (opcao != 0);
        } catch (Console.FimDaEntrada e) {
            console.mostrar("\nEntrada encerrada. Operação pendente cancelada. Até logo!");
        }
    }

    private void executar(int opcao) {
        try {
            switch (opcao) {
                case 1 -> consultarCondominio();
                case 2 -> consultarUnidade();
                case 3 -> console.mostrar(service.consultarMorador(lerNumeroUnidade()).toString());
                case 4 -> calcularTaxa(false);
                case 5 -> calcularTaxa(true);
                case 6 -> registrarPagamento();
                case 7 -> registrarDespesa();
                case 8 -> service.listarFuncionarios().forEach(f -> console.mostrar(f.toString()));
                case 9 -> simularArrecadacao();
                case 10 -> consultarHistorico();
                case 0 -> console.mostrar("Até logo!");
                default -> console.mostrar("Opção inválida.");
            }
        } catch (IllegalArgumentException e) {
            console.mostrar("Não foi possível realizar a operação: " + e.getMessage());
        } catch (PersistenceException e) {
            console.mostrar("Falha ao acessar o banco. Confira o histórico e o saldo antes de repetir uma operação financeira.");
        }
    }

    private int lerNumeroUnidade() {
        console.mostrar("Unidades disponíveis: 101, 102 e 201 (cobertura).");
        return console.lerInteiro("Número da unidade: ");
    }

    private void consultarCondominio() {
        Condominio condominio = service.consultarCondominio();
        console.mostrar("Nome: " + condominio.getNome());
        console.mostrar("Blocos: " + condominio.getBlocos());
        console.mostrar("Unidades cadastradas: " + service.listarUnidades().size());
        console.mostrar("Taxa padrão: " + console.dinheiro(condominio.getTaxaPadrao()) + " | " + condominio.classificarTaxa());
        console.mostrar("Portaria 24h: " + (condominio.isPortaria24h() ? "Sim" : "Não"));
        console.mostrar("Saldo do condomínio: " + console.dinheiro(condominio.getSaldoCaixa()));
    }

    private void consultarUnidade() {
        Unidade unidade = service.consultarUnidade(lerNumeroUnidade());
        console.mostrar(unidade.toString());
    }

    private void calcularTaxa(boolean comMulta) {
        Unidade unidade = service.consultarUnidade(lerNumeroUnidade());
        double multa = comMulta ? console.lerDecimal("Multa em % (10 representa 10%): ") : 0;
        console.mostrar("Valor calculado: " + console.dinheiro(unidade.calcularTaxa(multa)));
    }

    private void registrarPagamento() {
        int numero = lerNumeroUnidade();
        Unidade unidade = service.consultarUnidade(numero);
        double multa = console.lerDecimal("Multa em % (0 para nenhuma): ");
        console.mostrar("Pagamento da unidade " + numero + ": " + console.dinheiro(unidade.calcularTaxa(multa)));
        if (!console.confirmar("Confirmar pagamento")) {
            console.mostrar("Pagamento cancelado.");
            return;
        }
        MovimentacaoFinanceira movimento = service.registrarPagamento(numero, multa);
        console.mostrar("Pagamento registrado: " + console.dinheiro(movimento.getValor()));
        mostrarSaldo();
    }

    private void registrarDespesa() {
        String descricao = console.lerTexto("Descrição da despesa: ");
        double valor = console.lerDecimal("Valor: ");
        console.mostrar("Despesa: " + descricao + " | " + console.dinheiro(valor));
        if (!console.confirmar("Confirmar despesa")) {
            console.mostrar("Despesa cancelada.");
            return;
        }
        service.registrarDespesa(descricao, valor);
        console.mostrar("Despesa registrada.");
        mostrarSaldo();
    }

    private void mostrarSaldo() {
        console.mostrar("Saldo do condomínio: " + console.dinheiro(service.consultarCondominio().getSaldoCaixa()));
    }

    private void simularArrecadacao() {
        for (Unidade unidade : service.listarUnidades()) {
            console.mostrar("Unidade " + unidade.getNumero() + " - Taxa: " + console.dinheiro(unidade.calcularTaxa()));
        }
        console.mostrar("Arrecadação prevista: " + console.dinheiro(service.simularArrecadacao()));
        console.mostrar("Simulação considera todas as unidades, inclusive desocupadas, e não altera o caixa.");
    }

    private void consultarHistorico() {
        var movimentos = service.listarMovimentacoes();
        if (movimentos.isEmpty()) console.mostrar("Nenhuma movimentação registrada.");
        for (MovimentacaoFinanceira movimento : movimentos) {
            console.mostrar(movimento.getRegistradaEm().withNano(0) + " | " + movimento.getTipo()
                    + " | " + console.dinheiro(movimento.getValor()) + " | " + movimento.getDescricao());
        }
    }
}
