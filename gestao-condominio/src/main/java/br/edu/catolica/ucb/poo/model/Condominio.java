package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;

/**
 * O condomínio administrado pelo sistema.
 * A aplicação trabalha com um único condomínio, por isso o ID é sempre 1.
 */
@Entity
@Table(name = "condominios")
public class Condominio {

    @Id
    private Long id = 1L;

    @Column(nullable = false, length = 100)
    private String nome;

    /** Construtor vazio exigido pelo JPA para recriar o objeto a partir do banco. */
    protected Condominio() { }

    public Condominio(String nome) {
        this.nome = Validacao.texto(nome, "Nome", 100);
    }

    public String getNome() { return nome; }
}
