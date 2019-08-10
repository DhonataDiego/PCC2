package br.com.dhonatandiego.pcc;

import java.util.ArrayList;

public class Usuario {
    private String Nome;
    private String Email;
    private String SUS;
    private String Telefone;
    private String Endereço;
    private String Bairro;
    private String Cidade;
    private String CPF;
    private String RG;
    private String Senha;
    private String UF;
    private String Numero;
    private String Permissão;
    public ArrayList<Usuario>Dependentes = new ArrayList<>();

    public Usuario(String nome,String email,String senha,String telefone,String endereço,String bairro,String numero,String cidade,String uf,String RG,String CPF,String SUS) {
        this.setEmail(email);
        this.setTelefone(telefone);
        this.setCpf(CPF);
        this.setSenha(senha);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setNumero(numero);
        this.setRg(RG);
        this.setNome(nome);
        this.setUf(uf);
        this.setSUS(SUS);
        this.setEndereço(endereço);
    }
    public Usuario(String email,String senha){
        this.setEmail(email);
        this.setSenha(senha);
    }
    public Usuario(){}

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }

    public String getEndereço() {
        return Endereço;
    }

    public void setEndereço(String endereço) {
        this.Endereço = endereço;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        this.Bairro = bairro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        this.Cidade = cidade;
    }

    public String getCpf() {
        return CPF;
    }

    public void setCpf(String cpf) {
        this.CPF = cpf;
    }

    public String getRg() {
        return RG;
    }

    public void setRg(String rg) {
        this.RG = rg;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        this.Senha = senha;
    }

    public String getUf() {
        return UF;
    }

    public void setUf(String uf) {
        this.UF = uf;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        this.Numero = numero;
    }
    public String getSUS() {
        return SUS;
    }

    public void setSUS(String SUS) {
        this.SUS = SUS;
    }

    public String getPermissão() {
        return Permissão;
    }

    public void setPermissão(String Permissao) {
        this.Permissão = Permissão;
    }
}
