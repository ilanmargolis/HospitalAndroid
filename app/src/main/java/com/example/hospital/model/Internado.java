package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.hospital.config.Converters;

import java.util.Date;

@Entity
@TypeConverters(Converters.class)
public class Internado {

    @PrimaryKey
    private long id;
    private Date dataInternacao;
    private Date dataPrecisaoAlta;
    private Date dataAlta;
    private Leito leito;
    private Paciente paciente;

    public Internado() {
    }

    public Internado(Date dataInternacao, Date dataPrecisaoAlta, Date dataAlta, Leito leito, Paciente paciente) {
        this.dataInternacao = dataInternacao;
        this.dataPrecisaoAlta = dataPrecisaoAlta;
        this.dataAlta = dataAlta;
        this.leito = leito;
        this.paciente = paciente;
    }

    public Internado(Internado i) {
        this.id = i.id;
        this.dataInternacao = i.dataInternacao;
        this.dataPrecisaoAlta = i.dataPrecisaoAlta;
        this.dataAlta = i.dataAlta;
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

    public Date getDataPrecisaoAlta() {
        return dataPrecisaoAlta;
    }

    public void setDataPrecisaoAlta(Date dataPrecisaoAlta) {
        this.dataPrecisaoAlta = dataPrecisaoAlta;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
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
        return paciente.getNome().toString() + " (" +
                 leito.getCodigo().toString() + ")";
    }
}
