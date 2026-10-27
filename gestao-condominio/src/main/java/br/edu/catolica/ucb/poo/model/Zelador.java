package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ZELADOR")
public class Zelador extends Funcionario {
    protected Zelador() { }

    public Zelador(String nome, double salarioBase) {
        super(nome, salarioBase);
    }

    @Override public double calcularSalario() {
        return Valores.comAdicional(super.calcularSalario(), 10);
    }

    @Override public String toString() {
        return String.format("Zelador %s | Salário: R$ %.2f", getNome(), calcularSalario());
    }
}
