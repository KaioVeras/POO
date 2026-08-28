/*
    Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
    Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
    @author kaioveras
 */


/*
    Criar calculadora que recebe um valor e calcula a média e a quantidade 
    de números digitados

---

    No máximo 4 atributos

    Não pode usar laço de repetição (for, while)

    Não pode usar vetor
*/


public class CalculadoraMedia {
    private double valor;
            int valorQuantidade;
            double novoValor;
            double mediaValor;
            
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public int somarNumeros() {
        
        this.novoValor = this.novoValor + this.valor;
        this.valorQuantidade = this.valorQuantidade + 1;
        
        return this.valorQuantidade;
    }
    
    public double media() {
        this.mediaValor = this.novoValor / this.valorQuantidade;
        return this.mediaValor;
    }
    
}
