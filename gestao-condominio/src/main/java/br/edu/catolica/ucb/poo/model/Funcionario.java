package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 20)
@DiscriminatorValue("COMUM")
public class Funcionario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "salario_base", nullable = false)
    protected double salarioBase;

    protected Funcionario() { }

    public Funcionario(String nome, double salarioBase) {
        this.nome = Valores.texto(nome, "Nome", 100);
        this.salarioBase = Valores.dinheiro(salarioBase).doubleValue();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public double calcularSalario() { return Valores.dinheiro(salarioBase).doubleValue(); }

    @Override public String toString() {
        return String.format("Funcionário %s | Salário: R$ %.2f", nome, calcularSalario());
    }
}
