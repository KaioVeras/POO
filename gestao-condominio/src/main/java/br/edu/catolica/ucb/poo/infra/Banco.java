package br.edu.catolica.ucb.poo.infra;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/** Uma fábrica por aplicação; um EntityManager por operação. */
public final class Banco implements AutoCloseable {
    private final EntityManagerFactory factory;

    private Banco(String driver, String url, String usuario, String senha) {
        Map<String, Object> propriedades = new HashMap<>();
        propriedades.put("jakarta.persistence.jdbc.driver", driver);
        propriedades.put("jakarta.persistence.jdbc.url", url);
        propriedades.put("jakarta.persistence.jdbc.user", usuario);
        propriedades.put("jakarta.persistence.jdbc.password", senha);
        factory = Persistence.createEntityManagerFactory("condominio", propriedades);
    }

    public static Banco abrirH2(String url) {
        return new Banco("org.h2.Driver", url, "sa", "");
    }

    public static Banco abrirPadrao() {
        return switch (nomeSelecionado()) {
            case "h2" -> abrirH2(configuracao("db.url", "DB_URL", "jdbc:h2:./dados/condominio"));
            case "mysql" -> new Banco("com.mysql.cj.jdbc.Driver",
                    configuracao("db.url", "DB_URL", "jdbc:mysql://localhost:3306/gestao_condominio?serverTimezone=America/Sao_Paulo"),
                    configuracao("db.user", "DB_USER", "condominio"),
                    configuracao("db.password", "DB_PASSWORD", ""));
            default -> throw new IllegalArgumentException("Banco inválido. Use h2 ou mysql.");
        };
    }

    public static String nomeSelecionado() {
        return configuracao("banco", "BANCO", "h2").trim().toLowerCase(java.util.Locale.ROOT);
    }

    private static String configuracao(String propriedade, String ambiente, String padrao) {
        String valor = System.getProperty(propriedade);
        if (valor == null) valor = System.getenv(ambiente);
        return valor == null ? padrao : valor;
    }

    public <T> T consultar(Function<EntityManager, T> operacao) {
        try (EntityManager em = factory.createEntityManager()) {
            return operacao.apply(em);
        }
    }

    public <T> T transacionar(Function<EntityManager, T> operacao) {
        try (EntityManager em = factory.createEntityManager()) {
            EntityTransaction transacao = em.getTransaction();
            try {
                transacao.begin();
                T resultado = operacao.apply(em);
                transacao.commit();
                return resultado;
            } catch (RuntimeException e) {
                if (transacao.isActive()) transacao.rollback();
                throw e;
            }
        }
    }

    @Override public void close() { factory.close(); }
}
