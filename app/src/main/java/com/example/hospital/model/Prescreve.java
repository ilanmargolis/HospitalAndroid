package com.example.hospital.model;

import java.sql.Time;

public class Prescreve {

    private byte dosagem;
    private Time horario;
    private boolean suspender;
    private Internado internado;
    private Medico medico;
    private Medicamento medicamento;

    public Prescreve() {
    }

    public Prescreve(byte dosagem, Time horario, boolean suspender, Internado internado, Medico medico, Medicamento medicamento) {
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

    public byte getDosagem() {
        return dosagem;
    }

    public void setDosagem(byte dosagem) {
        this.dosagem = dosagem;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public boolean isSuspender() {
        return suspender;
    }

    public void setSuspender(boolean suspender) {
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
