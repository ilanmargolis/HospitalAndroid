package com.example.hospital.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.hospital.config.Converters;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters(Converters.class)
public class Alta implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @Embedded(prefix = "internado_")
    private Internado internado;
    @Embedded(prefix = "medico_")
    private Medico medico;
    private Date data;

    public Alta() {
    }

    public Alta(Internado internado, Medico medico, Date data) {
        this.internado = internado;
        this.medico = medico;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Alta{" +
                "data=" + data +
                ", medico=" + medico +
                '}';
    }
}
