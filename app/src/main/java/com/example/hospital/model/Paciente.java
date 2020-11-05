package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Paciente extends Usuario implements Serializable {

    private String cpf = null;
    private Date dataNascimento = null;
    private Character sexo = null;

    public Paciente() {
        super();
    }

    public Paciente(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Paciente(String nome, String email, String senha, String cpf, Date dataNascimento, Character sexo) {
        this(nome, email, senha);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    public Paciente(Paciente p) {
        this(p.getNome(), p.getEmail(), p.getSenha());
        this.cpf = p.cpf;
        this.dataNascimento = p.dataNascimento;
        this.sexo = p.sexo;
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

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return super.getNome() + " (" + cpf + ")";
    }
}
