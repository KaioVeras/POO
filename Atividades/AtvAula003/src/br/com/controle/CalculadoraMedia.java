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
    private double soma;
    private int valorQuantidade;         
    
    public void somarNumeros(double valor) {
        soma += valor;
        valorQuantidade++;
    }
    
    public double media() {
        if(valorQuantidade == 0) {
            return 0;
        }
        
        return soma / valorQuantidade;
    }
    
    public int getQuantidade() {
        return valorQuantidade;
    }   
}
