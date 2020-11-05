package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Leito implements Serializable {

    @PrimaryKey
    private long id;
    private String codigo;
    private Unidade unidade;

    public Leito() {
    }

    public Leito(String codigo, Unidade unidade) {
        this.codigo = codigo;
        this.unidade = unidade;
    }

    public Leito(Leito l) {
        this.id = l.id;
        this.codigo = l.codigo;
        this.unidade = l.unidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public String toString() {
        return codigo + " (" + unidade.getNome().toString() + ")";
    }
}
