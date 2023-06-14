package org.example;

import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    private JTextField dias, pratos, orcamento;
    private

    int qtd_dias, qtd_pratos;
    double valor_orcamento;
    static double lucro_total;
    List<prato> custoLucroPratos = new ArrayList<>();

    public Main() {
        setTitle("Monte seu cardápio");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formularioBase = new JPanel();
        formularioBase.setLayout(new GridLayout(4, 2, 15, 15));

        Border padding = BorderFactory.createEmptyBorder(10,10,10,10);
        formularioBase.setBorder(padding);

        dias = new JTextField();
        dias.setBorder(padding);
        dias.setSize(20, 5);

        pratos = new JTextField();
        pratos.setBorder(padding);

        orcamento = new JTextField();
        orcamento.setBorder(padding);

        formularioBase.add(new JLabel("Dias em que irá cozinhar:"));
        formularioBase.add(dias);
        formularioBase.add(new JLabel("Quantidade de pratos disponíveis:"));
        formularioBase.add(pratos);
        formularioBase.add(new JLabel("Orçamento:"));
        formularioBase.add(orcamento);

        JButton button = new JButton("Continuar");
        button.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                qtd_dias = validarCampoInteiro(dias, "Por favor, informe um número válido, de 1 à 21, para a quantidade de dias", 1, 21);
                qtd_pratos = validarCampoInteiro(pratos, "Por favor, informe um número válido, de 1 à 50, para a quantidade de pratos disponíveis", 1, 50);
                valor_orcamento = validarCampoDecimal(orcamento, "Por favor, informe um número válido, 0 à 100, para o valor do orçamento", 0, 100);

                if (qtd_pratos > 0) {
                    criarFormularioPratos();
                }
            }
        });

        formularioBase.add(new JLabel());
        formularioBase.add(button);

        add(formularioBase);
        setVisible(true);
    }

    private void criarFormularioPratos() {
        JFrame segundoForm = new JFrame("Preenchimento dos pratos");
        segundoForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formularioPratos = new JPanel();

        int gridVertical = (int) (qtd_pratos + 1);
        formularioPratos.setLayout(new GridLayout(gridVertical, 4, 15, 15));

        Border padding = BorderFactory.createEmptyBorder(10,10,10,10);

        formularioPratos.setBorder(padding);

        JTextField[] custoPratos = new JTextField[qtd_pratos];
        JTextField[] lucroPratos = new JTextField[qtd_pratos];

        for (int i = 0; i < qtd_pratos; i++) {
            formularioPratos.add(new JLabel("Prato " + (i + 1) + " | Custo"));
            custoPratos[i] = new JTextField();
            custoPratos[i].setBorder(padding);
            formularioPratos.add(custoPratos[i]);

            formularioPratos.add(new JLabel("Prato " + (i + 1) + " | Lucro"));
            lucroPratos[i] = new JTextField();
            lucroPratos[i].setBorder(padding);
            formularioPratos.add(lucroPratos[i]);
        }

        JButton botao = new JButton("Montar Cardápio");
        botao.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                custoLucroPratos.clear();

                if (qtd_pratos > 0) {
                    for (int i = 0; i < qtd_pratos; i++) {
                        prato novoCustoLucroPrato = validarCustoLucroDosPratos(custoPratos[i], lucroPratos[i], i);

                        if (novoCustoLucroPrato.getCusto() > 0 && novoCustoLucroPrato.getLucro() > 0) {
                            custoLucroPratos.add(novoCustoLucroPrato);
                        }
                    }

                    if (custoLucroPratos.size() > 0) {
                        List<prato> cardapioGuloso = montarCardapioGuloso(qtd_dias, qtd_pratos, valor_orcamento, custoLucroPratos);
                        criarFormularioResultado(cardapioGuloso);
                    }
                }
            }
        });

        formularioPratos.add(new JLabel());
        formularioPratos.add(new JLabel());
        formularioPratos.add(new JLabel());
        formularioPratos.add(botao);

        segundoForm.getContentPane().add(BorderLayout.CENTER, formularioPratos);
        segundoForm.pack();
        segundoForm.setLocationByPlatform(true);
        segundoForm.setVisible(true);
    }

    private void criarFormularioResultado(List<prato> cardapio) {
        String resumoCardapio = "Lucro total: ";

        if(cardapio.size() > 0){
            resumoCardapio = resumoCardapio.concat(String.format("%.1f", lucro_total) + "\n\n");
            resumoCardapio = resumoCardapio.concat("Pratos escolhidos: " + "\n");

            for (prato pratoDoDia : cardapio) {
                resumoCardapio = resumoCardapio.concat(pratoDoDia.getNome() + ' ');
            }
        }
        else{
            resumoCardapio = "0.0";
        }

        JOptionPane.showMessageDialog(
                null,
                resumoCardapio,
                "Resumo do cardápio",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private int validarCampoInteiro(JTextField campo, String mensagemErro, int min, int max) {
        Border padding = BorderFactory.createEmptyBorder(0,10,0,0);
        Border bordaErro = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red, 2),
                BorderFactory.createEmptyBorder(0, 10, 5, 5)
        );

        int valor = 0;

        try {
            campo.setBorder(padding);
            valor = Integer.parseInt(campo.getText());

            if (valor < min || valor > max) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, mensagemErro, "Valor Inválido", JOptionPane.INFORMATION_MESSAGE);
            campo.setText("0");
            campo.setBorder(bordaErro);
        }

        return valor;
    }

    private double validarCampoDecimal(JTextField campo, String mensagemErro, double min, double max) {
        Border padding = BorderFactory.createEmptyBorder(0,10,0,0);
        Border bordaErro = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red, 2),
                BorderFactory.createEmptyBorder(0, 10, 5, 5)
        );

        double valor = 0;

        try {
            campo.setBorder(padding);
            valor = Double.parseDouble(campo.getText());

            if (valor < min || valor > max) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, mensagemErro, "Valor Inválido", JOptionPane.INFORMATION_MESSAGE);
            campo.setText("0");
            campo.setBorder(bordaErro);
        }

        return valor;
    }

    private prato validarCustoLucroDosPratos(JTextField custo, JTextField lucro, int indice) {
        Border padding = BorderFactory.createEmptyBorder(0,10,0,0);
        Border bordaErro = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red, 2),
                BorderFactory.createEmptyBorder(0, 10, 5, 5)
        );

        prato prato = new prato();

        try {
            prato.setNome(Integer.toString(indice + 1));

            custo.setBorder(padding);
            prato.setCusto(Double.parseDouble(custo.getText()));

            lucro.setBorder(padding);
            prato.setLucro(Double.parseDouble(lucro.getText()));

            if (prato.getCusto() <= 0 || prato.getLucro() <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(
                    null,
                    "Por favor, informe um número válido, maior que zero, para as informações do Prato " + (indice + 1),
                    "Valor Inválido",
                    JOptionPane.INFORMATION_MESSAGE
            );

            custo.setText("0");
            custo.setBorder(bordaErro);

            lucro.setText("0");
            lucro.setBorder(bordaErro);
        }

        return prato;
    }

    public static List<prato> montarCardapioGuloso(int dias, int quantidadePratos, double orcamento, List<prato> pratos){
        double lucroTotal = 0;
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
                lucroTotal += pratodoDia.getLucro();

            }else{
                // Orçamento estourado
                cardapio.clear();
                return cardapio;
            }

        }

        lucro_total = lucroTotal;
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