package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;
import java.util.Locale;

/**
 * Apartamento identificado pelo par bloco e número.
 * O mesmo número pode existir em blocos diferentes (A/101 e B/101).
 * A vaga de garagem é opcional e não pode pertencer a dois apartamentos.
 */
@Entity
@Table(name = "apartamentos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bloco", "numero"}))
public class Apartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String bloco;

    @Column(nullable = false, length = 20)
    private String numero;

    /** Nulo quando o apartamento não tem vaga. */
    @Column(unique = true, length = 20)
    private String vaga;

    /** Construtor vazio exigido pelo JPA. */
    protected Apartamento() { }

    public Apartamento(String bloco, String numero, String vaga) {
        // Bloco e vaga ficam em maiúsculas para que "a" e "A" sejam o mesmo bloco.
        this.bloco = Validacao.texto(bloco, "Bloco", 20).toUpperCase(Locale.ROOT);
        this.numero = Validacao.texto(numero, "Número", 20);
        if (vaga == null || vaga.isBlank()) {
            this.vaga = null;
        } else {
            this.vaga = Validacao.texto(vaga, "Vaga", 20).toUpperCase(Locale.ROOT);
        }
    }

    public Long getId() { return id; }
    public String getBloco() { return bloco; }
    public String getNumero() { return numero; }
    public String getVaga() { return vaga; }

    /** Endereço do apartamento, sem o ID. Também é usado na ficha do morador. */
    public String descricao() {
        return "Bloco " + bloco + " | Apartamento " + numero
                + " | Garagem: " + (vaga == null ? "sem vaga" : vaga);
    }

    @Override
    public String toString() {
        return "ID " + id + " | " + descricao();
    }
}
