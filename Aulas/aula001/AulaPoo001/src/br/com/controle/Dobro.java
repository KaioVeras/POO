/* 
 Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.controle;

/*
 @author kaio.veras
*/

public class Dobro {
   private double valor; // Atributo
   
   public void setValor(double valor) { // Método
       this.valor = valor;
   }
   
   public double getValor() {
       return this.valor;
   }
   
   public double getDobro(){
       return this.valor * 2;
   }
}
