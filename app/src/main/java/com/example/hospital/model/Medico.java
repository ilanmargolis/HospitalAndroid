package com.example.hospital.model;

public class Medico {

    private long id;
    private String nome;
    private String cbos_codigo;
    private Conselho conselho;

    public Medico() {
    }

    public Medico(long id, String nome, String cbos_codigo, Conselho conselho) {
        this.id = id;
        this.nome = nome;
        this.cbos_codigo = cbos_codigo;
        this.conselho = conselho;
    }

    public Medico(Medico m) {
        this.id = m.id;
        this.nome = m.nome;
        this.cbos_codigo = m.cbos_codigo;
        this.conselho = m.conselho;
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

    public String getCbos_codigo() {
        return cbos_codigo;
    }

    public void setCbos_codigo(String cbos_codigo) {
        this.cbos_codigo = cbos_codigo;
    }

    public Conselho getConselho() {
        return conselho;
    }

    public void setConselho(Conselho conselho) {
        this.conselho = conselho;
    }

    @Override
    public String toString() {
        return nome;
    }
}
