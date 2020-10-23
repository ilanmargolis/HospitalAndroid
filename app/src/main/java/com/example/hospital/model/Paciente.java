package com.example.hospital.model;

import java.util.Date;

public class Paciente {

    private long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private Sexo sexo;

    public Paciente() {
    }

    public Paciente(long id, String nome, String cpf, Date dataNascimento, Sexo sexo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    public Paciente(Paciente p) {
        this.id = p.id;
        this.nome = p.nome;
        this.cpf = p.cpf;
        this.dataNascimento = p.dataNascimento;
        this.sexo = p.sexo;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }
}
