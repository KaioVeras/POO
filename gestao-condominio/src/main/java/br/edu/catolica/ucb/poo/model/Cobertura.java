package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("COBERTURA")
public class Cobertura extends Unidade {
    protected Cobertura() { }

    public Cobertura(int numero, double taxaBase) {
        super(numero, taxaBase);
    }

    @Override public double calcularTaxa() {
        return Valores.comAdicional(super.calcularTaxa(), 25);
    }

    @Override public String toString() {
        return String.format("Cobertura %d | Taxa: R$ %.2f | %s", getNumero(), calcularTaxa(), isOcupada() ? "Ocupada" : "Desocupada");
    }
}
