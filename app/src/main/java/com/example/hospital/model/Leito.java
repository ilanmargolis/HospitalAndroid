package com.example.hospital.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
 //       (foreignKeys = @ForeignKey(entity=Unidade.class, parentColumns="id", childColumns="unidade_id"))
public class Leito implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String codigo;
    @Embedded(prefix = "unidade_")
    private Unidade unidade;
    @Embedded(prefix = "setor_")
    private Setor setor;

    public Leito() {
    }

    public Leito(String codigo, Unidade unidade, Setor setor) {
        this.codigo = codigo;
        this.unidade = unidade;
        this.setor = setor;
    }

    public Leito(Leito l) {
        this.id = l.id;
        this.codigo = l.codigo;
        this.unidade = l.unidade;
        this.setor = l.setor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leito leito = (Leito) o;
        return Objects.equals(codigo, leito.codigo) &&
                Objects.equals(unidade, leito.unidade) &&
                Objects.equals(setor, leito.setor);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return codigo + " (" + unidade.getNome().toString() + ")";
    }
}
