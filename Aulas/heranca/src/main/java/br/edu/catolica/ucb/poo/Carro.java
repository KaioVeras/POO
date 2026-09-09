package br.edu.catolica.ucb.poo;

public class Carro {
    String modelo;
    double velocidadeAtual;

    Carro(String modelo) {
        this.modelo = modelo;
        this.velocidadeAtual = 0;
    }

    void acelerar(double incremento) {
        velocidadeAtual += incremento;

        System.out.println(
                modelo + " acelerou para " + velocidadeAtual + " km/h"
        );
    }

    void reduzir(double decremento) {
        velocidadeAtual -= decremento;

        System.out.println(
                modelo + " reduziu para " + velocidadeAtual + " km/h"
        );
    }
}
