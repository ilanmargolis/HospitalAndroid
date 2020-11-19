package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Conselho;

import java.util.List;

public class ConselhoCtrl {

    private final Context context;

    public ConselhoCtrl(Context context) {
        this.context = context;
    }

    public String insert(Conselho conselho){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.conselhoDao().insert(conselho);

            return "Conselho incluída com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir conselho: " + e;
        }
    }

    public String update(Conselho conselho){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.conselhoDao().update(conselho);

            return "Conselho alterada com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar conselho: " + e;
        }
    }

    public String delete(Conselho conselho){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.conselhoDao().update(conselho);

            return "Terminologia excluída com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir conselho: " + e;
        }
    }

    public List<Conselho> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.conselhoDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Conselho getById(long id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.conselhoDao().getById(id);
        } catch (Exception e) {
            return null;
        }
    }
}