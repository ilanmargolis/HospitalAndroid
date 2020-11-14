package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Funcionario extends Usuario implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Setor setor;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Funcionario(String nome, String email, String senha, Setor setor) {
        super(nome, email, senha);
        this.setor = setor;
    }

    public Funcionario(Funcionario m) {
        this(m.getNome(), m.getEmail(), m.getSenha());
        this.setor = m.setor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {
        return super.getNome();
    }
}
