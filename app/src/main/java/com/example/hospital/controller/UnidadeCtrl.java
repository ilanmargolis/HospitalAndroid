package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Unidade;

import java.util.List;

public class UnidadeCtrl {

    private final Context context;

    public UnidadeCtrl(Context context) {
        this.context = context;
    }

    public String insert(Unidade unidade){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.unidadeDao().insert(unidade);

            return "Unidade incluída com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir unidade: " + e;
        }
    }

    public String update(Unidade unidade){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.unidadeDao().update(unidade);

            return "Unidade alterada com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar unidade: " + e;
        }
    }

    public String delete(Unidade unidade){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.unidadeDao().update(unidade);

            return "Unidade excluída com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir unidade: " + e;
        }
    }

    public List<Unidade> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.unidadeDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Unidade getById(long id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.unidadeDao().getById(id);
        } catch (Exception e) {
            return null;
        }
    }
}