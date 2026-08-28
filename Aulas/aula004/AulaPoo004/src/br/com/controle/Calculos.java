/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 *
 * @author kaio.veras
 */
public class Calculos {
    private double valor;
            double soma;
    private int qtd;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
        soma += this.valor; 
        this.qtd++;
    }
    
    public int getQtd() {
        return this.qtd;
    }
    
    public double calcularMedia() {
        if(qtd == 0) {
            return 0;
        }
        
        return soma / this.qtd;
    }
    
}
