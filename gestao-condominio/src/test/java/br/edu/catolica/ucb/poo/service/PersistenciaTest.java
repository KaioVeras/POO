package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.infra.Banco;
import br.edu.catolica.ucb.poo.infra.DadosIniciais;
import br.edu.catolica.ucb.poo.model.Cobertura;
import br.edu.catolica.ucb.poo.model.Zelador;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class PersistenciaTest {
    @TempDir Path pasta;

    @Test void pagamentoEDespesaPersistemAposReabrirSemDuplicarDados() {
        String url = "jdbc:h2:" + pasta.resolve("condominio");
        try (Banco banco = Banco.abrirH2(url)) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            assertInstanceOf(Cobertura.class, service.consultarUnidade(201));
            assertTrue(service.listarFuncionarios().stream().anyMatch(f -> f instanceof Zelador));
            assertEquals(1625, service.simularArrecadacao());
            assertEquals(687.50, service.registrarPagamento(201, 10).getValor());
            service.registrarDespesa("Limpeza", 87.50);
            assertEquals(600, service.consultarCondominio().getSaldoCaixa());
        }
        try (Banco banco = Banco.abrirH2(url)) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            assertEquals(600, service.consultarCondominio().getSaldoCaixa());
            assertEquals(2, service.listarMovimentacoes().size());
            assertEquals(3, service.listarUnidades().size());
            assertEquals(2, service.listarFuncionarios().size());
            assertEquals("Carlos", service.consultarMorador(101).getNome());
        }
    }

    @Test void operacoesRecusadasNaoGravamLancamentoNemAlteramSaldo() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:recusas")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            assertThrows(IllegalArgumentException.class, () -> service.registrarDespesa("Compra", 1));
            assertThrows(IllegalArgumentException.class, () -> service.registrarPagamento(999, 0));
            assertThrows(IllegalArgumentException.class, () -> service.registrarPagamento(101, -1));
            assertEquals(0, service.consultarCondominio().getSaldoCaixa());
            assertTrue(service.listarMovimentacoes().isEmpty());
        }
    }

    @Test void erroDepoisDeAlterarCaixaDesfazTransacaoInteira() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:rollback")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            // Descrição excede limite do lançamento, depois do débito em memória.
            service.registrarPagamento(101, 0);
            assertThrows(IllegalArgumentException.class, () -> service.registrarDespesa("x".repeat(201), 100));
            assertEquals(500, service.consultarCondominio().getSaldoCaixa());
            assertEquals(1, service.listarMovimentacoes().size());
        }
    }
}
