package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Unidade {

    @PrimaryKey
    private long id;
    private String nome;
    private String logradouro;
    private String inscricaoEstadual;

    public Unidade() {
    }

    public Unidade(long id, String nome, String logradouro, String inscricaoEstadual) {
        this.id = id;
        this.nome = nome;
        this.logradouro = logradouro;
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Unidade(Unidade u) {
        this.id = u.id;
        this.nome = u.nome;
        this.logradouro = u.logradouro;
        this.inscricaoEstadual = u.inscricaoEstadual;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    @Override
    public String toString() {
        return nome;
    }
}
