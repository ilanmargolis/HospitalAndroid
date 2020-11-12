package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Terminologia;

import java.util.List;

public class TerminologiaCtrl {

    private final Context context;

    public TerminologiaCtrl(Context context) {
        this.context = context;
    }

    public String insert(Terminologia terminologia){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.terminologiaDao().insert(terminologia);

            return "Terminologia incluída com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir terminologia: " + e;
        }
    }

    public String update(Terminologia terminologia){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.terminologiaDao().update(terminologia);

            return "Terminologia alterada com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar terminologia: " + e;
        }
    }

    public String delete(Terminologia terminologia){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.terminologiaDao().update(terminologia);

            return "Terminologia excluída com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir terminologia: " + e;
        }
    }

    public List<Terminologia> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.terminologiaDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Terminologia get(long id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.terminologiaDao().get(id);
        } catch (Exception e) {
            return null;
        }
    }
}