package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "condominios")
public class Condominio {
    @Id
    private Long id = 1L;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private int blocos;

    @Column(name = "taxa_padrao", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxaPadrao;

    @Column(name = "portaria_24h", nullable = false)
    private boolean portaria24h;

    @Column(name = "saldo_caixa", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldoCaixa = new BigDecimal("0.00");

    protected Condominio() { }

    public Condominio(String nome, int blocos, double taxaPadrao, boolean portaria24h) {
        this.nome = Valores.texto(nome, "Nome", 100);
        if (blocos <= 0) throw new IllegalArgumentException("A quantidade de blocos deve ser positiva.");
        this.blocos = blocos;
        this.taxaPadrao = Valores.dinheiro(taxaPadrao);
        this.portaria24h = portaria24h;
    }

    public String getNome() { return nome; }
    public int getBlocos() { return blocos; }
    public double getTaxaPadrao() { return taxaPadrao.doubleValue(); }
    public boolean isPortaria24h() { return portaria24h; }
    public double getSaldoCaixa() { return saldoCaixa.doubleValue(); }

    public void receberPagamento(double valor) {
        BigDecimal novoSaldo = saldoCaixa.add(Valores.positivo(valor));
        saldoCaixa = Valores.dinheiro(novoSaldo.doubleValue());
    }

    public void pagarDespesa(double valor) {
        BigDecimal despesa = Valores.positivo(valor);
        if (despesa.compareTo(saldoCaixa) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente para pagar a despesa.");
        }
        saldoCaixa = saldoCaixa.subtract(despesa);
    }

    public String classificarTaxa() {
        if (taxaPadrao.compareTo(new BigDecimal("400")) <= 0) return "Taxa econômica";
        if (taxaPadrao.compareTo(new BigDecimal("700")) <= 0) return "Taxa intermediária";
        return "Taxa elevada";
    }
}
