package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Medico;

import java.util.List;

public class MedicoCtrl {

    private final Context context;

    public MedicoCtrl(Context context) {
        this.context = context;
    }

    public String insert(Medico medico){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.medicoDao().insert(medico);

            return "Médico incluído com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir médico: " + e;
        }
    }

    public String update(Medico medico){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.medicoDao().update(medico);

            return "Médico alterado com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar médico: " + e;
        }
    }

    public String delete(Medico medico){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.medicoDao().delete(medico);

            return "Médico excluído com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir médico: " + e;
        }
    }

    public List<Medico> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.medicoDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Medico getById(long id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.medicoDao().getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public Medico getByEmail(String email){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.medicoDao().getByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
}
