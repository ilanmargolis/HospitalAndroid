package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Terminologia implements Serializable {

    @PrimaryKey
    private int id;
    private String termo;
    private String descricao;

    public Terminologia() {
    }

    public Terminologia(String termo, String descricao) {
        this.termo = termo;
        this.descricao = descricao;
    }

    public Terminologia(Terminologia t) {
        this.id = t.id;
        this.termo = t.termo;
        this.descricao = t.descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return termo;
    }
}
