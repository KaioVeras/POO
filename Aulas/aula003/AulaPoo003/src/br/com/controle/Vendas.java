/*
    Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
    Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
    @author kaioveras
 */
public class Vendas {
    private double valor;
            double resposta;
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public double getValor() {
        return valor;
    }
    
    public double calcularDesconto() {
        if(this.valor >= 500) {
            resposta = this.valor - (this.valor * 0.1);
        } else {
            resposta = this.valor;
        }
        
        return resposta;
    }

}
