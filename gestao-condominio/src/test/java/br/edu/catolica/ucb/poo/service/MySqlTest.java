package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.infra.*;
import br.edu.catolica.ucb.poo.model.Cobertura;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import static org.junit.jupiter.api.Assertions.*;

/** Rodar apenas em uma base MySQL descartável com os dados iniciais e caixa zerado. */
@EnabledIfEnvironmentVariable(named = "TESTAR_MYSQL", matches = "true")
class MySqlTest {
    @Test void validaScriptMySqlEOperacoesComReabertura() {
        try (Banco banco = Banco.abrirPadrao()) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            assertEquals(0, service.consultarCondominio().getSaldoCaixa());
            assertEquals(3, service.listarUnidades().size());
            assertInstanceOf(Cobertura.class, service.consultarUnidade(201));
            assertEquals(687.50, service.registrarPagamento(201, 10).getValor());
            service.registrarDespesa("Limpeza", 87.50);
            assertThrows(IllegalArgumentException.class, () -> service.registrarDespesa("Excesso", 601));
            assertThrows(IllegalArgumentException.class, () -> service.registrarDespesa("x".repeat(201), 100));
        }
        try (Banco banco = Banco.abrirPadrao()) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            assertEquals(600, service.consultarCondominio().getSaldoCaixa());
            assertEquals(2, service.listarMovimentacoes().size());
            assertEquals(3300, service.listarFuncionarios().get(1).calcularSalario());
            assertEquals("Carlos", service.consultarMorador(101).getNome());
        }
    }
}
