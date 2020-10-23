package com.example.hospital.model;

public class Terminologia {

    private int id;
    private String termo;
    private String descricao;

    public Terminologia() {
    }

    public Terminologia(int id, String termo, String descricao) {
        this.id = id;
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
