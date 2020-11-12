package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Medico extends Usuario implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Cbos cbos;
    private Conselho conselho;

    public Medico() {
        super();
    }

    public Medico(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Medico(String nome, String email, String senha, Cbos cbos, Conselho conselho) {
        this(nome, email, senha);
        this.cbos = cbos;
        this.conselho = conselho;
    }

    public Medico(Medico m) {
        this(m.getNome(), m.getEmail(), m.getSenha());
        this.cbos = m.cbos;
        this.conselho = m.conselho;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cbos getCbos() {
        return cbos;
    }

    public void setCbos(Cbos cbos) {
        this.cbos = cbos;
    }

    public Conselho getConselho() {
        return conselho;
    }

    public void setConselho(Conselho conselho) {
        this.conselho = conselho;
    }

    @Override
    public String toString() {
        return super.getNome();
    }
}
