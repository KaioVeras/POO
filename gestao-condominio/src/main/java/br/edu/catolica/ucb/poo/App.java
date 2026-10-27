package br.edu.catolica.ucb.poo;

import br.edu.catolica.ucb.poo.infra.*;
import br.edu.catolica.ucb.poo.service.GestaoService;
import br.edu.catolica.ucb.poo.view.Console;
import br.edu.catolica.ucb.poo.view.MenuPrincipal;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {
        if (args.length > 1 || (args.length == 1 && !args[0].equals("--inicializar"))) {
            System.err.println("Uso: java -jar gestao-condominio.jar [--inicializar]");
            System.exit(1);
        }
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        try (Banco banco = Banco.abrirPadrao()) {
            DadosIniciais.inicializar(banco);
            System.out.println("Banco conectado: " + Banco.nomeSelecionado());
            if (args.length == 1) {
                System.out.println("Dados iniciais preparados. O banco foi fechado com segurança.");
                return;
            }
            GestaoService service = new GestaoService(banco);
            Console console = new Console(new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8), System.out);
            new MenuPrincipal(service, console).exibir();
        } catch (RuntimeException e) {
            System.err.println("Não foi possível iniciar ou continuar o sistema. Confira a configuração e se o banco está disponível.");
            System.err.println("Detalhe: " + e.getMessage());
            System.exit(1);
        }
    }
}
