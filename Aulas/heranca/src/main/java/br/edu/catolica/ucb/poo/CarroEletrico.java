package br.edu.catolica.ucb.poo;

public class CarroEletrico extends Carro {
    double porcentagemBateria;

    CarroEletrico(String modelo) {
        super(modelo);
        this.porcentagemBateria = 100;
    }

    void carregarBateria() {
        porcentagemBateria = 100;
        System.out.println(modelo + " foi totalmente carregado!");
    }
}