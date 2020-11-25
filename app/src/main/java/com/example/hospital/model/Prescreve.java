package com.example.hospital.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Time;

@Entity
public class Prescreve implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private byte dosagem;
    private String horario;
    private boolean suspender;
    @Embedded(prefix = "internado_")
    private Internado internado;
    @Embedded(prefix = "medico_")
    private Medico medico;
    @Embedded(prefix = "medicamento_")
    private Medicamento medicamento;

    public Prescreve() {
    }

    public Prescreve(byte dosagem, String horario, boolean suspender, Internado internado, Medico medico, Medicamento medicamento) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getDosagem() {
        return dosagem;
    }

    public void setDosagem(byte dosagem) {
        this.dosagem = dosagem;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
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
