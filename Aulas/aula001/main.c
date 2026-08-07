// Faça um programa que receba como dado de entrada um número qualquer
// e mostre como resultado o dobro do número digitado

#include <stdio.h>
#include <stdlib.h>

int main() {
    system("cls");

    int numero = 0;

    printf("==== Aula 01 ====\n");

    printf("Insira um numero: ");
    scanf("%d", &numero);

    int dobro = numero * 2;

    printf("O dobro do numero e: %d", dobro);

    return 0;
}

