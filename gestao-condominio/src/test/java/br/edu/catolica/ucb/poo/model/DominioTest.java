package br.edu.catolica.ucb.poo.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class DominioTest {
    @Test void adicionalArredondaEmDecimalAntesDeConverterParaDouble() {
        assertEquals(8714323586.11, Valores.comAdicional(5731976311.33, 52.03));
    }

    @Test void coberturaAplicaAdicionalTambemNoCalculoComMulta() {
        Unidade unidade = new Cobertura(201, 500);
        assertEquals(625, unidade.calcularTaxa());
        assertEquals(687.50, unidade.calcularTaxa(10));
        assertTrue(unidade.isOcupada());
        assertTrue(unidade.toString().contains("Cobertura 201"));
    }

    @Test void unidadeComumCalculaTaxaEMultaSemAlterarBase() {
        Unidade unidade = new Unidade(101, 500, false);
        assertEquals(550, unidade.calcularTaxa(10));
        assertEquals(500, unidade.calcularTaxa());
        assertFalse(unidade.isOcupada());
        assertEquals(0, new Unidade(102, 0).calcularTaxa());
    }

    @ParameterizedTest @ValueSource(doubles = {-1, Double.NaN, Double.POSITIVE_INFINITY})
    void rejeitaTaxasInvalidas(double valor) {
        assertThrows(IllegalArgumentException.class, () -> new Unidade(101, valor));
        assertThrows(IllegalArgumentException.class, () -> new Unidade(101, 500).calcularTaxa(valor));
    }

    @Test void despesaRecusadaPreservaCaixaEPermiteGastarSaldoExato() {
        Condominio condominio = new Condominio("Golden Java", 2, 500, true);
        condominio.receberPagamento(500);
        assertThrows(IllegalArgumentException.class, () -> condominio.pagarDespesa(501));
        assertEquals(500, condominio.getSaldoCaixa());
        condominio.pagarDespesa(500);
        assertEquals(0, condominio.getSaldoCaixa());
    }

    @ParameterizedTest @ValueSource(doubles = {0, -1, Double.NaN, Double.POSITIVE_INFINITY})
    void caixaRejeitaValoresNaoPositivosOuNaoFinitos(double valor) {
        Condominio condominio = new Condominio("Golden Java", 2, 500, true);
        assertThrows(IllegalArgumentException.class, () -> condominio.receberPagamento(valor));
        assertThrows(IllegalArgumentException.class, () -> condominio.pagarDespesa(valor));
        assertEquals(0, condominio.getSaldoCaixa());
    }

    @Test void caixaUsaCentavosDecimais() {
        Condominio condominio = new Condominio("Golden Java", 1, 500, true);
        condominio.receberPagamento(0.10);
        condominio.receberPagamento(0.20);
        assertEquals(0.30, condominio.getSaldoCaixa());
    }

    @Test void funcionarioPolimorficoRecebeAdicionalDeZelador() {
        Funcionario comum = new Funcionario("Paulo", 2500);
        Funcionario zelador = new Zelador("Marcos", 3000);
        assertEquals(2500, comum.calcularSalario());
        assertEquals(3300, zelador.calcularSalario());
        assertTrue(zelador.toString().contains("Zelador"));
        assertThrows(IllegalArgumentException.class, () -> new Funcionario("Paulo", -1));
    }

    @Test void classificacaoRespeitaLimites() {
        assertEquals("Taxa econômica", new Condominio("C", 1, 400, false).classificarTaxa());
        assertEquals("Taxa intermediária", new Condominio("C", 1, 400.01, false).classificarTaxa());
        assertEquals("Taxa intermediária", new Condominio("C", 1, 700, false).classificarTaxa());
        assertEquals("Taxa elevada", new Condominio("C", 1, 700.01, false).classificarTaxa());
    }

    @Test void validaIdentificacaoERepresentaMorador() {
        assertThrows(IllegalArgumentException.class, () -> new Unidade(0, 500));
        assertThrows(IllegalArgumentException.class, () -> new Morador(" ", "11111111111", new Unidade(101, 500)));
        Morador morador = new Morador("Carlos", "11111111111", new Unidade(101, 500));
        assertTrue(morador.toString().contains("101"));
        assertTrue(morador.toString().contains("Carlos"));
    }
}
