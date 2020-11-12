package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Medicamento implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nome;
    private Date dataValidade;
    private Terminologia terminologia;

    public Medicamento() {
    }

    public Medicamento(String nome, Date dataValidade, Terminologia terminologia) {
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
