package br.com.dhonatandiego.pcc;

import java.util.ArrayList;

public class Dependente {
    String Nome;
    String Email;
    String SUS;
    String Telefone;
    String Endereço;
    String Bairro;
    String Cidade;
    String CPF;
    String RG;
    String Senha;
    String UF;
    String Numero;
    String IDref;
    public Dependente(String IDref,String nome,String email,String telefone,String endereço,String bairro,String numero,String cidade,String uf,String RG,String CPF,String SUS) {
        this.IDref =IDref;
        Email =email;
        Telefone=telefone;
        this.CPF=CPF;
        Bairro=bairro;
        Cidade=cidade;
        Numero=numero;
        this.RG=RG;
        Nome=nome;
        this.UF=uf;
        this.SUS=SUS;
        Endereço=endereço;
    }
    public Dependente(String email,String senha){
        Email=email;
        Senha=senha;
    }
    public Dependente(){}

}
