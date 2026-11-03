package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.service.GestaoService;
import java.util.List;

/**
 * Cada submenu corresponde a um setor do condomínio.
 * A cada escolha a tela é limpa: fica visível apenas o resultado
 * da operação e, logo abaixo, o menu para a próxima escolha.
 */
public class MenuPrincipal {
    private final GestaoService service;
    private final Console console;

    public MenuPrincipal(GestaoService service, Console console) {
        this.service = service;
        this.console = console;
    }

    public void exibir() {
        try {
            int opcao;
            console.limparTela();
            do {
                console.mostrar("\nRESIDENCIAL GOLDEN JAVA\n1 - Administração\n2 - Moradia\n3 - Portaria\n0 - Sair");
                opcao = console.lerInteiro("Setor: ");
                console.limparTela();
                switch (opcao) {
                    case 1 -> administracao();
                    case 2 -> moradia();
                    case 3 -> portaria();
                    case 0 -> console.mostrar("Até logo!");
                    default -> console.mostrar("Opção inválida.");
                }
            } while (opcao != 0);
        } catch (Console.FimDaEntrada e) {
            console.mostrar("Entrada encerrada. Cadastro incompleto cancelado.");
        }
    }

    private void executar(Runnable operacao) {
        try {
            operacao.run();
        } catch (IllegalArgumentException e) {
            console.mostrar("Atenção: " + e.getMessage());
        } catch (jakarta.persistence.PersistenceException e) {
            console.mostrar("Falha no banco. Confira os dados cadastrados antes de repetir a operação.");
        }
    }

    /** Título do resultado mostrado acima do menu. */
    private void titulo(String texto) {
        console.mostrar("--- " + texto + " ---");
    }

    private void listar(List<?> registros) {
        if (registros.isEmpty()) console.mostrar("Nenhum registro encontrado.");
        for (Object r : registros) console.mostrar(r.toString());
    }

    private void administracao() {
        int op;
        do {
            console.mostrar("\nADMINISTRAÇÃO\n1 - Consultar condomínio\n2 - Cadastrar apartamento\n3 - Listar apartamentos\n4 - Cadastrar funcionário\n5 - Listar funcionários\n0 - Voltar");
            op = console.lerInteiro("Opção: ");
            console.limparTela();
            final int escolha = op;
            executar(() -> {
                switch (escolha) {
                    case 1 -> {
                        titulo("CONDOMÍNIO");
                        console.mostrar(service.consultarCondominio().getNome());
                    }
                    case 2 -> {
                        titulo("CADASTRAR APARTAMENTO");
                        String b = console.lerTexto("Bloco: ");
                        String n = console.lerTexto("Apartamento: ");
                        String v = console.lerTexto("Vaga de garagem (vazio se não houver): ");
                        console.mostrar("Cadastrado: " + service.cadastrarApartamento(b, n, v));
                    }
                    case 3 -> {
                        titulo("APARTAMENTOS ENCONTRADOS");
                        listar(service.listarApartamentos());
                    }
                    case 4 -> {
                        titulo("CADASTRAR FUNCIONÁRIO");
                        String n = console.lerTexto("Nome: ");
                        String c = console.lerTexto("Cargo: ");
                        console.mostrar("Cadastrado: " + service.cadastrarFuncionario(n, c));
                    }
                    case 5 -> {
                        titulo("FUNCIONÁRIOS ENCONTRADOS");
                        listar(service.listarFuncionarios());
                    }
                    case 0 -> { }
                    default -> console.mostrar("Opção inválida.");
                }
            });
        } while (op != 0);
    }

    private void moradia() {
        int op;
        do {
            console.mostrar("\nMORADIA\n1 - Cadastrar morador\n2 - Listar moradores\n3 - Consultar ficha do morador\n0 - Voltar");
            op = console.lerInteiro("Opção: ");
            console.limparTela();
            final int escolha = op;
            executar(() -> {
                switch (escolha) {
                    case 1 -> {
                        titulo("CADASTRAR MORADOR");
                        String n = console.lerTexto("Nome: ");
                        String cpf = console.lerTexto("CPF (11 dígitos): ");
                        String t = console.lerTexto("Telefone: ");
                        listar(service.listarApartamentos());
                        long id = console.lerInteiro("ID do apartamento: ");
                        console.mostrar("Morador cadastrado: " + service.cadastrarMorador(n, cpf, t, id));
                    }
                    case 2 -> {
                        titulo("MORADORES ENCONTRADOS");
                        listar(service.listarMoradores());
                    }
                    case 3 -> {
                        titulo("FICHA DO MORADOR");
                        long id = console.lerInteiro("ID do morador: ");
                        console.mostrar(service.consultarMorador(id).toString());
                        var pendentes = service.encomendasPendentes(id);
                        console.mostrar("Encomendas pendentes: " + pendentes.size());
                        listar(service.historicoEncomendas(id));
                    }
                    case 0 -> { }
                    default -> console.mostrar("Opção inválida.");
                }
            });
        } while (op != 0);
    }

    private void portaria() {
        int op;
        do {
            console.mostrar("\nPORTARIA\n1 - Receber encomenda\n2 - Listar encomendas pendentes\n3 - Registrar retirada\n0 - Voltar");
            op = console.lerInteiro("Opção: ");
            console.limparTela();
            final int escolha = op;
            executar(() -> {
                switch (escolha) {
                    case 1 -> {
                        titulo("RECEBER ENCOMENDA");
                        listar(service.listarMoradores());
                        long id = console.lerInteiro("ID do morador destinatário: ");
                        String d = console.lerTexto("Descrição da encomenda: ");
                        console.mostrar("Encomenda recebida: " + service.receberEncomenda(id, d));
                    }
                    case 2 -> {
                        titulo("ENCOMENDAS PENDENTES");
                        listar(service.listarPendentes());
                    }
                    case 3 -> {
                        titulo("REGISTRAR RETIRADA");
                        listar(service.listarPendentes());
                        long id = console.lerInteiro("ID da encomenda: ");
                        if (console.confirmar("Confirmar retirada")) {
                            service.retirarEncomenda(id);
                            console.mostrar("Retirada registrada.");
                        }
                    }
                    case 0 -> { }
                    default -> console.mostrar("Opção inválida.");
                }
            });
        } while (op != 0);
    }
}
