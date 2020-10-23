package com.example.hospital.model;

public class Leito {

    private long id;
    private String codigo;
    private Unidade unidade;

    public Leito() {
    }

    public Leito(long id, String codigo, Unidade unidade) {
        this.id = id;
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
