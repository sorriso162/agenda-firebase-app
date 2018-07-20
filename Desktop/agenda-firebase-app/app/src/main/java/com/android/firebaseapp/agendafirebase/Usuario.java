package com.android.firebaseapp.agendafirebase;

public class Usuario {
    private String nome;
    private String sobreNome;
    private String idade;
    private String email;
    private String dia1;
    private String dia2;
    private String dia3;

    public Usuario( ) {
    }
    public Usuario( String nome, String sobreNome,String idade) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getDia1() {
        return dia1;
    }

    public void setDia1(String dia1) {
        this.dia1 = dia1;
    }

    public String getDia2() {
        return dia2;
    }

    public void setDia2(String dia2) {
        this.dia2 = dia2;
    }

    public String getDia3(String s) {
        return dia3;
    }

    public void setDia3(String dia3) {
        this.dia3 = dia3;
    }
}
