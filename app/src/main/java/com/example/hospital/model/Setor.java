package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Setor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private String ramal;

    public Setor() {
    }

    public Setor(String nome, String ramal) {
        this.nome = nome;
        this.ramal = ramal;
    }

    public Setor(Setor u) {
        this.id = u.id;
        this.nome = u.nome;
        this.ramal = u.ramal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    @Override
    public String toString() {
        return nome;
    }
}
