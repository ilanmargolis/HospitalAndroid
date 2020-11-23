package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Prescreve;

import java.io.Serializable;
import java.util.List;

public class PrescreveCtrl implements Serializable {

    private final Context context;

    public PrescreveCtrl(Context context) {
        this.context = context;
    }

    public String insert(Prescreve prescreve){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.prescreveDao().insert(prescreve);

            return "Prescreve incluído com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir prescreve: " + e;
        }
    }

    public String update(Prescreve prescreve){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.prescreveDao().update(prescreve);

            return "Prescreve alterado com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar prescreve: " + e;
        }
    }

    public String delete(Prescreve prescreve){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.prescreveDao().update(prescreve);

            return "Prescreve excluído com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir prescreve: " + e;
        }
    }

    public List<Prescreve> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.prescreveDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Prescreve> getByInternado(long internado_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.prescreveDao().getByInternado(internado_id);
        } catch (Exception e) {
            return null;
        }
    }
}