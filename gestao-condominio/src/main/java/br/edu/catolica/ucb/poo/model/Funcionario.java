package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;

/**
 * Funcionário do condomínio.
 * O cargo é um texto ("Zelador", "Porteiro"...). Não há uma classe para cada cargo
 * porque, nesta versão, os cargos não têm comportamentos diferentes.
 */
@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String cargo;

    /** Construtor vazio exigido pelo JPA. */
    protected Funcionario() { }

    public Funcionario(String nome, String cargo) {
        this.nome = Validacao.texto(nome, "Nome", 100);
        this.cargo = Validacao.texto(cargo, "Cargo", 50);
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCargo() { return cargo; }

    @Override
    public String toString() {
        return "ID " + id + " | " + nome + " | Cargo: " + cargo;
    }
}
