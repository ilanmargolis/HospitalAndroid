package com.example.hospital.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity
public class Prescreve {

    @PrimaryKey
    private Long id;
    private Byte dosagem;
    private Time horario;
    private Byte suspender;
    private Internado internado;
    private Medico medico;
    private Medicamento medicamento;

    public Prescreve() {
    }

    public Prescreve(byte dosagem, Time horario, Byte suspender, Internado internado, Medico medico, Medicamento medicamento) {
        this.dosagem = dosagem;
        this.horario = horario;
        this.suspender = suspender;
        this.internado = internado;
        this.medico = medico;
        this.medicamento = medicamento;
    }

    public Prescreve(Prescreve p) {
        this.dosagem = p.dosagem;
        this.horario = p.horario;
        this.suspender = p.suspender;
        this.internado = p.internado;
        this.medico = p.medico;
        this.medicamento = p.medicamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getDosagem() {
        return dosagem;
    }

    public void setDosagem(Byte dosagem) {
        this.dosagem = dosagem;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public Byte getSuspender() {
        return suspender;
    }

    public void setSuspender(Byte suspender) {
        this.suspender = suspender;
    }

    public Internado getInternado() {
        return internado;
    }

    public void setInternado(Internado internado) {
        this.internado = internado;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public String toString() {
        return medico.getNome().toString() + " (" + medicamento + ") -> " +
                internado.getPaciente().getNome().toString();
    }
}
