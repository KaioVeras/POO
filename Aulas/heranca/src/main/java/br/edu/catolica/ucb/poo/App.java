package br.edu.catolica.ucb.poo;

public class App {

    public static void main(String[] args) {
        Carro fusca = new Carro("Fusca");
        fusca.acelerar(40);

        CarroEletrico tesla = new CarroEletrico("Tesla Model 3");

        tesla.acelerar(100);
        tesla.reduzir(30);

        tesla.porcentagemBateria = 85; // Alterando o atributo específico
        tesla.carregarBateria();
    }
}
