package org.example;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class prato {

    private String nome;
    private double custo;
    private double lucro;

    private double fatorLucro = 1;

    public prato(String nome, double custo, double lucro){
        this.nome = nome;
        this.custo = custo;
        this.lucro = lucro;
    }


    public prato(){}



    public double getLucro(){
        return lucro * fatorLucro;
    }

    public double fatorLucroCusto(){
        return getLucro() - this.custo;
    }


}
