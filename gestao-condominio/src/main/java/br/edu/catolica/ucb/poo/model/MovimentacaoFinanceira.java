package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
public class MovimentacaoFinanceira {
    public enum Tipo { PAGAMENTO, DESPESA }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Tipo tipo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(name = "registrada_em", nullable = false)
    private LocalDateTime registradaEm;

    @ManyToOne
    @JoinColumn(name = "unidade_numero")
    private Unidade unidade;

    protected MovimentacaoFinanceira() { }

    public MovimentacaoFinanceira(Tipo tipo, double valor, String descricao, Unidade unidade) {
        if (tipo == null) throw new IllegalArgumentException("Tipo obrigatório.");
        if (tipo == Tipo.PAGAMENTO && unidade == null) {
            throw new IllegalArgumentException("Pagamento precisa de uma unidade.");
        }
        this.tipo = tipo;
        this.valor = Valores.positivo(valor);
        this.descricao = Valores.texto(descricao, "Descrição", 200);
        this.unidade = unidade;
        this.registradaEm = LocalDateTime.now();
    }

    public double getValor() { return valor.doubleValue(); }
    public Tipo getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public LocalDateTime getRegistradaEm() { return registradaEm; }

    @Override public String toString() {
        return String.format("%s | %s | R$ %.2f | %s", registradaEm.withNano(0), tipo, getValor(), descricao);
    }
}
