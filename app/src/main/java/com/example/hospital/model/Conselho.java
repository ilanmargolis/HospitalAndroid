package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Conselho implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String codigo;
    private String sigla; // crm
    private String descricao;

    public Conselho() {
    }

    public Conselho(int id, String codigo, String sigla, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
