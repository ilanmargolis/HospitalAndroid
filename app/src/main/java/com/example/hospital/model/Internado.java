package com.example.hospital.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.example.hospital.config.Converters;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters(Converters.class)
public class Internado implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date dataInternacao;
    private Date dataPrevisaoAlta;
    @Embedded(prefix = "leito_")
    private Leito leito;
    @Embedded(prefix = "paciente_")
    private Paciente paciente;

    public Internado() {
    }

    public Internado(Date dataInternacao, Date dataPrevisaoAlta, Leito leito, Paciente paciente) {
        this.dataInternacao = dataInternacao;
        this.dataPrevisaoAlta = dataPrevisaoAlta;
        this.leito = leito;
        this.paciente = paciente;
    }

    public Internado(Internado i) {
        this.id = i.id;
        this.dataInternacao = i.dataInternacao;
        this.dataPrevisaoAlta = i.dataPrevisaoAlta;
        this.leito = i.leito;
        this.paciente = i.paciente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataInternacao() {
        return dataInternacao;
    }

    public void setDataInternacao(Date dataInternacao) {
        this.dataInternacao = dataInternacao;
    }

    public Date getDataPrevisaoAlta() {
        return dataPrevisaoAlta;
    }

    public void setDataPrevisaoAlta(Date dataPrevisaoAlta) {
        this.dataPrevisaoAlta = dataPrevisaoAlta;
    }

    public Leito getLeito() {
        return leito;
    }

    public void setLeito(Leito leito) {
        this.leito = leito;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        if (paciente != null && leito != null) {
            return paciente.getNome().toString() + " (" +
                    leito.getCodigo().toString() + ")";
        } else {
            return dataInternacao.toString();
        }
    }
}
