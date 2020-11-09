package com.example.hospital.model;

import androidx.room.Entity;

import java.io.Serializable;

@Entity
public class Funcionario extends Usuario implements Serializable {

    private String setor = null;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Funcionario(String nome, String email, String senha, String setor) {
        this(nome, email, senha);
        this.setor = setor;
    }

    public Funcionario(Funcionario m) {
        this(m.getNome(), m.getEmail(), m.getSenha());
        this.setor = m.setor;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {
        return super.getNome();
    }
}
