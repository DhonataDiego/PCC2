package br.com.dhonatandiego.pcc;

import java.io.Serializable;

public class Hospital implements Serializable {
    String IBGE;
    String UF;
    String MUNICIPIO;
    String CNES;
    String NOME_FANTASIA;
    String RAZAO_SOCIAL;
    String CNPJ_PROPRIO;
    String CNPJ_MANTENEDORA;
    String TIPO_GESTAO;
    String LOGRADOURO;
    String NUMERO;
    String BAIRRO;
    String CEP;
    String LATITUDE;
    String LONGITUDE;


    public Hospital(String IBGE, String UF, String MUNICIPIO, String CNES, String NOME_FANTASIA, String RAZAO_SOCIAL, String CNPJ_PROPRIO, String CNPJ_MANTENEDORA, String TIPO_GESTAO, String LOGRADOURO, String NUMERO, String BAIRRO, String CEP, String LATITUDE, String LONGITUDE) {
        this.IBGE = IBGE;
        this.UF = UF;
        this.MUNICIPIO = MUNICIPIO;
        this.CNES = CNES;
        this.NOME_FANTASIA = NOME_FANTASIA;
        this.RAZAO_SOCIAL = RAZAO_SOCIAL;
        this.CNPJ_PROPRIO = CNPJ_PROPRIO;
        this.CNPJ_MANTENEDORA = CNPJ_MANTENEDORA;
        this.TIPO_GESTAO = TIPO_GESTAO;
        this.LOGRADOURO = LOGRADOURO;
        this.NUMERO = NUMERO;
        this.BAIRRO = BAIRRO;
        this.CEP = CEP;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
    }
    public Hospital(){

    }
}
