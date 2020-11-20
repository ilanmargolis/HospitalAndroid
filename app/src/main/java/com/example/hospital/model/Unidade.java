package com.example.hospital.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Unidade implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nome;
    private String logradouro;
    private String inscricaoEstadual;
    private String telefone;

    public Unidade() {
    }

    public Unidade(String nome, String logradouro, String inscricaoEstadual, String telefone) {
        this.nome = nome;
        this.logradouro = logradouro;
        this.inscricaoEstadual = inscricaoEstadual;
        this.telefone = telefone;
    }

    public Unidade(Unidade u) {
        this.id = u.id;
        this.nome = u.nome;
        this.logradouro = u.logradouro;
        this.inscricaoEstadual = u.inscricaoEstadual;
        this.telefone = u.telefone;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unidade unidade = (Unidade) o;
        return Objects.equals(nome, unidade.nome);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return nome;
    }
}
