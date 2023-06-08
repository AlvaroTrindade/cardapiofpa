package org.example;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Entradas do Usuário
        int dias;
        int operacao = 0;
        int quantidadePratos;
        double orcamento;
        List<prato> pratosDisponiveis = new ArrayList<>();

        // Saída
        List<prato> cardapioGuloso;

        System.out.println("\n====================================");
        System.out.println(" ELABORAÇÃO DE CÁRDAPIO RESTAURANTE ");
        System.out.println("=====================================\n\n");

        System.out.println("BEM-VINDO!! \nPARA MONTAR SEU CARDAPIO, PREENCHA OS SEGUINTES ITENS:");
        System.out.print("\nDIA | PRATO | ORÇAMENTO (separados por espaço): ");

        String[] valores = scanner.nextLine().split(" ");
        dias = Integer.parseInt(valores[0]);
        quantidadePratos = Integer.parseInt(valores[1]);
        orcamento = Integer.parseInt(valores[2]);

        while(pratosDisponiveis.size() < quantidadePratos){

            prato prato = new prato();
            System.out.printf("Custo|Prato %d (separados por espaço): ", pratosDisponiveis.size()+1);
            prato.setNome("Prato " +  (pratosDisponiveis.size()+1));
            String[] custoLucro = scanner.nextLine().split(" ");
            prato.setCusto(Double.parseDouble(custoLucro[0]));
            prato.setLucro(Double.parseDouble(custoLucro[1]));

            pratosDisponiveis.add(prato);

        }

        cardapioGuloso = montarCardapioGuloso(dias, quantidadePratos, orcamento, pratosDisponiveis);

        exibirCardapio(cardapioGuloso);

    }

    public static void exibirCardapio(List<prato> cardapioGuloso){
        if(cardapioGuloso.size() > 0){
            for (prato pratoDodia : cardapioGuloso){
                System.out.println("Prato: " + pratoDodia.getNome());
            }
        }
        else{
            System.out.println("Nenhum prazo possível ao orçamento informado.");
        }
    }


    public static List<prato> montarCardapioGuloso(int dias, int quantidadePratos, double orcamento, List<prato> pratos){

        String lucroTotal;
        List<prato> cardapio = new ArrayList<>();


        for(int i = 1 ; i <= dias; i++){

            prato pratodoDia = null;

            if(i > 1){
                pratos = atualizaFatorLucro(pratos, cardapio);
            }

            do{
                // Se prato for diferente de nulo,
                // ele está na segunda pesquisa, pois o custo foi maior que o orçamento.
                if(pratodoDia != null){
                    pratos.remove(pratodoDia);
                    pratodoDia = null;
                }

                if(pratos.size() > 0){
                    pratodoDia = fatorLucroCusto(pratos);
                }

            } while(pratodoDia != null && pratodoDia.getCusto() > orcamento && pratos.size() > 0);

            if(pratodoDia != null){
                cardapio.add(pratodoDia);
                orcamento -= pratodoDia.getCusto();
            }else{
                // Orçamento estourado
                cardapio.clear();
                return cardapio;
            }

        }

        return cardapio;

    }


    // Método retorna o prato de maior fator Lucro/Custo
    public static prato fatorLucroCusto(List<prato> pratos){
        prato pratoMaxrFator = pratos.get(0);
        double fatorMax = pratos.get(0).fatorLucroCusto();

        for(prato p : pratos){
            if(p.fatorLucroCusto() > fatorMax){
                fatorMax = p.fatorLucroCusto();
                pratoMaxrFator = p;
            }
        }

        return pratoMaxrFator;
    }

    public static List<prato> atualizaFatorLucro(List<prato> pratos, List<prato> cardapio){
        int quantItensCardapio = cardapio.size();
        double fatorLucro = 1;

        // Se os dois últimos itens forem iguais
        if(quantItensCardapio >= 2 && (cardapio.get(quantItensCardapio-2).equals(cardapio.get(quantItensCardapio-1)))){
            fatorLucro = 0;
        }else if (quantItensCardapio == 1){
            fatorLucro = 0.5;
        }

            for(prato p : pratos){
                p.setFatorLucro(1);
                if(p.getNome().equals(cardapio.get(quantItensCardapio-1).getNome())){
                    p.setFatorLucro(fatorLucro);
                }
            }

        return pratos;
    }

}