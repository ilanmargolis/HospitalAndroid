package com.example.hospital.model;

import java.util.Date;

public class Medicamento {

    private int id;
    private String nome;
    private Date dataValidade;
    private Terminologia terminologia;

    public Medicamento() {
    }

    public Medicamento(int id, String nome, Date dataValidade, Terminologia terminologia) {
        this.id = id;
        this.nome = nome;
        this.dataValidade = dataValidade;
        this.terminologia = terminologia;
    }

    public Medicamento(Medicamento m) {
        this.id = m.id;
        this.nome = m.nome;
        this.dataValidade = m.dataValidade;
        this.terminologia = m.terminologia;
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

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Terminologia getTerminologia() {
        return terminologia;
    }

    public void setTerminologia(Terminologia terminologia) {
        this.terminologia = terminologia;
    }

    @Override
    public String toString() {
        return nome;
    }
}
