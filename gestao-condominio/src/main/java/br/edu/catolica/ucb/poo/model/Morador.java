package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "moradores")
public class Morador {
    @Id
    @Column(length = 11)
    private String cpf;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unidade_numero", nullable = false)
    private Unidade unidade;

    protected Morador() { }

    public Morador(String nome, String cpf, Unidade unidade) {
        this.nome = Valores.texto(nome, "Nome", 100);
        if (cpf == null || !cpf.matches("[0-9]{11}")) {
            throw new IllegalArgumentException("Informe o CPF com 11 dígitos, sem pontuação.");
        }
        if (unidade == null) throw new IllegalArgumentException("Informe a unidade do morador.");
        this.cpf = cpf;
        this.unidade = unidade;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getNumeroUnidade() { return unidade.getNumero(); }

    @Override public String toString() {
        return nome + " | CPF: " + cpf + " | Unidade: " + getNumeroUnidade();
    }
}
