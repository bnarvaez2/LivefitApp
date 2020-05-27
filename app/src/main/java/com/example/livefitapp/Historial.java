package com.example.livefitapp;

public class Historial {
    String año;
    String dia;
    String mes;
    String altura;
    String peso;
    String imc;
    String categoria;

    public Historial(){}

    public Historial(String año, String dia, String mes, String altura, String peso, String imc, String categoria) {
        this.año = año;
        this.dia = dia;
        this.mes = mes;
        this.altura = altura;
        this.peso = peso;
        this.imc = imc;
        this.categoria = categoria;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getCategoria(){
        return categoria;
    }

    public void setCategoria(String categoria){
        this.categoria=categoria;
    }
}
