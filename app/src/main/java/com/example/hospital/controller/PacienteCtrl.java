package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Paciente;

import java.util.List;

public class PacienteCtrl {

    private final Context context;

    public PacienteCtrl(Context context) {
        this.context = context;
    }

    public String insert(Paciente paciente){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.pacienteDao().insert(paciente);

            return "Paciente incluída com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir paciente: " + e;
        }
    }

    public String update(Paciente paciente){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.pacienteDao().update(paciente);

            return "Paciente alterada com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar paciente: " + e;
        }
    }

    public String delete(Paciente paciente){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.pacienteDao().delete(paciente);

            return "Paciente excluída com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir paciente: " + e;
        }
    }

    public List<Paciente> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.pacienteDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Paciente getByEmail(String email){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.pacienteDao().getByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

}
