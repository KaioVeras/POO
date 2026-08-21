/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/*
    Compras até 500 - sem desconto
    Acima de 500 - 10% de desconto
*/

/**
 *
 * @author kaio.veras
 */
public class Compras {
    private double valor;
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public double getValor() {
        return valor;
    }
    
    public String getDesconto() {
        // Calcular desconto de 10%
        if(this.valor > 500) {
            double valorComDesconto = this.valor - (this.valor * 10 / 100);
            return "Valor da compra com desconto de 10%: R$" + valorComDesconto;
        } else {
           return "Desconto indisponível para essa compra!!";
        }
    }
}
