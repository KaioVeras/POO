package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Encomenda recebida pela portaria para um morador.
 * Enquanto retiradaEm for nulo, a encomenda está pendente.
 * O registro nunca é apagado: depois da retirada ele continua no histórico.
 */
@Entity
@Table(name = "encomendas")
public class Encomenda {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Associação: muitas encomendas para um morador (coluna morador_id). */
    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id", nullable = false)
    private Morador morador;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(name = "recebida_em", nullable = false)
    private LocalDateTime recebidaEm;

    @Column(name = "retirada_em")
    private LocalDateTime retiradaEm;

    /** Construtor vazio exigido pelo JPA. */
    protected Encomenda() { }

    public Encomenda(Morador morador, String descricao) {
        if (morador == null) {
            throw new IllegalArgumentException("Morador obrigatório.");
        }
        this.morador = morador;
        this.descricao = Validacao.texto(descricao, "Descrição", 200);
        this.recebidaEm = LocalDateTime.now().withNano(0);
    }

    public Long getId() { return id; }
    public LocalDateTime getRetiradaEm() { return retiradaEm; }

    public boolean isPendente() {
        return retiradaEm == null;
    }

    /** Registra a retirada. Encapsulamento: só este método altera retiradaEm. */
    public void retirar() {
        if (!isPendente()) {
            throw new IllegalArgumentException("Encomenda já retirada.");
        }
        retiradaEm = LocalDateTime.now().withNano(0);
    }

    @Override
    public String toString() {
        Apartamento apartamento = morador.getApartamento();
        String situacao = isPendente() ? "PENDENTE" : "Retirada: " + retiradaEm.format(FORMATO);
        return "ID " + id + " | " + morador.getNome()
                + " | Bloco " + apartamento.getBloco() + ", Apto " + apartamento.getNumero()
                + " | " + descricao + " | Recebida: " + recebidaEm.format(FORMATO) + " | " + situacao;
    }
}
