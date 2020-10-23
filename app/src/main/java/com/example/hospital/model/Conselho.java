package com.example.hospital.model;

public class Conselho {

    private String sigla; // crm
    private String numero;
    private String uf;

    public Conselho() {
    }

    public Conselho(String sigla, String numero, String uf) {
        this.sigla = sigla;
        this.numero = numero;
        this.uf = uf;
    }

    public Conselho(Conselho c) {
        this.sigla = c.sigla;
        this.numero = c.numero;
        this.uf = c.uf;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return sigla + ' ' + numero + '-' + uf;
    }
}
