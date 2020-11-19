package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Cbos;

import java.io.Serializable;
import java.util.List;

public class CbosCtrl implements Serializable {

    private final Context context;

    public CbosCtrl(Context context) {
        this.context = context;
    }

    public String insert(Cbos cbos){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.cbosDao().insert(cbos);

            return "Cbos incluído com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir cbos: " + e;
        }
    }

    public String update(Cbos cbos){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.cbosDao().update(cbos);

            return "Cbos alterado com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar cbos: " + e;
        }
    }

    public String delete(Cbos cbos){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.cbosDao().update(cbos);

            return "Cbos excluído com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir cbos: " + e;
        }
    }

    public List<Cbos> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.cbosDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Cbos getByCodigo(String codigo){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.cbosDao().getByCodigo(codigo);
        } catch (Exception e) {
            return null;
        }
    }
}