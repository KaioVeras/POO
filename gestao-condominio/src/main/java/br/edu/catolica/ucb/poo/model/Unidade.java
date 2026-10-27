package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "unidades")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 20)
@DiscriminatorValue("COMUM")
public class Unidade {
    @Id
    private int numero;

    @Column(name = "taxa_base", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxaBase;

    @Column(nullable = false)
    private boolean ocupada;

    protected Unidade() { } // JPA precisa reconstruir os objetos lidos do banco.

    public Unidade(int numero, double taxaBase) {
        this(numero, taxaBase, true);
    }

    public Unidade(int numero, double taxaBase, boolean ocupada) {
        if (numero <= 0) throw new IllegalArgumentException("O número da unidade deve ser positivo.");
        this.numero = numero;
        this.taxaBase = Valores.dinheiro(taxaBase);
        this.ocupada = ocupada;
    }

    public int getNumero() { return numero; }
    public double getTaxaBase() { return taxaBase.doubleValue(); }
    public boolean isOcupada() { return ocupada; }

    public double calcularTaxa() { return getTaxaBase(); }

    public double calcularTaxa(double percentualMulta) {
        // A chamada polimórfica também usa a taxa especial de uma Cobertura.
        return Valores.comAdicional(calcularTaxa(), percentualMulta);
    }

    public void exibirSituacao() {
        System.out.println(ocupada ? "Ocupada" : "Desocupada");
    }

    @Override public String toString() {
        return String.format("Unidade %d | Taxa: R$ %.2f | %s", numero, calcularTaxa(), ocupada ? "Ocupada" : "Desocupada");
    }
}
