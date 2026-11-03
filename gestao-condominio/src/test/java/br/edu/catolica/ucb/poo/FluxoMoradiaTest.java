package br.edu.catolica.ucb.poo;

import static org.junit.jupiter.api.Assertions.*;

import br.edu.catolica.ucb.poo.infra.Banco;
import br.edu.catolica.ucb.poo.infra.DadosIniciais;
import br.edu.catolica.ucb.poo.model.Apartamento;
import br.edu.catolica.ucb.poo.model.Encomenda;
import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.service.GestaoService;
import br.edu.catolica.ucb.poo.view.Console;
import br.edu.catolica.ucb.poo.view.MenuPrincipal;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Testes do fluxo principal. Cada teste usa um banco H2 descartável
 * (em memória ou numa pasta temporária), sem tocar nos dados reais.
 */
class FluxoMoradiaTest {

    @TempDir
    Path pasta;

    @Test
    void cadastroEncomendaERetiradaPersistem() {
        String url = "jdbc:h2:" + pasta.resolve("moradia");
        long moradorId;

        try (Banco banco = Banco.abrirH2(url)) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            Apartamento apartamento = service.cadastrarApartamento("B", "102", "G02");
            Morador morador = service.cadastrarMorador("Ana", "22222222222", "61999998888", apartamento.getId());
            moradorId = morador.getId();
            Encomenda encomenda = service.receberEncomenda(moradorId, "Caixa de livros");

            assertEquals(1, service.encomendasPendentes(moradorId).size());
            assertEquals("G02", service.consultarMorador(moradorId).getApartamento().getVaga());

            service.retirarEncomenda(encomenda.getId());
            assertTrue(service.encomendasPendentes(moradorId).isEmpty());
            assertThrows(IllegalArgumentException.class, () -> service.retirarEncomenda(encomenda.getId()));
        }

        // Reabre o mesmo arquivo: os dados devem continuar lá, sem duplicar os iniciais.
        try (Banco banco = Banco.abrirH2(url)) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            assertEquals("Ana", service.consultarMorador(moradorId).getNome());
            assertEquals(2, service.listarMoradores().size());
            assertEquals(1, service.historicoEncomendas(moradorId).size());
            assertNotNull(service.historicoEncomendas(moradorId).get(0).getRetiradaEm());
        }
    }

    @Test
    void validaDuplicidadesEVinculosSemGravarParcialmente() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:validacao")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);

            assertThrows(IllegalArgumentException.class, () -> service.cadastrarApartamento("A", "101", ""));
            assertThrows(IllegalArgumentException.class, () -> service.cadastrarApartamento("B", "101", "G01"));
            assertThrows(IllegalArgumentException.class,
                    () -> service.cadastrarMorador("Outro", "11111111111", "61999999999", 1));
            assertThrows(IllegalArgumentException.class,
                    () -> service.cadastrarMorador("Ana", "22222222222", "61999999999", 999));
            assertThrows(IllegalArgumentException.class, () -> service.receberEncomenda(999, "Caixa"));
            assertThrows(IllegalArgumentException.class, () -> service.receberEncomenda(1, " "));

            assertEquals(1, service.listarMoradores().size());
            assertTrue(service.listarPendentes().isEmpty());
        }
    }

    @Test
    void apartamentosPermitemMesmoNumeroEmBlocosDiferentesESemVaga() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:blocos")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            assertNull(service.cadastrarApartamento("B", "101", "").getVaga());
            assertNull(service.cadastrarApartamento("C", "101", "").getVaga());
            assertEquals("Zelador", service.cadastrarFuncionario("Marcos", "Zelador").getCargo());
        }
    }

    @Test
    void menuCadastraMoradorEMostraFicha() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:menu")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            // Moradia > cadastrar Ana no apartamento 1 > consultar ficha 2 > voltar > sair
            String entrada = "2\n1\nAna\n22222222222\n61999998888\n1\n3\n2\n0\n0\n";
            Console console = new Console(new Scanner(entrada), new PrintStream(saida));

            new MenuPrincipal(service, console).exibir();

            String texto = saida.toString();
            assertEquals(2, service.listarMoradores().size());
            assertTrue(texto.contains("--- FICHA DO MORADOR ---"));
            assertTrue(texto.contains("Encomendas pendentes: 0"));
            assertTrue(texto.contains("G01"));
            assertFalse(texto.contains("\033[2J"), "Nos testes a tela não deve ser limpa.");
        }
    }

    @Test
    void fimDaEntradaCancelaCadastro() {
        try (Banco banco = Banco.abrirH2("jdbc:h2:mem:fimDaEntrada")) {
            DadosIniciais.inicializar(banco);
            GestaoService service = new GestaoService(banco);
            // A entrada termina no meio do cadastro: nada deve ser gravado.
            Console console = new Console(new Scanner("2\n1\nAna\n"), new PrintStream(new ByteArrayOutputStream()));

            new MenuPrincipal(service, console).exibir();

            assertEquals(1, service.listarMoradores().size());
        }
    }
}
