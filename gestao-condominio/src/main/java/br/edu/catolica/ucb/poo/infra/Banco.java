package br.edu.catolica.ucb.poo.infra;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Conexão com o banco de dados por meio do JPA.
 * Existe uma EntityManagerFactory para toda a aplicação e um EntityManager novo
 * para cada operação. Implementa AutoCloseable para ser usado em try-with-resources.
 */
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

    /** Abre um banco H2 na URL indicada (usado também pelos testes). */
    public static Banco abrirH2(String url) {
        return new Banco("org.h2.Driver", url, "sa", "");
    }

    /** Abre H2 (padrão) ou MySQL, conforme a configuração "banco" / BANCO. */
    public static Banco abrirPadrao() {
        return switch (nomeSelecionado()) {
            case "h2" -> abrirH2(configuracao("db.url", "DB_URL", "jdbc:h2:./dados/moradia"));
            case "mysql" -> new Banco("com.mysql.cj.jdbc.Driver",
                    configuracao("db.url", "DB_URL", "jdbc:mysql://localhost:3306/gestao_moradia?serverTimezone=America/Sao_Paulo"),
                    configuracao("db.user", "DB_USER", "condominio"),
                    configuracao("db.password", "DB_PASSWORD", ""));
            default -> throw new IllegalArgumentException("Banco inválido. Use h2 ou mysql.");
        };
    }

    public static String nomeSelecionado() {
        return configuracao("banco", "BANCO", "h2").trim().toLowerCase(java.util.Locale.ROOT);
    }

    /** Lê uma propriedade Java (-Dnome=valor), depois a variável de ambiente e, por fim, o valor padrão. */
    private static String configuracao(String propriedade, String ambiente, String padrao) {
        String valor = System.getProperty(propriedade);
        if (valor == null) valor = System.getenv(ambiente);
        return valor == null ? padrao : valor;
    }

    /** Executa uma leitura, sem transação. O EntityManager é fechado no fim. */
    public <T> T consultar(Function<EntityManager, T> operacao) {
        try (EntityManager em = factory.createEntityManager()) {
            return operacao.apply(em);
        }
    }

    /** Executa uma gravação: commit se tudo der certo, rollback se ocorrer qualquer erro. */
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
