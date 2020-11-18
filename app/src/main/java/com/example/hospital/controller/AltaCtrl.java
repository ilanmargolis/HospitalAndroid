package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Alta;

import java.io.Serializable;
import java.util.List;

public class AltaCtrl implements Serializable {

    private final Context context;

    public AltaCtrl(Context context) {
        this.context = context;
    }

    public String insert(Alta alta){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.altaDao().insert(alta);

            return "Alta incluída com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir alta: " + e;
        }
    }

    public String update(Alta alta){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.altaDao().update(alta);

            return "Alta alterada com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar alta: " + e;
        }
    }

    public String delete(Alta alta){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.altaDao().update(alta);

            return "Alta excluída com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir alta: " + e;
        }
    }

    public List<Alta> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.altaDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Alta get(long internado_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.altaDao().get(internado_id);
        } catch (Exception e) {
            return null;
        }
    }
}