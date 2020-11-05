package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medico extends Usuario{

    private String cbos_codigo = null;
    private Conselho conselho = null;

    public Medico() {
        super();
    }

    public Medico(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Medico(String nome, String email, String senha, String cbos_codigo, Conselho conselho) {
        this(nome, email, senha);
        this.cbos_codigo = cbos_codigo;
        this.conselho = conselho;
    }

    public Medico(Medico m) {
        this(m.getNome(), m.getEmail(), m.getSenha());
        this.cbos_codigo = m.cbos_codigo;
        this.conselho = m.conselho;
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
        return super.getNome();
    }
}
