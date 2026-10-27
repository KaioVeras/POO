package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.infra.*;
import br.edu.catolica.ucb.poo.service.GestaoService;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    @Test void falhaNaConsultaDepoisDoPagamentoNaoAfirmaQueEleFoiRecusado() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:falhaLeitura")) {
            DadosIniciais.inicializar(banco);
            GestaoService leituraComFalha = new GestaoService(banco) {
                @Override public br.edu.catolica.ucb.poo.model.Condominio consultarCondominio() {
                    throw new jakarta.persistence.PersistenceException("Conexão interrompida na consulta do saldo");
                }
            };
            String texto = executar(leituraComFalha, "6\n101\n0\ns\n0\n");
            assertEquals(500, new GestaoService(banco).consultarCondominio().getSaldoCaixa());
            assertFalse(texto.contains("operação não foi confirmada"));
            assertTrue(texto.contains("Confira o histórico"));
        }
    }

    @Test void menuRecuperaEntradaInvalidaERegistraOperacoesConfirmadas() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:menu")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            String texto = executar(service, "abc\n42\n4\n999\n6\n201\n10\ns\n7\nLimpeza\n87,50\ns\n9\n10\n0\n");
            assertEquals(600, service.consultarCondominio().getSaldoCaixa());
            assertEquals(2, service.listarMovimentacoes().size());
            assertTrue(texto.contains("687,50"));
            assertTrue(texto.contains("1625,00"));
            assertTrue(texto.contains("não encontrada"));
            assertTrue(texto.contains("Até logo"));
        }
    }

    @Test void fimDaEntradaECancelamentoNaoRegistramPagamento() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:cancelamento")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            executar(service, "6\n101\n0\nn\n0\n");
            executar(service, "6\n101\n0\n"); // EOF antes da confirmação.
            executar(service, "7\nCompra\n"); // EOF antes do valor.
            assertEquals(0, service.consultarCondominio().getSaldoCaixa());
            assertTrue(service.listarMovimentacoes().isEmpty());
        }
    }

    @Test void consultaDominiosESimulacaoNaoMovimentamCaixa() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:consultas")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            String texto = executar(service, "1\n2\n201\n3\n101\n4\n201\n5\n201\n10\n8\n9\n0\n");
            assertTrue(texto.contains("Carlos"));
            assertTrue(texto.contains("Marcos"));
            assertTrue(texto.contains("625,00"));
            assertEquals(0, service.consultarCondominio().getSaldoCaixa());
        }
    }

    private String executar(GestaoService service, String entrada) {
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        Console console = new Console(new Scanner(entrada), new PrintStream(saida, true, StandardCharsets.UTF_8));
        new MenuPrincipal(service, console).exibir();
        return saida.toString(StandardCharsets.UTF_8);
    }
}
